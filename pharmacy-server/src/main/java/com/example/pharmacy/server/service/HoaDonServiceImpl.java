package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.enums.AuditAction;
import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.common.exception.BusinessException;
import com.example.pharmacy.common.request.CreateHoaDonLineRequest;
import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.common.request.HoaDonSearchRequest;
import com.example.pharmacy.server.repository.HoaDonRepository;
import com.example.pharmacy.server.transaction.TransactionManager;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.HoaDonDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HoaDonServiceImpl implements HoaDonService {
    private final TransactionManager transactionManager;
    private final HoaDonRepository hoaDonRepository;
    private final CodeGenerationService codeGenerationService;
    private final AuditService auditService;

    public HoaDonServiceImpl(
            TransactionManager transactionManager,
            HoaDonRepository hoaDonRepository,
            CodeGenerationService codeGenerationService,
            AuditService auditService
    ) {
        this.transactionManager = Objects.requireNonNull(transactionManager, "transactionManager must not be null");
        this.hoaDonRepository = Objects.requireNonNull(hoaDonRepository, "hoaDonRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
        this.auditService = Objects.requireNonNull(auditService, "auditService must not be null");
    }

    @Override
    public String generateNewMaHoaDon() {
        return codeGenerationService.nextCode(BusinessCodeType.HOA_DON);
    }

    @Override
    public String createInvoice(CreateHoaDonRequest request, UserContext actor) {
        validateInvoiceRequest(request, actor);
        return transactionManager.execute(() -> {
            String maHoaDon = isBlank(request.getMaHoaDon())
                    ? codeGenerationService.nextCode(BusinessCodeType.HOA_DON)
                    : request.getMaHoaDon().trim();

            hoaDonRepository.insertHeader(maHoaDon, actor.getEmployeeId(), request);

            for (CreateHoaDonLineRequest line : request.getLines()) {
                allocateAndInsertLine(maHoaDon, request, line);
            }

            if (!isBlank(request.getMaPhieuDat())) {
                hoaDonRepository.updatePreorderStatus(request.getMaPhieuDat().trim(), 2);
            }

            auditService.logAction(actor, AuditAction.PAYMENT, "HoaDonDto", maHoaDon,
                    "Lap hoa don voi " + request.getLines().size() + " dong san pham.");
            return maHoaDon;
        });
    }

    @Override
    public List<HoaDonDto> findAll() {
        return hoaDonRepository.findAll();
    }

    @Override
    public HoaDonDto findById(String maHoaDon) {
        if (isBlank(maHoaDon)) {
            return null;
        }
        return hoaDonRepository.findById(maHoaDon.trim());
    }

    @Override
    public List<ChiTietHoaDonDto> findDetailsByMaHD(String maHoaDon) {
        if (isBlank(maHoaDon)) {
            return List.of();
        }
        return hoaDonRepository.findDetailsByMaHD(maHoaDon.trim());
    }

    @Override
    public List<HoaDonDto> search(HoaDonSearchRequest request) {
        if (request == null) {
            return findAll();
        }
        String keyword = normalize(request.getKeyword());
        String criteria = normalize(request.getCriteria());
        LocalDate fromDate = request.getFromDate();
        LocalDate toDate = request.getToDate();

        List<HoaDonDto> source = hoaDonRepository.findAll();
        return source.stream()
                .filter(hoaDon -> matchKeyword(hoaDon, criteria, keyword))
                .filter(hoaDon -> matchDate(hoaDon, fromDate, toDate))
                .toList();
    }

    private void allocateAndInsertLine(String maHoaDon, CreateHoaDonRequest request, CreateHoaDonLineRequest line) {
        HoaDonRepository.UnitConversion conversion = hoaDonRepository.findUnitConversion(line.getMaThuoc(), line.getMaDvt());
        if (conversion == null) {
            throw new BusinessException("Khong tim thay quy doi don vi cho thuoc " + line.getMaThuoc() + ".");
        }

        int heSoQuyDoi = normalizeConversionFactor(conversion.heSoQuyDoi());
        int requestedDisplayQty = line.getSoLuong();
        List<HoaDonRepository.LotStock> lots = hoaDonRepository.findLotsForUpdate(line.getMaThuoc());
        if (lots.isEmpty()) {
            throw new BusinessException("Khong con lo hang kha dung cho thuoc " + line.getMaThuoc() + ".");
        }

        int totalAvailableDisplay = lots.stream()
                .mapToInt(lot -> lot.soLuongTon() / heSoQuyDoi)
                .sum();
        if (totalAvailableDisplay < requestedDisplayQty) {
            throw new BusinessException("Khong du ton kho de ban thuoc " + line.getMaThuoc() + ".");
        }

        int remainingDisplay = requestedDisplayQty;
        BigDecimal remainingDiscount = safeMoney(line.getGiamGia());
        List<AllocatedLot> allocations = new ArrayList<>();

        for (HoaDonRepository.LotStock lot : lots) {
            if (remainingDisplay <= 0) {
                break;
            }
            int availableDisplay = lot.soLuongTon() / heSoQuyDoi;
            if (availableDisplay <= 0) {
                continue;
            }
            int allocateDisplay = Math.min(remainingDisplay, availableDisplay);
            int allocateBase = allocateDisplay * heSoQuyDoi;
            allocations.add(new AllocatedLot(lot.maLo(), allocateDisplay, allocateBase));
            remainingDisplay -= allocateDisplay;
        }

        if (remainingDisplay > 0) {
            throw new BusinessException("Khong the phan bo du lo hang cho thuoc " + line.getMaThuoc() + ".");
        }

        int remainingDisplayForDiscount = requestedDisplayQty;
        for (int index = 0; index < allocations.size(); index++) {
            AllocatedLot allocation = allocations.get(index);
            boolean isLast = index == allocations.size() - 1;
            BigDecimal allocatedDiscount;
            if (isLast) {
                allocatedDiscount = remainingDiscount;
            } else if (requestedDisplayQty <= 0 || remainingDiscount.signum() == 0) {
                allocatedDiscount = BigDecimal.ZERO;
            } else {
                allocatedDiscount = safeMoney(line.getGiamGia())
                        .multiply(BigDecimal.valueOf(allocation.soLuongDisplay()))
                        .divide(BigDecimal.valueOf(requestedDisplayQty), 2, RoundingMode.HALF_UP);
                if (allocatedDiscount.compareTo(remainingDiscount) > 0) {
                    allocatedDiscount = remainingDiscount;
                }
            }

            hoaDonRepository.insertDetail(
                    maHoaDon,
                    allocation.maLo(),
                    line.getMaDvt(),
                    allocation.soLuongDisplay(),
                    safeMoney(line.getDonGia()).doubleValue(),
                    allocatedDiscount.doubleValue()
            );

            int reservedConsume = isBlank(request.getMaPhieuDat()) ? 0 : allocation.soLuongBase();
            boolean updated = hoaDonRepository.updateLotAfterSale(allocation.maLo(), allocation.soLuongBase(), reservedConsume);
            if (!updated) {
                throw new BusinessException("Khong the cap nhat ton kho cho lo " + allocation.maLo() + ".");
            }

            remainingDiscount = remainingDiscount.subtract(allocatedDiscount).max(BigDecimal.ZERO);
            remainingDisplayForDiscount -= allocation.soLuongDisplay();
        }
    }

    private void validateInvoiceRequest(CreateHoaDonRequest request, UserContext actor) {
        if (actor == null || isBlank(actor.getEmployeeId())) {
            throw new BusinessException("UserContext khong hop le de lap hoa don.");
        }
        if (request == null) {
            throw new BusinessException("CreateHoaDonRequest khong duoc null.");
        }
        if (isBlank(request.getLoaiHoaDon())) {
            throw new BusinessException("Loai hoa don bat buoc phai co.");
        }
        if ("ETC".equalsIgnoreCase(request.getLoaiHoaDon()) && isBlank(request.getMaDonThuoc())) {
            throw new BusinessException("Hoa don ETC bat buoc phai co ma don thuoc.");
        }
        if (request.getLines() == null || request.getLines().isEmpty()) {
            throw new BusinessException("Hoa don phai co it nhat mot dong chi tiet.");
        }
        for (CreateHoaDonLineRequest line : request.getLines()) {
            if (line == null || isBlank(line.getMaThuoc()) || isBlank(line.getMaDvt())) {
                throw new BusinessException("Dong hoa don thieu ma thuoc hoac ma don vi tinh.");
            }
            if (line.getSoLuong() <= 0) {
                throw new BusinessException("So luong ban phai lon hon 0.");
            }
            if (safeMoney(line.getDonGia()).compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Don gia khong duoc am.");
            }
            if (safeMoney(line.getGiamGia()).compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Giam gia dong hang khong duoc am.");
            }
        }
    }

    private boolean matchKeyword(HoaDonDto hoaDon, String criteria, String keyword) {
        if (keyword.isEmpty() && criteria.isEmpty()) {
            return true;
        }
        if ("khach vang lai".equals(criteria)) {
            return hoaDon.getMaKH() == null || isBlank(hoaDon.getMaKH().getMaKH());
        }
        if (keyword.isEmpty()) {
            return true;
        }

        String maHoaDon = normalize(hoaDon.getMaHD());
        String tenKh = hoaDon.getMaKH() == null ? "" : normalize(hoaDon.getMaKH().getTenKH());
        String sdtKh = hoaDon.getMaKH() == null ? "" : normalize(hoaDon.getMaKH().getSdt());
        String tenNv = hoaDon.getMaNV() == null ? "" : normalize(hoaDon.getMaNV().getTenNV());

        return switch (criteria) {
            case "ma hoa don" -> maHoaDon.contains(keyword);
            case "ten khach hang" -> tenKh.contains(keyword);
            case "sdt khach hang" -> sdtKh.contains(keyword);
            case "ten nhan vien" -> tenNv.contains(keyword);
            default -> maHoaDon.contains(keyword) || tenKh.contains(keyword) || sdtKh.contains(keyword) || tenNv.contains(keyword);
        };
    }

    private boolean matchDate(HoaDonDto hoaDon, LocalDate fromDate, LocalDate toDate) {
        if (hoaDon.getNgayLap() == null) {
            return fromDate == null && toDate == null;
        }
        LocalDate ngayLap = hoaDon.getNgayLap().toLocalDateTime().toLocalDate();
        if (fromDate != null && ngayLap.isBefore(fromDate)) {
            return false;
        }
        if (toDate != null && ngayLap.isAfter(toDate)) {
            return false;
        }
        return true;
    }

    private BigDecimal safeMoney(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private int normalizeConversionFactor(double value) {
        int normalized = (int) Math.round(value);
        return Math.max(1, normalized);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private record AllocatedLot(String maLo, int soLuongDisplay, int soLuongBase) {
    }
}

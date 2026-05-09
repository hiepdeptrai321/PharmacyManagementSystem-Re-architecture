package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.enums.AuditAction;
import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.common.exception.BusinessException;
import com.example.pharmacy.common.request.CreatePhieuDoiItemRequest;
import com.example.pharmacy.common.request.CreatePhieuDoiRequest;
import com.example.pharmacy.common.request.CreatePhieuTraItemRequest;
import com.example.pharmacy.common.request.CreatePhieuTraRequest;
import com.example.pharmacy.server.repository.DoiTraRepository;
import com.example.pharmacy.server.transaction.TransactionManager;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.ChiTietPhieuDoiHangDto;
import com.example.pharmacy.common.model.ChiTietPhieuTraHangDto;
import com.example.pharmacy.common.model.HoaDonDto;
import com.example.pharmacy.common.model.PhieuDoiHangDto;
import com.example.pharmacy.common.model.PhieuTraHangDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DoiTraServiceImpl implements DoiTraService {
    private final TransactionManager transactionManager;
    private final DoiTraRepository doiTraRepository;
    private final CodeGenerationService codeGenerationService;
    private final AuditService auditService;

    public DoiTraServiceImpl(
            TransactionManager transactionManager,
            DoiTraRepository doiTraRepository,
            CodeGenerationService codeGenerationService,
            AuditService auditService
    ) {
        this.transactionManager = Objects.requireNonNull(transactionManager, "transactionManager must not be null");
        this.doiTraRepository = Objects.requireNonNull(doiTraRepository, "doiTraRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
        this.auditService = Objects.requireNonNull(auditService, "auditService must not be null");
    }

    @Override
    public HoaDonDto findHoaDonGocForDoiTra(String maHoaDon) {
        if (isBlank(maHoaDon)) {
            return null;
        }
        return doiTraRepository.findHoaDonGoc(maHoaDon.trim());
    }

    @Override
    public List<ChiTietHoaDonDto> findHoaDonDetailsForDoiTra(String maHoaDon) {
        if (isBlank(maHoaDon)) {
            return List.of();
        }
        return doiTraRepository.findHoaDonDetails(maHoaDon.trim());
    }

    @Override
    public int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh) {
        if (isBlank(maHoaDon) || isBlank(maLoHang) || isBlank(maDonViTinh)) {
            return 0;
        }
        return doiTraRepository.getSoLuongDaDoi(maHoaDon.trim(), maLoHang.trim(), maDonViTinh.trim());
    }

    @Override
    public int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh) {
        if (isBlank(maHoaDon) || isBlank(maLoHang) || isBlank(maDonViTinh)) {
            return 0;
        }
        return doiTraRepository.getSoLuongDaTra(maHoaDon.trim(), maLoHang.trim(), maDonViTinh.trim());
    }

    @Override
    public String createPhieuDoi(CreatePhieuDoiRequest request, UserContext actor) {
        validateActor(actor);
        validateCreatePhieuDoiRequest(request);
        return transactionManager.execute(() -> {
            HoaDonDto hoaDonGoc = requireEligibleHoaDon(request.getMaHoaDonGoc(), true);
            List<ChiTietHoaDonDto> invoiceDetails = doiTraRepository.findHoaDonDetails(request.getMaHoaDonGoc().trim());
            String maPhieuDoi = codeGenerationService.nextCode(BusinessCodeType.PHIEU_DOI_HANG);

            doiTraRepository.insertPhieuDoiHeader(maPhieuDoi, request, actor.getEmployeeId());

            for (CreatePhieuDoiItemRequest item : request.getItems()) {
                ChiTietHoaDonDto invoiceLine = requireInvoiceLine(invoiceDetails, item.getMaLoHang(), item.getMaDonViTinh());
                int soldQty = invoiceLine.getSoLuong();
                int daDoi = doiTraRepository.getSoLuongDaDoi(request.getMaHoaDonGoc().trim(), item.getMaLoHang().trim(), item.getMaDonViTinh().trim());
                int remaining = soldQty - daDoi;
                if (item.getSoLuong() > remaining) {
                    throw new BusinessException("So luong doi vuot qua so luong con duoc doi cua san pham " + safeTenThuoc(invoiceLine) + ".");
                }

                DoiTraRepository.UnitConversion conversion = requireUnitConversion(item.getMaThuoc(), item.getMaDonViTinh());
                int heSoQuyDoi = normalizeConversionFactor(conversion.heSoQuyDoi());
                List<AllocatedLot> allocations = allocateReplacementLots(item.getMaThuoc(), item.getSoLuong(), heSoQuyDoi);

                for (AllocatedLot allocation : allocations) {
                    doiTraRepository.insertPhieuDoiDetail(maPhieuDoi, allocation.maLo(), item, allocation.soLuongDisplay());
                    boolean updated = doiTraRepository.deductStock(allocation.maLo(), allocation.soLuongBase());
                    if (!updated) {
                        throw new BusinessException("Khong the tru ton kho cho lo " + allocation.maLo() + ".");
                    }
                }
            }

            auditService.logAction(actor, AuditAction.CREATE, "PhieuDoiHangDto", maPhieuDoi,
                    "Lap phieu doi voi " + request.getItems().size() + " dong chi tiet.");
            return maPhieuDoi;
        });
    }

    @Override
    public String createPhieuTra(CreatePhieuTraRequest request, UserContext actor) {
        validateActor(actor);
        validateCreatePhieuTraRequest(request);
        return transactionManager.execute(() -> {
            HoaDonDto hoaDonGoc = requireEligibleHoaDon(request.getMaHoaDonGoc(), true);
            List<ChiTietHoaDonDto> invoiceDetails = doiTraRepository.findHoaDonDetails(request.getMaHoaDonGoc().trim());
            if (invoiceDetails.stream().anyMatch(detail -> detail.getGiamGia() > 0)) {
                throw new BusinessException("Khong the tra hang voi hoa don co ap dung giam gia tren dong san pham.");
            }

            String maPhieuTra = codeGenerationService.nextCode(BusinessCodeType.PHIEU_TRA_HANG);
            doiTraRepository.insertPhieuTraHeader(maPhieuTra, request, actor.getEmployeeId());

            for (CreatePhieuTraItemRequest item : request.getItems()) {
                ChiTietHoaDonDto invoiceLine = requireInvoiceLine(invoiceDetails, item.getMaLoHang(), item.getMaDonViTinh());
                int soldQty = invoiceLine.getSoLuong();
                int daTra = doiTraRepository.getSoLuongDaTra(request.getMaHoaDonGoc().trim(), item.getMaLoHang().trim(), item.getMaDonViTinh().trim());
                int remaining = soldQty - daTra;
                if (item.getSoLuong() > remaining) {
                    throw new BusinessException("So luong tra vuot qua so luong con duoc tra cua san pham " + safeTenThuoc(invoiceLine) + ".");
                }

                DoiTraRepository.UnitConversion conversion = requireUnitConversion(item.getMaThuoc(), item.getMaDonViTinh());
                int heSoQuyDoi = normalizeConversionFactor(conversion.heSoQuyDoi());
                int soLuongBase = item.getSoLuong() * heSoQuyDoi;

                doiTraRepository.insertPhieuTraDetail(
                        maPhieuTra,
                        item,
                        invoiceLine.getDonGia(),
                        invoiceLine.getGiamGia()
                );
                boolean updated = doiTraRepository.addStock(item.getMaLoHang().trim(), soLuongBase);
                if (!updated) {
                    throw new BusinessException("Khong the cong ton kho cho lo " + item.getMaLoHang() + ".");
                }
            }

            auditService.logAction(actor, AuditAction.RETURN, "PhieuTraHangDto", maPhieuTra,
                    "Lap phieu tra voi " + request.getItems().size() + " dong chi tiet.");
            return maPhieuTra;
        });
    }

    @Override
    public void attachKhachHangToHoaDon(String maHoaDon, String maKhachHang, UserContext actor) {
        validateActor(actor);
        if (isBlank(maHoaDon) || isBlank(maKhachHang)) {
            throw new BusinessException("Ma hoa don va ma khach hang khong duoc de trong.");
        }
        transactionManager.execute(() -> {
            HoaDonDto hoaDon = doiTraRepository.findHoaDonGoc(maHoaDon.trim());
            if (hoaDon == null) {
                throw new BusinessException("Khong tim thay hoa don de cap nhat khach hang.");
            }
            doiTraRepository.attachKhachHangToHoaDon(maHoaDon.trim(), maKhachHang.trim());
            auditService.logAction(actor, AuditAction.UPDATE, "HoaDonDto", maHoaDon.trim(),
                    "Bo sung khach hang " + maKhachHang.trim() + " cho hoa don phuc vu doi/tra.");
            return null;
        });
    }

    @Override
    public List<PhieuDoiHangDto> findAllPhieuDoi() {
        return doiTraRepository.findAllPhieuDoi();
    }

    @Override
    public PhieuDoiHangDto findPhieuDoiById(String maPhieuDoi) {
        if (isBlank(maPhieuDoi)) {
            return null;
        }
        return doiTraRepository.findPhieuDoiById(maPhieuDoi.trim());
    }

    @Override
    public List<ChiTietPhieuDoiHangDto> findChiTietPhieuDoiByMaPD(String maPhieuDoi) {
        if (isBlank(maPhieuDoi)) {
            return List.of();
        }
        return doiTraRepository.findChiTietPhieuDoiByMaPD(maPhieuDoi.trim());
    }

    @Override
    public List<PhieuTraHangDto> findAllPhieuTra() {
        return doiTraRepository.findAllPhieuTra();
    }

    @Override
    public PhieuTraHangDto findPhieuTraById(String maPhieuTra) {
        if (isBlank(maPhieuTra)) {
            return null;
        }
        return doiTraRepository.findPhieuTraById(maPhieuTra.trim());
    }

    @Override
    public List<ChiTietPhieuTraHangDto> findChiTietPhieuTraByMaPT(String maPhieuTra) {
        if (isBlank(maPhieuTra)) {
            return List.of();
        }
        return doiTraRepository.findChiTietPhieuTraByMaPT(maPhieuTra.trim());
    }

    private HoaDonDto requireEligibleHoaDon(String maHoaDon, boolean requireCustomer) {
        HoaDonDto hoaDon = doiTraRepository.findHoaDonGoc(maHoaDon.trim());
        if (hoaDon == null) {
            throw new BusinessException("Khong tim thay hoa don goc.");
        }
        if (hoaDon.getNgayLap() == null) {
            throw new BusinessException("Hoa don goc khong hop le.");
        }
        LocalDate ngayLapHoaDon = hoaDon.getNgayLap().toLocalDateTime().toLocalDate();
        if (ngayLapHoaDon.isBefore(LocalDate.now().minusDays(7))) {
            throw new BusinessException("Hoa don da qua han 7 ngay, khong the doi/tra.");
        }
        if (requireCustomer && (hoaDon.getMaKH() == null || isBlank(hoaDon.getMaKH().getMaKH()))) {
            throw new BusinessException("Hoa don chua co khach hang. Vui long bo sung thong tin khach hang truoc khi doi/tra.");
        }
        return hoaDon;
    }

    private ChiTietHoaDonDto requireInvoiceLine(List<ChiTietHoaDonDto> details, String maLoHang, String maDonViTinh) {
        return details.stream()
                .filter(detail -> detail.getLoHang() != null
                        && detail.getLoHang().getMaLH() != null
                        && detail.getLoHang().getMaLH().equalsIgnoreCase(maLoHang)
                        && detail.getDvt() != null
                        && detail.getDvt().getMaDVT() != null
                        && detail.getDvt().getMaDVT().equalsIgnoreCase(maDonViTinh))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Khong tim thay dong hoa don phu hop de doi/tra."));
    }

    private DoiTraRepository.UnitConversion requireUnitConversion(String maThuoc, String maDvt) {
        DoiTraRepository.UnitConversion conversion = doiTraRepository.findUnitConversion(maThuoc.trim(), maDvt.trim());
        if (conversion == null) {
            throw new BusinessException("Khong tim thay quy doi don vi cho thuoc " + maThuoc + ".");
        }
        return conversion;
    }

    private List<AllocatedLot> allocateReplacementLots(String maThuoc, int requestedDisplayQty, int heSoQuyDoi) {
        List<DoiTraRepository.LotStock> lots = doiTraRepository.findLotsForUpdate(maThuoc.trim());
        if (lots.isEmpty()) {
            throw new BusinessException("Khong con lo hang kha dung de doi san pham " + maThuoc + ".");
        }
        int totalAvailableDisplay = lots.stream()
                .mapToInt(lot -> lot.soLuongTon() / heSoQuyDoi)
                .sum();
        if (totalAvailableDisplay < requestedDisplayQty) {
            throw new BusinessException("Khong du ton kho de doi san pham " + maThuoc + ".");
        }

        int remainingDisplay = requestedDisplayQty;
        List<AllocatedLot> allocations = new ArrayList<>();
        for (DoiTraRepository.LotStock lot : lots) {
            if (remainingDisplay <= 0) {
                break;
            }
            int availableDisplay = lot.soLuongTon() / heSoQuyDoi;
            if (availableDisplay <= 0) {
                continue;
            }
            int allocateDisplay = Math.min(remainingDisplay, availableDisplay);
            allocations.add(new AllocatedLot(lot.maLo(), allocateDisplay, allocateDisplay * heSoQuyDoi));
            remainingDisplay -= allocateDisplay;
        }
        if (remainingDisplay > 0) {
            throw new BusinessException("Khong the phan bo du lo hang doi cho thuoc " + maThuoc + ".");
        }
        return allocations;
    }

    private void validateActor(UserContext actor) {
        if (actor == null || isBlank(actor.getEmployeeId())) {
            throw new BusinessException("UserContext khong hop le de thuc hien doi/tra.");
        }
    }

    private void validateCreatePhieuTraRequest(CreatePhieuTraRequest request) {
        if (request == null) {
            throw new BusinessException("CreatePhieuTraRequest khong duoc null.");
        }
        if (isBlank(request.getMaHoaDonGoc()) || isBlank(request.getMaKhachHang())) {
            throw new BusinessException("Phieu tra bat buoc phai co ma hoa don goc va ma khach hang.");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BusinessException("Phieu tra phai co it nhat mot dong chi tiet.");
        }
        for (CreatePhieuTraItemRequest item : request.getItems()) {
            if (item == null || isBlank(item.getMaLoHang()) || isBlank(item.getMaThuoc()) || isBlank(item.getMaDonViTinh())) {
                throw new BusinessException("Chi tiet phieu tra thieu ma lo, ma thuoc hoac ma don vi tinh.");
            }
            if (item.getSoLuong() <= 0) {
                throw new BusinessException("So luong tra phai lon hon 0.");
            }
            if (isBlank(item.getLyDoTra())) {
                throw new BusinessException("Ly do tra khong duoc de trong.");
            }
        }
    }

    private void validateCreatePhieuDoiRequest(CreatePhieuDoiRequest request) {
        if (request == null) {
            throw new BusinessException("CreatePhieuDoiRequest khong duoc null.");
        }
        if (isBlank(request.getMaHoaDonGoc()) || isBlank(request.getMaKhachHang())) {
            throw new BusinessException("Phieu doi bat buoc phai co ma hoa don goc va ma khach hang.");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BusinessException("Phieu doi phai co it nhat mot dong chi tiet.");
        }
        for (CreatePhieuDoiItemRequest item : request.getItems()) {
            if (item == null || isBlank(item.getMaLoHang()) || isBlank(item.getMaThuoc()) || isBlank(item.getMaDonViTinh())) {
                throw new BusinessException("Chi tiet phieu doi thieu ma lo, ma thuoc hoac ma don vi tinh.");
            }
            if (item.getSoLuong() <= 0) {
                throw new BusinessException("So luong doi phai lon hon 0.");
            }
            if (isBlank(item.getLyDoDoi())) {
                throw new BusinessException("Ly do doi khong duoc de trong.");
            }
        }
    }

    private int normalizeConversionFactor(double value) {
        return Math.max(1, (int) Math.round(value));
    }

    private String safeTenThuoc(ChiTietHoaDonDto detail) {
        return detail != null && detail.getLoHang() != null && detail.getLoHang().getThuoc() != null
                ? Objects.toString(detail.getLoHang().getThuoc().getTenThuoc(), "")
                : "";
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private record AllocatedLot(String maLo, int soLuongDisplay, int soLuongBase) {
    }
}

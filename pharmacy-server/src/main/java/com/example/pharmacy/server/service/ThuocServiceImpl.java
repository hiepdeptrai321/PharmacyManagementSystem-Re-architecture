package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.repository.MedicineCatalogRepository;
import com.example.pharmacy.server.transaction.TransactionManager;
import com.example.pharmacy.common.model.ChiTietHoatChat;
import com.example.pharmacy.common.model.HoatChat;
import com.example.pharmacy.common.model.LoaiHang;
import com.example.pharmacy.common.model.ThuocTonKho;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLo;
import com.example.pharmacy.common.model.Thuoc_SanPham;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ThuocServiceImpl implements ThuocService {
    private final TransactionManager transactionManager;
    private final MedicineCatalogRepository medicineCatalogRepository;
    private final CodeGenerationService codeGenerationService;

    public ThuocServiceImpl(
            TransactionManager transactionManager,
            MedicineCatalogRepository medicineCatalogRepository,
            CodeGenerationService codeGenerationService
    ) {
        this.transactionManager = Objects.requireNonNull(transactionManager, "transactionManager must not be null");
        this.medicineCatalogRepository = Objects.requireNonNull(medicineCatalogRepository, "medicineCatalogRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public List<Thuoc_SanPham> findAll() {
        return medicineCatalogRepository.findAllMedicines();
    }

    @Override
    public String generateNewMaThuoc() {
        return codeGenerationService.nextCode(BusinessCodeType.THUOC);
    }

    @Override
    public List<LoaiHang> findAllLoaiHang() {
        return medicineCatalogRepository.findAllLoaiHang();
    }

    @Override
    public List<String> findAllLoaiHangNames() {
        return medicineCatalogRepository.findAllLoaiHangNames();
    }

    @Override
    public List<HoatChat> findAllHoatChat() {
        return medicineCatalogRepository.findAllHoatChat();
    }

    @Override
    public List<ChiTietHoatChat> findChiTietHoatChatByMaThuoc(String maThuoc) {
        return medicineCatalogRepository.findChiTietHoatChatByMaThuoc(maThuoc);
    }

    @Override
    public boolean create(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats, String maDonViTinhCoBan) {
        if (thuoc == null) {
            return false;
        }
        if (thuoc.getMaThuoc() == null || thuoc.getMaThuoc().isBlank()) {
            thuoc.setMaThuoc(generateNewMaThuoc());
        }
        try {
            return transactionManager.execute(() -> {
                if (!medicineCatalogRepository.insertMedicine(thuoc)) {
                    return false;
                }

                if (chiTietHoatChats != null) {
                    for (ChiTietHoatChat chiTietHoatChat : chiTietHoatChats) {
                        if (chiTietHoatChat == null || chiTietHoatChat.getHoatChat() == null) {
                            continue;
                        }
                        chiTietHoatChat.setThuoc(thuoc);
                        medicineCatalogRepository.insertChiTietHoatChat(thuoc.getMaThuoc(), chiTietHoatChat);
                    }
                }

                if (maDonViTinhCoBan != null && !maDonViTinhCoBan.isBlank()) {
                    medicineCatalogRepository.insertBaseUnit(thuoc.getMaThuoc(), maDonViTinhCoBan);
                }
                return true;
            });
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean update(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats) {
        if (thuoc == null || thuoc.getMaThuoc() == null || thuoc.getMaThuoc().isBlank()) {
            return false;
        }

        try {
            return transactionManager.execute(() -> {
                if (!medicineCatalogRepository.updateMedicine(thuoc)) {
                    return false;
                }

                List<ChiTietHoatChat> existing = medicineCatalogRepository.findChiTietHoatChatByMaThuoc(thuoc.getMaThuoc());
                Set<String> currentKeys = new HashSet<>();

                if (chiTietHoatChats != null) {
                    for (ChiTietHoatChat chiTietHoatChat : chiTietHoatChats) {
                        if (chiTietHoatChat == null || chiTietHoatChat.getHoatChat() == null) {
                            continue;
                        }
                        chiTietHoatChat.setThuoc(thuoc);
                        String maHoatChat = chiTietHoatChat.getHoatChat().getMaHoatChat();
                        currentKeys.add(maHoatChat);

                        ChiTietHoatChat existingChiTiet = existing.stream()
                                .filter(item -> item.getHoatChat() != null && Objects.equals(item.getHoatChat().getMaHoatChat(), maHoatChat))
                                .findFirst()
                                .orElse(null);

                        if (existingChiTiet == null) {
                            medicineCatalogRepository.insertChiTietHoatChat(thuoc.getMaThuoc(), chiTietHoatChat);
                        } else if (Float.compare(existingChiTiet.getHamLuong(), chiTietHoatChat.getHamLuong()) != 0) {
                            medicineCatalogRepository.updateChiTietHoatChat(thuoc.getMaThuoc(), chiTietHoatChat);
                        }
                    }
                }

                for (ChiTietHoatChat oldChiTiet : existing) {
                    if (oldChiTiet.getHoatChat() == null) {
                        continue;
                    }
                    String maHoatChat = oldChiTiet.getHoatChat().getMaHoatChat();
                    if (!currentKeys.contains(maHoatChat)) {
                        medicineCatalogRepository.deleteChiTietHoatChat(thuoc.getMaThuoc(), maHoatChat);
                    }
                }

                return true;
            });
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean softDelete(String maThuoc) {
        if (maThuoc == null || maThuoc.isBlank()) {
            return false;
        }
        try {
            return transactionManager.execute(() -> medicineCatalogRepository.softDeleteMedicine(maThuoc));
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public int getTongSoLuongTonByMaThuoc(String maThuoc) {
        return medicineCatalogRepository.getTongSoLuongTonByMaThuoc(maThuoc);
    }

    @Override
    public String getTenDonViTinhCoBan(String maThuoc) {
        return medicineCatalogRepository.getTenDonViTinhCoBan(maThuoc);
    }

    @Override
    public List<ThuocTonKho> getThuocTonKho() {
        return medicineCatalogRepository.getThuocTonKho();
    }

    @Override
    public List<Thuoc_SP_TheoLo> getAllTheoLo() {
        List<Thuoc_SP_TheoLo> rawLots = medicineCatalogRepository.findAllLots();
        List<Thuoc_SP_TheoLo> safeLots = new ArrayList<>();
        for (Thuoc_SP_TheoLo rawLot : rawLots) {
            Thuoc_SP_TheoLo safeLot = new Thuoc_SP_TheoLo();
            safeLot.setMaLH(rawLot.getMaLH());
            safeLot.setSoLuongTon(rawLot.getSoLuongTon());
            safeLot.setNsx(rawLot.getNsx());
            safeLot.setHsd(rawLot.getHsd());
            safeLot.setPhieuNhap(null);
            safeLot.setThuoc(rawLot.getThuoc());
            safeLots.add(safeLot);
        }
        return safeLots;
    }
}

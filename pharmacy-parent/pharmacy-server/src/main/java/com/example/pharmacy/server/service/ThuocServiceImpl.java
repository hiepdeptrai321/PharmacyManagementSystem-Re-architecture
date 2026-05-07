package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacymanagementsystem_qlht.dao.ChiTietDonViTinh_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.ChiTietHoatChat_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.DonViTinh_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.HoatChat_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.LoaiHang_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SP_TheoLo_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SanPham_Dao;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietDonViTinh;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;
import com.example.pharmacymanagementsystem_qlht.model.DonViTinh;
import com.example.pharmacymanagementsystem_qlht.model.HoatChat;
import com.example.pharmacymanagementsystem_qlht.model.LoaiHang;
import com.example.pharmacymanagementsystem_qlht.model.ThuocTonKho;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ThuocServiceImpl implements ThuocService {
    private final Thuoc_SanPham_Dao thuocDao = new Thuoc_SanPham_Dao();
    private final ChiTietHoatChat_Dao chiTietHoatChatDao = new ChiTietHoatChat_Dao();
    private final HoatChat_Dao hoatChatDao = new HoatChat_Dao();
    private final LoaiHang_Dao loaiHangDao = new LoaiHang_Dao();
    private final DonViTinh_Dao donViTinhDao = new DonViTinh_Dao();
    private final ChiTietDonViTinh_Dao chiTietDonViTinhDao = new ChiTietDonViTinh_Dao();
    private final Thuoc_SP_TheoLo_Dao thuocTheoLoDao = new Thuoc_SP_TheoLo_Dao();
    private final CodeGenerationService codeGenerationService;

    public ThuocServiceImpl(CodeGenerationService codeGenerationService) {
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public List<Thuoc_SanPham> findAll() {
        return thuocDao.selectAll();
    }

    @Override
    public String generateNewMaThuoc() {
        return codeGenerationService.nextCode(BusinessCodeType.THUOC);
    }

    @Override
    public List<LoaiHang> findAllLoaiHang() {
        return loaiHangDao.selectAll();
    }

    @Override
    public List<String> findAllLoaiHangNames() {
        return thuocDao.getAllLoaiHang();
    }

    @Override
    public List<HoatChat> findAllHoatChat() {
        return hoatChatDao.selectAll();
    }

    @Override
    public List<ChiTietHoatChat> findChiTietHoatChatByMaThuoc(String maThuoc) {
        return chiTietHoatChatDao.selectByMaThuoc(maThuoc);
    }

    @Override
    public boolean create(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats, String maDonViTinhCoBan) {
        if (thuoc == null) {
            return false;
        }
        if (thuoc.getMaThuoc() == null || thuoc.getMaThuoc().isBlank()) {
            thuoc.setMaThuoc(generateNewMaThuoc());
        }
        if (!thuocDao.insert(thuoc)) {
            return false;
        }

        if (chiTietHoatChats != null) {
            for (ChiTietHoatChat chiTietHoatChat : chiTietHoatChats) {
                if (chiTietHoatChat == null || chiTietHoatChat.getHoatChat() == null) {
                    continue;
                }
                chiTietHoatChat.setThuoc(thuoc);
                chiTietHoatChatDao.insert(chiTietHoatChat);
            }
        }

        if (maDonViTinhCoBan != null && !maDonViTinhCoBan.isBlank()) {
            DonViTinh donViTinh = donViTinhDao.selectById(maDonViTinhCoBan);
            if (donViTinh != null) {
                ChiTietDonViTinh chiTietDonViTinh = new ChiTietDonViTinh();
                chiTietDonViTinh.setThuoc(thuoc);
                chiTietDonViTinh.setDvt(donViTinh);
                chiTietDonViTinh.setHeSoQuyDoi(1.0);
                chiTietDonViTinh.setGiaNhap(0.0);
                chiTietDonViTinh.setGiaBan(0.0);
                chiTietDonViTinh.setDonViCoBan(true);
                chiTietDonViTinhDao.insert(chiTietDonViTinh);
            }
        }

        return true;
    }

    @Override
    public boolean update(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats) {
        if (thuoc == null || thuoc.getMaThuoc() == null || thuoc.getMaThuoc().isBlank()) {
            return false;
        }
        if (!thuocDao.update(thuoc)) {
            return false;
        }

        List<ChiTietHoatChat> existing = chiTietHoatChatDao.selectByMaThuoc(thuoc.getMaThuoc());
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
                    chiTietHoatChatDao.insert(chiTietHoatChat);
                } else if (Float.compare(existingChiTiet.getHamLuong(), chiTietHoatChat.getHamLuong()) != 0) {
                    chiTietHoatChatDao.update(chiTietHoatChat);
                }
            }
        }

        for (ChiTietHoatChat oldChiTiet : existing) {
            if (oldChiTiet.getHoatChat() == null) {
                continue;
            }
            String maHoatChat = oldChiTiet.getHoatChat().getMaHoatChat();
            if (!currentKeys.contains(maHoatChat)) {
                chiTietHoatChatDao.deleteById(thuoc.getMaThuoc(), maHoatChat);
            }
        }

        return true;
    }

    @Override
    public boolean softDelete(String maThuoc) {
        if (maThuoc == null || maThuoc.isBlank()) {
            return false;
        }
        return thuocDao.xoaThuoc_SanPham(maThuoc);
    }

    @Override
    public int getTongSoLuongTonByMaThuoc(String maThuoc) {
        return thuocTheoLoDao.selectSoLuongTonByMaThuoc(maThuoc);
    }

    @Override
    public String getTenDonViTinhCoBan(String maThuoc) {
        return thuocDao.getTenDVTByMaThuoc(maThuoc);
    }

    @Override
    public List<ThuocTonKho> getThuocTonKho() {
        return thuocTheoLoDao.getThuocTonKho();
    }

    @Override
    public List<Thuoc_SP_TheoLo> getAllTheoLo() {
        List<Thuoc_SP_TheoLo> rawLots = thuocTheoLoDao.selectAll();
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

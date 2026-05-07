package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacymanagementsystem_qlht.dao.ChiTietKhuyenMai_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.KhuyenMai_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SP_TangKem_Dao;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TangKem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DichVuKhuyenMai {
    private final ChiTietKhuyenMai_Dao ctDao = new ChiTietKhuyenMai_Dao();
    private final KhuyenMai_Dao kmDao = new KhuyenMai_Dao();
    private final Thuoc_SP_TangKem_Dao giftDao = new Thuoc_SP_TangKem_Dao();

    public ApDungKhuyenMai apDungChoSP(String maThuoc, int soLuong, BigDecimal donGia, LocalDate ngay) {
        ApDungKhuyenMai kq = new ApDungKhuyenMai();
        if (soLuong <= 0) return kq;

        List<ChiTietKhuyenMai> cts = ctDao.selectByMaThuoc(maThuoc);
        for (ChiTietKhuyenMai ct : cts) {
            KhuyenMai km = ct.getKhuyenMai();
            if (km == null || km.getLoaiKM() == null) continue;
            if (km.getNgayBatDau() == null || km.getNgayKetThuc() == null) continue;

            LocalDate nbd = km.getNgayBatDau().toLocalDate();
            LocalDate nkt = km.getNgayKetThuc().toLocalDate();
            if (ngay.isBefore(nbd) || ngay.isAfter(nkt)) continue;

            int slApDung = Math.max(1, ct.getSlApDung());

            int times = soLuong / slApDung;
            if (times <= 0) continue;


            String loai = km.getLoaiKM().getMaLoai(); // LKM001/LKM002/LKM003
            if ("LKM003".equalsIgnoreCase(loai)) {
                // Giảm % trên số lượng được áp
                BigDecimal percent = BigDecimal.valueOf(km.getGiaTriKM()).divide(BigDecimal.valueOf(100));
                BigDecimal giam = donGia
                        .multiply(BigDecimal.valueOf(slApDung))
                        .multiply(BigDecimal.valueOf(times))
                        .multiply(percent);
                kq.addDiscount(giam);
                kq.addApplied(km.getMaKM());
            } else if ("LKM002".equalsIgnoreCase(loai)) {
                // Giảm tiền trực tiếp theo lần áp
                BigDecimal giam = BigDecimal.valueOf(km.getGiaTriKM())
                        .multiply(BigDecimal.valueOf(times));
                kq.addDiscount(giam);
                kq.addApplied(km.getMaKM());
            } else  if ("LKM001".equalsIgnoreCase(loai)) {
                int ap = Math.max(0, ct.getSlApDung());
                if (ap <= 0) continue;

                int bundles = soLuong / ap;
                if (bundles <= 0) continue;

//                int maxBundles = ct.getSoHDToiDa() > 0 ? ct.getSoHDToiDa() : Integer.MAX_VALUE;
//                bundles = Math.min(bundles, maxBundles);

                List<Thuoc_SP_TangKem> gifts = giftDao.selectByMaKM(km.getMaKM());
                if (gifts != null) {
                    for (Thuoc_SP_TangKem g : gifts) {
                        if (g == null || g.getThuocTangKem() == null) continue;
                        int qty = Math.max(0, g.getSoLuong()) * bundles;
                        if (qty > 0) {
                            kq.addFreeItem(g.getThuocTangKem().getMaThuoc(), qty);
                        }
                    }
                }

                if (kq.getFreeItems().isEmpty()) {
                    String maTang = parseMaThuocTang(km.getMoTa());
                    if (maTang != null) kq.addFreeItem(maTang, bundles);
                }
                if (!kq.getFreeItems().isEmpty()) kq.addApplied(km.getMaKM());
            }
        }
        return kq;
    }
    public double tinhGiamGiaSanPham(ChiTietHoaDon row,
                                     ChiTietKhuyenMai ctKm,
                                     KhuyenMai km,
                                     double heSoToBase) {

        if (row == null || ctKm == null || km == null || km.getLoaiKM() == null)
            return 0d;

        String loaiKM = km.getLoaiKM().getMaLoai(); // LKM002 / LKM003

        // so luong
        int soLuongDisplay = Math.max(0, row.getSoLuong());

        int soLuongBase = (int) Math.floor(
                soLuongDisplay * Math.max(1d, heSoToBase)
        );
        int slApDung = Math.max(1, ctKm.getSlApDung());

        int times = soLuongBase / slApDung;
        if (times <= 0) return 0d;
        // don gia
        double donGia = Math.max(0d, row.getDonGia());

        // số lượng HIỂN THỊ tương ứng cho 1 lần áp KM
        int soLuongDisplayPerTime = (int) Math.ceil(
                slApDung / Math.max(1d, heSoToBase)
        );

        // giam
        // co dinh
        if ("LKM002".equalsIgnoreCase(loaiKM)) {
            return times * km.getGiaTriKM();
        }

        // giam %
        if ("LKM003".equalsIgnoreCase(loaiKM)) {
            double tienApDung =
                    soLuongDisplayPerTime * donGia * times;

            return tienApDung * (km.getGiaTriKM() / 100.0);
        }

        return 0d;
    }


    public ApDungKhuyenMai apDungChoHoaDon(BigDecimal baseSauKMHang, LocalDate ngay) {
        ApDungKhuyenMai kq = new ApDungKhuyenMai();
        if (baseSauKMHang == null || baseSauKMHang.signum() <= 0) return kq;

        BigDecimal base = baseSauKMHang;

        List<KhuyenMai> ds = kmDao.selectActiveInvoiceOn(Date.valueOf(ngay));
        // Best LKM004 by highest threshold <= base
        KhuyenMai best004 = ds.stream()
                .filter(km -> km.getLoaiKM() != null && "LKM004".equalsIgnoreCase(km.getLoaiKM().getMaLoai()))
                .filter(km -> km.getGiaTriApDung() <= base.doubleValue())
                .max(Comparator.comparingDouble(KhuyenMai::getGiaTriApDung))
                .orElse(null);

        // Best LKM005 by highest threshold <= base
        KhuyenMai best005 = ds.stream()
                .filter(km -> km.getLoaiKM() != null && "LKM005".equalsIgnoreCase(km.getLoaiKM().getMaLoai()))
                .filter(km -> km.getGiaTriApDung() <= base.doubleValue())
                .max(Comparator.comparingDouble(KhuyenMai::getGiaTriApDung))
                .orElse(null);

        BigDecimal g004 = BigDecimal.ZERO;
        BigDecimal g005 = BigDecimal.ZERO;

        if (best004 != null) {
            g004 = BigDecimal.valueOf(best004.getGiaTriKM());
            // cap discount not to exceed base
            if (g004.compareTo(base) > 0) g004 = base;
            g004 = g004.setScale(0, RoundingMode.HALF_UP);
        }
        if (best005 != null) {
            BigDecimal percent = BigDecimal.valueOf(best005.getGiaTriKM() / 100.0);
            g005 = base.multiply(percent).setScale(0, RoundingMode.HALF_UP);
        }

        // Pick the larger discount
        if (g004.compareTo(g005) >= 0) {
            if (best004 != null && g004.signum() > 0) {
                kq.addDiscount(g004);
                kq.addApplied(best004.getMaKM());
            }
        } else {
            if (best005 != null && g005.signum() > 0) {
                kq.addDiscount(g005);
                kq.addApplied(best005.getMaKM());
            }
        }
        return kq;
    }
    private String parseMaThuocTang(String moTa) {
        if (moTa == null) return null;
        String[] keys = { "MaThuocTang", "Tang", "FREE" };
        for (String key : keys) {
            Pattern p = Pattern.compile("(?i)\\b" + key + "\\s*=\\s*([A-Za-z0-9_\\-]+)");
            Matcher m = p.matcher(moTa);
            if (m.find()) return m.group(1);
        }
        return null;
    }
}


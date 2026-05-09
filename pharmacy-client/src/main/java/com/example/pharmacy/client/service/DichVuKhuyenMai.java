package com.example.pharmacy.client.service;

import com.example.pharmacy.common.model.ChiTietKhuyenMaiDto;
import com.example.pharmacy.common.model.KhuyenMaiDto;
import com.example.pharmacy.common.model.Thuoc_SP_TangKemDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DichVuKhuyenMai {
    private final KhuyenMaiService khuyenMaiService = new KhuyenMaiService();

    public ApDungKhuyenMai apDungChoSP(String maThuoc, int soLuongBase, BigDecimal donGia, LocalDate ngay) {
        ApDungKhuyenMai ketQua = new ApDungKhuyenMai();
        if (maThuoc == null || maThuoc.isBlank() || soLuongBase <= 0) {
            return ketQua;
        }

        List<KhuyenMaiDto> dsKhuyenMai = khuyenMaiService.findActiveOn(Date.valueOf(ngay));
        for (KhuyenMaiDto khuyenMai : dsKhuyenMai) {
            if (khuyenMai == null || khuyenMai.getLoaiKM() == null) {
                continue;
            }
            List<ChiTietKhuyenMaiDto> chiTiet = khuyenMaiService.findChiTietByMaKM(khuyenMai.getMaKM()).stream()
                    .filter(ct -> ct.getThuoc() != null && maThuoc.equalsIgnoreCase(ct.getThuoc().getMaThuoc()))
                    .toList();

            for (ChiTietKhuyenMaiDto ct : chiTiet) {
                int soLuongApDung = Math.max(1, ct.getSlApDung());
                int times = soLuongBase / soLuongApDung;
                if (times <= 0) {
                    continue;
                }
                String maLoai = khuyenMai.getLoaiKM().getMaLoai();
                if ("LKM003".equalsIgnoreCase(maLoai)) {
                    BigDecimal percent = BigDecimal.valueOf(khuyenMai.getGiaTriKM()).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                    BigDecimal giam = donGia
                            .multiply(BigDecimal.valueOf(soLuongApDung))
                            .multiply(BigDecimal.valueOf(times))
                            .multiply(percent);
                    ketQua.addDiscount(giam.setScale(2, RoundingMode.HALF_UP));
                    ketQua.addApplied(khuyenMai.getMaKM());
                } else if ("LKM002".equalsIgnoreCase(maLoai)) {
                    ketQua.addDiscount(BigDecimal.valueOf(khuyenMai.getGiaTriKM()).multiply(BigDecimal.valueOf(times)));
                    ketQua.addApplied(khuyenMai.getMaKM());
                } else if ("LKM001".equalsIgnoreCase(maLoai)) {
                    List<Thuoc_SP_TangKemDto> gifts = khuyenMaiService.findQuaTangByMaKM(khuyenMai.getMaKM());
                    for (Thuoc_SP_TangKemDto gift : gifts) {
                        if (gift.getThuocTangKem() == null) {
                            continue;
                        }
                        int qty = Math.max(0, gift.getSoLuong()) * times;
                        if (qty > 0) {
                            ketQua.addFreeItem(gift.getThuocTangKem().getMaThuoc(), qty);
                        }
                    }
                    if (ketQua.getFreeItems().isEmpty()) {
                        String maThuocTang = parseMaThuocTang(khuyenMai.getMoTa());
                        if (maThuocTang != null) {
                            ketQua.addFreeItem(maThuocTang, times);
                        }
                    }
                    if (!ketQua.getFreeItems().isEmpty()) {
                        ketQua.addApplied(khuyenMai.getMaKM());
                    }
                }
            }
        }
        return ketQua;
    }

    public ApDungKhuyenMai apDungChoHoaDon(BigDecimal baseSauKhuyenMaiSanPham, LocalDate ngay) {
        ApDungKhuyenMai ketQua = new ApDungKhuyenMai();
        if (baseSauKhuyenMaiSanPham == null || baseSauKhuyenMaiSanPham.signum() <= 0) {
            return ketQua;
        }

        List<KhuyenMaiDto> dsKhuyenMai = khuyenMaiService.findActiveInvoiceOn(Date.valueOf(ngay));
        KhuyenMaiDto bestLkm004 = dsKhuyenMai.stream()
                .filter(km -> km.getLoaiKM() != null && "LKM004".equalsIgnoreCase(km.getLoaiKM().getMaLoai()))
                .filter(km -> km.getGiaTriApDung() <= baseSauKhuyenMaiSanPham.doubleValue())
                .max(Comparator.comparingDouble(KhuyenMaiDto::getGiaTriApDung))
                .orElse(null);

        KhuyenMaiDto bestLkm005 = dsKhuyenMai.stream()
                .filter(km -> km.getLoaiKM() != null && "LKM005".equalsIgnoreCase(km.getLoaiKM().getMaLoai()))
                .filter(km -> km.getGiaTriApDung() <= baseSauKhuyenMaiSanPham.doubleValue())
                .max(Comparator.comparingDouble(KhuyenMaiDto::getGiaTriApDung))
                .orElse(null);

        BigDecimal giamCoDinh = BigDecimal.ZERO;
        BigDecimal giamPhanTram = BigDecimal.ZERO;

        if (bestLkm004 != null) {
            giamCoDinh = BigDecimal.valueOf(bestLkm004.getGiaTriKM());
            if (giamCoDinh.compareTo(baseSauKhuyenMaiSanPham) > 0) {
                giamCoDinh = baseSauKhuyenMaiSanPham;
            }
            giamCoDinh = giamCoDinh.setScale(0, RoundingMode.HALF_UP);
        }

        if (bestLkm005 != null) {
            giamPhanTram = baseSauKhuyenMaiSanPham
                    .multiply(BigDecimal.valueOf(bestLkm005.getGiaTriKM() / 100.0))
                    .setScale(0, RoundingMode.HALF_UP);
        }

        if (giamCoDinh.compareTo(giamPhanTram) >= 0) {
            if (bestLkm004 != null && giamCoDinh.signum() > 0) {
                ketQua.addDiscount(giamCoDinh);
                ketQua.addApplied(bestLkm004.getMaKM());
            }
        } else if (bestLkm005 != null && giamPhanTram.signum() > 0) {
            ketQua.addDiscount(giamPhanTram);
            ketQua.addApplied(bestLkm005.getMaKM());
        }
        return ketQua;
    }

    private String parseMaThuocTang(String moTa) {
        if (moTa == null) {
            return null;
        }
        String[] keys = {"MaThuocTang", "Tang", "FREE"};
        for (String key : keys) {
            Pattern pattern = Pattern.compile("(?i)\\b" + key + "\\s*=\\s*([A-Za-z0-9_\\-]+)");
            Matcher matcher = pattern.matcher(moTa);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }
}

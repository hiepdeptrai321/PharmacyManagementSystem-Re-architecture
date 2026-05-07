package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeTopSanPham;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThongKeTopSP_Dao {

    // Hàm chung thực thi query và map dữ liệu
    private List<ThongKeTopSanPham> executeQuery(String sql, LocalDate from, LocalDate to, int top) {
        List<ThongKeTopSanPham> list = new ArrayList<>();
        try (Connection conn = ConnectDB.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, top);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));

            try (ResultSet rs = ps.executeQuery()) {
                int i = 1;
                while (rs.next()) {
                    ThongKeTopSanPham sp = new ThongKeTopSanPham();
                    sp.setStt(i++);
                    sp.setMaThuoc(rs.getString("MaThuoc"));
                    sp.setTenThuoc(rs.getString("TenThuoc"));
                    sp.setDonViTinh(rs.getString("DVT_CoBan"));
                    sp.setSoLuong(rs.getInt("TongSoLuong")); // Số lượng đã quy đổi ra đơn vị cơ bản
                    sp.setTongTien(rs.getDouble("TongDoanhThu"));
                    list.add(sp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy Top bán chạy (Theo số lượng quy đổi)
    public List<ThongKeTopSanPham> getTopBanChay(LocalDate from, LocalDate to, int top) {
        String sql = "SELECT TOP (?) " +
                "   T.MaThuoc, " +
                "   T.TenThuoc, " +
                "   ISNULL(DVT_Chuan.TenDonViTinh, N'Chưa cập nhật') AS DVT_CoBan, " +
                // LOGIC CHÍNH: Nhân số lượng bán với Hệ Số Quy Đổi của đơn vị lúc bán
                "   SUM(CTHD.SoLuong * ISNULL(CTDVT_Ban.HeSoQuyDoi, 1)) AS TongSoLuong, " +
                "   SUM(CTHD.SoLuong * (CTHD.DonGia - CTHD.GiamGia)) AS TongDoanhThu " +
                "FROM ChiTietHoaDon CTHD " +
                "JOIN HoaDon HD ON CTHD.MaHD = HD.MaHD " +
                "JOIN Thuoc_SP_TheoLo L ON CTHD.MaLH = L.MaLH " +
                "JOIN Thuoc_SanPham T ON L.MaThuoc = T.MaThuoc " +
                // Join 1: Lấy tên Đơn vị tính CƠ BẢN (để hiển thị ra bảng là Viên/Hộp...)
                "LEFT JOIN ChiTietDonViTinh CTDVT_Chuan ON T.MaThuoc = CTDVT_Chuan.MaThuoc AND CTDVT_Chuan.DonViCoBan = 1 " +
                "LEFT JOIN DonViTinh DVT_Chuan ON CTDVT_Chuan.MaDVT = DVT_Chuan.MaDVT " +
                // Join 2: Lấy Hệ Số Quy Đổi của Đơn vị ĐÃ BÁN (để tính toán)
                "LEFT JOIN ChiTietDonViTinh CTDVT_Ban ON T.MaThuoc = CTDVT_Ban.MaThuoc AND CTHD.MaDVT = CTDVT_Ban.MaDVT " +
                "WHERE CAST(HD.NgayLap AS DATE) BETWEEN ? AND ? " +
                "GROUP BY T.MaThuoc, T.TenThuoc, DVT_Chuan.TenDonViTinh " +
                "ORDER BY TongSoLuong DESC";
        return executeQuery(sql, from, to, top);
    }

    // Lấy Top doanh thu (Vẫn cần quy đổi số lượng để hiển thị đúng logic)
    public List<ThongKeTopSanPham> getTopDoanhThu(LocalDate from, LocalDate to, int top) {
        String sql = "SELECT TOP (?) " +
                "   T.MaThuoc, " +
                "   T.TenThuoc, " +
                "   ISNULL(DVT_Chuan.TenDonViTinh, N'Chưa cập nhật') AS DVT_CoBan, " +
                "   SUM(CTHD.SoLuong * ISNULL(CTDVT_Ban.HeSoQuyDoi, 1)) AS TongSoLuong, " +
                "   SUM(CTHD.SoLuong * (CTHD.DonGia - CTHD.GiamGia)) AS TongDoanhThu " +
                "FROM ChiTietHoaDon CTHD " +
                "JOIN HoaDon HD ON CTHD.MaHD = HD.MaHD " +
                "JOIN Thuoc_SP_TheoLo L ON CTHD.MaLH = L.MaLH " +
                "JOIN Thuoc_SanPham T ON L.MaThuoc = T.MaThuoc " +
                "LEFT JOIN ChiTietDonViTinh CTDVT_Chuan ON T.MaThuoc = CTDVT_Chuan.MaThuoc AND CTDVT_Chuan.DonViCoBan = 1 " +
                "LEFT JOIN DonViTinh DVT_Chuan ON CTDVT_Chuan.MaDVT = DVT_Chuan.MaDVT " +
                "LEFT JOIN ChiTietDonViTinh CTDVT_Ban ON T.MaThuoc = CTDVT_Ban.MaThuoc AND CTHD.MaDVT = CTDVT_Ban.MaDVT " +
                "WHERE CAST(HD.NgayLap AS DATE) BETWEEN ? AND ? " +
                "GROUP BY T.MaThuoc, T.TenThuoc, DVT_Chuan.TenDonViTinh " +
                "ORDER BY TongDoanhThu DESC";
        return executeQuery(sql, from, to, top);
    }
}
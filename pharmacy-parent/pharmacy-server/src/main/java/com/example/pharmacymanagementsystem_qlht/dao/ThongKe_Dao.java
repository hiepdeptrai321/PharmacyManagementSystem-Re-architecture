package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeBanHang;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeSanPham;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.example.pharmacymanagementsystem_qlht.model.HoaDonDisplay; // <-- THÊM IMPORT NÀY
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class ThongKe_Dao {


    public List<ThongKeBanHang> getThongKeBanHang(String thoiGian) {
        List<ThongKeBanHang> list = new ArrayList<>();
        String sql; // Tên Stored Procedure

        // Chọn Stored Procedure dựa trên đầu vào
        switch (thoiGian) {
            case "Hôm nay":
                sql = "{call sp_ThongKeBanHang_HomNay}";
                break;
            case "Tuần này":
                sql = "{call sp_ThongKeBanHang_TuanNay}";
                break;
            case "Tháng này":
                sql = "{call sp_ThongKeBanHang_ThangNay}";
                break;
            case "Năm Nay":
                sql = "{call sp_ThongKeBanHang_NamNay}";
                break;
            default:
                sql = "{call sp_ThongKeBanHang_HomNay}"; // Mặc định
        }

        try (ResultSet rs = ConnectDB.query(sql)) {
            while (rs.next()) {
                ThongKeBanHang tk = new ThongKeBanHang(
                        rs.getString("ThoiGian"),
                        rs.getInt("SoLuongHoaDon"),
                        rs.getDouble("TongGiaTri"),
                        rs.getDouble("GiamGia"),
                        rs.getInt("SoLuongDonTra"),
                        rs.getDouble("GiaTriDonTra"),
                        rs.getDouble("DoanhThu")
                );
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    public List<ThongKeBanHang> getThongKeBanHang_TuyChon(LocalDate tuNgay, LocalDate denNgay) {
        List<ThongKeBanHang> list = new ArrayList<>();
        String sql = "{call sp_ThongKeBanHang_TuyChon(?, ?)}";

        Date sqlTuNgay = Date.valueOf(tuNgay);
        Date sqlDenNgay = Date.valueOf(denNgay);

        try (ResultSet rs = ConnectDB.query(sql, sqlTuNgay, sqlDenNgay)) {
            while (rs.next()) {
                ThongKeBanHang tk = new ThongKeBanHang(
                        rs.getString("ThoiGian"), rs.getInt("SoLuongHoaDon"),
                        rs.getDouble("TongGiaTri"), rs.getDouble("GiamGia"),
                        rs.getInt("SoLuongDonTra"), rs.getDouble("GiaTriDonTra"),
                        rs.getDouble("DoanhThu")
                );
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<HoaDonDisplay> getHoaDonTheoThoiGian(String thoiGian) {
        ObservableList<HoaDonDisplay> list = FXCollections.observableArrayList();
        String sql = "{CALL sp_GetHoaDonTheoThoiGian(?)}";

        try (Connection conn = ConnectDB.getInstance();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, thoiGian);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String maHD = rs.getString("maHD");
                    LocalDate ngayLap = rs.getDate("ngayLap").toLocalDate();
                    String maKH = rs.getString("maKH");
                    String maNV = rs.getString("maNV");
                    double tongTien = rs.getDouble("tongTienNet");

                    list.add(new HoaDonDisplay(maHD, ngayLap, maKH, maNV, tongTien));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- HÀM MỚI 2 ---
    public ObservableList<HoaDonDisplay> getHoaDonTheoTuyChon(LocalDate tuNgay, LocalDate denNgay) {
        ObservableList<HoaDonDisplay> list = FXCollections.observableArrayList();
        String sql = "{CALL sp_GetHoaDonTheoTuyChon(?, ?)}";

        try (Connection conn = ConnectDB.getInstance();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setDate(1, java.sql.Date.valueOf(tuNgay));
            cs.setDate(2, java.sql.Date.valueOf(denNgay));

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String maHD = rs.getString("maHD");
                    LocalDate ngayLap = rs.getDate("ngayLap").toLocalDate();
                    String maKH = rs.getString("maKH");
                    String maNV = rs.getString("maNV");
                    double tongTien = rs.getDouble("tongTienNet");

                    list.add(new HoaDonDisplay(maHD, ngayLap, maKH, maNV, tongTien));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

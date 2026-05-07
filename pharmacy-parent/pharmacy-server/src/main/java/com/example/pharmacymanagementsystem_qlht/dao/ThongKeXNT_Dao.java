package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeTonKho;
import com.example.pharmacymanagementsystem_qlht.model.ThuocHetHan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThongKeXNT_Dao {


    public ObservableList<ThongKeTonKho> getThongKeXNT(LocalDate tu, LocalDate den) throws SQLException {
        ObservableList<ThongKeTonKho> list = FXCollections.observableArrayList();
        String sql = "{CALL sp_ThongKeXNT(?, ?)}";
        ResultSet rs = null;

        try {

            rs = ConnectDB.query(sql, tu, den);
            while (rs.next()) {
                list.add(new ThongKeTonKho(
                        rs.getString("MaThuoc"),
                        rs.getString("TenThuoc"),
                        rs.getString("DVT"),
                        rs.getInt("TonDauKy"),
                        rs.getInt("NhapTrongKy"),
                        rs.getInt("XuatTrongKy"),
                        rs.getInt("TonCuoiKy")
                ));
            }
        } catch (Exception e) {
            throw new SQLException("Lỗi khi tải dữ liệu thống kê XNT: " + e.getMessage(), e);
        } finally {
            // Đóng kết nối
            if (rs != null) {
                rs.getStatement().getConnection().close();
            }
        }
        return list;
    }


    public ObservableList<ThuocHetHan> getThuocHetHan() throws SQLException {
        ObservableList<ThuocHetHan> list = FXCollections.observableArrayList();
        String sql = "{CALL sp_ThongKeThuocHetHan}";
        ResultSet rs = null;

        try {
            rs = ConnectDB.query(sql);
            while (rs.next()) {
                list.add(new ThuocHetHan(
                        rs.getString("maThuocHH"),
                        rs.getString("tenThuocHH"),
                        rs.getInt("soLuong"),
                        rs.getDate("ngayHetHan").toLocalDate()
                ));
            }
        } catch (Exception e) {
            throw new SQLException("Lỗi khi tải danh sách thuốc hết hạn: " + e.getMessage(), e);
        } finally {
            if (rs != null) {
                rs.getStatement().getConnection().close();
            }
        }
        return list;
    }
}
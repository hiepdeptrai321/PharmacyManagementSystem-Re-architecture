// src/main/java/com/example/pharmacymanagementsystem_qlht/dao/ChiTietDonViTinh_Dao.java
package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietDonViTinh;

import java.sql.ResultSet;
import java.util.*;

public class ChiTietDonViTinh_Dao implements DaoInterface<ChiTietDonViTinh> {
    private final String INSERT_SQL = "INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE ChiTietDonViTinh SET HeSoQuyDoi=?, GiaNhap=?, GiaBan=?, DonViCoBan=? WHERE MaThuoc=? AND MaDVT=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM ChiTietDonViTinh WHERE MaThuoc=? AND MaDVT=?";
    private final String SELECT_BY_ID_SQL = "SELECT MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan FROM ChiTietDonViTinh WHERE MaThuoc=? AND MaDVT=?";
    private final String SELECT_ALL_SQL = "SELECT Thuoc_SanPham.MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan FROM ChiTietDonViTinh" +
            " JOIN Thuoc_SanPham ON ChiTietDonViTinh.MaThuoc = Thuoc_SanPham.MaThuoc " +
            " WHERE Thuoc_SanPham.TrangThaiXoa = 0 ";
    private final String SELECT_BY_MATHUOC_SQL = "SELECT MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan FROM ChiTietDonViTinh WHERE MaThuoc=?";
    @Override
    public boolean insert(ChiTietDonViTinh e) {
        return ConnectDB.update(INSERT_SQL, e.getThuoc().getMaThuoc(), e.getDvt().getMaDVT(), e.getHeSoQuyDoi(), e.getGiaNhap(), e.getGiaBan(), e.isDonViCoBan())>0;
    }

    @Override
    public boolean update(ChiTietDonViTinh e) {
        return ConnectDB.update(UPDATE_SQL, e.getHeSoQuyDoi(), e.getGiaNhap(), e.getGiaBan(), e.isDonViCoBan(), e.getThuoc().getMaThuoc(), e.getDvt().getMaDVT())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys[0], keys[1])>0;
    }

    @Override
    public ChiTietDonViTinh selectById(Object... keys) {
        return this.selectBySql(SELECT_BY_ID_SQL, keys[0], keys[1]).get(0);
    }

    @Override
    public List<ChiTietDonViTinh> selectBySql(String sql, Object... args) {
        List<ChiTietDonViTinh> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                ChiTietDonViTinh ct = new ChiTietDonViTinh();
                ct.setThuoc(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuoc")));
                ct.setDvt(new DonViTinh_Dao().selectById(rs.getString("MaDVT")));
                ct.setHeSoQuyDoi(rs.getDouble("HeSoQuyDoi"));
                ct.setGiaNhap(rs.getDouble("GiaNhap"));
                ct.setGiaBan(rs.getDouble("GiaBan"));
                ct.setDonViCoBan(rs.getBoolean("DonViCoBan"));
                list.add(ct);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<ChiTietDonViTinh> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
    public List<ChiTietDonViTinh> selectByMaThuoc(Object... keys) {
        return selectBySql(SELECT_BY_MATHUOC_SQL, keys);
    }

}

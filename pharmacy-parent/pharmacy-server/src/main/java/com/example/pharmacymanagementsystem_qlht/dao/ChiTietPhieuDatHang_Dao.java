package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDatHang;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuDatHang_Dao implements DaoInterface<ChiTietPhieuDatHang> {
    private final String INSERT_SQL = "INSERT INTO ChiTietPhieuDatHang (MaPDat, MaThuoc, SoLuong, DonGia, GiamGia, MaDVT) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE ChiTietPhieuDatHang SET SoLuong=?, DonGia=?, GiamGia=? WHERE MaPDat=? AND MaThuoc=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM ChiTietPhieuDatHang WHERE MaPDat=? AND MaThuoc=?";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietPhieuDatHang WHERE MaPDat=? AND MaThuoc=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM ChiTietPhieuDatHang";
    private final String SELECT_BY_MAPDAT_SQL = "SELECT * FROM ChiTietPhieuDatHang WHERE MaPDat=?";

    @Override
    public boolean insert(ChiTietPhieuDatHang e) {
        return ConnectDB.update(INSERT_SQL, e.getPhieuDatHang().getMaPDat(), e.getThuoc().getMaThuoc(), e.getSoLuong(), e.getDonGia(), e.getGiamGia(), e.getDvt())>0;
    }

    @Override
    public boolean update(ChiTietPhieuDatHang e) {
        return ConnectDB.update(UPDATE_SQL, e.getSoLuong(), e.getDonGia(), e.getGiamGia(), e.getPhieuDatHang().getMaPDat(), e.getThuoc().getMaThuoc())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys[0], keys[1])>0;
    }

    @Override
    public ChiTietPhieuDatHang selectById(Object... keys) {
        List<ChiTietPhieuDatHang> list = selectBySql(SELECT_BY_ID_SQL, keys[0], keys[1]);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ChiTietPhieuDatHang> selectBySql(String sql, Object... args) {
        List<ChiTietPhieuDatHang> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                ChiTietPhieuDatHang ct = new ChiTietPhieuDatHang();
                ct.setPhieuDatHang(new PhieuDatHang_Dao().selectById(rs.getString("MaPDat")));
                ct.setThuoc(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuoc")));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGia(rs.getDouble("DonGia"));
                ct.setGiamGia(rs.getDouble("GiamGia"));
                ct.setDvt(rs.getString("MaDVT"));
                ct.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(ct);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<ChiTietPhieuDatHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public List<ChiTietPhieuDatHang> selectByMaPhieuDat(String maPD) {
        return selectBySql(SELECT_BY_MAPDAT_SQL, maPD);
    }
}

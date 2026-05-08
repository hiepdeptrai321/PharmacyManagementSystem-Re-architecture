package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietKhuyenMai;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChiTietKhuyenMai_Dao implements DaoInterface<ChiTietKhuyenMai> {
    private final String INSERT_SQL = "INSERT INTO ChiTietKhuyenMai (MaThuoc, MaKM, SLApDung, SLToiDa) VALUES (?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE ChiTietKhuyenMai SET SLApDung=?, SLToiDa=? WHERE MaThuoc=? AND MaKM=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM ChiTietKhuyenMai WHERE MaThuoc=? AND MaKM=?";
    private final String SELECT_BY_ID_SQL = "SELECT MaThuoc, MaKM, SLApDung, SLToiDa FROM ChiTietKhuyenMai WHERE MaThuoc=? AND MaKM=?";
    private final String SELECT_ALL_SQL = "SELECT MaThuoc, MaKM, SLApDung, SLToiDa FROM ChiTietKhuyenMai";
    private final String SELECT_BY_MAKM_SQL = "SELECT MaThuoc, MaKM, SLApDung, SLToiDa FROM ChiTietKhuyenMai WHERE MaKM=?";
    private final String DELETE_BY_MAKM_SQL = "DELETE FROM ChiTietKhuyenMai WHERE MaKM=?";
    private final String SELECT_BY_MATHUOC_SQL =
            "SELECT MaThuoc, MaKM, SLApDung, SLToiDa FROM ChiTietKhuyenMai WHERE MaThuoc=?";

    public List<ChiTietKhuyenMai> selectByMaThuoc(String maThuoc) {
        return selectBySql(SELECT_BY_MATHUOC_SQL, maThuoc);
    }
    @Override
    public boolean insert(ChiTietKhuyenMai e) {
        return ConnectDB.update(INSERT_SQL, e.getThuoc().getMaThuoc(), e.getKhuyenMai().getMaKM(), e.getSlApDung(), e.getSoHDToiDa())>0;
    }

    @Override
    public boolean update(ChiTietKhuyenMai e) {
        return ConnectDB.update(UPDATE_SQL, e.getSlApDung(), e.getSoHDToiDa(), e.getThuoc().getMaThuoc(), e.getKhuyenMai().getMaKM())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys[0], keys[1])>0;
    }

    @Override
    public ChiTietKhuyenMai selectById(Object... keys) {
        List<ChiTietKhuyenMai> list = selectBySql(SELECT_BY_ID_SQL, keys[0], keys[1]);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<ChiTietKhuyenMai> selectByMaKM(String maKM) {
        return selectBySql(SELECT_BY_MAKM_SQL, maKM);
    }

    @Override
    public List<ChiTietKhuyenMai> selectBySql(String sql, Object... args) {
        List<ChiTietKhuyenMai> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                ChiTietKhuyenMai ct = new ChiTietKhuyenMai();
                ct.setThuoc(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuoc")));
                ct.setKhuyenMai(new KhuyenMai_Dao().selectById(rs.getString("MaKM")));
                ct.setSlApDung(rs.getInt("SLApDung"));
                ct.setSoHDToiDa(rs.getInt("SLToiDa"));
                list.add(ct);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<ChiTietKhuyenMai> selectActiveByMaThuoc(String maThuoc, java.sql.Date today) {
        String sql =
                "SELECT ct.MaThuoc, ct.MaKM, ct.SLApDung, ct.SLToiDa " +
                        "FROM ChiTietKhuyenMai ct " +
                        "JOIN KhuyenMai km ON km.MaKM = ct.MaKM " +
                        "WHERE ct.MaThuoc = ? AND km.NgayBatDau <= ? AND km.NgayKetThuc >= ?";
        return selectBySql(sql, maThuoc, today, today);
    }


    @Override
    public List<ChiTietKhuyenMai> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
    public boolean deleteByMaKM(String maKM) {
        return ConnectDB.update(DELETE_BY_MAKM_SQL, maKM)>0;
    }
}

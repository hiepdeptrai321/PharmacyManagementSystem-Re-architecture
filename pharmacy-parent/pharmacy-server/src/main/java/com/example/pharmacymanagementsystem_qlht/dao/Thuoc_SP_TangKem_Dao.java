package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TangKem;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Thuoc_SP_TangKem_Dao implements DaoInterface<Thuoc_SP_TangKem> {
    private final String INSERT_SQL = "INSERT INTO Thuoc_SP_TangKem (MaThuocTangKem, MaKM, SoLuong) VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE Thuoc_SP_TangKem SET SoLuong=? WHERE MaThuocTangKem=? AND MaKM=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM Thuoc_SP_TangKem WHERE MaThuocTangKem=? AND MaKM=?";
    private final String SELECT_BY_ID_SQL = "SELECT MaThuocTangKem, MaKM, Sơ'ưoLuong FROM Thuoc_SP_TangKem WHERE MaThuocTangKem=? AND MaKM=?";
    private final String SELECT_ALL_SQL = "SELECT MaThuocTangKem, MaKM, SoLuong FROM Thuoc_SP_TangKem";
    private final String SELECT_BY_MAKM_SQL = "SELECT MaThuocTangKem, MaKM, SoLuong FROM Thuoc_SP_TangKem WHERE MaKM=?";
    private final String DELETE_BY_MAKM_SQL = "DELETE FROM Thuoc_SP_TangKem WHERE MaKM=?";

    @Override
    public boolean insert(Thuoc_SP_TangKem e) {
        return ConnectDB.update(INSERT_SQL, e.getThuocTangKem().getMaThuoc(), e.getKhuyenmai().getMaKM(), e.getSoLuong())>0;
    }

    @Override
    public boolean update(Thuoc_SP_TangKem e) {
        return ConnectDB.update(UPDATE_SQL, e.getSoLuong(), e.getThuocTangKem().getMaThuoc(), e.getKhuyenmai().getMaKM())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys[0], keys[1])>0;
    }

    @Override
    public Thuoc_SP_TangKem selectById(Object... keys) {
        List<Thuoc_SP_TangKem> list = selectBySql(SELECT_BY_ID_SQL, keys[0], keys[1]);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Thuoc_SP_TangKem> selectBySql(String sql, Object... args) {
        List<Thuoc_SP_TangKem> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                Thuoc_SP_TangKem ct = new Thuoc_SP_TangKem();
                ct.setThuocTangKem(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuocTangKem")));
                ct.setKhuyenmai(new KhuyenMai_Dao().selectById(rs.getString("MaKM")));
                ct.setSoLuong(rs.getInt("SoLuong"));
                list.add(ct);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Thuoc_SP_TangKem> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
    public List<Thuoc_SP_TangKem> selectByMaKM(String maKM) {
        return selectBySql(SELECT_BY_MAKM_SQL, maKM);
    }
    public boolean deleteByMaKM(String maKM) {
        return ConnectDB.update(DELETE_BY_MAKM_SQL, maKM)>0;
    }
}


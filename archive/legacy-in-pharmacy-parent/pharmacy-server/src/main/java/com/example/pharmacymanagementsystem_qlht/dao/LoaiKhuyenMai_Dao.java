package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.LoaiKhuyenMai;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoaiKhuyenMai_Dao implements DaoInterface<LoaiKhuyenMai> {
    private final String INSERT_SQL = "INSERT INTO LoaiKhuyenMai (MaLoai, TenLoai, MoTa) VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE LoaiKhuyenMai SET TenLoai=?, MoTa=? WHERE MaLoai=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM LoaiKhuyenMai WHERE MaLoai=?";
    private final String SELECT_ALL_SQL = "SELECT MaLoai, TenLoai, MoTa FROM LoaiKhuyenMai";
    private final String SELECT_BY_ID_SQL = "SELECT MaLoai, TenLoai, MoTa FROM LoaiKhuyenMai WHERE MaLoai = ?";
    private final String SElECT_BY_TEN_SQL = "SELECT MaLoai, TenLoai, MoTa FROM LoaiKhuyenMai WHERE TenLoai = ?";
    @Override
    public boolean insert(LoaiKhuyenMai e) {
        return ConnectDB.update(INSERT_SQL, e.getMaLoai(), e.getTenLoai(), e.getMoTa())>0;
    }

    @Override
    public boolean update(LoaiKhuyenMai e) {
        return ConnectDB.update(UPDATE_SQL, e.getTenLoai(), e.getMoTa(), e.getMaLoai())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys)>0;
    }

    @Override
    public LoaiKhuyenMai selectById(Object... keys) {
        return this.selectBySql(SELECT_BY_ID_SQL,keys).get(0);
    }

    @Override
    public List<LoaiKhuyenMai> selectBySql(String sql, Object... args) {
        List<LoaiKhuyenMai> loaiKhuyenMaiList = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql,args);
            while(rs.next()){
                LoaiKhuyenMai loaiKhuyenMai = new LoaiKhuyenMai();
                loaiKhuyenMai.setMaLoai(rs.getString("MaLoai"));
                loaiKhuyenMai.setTenLoai(rs.getString("TenLoai"));
                loaiKhuyenMai.setMoTa(rs.getString("MoTa"));
                loaiKhuyenMaiList.add(loaiKhuyenMai);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return loaiKhuyenMaiList;
    }

    @Override
    public List<LoaiKhuyenMai> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
    public LoaiKhuyenMai selectByTen(String tenLoai) {
        return this.selectBySql(SElECT_BY_TEN_SQL, tenLoai).get(0);
    }
}

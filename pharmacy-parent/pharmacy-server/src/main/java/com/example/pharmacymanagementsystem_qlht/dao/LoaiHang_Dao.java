package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.LoaiHang;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoaiHang_Dao implements DaoInterface<LoaiHang>{

    private final String INSERT_SQL = "INSERT INTO LoaiHang(MaLoaiHang, TenLH, MoTa) VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE LoaiHang SET TenLH=?, MoTa=? WHERE MaLoaiHang=?";
    private final String DELETE_BY_ID = "DELETE FROM LoaiHang WHERE MaLoaiHang = ?";
    private final String SELECT_BY_ID = "SELECT * FROM LoaiHang WHERE MaLoaiHang=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM LoaiHang";
    private final String SELECT_TENLH = "SELECT TenLH FROM LoaiHang";

    @Override
    public boolean insert(LoaiHang e) {
        return ConnectDB.update(INSERT_SQL, e.getMaLoaiHang(), e.getTenLoaiHang(), e.getMoTa())>0;
    }

    @Override
    public boolean update(LoaiHang e) {
        return ConnectDB.update(UPDATE_SQL, e.getTenLoaiHang(), e.getMoTa(), e.getMaLoaiHang())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID, keys)>0;
    }

    @Override
    public LoaiHang selectById(Object... keys) {
        List<LoaiHang> list = selectBySql(SELECT_BY_ID, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LoaiHang> selectBySql(String sql, Object... args) {
        List<LoaiHang> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                LoaiHang lh = new LoaiHang();
                lh.setMaLoaiHang(rs.getString("MaLoaiHang"));
                lh.setTenLoaiHang(rs.getString("TenLH"));
                lh.setMoTa(rs.getString("MoTa"));
                list.add(lh);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> getAllTenLH() {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(SELECT_TENLH);
            while (rs.next()) {
                list.add(rs.getString("TenLH"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<LoaiHang> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public LoaiHang selectByTenLH(String string) {
        String sql = "SELECT * FROM LoaiHang WHERE TenLH = ?";
        List<LoaiHang> list = selectBySql(sql, string);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<LoaiHang> selectTenLoaiHang() {
        String sql = "SELECT TenLH FROM LoaiHang";
        return this.selectBySql(sql);
    }

    public List<String> getAllLoaiHang() {
        String sql = "SELECT TenLH FROM LoaiHang";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql);
            while (rs.next()) {
                list.add(rs.getString("TenLH"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public LoaiHang selectByTenLoaiHang(String string) {
        String sql = "SELECT * FROM LoaiHang WHERE TenLH = ?";
        List<LoaiHang> list = selectBySql(sql, string);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public String generateNewMaLoaiHang() {
        String newMa = "LH001";
        String sql =
                "SELECT TOP 1 MaLoaiHang " +
                        "FROM LoaiHang " +
                        "WHERE MaLoaiHang LIKE 'LH%' " +
                        "ORDER BY MaLoaiHang DESC";

        try (ResultSet rs = ConnectDB.query(sql)) {
            if (rs.next()) {
                String lastMa = rs.getString("MaLoaiHang");
                String num = lastMa.substring(2);

                if (num.matches("\\d+")) {
                    newMa = String.format("LH%03d", Integer.parseInt(num) + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newMa;
    }


}



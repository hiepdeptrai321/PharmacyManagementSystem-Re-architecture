package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KeHang_Dao implements DaoInterface<KeHang> {

    private final String INSERT_SQL = "INSERT INTO KeHang(MaKe, Tenke, MoTa) VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE KeHang SET TenKe=?, MoTa=? WHERE MaKe=?";
    private final String DELETE_BY_ID = "DELETE FROM KeHang WHERE MaKe = ?";
    private final String SELECT_BY_ID = "SELECT * FROM KeHang WHERE MaKe=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM KeHang";

    @Override
    public boolean insert(KeHang e) {
        return ConnectDB.update(INSERT_SQL, e.getMaKe(), e.getTenKe(), e.getMoTa()) > 0;}

    @Override
    public boolean update(KeHang e) {
        return ConnectDB.update(UPDATE_SQL, e.getTenKe(), e.getMoTa(), e.getMaKe()) > 0;}

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID, keys)>0;
    }

    @Override
    public KeHang selectById(Object... keys) {
       List <KeHang> list = selectBySql(SELECT_BY_ID, keys);
        if (list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List selectBySql(String sql, Object... args) {
        List <KeHang> list = new ArrayList<>();
                try {
                    ResultSet rs = ConnectDB.query(sql, args);
                    while (rs.next()){
                        KeHang keHang = new KeHang();
                        keHang.setMaKe(rs.getString("MaKe"));
                        keHang.setTenKe(rs.getString("TenKe"));
                        keHang.setMoTa(rs.getString("MoTa"));
                        list.add(keHang);
                    }
                    rs.getStatement().getConnection().close();
                }
                catch (Exception e){
                    throw new RuntimeException(e);
                }
        return list;
    }

    public String generateNewMaKeHang() {
        String newMaKeHang = "KE001"; // Default value if no records exist
        String SELECT_TOP1_SQL = "SELECT TOP 1 maKe FROM KeHang ORDER BY MaKe DESC";
        try {
            ResultSet rs = ConnectDB.query(SELECT_TOP1_SQL);
            if (rs.next()) {
                String lastMaKeHang = rs.getString("maKe");
                int stt = Integer.parseInt(lastMaKeHang.substring(2)); // Extract numeric part
                stt++; // Increment the numeric part
                newMaKeHang = String.format("KE%03d", stt); // Format with leading zeros
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newMaKeHang;
    }

    @Override
    public List selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<String> getAllTenKe() {
        String sql = "SELECT TenKe FROM KeHang";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql);
            while (rs.next()) {
                list.add(rs.getString("TenKe"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public KeHang selectByTenKe(String string) {
        String sql = "SELECT * FROM KeHang WHERE TenKe = ?";
        List<KeHang> list = selectBySql(sql, string);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<String> selectTenKe() {
        String sql = "SELECT TenKe FROM KeHang";
        return this.selectBySql(sql);
    }
}

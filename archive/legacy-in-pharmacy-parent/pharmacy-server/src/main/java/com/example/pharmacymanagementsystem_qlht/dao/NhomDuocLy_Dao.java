package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.NhomDuocLy;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhomDuocLy_Dao implements DaoInterface<NhomDuocLy> {

    private final String INSERT_SQL = "INSERT INTO NhomDuocLy(MaNDL, TenNDL, Mota) VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE NhomDuocLy SET TenNDL=?, MoTa=? WHERE MaNDL=?";
    private final String DELETE_BY_ID = "DELETE FROM NhomDuocLy WHERE MaNDL = ?";
    private final String SELECT_BY_ID = "SELECT * FROM NhomDuocLy WHERE MaNDL=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM NhomDuocLy";

    @Override
    public boolean insert(NhomDuocLy e) {
        return ConnectDB.update(INSERT_SQL, e.getMaNDL(), e.getTenNDL(), e.getMoTa())>0;
    }

    @Override
    public boolean update(NhomDuocLy e) {
        return ConnectDB.update(UPDATE_SQL, e.getTenNDL(), e.getMoTa(), e.getMaNDL())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID, keys)>0;
    }

    @Override
    public NhomDuocLy selectById(Object... keys) {
        List<NhomDuocLy> list = selectBySql(SELECT_BY_ID, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhomDuocLy> selectBySql(String sql, Object... args) {
        List<NhomDuocLy> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                NhomDuocLy ndl = new NhomDuocLy();
                ndl.setMaNDL(rs.getString("MaNDL"));
                ndl.setTenNDL(rs.getString("TenNDL"));
                ndl.setMoTa(rs.getString("MoTa"));
                list.add(ndl);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public String generateNewMaNhomDL() {
        String newMaNhomDuocLy = "NDL001"; // Default value if no records exist
        String SELECT_TOP1_SQL = "SELECT TOP 1 MaNDL FROM NhomDuocLy ORDER BY MaNDL DESC";
        try {
            ResultSet rs = ConnectDB.query(SELECT_TOP1_SQL);
            if (rs.next()) {
                String lastMaNDL = rs.getString("maNDL");
                int stt = Integer.parseInt(lastMaNDL.substring(3)); // Extract numeric part
                stt++; // Increment the numeric part
                newMaNhomDuocLy = String.format("NDL%03d", stt); // Format with leading zeros
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newMaNhomDuocLy;
    }


    @Override
    public List<NhomDuocLy> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<String> getAllTenNhomDuocLy() {
        String sql = "SELECT DISTINCT TenNDL FROM NhomDuocLy";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql);
            while (rs.next()) {
                list.add(rs.getString("TenNDL"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public NhomDuocLy selectByTenNhomDuocLy(String string) {
        String sql = "SELECT * FROM NhomDuocLy WHERE TenNDL = ?";
        List<NhomDuocLy> list = selectBySql(sql, string);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}

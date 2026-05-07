package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.DonViTinh;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DonViTinh_Dao implements DaoInterface<DonViTinh>{

    private final String INSERT_SQL = "INSERT INTO DonViTinh (MaDVT, TenDonViTinh, KiHieu) VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE DonViTinh SET TenDonViTinh=?, KiHieu=? WHERE MaDVT=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM DonViTinh WHERE MaDVT=?";
    private final String SELECT_BY_ID_SQL = "SELECT MaDVT, TenDonViTinh, KiHieu FROM DonViTinh WHERE MaDVT = ?";
    private final String SELECT_ALL_SQL = "SELECT MaDVT, TenDonViTinh, KiHieu FROM DonViTinh";
    private final String SELECT_BY_TEN_SQL = "SELECT MaDVT, TenDonViTinh, KiHieu FROM DonViTinh WHERE TenDonViTinh = ?";
    private final String SELECT_TOP1_MANCC = "SELECT TOP 1 MaDVT FROM DonViTinh ORDER BY MaDVT DESC";

    @Override
    public boolean insert(DonViTinh e) {
        return ConnectDB.update(INSERT_SQL, e.getMaDVT(), e.getTenDonViTinh(), e.getKiHieu())>0;
    }

    @Override
    public boolean update(DonViTinh e) {
        return ConnectDB.update(UPDATE_SQL, e.getTenDonViTinh(), e.getKiHieu(), e.getMaDVT())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys)>0;
    }

    @Override
    public DonViTinh selectById(Object... keys) {
        List<DonViTinh> list = selectBySql(SELECT_BY_ID_SQL, keys);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<DonViTinh> selectBySql(String sql, Object... args) {
        List<DonViTinh> donViTinhList = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                DonViTinh dvt = new DonViTinh();
                dvt.setMaDVT(rs.getString("MaDVT"));
                dvt.setTenDonViTinh(rs.getString("TenDonViTinh"));
                dvt.setKiHieu(rs.getString("KiHieu"));
                donViTinhList.add(dvt);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return donViTinhList;
    }

    @Override
    public List<DonViTinh> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
    public DonViTinh selectByTenDVT(String tenDVT) {
        return selectBySql(SELECT_BY_TEN_SQL, tenDVT).get(0);
    }

    public String generatekeyDonViTinh() {
        String key = "DVT001";
        try {
            String lastKey = ConnectDB.queryTaoMa(SELECT_TOP1_MANCC);
            if (lastKey != null && lastKey.startsWith("DVT")) {
                int numericPart = Integer.parseInt(lastKey.substring(3));
                numericPart++;
                key = String.format("DVT%02d", numericPart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
}

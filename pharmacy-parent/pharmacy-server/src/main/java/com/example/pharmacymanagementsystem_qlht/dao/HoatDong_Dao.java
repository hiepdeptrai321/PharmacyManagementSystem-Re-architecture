package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.HoatDong;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HoatDong_Dao implements DaoInterface<HoatDong> {

    // TODO (MariaDB migration): MaHDong khong con duoc sinh bang generated column
    // dua tren ID AUTO_INCREMENT. Khi migrate HoatDong sang service/JPA, can insert
    // truoc de lay ID, sau do sinh MaHDong theo format HD + LPAD(ID, 4, '0') va
    // update lai ban ghi o service layer.
    private final String INSERT_SQL = "INSERT INTO HoatDong(MaHDong, LoaiHD, ThoiGian, MaNV, BangDL, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE HoatDong SET LoaiHD=?, ThoiGian=?, MaNV=?, BangDL=?,GhiChu=? WHERE maHD=?";
    private final String DELETE_BY_ID = "DELETE FROM HoatDong WHERE MaHDong = ?";
    private final String SELECT_BY_ID = "SELECT * FROM HoatDong WHERE MaHDong=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM HoatDong";

    @Override
    public boolean insert(HoatDong e) {
        return ConnectDB.update(INSERT_SQL, e.getLoaiHD(), e.getBang(), e.getThoiGian(), e.getNoiDung(), e.getNhanVien().getMaNV(), e.getMaHD())>0;
    }

    @Override
    public boolean update(HoatDong e) {
        return ConnectDB.update(UPDATE_SQL, e.getLoaiHD(), e.getBang(), e.getThoiGian(), e.getNoiDung(), e.getMaHD())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID, keys)>0;
    }

    @Override
    public HoatDong selectById(Object... keys) {
        List<HoatDong> list = selectBySql(SELECT_BY_ID, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoatDong> selectBySql(String sql, Object... args) {
        List<HoatDong> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                HoatDong hd = new HoatDong();
                hd.setMaHD(rs.getString("MaHDong"));
                hd.setLoaiHD(rs.getString("LoaiHD"));
                hd.setBang(rs.getString("BangDL"));
                hd.setThoiGian(rs.getTimestamp("ThoiGian"));
                hd.setNoiDung(rs.getString("NoiDung"));
                hd.setNhanVien(new NhanVien_Dao().selectById(rs.getString("MaNV")));
                list.add(hd);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<HoatDong> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

}




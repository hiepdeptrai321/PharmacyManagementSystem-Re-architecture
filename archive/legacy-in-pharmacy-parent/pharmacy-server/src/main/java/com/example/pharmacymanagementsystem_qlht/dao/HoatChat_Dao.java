package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.HoatChat;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HoatChat_Dao implements DaoInterface<HoatChat> {
    private final String INSERT_SQL = "INSERT INTO HoatChat (TenHoatChat) VALUES (?)";
    private final String UPDATE_SQL = "UPDATE HoatChat SET TenHoatChat = ? WHERE MaHoatChat = ?";
    private final String DELETE_SQL = "DELETE FROM HoatChat WHERE MaHoatChat = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM HoatChat";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM HoatChat WHERE MaHoatChat = ?";

    @Override
    public boolean insert(HoatChat e) {
        return ConnectDB.update(INSERT_SQL, e.getTenHoatChat())>0;
    }

    @Override
    public boolean update(HoatChat e) {
        return ConnectDB.update(UPDATE_SQL,  e.getTenHoatChat(), e.getMaHoatChat())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_SQL, keys)>0;
    }

    @Override
    public HoatChat selectById(Object... keys) {
        return this.selectBySql(SELECT_BY_ID_SQL, keys).get(0);
    }

    @Override
    public List<HoatChat> selectBySql(String sql, Object... args) {
        List<HoatChat> list = new ArrayList<HoatChat>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                HoatChat hc = new HoatChat();
                hc.setMaHoatChat(rs.getString("MaHoatChat"));
                hc.setTenHoatChat(rs.getString("TenHoatChat"));
                list.add(hc);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<HoatChat> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
}

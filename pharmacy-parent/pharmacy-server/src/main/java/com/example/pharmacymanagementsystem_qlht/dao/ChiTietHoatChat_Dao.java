package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoatChat_Dao implements DaoInterface<ChiTietHoatChat>{
    private final String INSERT_SQL = "INSERT INTO ChiTietHoatChat (MaThuoc, MaHoatChat, HamLuong) VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE ChiTietHoatChat SET HamLuong = ? WHERE MaThuoc = ? AND MaHoatChat = ?";
    private final String DELETE_BY_ID = "DELETE FROM ChiTietHoatChat WHERE MaThuoc = ? AND MaHoatChat = ?";
    private final String SELECT_BY_ID = "SELECT * FROM ChiTietHoatChat WHERE MaThuoc = ? AND MaHoatChat = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM ChiTietHoatChat";
    private final String SELECT_BY_MATHUOC = "SELECT * FROM ChiTietHoatChat WHERE MaThuoc = ?";

    @Override
    public boolean insert(ChiTietHoatChat e) {
        return ConnectDB.update(INSERT_SQL, e.getThuoc().getMaThuoc(), e.getHoatChat().getMaHoatChat(), e.getHamLuong())>0;
    }

    @Override
    public boolean update(ChiTietHoatChat e) {
        return ConnectDB.update(UPDATE_SQL, e.getHamLuong(), e.getThuoc().getMaThuoc(), e.getHoatChat().getMaHoatChat())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID, keys[0], keys[1])>0;
    }

    public ChiTietHoatChat selectById(Object... keys) {
        List<ChiTietHoatChat> list = this.selectBySql(SELECT_BY_ID, keys[0], keys[1]);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChiTietHoatChat> selectBySql(String sql, Object... args) {
        List<ChiTietHoatChat> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                ChiTietHoatChat ct = new ChiTietHoatChat();
                ct.setThuoc(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuoc")));
                ct.setHoatChat(new HoatChat_Dao().selectById(rs.getString("MaHoatChat")));
                ct.setHamLuong(rs.getFloat("HamLuong"));
                list.add(ct);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<ChiTietHoatChat> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<ChiTietHoatChat> selectByMaThuoc(String maThuoc) {
        return this.selectBySql(SELECT_BY_MATHUOC, maThuoc);
    }
}

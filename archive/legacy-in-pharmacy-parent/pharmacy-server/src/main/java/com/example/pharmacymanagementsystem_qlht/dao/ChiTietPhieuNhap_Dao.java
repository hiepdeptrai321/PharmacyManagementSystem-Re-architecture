package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuNhap;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuNhap_Dao implements DaoInterface<ChiTietPhieuNhap> {
    private final String INSERT_SQL = "INSERT INTO ChiTietPhieuNhap (MaPN, MaThuoc, SoLuong, GiaNhap, ChietKhau, Thue) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE ChiTietPhieuNhap SET SoLuong = ?, GiaNhap = ?, ChietKhau = ?, Thue = ? WHERE MaPN = ? AND MaThuoc = ? AND MaLH = ?";
    private final String DELETE_SQL = "DELETE FROM ChiTietPhieuNhap WHERE MaPN = ? AND MaThuoc = ? AND MaLH = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM ChiTietPhieuNhap";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietPhieuNhap WHERE MaPN = ? AND MaThuoc = ? AND MaLH = ?";
    private final String SELECT_BY_MAPN_SQL = "SELECT * FROM ChiTietPhieuNhap WHERE MaPN = ?";
    private final String SELECT_TOP1_MALOHANG = "SELECT TOP 1 MaLH FROM ChiTietPhieuNhap ORDER BY MaLH DESC";

    @Override
    public boolean insert(ChiTietPhieuNhap e) {
        return ConnectDB.update(INSERT_SQL, e.getPhieuNhap().getMaPN(), e.getThuoc().getMaThuoc(), e.getMaLH(), e.getSoLuong(), e.getGiaNhap(),e.getChietKhau(),e.getThue())>0;
    }

    @Override
    public boolean update(ChiTietPhieuNhap e) {
        return ConnectDB.update(UPDATE_SQL, e.getSoLuong(), e.getGiaNhap(), e.getChietKhau(), e.getThue(), e.getPhieuNhap().getMaPN(), e.getThuoc().getMaThuoc(), e.getMaLH())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_SQL, keys[0], keys[1], keys[2])>0;
    }

    @Override
    public ChiTietPhieuNhap selectById(Object... keys) {
        if (keys == null || keys.length < 3) {
            throw new IllegalArgumentException("selectById requires MaPN, MaThuoc, MaLH");
        }
        List<ChiTietPhieuNhap> list = this.selectBySql(SELECT_BY_ID_SQL, keys[0], keys[1], keys[2]);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ChiTietPhieuNhap> selectBySql(String sql, Object... args) {
        List<ChiTietPhieuNhap> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
                ctpn.setPhieuNhap(new PhieuNhap_Dao().selectById(rs.getString("MaPN")));
                ctpn.setThuoc(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuoc")));
                ctpn.setMaLH(rs.getString("MaLH"));
                ctpn.setSoLuong(rs.getInt("SoLuong"));
                ctpn.setGiaNhap(rs.getDouble("GiaNhap"));
                ctpn.setChietKhau(rs.getFloat("ChietKhau"));
                ctpn.setThue(rs.getFloat("Thue"));
                list.add(ctpn);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<ChiTietPhieuNhap> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<ChiTietPhieuNhap> getChiTietPhieuNhapByMaPN(String maPN) {
        return this.selectBySql(SELECT_BY_MAPN_SQL, maPN);
    }

    public String generateMaLH(){
        String key = "LH00001";
        try {
            String lastKey = ConnectDB.queryTaoMa(SELECT_TOP1_MALOHANG);
            if (lastKey != null && lastKey.startsWith("LH")) {
                int stt = Integer.parseInt(lastKey.substring(2));
                stt++;
                key = String.format("LH%05d", stt);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return key;
    }
}

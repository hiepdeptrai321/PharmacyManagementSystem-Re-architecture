package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuNhap;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuTraHang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuTraHang_Dao implements DaoInterface<ChiTietPhieuTraHang> {
    private final String INSERT_SQL = "INSERT INTO ChiTietPhieuTraHang (MaLH, MaPT, MaThuoc, MaDVT, SoLuong, DonGia, GiamGia, LyDoTra) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE ChiTietPhieuTraHang SET SoLuong=?, DonGia=?, GiamGia=?, LyDoDoi=? WHERE MaLH=? AND MaPT=? AND MaThuoc=? AND MaDVT=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM ChiTietPhieuTraHang WHERE MaLH=? AND MaPT=? AND MaThuoc=? AND MaDVT=?";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietPhieuTraHang WHERE MaLH=? AND MaPT=? AND MaThuoc=? AND MaDVT=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM ChiTietPhieuTraHang";
    private final String SELECT_BY_MAPT_SQL = "SELECT * FROM ChiTietPhieuTraHang WHERE MaPT = ?";

    @Override
    public boolean insert(ChiTietPhieuTraHang e) {
        return ConnectDB.update(
                INSERT_SQL,
                e.getLoHang().getMaLH(),
                e.getPhieuTraHang().getMaPT(),
                e.getThuoc().getMaThuoc(),
                e.getDvt() != null ? e.getDvt().getMaDVT() : null,
                e.getSoLuong(),
                e.getDonGia(),
                e.getGiamGia(),
                e.getLyDoTra()
        ) > 0;
    }

    @Override
    public boolean update(ChiTietPhieuTraHang e) {
        return ConnectDB.update(
                UPDATE_SQL,
                e.getSoLuong(),
                e.getDonGia(),
                e.getGiamGia(),
                e.getLyDoTra(),
                e.getLoHang().getMaLH(),
                e.getPhieuTraHang().getMaPT(),
                e.getThuoc().getMaThuoc(),
                e.getDvt() != null ? e.getDvt().getMaDVT() : null
        ) > 0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys[0], keys[1], keys[2], keys[3]) > 0;
    }

    @Override
    public ChiTietPhieuTraHang selectById(Object... keys) {
        List<ChiTietPhieuTraHang> list = selectBySql(SELECT_BY_ID_SQL, keys[0], keys[1], keys[2], keys[3]);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ChiTietPhieuTraHang> selectBySql(String sql, Object... args) {
        List<ChiTietPhieuTraHang> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                ChiTietPhieuTraHang ct = new ChiTietPhieuTraHang();
                ct.setLoHang(new Thuoc_SP_TheoLo_Dao().selectById(rs.getString("MaLH")));
                ct.setPhieuTraHang(new PhieuTraHang_Dao().selectById(rs.getString("MaPT")));
                ct.setThuoc(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuoc")));
                ct.setDvt(new DonViTinh_Dao().selectById(rs.getString("MaDVT")));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGia(rs.getDouble("DonGia"));
                ct.setGiamGia(rs.getDouble("GiamGia"));
                try {
                    String ly = rs.getString("LyDoTra");
                    ct.setLyDoTra(ly == null ? "" : ly);
                } catch (Exception ignore) {
                    ct.setLyDoTra("");
                }

                list.add(ct);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public int tongSoLuongDaTra(String maHD, String maLH) {
        String sql = """
        SELECT COALESCE(SUM(ct.SoLuong), 0)
        FROM ChiTietPhieuTraHang ct
        JOIN PhieuTraHang p ON ct.MaPT = p.MaPT
        WHERE p.MaHD = ? AND ct.MaLH = ?
    """;

        try (Connection con = ConnectDB.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHD);
            ps.setString(2, maLH);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



    @Override
    public List<ChiTietPhieuTraHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public List<ChiTietPhieuTraHang> getChiTietPhieuTraByMaPT(String maPT) {
        return this.selectBySql(SELECT_BY_MAPT_SQL, maPT);
    }
}
package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMai_Dao implements DaoInterface<KhuyenMai> {
    private final String INSERT_SQL = "INSERT INTO KhuyenMai (MaKM, MaLoai, TenKM, GiaTriKM, NgayBatDau, NgayKetThuc, MoTa, GiaTriApDung) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE KhuyenMai SET MaLoai=?, TenKM=?, GiaTriKM=?, NgayBatDau=?, NgayKetThuc=?, MoTa=?, GiaTriApDung=? WHERE MaKM=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM KhuyenMai WHERE MaKM=?";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM KhuyenMai WHERE MaKM = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM KhuyenMai";
    private final String SELECT_BY_TUKHOA_SQL = "SELECT * FROM KhuyenMai WHERE TenKM LIKE ? OR MaKM LIKE ?";
    private final String DElETE_BY_MAKM_SQL = "DELETE FROM KhuyenMai WHERE MaKM=?";

    @Override
    public boolean insert(KhuyenMai e) {
        return ConnectDB.update(INSERT_SQL, e.getMaKM(), e.getLoaiKM().getMaLoai(), e.getTenKM(), e.getGiaTriKM(), e.getNgayBatDau(), e.getNgayKetThuc(), e.getMoTa(), e.getGiaTriApDung())>0;
    }

    @Override
    public boolean update(KhuyenMai e) {
        return ConnectDB.update(UPDATE_SQL, e.getLoaiKM().getMaLoai(), e.getTenKM(), e.getGiaTriKM(), e.getNgayBatDau(), e.getNgayKetThuc(), e.getMoTa(), e.getGiaTriApDung(), e.getMaKM())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys)>0;
    }

    @Override
    public KhuyenMai selectById(Object... keys) {
        return this.selectBySql(SELECT_BY_ID_SQL, keys).get(0);
    }

    @Override
    public List<KhuyenMai> selectBySql(String sql, Object... args) {
        List<KhuyenMai> khuyenMaiList = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                KhuyenMai khuyenMai = new KhuyenMai();
                khuyenMai.setMaKM(rs.getString("MaKM"));
                khuyenMai.setLoaiKM(new LoaiKhuyenMai_Dao().selectById(rs.getString("MaLoai")));
                khuyenMai.setTenKM(rs.getString("TenKM"));
                khuyenMai.setGiaTriKM(rs.getFloat("GiaTriKM"));
                khuyenMai.setNgayBatDau(rs.getDate("NgayBatDau"));
                khuyenMai.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                khuyenMai.setMoTa(rs.getString("MoTa"));
                khuyenMai.setNgayTao(rs.getTimestamp("NgayTao"));
                khuyenMai.setGiaTriApDung(rs.getFloat("GiaTriApDung"));
                khuyenMaiList.add(khuyenMai);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return khuyenMaiList;
    }
    public List<KhuyenMai> selectActiveOn(Date day) {
        String sql = "SELECT * FROM KhuyenMai WHERE NgayBatDau <= ? AND NgayKetThuc >= ?";
        return selectBySql(sql, day, day);
    }
    public List<KhuyenMai> selectActiveInvoiceOn(Date day) {
        String sql =
                "SELECT * FROM KhuyenMai " +
                        "WHERE NgayBatDau <= ? AND NgayKetThuc >= ? AND MaLoai IN ('LKM004','LKM005')";
        return selectBySql(sql, day, day);
    }

    @Override
    public List<KhuyenMai> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public String generateNewMaKM() {
        String newMaKM = "KM001"; // Default value if no records exist
        String SELECT_TOP1_SQL = "SELECT TOP 1 maKM FROM KhuyenMai ORDER BY MaKM DESC";
        try {
            ResultSet rs = ConnectDB.query(SELECT_TOP1_SQL);
            if (rs.next()) {
                String lastMaKM = rs.getString("maKM");
                int stt = Integer.parseInt(lastMaKM.substring(2)); // Extract numeric part
                stt++; // Increment the numeric part
                newMaKM = String.format("KM%03d", stt); // Format with leading zeros
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newMaKM;
    }

    public List<KhuyenMai> selectByTuKhoa(String tuKhoa){
        return this.selectBySql(SELECT_BY_TUKHOA_SQL, "%" + tuKhoa + "%", "%" + tuKhoa + "%");
    }
    public boolean deleteByMaKM(String maKM) {
        return ConnectDB.update(DElETE_BY_MAKM_SQL, maKM)>0;
    }
}

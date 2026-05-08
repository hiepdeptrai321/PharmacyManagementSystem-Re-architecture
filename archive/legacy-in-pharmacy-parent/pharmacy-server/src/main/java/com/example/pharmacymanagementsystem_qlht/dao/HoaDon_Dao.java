package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.HoaDon;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_Dao implements DaoInterface<HoaDon>{

    private final KhachHang_Dao khDao = new KhachHang_Dao();

    private final String INSERT_SQL = "INSERT INTO HoaDon (MaHD, MaNV, NgayLap, MaKH, TrangThai, LoaiHoaDon, MaDonThuoc) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE HoaDon SET MaNV=?, NgayLap=?, MaKH=?, TrangThai=?, LoaiHoaDon=?, MaDonThuoc=? WHERE MaHD=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM HoaDon WHERE MaHD=?";
    private final String SELECT_BY_ID_SQL = "SELECT MaHD, MaNV, NgayLap, MaKH, TrangThai, LoaiHoaDon, MaDonThuoc FROM HoaDon WHERE MaHD=?";
    private final String SELECT_ALL_SQL =
            "SELECT HD.MaHD, HD.NgayLap, HD.TrangThai, HD.LoaiHoaDon, HD.MaDonThuoc, " +
                    "       NV.MaNV, NV.TenNV, " +
                    "       KH.MaKH, KH.TenKH, KH.SDT " +
                    "FROM HoaDon HD " +
                    "LEFT JOIN NhanVien NV ON HD.MaNV = NV.MaNV " +
                    "LEFT JOIN KhachHang KH ON HD.MaKH = KH.MaKH";
    private final String INSERT_HD_SQL = "INSERT INTO HoaDon (MaNV, MaKH, NgayLap, TrangThai) OUTPUT INSERTED.MaHD VALUES (?, ?, GETDATE(), ?)";

    private final String SELECT_BY_TUKHOA_SQL = "SELECT MaHD, MaNV, NgayLap, MaKH, TrangThai FROM HoaDon WHERE MaHD LIKE ?";
    public String insertAndGetId(Connection conn, HoaDon hoaDon) throws SQLException {
        String generatedMaHoaDon = null;

        // Sử dụng try-with-resources cho PreparedStatement
        try (PreparedStatement ps = conn.prepareStatement(INSERT_HD_SQL)) {

            // Giả định: HoaDon model chứa các object NhanVien và KhachHang có thể lấy được mã
            ps.setString(1, hoaDon.getMaNV().getMaNV());
            ps.setString(2, hoaDon.getMaKH().getMaKH());
            ps.setBoolean(3, hoaDon.getTrangThai());

            // Dùng executeQuery để lấy kết quả từ OUTPUT INSERTED
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    generatedMaHoaDon = rs.getString(1); // Cột đầu tiên là MaHD
                }
            }
        }
        return generatedMaHoaDon;
    }
    @Override
    public boolean insert(HoaDon e) {
        boolean ok = ConnectDB.update(INSERT_SQL, e.getMaHD(), e.getMaNV().getMaNV(), e.getNgayLap(), e.getMaKH().getMaKH(), e.getTrangThai())>0;
        if (ok) {
            // update customer statuses after a successful sale
            try {
                khDao.refreshTrangThai();
            } catch (Exception ex) {
                // non-fatal: log and continue
                System.err.println("Không thể cập nhật trạng thái Khách Hàng sau khi tạo Hóa Đơn: " + ex.getMessage());
            }
        }
        return ok;
    }

    @Override
    public boolean update(HoaDon e) {
        return ConnectDB.update(
                UPDATE_SQL,
                e.getMaNV().getMaNV(),
                e.getNgayLap(),
                e.getMaKH() != null ? e.getMaKH().getMaKH() : null,
                e.getTrangThai(),
                e.getLoaiHoaDon(),
                e.getMaDonThuoc(),
                e.getMaHD()
        ) > 0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys)>0;
    }

    @Override
    public HoaDon selectById(Object... keys) {
        List<HoaDon> list = selectBySql(SELECT_BY_ID_SQL, keys);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> hoaDonList = new ArrayList<>();
        try (ResultSet rs = ConnectDB.query(sql, args)) {
            java.sql.ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setNgayLap(rs.getTimestamp("NgayLap"));
                hd.setTrangThai(rs.getBoolean("TrangThai"));
                hd.setLoaiHoaDon(rs.getString("LoaiHoaDon"));
                hd.setMaDonThuoc(rs.getString("MaDonThuoc"));

                // Khách Hàng
                KhachHang kh = new KhachHang();
                if (hasColumn(meta, "MaKH")) kh.setMaKH(rs.getString("MaKH"));
                if (hasColumn(meta, "TenKH")) kh.setTenKH(rs.getString("TenKH"));
                if (hasColumn(meta, "SDT")) kh.setSdt(rs.getString("SDT"));
                hd.setMaKH(kh);

                // Nhân Viên
                NhanVien nv = new NhanVien();
                if (hasColumn(meta, "MaNV")) nv.setMaNV(rs.getString("MaNV"));
                if (hasColumn(meta, "TenNV")) nv.setTenNV(rs.getString("TenNV"));
                hd.setMaNV(nv);

                hoaDonList.add(hd);
            }
        } catch (Exception e) {
            System.err.println("Lỗi truy vấn dữ liệu Hóa Đơn (selectBySql): ");
            e.printStackTrace();
        }
        return hoaDonList;
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

//    public String generateNewMaHD() {
//        String newMaHD = "HD001"; // Default value if no records exist
//        String SELECT_TOP1_SQL = "SELECT TOP 1 MaHD FROM HoaDon ORDER BY MaHD DESC";
//        try {
//            ResultSet rs = ConnectDB.query(SELECT_TOP1_SQL);
//            if (rs.next()) {
//                String lastMaHD = rs.getString("MaHD");
//                int stt = Integer.parseInt(lastMaHD.substring(2)); // Extract numeric part
//                stt++; // Increment the numeric part
//                newMaHD = String.format("HD%03d", stt); // Format with leading zeros
//            }
//            rs.getStatement().close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return newMaHD;
//    }
    public String generateNewMaHD() {
        String newMaHD = "HD001";
        String sql = "SELECT TOP 1 MaHD FROM HoaDon ORDER BY MaHD DESC";
        try {
            ResultSet rs = ConnectDB.query(sql);
            if (rs.next()) {
                String lastMaHD = rs.getString("MaHD");
                int stt = Integer.parseInt(lastMaHD.substring(2)) + 1;
                newMaHD = String.format("HD%03d", stt);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newMaHD;
    }

    public List<HoaDon> selectByTuKhoa(String tuKhoa){
        return this.selectBySql(SELECT_BY_TUKHOA_SQL, "%" + tuKhoa + "%");
    }
    private boolean hasColumn(java.sql.ResultSetMetaData meta, String column) throws java.sql.SQLException {
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            if (meta.getColumnName(i).equalsIgnoreCase(column)) return true;
        }
        return false;
    }
    public boolean updateKhachHang(String maHD, String maKH) {
        String sql = "UPDATE HoaDon SET MaKH = ? WHERE MaHD = ?";
        return ConnectDB.update(sql, maKH, maHD) > 0;
    }
}

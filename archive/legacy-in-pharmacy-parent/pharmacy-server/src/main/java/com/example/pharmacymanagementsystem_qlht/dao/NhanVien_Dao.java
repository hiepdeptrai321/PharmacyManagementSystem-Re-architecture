package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhanVien_Dao implements DaoInterface<NhanVien> {
    private final String INSERT_SQL = "INSERT INTO NhanVien(MaNV, TenNV, SDT,Email, NgaySinh, GioiTinh, DiaChi, TrangThai, TaiKhoan,MatKhau, NgayVaoLam, NgayKetThuc, TrangThaiXoa, VaiTro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE NhanVien SET TenNV=?, SDT=?, Email=?, NgaySinh=?, GioiTinh=?, DiaChi=?, TrangThai=?, TaiKhoan=?, MatKhau=?, NgayVaoLam=?, NgayKetThuc=?, TrangThaiXoa =? WHERE MaNV=?";
    private final String DELETE_BY_ID = "DELETE FROM NhanVien WHERE MaNV = ?";
    private final String SELECT_BY_ID = "SELECT * FROM NhanVien WHERE MaNV=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM NhanVien WHERE TrangThaiXoa=0";
    private final String SELECT_BY_TAIKHOAN_MATKHAU = "SELECT * FROM NhanVien WHERE TaiKhoan=? AND MatKhau=?";

    @Override
    public boolean insert(NhanVien e) {
        return ConnectDB.update(INSERT_SQL,
                e.getMaNV(),
                e.getTenNV(),
                e.getSdt(),
                e.getEmail(),
                e.getNgaySinh(),
                e.isGioiTinh(),
                e.getDiaChi(),
                e.isTrangThai(),
                e.getTaiKhoan(),
                e.getMatKhau(),
                e.getNgayVaoLam(),
                e.getNgayNghiLam(),e.isTrangThaiXoa(),
                e.getMaNV())>0;
    }

    @Override
    public boolean update(NhanVien e) {
        return ConnectDB.update(UPDATE_SQL,
            e.getTenNV(),
            e.getSdt(),
            e.getEmail(),
            e.getNgaySinh(),
            e.isGioiTinh(),
            e.getDiaChi(),
            e.isTrangThai(),
            e.getTaiKhoan(),
            e.getMatKhau(),
                e.getNgayVaoLam(),
                e.getNgayNghiLam(),e.isTrangThaiXoa(),
            e.getMaNV())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID, keys)>0;
    }

    @Override
    public NhanVien selectById(Object... keys) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setSdt(rs.getString("SDT"));
                nv.setEmail(rs.getString("Email"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setTrangThai(rs.getBoolean("TrangThai"));
                nv.setTaiKhoan(rs.getString("TaiKhoan"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setNgayVaoLam(rs.getDate("NgayVaoLam"));
                nv.setNgayNghiLam(rs.getDate("NgayKetThuc"));
                nv.setTrangThaiXoa(rs.getBoolean("TrangThaiXoa"));
                nv.setVaiTro(rs.getString("VaiTro"));
                list.add(nv);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public String insertNhanVienProc(NhanVien nhanVien) {
        String maMoi = null;
        try {
            // Gọi procedure (ConnectDB sẽ tự nhận ra là CallableStatement)
            ResultSet rs = ConnectDB.query(
                    "{CALL sp_InsertNhanVien(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}",
                    nhanVien.getTenNV(), nhanVien.getSdt(), nhanVien.getEmail(), nhanVien.getNgaySinh(), nhanVien.isGioiTinh(), nhanVien.getDiaChi(),
                    nhanVien.isTrangThai(),nhanVien.getNgayVaoLam() , nhanVien.getTaiKhoan(), nhanVien.getMatKhau()
            );

            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return maMoi;
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM NhanVien WHERE TaiKhoan=? AND MatKhau=?";
        List<NhanVien> list = selectBySql(sql, username, password);
        return !list.isEmpty();
    }
    public NhanVien selectByTKVaMK(String taiKhoan, String matKhau) {
        List<NhanVien> list = selectBySql(SELECT_BY_TAIKHOAN_MATKHAU, taiKhoan, matKhau);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}

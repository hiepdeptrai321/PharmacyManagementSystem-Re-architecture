package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Thuoc_SanPham_Dao implements DaoInterface<Thuoc_SanPham> {
    private final String INSERT_SQL = "INSERT INTO Thuoc_SanPham (MaThuoc,TenThuoc, HamLuong, DonViHL, DuongDung, QuyCachDongGoi, SDK_GPNK, HangSX, NuocSX, MaNDL, MaLoaiHang, HinhAnh, ViTri, TrangThaiXoa,ETC) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL =
            "UPDATE Thuoc_SanPham SET " +
                    "TenThuoc=?, HamLuong=?, DonViHL=?, DuongDung=?, QuyCachDongGoi=?, SDK_GPNK=?, HangSX=?, NuocSX=?, " +
                    "HinhAnh=?, MaLoaiHang=?, MaNDL=?, ViTri=? , ETC=?" +
                    "WHERE MaThuoc=?";
    private final String DELETE_SQL = "DELETE FROM Thuoc_SanPham WHERE MaThuoc=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM Thuoc_SanPham WHERE TrangThaiXoa = 0";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM Thuoc_SanPham WHERE MaThuoc=?";
    private final String SELECT_BY_TUKHOA_SQL = "SELECT * FROM Thuoc_SanPham WHERE TenThuoc LIKE ? OR MaThuoc LIKE ?";
    private final String SELECT_THUOC_SANPHAM_DONVICOBAN_SQL =
            "SELECT DISTINCT ts.* FROM Thuoc_SanPham ts " +
                    "JOIN ChiTietDonViTinh ctdvt ON ts.MaThuoc = ctdvt.MaThuoc " +
                    "WHERE ctdvt.DonViCoBan = 1";
    private final String SELECT_THUOC_SANPHAM_DONVICOBAN_SQL_VER2 =
            "SELECT DISTINCT * FROM Thuoc_SanPham ts " +
                    "JOIN ChiTietDonViTinh ctdvt ON ts.MaThuoc = ctdvt.MaThuoc " +
                    "JOIN DonViTinh dvt ON ctdvt.MaDVT = dvt.MaDVT " +
                    "WHERE ctdvt.DonViCoBan = 1";

    private final String SELECT_THUOC_SANPHAM_DONVICOBAN_BYTUKHOA_SQL =
            "SELECT * FROM Thuoc_SanPham ts " +
            "JOIN ChiTietDonViTinh ctdvt ON ts.MaThuoc = ctdvt.MaThuoc " +
            "WHERE ctdvt.DonViCoBan = 1 AND (ts.TenThuoc LIKE ? OR ts.MaThuoc LIKE ?)";
    private final String SELECT_THUOC_SANPHAM_DONVICOBAN_BYTUKHOA_SQL_VER2 =
            "SELECT DISTINCT * FROM Thuoc_SanPham ts " +
                    "JOIN ChiTietDonViTinh ctdvt ON ts.MaThuoc = ctdvt.MaThuoc " +
                    "JOIN DonViTinh dvt ON ctdvt.MaDVT = dvt.MaDVT " +
                    "WHERE ctdvt.DonViCoBan = 1 AND (ts.TenThuoc LIKE ? OR ts.MaThuoc LIKE ?)";


    private final String SELECT_TENDVT_BYMA_SQL = "SELECT TenDonViTinh FROM ChiTietDonViTinh ctdvt JOIN DonViTinh dvt ON ctdvt.MaDVT = dvt.MaDVT WHERE MaThuoc = ? AND DonViCoBan = 1";
    private final String SELECT_TOP1_MATHUOC = "SELECT TOP 1 MaThuoc FROM Thuoc_SanPham ORDER BY MaThuoc DESC";
    private final String SELECT_THONG_TIN_GOI_Y_SQL =
            "SELECT TOP ? ts.MaThuoc, ts.TenThuoc, SUM(tspl.SoLuongTon) AS TongSoLuongTon, dvt.TenDonViTinh " +
                    "FROM Thuoc_SanPham ts " +
                    "LEFT JOIN ChiTietDonViTinh ctdvt ON ts.MaThuoc = ctdvt.MaThuoc AND ctdvt.DonViCoBan = 1 " +
                    "LEFT JOIN DonViTinh dvt ON ctdvt.MaDVT = dvt.MaDVT " +
                    "LEFT JOIN Thuoc_SP_TheoLo tspl ON ts.MaThuoc = tspl.MaThuoc " +
                    "WHERE ts.TenThuoc LIKE ? OR ts.MaThuoc LIKE ? " +
                    "GROUP BY ts.MaThuoc, ts.TenThuoc, dvt.TenDonViTinh " +
                    "ORDER BY ts.TenThuoc";
    @Override
    public boolean insert(Thuoc_SanPham e) {
        return ConnectDB.update(INSERT_SQL,e.getMaThuoc(), e.getTenThuoc(), e.getHamLuong(), e.getDonViHamLuong(), e.getDuongDung(), e.getQuyCachDongGoi(), e.getSDK_GPNK(), e.getHangSX(), e.getNuocSX(),e.getNhomDuocLy()!=null?e.getNhomDuocLy().getMaNDL():null, e.getLoaiHang()!=null?e.getLoaiHang().getMaLoaiHang():null, e.getHinhAnh(),e.getVitri()!=null?e.getVitri().getMaKe():null,0,e.isETC())>0;
    }

    @Override
    public boolean update(Thuoc_SanPham thuoc) {
        return ConnectDB.update(
                UPDATE_SQL,
                thuoc.getTenThuoc(),
                thuoc.getHamLuong(),
                thuoc.getDonViHamLuong(),
                thuoc.getDuongDung(),
                thuoc.getQuyCachDongGoi(),
                thuoc.getSDK_GPNK(),
                thuoc.getHangSX(),
                thuoc.getNuocSX(),
                thuoc.getHinhAnh(), // ⚡ Đặt đúng chỗ thứ 9
                thuoc.getLoaiHang() != null ? thuoc.getLoaiHang().getMaLoaiHang() : null,
                thuoc.getNhomDuocLy() != null ? thuoc.getNhomDuocLy().getMaNDL() : null,
                thuoc.getVitri() != null ? thuoc.getVitri().getMaKe() : null,
                thuoc.isETC(),
                thuoc.getMaThuoc()
        ) > 0;
    }


    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_SQL, keys)>0;
    }

    @Override
    public Thuoc_SanPham selectById(Object... keys) {
        return this.selectBySql(SELECT_BY_ID_SQL, keys).get(0);
    }

    @Override
    public List<Thuoc_SanPham> selectBySql(String sql, Object... args) {
        List<Thuoc_SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                Thuoc_SanPham sp = new Thuoc_SanPham();
                sp.setMaThuoc(rs.getString("MaThuoc"));
                sp.setTenThuoc(rs.getString("TenThuoc"));
                sp.setHamLuong(rs.getInt("HamLuong"));
                sp.setDonViHamLuong(rs.getString("DonViHL"));
                sp.setDuongDung(rs.getString("DuongDung"));
                sp.setQuyCachDongGoi(rs.getString("QuyCachDongGoi"));
                sp.setSDK_GPNK(rs.getString("SDK_GPNK"));
                sp.setHangSX(rs.getString("HangSX"));
                sp.setNuocSX(rs.getString("NuocSX"));
                sp.setNhomDuocLy(new NhomDuocLy_Dao().selectById(rs.getString("MaNDL")));
                sp.setLoaiHang(new LoaiHang_Dao().selectById(rs.getString("MaLoaiHang")));
                sp.setHinhAnh(rs.getBytes("HinhAnh"));
                sp.setVitri(new KeHang_Dao().selectById(rs.getString("ViTri")));
                sp.setETC(rs.getBoolean("ETC"));
                list.add(sp);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Thuoc_SanPham> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<String> getAllLoaiHang() {
        String sql = "SELECT TenLH FROM LoaiHang";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql);
            while (rs.next()) {
                list.add(rs.getString("TenLH"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<String> getAllXuatXu() {
        String sql = "SELECT DISTINCT NuocSX FROM Thuoc_SanPham";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql);
            while (rs.next()) {
                list.add(rs.getString("NuocSX"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<Thuoc_SanPham> selectByTuKhoa(String tuKhoa) {
        return this.selectBySql(SELECT_BY_TUKHOA_SQL, "%" + tuKhoa + "%", "%" + tuKhoa + "%");
    }

    // Only join ChiTietDonViTinh (unit info)
    public List<Thuoc_SanPham> selectAllSLTheoDonViCoBan_ChiTietDVT() {
        List<Thuoc_SanPham> list = this.selectBySql(SELECT_THUOC_SANPHAM_DONVICOBAN_SQL);
        ChiTietDonViTinh_Dao ctdvtDao = new ChiTietDonViTinh_Dao();
        for (Thuoc_SanPham sp : list) {
            List<ChiTietDonViTinh> dsCTDVT = ctdvtDao.selectByMaThuoc(sp.getMaThuoc());
            sp.setDsCTDVT(dsCTDVT);
        }
        return list;
    }
    // Only join ChiTietDonViTinh (unit info)
    public List<Thuoc_SanPham> selectAllSLTheoDonViCoBan_ChiTietDVT_Ver2() {
        List<Thuoc_SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(SELECT_THUOC_SANPHAM_DONVICOBAN_SQL_VER2);
            while (rs.next()) {
                Thuoc_SanPham sp = new Thuoc_SanPham();
                String maThuoc = rs.getString("MaThuoc");
                sp.setMaThuoc(maThuoc);
                sp.setTenThuoc(rs.getString("TenThuoc"));
                sp.setHamLuong(rs.getInt("HamLuong"));
                sp.setDonViHamLuong(rs.getString("DonViHL"));
                sp.setDuongDung(rs.getString("DuongDung"));
                sp.setQuyCachDongGoi(rs.getString("QuyCachDongGoi"));
                sp.setSDK_GPNK(rs.getString("SDK_GPNK"));
                sp.setHangSX(rs.getString("HangSX"));
                sp.setNuocSX(rs.getString("NuocSX"));
                sp.setNhomDuocLy(new NhomDuocLy_Dao().selectById(rs.getString("MaNDL")));
                sp.setLoaiHang(new LoaiHang_Dao().selectById(rs.getString("MaLoaiHang")));
                sp.setHinhAnh(rs.getBytes("HinhAnh"));
                sp.setVitri(new KeHang_Dao().selectById(rs.getString("ViTri")));
                sp.setDvcb(new ChiTietDonViTinh_Dao().selectById(maThuoc,rs.getString("MaDVT")));
                list.add(sp);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // By keyword, only join ChiTietDonViTinh
    public List<Thuoc_SanPham> selectSLTheoDonViCoBanByTuKhoa_ChiTietDVT(String tuKhoa) {
        List<Thuoc_SanPham> list = this.selectBySql(SELECT_THUOC_SANPHAM_DONVICOBAN_BYTUKHOA_SQL, "%" + tuKhoa + "%", "%" + tuKhoa + "%");
        ChiTietDonViTinh_Dao ctdvtDao = new ChiTietDonViTinh_Dao();
        for (Thuoc_SanPham sp : list) {
            List<ChiTietDonViTinh> dsCTDVT = ctdvtDao.selectByMaThuoc(sp.getMaThuoc());
            sp.setDsCTDVT(dsCTDVT);
        }
        return list;
    }

    // By keyword, only join ChiTietDonViTinh
    public List<Thuoc_SanPham> selectSLTheoDonViCoBanByTuKhoa_ChiTietDVT_Ver2(String tuKhoa) {
        List<Thuoc_SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(SELECT_THUOC_SANPHAM_DONVICOBAN_BYTUKHOA_SQL_VER2,  "%" + tuKhoa + "%", "%" + tuKhoa + "%");
            while (rs.next()) {
                Thuoc_SanPham sp = new Thuoc_SanPham();
                String maThuoc = rs.getString("MaThuoc");
                sp.setMaThuoc(maThuoc);
                sp.setTenThuoc(rs.getString("TenThuoc"));
                sp.setHamLuong(rs.getInt("HamLuong"));
                sp.setDonViHamLuong(rs.getString("DonViHL"));
                sp.setDuongDung(rs.getString("DuongDung"));
                sp.setQuyCachDongGoi(rs.getString("QuyCachDongGoi"));
                sp.setSDK_GPNK(rs.getString("SDK_GPNK"));
                sp.setHangSX(rs.getString("HangSX"));
                sp.setNuocSX(rs.getString("NuocSX"));
                sp.setNhomDuocLy(new NhomDuocLy_Dao().selectById(rs.getString("MaNDL")));
                sp.setLoaiHang(new LoaiHang_Dao().selectById(rs.getString("MaLoaiHang")));
                sp.setHinhAnh(rs.getBytes("HinhAnh"));
                sp.setVitri(new KeHang_Dao().selectById(rs.getString("ViTri")));
                sp.setDvcb(new ChiTietDonViTinh_Dao().selectById(maThuoc,rs.getString("MaDVT")));
                list.add(sp);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public String getTenDVTByMaThuoc(String maThuoc) {
        String tenDVT = null;
        try {
            ResultSet rs = ConnectDB.query(SELECT_TENDVT_BYMA_SQL, maThuoc);
            if (rs.next()) {
                tenDVT = rs.getString("TenDonViTinh");
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tenDVT;
    }
    public List<String> timTheoTenChiTietAll(String keyword, int limit) {
        if (keyword == null) keyword = "";
        keyword = keyword.trim();
        if (keyword.isEmpty()) return new ArrayList<>();

        if (limit <= 0) limit = 10;
        if (limit > 50) limit = 50;

        // chèn trực tiếp limit vào SQL — vì limit đã được kiểm soát ở trên
        String sql = "SELECT TOP " + limit + " " +
                "ts.MaThuoc, ts.TenThuoc, COALESCE(SUM(tspl.SoLuongTon), 0) AS TongSoLuongTon " +
                "FROM Thuoc_SanPham ts " +
                "LEFT JOIN Thuoc_SP_TheoLo tspl ON ts.MaThuoc = tspl.MaThuoc " +
                "WHERE ts.TenThuoc COLLATE SQL_Latin1_General_CP1_CI_AI LIKE ? " +
                "   OR ts.MaThuoc COLLATE SQL_Latin1_General_CP1_CI_AI LIKE ? " +
                "GROUP BY ts.MaThuoc, ts.TenThuoc " +
                "ORDER BY ts.TenThuoc";

        List<String> suggestions = new ArrayList<>();
        try (ResultSet rs = ConnectDB.query(sql, "%" + keyword + "%", "%" + keyword + "%")) {
            ChiTietDonViTinh_Dao ctdvtDao = new ChiTietDonViTinh_Dao();

            while (rs.next()) {
                String maThuoc = rs.getString("MaThuoc");
                String tenThuoc = rs.getString("TenThuoc");
                int tongTonCoBan = rs.getInt("TongSoLuongTon");

                for (ChiTietDonViTinh ctdvt : ctdvtDao.selectByMaThuoc(maThuoc)) {
                    double heSo = ctdvt.getHeSoQuyDoi();
                    if (heSo <= 0) continue;
                    int soLuongTheoDVT = (int) Math.floor(tongTonCoBan / heSo);
                    String tenDVT = (ctdvt.getDvt() != null && ctdvt.getDvt().getTenDonViTinh() != null)
                            ? ctdvt.getDvt().getTenDonViTinh()
                            : "ĐVT";
                    suggestions.add(tenThuoc + " | Số lượng tồn: " + soLuongTheoDVT + " " + tenDVT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tìm kiếm chi tiết thuốc: " + e.getMessage(), e);
        }
        return suggestions;
    }



    public List<String> timTheoTenChiTiet(String keyword, int limit) {
        if (keyword == null) keyword = "";
        keyword = keyword.trim();
        if (keyword.isEmpty()) return new ArrayList<>();

        // Clamp limit to number of products to fetch
        if (limit <= 0) limit = 10;
        if (limit > 50) limit = 50;

        // Lấy tổng tồn theo sản phẩm, không join đơn vị để tránh nhân bản dữ liệu
        String sql =
                "SELECT ts.MaThuoc, ts.TenThuoc, COALESCE(SUM(tspl.SoLuongTon), 0) AS TongSoLuongTon " +
                        "FROM Thuoc_SanPham ts " +
                        "LEFT JOIN Thuoc_SP_TheoLo tspl ON ts.MaThuoc = tspl.MaThuoc " +
                        "WHERE ts.TenThuoc LIKE ? OR ts.MaThuoc LIKE ? " +
                        "GROUP BY ts.MaThuoc, ts.TenThuoc " +
                        "ORDER BY ts.TenThuoc " +
                        "OFFSET 0 ROWS FETCH NEXT " + limit + " ROWS ONLY";

        List<String> suggestions = new ArrayList<>();
        try (ResultSet rs = ConnectDB.query(sql, "%" + keyword + "%", "%" + keyword + "%")) {
            ChiTietDonViTinh_Dao ctdvtDao = new ChiTietDonViTinh_Dao();

            while (rs.next()) {
                String maThuoc = rs.getString("MaThuoc");
                String tenThuoc = rs.getString("TenThuoc");
                int tongTonCoBan = rs.getInt("TongSoLuongTon"); // tồn theo đơn vị cơ bản

                // Lấy tất cả DVT của thuốc và sắp xếp: đơn vị lớn -> nhỏ (cơ bản ở cuối)
                List<ChiTietDonViTinh> dsCTDVT = ctdvtDao.selectByMaThuoc(maThuoc);
                dsCTDVT.sort((a, b) -> {
                    int cmp = Double.compare(b.getHeSoQuyDoi(), a.getHeSoQuyDoi());
                    if (cmp != 0) return cmp;
                    // Đưa đơn vị cơ bản (DonViCoBan=true, hệ số nhỏ nhất) về cuối nếu hệ số bằng nhau
                    return Boolean.compare(a.isDonViCoBan(), b.isDonViCoBan());
                });

                for (ChiTietDonViTinh ctdvt : dsCTDVT) {
                    double heSo = ctdvt.getHeSoQuyDoi(); // ví dụ: 1 hộp = 10 vỉ => heSo= số đơn vị cơ bản/1 đơn vị này
                    if (heSo <= 0) continue;

                    int soLuongTheoDVT = (int) Math.floor(tongTonCoBan / heSo);
                    if (soLuongTheoDVT <= 0) continue; // không hiển thị dòng 0

                    String tenDVT = (ctdvt.getDvt() != null && ctdvt.getDvt().getTenDonViTinh() != null)
                            ? ctdvt.getDvt().getTenDonViTinh()
                            : "ĐVT";

                    // Mỗi đơn vị là một dòng: "<Tên thuốc> | Số lượng tồn: <n> <đvt>"
                    suggestions.add(tenThuoc + " | Số lượng tồn: " + soLuongTheoDVT + " " + tenDVT);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm chi tiết thuốc: " + e.getMessage(), e);
        }
        return suggestions;
    }
    public int getTongTonCoBan(String maThuoc) {
        String sql = "SELECT COALESCE(SUM(SoLuongTon), 0) FROM Thuoc_SP_TheoLo WHERE MaThuoc = ?";
        try (ResultSet rs = ConnectDB.query(sql, maThuoc)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi lấy tổng tồn: " + e.getMessage(), e);
        }
    }

    public List<String> timTheoTen(String keyword, int limit) {
        if (keyword == null) keyword = "";
        keyword = keyword.trim();
        if (keyword.isEmpty() || limit <= 0) return new ArrayList<>();

        String sql = "SELECT TenThuoc " +
                "FROM Thuoc_SanPham " +
                "WHERE TenThuoc LIKE ? OR MaThuoc LIKE ? " +
                "ORDER BY TenThuoc " +
                "OFFSET 0 ROWS FETCH NEXT " + limit + " ROWS ONLY";

        List<String> names = new ArrayList<>();
        try (ResultSet rs = ConnectDB.query(sql, "%" + keyword + "%", "%" + keyword + "%")) {
            while (rs.next()) {
                names.add(rs.getString("TenThuoc"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return names;
    }



    public List<String> layDanhSachThuocTheoKe(String maKe) {
        List<String> danhSach = new ArrayList<>();
        String sql = "SELECT TenThuoc FROM Thuoc_SanPham WHERE ViTri = ?";
        try {
            ResultSet rs = ConnectDB.query(sql, maKe);
            while (rs.next()) {
                danhSach.add(rs.getString("TenThuoc"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public String getTenLoaiHangByMaThuoc(String maThuoc) {
        String tenLoaiHang = null;
        String sql = "SELECT lh.TenLH FROM Thuoc_SanPham ts JOIN LoaiHang lh ON ts.MaLoaiHang = lh.MaLoaiHang WHERE ts.MaThuoc = ?";
        try {
            ResultSet rs = ConnectDB.query(sql, maThuoc);
            if (rs.next()) {
                tenLoaiHang = rs.getString("TenLH");
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tenLoaiHang;
    }
    public List<String> layDanhSachThuocTheoNDL(String maKe) {
        List<String> danhSach = new ArrayList<>();
        String sql = "SELECT TenThuoc FROM Thuoc_SanPham WHERE MaNDL = ?";
        try {
            ResultSet rs = ConnectDB.query(sql, maKe);
            while (rs.next()) {
                danhSach.add(rs.getString("TenThuoc"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public String generatekeyThuocSanPham() {
        String key = "TS001";
        try {
            String lastKey = ConnectDB.queryTaoMa(SELECT_TOP1_MATHUOC);
                if (lastKey != null && lastKey.startsWith("TS")) {
                    int stt = Integer.parseInt(lastKey.substring(2));
                    stt++;
                    key = String.format("TS%03d", stt);
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    public String insertThuocProc(Thuoc_SanPham sp) {
        String maMoi = null;
        try {
            // Gọi stored procedure thêm thuốc
            ResultSet rs = ConnectDB.query(
                    "{CALL sp_InsertThuoc_SanPham(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}",
                    sp.getTenThuoc(),
                    sp.getHamLuong(),
                    sp.getDonViHamLuong(),
                    sp.getDuongDung(),
                    sp.getQuyCachDongGoi(),
                    sp.getSDK_GPNK(),
                    sp.getHangSX(),
                    sp.getNuocSX(),
                    (sp.getLoaiHang() != null) ? sp.getLoaiHang().getMaLoaiHang() : null,
                    (sp.getNhomDuocLy() != null) ? sp.getNhomDuocLy().getMaNDL() : null,
                    (sp.getVitri() != null) ? sp.getVitri().getMaKe() : null
            );

            if (rs.next()) {
                maMoi = rs.getString(1);
            }

            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return maMoi;
    }

    public boolean xoaThuoc_SanPham(String maThuoc) {
        String sql = "UPDATE Thuoc_SanPham SET TrangThaiXoa = 1 WHERE MaThuoc = ?";
        return ConnectDB.update(sql, maThuoc) > 0;
    }
}
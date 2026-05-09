package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.TonKhoRepository;
import com.example.pharmacy.common.model.ChiTietDonViTinh;
import com.example.pharmacy.common.model.DonViTinh;
import com.example.pharmacy.common.model.KeHang;
import com.example.pharmacy.common.model.LoaiHang;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLo;
import com.example.pharmacy.common.model.Thuoc_SanPham;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcTonKhoRepository extends AbstractJdbcRepository implements TonKhoRepository {
    private static final String SELECT_ALL_LOTS_SQL = """
            SELECT lo.MaPN,
                   lo.MaThuoc,
                   lo.MaLH,
                   lo.SoLuongTon,
                   lo.NSX,
                   lo.HSD,
                   ts.TenThuoc,
                   ts.ViTri,
                   ts.MaLoaiHang,
                   kh.TenKe,
                   lh.TenLH,
                   dvt.MaDVT AS BaseMaDVT,
                   dvt.TenDonViTinh AS BaseTenDVT
            FROM Thuoc_SP_TheoLo lo
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = lo.MaThuoc
            LEFT JOIN KeHang kh ON kh.MaKe = ts.ViTri
            LEFT JOIN LoaiHang lh ON lh.MaLoaiHang = ts.MaLoaiHang
            LEFT JOIN ChiTietDonViTinh ctdvt ON ctdvt.MaThuoc = ts.MaThuoc AND ctdvt.DonViCoBan = 1
            LEFT JOIN DonViTinh dvt ON dvt.MaDVT = ctdvt.MaDVT
            ORDER BY ts.TenThuoc ASC, lo.MaLH ASC
            """;
    private static final String UPDATE_LOT_QUANTITY_SQL = """
            UPDATE Thuoc_SP_TheoLo
            SET SoLuongTon = ?
            WHERE MaLH = ?
            """;

    public JdbcTonKhoRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public List<Thuoc_SP_TheoLo> findAllLots() {
        Connection connection = null;
        List<Thuoc_SP_TheoLo> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_LOTS_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapLot(resultSet));
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load stock lots.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean updateLotQuantity(Thuoc_SP_TheoLo thuocTheoLo) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_LOT_QUANTITY_SQL)) {
                statement.setInt(1, thuocTheoLo.getSoLuongTon());
                statement.setString(2, thuocTheoLo.getMaLH());
                return statement.executeUpdate() > 0;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not update stock lot quantity.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private Thuoc_SP_TheoLo mapLot(ResultSet resultSet) throws Exception {
        Thuoc_SanPham thuoc = new Thuoc_SanPham();
        thuoc.setMaThuoc(resultSet.getString("MaThuoc"));
        thuoc.setTenThuoc(resultSet.getString("TenThuoc"));

        String maKe = resultSet.getString("ViTri");
        if (maKe != null) {
            KeHang keHang = new KeHang();
            keHang.setMaKe(maKe);
            keHang.setTenKe(resultSet.getString("TenKe"));
            thuoc.setVitri(keHang);
        }

        String maLoaiHang = resultSet.getString("MaLoaiHang");
        if (maLoaiHang != null) {
            LoaiHang loaiHang = new LoaiHang();
            loaiHang.setMaLoaiHang(maLoaiHang);
            loaiHang.setTenLoaiHang(resultSet.getString("TenLH"));
            thuoc.setLoaiHang(loaiHang);
        }

        String maDvt = resultSet.getString("BaseMaDVT");
        if (maDvt != null) {
            DonViTinh donViTinh = new DonViTinh();
            donViTinh.setMaDVT(maDvt);
            donViTinh.setTenDonViTinh(resultSet.getString("BaseTenDVT"));
            ChiTietDonViTinh chiTietDonViTinh = new ChiTietDonViTinh();
            chiTietDonViTinh.setThuoc(thuoc);
            chiTietDonViTinh.setDvt(donViTinh);
            chiTietDonViTinh.setDonViCoBan(true);
            thuoc.getDsCTDVT().add(chiTietDonViTinh);
        }

        Thuoc_SP_TheoLo lot = new Thuoc_SP_TheoLo();
        lot.setMaLH(resultSet.getString("MaLH"));
        lot.setSoLuongTon(resultSet.getInt("SoLuongTon"));
        Date nsx = resultSet.getDate("NSX");
        Date hsd = resultSet.getDate("HSD");
        lot.setNsx(nsx);
        lot.setHsd(hsd);
        lot.setThuoc(thuoc);
        return lot;
    }
}

package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.TonKhoRepository;
import com.example.pharmacy.common.model.ChiTietDonViTinhDto;
import com.example.pharmacy.common.model.DonViTinhDto;
import com.example.pharmacy.common.model.KeHangDto;
import com.example.pharmacy.common.model.LoaiHangDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcTonKhoRepository extends AbstractJdbcRepository implements TonKhoRepository {
    private static final String SELECT_ALL_LOTS_SQL = """
            SELECT lo.MaLH,
                   lo.SoLuongTon,
                   lo.NSX,
                   lo.HSD,
                   ts.MaThuoc,
                   ts.TenThuoc,
                   ts.ViTri,
                   ts.MaLoaiHang,
                   ts.MaNDL,
                   kh.MaKe,
                   kh.TenKe,
                   lh.MaLoaiHang,
                   lh.TenLH,
                   ctdvt.MaDVT,
                   dvt.TenDonViTinh
            FROM Thuoc_SP_TheoLo lo
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = lo.MaThuoc
            LEFT JOIN KeHang kh ON kh.MaKe = ts.ViTri
            LEFT JOIN LoaiHang lh ON lh.MaLoaiHang = ts.MaLoaiHang
            LEFT JOIN ChiTietDonViTinh ctdvt ON ctdvt.MaThuoc = ts.MaThuoc AND ctdvt.DonViCoBan = 1
            LEFT JOIN DonViTinh dvt ON dvt.MaDVT = ctdvt.MaDVT
            ORDER BY ts.TenThuoc, lo.MaLH
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
    public List<Thuoc_SP_TheoLoDto> findAllLots() {
        Connection connection = null;
        List<Thuoc_SP_TheoLoDto> list = new ArrayList<>();
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
    public boolean updateLotQuantity(Thuoc_SP_TheoLoDto thuocTheoLo) {
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

    private Thuoc_SP_TheoLoDto mapLot(ResultSet resultSet) throws Exception {
        Thuoc_SanPhamDto thuoc = new Thuoc_SanPhamDto();
        thuoc.setMaThuoc(resultSet.getString("MaThuoc"));
        thuoc.setTenThuoc(resultSet.getString("TenThuoc"));

        String maKe = resultSet.getString("ViTri");
        if (maKe != null) {
            KeHangDto keHang = new KeHangDto();
            keHang.setMaKe(maKe);
            keHang.setTenKe(resultSet.getString("TenKe"));
            thuoc.setVitri(keHang);
        }

        String maLoaiHang = resultSet.getString("MaLoaiHang");
        if (maLoaiHang != null) {
            LoaiHangDto loaiHang = new LoaiHangDto();
            loaiHang.setMaLoaiHang(maLoaiHang);
            loaiHang.setTenLoaiHang(resultSet.getString("TenLH"));
            thuoc.setLoaiHang(loaiHang);
        }

        String maDvt = resultSet.getString("BaseMaDVT");
        if (maDvt != null) {
            DonViTinhDto donViTinh = new DonViTinhDto();
            donViTinh.setMaDVT(maDvt);
            donViTinh.setTenDonViTinh(resultSet.getString("BaseTenDVT"));
            ChiTietDonViTinhDto chiTietDonViTinh = new ChiTietDonViTinhDto();
            chiTietDonViTinh.setThuoc(thuoc);
            chiTietDonViTinh.setDvt(donViTinh);
            chiTietDonViTinh.setDonViCoBan(true);
            thuoc.getDsCTDVT().add(chiTietDonViTinh);
        }

        Thuoc_SP_TheoLoDto lot = new Thuoc_SP_TheoLoDto();
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

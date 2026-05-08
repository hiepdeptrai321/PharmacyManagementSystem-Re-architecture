package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon;

import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import com.example.pharmacymanagementsystem_qlht.model.HoaDon;
import com.example.pharmacymanagementsystem_qlht.service.ApDungKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.service.DichVuKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.service.HoaDonService;
import com.example.pharmacymanagementsystem_qlht.service.ThuocService;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoaDon.ChiTietHoaDon_GUI;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChiTietHoaDon_Ctrl extends Application {
    public TableView<ChiTietHoaDon> tblChiTietHoaDon;
    public TableColumn<ChiTietHoaDon, Number> colNSTT;
    public TableColumn<ChiTietHoaDon, String> colNTen;
    public TableColumn<ChiTietHoaDon, Integer> colNSL;
    public TableColumn<ChiTietHoaDon, String> colNDonVi;
    public TableColumn<ChiTietHoaDon, Double> colNDonGia;
    public TableColumn<ChiTietHoaDon, Double> colNChietKhau;
    public TableColumn<ChiTietHoaDon, Double> colNThanhTien;
    public TableColumn<ChiTietHoaDon, String> colMaLoHang;
    public Label lblMaHoaDonValue;
    public Label lblNgayLapValue;
    public Label lblTenNhanVienValue;
    public Label lblTenKhachHangValue;
    public Label lblSDTKhachHangValue;
    public Label lblGhiChuValue;
    public Label lblTongTienHang;
    public Label lblGiamTheoSP;
    public Label lblGiamTheoHD;
    public Label lblVAT;
    public Label lblTongThanhToan;
    public Button btnDong;
    public Button btnInHoaDon;
    public Label lblLoaiHoaDon;
    public Label lblMaDonThuocTitle;
    public Label lblMaDonThuocValue;

    private HoaDon hoaDon;
    private final HoaDonService hoaDonService = new HoaDonService();
    private final DichVuKhuyenMai dichVuKhuyenMai = new DichVuKhuyenMai();
    private final ThuocService thuocService = new ThuocService();
    private final Map<String, String> tenThuocCache = new HashMap<>();

    @Override
    public void start(Stage stage) {
        ChiTietHoaDon_GUI gui = new ChiTietHoaDon_GUI();
        gui.showWithController(stage, this);
        initialize();
    }

    public void initialize() {
        btnDong.setOnAction(e -> ((Stage) btnDong.getScene().getWindow()).close());
        btnInHoaDon.setOnAction(e -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.INFORMATION
            );
            alert.setTitle("Thong bao");
            alert.setHeaderText(null);
            alert.setContentText("Chuc nang in hoa don se duoc hoan thien tiep trong batch sau.");
            alert.showAndWait();
        });
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
        hienThiThongTin();
    }

    public void xuLyXuatPDF() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION
        );
        alert.setTitle("Thong bao");
        alert.setHeaderText(null);
        alert.setContentText("Chuc nang xuat PDF hoa don se duoc hoan thien tiep trong batch sau.");
        alert.showAndWait();
    }

    private void hienThiThongTin() {
        if (hoaDon == null) {
            return;
        }

        lblMaHoaDonValue.setText(safe(hoaDon.getMaHD()));
        lblNgayLapValue.setText(hoaDon.getNgayLap() == null ? "" : new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(hoaDon.getNgayLap()));
        lblTenNhanVienValue.setText(hoaDon.getMaNV() == null ? "" : safe(hoaDon.getMaNV().getTenNV()));
        if (hoaDon.getMaKH() != null) {
            lblTenKhachHangValue.setText(safe(hoaDon.getMaKH().getTenKH()));
            lblSDTKhachHangValue.setText(safe(hoaDon.getMaKH().getSdt()));
        } else {
            lblTenKhachHangValue.setText("Khach le");
            lblSDTKhachHangValue.setText("");
        }

        boolean isEtc = "ETC".equalsIgnoreCase(hoaDon.getLoaiHoaDon());
        lblLoaiHoaDon.setText(isEtc ? "Hoa don ke don (ETC)" : "Hoa don khong ke don (OTC)");
        lblMaDonThuocValue.setText(isEtc ? safe(hoaDon.getMaDonThuoc()) : "");
        lblMaDonThuocValue.setVisible(isEtc);
        lblMaDonThuocTitle.setVisible(isEtc);

        List<ChiTietHoaDon> list = hoaDonService.findDetailsByMaHD(hoaDon.getMaHD());
        tblChiTietHoaDon.setItems(FXCollections.observableArrayList(list));
        configureTable();
        updateSummary(list);
    }

    private void configureTable() {
        colNSTT.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(tblChiTietHoaDon.getItems().indexOf(cd.getValue()) + 1));
        colNTen.setCellValueFactory(cell -> {
            ChiTietHoaDon row = cell.getValue();
            String ten = row.getLoHang() != null && row.getLoHang().getThuoc() != null
                    ? safe(row.getLoHang().getThuoc().getTenThuoc()) + giftSuffix(row)
                    : "";
            return new SimpleStringProperty(ten);
        });
        colMaLoHang.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getLoHang() == null ? "" : safe(cell.getValue().getLoHang().getMaLH())
        ));
        colNSL.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colNDonVi.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getDvt() == null ? "" : safe(cell.getValue().getDvt().getTenDonViTinh())
        ));
        colNDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colNDonGia.setCellFactory(tc -> moneyCell());
        colNChietKhau.setCellValueFactory(new PropertyValueFactory<>("giamGia"));
        colNChietKhau.setCellFactory(tc -> moneyCell());
        colNThanhTien.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(
                Math.max(0, cell.getValue().getSoLuong() * cell.getValue().getDonGia() - cell.getValue().getGiamGia())
        ));
        colNThanhTien.setCellFactory(tc -> moneyCell());
    }

    private javafx.scene.control.TableCell<ChiTietHoaDon, Double> moneyCell() {
        return new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : formatVND(item == null ? 0 : item));
                setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
            }
        };
    }

    private void updateSummary(List<ChiTietHoaDon> details) {
        BigDecimal tongHang = BigDecimal.ZERO;
        BigDecimal giamTheoSp = BigDecimal.ZERO;
        for (ChiTietHoaDon row : details) {
            BigDecimal line = BigDecimal.valueOf(row.getDonGia()).multiply(BigDecimal.valueOf(row.getSoLuong()));
            tongHang = tongHang.add(line);
            giamTheoSp = giamTheoSp.add(BigDecimal.valueOf(Math.max(0, row.getGiamGia())));
        }

        BigDecimal baseSauGiamSp = tongHang.subtract(giamTheoSp).max(BigDecimal.ZERO);
        BigDecimal giamTheoHd = dichVuKhuyenMai.apDungChoHoaDon(
                baseSauGiamSp,
                hoaDon.getNgayLap() == null ? java.time.LocalDate.now() : hoaDon.getNgayLap().toLocalDateTime().toLocalDate()
        ).getDiscount().setScale(0, RoundingMode.HALF_UP);
        BigDecimal baseSauHd = baseSauGiamSp.subtract(giamTheoHd).max(BigDecimal.ZERO);
        BigDecimal vat = baseSauHd.multiply(new BigDecimal("0.05")).setScale(0, RoundingMode.HALF_UP);
        BigDecimal tongThanhToan = baseSauHd.add(vat);

        lblTongTienHang.setText(formatVND(tongHang.doubleValue()));
        lblGiamTheoSP.setText(formatVND(giamTheoSp.doubleValue()));
        lblGiamTheoHD.setText(formatVND(giamTheoHd.doubleValue()));
        lblVAT.setText(formatVND(vat.doubleValue()));
        lblTongThanhToan.setText(formatVND(tongThanhToan.doubleValue()));
        lblGhiChuValue.setText("");
    }

    private String giftSuffix(ChiTietHoaDon row) {
        if (row == null || row.getLoHang() == null || row.getLoHang().getThuoc() == null) {
            return "";
        }
        String maThuoc = row.getLoHang().getThuoc().getMaThuoc();
        int soLuong = Math.max(0, row.getSoLuong());
        if (maThuoc == null || soLuong <= 0) {
            return "";
        }
        ApDungKhuyenMai apDung = dichVuKhuyenMai.apDungChoSP(
                maThuoc,
                soLuong,
                BigDecimal.valueOf(Math.max(0, row.getDonGia())),
                hoaDon != null && hoaDon.getNgayLap() != null
                        ? hoaDon.getNgayLap().toLocalDateTime().toLocalDate()
                        : java.time.LocalDate.now()
        );
        if (apDung.getFreeItems().isEmpty()) {
            return "";
        }

        StringBuilder gifts = new StringBuilder();
        for (String maTang : apDung.getFreeItems().keySet()) {
            String ten = getTenThuoc(maTang);
            if (!ten.isBlank()) {
                if (gifts.length() > 0) {
                    gifts.append(", ");
                }
                gifts.append(ten);
            }
        }
        return gifts.length() == 0 ? "" : " (co tang kem " + gifts + ")";
    }

    private String getTenThuoc(String maThuoc) {
        if (maThuoc == null) {
            return "";
        }
        return tenThuocCache.computeIfAbsent(maThuoc, key ->
                thuocService.findAll().stream()
                        .filter(thuoc -> key.equalsIgnoreCase(thuoc.getMaThuoc()))
                        .map(thuoc -> safe(thuoc.getTenThuoc()))
                        .findFirst()
                        .orElse("")
        );
    }

    private String formatVND(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        decimalFormat.setGroupingUsed(true);
        return decimalFormat.format(Math.max(0, Math.round(value))) + " VND";
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}

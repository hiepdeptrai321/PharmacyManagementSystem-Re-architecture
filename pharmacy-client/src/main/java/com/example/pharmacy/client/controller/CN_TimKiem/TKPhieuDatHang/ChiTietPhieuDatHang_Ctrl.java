package com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuDatHang;

import com.example.pharmacy.common.model.ChiTietPhieuDatHangDto;
import com.example.pharmacy.common.model.DonViTinhDto;
import com.example.pharmacy.common.model.PhieuDatHangDto;
import com.example.pharmacy.client.service.DonViTinhService;
import com.example.pharmacy.client.service.PhieuDatHangService;
import com.example.pharmacy.client.session.SessionContext;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.example.pharmacy.client.TienIch.TuyChinhAlert.hien;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.WARNING;

public class ChiTietPhieuDatHang_Ctrl {
    public static PhieuDatHangDto phieuDatHang;
    public TableColumn<ChiTietPhieuDatHangDto, Number> colSTT;
    public TableColumn<ChiTietPhieuDatHangDto, String> colTenSP;
    public TableColumn<ChiTietPhieuDatHangDto, Integer> colSoLuong;
    public TableColumn<ChiTietPhieuDatHangDto, String> colDonVi;
    public TableColumn<ChiTietPhieuDatHangDto, Double> colDonGia;
    public TableColumn<ChiTietPhieuDatHangDto, String> colNhaCungCap;
    public TableColumn<ChiTietPhieuDatHangDto, String> colThanhTien;
    public TableColumn<ChiTietPhieuDatHangDto, String> colTT;
    public TableView<ChiTietPhieuDatHangDto> tblChiTietPhieuDat;
    public Label lblMaPhieuDatValue;
    public Label lblNgayLapValue;
    public Label lblTenNhanVienValue;
    public Label lblTenNCCValue;
    public Label lblSDTNCCValue;
    public Label lblGhiChuValue;
    public Label lblTongTienDatValue;
    public Label lblChietKhauPDValue;
    public Label lblThueVATValue;
    public Label lblTongTienPhaiDatValue;
    public Label lblPTTTValue;
    public Label lblTienDaThanhToanValue;
    public Label lblTienConLaiValue;
    public Label lbTT;
    public Button btnInPhieuDat;
    public Button btnLapHoaDon;
    public Button btnDong;
    public final NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

    private final PhieuDatHangService phieuDatHangService = new PhieuDatHangService();
    private final DonViTinhService donViTinhService = new DonViTinhService();

    public void initialize() {
        if (btnDong != null) {
            btnDong.setOnAction(e -> ((Stage) btnDong.getScene().getWindow()).close());
        }
        btnLapHoaDon.setOnAction(e -> onLapHoaDon());
        hienThiThongTin();
    }

    public void setPhieuDatHang(PhieuDatHangDto pDat) {
        phieuDatHang = pDat;
    }

    private void hienThiThongTin() {
        if (phieuDatHang == null) {
            return;
        }

        lblMaPhieuDatValue.setText(phieuDatHang.getMaPDat());
        if (phieuDatHang.getNgayLap() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            lblNgayLapValue.setText(formatter.format(phieuDatHang.getNgayLap()));
        } else {
            lblNgayLapValue.setText("Khong ro");
        }

        lblTenNhanVienValue.setText(phieuDatHang.getNhanVien() == null ? "" : phieuDatHang.getNhanVien().getTenNV());
        if (phieuDatHang.getKhachHang() != null) {
            lblTenNCCValue.setText(phieuDatHang.getKhachHang().getTenKH());
            lblSDTNCCValue.setText(phieuDatHang.getKhachHang().getSdt());
        } else {
            lblTenNCCValue.setText("");
            lblSDTNCCValue.setText("");
        }

        lblGhiChuValue.setText(phieuDatHang.getGhiChu() == null ? "" : phieuDatHang.getGhiChu());
        Platform.runLater(() -> lbTT.setText(getTrangThaiText(phieuDatHang.getTrangthai())));

        List<ChiTietPhieuDatHangDto> list = phieuDatHangService.findDetailsByMaPhieuDat(phieuDatHang.getMaPDat());
        tblChiTietPhieuDat.getItems().setAll(list);

        colSTT.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(tblChiTietPhieuDat.getItems().indexOf(cellData.getValue()) + 1)
        );
        colTenSP.setCellValueFactory(cel -> new SimpleStringProperty(cel.getValue().getThuoc().getTenThuoc()));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colDonVi.setCellValueFactory(cel -> {
            DonViTinhDto donViTinh = donViTinhService.findById(cel.getValue().getDvt());
            return new SimpleStringProperty(donViTinh == null ? "" : donViTinh.getTenDonViTinh());
        });
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colNhaCungCap.setCellValueFactory(cel -> new SimpleStringProperty(String.format("%.2f", cel.getValue().getGiamGia())));
        colThanhTien.setCellValueFactory(cel -> {
            ChiTietPhieuDatHangDto item = cel.getValue();
            double thanhTien = item.getSoLuong() * item.getDonGia();
            if (item.getGiamGia() != 0) {
                thanhTien = thanhTien * (1 - item.getGiamGia() / 100.0);
            }
            return new SimpleStringProperty(String.format("%.2f", thanhTien));
        });
        colTT.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isTrangThai() ? "San hang" : "Het hang"));

        double tongTruocChietKhau = 0.0;
        double tongGiamGia = 0.0;
        for (ChiTietPhieuDatHangDto item : list) {
            double line = item.getSoLuong() * item.getDonGia();
            tongTruocChietKhau += line;
            tongGiamGia += item.getGiamGia() == 0 ? 0 : line * item.getGiamGia() / 100.0;
        }

        double tongSauChietKhau = tongTruocChietKhau - tongGiamGia;
        double thueVat = 0.0;
        double tongPhaiDat = tongSauChietKhau + thueVat;

        lblTongTienDatValue.setText(String.format("%.2f VND", tongTruocChietKhau));
        lblChietKhauPDValue.setText(String.format("-%.2f VND", tongGiamGia));
        lblThueVATValue.setText(String.format("%.2f VND", thueVat));
        lblTongTienPhaiDatValue.setText(String.format("%.2f VND", tongPhaiDat));
        lblTienDaThanhToanValue.setText(String.format("%.2f VND", phieuDatHang.getSoTienCoc()));
        lblTienConLaiValue.setText(String.format("%.2f VND", Math.max(0.0, tongPhaiDat - phieuDatHang.getSoTienCoc())));
        lblPTTTValue.setText("Chuyen khoan");
    }

    private String getTrangThaiText(int trangThai) {
        return switch (trangThai) {
            case 0 -> "Chua co hang";
            case 1 -> "San hang";
            default -> "Da hoan thanh";
        };
    }

    public void onLapHoaDon() {
        if (phieuDatHang == null) {
            hien(ERROR, "Loi", "Khong tim thay thong tin phieu dat hang!");
            return;
        }
        if (phieuDatHang.getTrangthai() == 0) {
            hien(WARNING, "Khong the lap hoa don", "Phieu dat hang nay chua co hang.");
            return;
        }
        if (phieuDatHang.getTrangthai() == 2) {
            hien(WARNING, "Khong the lap hoa don", "Phieu dat hang nay da duoc hoan tat.");
            return;
        }

        boolean approved = phieuDatHangService.approve(phieuDatHang.getMaPDat(), SessionContext.requireCurrentUser());
        phieuDatHang = phieuDatHangService.findById(phieuDatHang.getMaPDat());
        hien(approved ? Alert.AlertType.INFORMATION : WARNING,
                approved ? "Duyet phieu dat thanh cong" : "Khong du hang",
                approved
                        ? "Phieu dat hang da duoc duyet. Luong lap hoa don se duoc noi tiep o Nhom 5."
                        : "Khong du hang trong kho de duyet toan bo phieu dat.");
        hienThiThongTin();
    }
}

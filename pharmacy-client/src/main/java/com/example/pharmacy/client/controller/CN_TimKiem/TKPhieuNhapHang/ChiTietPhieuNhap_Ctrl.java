package com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuNhapHang;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.common.model.ChiTietPhieuNhapDto;
import com.example.pharmacy.common.model.PhieuNhapDto;
import com.example.pharmacy.client.service.PhieuNhapService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class ChiTietPhieuNhap_Ctrl {
    public TextField txtMaPhieuNhap;
    public TextField txtNhaCungCap;
    public TextField txtNgayNhap;
    public TextField txtTrangThai;
    public TextField txtNhanVien;
    public TextArea txtGhiChu;
    public TableView<ChiTietPhieuNhapDto> tblChiTietPhieuNhap;
    public TableColumn<ChiTietPhieuNhapDto, String> colTenThuoc;
    public TableColumn<ChiTietPhieuNhapDto, String> colMaThuoc;
    public TableColumn<ChiTietPhieuNhapDto, String> colSoLuong;
    public TableColumn<ChiTietPhieuNhapDto, String> colGiaNhap;
    public TableColumn<ChiTietPhieuNhapDto, String> colThue;
    public TableColumn<ChiTietPhieuNhapDto, String> colChietKhau;
    public Label lblTongGiaNhap;
    public TableColumn<ChiTietPhieuNhapDto, String> colMaLoHang;

    private final PhieuNhapService phieuNhapService = new PhieuNhapService();

    public void initialize() {
        Platform.runLater(this::applyDialogIconSafely);
    }

    public void load(PhieuNhapDto temp) {
        txtMaPhieuNhap.setText(temp.getMaPN());
        txtNhaCungCap.setText(temp.getNhaCungCap().getTenNCC());
        txtNgayNhap.setText(String.valueOf(temp.getNgayNhap()));
        txtTrangThai.setText(Boolean.TRUE.equals(temp.getTrangThai()) ? "Hoan thanh" : "Chua hoan thanh");
        txtNhanVien.setText(temp.getNhanVien().getTenNV());
        txtGhiChu.setText(temp.getGhiChu());

        List<ChiTietPhieuNhapDto> list = phieuNhapService.findDetailsByMaPhieuNhap(temp.getMaPN());
        tblChiTietPhieuNhap.getItems().setAll(list);

        colTenThuoc.setCellValueFactory(cel -> new SimpleStringProperty(cel.getValue().getThuoc().getTenThuoc()));
        colMaThuoc.setCellValueFactory(cel -> new SimpleStringProperty(cel.getValue().getThuoc().getMaThuoc()));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        colThue.setCellValueFactory(new PropertyValueFactory<>("thue"));
        colChietKhau.setCellValueFactory(new PropertyValueFactory<>("chietKhau"));
        colMaLoHang.setCellValueFactory(new PropertyValueFactory<>("maLH"));
        lblTongGiaNhap.setText("Tong gia nhap: " + String.format("%.2f", temp.getTongTien()) + " VND");
    }

    public void btnThoat(MouseEvent mouseEvent) {
        ((Stage) txtGhiChu.getScene().getWindow()).close();
    }

    private void applyDialogIconSafely() {
        if (txtGhiChu == null || txtGhiChu.getScene() == null || txtGhiChu.getScene().getWindow() == null) {
            return;
        }
        if (txtGhiChu.getScene().getWindow() instanceof Stage dialog) {
            TuyChinhAlert.setAppIcon(dialog);
        }
    }
}

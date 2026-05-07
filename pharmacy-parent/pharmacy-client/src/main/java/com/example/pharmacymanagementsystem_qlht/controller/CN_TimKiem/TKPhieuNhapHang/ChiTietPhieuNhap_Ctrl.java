package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuNhapHang;

import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuNhap;
import com.example.pharmacymanagementsystem_qlht.model.PhieuNhap;
import com.example.pharmacymanagementsystem_qlht.service.PhieuNhapService;
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
    public TableView<ChiTietPhieuNhap> tblChiTietPhieuNhap;
    public TableColumn<ChiTietPhieuNhap, String> colTenThuoc;
    public TableColumn<ChiTietPhieuNhap, String> colMaThuoc;
    public TableColumn<ChiTietPhieuNhap, String> colSoLuong;
    public TableColumn<ChiTietPhieuNhap, String> colGiaNhap;
    public TableColumn<ChiTietPhieuNhap, String> colThue;
    public TableColumn<ChiTietPhieuNhap, String> colChietKhau;
    public Label lblTongGiaNhap;
    public TableColumn<ChiTietPhieuNhap, String> colMaLoHang;

    private final PhieuNhapService phieuNhapService = new PhieuNhapService();

    public void initialize() {
        Platform.runLater(() -> {
            Stage dialog = (Stage) txtGhiChu.getScene().getWindow();
            dialog.getIcons().add(new javafx.scene.image.Image(
                    getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        });
    }

    public void load(PhieuNhap temp) {
        txtMaPhieuNhap.setText(temp.getMaPN());
        txtNhaCungCap.setText(temp.getNhaCungCap().getTenNCC());
        txtNgayNhap.setText(String.valueOf(temp.getNgayNhap()));
        txtTrangThai.setText(Boolean.TRUE.equals(temp.getTrangThai()) ? "Hoan thanh" : "Chua hoan thanh");
        txtNhanVien.setText(temp.getNhanVien().getTenNV());
        txtGhiChu.setText(temp.getGhiChu());

        List<ChiTietPhieuNhap> list = phieuNhapService.findDetailsByMaPhieuNhap(temp.getMaPN());
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
}

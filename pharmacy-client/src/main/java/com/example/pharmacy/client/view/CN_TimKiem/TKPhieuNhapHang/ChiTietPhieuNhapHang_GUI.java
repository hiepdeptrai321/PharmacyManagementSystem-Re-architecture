package com.example.pharmacy.client.view.CN_TimKiem.TKPhieuNhapHang;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuNhapHang.ChiTietPhieuNhap_Ctrl;
import com.example.pharmacy.common.model.ChiTietPhieuNhapDto;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ChiTietPhieuNhapHang_GUI extends Application {

    @Override
    public void start(Stage stage) {
        ViewRefs v = buildUIForController();
        Scene scene = new Scene(v.root, 855, 390);
        addStyles(scene);
        TuyChinhAlert.setAppIcon(stage);
        stage.setTitle("Chi tiet phieu nhap");
        stage.setScene(scene);
        stage.show();
    }

    public void showWithController(Stage stage, ChiTietPhieuNhap_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        ctrl.txtMaPhieuNhap = v.txtMaPhieuNhap;
        ctrl.txtNhaCungCap = v.txtNhaCungCap;
        ctrl.txtNgayNhap = v.txtNgayNhap;
        ctrl.txtTrangThai = v.txtTrangThai;
        ctrl.txtNhanVien = v.txtNhanVien;
        ctrl.txtGhiChu = v.txtGhiChu;

        ctrl.tblChiTietPhieuNhap = v.tblChiTietPhieuNhap;
        ctrl.colMaThuoc = v.colMaThuoc;
        ctrl.colTenThuoc = v.colTenThuoc;
        ctrl.colMaLoHang = v.colMaLoHang;
        ctrl.colSoLuong = v.colSoLuong;
        ctrl.colGiaNhap = v.colGiaNhap;
        ctrl.colChietKhau = v.colChietKhau;
        ctrl.colThue = v.colThue;
        ctrl.lblTongGiaNhap = v.lblTongGiaNhap;

        v.btnThoatPane.setOnMouseClicked(ctrl::btnThoat);

        try {
            ctrl.initialize();
        } catch (Exception ignore) {
        }

        Scene scene = new Scene(v.root, 855, 390);
        addStyles(scene);
        TuyChinhAlert.setAppIcon(stage);
        stage.setTitle("Chi tiet phieu nhap");
        stage.setScene(scene);
        stage.show();
    }

    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        v.root = new Pane();
        v.root.setPrefSize(855, 390);
        v.root.setStyle("-fx-font-size: 14px;");

        Pane paneTitle = new Pane();
        paneTitle.setId("paneTitle");
        paneTitle.setPrefSize(855, 40);

        Label lbTitle = new Label("Chi tiet phieu nhap");
        lbTitle.setLayoutX(14);
        lbTitle.setLayoutY(2);
        lbTitle.setFont(Font.font("System Bold", 25));
        paneTitle.getChildren().add(lbTitle);

        Label lbMaPN = new Label("Ma phieu nhap");
        lbMaPN.setLayoutX(14);
        lbMaPN.setLayoutY(47);

        v.txtMaPhieuNhap = new TextField();
        v.txtMaPhieuNhap.setLayoutX(14);
        v.txtMaPhieuNhap.setLayoutY(64);
        v.txtMaPhieuNhap.setPrefSize(141, 30);

        Label lbNCC = new Label("Nha cung cap:");
        lbNCC.setLayoutX(164);
        lbNCC.setLayoutY(47);

        v.txtNhaCungCap = new TextField();
        v.txtNhaCungCap.setLayoutX(163);
        v.txtNhaCungCap.setLayoutY(64);
        v.txtNhaCungCap.setPrefSize(343, 30);

        Label lbNgayNhap = new Label("Ngay nhap:");
        lbNgayNhap.setLayoutX(14);
        lbNgayNhap.setLayoutY(94);

        v.txtNgayNhap = new TextField();
        v.txtNgayNhap.setLayoutX(14);
        v.txtNgayNhap.setLayoutY(111);
        v.txtNgayNhap.setPrefSize(149, 25);

        Label lbTrangThai = new Label("Trang thai:");
        lbTrangThai.setLayoutX(168);
        lbTrangThai.setLayoutY(94);

        v.txtTrangThai = new TextField();
        v.txtTrangThai.setLayoutX(168);
        v.txtTrangThai.setLayoutY(111);
        v.txtTrangThai.setPrefSize(155, 30);

        Label lbNhanVien = new Label("Nhan vien:");
        lbNhanVien.setLayoutX(328);
        lbNhanVien.setLayoutY(94);

        v.txtNhanVien = new TextField();
        v.txtNhanVien.setLayoutX(327);
        v.txtNhanVien.setLayoutY(111);
        v.txtNhanVien.setPrefSize(179, 25);

        Label lbGhiChu = new Label("Ghi chu:");
        lbGhiChu.setLayoutX(518);
        lbGhiChu.setLayoutY(47);

        v.txtGhiChu = new TextArea();
        v.txtGhiChu.setLayoutX(518);
        v.txtGhiChu.setLayoutY(64);
        v.txtGhiChu.setPrefSize(323, 77);

        v.tblChiTietPhieuNhap = new TableView<>();
        v.tblChiTietPhieuNhap.setLayoutX(16);
        v.tblChiTietPhieuNhap.setLayoutY(148);
        v.tblChiTietPhieuNhap.setPrefSize(827, 200);

        v.colMaThuoc = new TableColumn<>("Ma thuoc");
        v.colMaThuoc.setPrefWidth(96);
        v.colMaThuoc.setStyle("-fx-alignment: CENTER;");

        v.colTenThuoc = new TableColumn<>("Ten thuoc");
        v.colTenThuoc.setPrefWidth(196.33);
        v.colTenThuoc.setStyle("-fx-alignment: CENTER-LEFT;");

        v.colMaLoHang = new TableColumn<>("Lo hang");
        v.colMaLoHang.setPrefWidth(132);
        v.colMaLoHang.setStyle("-fx-alignment: CENTER;");

        v.colSoLuong = new TableColumn<>("So luong");
        v.colSoLuong.setPrefWidth(94);
        v.colSoLuong.setStyle("-fx-alignment: CENTER;");

        v.colGiaNhap = new TableColumn<>("Gia nhap");
        v.colGiaNhap.setPrefWidth(112);
        v.colGiaNhap.setStyle("-fx-alignment: CENTER;");

        v.colChietKhau = new TableColumn<>("CK");
        v.colChietKhau.setPrefWidth(108);
        v.colChietKhau.setStyle("-fx-alignment: CENTER;");

        v.colThue = new TableColumn<>("Thue");
        v.colThue.setPrefWidth(93);
        v.colThue.setStyle("-fx-alignment: CENTER;");

        v.tblChiTietPhieuNhap.getColumns().addAll(
                v.colMaThuoc, v.colTenThuoc, v.colMaLoHang, v.colSoLuong, v.colGiaNhap, v.colChietKhau, v.colThue
        );

        v.lblTongGiaNhap = new Label("Tong gia nhap:");
        v.lblTongGiaNhap.setLayoutX(16);
        v.lblTongGiaNhap.setLayoutY(357);
        v.lblTongGiaNhap.setFont(Font.font("System Bold", 15));

        v.btnThoatPane = new Pane();
        v.btnThoatPane.setId("buttonThoat");
        v.btnThoatPane.setLayoutX(769);
        v.btnThoatPane.setLayoutY(355);
        v.btnThoatPane.setPrefSize(74, 26);

        Label lbThoat = new Label("Thoat");
        lbThoat.setId("labelTrang");
        lbThoat.setLayoutX(18);
        lbThoat.setLayoutY(3);
        v.btnThoatPane.getChildren().add(lbThoat);

        v.root.getChildren().addAll(
                paneTitle,
                lbMaPN, v.txtMaPhieuNhap,
                lbNCC, v.txtNhaCungCap,
                lbNgayNhap, v.txtNgayNhap,
                lbTrangThai, v.txtTrangThai,
                lbNhanVien, v.txtNhanVien,
                lbGhiChu, v.txtGhiChu,
                v.tblChiTietPhieuNhap,
                v.lblTongGiaNhap,
                v.btnThoatPane
        );

        return v;
    }

    private void addStyles(Scene scene) {
        var css = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacy/client/css/ChiTietPhieuNhap.css"),
                "Khong tim thay css/ChiTietPhieuNhap.css"
        ).toExternalForm();
        scene.getStylesheets().add(css);
    }

    private static class ViewRefs {
        Pane root;
        TextField txtMaPhieuNhap;
        TextField txtNhaCungCap;
        TextField txtNgayNhap;
        TextField txtTrangThai;
        TextField txtNhanVien;
        TextArea txtGhiChu;
        TableView<ChiTietPhieuNhapDto> tblChiTietPhieuNhap;
        TableColumn<ChiTietPhieuNhapDto, String> colMaThuoc;
        TableColumn<ChiTietPhieuNhapDto, String> colTenThuoc;
        TableColumn<ChiTietPhieuNhapDto, String> colMaLoHang;
        TableColumn<ChiTietPhieuNhapDto, String> colSoLuong;
        TableColumn<ChiTietPhieuNhapDto, String> colGiaNhap;
        TableColumn<ChiTietPhieuNhapDto, String> colChietKhau;
        TableColumn<ChiTietPhieuNhapDto, String> colThue;
        Label lblTongGiaNhap;
        Pane btnThoatPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

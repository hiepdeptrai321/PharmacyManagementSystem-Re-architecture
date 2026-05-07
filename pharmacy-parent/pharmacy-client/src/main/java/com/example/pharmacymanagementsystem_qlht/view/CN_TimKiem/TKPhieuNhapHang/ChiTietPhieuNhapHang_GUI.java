package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuNhapHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuNhapHang.ChiTietPhieuNhap_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuNhap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ChiTietPhieuNhapHang_GUI extends Application {

    @Override
    public void start(Stage stage) {
        ViewRefs v = buildUIForController();
        Scene scene = new Scene(v.root, 855, 390);
        addStyles(scene);
        stage.setTitle("Chi tiết phiếu nhập");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Dùng trong app: KHÔNG lookup – tạo control và truyền thẳng cho controller
     */
    public void showWithController(Stage stage, ChiTietPhieuNhap_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // ===== GÁN CONTROL VỀ CONTROLLER (đúng fx:id FXML) =====
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

        // onMouseClicked="#btnThoat" (Pane “Thoát”)
        v.btnThoatPane.setOnMouseClicked(ctrl::btnThoat);

        // Nếu controller có initialize()
        try {
            ctrl.initialize();
        } catch (Exception ignore) {
        }

        Scene scene = new Scene(v.root, 855, 390);
        addStyles(scene);
        stage.setTitle("Chi tiết phiếu nhập");
        stage.setScene(scene);
        stage.show();
    }

    // ================== XÂY UI & GIỮ THAM CHIẾU ==================
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        // Root
        v.root = new Pane();
        v.root.setPrefSize(855, 390);
        v.root.setStyle("-fx-font-size: 14 px;");

        // Title pane
        Pane paneTitle = new Pane();
        paneTitle.setId("paneTitle");
        paneTitle.setPrefSize(855, 40);

        Label lbTitle = new Label("Chi tiết phiếu nhập");
        lbTitle.setLayoutX(14);
        lbTitle.setLayoutY(2);
        lbTitle.setFont(Font.font("System Bold", 25));
        paneTitle.getChildren().add(lbTitle);

        // Labels + fields
        Label lbMaPN = new Label("Mã phiếu nhập");
        lbMaPN.setLayoutX(14);
        lbMaPN.setLayoutY(47);

        v.txtMaPhieuNhap = new TextField();
        v.txtMaPhieuNhap.setLayoutX(14);
        v.txtMaPhieuNhap.setLayoutY(64);
        v.txtMaPhieuNhap.setPrefSize(141, 30);

        Label lbNCC = new Label("Nhà cung cấp:");
        lbNCC.setLayoutX(164);
        lbNCC.setLayoutY(47);

        v.txtNhaCungCap = new TextField();
        v.txtNhaCungCap.setLayoutX(163);
        v.txtNhaCungCap.setLayoutY(64);
        v.txtNhaCungCap.setPrefSize(343, 30);

        Label lbNgayNhap = new Label("Ngày nhập:");
        lbNgayNhap.setLayoutX(14);
        lbNgayNhap.setLayoutY(94);

        v.txtNgayNhap = new TextField();
        v.txtNgayNhap.setLayoutX(14);
        v.txtNgayNhap.setLayoutY(111);
        v.txtNgayNhap.setPrefSize(149, 25);

        Label lbTrangThai = new Label("Trạng thái:");
        lbTrangThai.setLayoutX(168);
        lbTrangThai.setLayoutY(94);

        v.txtTrangThai = new TextField();
        v.txtTrangThai.setLayoutX(168);
        v.txtTrangThai.setLayoutY(111);
        v.txtTrangThai.setPrefSize(155, 30);

        Label lbNhanVien = new Label("Nhân viên:");
        lbNhanVien.setLayoutX(328);
        lbNhanVien.setLayoutY(94);

        v.txtNhanVien = new TextField();
        v.txtNhanVien.setLayoutX(327);
        v.txtNhanVien.setLayoutY(111);
        v.txtNhanVien.setPrefSize(179, 25);

        Label lbGhiChu = new Label("Ghi chú:");
        lbGhiChu.setLayoutX(518);
        lbGhiChu.setLayoutY(47);

        v.txtGhiChu = new TextArea();
        v.txtGhiChu.setLayoutX(518);
        v.txtGhiChu.setLayoutY(64);
        v.txtGhiChu.setPrefSize(323, 77);

        // Table
        v.tblChiTietPhieuNhap = new TableView<>();
        v.tblChiTietPhieuNhap.setLayoutX(16);
        v.tblChiTietPhieuNhap.setLayoutY(148);
        v.tblChiTietPhieuNhap.setPrefSize(827, 200);

        v.colMaThuoc = new TableColumn<>("Mã thuốc");
        v.colMaThuoc.setPrefWidth(96);

        v.colTenThuoc = new TableColumn<>("Tên thuốc");
        v.colTenThuoc.setPrefWidth(196.33334350585938);

        v.colMaLoHang = new TableColumn<>("Lô hàng");
        v.colMaLoHang.setPrefWidth(132);

        v.colSoLuong = new TableColumn<>("Số lượng");
        v.colSoLuong.setPrefWidth(94);

        v.colGiaNhap = new TableColumn<>("Giá nhập");
        v.colGiaNhap.setPrefWidth(112);

        v.colChietKhau = new TableColumn<>("CK");
        v.colChietKhau.setPrefWidth(108);

        v.colThue = new TableColumn<>("Thuế");
        v.colThue.setPrefWidth(93);

        v.tblChiTietPhieuNhap.getColumns().addAll(
                v.colMaThuoc, v.colTenThuoc, v.colMaLoHang,
                v.colSoLuong, v.colGiaNhap, v.colChietKhau, v.colThue
        );

        // Tổng giá nhập
        v.lblTongGiaNhap = new Label("Tổng giá nhập:");
        v.lblTongGiaNhap.setLayoutX(16);
        v.lblTongGiaNhap.setLayoutY(357);
        v.lblTongGiaNhap.setFont(Font.font("System Bold", 15));

        // Nút Thoát (Pane click)
        v.btnThoatPane = new Pane();
        v.btnThoatPane.setId("buttonThoat");
        v.btnThoatPane.setLayoutX(769);
        v.btnThoatPane.setLayoutY(355);
        v.btnThoatPane.setPrefSize(74, 26);

        Label lbThoat = new Label("Thoát");
        lbThoat.setId("labelTrang");
        lbThoat.setLayoutX(18);
        lbThoat.setLayoutY(3);
        v.btnThoatPane.getChildren().add(lbThoat);

        // Add all
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
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ChiTietPhieuNhap.css"),
                "Không tìm thấy css/ChiTietPhieuNhap.css"
        ).toExternalForm();
        scene.getStylesheets().add(css);
    }

    // ================== Giữ tham chiếu control ==================
    private static class ViewRefs {
        Pane root;

        TextField txtMaPhieuNhap, txtNhaCungCap, txtNgayNhap, txtTrangThai, txtNhanVien;
        TextArea txtGhiChu;

        TableView<ChiTietPhieuNhap> tblChiTietPhieuNhap;
        TableColumn<ChiTietPhieuNhap, String> colMaThuoc, colTenThuoc, colMaLoHang;
        TableColumn<ChiTietPhieuNhap, String> colSoLuong;
        TableColumn<ChiTietPhieuNhap, String> colGiaNhap, colChietKhau, colThue;

        Label lblTongGiaNhap;
        Pane btnThoatPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

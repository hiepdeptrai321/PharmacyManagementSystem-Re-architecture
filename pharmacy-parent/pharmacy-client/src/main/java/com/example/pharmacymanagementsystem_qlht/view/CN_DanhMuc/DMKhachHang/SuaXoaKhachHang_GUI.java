package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKhachHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhachHang.ChiTietKhachHang_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class SuaXoaKhachHang_GUI {

    public void showWithController(Stage stage, ChiTietKhachHang_Ctrl ctrl) {
        Pane root = new Pane();
        root.setPrefHeight(280.0);
        root.setPrefWidth(582.0);
        root.setStyle("-fx-font-size: 13;");

        // Title
        Label lbTitle = new Label("Thông tin khách hàng");
        lbTitle.setLayoutX(13.5);
        lbTitle.setLayoutY(8.0);
        lbTitle.setPrefHeight(31.0);
        lbTitle.setPrefWidth(555.0);
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setFont(Font.font("System Bold", 18.0));

        // Row 1
        Label lblTenKH = new Label("Tên khách hàng");
        lblTenKH.setLayoutX(13.5);
        lblTenKH.setLayoutY(49.0);
        TextField txtTenKH = new TextField();
        txtTenKH.setId("txtTenKH");
        txtTenKH.setLayoutX(14.0);
        txtTenKH.setLayoutY(66.0);
        txtTenKH.setPrefHeight(25.0);
        txtTenKH.setPrefWidth(232.0);
        Label errTenKH = new Label();
        errTenKH.setId("errTenKH");
        errTenKH.setLayoutX(16.0);
        errTenKH.setLayoutY(93.0);
        errTenKH.setPrefHeight(0.0);
        errTenKH.setPrefWidth(227.0);
        errTenKH.setTextFill(javafx.scene.paint.Color.RED);

        Label lblNgaySinh = new Label("Ngày sinh");
        lblNgaySinh.setLayoutX(258.0);
        lblNgaySinh.setLayoutY(49.0);
        DatePicker txtNgaySinh = new DatePicker();
        txtNgaySinh.setId("txtNgaySinh");
        txtNgaySinh.setLayoutX(258.0);
        txtNgaySinh.setLayoutY(66.0);
        txtNgaySinh.setPrefHeight(25.0);
        txtNgaySinh.setPrefWidth(156.0);

        Label lblGioiTinh = new Label("Giới tính");
        lblGioiTinh.setLayoutX(424.0);
        lblGioiTinh.setLayoutY(49.0);
        ComboBox<String> cboGioiTinh = new ComboBox<>();
        cboGioiTinh.setId("cboGioiTinh");
        cboGioiTinh.setLayoutX(424.0);
        cboGioiTinh.setLayoutY(66.0);
        cboGioiTinh.setPrefHeight(25.0);
        cboGioiTinh.setPrefWidth(142.0);

        // Row 2
        Label lblSDT = new Label("Số điện thoại");
        lblSDT.setLayoutX(17.0);
        lblSDT.setLayoutY(107.0);
        TextField txtSDT = new TextField();
        txtSDT.setId("txtSDT");
        txtSDT.setLayoutX(15.0);
        txtSDT.setLayoutY(126.0);
        txtSDT.setPrefHeight(25.0);
        txtSDT.setPrefWidth(298.0);
        Label errSDT = new Label();
        errSDT.setId("errSDT");
        errSDT.setLayoutX(13.0);
        errSDT.setLayoutY(150.0);
        errSDT.setPrefHeight(12.0);
        errSDT.setPrefWidth(298.0);
        errSDT.setTextFill(javafx.scene.paint.Color.RED);


        Label lblEmail = new Label("Email");
        lblEmail.setLayoutX(332.0);
        lblEmail.setLayoutY(107.0);
        TextField txtEmail = new TextField();
        txtEmail.setId("txtEmail");
        txtEmail.setLayoutX(327.0);
        txtEmail.setLayoutY(127.0);
        txtEmail.setPrefHeight(17.0);
        txtEmail.setPrefWidth(245.0);
        Label errEmail = new Label();
        errEmail.setId("errEmail");
        errEmail.setLayoutX(328.0);
        errEmail.setLayoutY(152.0);
        errEmail.setPrefHeight(12.0);
        errEmail.setPrefWidth(243.0);
        errEmail.setTextFill(javafx.scene.paint.Color.RED);


        // Row 3
        Label lblDiaChi = new Label("Địa chỉ");
        lblDiaChi.setLayoutX(16.0);
        lblDiaChi.setLayoutY(169.0);
        TextField txtDiaChi = new TextField();
        txtDiaChi.setId("txtDiaChi");
        txtDiaChi.setLayoutX(16.0);
        txtDiaChi.setLayoutY(189.0);
        txtDiaChi.setPrefHeight(27.0);
        txtDiaChi.setPrefWidth(551.0);
        Label errDiaChi = new Label();
        errDiaChi.setId("errDiaChi");
        errDiaChi.setLayoutX(12.0);
        errDiaChi.setLayoutY(214.0);
        errDiaChi.setPrefHeight(12.0);
        errDiaChi.setPrefWidth(552.0);
        errDiaChi.setTextFill(javafx.scene.paint.Color.RED);


        // Buttons
        Button btnXoa = new Button();
        btnXoa.setId("nutXoa");
        btnXoa.setLayoutX(15.0);
        btnXoa.setLayoutY(237.0);
        btnXoa.setPrefHeight(25.0);
        btnXoa.setPrefWidth(84.0);
        Label lblXoa = new Label("Xóa");
        lblXoa.setId("txtTrang_Bold");
        btnXoa.setGraphic(lblXoa);

        Button btnHuy = new Button("Hủy");
        btnHuy.setId("btnHuy");
        btnHuy.setLayoutX(376.0);
        btnHuy.setLayoutY(239.0);
        btnHuy.setPrefHeight(25.0);
        btnHuy.setPrefWidth(84.0);

        Button btnLuu = new Button();
        btnLuu.setId("btnluu");
        btnLuu.setLayoutX(475.0);
        btnLuu.setLayoutY(239.0);
        btnLuu.setPrefHeight(25.0);
        btnLuu.setPrefWidth(84.0);
        Label lblLuu = new Label("Lưu");
        lblLuu.setId("txtTrang_Bold");
        btnLuu.setGraphic(lblLuu);
        btnXoa.setGraphic(lblXoa);


        root.getChildren().addAll(
                lbTitle, lblTenKH, txtTenKH, errTenKH, lblNgaySinh, txtNgaySinh, lblGioiTinh, cboGioiTinh,
                lblSDT, txtSDT, errSDT, lblEmail, txtEmail, errEmail,
                lblDiaChi, txtDiaChi, errDiaChi,
                btnXoa, btnHuy, btnLuu
        );

        // --- Scene và CSS ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css")).toExternalForm());

        // --- Tiêm vào Controller ---
        ctrl.btnLuu = btnLuu;
        ctrl.btnHuy = btnHuy;
        ctrl.btnXoa = btnXoa;
        ctrl.txtDiaChi = txtDiaChi;
        ctrl.txtEmail = txtEmail;
        ctrl.txtNgaySinh = txtNgaySinh;
        ctrl.txtSDT = txtSDT;
        ctrl.txtTenKH = txtTenKH;
        ctrl.cboGioiTinh = cboGioiTinh;
        ctrl.errTenKH = errTenKH;
        ctrl.errDiaChi = errDiaChi;
        ctrl.errEmail = errEmail;
        ctrl.errSDT = errSDT;

        // --- Khởi chạy Controller ---
        ctrl.initialize();

        // Gán Scene cho Stage
        stage.setScene(scene);
    }
}
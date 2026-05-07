package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy.ThemNhomDuocLy_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ThemNhomDuocLy_GUI {

    public void showWithController(Stage stage, ThemNhomDuocLy_Ctrl ctrl) {
        Pane root = new Pane();
        root.setPrefHeight(251.0);
        root.setPrefWidth(582.0);
        root.setStyle("-fx-font-size: 13;");

        // Title
        Label lbTitle = new Label("Thêm nhóm dược lý");
        lbTitle.setLayoutX(13.5);
        lbTitle.setLayoutY(8.0);
        lbTitle.setPrefHeight(31.0);
        lbTitle.setPrefWidth(555.0);
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setFont(Font.font("System Bold", 18.0));

        // Tên Nhóm Dược Lý
        Label lblTenNDL = new Label("Tên nhóm dược lý");
        lblTenNDL.setLayoutX(13.5);
        lblTenNDL.setLayoutY(49.0);
        TextField txtTenNhomDuocLy = new TextField();
        txtTenNhomDuocLy.setId("txtTenNhomDuocLy");
        txtTenNhomDuocLy.setLayoutX(14.0);
        txtTenNhomDuocLy.setLayoutY(66.0);
        txtTenNhomDuocLy.setPrefHeight(25.0);
        txtTenNhomDuocLy.setPrefWidth(555.0);

        // Mô tả
        Label lblMoTa = new Label("Mô tả");
        lblMoTa.setLayoutX(14.0);
        lblMoTa.setLayoutY(99.0);
        TextArea txtMoTa = new TextArea();
        txtMoTa.setId("txtMoTa");
        txtMoTa.setLayoutX(14.0);
        txtMoTa.setLayoutY(120.0);
        txtMoTa.setPrefHeight(69.0);
        txtMoTa.setPrefWidth(555.0);

        // Button Thêm
        Button btnThem = new Button();
        btnThem.setId("nutThem");
        btnThem.setLayoutX(479.0);
        btnThem.setLayoutY(202.0);
        btnThem.setPrefHeight(30.0);
        btnThem.setPrefWidth(74.0);
        Label lblThem = new Label("Thêm");
        lblThem.setId("txtTrang_Bold");
        btnThem.setGraphic(lblThem);

        // Button Hủy
        Button btnHuy = new Button();
        btnHuy.setId("nutXoa");
        btnHuy.setLayoutX(25.0);
        btnHuy.setLayoutY(202.0);
        btnHuy.setPrefHeight(30);
        btnHuy.setPrefWidth(63.0);
        Label lblHuy = new Label("Hủy");
        lblHuy.setId("txtTrang_Bold");
        btnHuy.setGraphic(lblHuy);

        root.getChildren().addAll(lbTitle, lblTenNDL, txtTenNhomDuocLy, lblMoTa, txtMoTa, btnThem, btnHuy);

        // --- Scene và CSS ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css")).toExternalForm());

        // --- Tiêm vào Controller ---
        ctrl.btnHuy = btnHuy;
        ctrl.btnThem = btnThem;
        ctrl.txtMoTa = txtMoTa;
        ctrl.txtTenNhomDuocLy = txtTenNhomDuocLy;

        // --- Khởi chạy Controller ---
        ctrl.initialize();

        // Gán Scene cho Stage
        stage.setScene(scene);
    }
}
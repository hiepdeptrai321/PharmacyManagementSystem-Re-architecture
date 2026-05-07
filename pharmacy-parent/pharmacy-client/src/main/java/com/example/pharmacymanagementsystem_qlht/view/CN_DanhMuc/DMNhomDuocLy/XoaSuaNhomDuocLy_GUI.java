package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy.SuaXoaNhomDuocLy;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class XoaSuaNhomDuocLy_GUI {

    public void showWithController(Stage stage, SuaXoaNhomDuocLy ctrl) {
        Pane root = new Pane();
        root.setPrefHeight(251.0);
        root.setPrefWidth(582.0);
        root.setStyle("-fx-font-size: 13;");

        // Title
        Label lbTitle = new Label("Chi tiết nhóm dược lý");
        lbTitle.setLayoutX(13.5);
        lbTitle.setLayoutY(8.0);
        lbTitle.setPrefHeight(31.0);
        lbTitle.setPrefWidth(555.0);
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setFont(Font.font("System Bold", 18.0));

        Separator separator = new Separator();
        separator.setLayoutX(-8.0);
        separator.setLayoutY(38.0);
        separator.setPrefHeight(0.0);
        separator.setPrefWidth(589.0);

        // Tên Nhóm Dược Lý
        Label lblTenNDL = new Label("Tên nhóm dược lý");
        lblTenNDL.setLayoutX(13.5);
        lblTenNDL.setLayoutY(49.0);
        TextField txtTenNDL = new TextField();
        txtTenNDL.setId("txtTenNDL");
        txtTenNDL.setLayoutX(14.0);
        txtTenNDL.setLayoutY(66.0);
        txtTenNDL.setPrefHeight(25.0);
        txtTenNDL.setPrefWidth(555.0);

        // Mô tả
        Label lblMoTa = new Label("Mô tả");
        lblMoTa.setLayoutX(14.0);
        lblMoTa.setLayoutY(99.0);
        TextArea txtMota = new TextArea();
        txtMota.setId("txtMota");
        txtMota.setLayoutX(14.0);
        txtMota.setLayoutY(120.0);
        txtMota.setPrefHeight(69.0);
        txtMota.setPrefWidth(555.0);

        // Nút Lưu (dạng Pane)
        Pane btnLuu = new Pane();
        btnLuu.setId("nutThem");
        btnLuu.setLayoutX(477.0);
        btnLuu.setLayoutY(202.0);
        btnLuu.setPrefHeight(30);
        btnLuu.setPrefWidth(91.0);
        Label lblLuu = new Label("Lưu");
        lblLuu.setId("txtTrang_Bold");
        lblLuu.setLayoutX(30.0);
        lblLuu.setLayoutY(4.0);
        btnLuu.getChildren().add(lblLuu);

        // Nút Xóa (dạng Pane)
        Pane btnXoa = new Pane();
        btnXoa.setId("nutXoa");
        btnXoa.setLayoutX(14.0);
        btnXoa.setLayoutY(202.0);
        btnXoa.setPrefHeight(30);
        btnXoa.setPrefWidth(91.0);
        Label lblXoa = new Label("Xóa");
        lblXoa.setId("txtTrang_Bold");
        lblXoa.setLayoutX(34.0);
        lblXoa.setLayoutY(4.0);
        btnXoa.getChildren().add(lblXoa);

        root.getChildren().addAll(lbTitle, separator, lblTenNDL, txtTenNDL, lblMoTa, txtMota, btnLuu, btnXoa);

        // --- Scene và CSS ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css")).toExternalForm());

        // --- Tiêm vào Controller ---
        ctrl.btnLuu = btnLuu;
        ctrl.btnXoa = btnXoa;
        ctrl.txtMota = txtMota;
        ctrl.txtTenNDL = txtTenNDL;

        // --- Khởi chạy Controller ---
        ctrl.initialize();

        // Gán Scene cho Stage
        stage.setScene(scene);
    }
}
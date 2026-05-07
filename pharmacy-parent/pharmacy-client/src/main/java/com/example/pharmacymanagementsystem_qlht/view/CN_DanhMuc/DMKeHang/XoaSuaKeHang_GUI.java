package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang.XoaSuaKeHang_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class XoaSuaKeHang_GUI {

    public void showWithController(Stage stage, XoaSuaKeHang_Ctrl ctrl) {
        Pane root = new Pane();
        root.setPrefHeight(251.0);
        root.setPrefWidth(582.0);
        root.setStyle("-fx-font-size: 13;");

        Label lbTitle = new Label("Chi tiết kệ hàng");
        lbTitle.setLayoutX(13.5);
        lbTitle.setLayoutY(8.0);
        lbTitle.setPrefHeight(31.0);
        lbTitle.setPrefWidth(555.0);
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setFont(Font.font("System Bold", 18.0));

        Label lblTenKe = new Label("Tên kệ");
        lblTenKe.setLayoutX(13.5);
        lblTenKe.setLayoutY(49.0);

        TextField txtTenKe = new TextField();
        txtTenKe.setId("txtTenKe");
        txtTenKe.setLayoutX(14.0);
        txtTenKe.setLayoutY(66.0);
        txtTenKe.setPrefHeight(25.0);
        txtTenKe.setPrefWidth(555.0);

        Label lblMoTa = new Label("Mô tả");
        lblMoTa.setLayoutX(13.0);
        lblMoTa.setLayoutY(104.0);

        TextArea txtMota = new TextArea();
        txtMota.setId("txtMota");
        txtMota.setLayoutX(14.0);
        txtMota.setLayoutY(120.0);
        txtMota.setPrefHeight(69.0);
        txtMota.setPrefWidth(555.0);

        // Nút Lưu (dưới dạng Pane)
        Pane btnLuu = new Pane();
        btnLuu.setId("nutThem");
        btnLuu.setLayoutX(477.0);
        btnLuu.setLayoutY(202.0);
        btnLuu.setPrefHeight(25.0);
        btnLuu.setPrefWidth(91.0);
        Label lblLuu = new Label("Lưu");
        lblLuu.setId("txtTrang_Bold");
        lblLuu.setLayoutX(30.0);
        lblLuu.setLayoutY(4.0);
        btnLuu.getChildren().add(lblLuu);

        // Nút Xóa (dưới dạng Pane)
        Pane btnXoa = new Pane();
        btnXoa.setId("nutXoa");
        btnXoa.setLayoutX(14.0);
        btnXoa.setLayoutY(202.0);
        btnXoa.setPrefHeight(25.0);
        btnXoa.setPrefWidth(91.0);
        Label lblXoa = new Label("Xóa");
        lblXoa.setId("txtTrang_Bold");
        lblXoa.setLayoutX(34.0);
        lblXoa.setLayoutY(4.0);
        btnXoa.getChildren().add(lblXoa);

        root.getChildren().addAll(lbTitle, lblTenKe, txtTenKe, lblMoTa, txtMota, btnLuu, btnXoa);

        // --- Scene và CSS ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css")).toExternalForm());

        // --- Tiêm vào Controller ---
        ctrl.btnLuu = btnLuu;
        ctrl.btnXoa = btnXoa;
        ctrl.txtMota = txtMota;
        ctrl.txtTenKe = txtTenKe;

        // --- Khởi chạy Controller ---
        ctrl.initialize();

        // Gán Scene cho Stage (do cửa sổ này là dialog)
        stage.setScene(scene);
    }
}
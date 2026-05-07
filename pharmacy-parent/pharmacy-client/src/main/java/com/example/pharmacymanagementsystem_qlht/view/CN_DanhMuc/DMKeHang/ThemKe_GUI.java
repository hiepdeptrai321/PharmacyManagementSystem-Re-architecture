package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang.ThemKe_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ThemKe_GUI {

    public void showWithController(Stage stage, ThemKe_Ctrl ctrl) {
        Pane root = new Pane();
        root.setPrefHeight(251.0);
        root.setPrefWidth(582.0);
        root.setStyle("-fx-font-size: 13;");

        Label lbTitle = new Label("Thêm kệ hàng");
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
        lblMoTa.setLayoutX(14.0);
        lblMoTa.setLayoutY(100.0);

        TextArea txtMoTa = new TextArea();
        txtMoTa.setId("txtMoTa");
        txtMoTa.setLayoutX(14.0);
        txtMoTa.setLayoutY(120.0);
        txtMoTa.setPrefHeight(69.0);
        txtMoTa.setPrefWidth(555.0);

        // Nút Thêm
        Button btnThem = new Button();
        btnThem.setId("nutThem");
        btnThem.setLayoutX(479.0);
        btnThem.setLayoutY(202.0);
        btnThem.setPrefHeight(25.0);
        btnThem.setPrefWidth(74.0);
        Label lblThem = new Label("Thêm");
        lblThem.setId("txtTrang_Bold");
        btnThem.setGraphic(lblThem);

        // Nút Hủy
        Button btnHuy = new Button();
        btnHuy.setId("nutXoa"); // ID trong FXML là "nutXoa"
        btnHuy.setLayoutX(25.0);
        btnHuy.setLayoutY(202.0);
        btnHuy.setPrefHeight(25.0);
        btnHuy.setPrefWidth(63.0);
        Label lblHuy = new Label("Hủy");
        lblHuy.setId("txtTrang_Bold");
        btnHuy.setGraphic(lblHuy);

        root.getChildren().addAll(lbTitle, lblTenKe, txtTenKe, lblMoTa, txtMoTa, btnThem, btnHuy);

        // --- Scene và CSS ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css")).toExternalForm());

        // --- Tiêm vào Controller ---
        ctrl.btnHuy = btnHuy;
        ctrl.btnThem = btnThem;
        ctrl.txtMoTa = txtMoTa;
        ctrl.txtTenKe = txtTenKe;

        // --- Khởi chạy Controller ---
        ctrl.initialize();

        // Gán Scene cho Stage (do cửa sổ này là dialog)
        stage.setScene(scene);
    }
}
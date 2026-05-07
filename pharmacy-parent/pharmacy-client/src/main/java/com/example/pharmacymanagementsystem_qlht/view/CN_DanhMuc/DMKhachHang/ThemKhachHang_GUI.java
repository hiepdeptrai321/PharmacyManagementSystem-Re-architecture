package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKhachHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhachHang.ThemKhachHang_Ctrl;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ThemKhachHang_GUI {

    public void showWithController(Stage stage, ThemKhachHang_Ctrl ctrl) {
        GridPane root = new GridPane();
        root.setHgap(12);
        root.setVgap(2);
        root.setPrefHeight(150.0);
        root.setPrefWidth(688.0);
        root.setStyle("-fx-font-size: 13;");
        root.setPadding(new Insets(6, 10, 10, 10));

        // Column Constraints
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHgrow(Priority.ALWAYS);
        col0.setPercentWidth(37.0);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(36.0);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setPercentWidth(27.0);
        root.getColumnConstraints().addAll(col0, col1, col2);

        // Row Constraints (5 rows)
        for (int i = 0; i < 5; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            root.getRowConstraints().add(rc);
        }

        // Title
        Label lbTitle = new Label("Thêm khách hàng");
        lbTitle.setPrefHeight(26.0);
        lbTitle.setPrefWidth(659.0);
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setFont(Font.font("System Bold", 16.0));
        root.add(lbTitle, 0, 0, 3, 1); // span 3 columns

        // --- Row 1: Tên, Ngày sinh, Giới tính ---
        // Tên KH
        VBox vbTenKH = new VBox(2);
        Label lblTenKH = new Label("Tên khách hàng");
        TextField txtTenKH = new TextField();
        txtTenKH.setMaxWidth(Double.MAX_VALUE);
        Label errTenKH = new Label();
        errTenKH.setMaxWidth(Double.MAX_VALUE);
        errTenKH.setStyle("-fx-font-size:11px; -fx-text-fill: red;");
        errTenKH.setWrapText(true);
        vbTenKH.getChildren().addAll(lblTenKH, txtTenKH, errTenKH);
        root.add(vbTenKH, 0, 1);

        // Ngày sinh
        VBox vbNgaySinh = new VBox(2);
        Label lblNgaySinh = new Label("Ngày sinh");
        DatePicker dpNgaySinh = new DatePicker();
        dpNgaySinh.setMaxWidth(Double.MAX_VALUE);
        Label errNgaySinh = new Label();
        errNgaySinh.setMaxWidth(Double.MAX_VALUE);
        errNgaySinh.setStyle("-fx-font-size:11px; -fx-text-fill: red;");
        errNgaySinh.setWrapText(true);
        vbNgaySinh.getChildren().addAll(lblNgaySinh, dpNgaySinh, errNgaySinh);
        root.add(vbNgaySinh, 1, 1);

        // Giới tính
        VBox vbGioiTinh = new VBox(2);
        Label lblGioiTinh = new Label("Giới tính");
        ComboBox<String> cbGioiTinh = new ComboBox<>();
        cbGioiTinh.setMaxWidth(Double.MAX_VALUE);
        cbGioiTinh.setPromptText("Chọn giới tính");
        Label errGioiTinh = new Label();
        errGioiTinh.setMaxWidth(Double.MAX_VALUE);
        errGioiTinh.setStyle("-fx-font-size:11px; -fx-text-fill: red;");
        errGioiTinh.setWrapText(true);
        vbGioiTinh.getChildren().addAll(lblGioiTinh, cbGioiTinh, errGioiTinh);
        root.add(vbGioiTinh, 2, 1);

        // --- Row 2: SĐT, Email ---
        // SĐT
        VBox vbSDT = new VBox(2);
        Label lblSDT = new Label("Số điện thoại");
        TextField txtSDT = new TextField();
        txtSDT.setMaxWidth(Double.MAX_VALUE);
        Label errSDT = new Label();
        errSDT.setMaxWidth(Double.MAX_VALUE);
        errSDT.setStyle("-fx-font-size:11px; -fx-text-fill: red;");
        errSDT.setWrapText(true);
        vbSDT.getChildren().addAll(lblSDT, txtSDT, errSDT);
        root.add(vbSDT, 0, 2);

        // Email
        VBox vbEmail = new VBox(2);
        Label lblEmail = new Label("Email");
        TextField txtEmail = new TextField();
        txtEmail.setMaxWidth(Double.MAX_VALUE);
        Label errEmail = new Label();
        errEmail.setMaxWidth(Double.MAX_VALUE);
        errEmail.setStyle("-fx-font-size:11px; -fx-text-fill: red;");
        errEmail.setWrapText(true);
        vbEmail.getChildren().addAll(lblEmail, txtEmail, errEmail);
        root.add(vbEmail, 1, 2);

        // --- Row 3: Địa chỉ ---
        VBox vbDiaChi = new VBox(2);
        Label lblDiaChi = new Label("Địa chỉ");
        TextField txtDiaChi = new TextField();
        txtDiaChi.setMaxWidth(Double.MAX_VALUE);
        Label errDiaChi = new Label();
        errDiaChi.setMaxWidth(Double.MAX_VALUE);
        errDiaChi.setStyle("-fx-font-size:11px; -fx-text-fill: red;");
        errDiaChi.setWrapText(true);
        vbDiaChi.getChildren().addAll(lblDiaChi, txtDiaChi, errDiaChi);
        root.add(vbDiaChi, 0, 3, 2, 1); // span 2 columns

        // --- Row 4: Message và Buttons ---
        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill: red;");
        lblMessage.setWrapText(true);
        root.add(lblMessage, 0, 4, 2, 1);

        Button btnHuy = new Button("Hủy");
        btnHuy.setPrefWidth(60.0);
        Button btnThem = new Button("Thêm");
        btnThem.setPrefWidth(59.0);



        btnHuy.setStyle("-fx-background-color: #ff0700; -fx-text-fill: white;");
        btnThem.setId("btnluu");


        HBox hbButtons = new HBox(8, btnHuy, btnThem);
        hbButtons.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        root.add(hbButtons, 2, 4);

        // --- Scene và CSS ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css")).toExternalForm());

        // --- Tiêm vào Controller ---
        ctrl.txtTenKH = txtTenKH;
        ctrl.dpNgaySinh = dpNgaySinh;
        ctrl.txtSDT = txtSDT;
        ctrl.txtEmail = txtEmail;
        ctrl.txtDiaChi = txtDiaChi;
        ctrl.cbGioiTinh = cbGioiTinh;

        ctrl.lblMessage = lblMessage;
        ctrl.btnThem = btnThem;
        ctrl.btnHuy = btnHuy;

        ctrl.errTenKH = errTenKH;
        ctrl.errNgaySinh = errNgaySinh;
        ctrl.errSDT = errSDT;
        ctrl.errEmail = errEmail;
        ctrl.errDiaChi = errDiaChi;
        ctrl.errGioiTinh = errGioiTinh;

        ctrl.init();

        stage.setScene(scene);
    }
}
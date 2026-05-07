package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKNhaCungCap.ChiTietNhaCungCap_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ChiTietNhaCungCap_GUI {

    public void showWithController(Stage stage, ChiTietNhaCungCap_Ctrl ctrl) {
        Pane root = new Pane();
        root.setPrefHeight(326.0);
        root.setPrefWidth(601.0);
        root.setStyle("-fx-font-size: 13 px;");

        // --- Thanh tiêu đề ---
        Pane paneTitle = new Pane();
        paneTitle.setId("paneGachChan");
        paneTitle.setPrefHeight(38.0);
        paneTitle.setPrefWidth(601.0);

        Label lbTitle = new Label("Chi tiết nhà cung cấp");
        lbTitle.setLayoutX(14.0);
        lbTitle.setLayoutY(2.0);
        lbTitle.setPrefWidth(263.0);
        lbTitle.setFont(Font.font("System Bold", 25.0));
        paneTitle.getChildren().add(lbTitle);

        // --- Row 1 ---
        Label lblMaNCC = new Label("Mã NCC:");
        lblMaNCC.setLayoutX(14.0);
        lblMaNCC.setLayoutY(46.0);
        TextField maNCC = new TextField();
        maNCC.setId("maNCC");
        maNCC.setEditable(false);
        maNCC.setLayoutX(14.0);
        maNCC.setLayoutY(63.0);
        maNCC.setPrefHeight(25.0);
        maNCC.setPrefWidth(117.0);

        Label lblTenNCC = new Label("Tên NCC:");
        lblTenNCC.setLayoutX(136.0);
        lblTenNCC.setLayoutY(46.0);
        TextField tenNCC = new TextField();
        tenNCC.setId("tenNCC");
        tenNCC.setEditable(false);
        tenNCC.setLayoutX(136.0);
        tenNCC.setLayoutY(63.0);
        tenNCC.setPrefHeight(25.0);
        tenNCC.setPrefWidth(342.0);

        Label lblSDT = new Label("Số điện thoại");
        lblSDT.setLayoutX(484.0);
        lblSDT.setLayoutY(45.0);
        TextField SDT = new TextField();
        SDT.setId("SDT");
        SDT.setEditable(false);
        SDT.setLayoutX(484.0);
        SDT.setLayoutY(63.0);
        SDT.setPrefHeight(25.0);
        SDT.setPrefWidth(105.0);

        // --- Row 2 ---
        Label lblEmail = new Label("Email:");
        lblEmail.setLayoutX(14.0);
        lblEmail.setLayoutY(94.0);
        TextField Email = new TextField();
        Email.setId("Email");
        Email.setEditable(false);
        Email.setLayoutX(14.0);
        Email.setLayoutY(111.0);
        Email.setPrefHeight(25.0);
        Email.setPrefWidth(208.0);

        Label lblDiaChi = new Label("Địa chỉ:");
        lblDiaChi.setLayoutX(227.0);
        lblDiaChi.setLayoutY(94.0);
        TextField DiaChi = new TextField();
        DiaChi.setId("DiaChi");
        DiaChi.setEditable(false);
        DiaChi.setLayoutX(227.0);
        DiaChi.setLayoutY(111.0);
        DiaChi.setPrefHeight(25.0);
        DiaChi.setPrefWidth(362.0);

        // --- Row 3 ---
        Label lblGPKD = new Label("GPKD:");
        lblGPKD.setLayoutX(14.0);
        lblGPKD.setLayoutY(142.0);
        TextField GPKD = new TextField();
        GPKD.setId("GPKD");
        GPKD.setEditable(false);
        GPKD.setLayoutX(14.0);
        GPKD.setLayoutY(159.0);
        GPKD.setPrefHeight(25.0);
        GPKD.setPrefWidth(134.0);

        Label lblTenCongTy = new Label("Tên công ty:");
        lblTenCongTy.setLayoutX(153.0);
        lblTenCongTy.setLayoutY(142.0);
        TextField TenCongTy = new TextField();
        TenCongTy.setId("TenCongTy");
        TenCongTy.setEditable(false);
        TenCongTy.setLayoutX(153.0);
        TenCongTy.setLayoutY(159.0);
        TenCongTy.setPrefHeight(25.0);
        TenCongTy.setPrefWidth(271.0);

        Label lblMST = new Label("Mã số thuế");
        lblMST.setLayoutX(430.0);
        lblMST.setLayoutY(142.0);
        TextField MST = new TextField();
        MST.setId("MST");
        MST.setEditable(false);
        MST.setLayoutX(430.0);
        MST.setLayoutY(159.0);
        MST.setPrefHeight(25.0);
        MST.setPrefWidth(158.0);

        // --- Row 4 (Ghi chú) ---
        Label lblGhiChu = new Label("Ghi chú:");
        lblGhiChu.setLayoutX(14.0);
        lblGhiChu.setLayoutY(191.0);
        TextArea GhiChu = new TextArea();
        GhiChu.setId("GhiChu");
        GhiChu.setEditable(false);
        GhiChu.setLayoutX(14.0);
        GhiChu.setLayoutY(209.0);
        GhiChu.setPrefHeight(72.0);
        GhiChu.setPrefWidth(572.0);
        GhiChu.setWrapText(true);

        // --- Button Thoát ---
        Button btnThoat = new Button("Thoát");
        btnThoat.setId("btnThoat");
        btnThoat.setLayoutX(528.0);
        btnThoat.setLayoutY(290.0);

        root.getChildren().addAll(
                paneTitle, lblMaNCC, maNCC, lblTenNCC, tenNCC, lblSDT, SDT,
                lblEmail, Email, lblDiaChi, DiaChi,
                lblGPKD, GPKD, lblTenCongTy, TenCongTy, lblMST, MST,
                lblGhiChu, GhiChu, btnThoat
        );

        // --- Scene và CSS ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ChiTietNhaCungCap.css")).toExternalForm());

        // --- Tiêm vào Controller ---
        ctrl.DiaChi = DiaChi;
        ctrl.Email = Email;
        ctrl.GPKD = GPKD;
        ctrl.GhiChu = GhiChu;
        ctrl.MST = MST;
        ctrl.SDT = SDT;
        ctrl.TenCongTy = TenCongTy;
        ctrl.btnThoat = btnThoat;
        ctrl.maNCC = maNCC;
        ctrl.tenNCC = tenNCC;

        // --- Khởi chạy Controller ---
        ctrl.initialize();

        // Gán Scene cho Stage
        stage.setScene(scene);
    }
}
package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang.DanhMucKeHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class DanhMucKeHang_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, DanhMucKeHang_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefHeight(895.0);
        root.setPrefWidth(1646.0);
        root.setStyle("-fx-font-size: 14;");

        // --- Thanh tiêu đề ---
        Pane lblPaneTitle = new Pane();
        lblPaneTitle.setId("lblpaneTitle");
        lblPaneTitle.setLayoutX(11.0);
        lblPaneTitle.setLayoutY(-2.0);
        lblPaneTitle.setPrefHeight(40.0);
        lblPaneTitle.setPrefWidth(1634.0);

        Label lbTitle = new Label("Danh mục kệ hàng");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutY(2.0);
        lbTitle.setPrefHeight(36.0);
        lbTitle.setPrefWidth(280.0);
        lbTitle.setFont(Font.font(48.0));

        // --- ĐÃ SỬA LỖI ẢNH ---
        // Tôi đã xóa "%20(1)" khỏi tên file
        ImageView imgShelf = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/shelf_8693463.png")).toExternalForm())
        );

        imgShelf.setPickOnBounds(true);
        imgShelf.setPreserveRatio(true);

        lblPaneTitle.getChildren().addAll(lbTitle, imgShelf);

        // --- Tìm kiếm và các nút ---
        TextField txtTimKiem = new TextField();
        txtTimKiem.setId("txtTimKiem");
        txtTimKiem.setLayoutX(11.0);
        txtTimKiem.setLayoutY(51.0);
        txtTimKiem.setPrefHeight(40.0);
        txtTimKiem.setPrefWidth(772.0);
        txtTimKiem.setPromptText("Tìm theo mã, tên kệ");

        Button btnTim = new Button("🔍 Tìm");
        btnTim.setId("btntim");
        btnTim.setLayoutX(794.0);
        btnTim.setLayoutY(51.0);
        btnTim.setMinWidth(35.0);
        btnTim.setPrefHeight(40.0);
        btnTim.setPrefWidth(77.0);
        btnTim.getStyleClass().add("btn");

        Button btnThem = new Button("✚Thêm kệ hàng");
        btnThem.setId("btnthemthuoc");
        btnThem.setLayoutX(1482.0);
        btnThem.setLayoutY(52.0);
        btnThem.setPrefHeight(40.0);
        btnThem.setPrefWidth(144.0);
        btnThem.getStyleClass().add("btn");

        Button btnLamMoi = new Button();
        btnLamMoi.setId("btnReset");
        btnLamMoi.setLayoutX(879.0);
        btnLamMoi.setLayoutY(51.0);
        btnLamMoi.setPrefHeight(40.0);
        btnLamMoi.setPrefWidth(45.0);

        ImageView imgRefresh = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm())
        );
        imgRefresh.setFitHeight(23.0);
        imgRefresh.setFitWidth(30.0);
        imgRefresh.setPickOnBounds(true);
        imgRefresh.setPreserveRatio(true);
        btnLamMoi.setGraphic(imgRefresh);


        imgShelf.setFitHeight(40.0);
        imgShelf.setFitWidth(40.0);
        imgShelf.setLayoutX(290.0);
        imgShelf.setLayoutY(6.0);
        lbTitle.setPrefSize(320, 36);
        lbTitle.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
        btnTim.setPrefSize(69, 40);
        btnLamMoi.setPrefSize(45, 40);
        btnThem.setPrefSize(150, 40);
        btnThem.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
        btnTim.setStyle(" -fx-background-color: #0c81ff; -fx-text-fill: white;");

        // --- Bảng ---
        TableView<KeHang> tblKeHang = new TableView<>();
        tblKeHang.setId("tablethuoc");
        tblKeHang.setLayoutX(12.0);
        tblKeHang.setLayoutY(102.0);
        tblKeHang.setPrefHeight(785.0);
        tblKeHang.setPrefWidth(1624.0);

        TableColumn<KeHang, String> cotSTT = new TableColumn<>("STT");
        cotSTT.setPrefWidth(90.33);
        cotSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KeHang, String> cotMaKe = new TableColumn<>("Mã Kệ");
        cotMaKe.setPrefWidth(260.67);
        cotMaKe.setStyle("-fx-alignment: CENTER;");

        TableColumn<KeHang, String> cotTenKe = new TableColumn<>("Tên Kệ");
        cotTenKe.setMinWidth(0.0);
        cotTenKe.setPrefWidth(1143.0);

        TableColumn<KeHang, String> colChiTiet = new TableColumn<>();
        colChiTiet.setPrefWidth(111.0);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tblKeHang.getColumns().addAll(cotSTT, cotMaKe, cotTenKe, colChiTiet);

        // Thêm tất cả vào root
        root.getChildren().addAll(lblPaneTitle, txtTimKiem, btnTim, btnThem, btnLamMoi, tblKeHang);

        // --- Scene và CSS ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css")).toExternalForm());

        // --- BƯỚC 1: Tiêm vào Controller ---
        ctrl.btnThem = btnThem;
        ctrl.btnLamMoi = btnLamMoi;
        ctrl.btnTim = btnTim;
        ctrl.cotMaKe = cotMaKe;
        ctrl.cotSTT = cotSTT;
        ctrl.cotTenKe = cotTenKe;
        ctrl.colChiTiet = colChiTiet;
        ctrl.tblKeHang = (TableView<KeHang>) tblKeHang;
        ctrl.txtTimKiem = txtTimKiem;

        // --- BƯỚC 2: Khởi chạy Controller ---
        // (Lúc này các nút đã được tiêm vào)
        ctrl.initialize();

        stage.setTitle("Danh mục kệ hàng");
        stage.setScene(scene);
    }
}
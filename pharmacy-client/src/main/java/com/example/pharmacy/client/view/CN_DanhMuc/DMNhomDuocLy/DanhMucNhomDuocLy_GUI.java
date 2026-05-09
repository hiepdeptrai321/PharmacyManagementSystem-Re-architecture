package com.example.pharmacy.client.view.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacy.client.controller.CN_DanhMuc.DMNhomDuocLy.DanhMucNhomDuocLy_Ctrl;
import com.example.pharmacy.common.model.NhomDuocLyDto;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class DanhMucNhomDuocLy_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, DanhMucNhomDuocLy_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefHeight(895.0);
        root.setPrefWidth(1646.0);
        root.setStyle("-fx-font-size: 14;");

        // --- Tiêu đề ---
        Pane lblPaneTitle = new Pane();
        lblPaneTitle.setId("lblpaneTitle");
        lblPaneTitle.setLayoutX(11.0);
        lblPaneTitle.setLayoutY(-2.0);
        lblPaneTitle.setPrefHeight(40.0);
        lblPaneTitle.setPrefWidth(1634.0);

        Label lbTitle = new Label("Danh mục nhóm dược lý");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutY(2.0);
        lbTitle.setPrefHeight(36.0);
        lbTitle.setPrefWidth(306.0);
        lbTitle.setFont(Font.font(48.0));

        ImageView imgTitle = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacy/client/img/drugs.png")).toExternalForm())
        );
        imgTitle.setFitHeight(34.0);
        imgTitle.setFitWidth(46.0);
        imgTitle.setLayoutX(325.0);
        imgTitle.setLayoutY(4.0);
        imgTitle.setPickOnBounds(true);
        imgTitle.setPreserveRatio(true);

        lblPaneTitle.getChildren().addAll(lbTitle, imgTitle);

        // --- Thanh tìm kiếm và nút ---
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

        Button btnThem = new Button("✚ Thêm nhóm dược lý");
        btnThem.setId("btnthemthuoc");
        btnThem.setLayoutX(1455.0);
        btnThem.setLayoutY(52.0);
        btnThem.setPrefHeight(40.0);
        btnThem.setPrefWidth(176.0);
        btnThem.getStyleClass().add("btn");

        Button btnLamMoi = new Button();
        btnLamMoi.setId("btnReset");
        btnLamMoi.setLayoutX(879.0);
        btnLamMoi.setLayoutY(51.0);
        btnLamMoi.setPrefHeight(40.0);
        btnLamMoi.setPrefWidth(45.0);

        ImageView imgRefresh = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacy/client/img/refresh-3104.png")).toExternalForm())
        );
        imgRefresh.setFitHeight(23.0);
        imgRefresh.setFitWidth(30.0);
        imgRefresh.setPickOnBounds(true);
        imgRefresh.setPreserveRatio(true);
        btnLamMoi.setGraphic(imgRefresh);


        imgTitle.setFitHeight(40.0);
        imgTitle.setFitWidth(40.0);
        imgTitle.setLayoutX(375.0);
        imgTitle.setLayoutY(6.0);
        lbTitle.setPrefSize(390, 36);
        lbTitle.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
        btnTim.setPrefSize(69, 40);
        btnLamMoi.setPrefSize(45, 40);
        btnThem.setPrefSize(180, 40);
        btnThem.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
        btnTim.setStyle(" -fx-background-color: #0c81ff; -fx-text-fill: white;");

        // --- Bảng ---
        TableView<NhomDuocLyDto> tbNhomDuocLy = new TableView<>();
        tbNhomDuocLy.setId("tablethuoc");
        tbNhomDuocLy.setLayoutX(12.0);
        tbNhomDuocLy.setLayoutY(102.0);
        tbNhomDuocLy.setPrefHeight(785.0);
        tbNhomDuocLy.setPrefWidth(1624.0);

        TableColumn<NhomDuocLyDto, String> cotSTT = new TableColumn<>("STT");
        cotSTT.setPrefWidth(90.33);
        cotSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<NhomDuocLyDto, String> cotMaNDL = new TableColumn<>("Mã nhóm dược lý");
        cotMaNDL.setPrefWidth(260.67);
        cotMaNDL.setStyle("-fx-alignment: CENTER;");

        TableColumn<NhomDuocLyDto, String> cotTenNDL = new TableColumn<>("Tên nhóm dược lý");
        cotTenNDL.setMinWidth(0.0);
        cotTenNDL.setPrefWidth(1139.0);

        TableColumn<NhomDuocLyDto, String> colChiTiet = new TableColumn<>();
        colChiTiet.setPrefWidth(117.0);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tbNhomDuocLy.getColumns().addAll(cotSTT, cotMaNDL, cotTenNDL, colChiTiet);

        // Thêm tất cả vào root
        root.getChildren().addAll(lblPaneTitle, txtTimKiem, btnTim, btnThem, btnLamMoi, tbNhomDuocLy);

        // --- Scene và CSS ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacy/client/css/QuanLyThuoc.css")).toExternalForm());

        // --- Tiêm vào Controller ---
        ctrl.btnThem = btnThem;
        ctrl.btnLamMoi = btnLamMoi;
        ctrl.btnTim = btnTim;
        // ctrl.btnXoa = btnXoa; // Không thấy btnXoa trong FXML này
        ctrl.cotMaNDL = cotMaNDL;
        ctrl.cotSTT = cotSTT;
        ctrl.cotTenNDL = cotTenNDL;
        ctrl.colChiTiet = colChiTiet;
        ctrl.tbNhomDuocLy = (TableView<NhomDuocLyDto>) tbNhomDuocLy;
        ctrl.txtTimKiem = txtTimKiem;

        // --- Khởi chạy Controller ---
        ctrl.initialize();

        stage.setTitle("Danh mục nhóm dược lý");
        stage.setScene(scene);
    }
}
package com.example.pharmacy.client.view.CN_DanhMuc.DMKhachHang;

import com.example.pharmacy.client.controller.CN_DanhMuc.DMKhachHang.DanhMucKhachHang_Ctrl;
import com.example.pharmacy.common.model.KhachHangDto;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class DanhMucKhachHang_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, DanhMucKhachHang_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefHeight(895.0);
        root.setPrefWidth(1646.0);
        root.setStyle("-fx-font-size: 14;");

        // --- Tiêu đề ---
        Pane lblPaneTitle = new Pane();
        lblPaneTitle.setId("lblpaneTitle");
        lblPaneTitle.setLayoutX(11.0);
        lblPaneTitle.setPrefHeight(40.0);
        lblPaneTitle.setPrefWidth(1624.0);

        Label lbTitle = new Label("Danh mục khách hàng");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutY(2.0);
        lbTitle.setPrefHeight(36.0);
        lbTitle.setPrefWidth(306.0);
        lbTitle.setFont(Font.font(36.0));

        ImageView imgTitle = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacy/client/img/three_16725211.png")).toExternalForm())
        );
        imgTitle.setFitHeight(37.0);
        imgTitle.setFitWidth(46.0);
        imgTitle.setLayoutX(350.0);
        imgTitle.setLayoutY(1.0);
        imgTitle.setPickOnBounds(true);
        imgTitle.setPreserveRatio(true);

        lblPaneTitle.getChildren().addAll(lbTitle, imgTitle);

        // --- Thanh tìm kiếm và nút ---
        TextField txtTim = new TextField();
        txtTim.setId("txtTim");
        txtTim.setLayoutX(12.0);
        txtTim.setLayoutY(47.0);
        txtTim.setPrefHeight(40.0);
        txtTim.setPrefWidth(768.0);
        txtTim.setPromptText("Tìm theo mã, tên hoặc SĐT khách hàng");

        Button btnTim = new Button("🔍 Tìm");
        btnTim.setId("btntim");
        btnTim.setLayoutX(790.0);
        btnTim.setLayoutY(47.0);
        btnTim.setMinWidth(35.0);
        btnTim.setPrefHeight(40.0);
        btnTim.setPrefWidth(76.0);

        Button btnthemKH = new Button("✚Thêm khách hàng");
        btnthemKH.setId("btnthemthuoc");
        btnthemKH.setLayoutX(1477.0);
        btnthemKH.setLayoutY(47.0);
        btnthemKH.setPrefHeight(40.0);
        btnthemKH.setPrefWidth(158.0);
        AnchorPane.setTopAnchor(btnthemKH, 47.0);

        Button btnLamMoi = new Button();
        btnLamMoi.setId("btnReset");
        btnLamMoi.setLayoutX(871.0);
        btnLamMoi.setLayoutY(48.0);
        btnLamMoi.setMinHeight(39.0);
        btnLamMoi.setMinWidth(43.0);
        btnLamMoi.setPrefHeight(39.0);
        btnLamMoi.setPrefWidth(43.0);

        ImageView imgRefresh = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacy/client/img/refresh-3104.png")).toExternalForm())
        );
        imgRefresh.setFitHeight(20.0);
        imgRefresh.setFitWidth(33.0);
        imgRefresh.setPickOnBounds(true);
        imgRefresh.setPreserveRatio(true);
        btnLamMoi.setGraphic(imgRefresh);

        imgTitle.setFitHeight(40.0);
        imgTitle.setFitWidth(40.0);
        imgTitle.setLayoutX(345.0);
        imgTitle.setLayoutY(6.0);
        lbTitle.setPrefSize(365, 36);
        lbTitle.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
        btnTim.setPrefSize(69, 40);
        btnLamMoi.setPrefSize(45, 40);
        btnthemKH.setPrefSize(157, 40);
        btnthemKH.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
        btnTim.setStyle(" -fx-background-color: #0c81ff; -fx-text-fill: white;");

        // --- Bảng ---
        TableView<KhachHangDto> tbKhachHang = new TableView<>();
        tbKhachHang.setId("tablethuoc");
        tbKhachHang.setLayoutX(12.0);
        tbKhachHang.setLayoutY(97.0);
        tbKhachHang.setPrefHeight(791.0);
        tbKhachHang.setPrefWidth(1624.0);

        TableColumn<KhachHangDto, String> cotSTT = new TableColumn<>("STT");
        cotSTT.setPrefWidth(91.67);
        cotSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHangDto, String> cotMaKH = new TableColumn<>("Mã khách hàng");
        cotMaKH.setPrefWidth(261.0);
        cotMaKH.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHangDto, String> cotTenKH = new TableColumn<>("Tên khách hàng");
        cotTenKH.setPrefWidth(401.0);

        TableColumn<KhachHangDto, String> cotGioiTinh = new TableColumn<>("Giới tính");
        cotGioiTinh.setMinWidth(0.0);
        cotGioiTinh.setPrefWidth(172.33);

        TableColumn<KhachHangDto, String> cotSDT = new TableColumn<>("SĐT");
        cotSDT.setMinWidth(0.0);
        cotSDT.setPrefWidth(294.0);
        cotSDT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHangDto, String> cotDiaChi = new TableColumn<>("Địa chỉ");
        cotDiaChi.setMinWidth(0.0);
        cotDiaChi.setPrefWidth(287.0);

        TableColumn<KhachHangDto, String> cotChiTiet = new TableColumn<>();
        cotChiTiet.setPrefWidth(97.0);
        cotChiTiet.setStyle("-fx-alignment: CENTER;");

        tbKhachHang.getColumns().addAll(cotSTT, cotMaKH, cotTenKH, cotGioiTinh, cotSDT, cotDiaChi, cotChiTiet);

        // Thêm tất cả vào root
        root.getChildren().addAll(lblPaneTitle, txtTim, btnTim, btnthemKH, btnLamMoi, tbKhachHang);

        // --- Scene và CSS ---
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacy/client/css/QuanLyThuoc.css")).toExternalForm());

        // --- Tiêm vào Controller ---
        ctrl.txtTim = txtTim;
        ctrl.btnTim = btnTim;
        ctrl.btnthemKH = btnthemKH;
        ctrl.btnLamMoi = btnLamMoi;
        ctrl.tbKhachHang = (TableView<KhachHangDto>) tbKhachHang;
        ctrl.cotSTT = cotSTT;
        ctrl.cotMaKH = cotMaKH;
        ctrl.cotTenKH = cotTenKH;
        ctrl.cotGioiTinh = cotGioiTinh;
        ctrl.cotSDT = cotSDT;
        ctrl.cotDiaChi = cotDiaChi;
        ctrl.cotChiTiet = cotChiTiet;

        // --- Khởi chạy Controller ---
        ctrl.initialize();

        stage.setTitle("Danh mục khách hàng");
        stage.setScene(scene);
    }
}
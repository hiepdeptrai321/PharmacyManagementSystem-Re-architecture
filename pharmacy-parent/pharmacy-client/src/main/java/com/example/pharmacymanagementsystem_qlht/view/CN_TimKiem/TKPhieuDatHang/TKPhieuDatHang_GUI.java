package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDatHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDatHang.TKPhieuDatHang_Ctrl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class TKPhieuDatHang_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, TKPhieuDatHang_Ctrl ctrl) {
        Pane root = new Pane();
        root.setPrefSize(1646, 895);
        // Sửa nhẹ lỗi syntax cũ: "14 px" -> "14px"
        root.setStyle("-fx-font-size: 14px;");

        // Top VBox -> title, separator, search row, table inside ScrollPane
        VBox mainVBox = new VBox();
        mainVBox.setLayoutX(10);
        mainVBox.setLayoutY(14);
        mainVBox.setSpacing(8);

        // Title HBox
        HBox titleBox = new HBox();
        titleBox.setPrefHeight(46);
        Label lbTimKiem = new Label("Tìm kiếm phiếu đặt hàng");
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setPrefHeight(53);
        lbTimKiem.setPrefWidth(449);
        lbTimKiem.setStyle("-fx-font-size: 36px;");

        Region spacer1 = new Region();
        spacer1.setPrefWidth(20);

        ImageView imgBill = new ImageView();
        imgBill.setFitHeight(46);
        imgBill.setFitWidth(48);
        imgBill.setPickOnBounds(true);
        imgBill.setPreserveRatio(true);
        try {
            imgBill.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png")).toExternalForm()));
        } catch (Exception ignored) {}
        titleBox.getChildren().addAll(lbTimKiem, imgBill);

        Separator sep = new Separator();
        sep.setPrefWidth(200);

        // Search HBox
        HBox searchBox = new HBox();
        searchBox.setPrefHeight(60);
        searchBox.setSpacing(8);
        searchBox.setPadding(new Insets(0,0,0,0));

        ComboBox<String> cboTimKiem = new ComboBox<>();
        cboTimKiem.setPrefHeight(40);
        cboTimKiem.setPrefWidth(164);
        cboTimKiem.setPromptText("Tìm theo");

        TextField txtNoiDungTimKiem = new TextField();
        txtNoiDungTimKiem.setPrefHeight(40);
        txtNoiDungTimKiem.setPrefWidth(240);
        txtNoiDungTimKiem.setPromptText("Nhập thông tin phiếu đặt hàng");

        Region spacer2 = new Region();
        spacer2.setPrefWidth(30);

        ImageView imgTime = new ImageView();
        imgTime.setFitHeight(27);
        imgTime.setFitWidth(28);
        imgTime.setPickOnBounds(true);
        imgTime.setPreserveRatio(true);
        try {
            imgTime.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/time-2623.png")).toExternalForm()));
        } catch (Exception ignored) {}

        Label lbTu = new Label("Từ: ");
        DatePicker dpTuNgay = new DatePicker();
        dpTuNgay.setPrefHeight(40);
        dpTuNgay.setPrefWidth(125);


        Label lbDen = new Label("Đến: ");
        DatePicker dpDenNgay = new DatePicker();
        dpDenNgay.setPrefHeight(40);
        dpDenNgay.setPrefWidth(125);

        Region spacer3 = new Region();
        spacer3.setPrefWidth(30);

        Button btnTimKiem = new Button("Tìm");
        btnTimKiem.setPrefHeight(40);
        btnTimKiem.setPrefWidth(74);
        btnTimKiem.setStyle("-fx-background-color: #188dfb; -fx-text-fill: white");

        // set graphic for search button if available
        try {
            ImageView imgSearch = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png")).toExternalForm()));
            imgSearch.setFitHeight(20);
            imgSearch.setFitWidth(20);
            btnTimKiem.setGraphic(imgSearch);
        } catch (Exception ignored) {}

        Button btnHuyBo = new Button();
        btnHuyBo.setPrefHeight(40);
        btnHuyBo.setPrefWidth(52);
        btnHuyBo.setId("btnReset");
        // reset graphic
        try {
            ImageView imgRefresh = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm()));
            imgRefresh.setFitHeight(25);
            imgRefresh.setFitWidth(25);
            btnHuyBo.setGraphic(imgRefresh);
        } catch (Exception ignored) {}

        Region spacer4 = new Region();
        spacer4.setPrefWidth(420);

        ComboBox<String> cbLoc = new ComboBox<>();
        cbLoc.setPrefHeight(41);
        cbLoc.setPrefWidth(175);
        cbLoc.setPromptText("⌛ Bộ lọc nhanh");

        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.getChildren().addAll(cboTimKiem, txtNoiDungTimKiem, spacer2, imgTime, lbTu, dpTuNgay, lbDen, dpDenNgay, spacer3, btnTimKiem, btnHuyBo, spacer4, cbLoc);

        // ScrollPane with TableView
        TableView<Object> tblPD = new TableView<>();
        tblPD.setPrefHeight(738);
        tblPD.setPrefWidth(1620);

        TableColumn<Object, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(51);
        colSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colMaPD = new TableColumn<>("Mã PĐH");
        colMaPD.setPrefWidth(156);
        colMaPD.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colNgayLap = new TableColumn<>("Ngày lập");
        colNgayLap.setPrefWidth(195);
        colNgayLap.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colTenKH = new TableColumn<>("Khách hàng");
        colTenKH.setPrefWidth(311);

        TableColumn<Object, String> colSdtKH = new TableColumn<>("Số điện thoại");
        colSdtKH.setPrefWidth(211);
        colSdtKH.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colSoTienCoc = new TableColumn<>("Số tiền cọc");
        colSoTienCoc.setPrefWidth(228);
        colSoTienCoc.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colTenNV = new TableColumn<>("Nhân viên");
        colTenNV.setPrefWidth(243);

        TableColumn<Object, String> colTT = new TableColumn<>("Trạng thái");
        colTT.setPrefWidth(120);
        colTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colChiTiet = new TableColumn<>("Chi tiết");
        colChiTiet.setPrefWidth(88);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tblPD.getColumns().addAll(colSTT, colMaPD, colNgayLap, colTenKH, colSdtKH, colSoTienCoc, colTenNV, colTT, colChiTiet);

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(tblPD);
        scroll.setPrefViewportHeight(738);
        scroll.setPrefViewportWidth(1620);

        mainVBox.getChildren().addAll(titleBox, sep, searchBox, scroll);
        root.getChildren().add(mainVBox);

        Scene scene = new Scene(root);

        // --- CẬP NHẬT: Gắn CSS vào Root Pane ---
        String cssPath = "/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css";
        java.net.URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("Đã gắn CSS vào Root Pane (TKPhieuDatHang) thành công!");
        } else {
            // Fallback: Thử tìm đường dẫn ngắn
            var shortUrl = getClass().getResource("/css/TimKiemHoaDon.css");
            if(shortUrl != null) {
                root.getStylesheets().add(shortUrl.toExternalForm());
            } else {
                System.err.println("LỖI: Không tìm thấy file CSS tại: " + cssPath);
            }
        }

        // Inject into controller (Giữ nguyên logic cũ của bạn)
        try {
            ctrl.cboTimKiem = cboTimKiem;
            ctrl.txtNoiDungTimKiem = txtNoiDungTimKiem;
            ctrl.dpTuNgay = dpTuNgay;
            ctrl.dpDenNgay = dpDenNgay;
            ctrl.btnTimKiem = btnTimKiem;
            ctrl.btnHuyBo = btnHuyBo;
            ctrl.cbLoc = cbLoc;
            ctrl.tblPD = (TableView) tblPD;
            ctrl.colSTT = (TableColumn) colSTT;
            ctrl.colMaPD = (TableColumn) colMaPD;
            ctrl.colNgayLap = (TableColumn) colNgayLap;
            ctrl.colTenKH = (TableColumn) colTenKH;
            ctrl.colSdtKH = (TableColumn) colSdtKH;
            ctrl.colSoTienCoc = (TableColumn) colSoTienCoc;
            ctrl.colTenNV = (TableColumn) colTenNV;
            ctrl.colTT = (TableColumn) colTT;
            ctrl.colChiTiet = (TableColumn) colChiTiet;
        } catch (Exception ignored) {}

        // Initialize controller logic
        ctrl.initialize();

        stage.setTitle("Tìm kiếm phiếu đặt hàng");
        stage.setScene(scene);
    }
}
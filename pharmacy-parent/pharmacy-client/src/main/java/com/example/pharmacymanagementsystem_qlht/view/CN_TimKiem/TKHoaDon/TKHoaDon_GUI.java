package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoaDon;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.TimKiemHoaDon_Ctrl;
import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.TimKiemHoaDon_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.HoaDon;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TKHoaDon_GUI {

    public static void showWithController(Stage stage, TimKiemHoaDon_Ctrl ctrl) {

        Pane mainPane = new Pane();
        mainPane.setPrefSize(1646, 895);
        mainPane.setStyle("-fx-font-size: 14px;");
        mainPane.getStylesheets().add(
                TKHoaDon_GUI.class.getResource(
                        "/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css"
                ).toExternalForm()
        );

        VBox vBox = new VBox();
        vBox.setLayoutX(10);
        vBox.setLayoutY(14);
        vBox.setPrefSize(1619, 871);

        // ===== Header =====
        HBox hBoxTitle = new HBox();
        hBoxTitle.setPrefSize(328, 58);

        Label lbTimKiem = new Label("Tìm kiếm hóa đơn");
        lbTimKiem.setPrefSize(328, 53);
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setFont(new Font(36));
        ImageView ivBill = new ImageView(new Image(
                TKHoaDon_GUI.class.getResourceAsStream(
                        "/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png"
                )
        ));
        ivBill.setFitHeight(46);
        ivBill.setFitWidth(48);


        hBoxTitle.getChildren().addAll(lbTimKiem, ivBill);

        Separator separator = new Separator();

        // ===== Thanh tìm kiếm =====
        HBox hBoxSearch = new HBox(5);
        hBoxSearch.setPrefSize(1618, 63);
        VBox.setMargin(hBoxSearch, new Insets(5, 0, 5, 0));

        ComboBox<String> cboTieuChiTimKiem = new ComboBox<>();
        cboTieuChiTimKiem.setPromptText("Tiêu chí tìm kiếm");
        cboTieuChiTimKiem.setPrefSize(184, 41);
        cboTieuChiTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(cboTieuChiTimKiem, new Insets(10, 5, 0, 0));

        TextField txtNoiDungTimKiem = new TextField();
        txtNoiDungTimKiem.setPromptText("Nhập nội dung tìm kiếm");
        txtNoiDungTimKiem.setPrefSize(260, 40);
        txtNoiDungTimKiem.getStyleClass().add("tftim");
        HBox.setMargin(txtNoiDungTimKiem, new Insets(10, 0, 0, 0));

        Region region1 = new Region();
        region1.setPrefSize(32, 63);

        ImageView ivTime = new ImageView(new Image(
                TKHoaDon_GUI.class.getResourceAsStream(
                        "/com/example/pharmacymanagementsystem_qlht/img/time-2623.png"
                )
        ));
        ivTime.setFitHeight(30);
        ivTime.setFitWidth(30);

        // --- THÊM 2 DÒNG NÀY ĐỂ KHÓA VỊ TRÍ ---
        ivTime.setTranslateX(0);
        ivTime.setTranslateY(0);
        // --------------------------------------

        HBox.setMargin(ivTime, new Insets(16, 4, 0, 0));

        Label lbTu = new Label("Từ:");
        lbTu.setPrefSize(32, 25);
        lbTu.getStyleClass().add("tftim");
        lbTu.setFont(new Font(14));
        HBox.setMargin(lbTu, new Insets(10, 0, 0, 0));

        DatePicker dpTuNgay = new DatePicker();
        dpTuNgay.setPrefSize(125, 40);
        dpTuNgay.getStyleClass().add("tftim");
        HBox.setMargin(dpTuNgay, new Insets(10, 0, 0, 0));

        Label lbDen = new Label("Đến:");
        lbDen.setPrefSize(38, 25);
        lbDen.getStyleClass().add("tftim");
        lbDen.setFont(new Font(14));
        HBox.setMargin(lbDen, new Insets(10, 0, 0, 10));

        DatePicker dpDenNgay = new DatePicker();
        dpDenNgay.setPrefSize(125, 40);
        dpDenNgay.getStyleClass().add("tftim");
        HBox.setMargin(dpDenNgay, new Insets(10, 0, 0, 0));

        Region region2 = new Region();
        region2.setPrefSize(25, 63);

        // ===== Nút tìm =====
        Button btnTimKiem = new Button("Tìm");
        btnTimKiem.setPrefSize(72, 40);
        btnTimKiem.setDefaultButton(true);
        btnTimKiem.setContentDisplay(ContentDisplay.RIGHT);
        btnTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(btnTimKiem, new Insets(10, 0, 0, 5));

        ImageView ivSearch = new ImageView(new Image(
                TKHoaDon_GUI.class.getResourceAsStream(
                        "/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png"
                )
        ));
        ivSearch.setFitHeight(20);
        ivSearch.setFitWidth(20);
        ivSearch.setCursor(Cursor.DEFAULT);
        btnTimKiem.setGraphic(ivSearch);

        // ===== Nút hủy =====
        Button btnHuyBo = new Button();
        btnHuyBo.setPrefSize(52, 40);
        btnHuyBo.getStyleClass().add("btntim");
        btnHuyBo.setId("btnReset");
        btnHuyBo.getStylesheets().add(
                TKHoaDon_GUI.class.getResource(
                        "/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css"
                ).toExternalForm()
        );
        HBox.setMargin(btnHuyBo, new Insets(10, 0, 0, 5));

        ImageView ivRefresh = new ImageView(new Image(
                TKHoaDon_GUI.class.getResourceAsStream(
                        "/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png"
                )
        ));
        ivRefresh.setFitHeight(20);
        ivRefresh.setFitWidth(20);
        btnHuyBo.setGraphic(ivRefresh);

        Region spacerRight = new Region();
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        ComboBox<String> cboBoLocNhanh = new ComboBox<>();
        cboBoLocNhanh.setPromptText("⌛ Bộ lọc nhanh");
        cboBoLocNhanh.setPrefSize(222, 41);
        cboBoLocNhanh.getStyleClass().add("btntim");
        HBox.setMargin(cboBoLocNhanh, new Insets(10, 0, 0, 0));

        hBoxSearch.getChildren().addAll(
                cboTieuChiTimKiem, txtNoiDungTimKiem, region1, ivTime,
                lbTu, dpTuNgay, lbDen, dpDenNgay,
                region2, btnTimKiem, btnHuyBo, spacerRight, cboBoLocNhanh
        );

        StackPane rootTablePane = new StackPane();
        rootTablePane.setId("rootTablePane");

        // position and size like the old table
        rootTablePane.setLayoutX(12);
        rootTablePane.setLayoutY(106);
        rootTablePane.setPrefSize(1619, 735);

        // ===== Bảng =====
        TableView<HoaDon> tblHD = new TableView<>();
        tblHD.setPrefSize(1619, 735);
        tblHD.setStyle("-fx-font-size: 14px;");

        TableColumn<HoaDon, String> colMaHD = new TableColumn<>("Mã hóa đơn");
        colMaHD.setPrefWidth(162);
        colMaHD.setStyle("-fx-alignment: CENTER;");

        TableColumn<HoaDon, String> colNgayLap = new TableColumn<>("Ngày lập");
        colNgayLap.setPrefWidth(255);
        colNgayLap.setStyle("-fx-alignment: CENTER;");

        TableColumn<HoaDon, String> colTenKH = new TableColumn<>("Khách hàng");
        colTenKH.setPrefWidth(351);

        TableColumn<HoaDon, String> colSdtKH = new TableColumn<>("SĐT");
        colSdtKH.setPrefWidth(229.6);
        colSdtKH.setStyle("-fx-alignment: CENTER;");

        TableColumn<HoaDon, String> colTenNV = new TableColumn<>("Nhân viên");
        colTenNV.setPrefWidth(293);

        TableColumn<HoaDon, Integer> colSLP = new TableColumn<>("Số lượng phiếu đổi trả");
        colSLP.setPrefWidth(205);
        colSLP.setStyle("-fx-alignment: CENTER;");

        TableColumn<HoaDon, String> colChiTiet = new TableColumn<>("Chi tiết");
        colChiTiet.setPrefWidth(105);
        colChiTiet.setStyle("-fx-alignment: CENTER;");


        tblHD.getColumns().addAll(colMaHD, colNgayLap, colTenKH, colSdtKH, colTenNV, colSLP, colChiTiet);

        rootTablePane.getChildren().addAll(tblHD);

        vBox.getChildren().addAll(hBoxTitle, separator, hBoxSearch, rootTablePane);
        mainPane.getChildren().add(vBox);

        // ===== Gán vào controller =====
        ctrl.tblHD = tblHD;
        ctrl.colMaHD = colMaHD;
        ctrl.colNgayLap = colNgayLap;
        ctrl.colTenKH = colTenKH;
        ctrl.colSdtKH = colSdtKH;
        ctrl.colTenNV = colTenNV;
        ctrl.colSLP = colSLP;
        ctrl.colChiTiet = colChiTiet;
        ctrl.cboTieuChiTimKiem = cboTieuChiTimKiem;
        ctrl.txtNoiDungTimKiem = txtNoiDungTimKiem;
        ctrl.dpTuNgay = dpTuNgay;
        ctrl.dpDenNgay = dpDenNgay;
        ctrl.cboBoLocNhanh = cboBoLocNhanh;
        ctrl.btnTimKiem = btnTimKiem;
        ctrl.btnHuyBo = btnHuyBo;
        ctrl.rootTablePane = rootTablePane;

        ctrl.initialize();

        // ===== Scene =====
        Scene scene = new Scene(mainPane);
        stage.setTitle("Tìm kiếm hóa đơn");
        stage.setScene(scene);
    }
}


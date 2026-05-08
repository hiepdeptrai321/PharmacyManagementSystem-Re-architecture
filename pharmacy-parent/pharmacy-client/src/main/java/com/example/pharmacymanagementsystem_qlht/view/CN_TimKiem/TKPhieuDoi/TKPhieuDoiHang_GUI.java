package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang.TKPhieuDoiHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDoiHang;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TKPhieuDoiHang_GUI extends Application {

    public Pane mainPane;
    public TableView<PhieuDoiHang> tblPD;
    public TableColumn<PhieuDoiHang, Number> colSTT;
    public TableColumn<PhieuDoiHang, String> colMaPD, colMaHD, colNgayLap, colTenKH, colSdtKH, colTenNV, colChiTiet;
    public ComboBox<String> cboTimKiem, cbLoc;
    public TextField txtNoiDungTimKiem;
    public DatePicker dpTuNgay, dpDenNgay;
    public Button btnTimKiem, btnHuyBo;

    @Override
    public void start(Stage stage) {
        TKPhieuDoiHang_Ctrl ctrl = new TKPhieuDoiHang_Ctrl();
        showWithController(stage, ctrl);
    }

    public void showWithController(Stage stage, TKPhieuDoiHang_Ctrl ctrl) {
        // Pane chính
        mainPane = new Pane();
        mainPane.setPrefSize(1646, 895);
        mainPane.setStyle("-fx-font-size: 14px;");
        mainPane.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css").toExternalForm());

        VBox vbox = new VBox(5);
        vbox.setLayoutX(10);
        vbox.setLayoutY(14);

        // --- Tiêu đề ---
        HBox hbTitle = new HBox();
        Label lbTimKiem = new Label("Tìm kiếm phiếu đổi hàng");
        lbTimKiem.setPrefSize(449, 53);
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setFont(new Font(36));
        ImageView ivTitle = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png").toExternalForm()));
        ivTitle.setFitHeight(46);
        ivTitle.setFitWidth(48);
        hbTitle.getChildren().addAll(lbTimKiem, new Label(), ivTitle);

        Separator sep = new Separator();

        // --- Thanh tìm kiếm ---
        HBox searchBox = new HBox();
        searchBox.setPrefSize(1607, 68);

        ctrl.cboTimKiem = new ComboBox<>();
        ctrl.cboTimKiem.setPromptText("Tìm theo");
        ctrl.cboTimKiem.setPrefSize(200, 41);
        ctrl.cboTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(ctrl.cboTimKiem, new Insets(10, 5, 0, 0));

        ctrl.txtNoiDungTimKiem = new TextField();
        ctrl.txtNoiDungTimKiem.setPromptText("Nhập thông tin phiếu trả");
        ctrl.txtNoiDungTimKiem.setPrefSize(251, 40);
        ctrl.txtNoiDungTimKiem.getStyleClass().add("tftim");
        HBox.setMargin(ctrl.txtNoiDungTimKiem, new Insets(10, 0, 0, 0));

        ImageView iconTime = new ImageView();
        try {
            iconTime = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/time-2623.png").toExternalForm()));
        } catch (Exception e) { }

        iconTime.setFitWidth(30);
        iconTime.setFitHeight(30);
        iconTime.setPreserveRatio(true);
        HBox.setMargin(iconTime, new Insets(16, 4, 0, 40));

        Label lblTu = new Label("Từ: ");
        lblTu.setPrefSize(32, 25);
        lblTu.getStyleClass().add("tftim");
        HBox.setMargin(lblTu, new Insets(10, 0, 0, 0));

        ctrl.dpTuNgay = new DatePicker();
        ctrl.dpTuNgay.setPrefSize(125, 40);
        ctrl.dpTuNgay.getStyleClass().add("tftim");
        HBox.setMargin(ctrl.dpTuNgay, new Insets(10, 0, 0, 0));

        Label lblDen = new Label("Đến: ");
        lblDen.setPrefSize(38, 25);
        lblDen.getStyleClass().add("tftim");
        HBox.setMargin(lblDen, new Insets(10, 0, 0, 10));

        ctrl.dpDenNgay = new DatePicker();
        ctrl.dpDenNgay.setPrefSize(125, 40);
        ctrl.dpDenNgay.getStyleClass().add("tftim");
        HBox.setMargin(ctrl.dpDenNgay, new Insets(10, 0, 0, 0));

        Region rg2 = new Region();
        rg2.setPrefSize(30, 63);

        ctrl.btnTimKiem = new Button("Tìm");
        ctrl.btnTimKiem.setPrefSize(78, 40);
        ctrl.btnTimKiem.setDefaultButton(true);
        ctrl.btnTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(ctrl.btnTimKiem, new Insets(10, 0, 0, 15));

        ImageView imgSearch = new ImageView();
        try {
            imgSearch = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png").toExternalForm()));
        } catch (Exception e) {}

        imgSearch.setFitWidth(20);
        imgSearch.setFitHeight(20);
        imgSearch.setCursor(Cursor.DEFAULT);
        ctrl.btnTimKiem.setGraphic(imgSearch);

        ctrl.btnHuyBo = new Button();
        ctrl.btnHuyBo.setPrefSize(52, 40);
        ctrl.btnHuyBo.setId("btnReset");
        ctrl.btnHuyBo.getStyleClass().add("btntim");
        HBox.setMargin(ctrl.btnHuyBo, new Insets(10, 8, 0, 8));

        ImageView imgRefresh = new ImageView();
        try {
            imgRefresh = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png").toExternalForm()));
        } catch(Exception e){}

        imgRefresh.setFitWidth(20);
        imgRefresh.setFitHeight(20);
        ctrl.btnHuyBo.setGraphic(imgRefresh);

        Region rg3 = new Region();
        rg3.setPrefSize(390, 68);

        ctrl.cbLoc = new ComboBox<>();
        ctrl.cbLoc.setPromptText("⌛ Bộ lọc nhanh");
        ctrl.cbLoc.setPrefSize(185, 41);
        ctrl.cbLoc.getStyleClass().add("btntim");
        HBox.setMargin(ctrl.cbLoc, new Insets(10, 0, 0, 0));

        searchBox.getChildren().addAll(
                ctrl.cboTimKiem, ctrl.txtNoiDungTimKiem,
                iconTime, lblTu, ctrl.dpTuNgay,
                lblDen, ctrl.dpDenNgay, rg2,
                ctrl.btnTimKiem, ctrl.btnHuyBo,
                rg3, ctrl.cbLoc
        );

        // --- Bảng dữ liệu ---
        tblPD = new TableView<>();
        tblPD.setPrefSize(1618, 747);

        colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(51);
        colSTT.setStyle("-fx-alignment: CENTER;");

        colMaPD = new TableColumn<>("Mã phiếu đổi");
        colMaPD.setPrefWidth(193);
        colMaPD.setStyle("-fx-alignment: CENTER;");

        colMaHD = new TableColumn<>("Mã hóa đơn");
        colMaHD.setPrefWidth(213);
        colMaHD.setStyle("-fx-alignment: CENTER;");

        colNgayLap = new TableColumn<>("Ngày lập");
        colNgayLap.setPrefWidth(228);
        colNgayLap.setStyle("-fx-alignment: CENTER;");

        colTenKH = new TableColumn<>("Khách hàng");
        colTenKH.setPrefWidth(321);

        colSdtKH = new TableColumn<>("SĐT");
        colSdtKH.setPrefWidth(235);
        colSdtKH.setStyle("-fx-alignment: CENTER;");

        colTenNV = new TableColumn<>("Nhân viên");
        colTenNV.setPrefWidth(262);

        colChiTiet = new TableColumn<>("Chi tiết");
        colChiTiet.setPrefWidth(95);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tblPD.getColumns().addAll(colSTT, colMaPD, colMaHD, colNgayLap, colTenKH, colSdtKH, colTenNV, colChiTiet);

        ScrollPane scroll = new ScrollPane(tblPD);

        // --- Thêm vào layout ---
        vbox.getChildren().addAll(hbTitle, sep, searchBox, scroll);
        mainPane.getChildren().add(vbox);

        // --- Gán controller ---
        ctrl.tblPD =  tblPD;
        ctrl.colSTT =  colSTT;
        ctrl.colMaPD =  colMaPD;
        ctrl.colMaHD = colMaHD;
        ctrl.colNgayLap =  colNgayLap;
        ctrl.colTenKH = colTenKH;
        ctrl.colSdtKH =  colSdtKH;
        ctrl.colTenNV =  colTenNV;
        ctrl.colChiTiet = colChiTiet;
//        ctrl.cboTimKiem = cboTimKiem;
//        ctrl.txtNoiDungTimKiem = txtNoiDungTimKiem;
//        ctrl.dpTuNgay = dpTuNgay;
//        ctrl.dpDenNgay = dpDenNgay;
//        ctrl.cbLoc = cbLoc;
//        ctrl.btnTimKiem = btnTimKiem;
//        ctrl.btnHuyBo = btnHuyBo;
        ctrl.initialize();
        // --- Hiển thị ---
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Tìm kiếm phiếu đổi hàng");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
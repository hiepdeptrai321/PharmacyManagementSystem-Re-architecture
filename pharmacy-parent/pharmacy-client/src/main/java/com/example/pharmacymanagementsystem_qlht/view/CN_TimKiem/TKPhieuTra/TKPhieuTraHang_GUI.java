package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuTra;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang.TKPhieuTraHang_Ctrl;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TKPhieuTraHang_GUI extends Application {

    // Controller reference
    private TKPhieuTraHang_Ctrl ctrl;

    // Constructor
    public TKPhieuTraHang_GUI() {}

    // showWithController for real usage
    public void showWithController(Stage stage, TKPhieuTraHang_Ctrl ctrl) {
        this.ctrl = ctrl;
        Pane root = createUI(ctrl);

        // --- TẠO SCENE ---
        Scene scene = new Scene(root, 1646, 895);

        // --- CÁCH THÊM CSS MỚI (Giống ThongKeBanHang_GUI) ---
        // Lưu ý: Đường dẫn CSS vẫn giữ nguyên của file này (TimKiemHoaDon.css)
        String cssPath = "/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css";
        java.net.URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl != null) {
            // Add thẳng vào Pane gốc -> Đi đâu cũng có CSS
            root.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("Đã gắn CSS vào Root Pane (TKPhieuTraHang) thành công!");
        } else {
            // Thử tìm đường dẫn ngắn nếu đường dẫn dài lỗi (Fallback)
            var shortUrl = getClass().getResource("/css/TimKiemHoaDon.css");
            if(shortUrl != null) {
                root.getStylesheets().add(shortUrl.toExternalForm());
                System.out.println("Đã gắn CSS (đường dẫn ngắn) thành công!");
            } else {
                System.err.println("LỖI: Không tìm thấy file CSS tại: " + cssPath);
            }
        }

        stage.setScene(scene);
        stage.setTitle("Tìm kiếm phiếu trả hàng");
        ctrl.initialize();
    }

    @Override
    public void start(Stage stage) {
        showWithController(stage, new TKPhieuTraHang_Ctrl());
    }

    private Pane createUI(TKPhieuTraHang_Ctrl ctrl) {
        Pane mainPane = new Pane();
        mainPane.setPrefSize(1646, 895);
        // Có thể bỏ dòng setStyle cứng này nếu trong CSS file đã có quy định font
        mainPane.setStyle("-fx-font-size: 14px;");

        VBox vbox = new VBox(5);
        vbox.setLayoutX(10);
        vbox.setLayoutY(14);

        // --- Tiêu đề ---
        HBox titleBox = new HBox();
        titleBox.setPrefSize(782, 46);

        Label lbTimKiem = new Label("Tìm kiếm phiếu trả hàng");
        lbTimKiem.setPrefSize(449, 53);
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setFont(new Font(36));

        // Lưu ý: Cần đảm bảo ảnh tồn tại để tránh NullPointerException,
        // nhưng ở đây tôi giữ nguyên logic ảnh của bạn
        ImageView imgTitle = new ImageView();
        try {
            imgTitle = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png").toExternalForm()));
        } catch (Exception e) {
            System.err.println("Không tìm thấy ảnh title");
        }

        imgTitle.setFitWidth(48);
        imgTitle.setFitHeight(46);
        imgTitle.setPreserveRatio(true);

        titleBox.getChildren().addAll(lbTimKiem, new Label(), imgTitle);

        Separator sep = new Separator();
        sep.setPrefWidth(200);

        // --- Thanh tìm kiếm ---
        HBox searchBox = new HBox();
        searchBox.setPrefSize(1607, 68);

        ctrl.cboTimKiem = new ComboBox<>();
        ctrl.cboTimKiem.setPromptText("Tìm theo");
        ctrl.cboTimKiem.setPrefSize(200, 40);
        ctrl.cboTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(ctrl.cboTimKiem, new Insets(10, 5, 0, 0));

        ctrl.txtNoiDungTimKiem = new TextField();
        ctrl.txtNoiDungTimKiem.setPromptText("Nhập thông tin phiếu trả");
        ctrl.txtNoiDungTimKiem.setPrefSize(251, 40);
        ctrl.txtNoiDungTimKiem.getStyleClass().add("tftim");
        HBox.setMargin(ctrl.txtNoiDungTimKiem, new Insets(10, 0, 0, 0));

        Region rg1 = new Region();
        rg1.setPrefSize(30, 51);

        ImageView iconTime = new ImageView();
        try {
            iconTime = new ImageView(new Image(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/time-2623.png").toExternalForm()));
        } catch (Exception e) { }

        iconTime.setFitWidth(28);
        iconTime.setFitHeight(27);
        iconTime.setPreserveRatio(true);
        HBox.setMargin(iconTime, new Insets(16, 4, 0, 0));

        Label lblTu = new Label("Từ: ");
        lblTu.setPrefSize(27, 37);
        lblTu.getStyleClass().add("tftim");
        HBox.setMargin(lblTu, new Insets(10, 0, 0, 0));

        ctrl.dpTuNgay = new DatePicker();
        ctrl.dpTuNgay.setPrefSize(125, 40);
        ctrl.dpTuNgay.getStyleClass().add("tftim");
        HBox.setMargin(ctrl.dpTuNgay, new Insets(10, 10, 0, 0));

        Label lblDen = new Label("Đến: ");
        lblDen.setPrefSize(35, 37);
        lblDen.getStyleClass().add("tftim");
        HBox.setMargin(lblDen, new Insets(10, 0, 0, 0));

        ctrl.dpDenNgay = new DatePicker();
        ctrl.dpDenNgay.setPrefSize(125, 40);
        ctrl.dpDenNgay.getStyleClass().add("tftim");
        HBox.setMargin(ctrl.dpDenNgay, new Insets(10, 0, 0, 0));

        Region rg2 = new Region();
        rg2.setPrefSize(30, 51);

        ctrl.btnTimKiem = new Button("Tìm");
        ctrl.btnTimKiem.setPrefSize(78, 40);
        ctrl.btnTimKiem.setDefaultButton(true);
        ctrl.btnTimKiem.getStyleClass().add("btntim");
        HBox.setMargin(ctrl.btnTimKiem, new Insets(10, 8, 0, 8));

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
        rg3.setPrefSize(405, 68);

        ctrl.cbLoc = new ComboBox<>();
        ctrl.cbLoc.setPromptText("⌛ Bộ lọc nhanh");
        ctrl.cbLoc.setPrefSize(185, 41);
        ctrl.cbLoc.getStyleClass().add("btntim");
        HBox.setMargin(ctrl.cbLoc, new Insets(10, 0, 0, 0));

        searchBox.getChildren().addAll(
                ctrl.cboTimKiem, ctrl.txtNoiDungTimKiem, rg1,
                iconTime, lblTu, ctrl.dpTuNgay, lblDen, ctrl.dpDenNgay,
                rg2, ctrl.btnTimKiem, ctrl.btnHuyBo, rg3, ctrl.cbLoc
        );

        // --- Bảng ---
        ctrl.tblPT = new TableView<>();
        ctrl.tblPT.setPrefSize(1605, 749);

        ctrl.colSTT = new TableColumn<>("STT");
        ctrl.colSTT.setPrefWidth(51);
        ctrl.colSTT.setStyle("-fx-alignment: CENTER;");

        ctrl.colMaPT = new TableColumn<>("Mã phiếu trả");
        ctrl.colMaPT.setPrefWidth(189.33);
        ctrl.colMaPT.setStyle("-fx-alignment: CENTER;");

        ctrl.colMaHD = new TableColumn<>("Mã hóa đơn");
        ctrl.colMaHD.setPrefWidth(182.66);
        ctrl.colMaHD.setStyle("-fx-alignment: CENTER;");

        ctrl.colNgayLap = new TableColumn<>("Ngày lập");
        ctrl.colNgayLap.setPrefWidth(216.33);
        ctrl.colNgayLap.setStyle("-fx-alignment: CENTER;");

        ctrl.colTenKH = new TableColumn<>("Khách hàng");
        ctrl.colTenKH.setPrefWidth(285);

        ctrl.colSdtKH = new TableColumn<>("SĐT");
        ctrl.colSdtKH.setPrefWidth(243);
        ctrl.colSdtKH.setStyle("-fx-alignment: CENTER;");

        ctrl.colTenNV = new TableColumn<>("Nhân viên");
        ctrl.colTenNV.setPrefWidth(337);

        ctrl.colChiTiet = new TableColumn<>();
        ctrl.colChiTiet.setPrefWidth(84);
        ctrl.colChiTiet.setStyle("-fx-alignment: CENTER;");

        ctrl.tblPT.getColumns().addAll(
                ctrl.colSTT, ctrl.colMaPT, ctrl.colMaHD, ctrl.colNgayLap,
                ctrl.colTenKH, ctrl.colSdtKH, ctrl.colTenNV, ctrl.colChiTiet
        );

        ScrollPane scrollPane = new ScrollPane(ctrl.tblPT);

        // --- Gộp bố cục ---
        vbox.getChildren().addAll(titleBox, sep, searchBox, scrollPane);
        mainPane.getChildren().add(vbox);

        // Icon góc
        ImageView iconTime2 = new ImageView();
        iconTime2.setLayoutX(500);
        iconTime2.setLayoutY(-91);
        iconTime2.setFitWidth(40);
        iconTime2.setFitHeight(35);
        iconTime2.setPreserveRatio(true);
        iconTime2.getStyleClass().add("icontime");
        mainPane.getChildren().add(iconTime2);

        return mainPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
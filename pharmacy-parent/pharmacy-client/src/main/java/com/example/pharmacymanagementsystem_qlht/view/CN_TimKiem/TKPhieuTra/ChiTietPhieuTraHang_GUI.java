package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuTra;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang.ChiTietPhieuTraHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuTraHang;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox; // Import VBox dù không dùng trực tiếp, vì cấu trúc bên phải gần như là VBox
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class ChiTietPhieuTraHang_GUI extends Application {

    private static final String RESOURCE_BASE_PATH = "/com/example/pharmacymanagementsystem_qlht/";

    public static void showWithController(Stage stage,ChiTietPhieuTraHang_Ctrl ctrl) {
        AnchorPane root = buildPane(ctrl);
        Scene scene = new Scene(root);
        try {
            scene.getStylesheets().add(Objects.requireNonNull(ChiTietPhieuTraHang_GUI.class.getResource(RESOURCE_BASE_PATH + "css/ChiTietPhieuTra.css")).toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Lỗi tải CSS: Kiểm tra đường dẫn " + RESOURCE_BASE_PATH + "css/ChiTietPhieuTra.css");
        }        stage.setTitle("Chi Tiết Phiếu Trả Hàng");
        stage.setScene(scene);
        stage.show();
        ctrl.initialize();
    }

    @Override
    public void start(Stage stage) throws Exception {
        showWithController(stage, new ChiTietPhieuTraHang_Ctrl());
    }
    private static AnchorPane buildPane(ChiTietPhieuTraHang_Ctrl ctrl) {

        // === 1. Root AnchorPane ===
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14 px;");

        // === 2. SplitPane ===
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.7037712895377128);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);
        AnchorPane.setTopAnchor(splitPane, 0.0);

        // === 3. Left AnchorPane (Danh sách sản phẩm) ===
        AnchorPane leftAnchor = new AnchorPane();
        leftAnchor.setMinHeight(0.0);
        leftAnchor.setMinWidth(0.0);

        Pane leftPane = new Pane();
        AnchorPane.setBottomAnchor(leftPane, 0.0);
        AnchorPane.setLeftAnchor(leftPane, 0.0);
        AnchorPane.setRightAnchor(leftPane, 0.0);
        AnchorPane.setTopAnchor(leftPane, 0.0);

        // - Tiêu đề danh sách
        Label lbDSPTra = new Label("DANH SÁCH SẢN PHẨM TRẢ");
        lbDSPTra.setLayoutX(14.0);
        lbDSPTra.setLayoutY(52.0);
        lbDSPTra.getStyleClass().add("section-label");

        // - Thông tin Mã và Ngày lập (Top left)
        Label lbMaPT = new Label("Mã phiếu trả: ");
        lbMaPT.setLayoutX(10.0);
        lbMaPT.setLayoutY(13.0);
        lbMaPT.getStyleClass().add("bold-label");

        Label lblMaPhieuTraValue = new Label("PT012345");
        lblMaPhieuTraValue.setId("lblMaPhieuTraValue");
        lblMaPhieuTraValue.setLayoutX(117.0);
        lblMaPhieuTraValue.setLayoutY(14.0);
        lblMaPhieuTraValue.getStyleClass().add("value-label");

        Label lbNgayLap = new Label("Ngày lập: ");
        lbNgayLap.setLayoutX(203.0);
        lbNgayLap.setLayoutY(13.0);
        lbNgayLap.getStyleClass().add("bold-label");
        lbNgayLap.setFont(Font.font("System Bold", 14.0));

        Label lblNgayLapValue = new Label("01/10/2025");
        lblNgayLapValue.setId("lblNgayLapValue");
        lblNgayLapValue.setLayoutX(282.0);
        lblNgayLapValue.setLayoutY(14.0);
        lblNgayLapValue.setPrefHeight(20.0);
        lblNgayLapValue.setPrefWidth(315.0);
        lblNgayLapValue.getStyleClass().add("value-label");

        // - TableView Chi Tiết
        TableView<ChiTietPhieuTraHang> tblChiTietPhieuTra = new TableView<>();
        tblChiTietPhieuTra.setId("tblChiTietPhieuTra");
        tblChiTietPhieuTra.setLayoutX(15.0);
        tblChiTietPhieuTra.setLayoutY(80.0);
        tblChiTietPhieuTra.setPrefHeight(792.0);
        tblChiTietPhieuTra.setPrefWidth(1134.0);
        tblChiTietPhieuTra.getStyleClass().add("main-table");
        tblChiTietPhieuTra.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);

        // Cột STT
        TableColumn<ChiTietPhieuTraHang, String> colSTT = new TableColumn<>("STT");
        colSTT.setId("colSTT");
        colSTT.setPrefWidth(61.333335876464844);
        colSTT.setStyle("-fx-alignment: CENTER;");

        // Cột Tên SP
        TableColumn<ChiTietPhieuTraHang, String> colTenSP = new TableColumn<>("Tên sản phẩm/Hoạt chất");
        colTenSP.setId("colTenSP");
        colTenSP.setPrefWidth(439.6666259765625);
        colTenSP.setStyle("-fx-alignment: CENTER;");

        // Cột Số lượng
        TableColumn<ChiTietPhieuTraHang, String> colSoLuong = new TableColumn<>("Số lượng trả");
        colSoLuong.setId("colSoLuong");
        colSoLuong.setPrefWidth(138.0);
        colSoLuong.setStyle("-fx-alignment: CENTER;");

        // Cột Đơn giá
        TableColumn<ChiTietPhieuTraHang, String> colDonGia = new TableColumn<>("Đơn giá trả");
        colDonGia.setId("colDonGia");
        colDonGia.setPrefWidth(133.33331298828125);
        colDonGia.setStyle("-fx-alignment: CENTER;");

        // Cột Lý do
        TableColumn<ChiTietPhieuTraHang, String> colLyDo = new TableColumn<>("Lý do trả");
        colLyDo.setId("colLyDo");
        colLyDo.setPrefWidth(242.0);
        colLyDo.setStyle("-fx-alignment: CENTER;");

        // Cột Thành tiền
        TableColumn<ChiTietPhieuTraHang, Double> colThanhTien = new TableColumn<>("Thành tiền trả");
        colThanhTien.setId("colThanhTien");
        colThanhTien.setPrefWidth(116.0);
        colThanhTien.setStyle("-fx-alignment: CENTER;");

        tblChiTietPhieuTra.getColumns().addAll(colSTT, colTenSP, colSoLuong, colDonGia, colLyDo, colThanhTien);

        leftPane.getChildren().addAll(lbDSPTra, tblChiTietPhieuTra, lbMaPT, lblMaPhieuTraValue, lbNgayLap, lblNgayLapValue);
        leftAnchor.getChildren().add(leftPane);

        // === 4. Right AnchorPane (Thông tin chi tiết) ===
        AnchorPane rightAnchor = new AnchorPane();
        rightAnchor.setMinHeight(0.0);
        rightAnchor.setMinWidth(0.0);
        rightAnchor.getStyleClass().add("right-pane-bg");

        Pane rightPane = new Pane();
        AnchorPane.setBottomAnchor(rightPane, 10.0);
        AnchorPane.setLeftAnchor(rightPane, 10.0);
        AnchorPane.setRightAnchor(rightPane, 10.0);
        AnchorPane.setTopAnchor(rightPane, 10.0);

        // - Tiêu đề 1
        Label lbTitle1 = new Label("THÔNG TIN PHIẾU TRẢ");
        lbTitle1.setFont(Font.font("System Bold", 24.0));
        lbTitle1.getStyleClass().add("section-label"); // FXML có section-label ở đây

        // - Pane Thông tin chi tiết (NV, KH, SĐT, Ghi chú)
        Pane infoPane = new Pane();
        infoPane.setLayoutY(41.0);
        infoPane.setPrefHeight(160.0);
        infoPane.setPrefWidth(462.0); // FXML has two overlapping panes, using the smaller one with details
        infoPane.getStyleClass().add("info-pane");

        // --- Nội dung Info Pane ---
        Label lbTenNV = new Label("Tên nhân viên lập:");
        lbTenNV.setLayoutX(5.0); lbTenNV.setLayoutY(5.0); lbTenNV.getStyleClass().add("bold-label");

        Label lblTenNV = new Label();
        lblTenNV.setId("lblTenNV");
        lblTenNV.setLayoutX(164.0); lblTenNV.setLayoutY(7.0);
        lblTenNV.setPrefHeight(20.0); lblTenNV.setPrefWidth(207.0);
        lblTenNV.setTextFill(Color.web("#3581bc"));
        lblTenNV.setFont(Font.font("System Bold", 14.0));

        Label lbTenKH = new Label("Tên khách hàng:");
        lbTenKH.setLayoutX(5.0); lbTenKH.setLayoutY(35.0); lbTenKH.getStyleClass().add("bold-label");

        Label lblTenKH = new Label();
        lblTenKH.setId("lblTenKH");
        lblTenKH.setLayoutX(151.0); lblTenKH.setLayoutY(37.0);
        lblTenKH.setPrefHeight(17.0); lblTenKH.setPrefWidth(232.0);

        Label lbSDTKH = new Label("SĐT Khách hàng:");
        lbSDTKH.setLayoutX(5.0); lbSDTKH.setLayoutY(65.0); lbSDTKH.getStyleClass().add("bold-label");

        Label lblSDTKH = new Label();
        lblSDTKH.setId("lblSDTKH");
        lblSDTKH.setLayoutX(150.0); lblSDTKH.setLayoutY(67.0);
        lblSDTKH.setPrefHeight(17.0); lblSDTKH.setPrefWidth(238.0);

        Label lbGhiChu = new Label("Ghi chú:");
        lbGhiChu.setLayoutX(5.0); lbGhiChu.setLayoutY(95.0); lbGhiChu.getStyleClass().add("bold-label");

        Label lblGhiChuValue = new Label("Không có.");
        lblGhiChuValue.setId("lblGhiChuValue");
        lblGhiChuValue.setLayoutX(93.0); lblGhiChuValue.setLayoutY(95.0);
        lblGhiChuValue.setPrefWidth(200.0); lblGhiChuValue.getStyleClass().add("value-label");
        lblGhiChuValue.setWrapText(true);


        // FXML Labels (redundant but need to be mapped)
        Label lblTenNhanVienValue = new Label();
        lblTenNhanVienValue.setId("lblTenNhanVienValue");
        lblTenNhanVienValue.setLayoutX(160.0); lblTenNhanVienValue.setLayoutY(5.0);
        lblTenNhanVienValue.getStyleClass().add("value-label");

        Label lblTenKhachHangValue = new Label();
        lblTenKhachHangValue.setId("lblTenKhachHangValue");
        lblTenKhachHangValue.setLayoutX(160.0); lblTenKhachHangValue.setLayoutY(35.0);
        lblTenKhachHangValue.getStyleClass().add("value-label");

        Label lblSDTKhachHangValue = new Label();
        lblSDTKhachHangValue.setId("lblSDTKhachHangValue");
        lblSDTKhachHangValue.setLayoutX(160.0); lblSDTKhachHangValue.setLayoutY(65.0);
        lblSDTKhachHangValue.getStyleClass().add("value-label");

        infoPane.getChildren().addAll(lbTenNV, lblTenNhanVienValue, lbTenKH, lblTenKhachHangValue, lbSDTKH, lblSDTKhachHangValue, lbGhiChu, lblGhiChuValue, lblTenNV, lblTenKH, lblSDTKH);

        // - Separator 1
        Separator separator1 = new Separator();
        separator1.setLayoutY(216.0);

        // - Tiêu đề 2
        Label lbTitle2 = new Label("TÓM TẮT TRẢ HÀNG");
        lbTitle2.setLayoutY(234.4);
        lbTitle2.setFont(Font.font("System Bold", 24.0));

        // - Pane Tóm tắt trả hàng
        Pane summaryPane = new Pane();
        summaryPane.setLayoutY(275.0);
        summaryPane.setPrefHeight(280.0);
        summaryPane.setPrefWidth(545.0);
        summaryPane.getStyleClass().add("summary-pane");

        // --- Nội dung Summary Pane ---

        // Tổng tiền trả
        Pane paneTien = new Pane();
        paneTien.setLayoutX(10);
        paneTien.setLayoutY(10);
        paneTien.setPrefWidth(550);
        paneTien.setPrefHeight(140);
        paneTien.setStyle("""
        -fx-background-color: #fafafa;
        -fx-border-color: #e0e0e0;
        -fx-border-radius: 8;
        -fx-background-radius: 8;
        -fx-padding: 10;
    """);
        Label lbTongTienHDGoc = new Label("Tổng tiền hóa đơn gốc");
        lbTongTienHDGoc.setLayoutX(15);
        lbTongTienHDGoc.setLayoutY(15);
        lbTongTienHDGoc.setStyle("-fx-font-size: 12px; -fx-text-fill: #616161;");

        Label lblTongTienHDGocValue = new Label("0 VNĐ");
        lblTongTienHDGocValue.setId("lblTongTienHDGocValue");
        lblTongTienHDGocValue.setLayoutX(400);
        lblTongTienHDGocValue.setLayoutY(15);
        lblTongTienHDGocValue.setStyle("""
    -fx-font-size: 13px;
    -fx-font-weight: bold;
    -fx-text-fill: #424242;
""");
        Separator sep = new Separator();
        sep.setLayoutX(15);
        sep.setLayoutY(45);
        sep.setPrefWidth(520);
        sep.setOpacity(0.3);
        Label lbSoTienTraLai = new Label("SỐ TIỀN TRẢ LẠI");
        lbSoTienTraLai.setLayoutX(15);
        lbSoTienTraLai.setLayoutY(60);
        lbSoTienTraLai.setStyle("""
        -fx-font-size: 13px;
        -fx-font-weight: bold;
        -fx-text-fill: #333333;
        """);

        Label lblSoTienTraLaiValue = new Label("0 VNĐ");
        lblSoTienTraLaiValue.setId("lblSoTienTraLaiValue");
        lblSoTienTraLaiValue.setLayoutX(15);
        lblSoTienTraLaiValue.setLayoutY(85);
        lblSoTienTraLaiValue.setStyle("""
        -fx-font-size: 26px;
        -fx-font-weight: bold;
        -fx-text-fill: #d32f2f;
        """);
        paneTien.getChildren().addAll(
                lbTongTienHDGoc,
                lblTongTienHDGocValue,
                sep,
                lbSoTienTraLai,
                lblSoTienTraLaiValue
        );

        summaryPane.getChildren().addAll(paneTien);

        // - Pane Buttons
        Pane buttonPane = new Pane();
        buttonPane.setLayoutX(-11.0);
        buttonPane.setLayoutY(561.4000244140625);

        DropShadow dropShadow = new DropShadow(10.0, Color.web("#a7a4a4")); // Màu đổ bóng theo FXML

        // Button In Phiếu Trả
        Button btnInPhieuTra = new Button("In Phiếu Trả");
        btnInPhieuTra.setId("btnInPhieuTra");
        btnInPhieuTra.setLayoutX(10.0);
        btnInPhieuTra.setPrefHeight(58.0);
        btnInPhieuTra.setPrefWidth(472.0);
        btnInPhieuTra.getStyleClass().add("print-btn");
        btnInPhieuTra.setFont(Font.font("System Bold", 14.0));
        btnInPhieuTra.setEffect(dropShadow);

        // Button Đóng
        Button btnDong = new Button("Đóng");
        btnDong.setId("btnDong");
        btnDong.setLayoutX(9.0);
        btnDong.setLayoutY(68.0);
        btnDong.setPrefHeight(58.0);
        btnDong.setPrefWidth(473.0);
        btnDong.getStyleClass().add("danger-btn");
        btnDong.setFont(Font.font("System Bold", 14.0));
        btnDong.setEffect(dropShadow);

        buttonPane.getChildren().addAll(btnInPhieuTra, btnDong);

        rightPane.getChildren().addAll(lbTitle1, infoPane, separator1, lbTitle2, summaryPane, buttonPane);
        rightAnchor.getChildren().add(rightPane);

        // === 5. Kết hợp và Tiêm Controls vào Controller ===
        splitPane.getItems().addAll(leftAnchor, rightAnchor);
        root.getChildren().add(splitPane);

        // Gán Controls vào Controller
        if (ctrl != null) {
            ctrl.tblChiTietPhieuTra = tblChiTietPhieuTra;
            ctrl.colSTT = colSTT;
            ctrl.colTenSP = colTenSP;
            ctrl.colSoLuong = colSoLuong;
            ctrl.colDonGia = colDonGia;
            ctrl.colLyDo = colLyDo;
            ctrl.colThanhTien = colThanhTien;
            ctrl.lblMaPhieuTraValue = lblMaPhieuTraValue;
            ctrl.lblNgayLapValue = lblNgayLapValue;
            ctrl.lblTenNhanVienValue = lblTenNhanVienValue;
            ctrl.lblTenNV = lblTenNV;
            ctrl.lblTenKhachHangValue = lblTenKhachHangValue;
            ctrl.lblTenKH = lblTenKH;
            ctrl.lblSDTKhachHangValue = lblSDTKhachHangValue;
            ctrl.lblSDTKH = lblSDTKH;
            ctrl.lblGhiChuValue = lblGhiChuValue;
            ctrl.lblTongTienHDGocValue = lblTongTienHDGocValue;
            ctrl.lblSoTienTraLaiValue = lblSoTienTraLaiValue;
            ctrl.btnInPhieuTra = btnInPhieuTra;
            ctrl.btnDong = btnDong;
            ctrl.onUIReady();
        }


        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuTra;

import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuTra.LapPhieuTraHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import com.example.pharmacymanagementsystem_qlht.service.TraHangItem;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class LapPhieuTra_GUI {

    public void showWithController(Stage stage, LapPhieuTraHang_Ctrl ctrl) {

        // ===== Root =====
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/LapPhieuTraHang.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/LapPhieuDoiHang.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/Chung.css").toExternalForm());

        // ===== Header =====
        Pane headerPane = new Pane();
        headerPane.setPrefSize(1587, 54);
        headerPane.getStyleClass().add("header-pane");
        AnchorPane.setTopAnchor(headerPane, 8.0);
        AnchorPane.setLeftAnchor(headerPane, 28.0);
        AnchorPane.setRightAnchor(headerPane, 31.0);

        ImageView ivIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/return_11153370.png")));
        ivIcon.setFitHeight(53);
        ivIcon.setFitWidth(67);
        ivIcon.setLayoutX(297);
        ivIcon.setLayoutY(1);
        headerPane.getChildren().add(ivIcon);

        Label lblTitle = new Label("LẬP PHIẾU TRẢ HÀNG");
        lblTitle.getStyleClass().add("title-label");
        lblTitle.setLayoutX(40);
        lblTitle.setLayoutY(21);
        AnchorPane.setTopAnchor(lblTitle, 18.0);
        AnchorPane.setLeftAnchor(lblTitle, 40.0);

        // ===== Search Pane =====
        Pane searchPane = new Pane();
        searchPane.setPrefSize(1167, 70);
        searchPane.getStyleClass().add("search-pane");
        AnchorPane.setTopAnchor(searchPane, 71.0);
        AnchorPane.setLeftAnchor(searchPane, 30.0);
        AnchorPane.setRightAnchor(searchPane, 449.0);

        Label lblTimHD = new Label("Tìm hóa đơn gốc:");
        lblTimHD.setLayoutX(20);
        lblTimHD.setLayoutY(20);
        lblTimHD.setFont(Font.font("System Bold", 13));

        TextField txtTimHoaDon = new TextField();
        txtTimHoaDon.setId("txtTimHoaDonGoc");
        txtTimHoaDon.setLayoutX(140);
        txtTimHoaDon.setLayoutY(15);
        txtTimHoaDon.setPrefSize(939, 29);
        txtTimHoaDon.setPromptText("Nhập mã hóa đơn gốc vào đây...");

        Button btnTimHD = new Button("Tìm");
        btnTimHD.setId("btnTimHD");
        btnTimHD.setLayoutX(1098);
        btnTimHD.setLayoutY(15);
        btnTimHD.setPrefSize(54, 28);

        searchPane.getChildren().addAll(lblTimHD, txtTimHoaDon, btnTimHD);

        // ===== Hint =====
        Label lblHint = new Label("Chọn hóa đơn gốc để bắt đầu trả hàng");
        lblHint.getStyleClass().add("hint-label");
        lblHint.setLayoutX(40);
        lblHint.setLayoutY(150);
        AnchorPane.setTopAnchor(lblHint, 150.0);
        AnchorPane.setLeftAnchor(lblHint, 40.0);

        // ===== Bảng sản phẩm gốc =====
        // ===== Bảng sản phẩm trong hóa đơn gốc =====
        Label lblSPGoc = new Label("Sản phẩm trong hóa đơn gốc");
        lblSPGoc.getStyleClass().add("section-label");
        lblSPGoc.setLayoutX(40);
        lblSPGoc.setLayoutY(180);

        TableView<ChiTietHoaDon> tblSanPhamHoaDon = new TableView<>();
        tblSanPhamHoaDon.getStyleClass().add("main-table");
        tblSanPhamHoaDon.setPrefSize(1156, 304);
        AnchorPane.setTopAnchor(tblSanPhamHoaDon, 210.0);
        AnchorPane.setLeftAnchor(tblSanPhamHoaDon, 40.0);
        AnchorPane.setRightAnchor(tblSanPhamHoaDon, 450.0);

// Các cột
        TableColumn<ChiTietHoaDon, String> colSTTGoc = new TableColumn<>("STT");
        colSTTGoc.setPrefWidth(40.0);

        TableColumn<ChiTietHoaDon, String> colTenSPGoc = new TableColumn<>("Tên sản phẩm");
        colTenSPGoc.setPrefWidth(481.6666564941406);

        TableColumn<ChiTietHoaDon, String> colSLGoc = new TableColumn<>("Số lượng");
        colSLGoc.setPrefWidth(148.33334350585938);

        TableColumn<ChiTietHoaDon, String> colDonViGoc = new TableColumn<>("Đơn vị");
        colDonViGoc.setPrefWidth(124.0);

        TableColumn<ChiTietHoaDon, String> colDonGiaGoc = new TableColumn<>("Đơn giá");
        colDonGiaGoc.setPrefWidth(146.66668701171875);

        TableColumn<ChiTietHoaDon, String> colThanhTienGoc = new TableColumn<>("Thành tiền");
        colThanhTienGoc.setPrefWidth(144.6666259765625);

        TableColumn<ChiTietHoaDon, Void> colTra = new TableColumn<>("Trả");
        colTra.setPrefWidth(61.66668701171875);

        tblSanPhamHoaDon.getColumns().setAll(
                colSTTGoc, colTenSPGoc, colSLGoc, colDonViGoc, colDonGiaGoc, colThanhTienGoc, colTra
        );

// ===== Bảng chi tiết trả hàng =====
        Label lblChiTietTra = new Label("Chi tiết trả hàng");
        lblChiTietTra.getStyleClass().add("section-label");
        lblChiTietTra.setLayoutX(40);
        lblChiTietTra.setLayoutY(528);

        TableView<TraHangItem> tblChiTietTraHang = new TableView<>();
        tblChiTietTraHang.getStyleClass().add("main-table");
        tblChiTietTraHang.setPrefSize(1160, 295);
        AnchorPane.setTopAnchor(tblChiTietTraHang, 559.0);
        AnchorPane.setLeftAnchor(tblChiTietTraHang, 40.0);
        AnchorPane.setRightAnchor(tblChiTietTraHang, 446.0);
        AnchorPane.setBottomAnchor(tblChiTietTraHang, 41.0);

// Các cột
        TableColumn<TraHangItem, String> colSTTTra = new TableColumn<>("STT");
        colSTTTra.setPrefWidth(40.0);

        TableColumn<TraHangItem, String> colTenSPTra = new TableColumn<>("Tên sản phẩm");
        colTenSPTra.setPrefWidth(401.6666564941406);

        TableColumn<TraHangItem, Number> colSLTra = new TableColumn<>("Số lượng");
        colSLTra.setPrefWidth(130);

        TableColumn<TraHangItem, String> colDonViTra = new TableColumn<>("Đơn vị");
        colDonViTra.setPrefWidth(93.66668701171875);

        TableColumn<TraHangItem, Double> colDonGiaTra = new TableColumn<>("Đơn giá");
        colDonGiaTra.setPrefWidth(115.6666259765625);

        TableColumn<TraHangItem, Double> colThanhTienTra = new TableColumn<>("Thành tiền");
        colThanhTienTra.setPrefWidth(122.33331298828125);

        TableColumn<TraHangItem, String> colLyDo = new TableColumn<>("Lý do trả");
        colLyDo.setPrefWidth(212.66668701171875);

        TableColumn<TraHangItem, Void> colBo = new TableColumn<>("Bỏ");
        colBo.setPrefWidth(61.20009765624991);

        tblChiTietTraHang.getColumns().setAll(
                colSTTTra, colTenSPTra, colSLTra, colDonViTra,
                colDonGiaTra, colThanhTienTra, colLyDo, colBo
        );

        // ===== Info Pane (bên phải) =====
        Pane infoPane = new Pane();
        infoPane.getStyleClass().add("info-pane");
        infoPane.setPrefSize(391, 786);
        AnchorPane.setTopAnchor(infoPane, 70.0);
        AnchorPane.setRightAnchor(infoPane, 30.0);
        AnchorPane.setBottomAnchor(infoPane, 39.0);

        Label lblHeaderInfo = new Label("THÔNG TIN PHIẾU TRẢ HÀNG");
        lblHeaderInfo.getStyleClass().add("bold-label");
        lblHeaderInfo.setLayoutX(30);
        lblHeaderInfo.setLayoutY(14);

        Label lblMaHDGocText = new Label("Mã hóa đơn gốc:");
        lblMaHDGocText.setLayoutX(32);
        lblMaHDGocText.setLayoutY(55);

        TextField lblMaHDGoc = new TextField("");
        lblMaHDGoc.setEditable(false);
        lblMaHDGoc.setLayoutX(211);
        lblMaHDGoc.setLayoutY(49);
        lblMaHDGoc.setPrefWidth(120);

        Label lblTenKHText = new Label("Tên khách hàng:");
        lblTenKHText.setLayoutX(34);
        lblTenKHText.setLayoutY(100);
        TextField lblTenKH = new TextField("");
        lblTenKH.setEditable(false);
        lblTenKH.setLayoutX(213);
        lblTenKH.setLayoutY(94);
        lblTenKH.setPrefWidth(120);

        Label lblSDTText = new Label("Số điện thoại:");
        lblSDTText.setLayoutX(35);
        lblSDTText.setLayoutY(145);
        TextField lblSDT = new TextField("");
        lblSDT.setEditable(false);
        lblSDT.setLayoutX(214);
        lblSDT.setLayoutY(139);
        lblSDT.setPrefWidth(120);

        Label lblNgayLapText = new Label("Ngày lập phiếu:");
        lblNgayLapText.setLayoutX(36);
        lblNgayLapText.setLayoutY(194);

        DatePicker dpNgayLapPhieu = new DatePicker();
        dpNgayLapPhieu.setLayoutX(215);
        dpNgayLapPhieu.setLayoutY(193);
        dpNgayLapPhieu.setPrefSize(119, 27);

        Label lblThanhTienGocText = new Label("Thành tiền gốc:");
        lblThanhTienGocText.setLayoutX(28);
        lblThanhTienGocText.setLayoutY(270);
        Label lblTongTienGoc = new Label("0 VNĐ");
        lblTongTienGoc.getStyleClass().add("value-label");
        lblTongTienGoc.setLayoutX(207);
        lblTongTienGoc.setLayoutY(269);

        Label lblTongTraLaiText = new Label("Tổng tiền hàng trả lại:");
        lblTongTraLaiText.setLayoutX(28);
        lblTongTraLaiText.setLayoutY(310);
        Label lblTongTienTraLai = new Label("0 VNĐ");
        lblTongTienTraLai.getStyleClass().add("value-label");
        lblTongTienTraLai.setLayoutX(207);
        lblTongTienTraLai.setLayoutY(309);

        Label lblTienTraLaiText = new Label("Số tiền trả lại:");
        lblTienTraLaiText.getStyleClass().add("bold-label");
        lblTienTraLaiText.setLayoutX(27);
        lblTienTraLaiText.setLayoutY(351);
        Label lblSoTienTraLai = new Label("0 VNĐ");
        lblSoTienTraLai.getStyleClass().add("main-value-label");
        lblSoTienTraLai.setLayoutX(207);
        lblSoTienTraLai.setLayoutY(349);

        Label lblGhiChuText = new Label("Ghi chú:");
        lblGhiChuText.setLayoutX(34);
        lblGhiChuText.setLayoutY(454);

        TextArea txtGhiChu = new TextArea();
        txtGhiChu.setLayoutX(110);
        txtGhiChu.setLayoutY(449);
        txtGhiChu.setPrefSize(247, 148);

        Button btnTraHang = new Button("Trả hàng");
        btnTraHang.setLayoutX(32);
        btnTraHang.setLayoutY(652);
        btnTraHang.getStyleClass().add("success-btn");
        btnTraHang.setPrefSize(325, 44);
        btnTraHang.setOnAction(e -> ctrl.xuLyTraHang());

        Button btnHuy = new Button("Hủy");
        btnHuy.setLayoutX(32);
        btnHuy.setLayoutY(710);
        btnHuy.setPrefSize(325, 44);
        btnHuy.setStyle("-fx-background-color: red;");
        btnHuy.getStyleClass().add("print-btn");
        btnHuy.setOnAction(e -> ctrl.xuLyHuy());

            infoPane.getChildren().addAll(
                    lblHeaderInfo, lblMaHDGocText, lblMaHDGoc,
                    lblTenKHText, lblTenKH, lblSDTText, lblSDT,
                    lblNgayLapText, dpNgayLapPhieu,
                    lblThanhTienGocText, lblTongTienGoc,
                    lblTongTraLaiText, lblTongTienTraLai,
                    lblTienTraLaiText, lblSoTienTraLai,
                    lblGhiChuText, txtGhiChu,
                    btnTraHang, btnHuy
            );

            // ===== Add all to root =====
            root.getChildren().addAll(
                    headerPane, lblTitle, searchPane,
                    lblHint, lblSPGoc, tblSanPhamHoaDon,
                    lblChiTietTra, tblChiTietTraHang,
                    infoPane
            );

            // ===== Gán control vào controller =====
            ctrl.txtTimHoaDon = txtTimHoaDon;
            ctrl.btnTimHoaDon = btnTimHD;
            ctrl.btnTraHang = btnTraHang;
            ctrl.btnHuy = btnHuy;
            ctrl.lblMaHDGoc = lblMaHDGoc;
            ctrl.lblTenKH = lblTenKH;
            ctrl.lblSDT = lblSDT;
            ctrl.lblTongTienGoc = lblTongTienGoc;
            ctrl.lblTongTienTraLai = lblTongTienTraLai;
            ctrl.lblSoTienTraLai = lblSoTienTraLai;
            ctrl.txtGhiChu = txtGhiChu;
            ctrl.dpNgayLapPhieu = dpNgayLapPhieu;

            ctrl.tblSanPhamHoaDon = tblSanPhamHoaDon;
            ctrl.colSTTGoc = colSTTGoc;
            ctrl.colTenSPGoc = colTenSPGoc;
            ctrl.colSLGoc = colSLGoc;
            ctrl.colDonViGoc = colDonViGoc;
            ctrl.colDonGiaGoc = colDonGiaGoc;
            ctrl.colThanhTienGoc = colThanhTienGoc;
            ctrl.colTra = colTra;

            ctrl.tblChiTietTraHang = tblChiTietTraHang;
            ctrl.colSTTTra = colSTTTra;
            ctrl.colTenSPTra = colTenSPTra;
            ctrl.colSLTra = colSLTra;
            ctrl.colDonViTra = colDonViTra;
            ctrl.colDonGiaTra = colDonGiaTra;
            ctrl.colThanhTienTra = colThanhTienTra;
            ctrl.colLyDo = colLyDo;
            ctrl.colBo = colBo;
        ctrl.initialize(null, null);

        // ===== Scene setup =====
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Lập Phiếu Trả Hàng");

        // ===== Ngăn TextField tự động focus =====
        Platform.runLater(() -> txtTimHoaDon.getParent().requestFocus());
    }

    public static void main(String[] args) {
        launch(args);
    }
}



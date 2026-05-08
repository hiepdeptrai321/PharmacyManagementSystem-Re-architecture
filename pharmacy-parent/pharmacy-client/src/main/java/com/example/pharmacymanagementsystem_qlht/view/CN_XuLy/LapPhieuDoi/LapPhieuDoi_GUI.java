package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuDoi;

import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuDoi.LapPhieuDoiHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import com.example.pharmacymanagementsystem_qlht.service.DoiHangItem;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class LapPhieuDoi_GUI {

    public void showWithController(Stage stage, LapPhieuDoiHang_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 13px;");
        root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/LapPhieuDoiHang.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/Chung.css").toExternalForm());
        // ===== Header Pane =====
        Pane headerPane = new Pane();
        headerPane.getStyleClass().add("header-pane");
        headerPane.setLayoutX(28);
        headerPane.setPrefSize(1598, 54);
        AnchorPane.setTopAnchor(headerPane, 8.0);
        AnchorPane.setLeftAnchor(headerPane, 28.0);
        AnchorPane.setRightAnchor(headerPane, 20.0);

        ImageView imgHeader = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/exchange_6049036.png"))
        ));
        imgHeader.setFitHeight(44);
        imgHeader.setFitWidth(61);
        imgHeader.setLayoutX(288);
        imgHeader.setLayoutY(5);
        imgHeader.setPickOnBounds(true);
        imgHeader.setPreserveRatio(true);
        headerPane.getChildren().add(imgHeader);

        // ===== Title =====
        Label lblTitle = new Label("LẬP PHIẾU ĐỔI HÀNG");
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

        TextField txtTimHoaDonGoc = new TextField();
        txtTimHoaDonGoc.setId("txtTimHoaDonGoc");
        txtTimHoaDonGoc.setLayoutX(140);
        txtTimHoaDonGoc.setLayoutY(15);
        txtTimHoaDonGoc.setPrefSize(939, 29);
        txtTimHoaDonGoc.setPromptText("Nhập mã hóa đơn gốc vào đây...");

        Button btnTimHD = new Button("Tìm");
        btnTimHD.setId("btnTimHD");
        btnTimHD.setLayoutX(1098);
        btnTimHD.setLayoutY(15);
        btnTimHD.setPrefSize(54, 28);

        searchPane.getChildren().addAll(lblTimHD, txtTimHoaDonGoc, btnTimHD);

        // ===== Hint =====
        Label lblHint = new Label("Chọn hóa đơn gốc để bắt đầu đổi hàng");
        lblHint.getStyleClass().add("hint-label");
        lblHint.setLayoutX(40);
        lblHint.setLayoutY(150);
        AnchorPane.setTopAnchor(lblHint, 150.0);
        AnchorPane.setLeftAnchor(lblHint, 40.0);

        // ===== Table: Sản phẩm gốc =====
        Label lblSPGoc = new Label("Sản phẩm trong hóa đơn gốc");
        lblSPGoc.getStyleClass().add("section-label");
        lblSPGoc.setLayoutX(40);
        lblSPGoc.setLayoutY(180);
        AnchorPane.setTopAnchor(lblSPGoc, 180.0);
        AnchorPane.setLeftAnchor(lblSPGoc, 40.0);

        TableView<ChiTietHoaDon> tblSanPhamGoc = new TableView<>();
        tblSanPhamGoc.setId("tblSanPhamGoc");
        tblSanPhamGoc.setPrefSize(1156, 248);
        tblSanPhamGoc.getStyleClass().add("main-table");
        AnchorPane.setTopAnchor(tblSanPhamGoc, 210.0);
        AnchorPane.setLeftAnchor(tblSanPhamGoc, 40.0);
        AnchorPane.setRightAnchor(tblSanPhamGoc, 450.0);

        TableColumn<ChiTietHoaDon, String> colSTTGoc = new TableColumn<>("STT");
        colSTTGoc.setPrefWidth(50);

        TableColumn<ChiTietHoaDon, String> colTenSPGoc = new TableColumn<>("Tên sản phẩm");
        colTenSPGoc.setPrefWidth(374);

        TableColumn<ChiTietHoaDon, String> colSoLuongGoc = new TableColumn<>("Số lượng");
        colSoLuongGoc.setPrefWidth(110);

        TableColumn<ChiTietHoaDon, String> colDonViGoc = new TableColumn<>("Đơn vị");
        colDonViGoc.setPrefWidth(132);

        TableColumn<ChiTietHoaDon, String> colDonGiaGoc = new TableColumn<>("Đơn giá");
        colDonGiaGoc.setPrefWidth(151.67);

        TableColumn<ChiTietHoaDon, String> colGiamGiaGoc = new TableColumn<>("Giảm giá");
        colGiamGiaGoc.setPrefWidth(143.67);

        TableColumn<ChiTietHoaDon, String> colThanhTienGoc = new TableColumn<>("Thành tiền");
        colThanhTienGoc.setPrefWidth(131.33);

        TableColumn<ChiTietHoaDon, Void> colDoi = new TableColumn<>("Đổi");
        colDoi.setPrefWidth(61);

        tblSanPhamGoc.getColumns().addAll(colSTTGoc, colTenSPGoc, colSoLuongGoc, colDonViGoc,
                colDonGiaGoc, colGiamGiaGoc, colThanhTienGoc, colDoi);

        // ===== Table: Sản phẩm đổi =====
        Label lblSPMoi = new Label("Sản phẩm đổi");
        lblSPMoi.getStyleClass().add("section-label");
        AnchorPane.setTopAnchor(lblSPMoi, 474.0);
        AnchorPane.setLeftAnchor(lblSPMoi, 43.0);

        TableView<DoiHangItem> tblSanPhamDoi = new TableView<>();
        tblSanPhamDoi.setId("tblSanPhamDoi");
        tblSanPhamDoi.setPrefSize(1160, 263);
        tblSanPhamDoi.getStyleClass().add("main-table");
        AnchorPane.setTopAnchor(tblSanPhamDoi, 510.0);
        AnchorPane.setLeftAnchor(tblSanPhamDoi, 38.0);
        AnchorPane.setRightAnchor(tblSanPhamDoi, 451.6);
        AnchorPane.setBottomAnchor(tblSanPhamDoi, 122.8);

        TableColumn<DoiHangItem, String> colSTTDoi = new TableColumn<>("STT");
        colSTTDoi.setPrefWidth(50);

        TableColumn<DoiHangItem, String> colTenSPDoi = new TableColumn<>("Tên sản phẩm");
        colTenSPDoi.setPrefWidth(445.6);

        TableColumn<DoiHangItem, Number> colSoLuongDoi = new TableColumn<>("Số lượng đổi");
        colSoLuongDoi.setPrefWidth(124.33);

        TableColumn<DoiHangItem, String> colDonViDoi = new TableColumn<>("Đơn vị");
        colDonViDoi.setPrefWidth(92.67);

        TableColumn<DoiHangItem, String> colLyDo = new TableColumn<>("Lý do đổi");
        colLyDo.setPrefWidth(370.33);

        TableColumn<DoiHangItem, Void> colBo = new TableColumn<>("Bỏ");
        colBo.setPrefWidth(68.67);

        tblSanPhamDoi.getColumns().addAll(colSTTDoi, colTenSPDoi, colSoLuongDoi, colDonViDoi, colLyDo, colBo);

        // ===== Info Pane (bên phải) =====
        Pane infoPane = new Pane();
        infoPane.getStyleClass().add("info-pane");
        infoPane.setPrefWidth(391);
        AnchorPane.setTopAnchor(infoPane, 70.0);
        AnchorPane.setBottomAnchor(infoPane, 120.0);
        AnchorPane.setRightAnchor(infoPane, 30.0);

        Label lblInfoTitle = new Label("THÔNG TIN PHIẾU ĐỔI HÀNG");
        lblInfoTitle.getStyleClass().add("bold-label");
        lblInfoTitle.setLayoutX(30);
        lblInfoTitle.setLayoutY(61);

        Label lblMaHDGocText = new Label("Mã hóa đơn gốc:");
        lblMaHDGocText.setLayoutX(30);
        lblMaHDGocText.setLayoutY(100);

        TextField lblMaHDGoc = new TextField();
        lblMaHDGoc.setEditable(false);
        lblMaHDGoc.setLayoutX(170);
        lblMaHDGoc.setLayoutY(95);
        lblMaHDGoc.setPrefWidth(140);

        Label lblTenKHText = new Label("Tên khách hàng:");
        lblTenKHText.setLayoutX(31);
        lblTenKHText.setLayoutY(141);

        TextField lblTenKH = new TextField();
        lblTenKH.setEditable(false);
        lblTenKH.setLayoutX(170);
        lblTenKH.setLayoutY(136);
        lblTenKH.setPrefWidth(140);

        Label lblSDTText = new Label("Số điện thoại:");
        lblSDTText.setLayoutX(30);
        lblSDTText.setLayoutY(189);

        TextField lblSDT = new TextField();
        lblSDT.setEditable(false);
        lblSDT.setLayoutX(170);
        lblSDT.setLayoutY(183);
        lblSDT.setPrefWidth(140);

        Label lblNgayLapText = new Label("Ngày lập phiếu:");
        lblNgayLapText.setLayoutX(30);
        lblNgayLapText.setLayoutY(233);

        DatePicker dpNgayLapPhieu = new DatePicker();
        dpNgayLapPhieu.setLayoutX(170);
        dpNgayLapPhieu.setLayoutY(227);
        dpNgayLapPhieu.setPrefWidth(140);

        Label lblGhiChuText = new Label("Ghi chú:");
        lblGhiChuText.setLayoutX(30);
        lblGhiChuText.setLayoutY(269);

        TextArea txtGhiChu = new TextArea();
        txtGhiChu.setLayoutX(30);
        txtGhiChu.setLayoutY(315);
        txtGhiChu.setPrefSize(322, 207);

        Button btnDoi = new Button("Đổi hàng");
        btnDoi.getStyleClass().add("success-btn");
        btnDoi.setLayoutX(29);
        btnDoi.setLayoutY(570);
        btnDoi.setPrefSize(327, 44);
        btnDoi.setFont(Font.font(14));

        Button btnHuy = new Button("Hủy");
        btnHuy.setLayoutX(28);
        btnHuy.setLayoutY(625);
        btnHuy.setPrefSize(331, 44);
        btnHuy.setStyle("-fx-background-color: red;");
        btnHuy.getStyleClass().add("print-btn");
        btnHuy.setFont(Font.font(14));

        infoPane.getChildren().addAll(lblInfoTitle, lblMaHDGocText, lblMaHDGoc, lblTenKHText, lblTenKH,
                lblSDTText, lblSDT, lblNgayLapText, dpNgayLapPhieu, lblGhiChuText, txtGhiChu, btnDoi, btnHuy);

        // ===== Assemble all =====
        root.getChildren().addAll(headerPane, lblTitle, searchPane, lblHint, lblSPGoc, tblSanPhamGoc,
                lblSPMoi, tblSanPhamDoi, infoPane);

        Scene scene = new Scene(root);
//        scene.getStylesheets().add(Objects.requireNonNull(
//                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/LapPhieuDoiHang.css")
//        ).toExternalForm());

        // ===== Inject to controller =====
        ctrl.tblSanPhamGoc = tblSanPhamGoc;
        ctrl.colSTTGoc = colSTTGoc;
        ctrl.colTenSPGoc = colTenSPGoc;
        ctrl.colSoLuongGoc = colSoLuongGoc;
        ctrl.colDonViGoc = colDonViGoc;
        ctrl.colDonGiaGoc = colDonGiaGoc;
        ctrl.colGiamGiaGoc = colGiamGiaGoc;
        ctrl.colThanhTienGoc = colThanhTienGoc;
        ctrl.colDoi = colDoi;

        ctrl.tblSanPhamDoi = tblSanPhamDoi;
        ctrl.colSTTDoi = colSTTDoi;
        ctrl.colTenSPDoi = colTenSPDoi;
        ctrl.colSoLuongDoi = colSoLuongDoi;
        ctrl.colDonViDoi = colDonViDoi;
        ctrl.colLyDo = colLyDo;
        ctrl.colBo = colBo;

        ctrl.txtTimHoaDonGoc = txtTimHoaDonGoc;
        ctrl.btnTimHD = btnTimHD;
        ctrl.btnDoi = btnDoi;
        ctrl.btnHuy = btnHuy;
        ctrl.lblMaHDGoc = lblMaHDGoc;
        ctrl.lblTenKH = lblTenKH;
        ctrl.lblSDT = lblSDT;
        ctrl.dpNgayLapPhieu = dpNgayLapPhieu;
        ctrl.txtGhiChu = txtGhiChu;

        // Gọi initialize() nếu có logic khởi tạo
        ctrl.initialize();

        stage.setTitle("Lập phiếu đổi hàng");
        stage.setScene(scene);
    }
}


// java
package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuDat;

import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuDatHang.LapPhieuDatHang_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class LapPhieuDat_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, LapPhieuDatHang_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.getStylesheets().add(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/LapPhieuDatHang.css").toExternalForm());

        // Header decorative pane (kept simple)
        Pane headerPane = new Pane();
        headerPane.getStyleClass().add("header-pane");
        headerPane.setLayoutX(20);
        headerPane.setLayoutY(8);
        headerPane.setPrefSize(1606, 72);

        ImageView headerIcon = new ImageView();
        headerIcon.setFitWidth(61);
        headerIcon.setFitHeight(54);
        headerIcon.setLayoutX(310);
        headerIcon.setLayoutY(4);
        headerIcon.setPickOnBounds(true);
        headerIcon.setPreserveRatio(true);
        // try load a generic icon if present
        try {
            headerIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/preorder1.png")).toExternalForm()));
        } catch (Exception ignored) {}

        headerPane.getChildren().add(headerIcon);

        Label title = new Label("LẬP PHIẾU ĐẶT HÀNG");
        title.getStyleClass().add("title-label");
        title.setLayoutX(30);
        title.setLayoutY(21);
        title.setPrefSize(286, 38);
        title.setFont(Font.font("System Bold", 18));

        // Search pane
        Pane searchPane = new Pane();
        searchPane.getStyleClass().add("search-pane");
        searchPane.setLayoutX(30);
        searchPane.setLayoutY(80);
        searchPane.setPrefSize(1140, 70);

        Label lblFind = new Label("Tìm sản phẩm:");
        lblFind.setLayoutX(21);
        lblFind.setLayoutY(26);
        lblFind.setFont(Font.font("System Bold", 13));

        TextField tfTimSanPham = new TextField();
        tfTimSanPham.setId("tfTimSanPham");
        tfTimSanPham.setLayoutX(116);
        tfTimSanPham.setLayoutY(17);
        tfTimSanPham.setPrefSize(951, 38);
        tfTimSanPham.setPromptText("Nhập tên hoặc mã sản phẩm");

        Button btnLamMoi = new Button();
        btnLamMoi.setId("btnLamMoi");
        btnLamMoi.setLayoutX(1079);
        btnLamMoi.setLayoutY(16);
        btnLamMoi.setPrefSize(50, 38);

        ImageView imgReset = new ImageView();
        imgReset.setFitHeight(20);
        imgReset.setFitWidth(34);
        imgReset.setLayoutX(1094);
        imgReset.setLayoutY(25);
        imgReset.setPickOnBounds(true);
        imgReset.setPreserveRatio(true);
        try {
            imgReset.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm()));
        } catch (Exception ignored) {}

        searchPane.getChildren().addAll(lblFind, tfTimSanPham, btnLamMoi, imgReset);

        // Left HBox containing table and filler
        HBox leftBox = new HBox(10);
        leftBox.setLayoutX(32);
        leftBox.setLayoutY(170);
        leftBox.setPrefSize(1147, 702);

        VBox leftVBox = new VBox(12);
        leftVBox.setPrefSize(1137, 702);

        Label sectionLabel = new Label("Danh sách sản phẩm đặt mua");
        sectionLabel.getStyleClass().add("section-label");

        TableView<Object> tbSanPham = new TableView<>();
        tbSanPham.setStyle("-fx-alignment: CENTER;");
        tbSanPham.setId("tbSanPham");
        tbSanPham.setPrefSize(1137, 701);
        tbSanPham.getStyleClass().add("main-table");

        TableColumn<Object, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(50);
        TableColumn<Object, String> colTenSP = new TableColumn<>("Tên sản phẩm");
        colTenSP.setPrefWidth(358);
        TableColumn<Object, String> colSoLuong = new TableColumn<>("Số lượng");
        colSoLuong.setPrefWidth(121);
        TableColumn<Object, String> colDonVi = new TableColumn<>("Đơn vị");
        colDonVi.setPrefWidth(150);
        TableColumn<Object, String> colDonGia = new TableColumn<>("Đơn giá");
        colDonGia.setPrefWidth(174);
        TableColumn<Object, String> colThanhTien = new TableColumn<>("Thành tiền");
        colThanhTien.setPrefWidth(238);
        TableColumn<Object, String> colXoa = new TableColumn<>("");
        colXoa.setPrefWidth(45);

        colDonGia.setStyle("-fx-alignment: CENTER;");
        colThanhTien.setStyle("-fx-alignment: CENTER;");
        colXoa.setStyle("-fx-alignment: CENTER;");
        colSTT.setStyle("-fx-alignment: CENTER;");
        colDonVi.setStyle("-fx-alignment: CENTER;");
        colTenSP.setStyle("-fx-alignment: CENTER-LEFT;");

        tbSanPham.getColumns().addAll(colSTT, colTenSP, colSoLuong, colDonVi, colDonGia, colThanhTien, colXoa);

        leftVBox.getChildren().addAll(sectionLabel, tbSanPham);

        Pane middleDecor = new Pane();
        middleDecor.getStyleClass().add("middle-decor");
        HBox.setHgrow(middleDecor, Priority.ALWAYS);

        leftBox.getChildren().addAll(leftVBox, middleDecor);

        // Info pane on right
        Pane infoPane = new Pane();
        infoPane.getStyleClass().add("info-pane");
        infoPane.setLayoutX(1196);
        infoPane.setLayoutY(18);
        infoPane.setPrefSize(430, 859);

        Label lbInfoTitle = new Label("THÔNG TIN PHIẾU ĐẶT HÀNG");
        lbInfoTitle.getStyleClass().add("bold-label");
        lbInfoTitle.setFont(Font.font(18));
        lbInfoTitle.setLayoutX(20);
        lbInfoTitle.setLayoutY(20);

        Label lblMa = new Label("Mã phiếu đặt:");
        lblMa.setLayoutX(20);
        lblMa.setLayoutY(58);
        TextField tfMa = new TextField();
        tfMa.setId("tfMa");
        tfMa.setLayoutX(162);
        tfMa.setLayoutY(55);
        tfMa.setPrefSize(252, 27);

        Label lblNgayLap = new Label("Ngày lập phiếu:");
        lblNgayLap.setLayoutX(20);
        lblNgayLap.setLayoutY(98);
        DatePicker dpNgayLap = new DatePicker();
        dpNgayLap.setId("dpNgayLap");
        dpNgayLap.setLayoutX(162);
        dpNgayLap.setLayoutY(95);
        dpNgayLap.setPrefSize(252, 27);

        Label lblTenKH = new Label("Tên khách hàng:");
        lblTenKH.setLayoutX(18);
        lblTenKH.setLayoutY(165);
        TextField tfTenKH = new TextField();
        tfTenKH.setId("tfTenKH");
        tfTenKH.setLayoutX(162);
        tfTenKH.setLayoutY(162);
        tfTenKH.setPrefSize(252, 27);

        Separator sep1 = new Separator();
        sep1.setLayoutX(18);
        sep1.setLayoutY(145);
        sep1.setPrefWidth(394);

        Label lblSDT = new Label("Số điện thoại:");
        lblSDT.setLayoutX(20);
        lblSDT.setLayoutY(205);
        TextField tfSDT = new TextField();
        tfSDT.setId("tfSDT");
        tfSDT.setLayoutX(162);
        tfSDT.setLayoutY(202);
        tfSDT.setPrefSize(252, 27);

        Label lblLoaiDon = new Label("Loại đơn");
        lblLoaiDon.setLayoutX(20);
        lblLoaiDon.setLayoutY(285);
        ComboBox<String> cbLoaiDon = new ComboBox<>();
        cbLoaiDon.setId("cbLoaiDon");
        cbLoaiDon.setLayoutX(162);
        cbLoaiDon.setLayoutY(283);
        cbLoaiDon.setPrefSize(252, 25);

        Label lblMaDon = new Label("Mã đơn thuốc:");
        lblMaDon.setLayoutX(20);
        lblMaDon.setLayoutY(320);
        TextField tfMaDon = new TextField();
        tfMaDon.setId("tfMaDon");
        tfMaDon.setLayoutX(162);
        tfMaDon.setLayoutY(317);
        tfMaDon.setPrefSize(252, 27);

        Separator sep2 = new Separator();
        sep2.setLayoutX(18);
        sep2.setLayoutY(364);
        sep2.setPrefWidth(394);

        Label lblTongTien = new Label("Tổng tiền hàng:");
        lblTongTien.setLayoutX(20);
        lblTongTien.setLayoutY(380);

        Label lbTongTien = new Label("0 VNĐ");
        lbTongTien.setId("lbTongTien");
        lbTongTien.getStyleClass().add("value-label");
        lbTongTien.setLayoutX(166);
        lbTongTien.setLayoutY(380);

        Label lblThue = new Label("Thuế (VAT):");
        lblThue.setLayoutX(20);
        lblThue.setLayoutY(410);

        Label lbThue = new Label("0 VNĐ");
        lbThue.setId("lbThue");
        lbThue.getStyleClass().add("value-label");
        lbThue.setLayoutX(166);
        lbThue.setLayoutY(410);

        Label lblTongTT = new Label("Tổng thanh toán:");
        lblTongTT.setLayoutX(18);
        lblTongTT.setLayoutY(440);
        lblTongTT.getStyleClass().add("bold-label");

        Label lbTongTT = new Label("0 VNĐ");
        lbTongTT.setId("lbTongTT");
        lbTongTT.getStyleClass().add("main-value-label");
        lbTongTT.setLayoutX(166);
        lbTongTT.setLayoutY(436);

        Label lblTienCoc = new Label("Tiền cọc:");
        lblTienCoc.setLayoutX(20);
        lblTienCoc.setLayoutY(475);
        TextField tfTienCoc = new TextField();
        tfTienCoc.setId("tfTienCoc");
        tfTienCoc.setLayoutX(162);
        tfTienCoc.setLayoutY(472);
        tfTienCoc.setPrefSize(252, 27);
        tfTienCoc.setPromptText("Nhập số tiền cọc...");

        Label lblTienThieu = new Label("Còn thiếu :");
        lblTienThieu.setLayoutX(20);
        lblTienThieu.setLayoutY(508);
        lblTienThieu.getStyleClass().add("bold-label");

        Label lbTienThieu = new Label("0 VNĐ");
        lbTienThieu.setId("lbTienThieu");
        lbTienThieu.getStyleClass().add("main-value-label");
        lbTienThieu.setLayoutX(166);
        lbTienThieu.setLayoutY(504);

        Label lblGhiChu = new Label("Ghi chú:");
        lblGhiChu.setLayoutX(21);
        lblGhiChu.setLayoutY(541);

        TextArea tfGhiChu = new TextArea();
        tfGhiChu.setId("tfGhiChu");
        tfGhiChu.setLayoutX(20);
        tfGhiChu.setLayoutY(571);
        tfGhiChu.setPrefSize(394, 126);

        Button btnDatHang = new Button("Đặt hàng");
        btnDatHang.setId("btnDatHang");
        btnDatHang.setLayoutX(20);
        btnDatHang.setLayoutY(733);
        btnDatHang.setPrefSize(394, 44);
        btnDatHang.getStyleClass().add("success-btn");
        btnDatHang.setFont(Font.font("System Bold", 13));

        Button btnDatHangVaIn = new Button("Đặt hàng và in phiếu");
        btnDatHangVaIn.setId("btnDatHangVaIn");
        btnDatHangVaIn.setLayoutX(20);
        btnDatHangVaIn.setLayoutY(787);
        btnDatHangVaIn.setPrefSize(394, 44);
        btnDatHangVaIn.getStyleClass().add("primary-btn");
        btnDatHangVaIn.setFont(Font.font("System Bold", 13));

        Button btnTimKH = new Button("Tìm khách hàng");
        btnTimKH.setId("btnTimKH");
        btnTimKH.setLayoutX(20);
        btnTimKH.setLayoutY(237);
        btnTimKH.setPrefSize(188, 25);
        btnTimKH.getStyleClass().add("btnTim");

        Button btnThemKH = new Button("Thêm khách hàng");
        btnThemKH.setId("btnThemKH");
        btnThemKH.setLayoutX(223);
        btnThemKH.setLayoutY(237);
        btnThemKH.setPrefSize(191, 25);
        btnThemKH.getStyleClass().add("btnThem");

        infoPane.getChildren().addAll(
                lbInfoTitle, lblMa, tfMa, lblNgayLap, dpNgayLap,
                lblTenKH, tfTenKH, sep1, lblSDT, tfSDT, lblLoaiDon, cbLoaiDon,
                lblMaDon, tfMaDon, sep2, lblTongTien, lbTongTien, lblThue, lbThue,
                lblTongTT, lbTongTT, lblTienCoc, tfTienCoc, lblTienThieu, lbTienThieu,
                lblGhiChu, tfGhiChu, btnDatHang, btnDatHangVaIn, btnTimKH, btnThemKH
        );

        root.getChildren().addAll(headerPane, title, searchPane, leftBox, infoPane);

        Scene scene = new Scene(root);

        // Inject controls into controller (best-effort unchecked casts similar to existing pattern)
        try {
            ctrl.tfTimSanPham = tfTimSanPham;
            ctrl.btnLamMoi = btnLamMoi;
            ctrl.tbSanPham = (TableView) tbSanPham;
            ctrl.colSTT = (TableColumn) colSTT;
            ctrl.colTenSP = (TableColumn) colTenSP;
            ctrl.colSoLuong = (TableColumn) colSoLuong;
            ctrl.colDonVi = (TableColumn) colDonVi;
            ctrl.colDonGia = (TableColumn) colDonGia;
            ctrl.colThanhTien = (TableColumn) colThanhTien;
            ctrl.colXoa = (TableColumn) colXoa;

            ctrl.infoPane = infoPane;
            ctrl.tfMa = tfMa;
            ctrl.dpNgayLap = dpNgayLap;
            ctrl.tfTenKH = tfTenKH;
            ctrl.tfSDT = tfSDT;
            ctrl.cbLoaiDon = cbLoaiDon;
            ctrl.tfMaDon = tfMaDon;
            ctrl.lbTongTien = lbTongTien;
            ctrl.lbThue = lbThue;
            ctrl.lbTongTT = lbTongTT;
            ctrl.tfTienCoc = tfTienCoc;
            ctrl.lbTienThieu = lbTienThieu;
            ctrl.tfGhiChu = tfGhiChu;
            ctrl.btnDatHang = btnDatHang;
            ctrl.btnDatHangVaIn = btnDatHangVaIn;
            ctrl.btnTimKH = btnTimKH;
            ctrl.btnThemKH = btnThemKH;
        } catch (Exception ignored) {
            // silent best-effort injection
        }

        // Initialize controller logic
        ctrl.initialize();

        stage.setTitle("LẬP PHIẾU ĐẶT HÀNG");
        stage.setScene(scene);
    }
}

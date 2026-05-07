package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDatHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDatHang.ChiTietPhieuDatHang_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Objects;

public class ChiTietPhieuDatHang_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, ChiTietPhieuDatHang_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14 px;");

        SplitPane split = new SplitPane();
        split.setPrefSize(1646, 895);
        split.setDividerPositions(0.65);

        // Left pane: table + headers
        AnchorPane left = new AnchorPane();
        left.setMinSize(0, 0);

        Label lblSection = new Label("DANH SÁCH SẢN PHẨM ĐẶT");
        lblSection.getStyleClass().add("section-label");
        lblSection.setLayoutX(14);
        lblSection.setLayoutY(51);

        TableView<Object> tblChiTietPhieuDat = new TableView<>();
        tblChiTietPhieuDat.setId("tblChiTietPhieuDat");
        tblChiTietPhieuDat.getStyleClass().add("main-table");
        tblChiTietPhieuDat.setLayoutX(12);
        tblChiTietPhieuDat.setLayoutY(79);
        tblChiTietPhieuDat.setPrefSize(1042, 797);

        TableColumn<Object, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(40);
        colSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colTenSP = new TableColumn<>("Tên sản phẩm/Hoạt chất");
        colTenSP.setPrefWidth(229);

        TableColumn<Object, String> colSoLuong = new TableColumn<>("Số lượng");
        colSoLuong.setPrefWidth(88);
        colSoLuong.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colDonVi = new TableColumn<>("Đơn vị");
        colDonVi.setPrefWidth(66);
        colDonVi.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colDonGia = new TableColumn<>("Đơn giá đặt");
        colDonGia.setPrefWidth(137);
        colDonGia.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colNhaCungCap = new TableColumn<>("CK(%)");
        colNhaCungCap.setPrefWidth(100);
        colNhaCungCap.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colThanhTien = new TableColumn<>("Thành tiền đặt");
        colThanhTien.setPrefWidth(168);
        colThanhTien.setStyle("-fx-alignment: CENTER_RIGHT;");

        TableColumn<Object, String> colTT = new TableColumn<>("Trạng thái");
        colTT.setPrefWidth(125);
        colTT.setStyle("-fx-alignment: CENTER;");

        tblChiTietPhieuDat.getColumns().addAll(colSTT, colTenSP, colSoLuong, colDonVi, colDonGia, colNhaCungCap, colThanhTien, colTT);
        tblChiTietPhieuDat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label lblMaPhieu = new Label("Mã phiếu đặt: ");
        lblMaPhieu.setLayoutX(14);
        lblMaPhieu.setLayoutY(14);
        lblMaPhieu.getStyleClass().add("bold-label");

        Label lblMaPhieuValue = new Label("PD012345");
        lblMaPhieuValue.setId("lblMaPhieuDatValue");
        lblMaPhieuValue.getStyleClass().add("value-label");
        lblMaPhieuValue.setLayoutX(116);
        lblMaPhieuValue.setLayoutY(15);

        Label lblNgayLap = new Label("Ngày lập: ");
        lblNgayLap.setLayoutX(200);
        lblNgayLap.setLayoutY(14);
        lblNgayLap.getStyleClass().add("bold-label");

        Label lblNgayLapValue = new Label("08/10/2025");
        lblNgayLapValue.setId("lblNgayLapValue");
        lblNgayLapValue.getStyleClass().add("value-label");
        lblNgayLapValue.setLayoutX(271);
        lblNgayLapValue.setLayoutY(15);
        lblNgayLapValue.setPrefSize(170, 20);

        left.getChildren().addAll(lblSection, tblChiTietPhieuDat, lblMaPhieu, lblMaPhieuValue, lblNgayLap, lblNgayLapValue);

        // Right pane: info and summary
        AnchorPane right = new AnchorPane();
        right.getStyleClass().add("right-pane-bg");

        Label lbInfoTitle = new Label("THÔNG TIN PHIẾU ĐẶT");
        lbInfoTitle.setFont(Font.font("System Bold", 24));
        lbInfoTitle.setLayoutX(10);
        lbInfoTitle.setLayoutY(10);

        Pane infoPane = new Pane();
        infoPane.getStyleClass().add("info-pane");
        infoPane.setLayoutX(10);
        infoPane.setLayoutY(50);
        infoPane.setPrefSize(543, 160);

        Label lblTenNV = new Label("Tên nhân viên lập:");
        lblTenNV.getStyleClass().add("bold-label");
        lblTenNV.setLayoutX(5);
        lblTenNV.setLayoutY(5);

        Label lblTenNhanVienValue = new Label();
        lblTenNhanVienValue.setId("lblTenNhanVienValue");
        lblTenNhanVienValue.getStyleClass().add("value-label");
        lblTenNhanVienValue.setLayoutX(160);
        lblTenNhanVienValue.setLayoutY(5);

        Label lblTenNCC = new Label("Tên nhà cung cấp:");
        lblTenNCC.getStyleClass().add("bold-label");
        lblTenNCC.setLayoutX(5);
        lblTenNCC.setLayoutY(35);

        Label lblTenNCCValue = new Label();
        lblTenNCCValue.setId("lblTenNCCValue");
        lblTenNCCValue.getStyleClass().add("value-label");
        lblTenNCCValue.setLayoutX(160);
        lblTenNCCValue.setLayoutY(35);

        Label lblSDTNCC = new Label("SĐT NCC:");
        lblSDTNCC.getStyleClass().add("bold-label");
        lblSDTNCC.setLayoutX(5);
        lblSDTNCC.setLayoutY(65);

        Label lblSDTNCCValue = new Label();
        lblSDTNCCValue.setId("lblSDTNCCValue");
        lblSDTNCCValue.getStyleClass().add("value-label");
        lblSDTNCCValue.setLayoutX(160);
        lblSDTNCCValue.setLayoutY(65);

        Label lblGhiChu = new Label("Ghi chú:");
        lblGhiChu.getStyleClass().add("bold-label");
        lblGhiChu.setLayoutX(5);
        lblGhiChu.setLayoutY(95);

        Label lblGhiChuValue = new Label("Không có.");
        lblGhiChuValue.setId("lblGhiChuValue");
        lblGhiChuValue.getStyleClass().add("value-label");
        lblGhiChuValue.setLayoutX(160);
        lblGhiChuValue.setLayoutY(95);
        lblGhiChuValue.setPrefWidth(200);
        lblGhiChuValue.setWrapText(true);

        Label lblTrangThai = new Label("Trạng thái:");
        lblTrangThai.getStyleClass().add("bold-label");
        lblTrangThai.setLayoutX(5);
        lblTrangThai.setLayoutY(125);

        Label lbTT = new Label("Chưa có hàng");
        lbTT.setId("lbTT");
        lbTT.getStyleClass().addAll("value-label", "lbTT");
        lbTT.setLayoutX(160);
        lbTT.setLayoutY(126);
        lbTT.setPrefWidth(200);
        lbTT.setWrapText(true);

        infoPane.getChildren().addAll(lblTenNV, lblTenNhanVienValue, lblTenNCC, lblTenNCCValue, lblSDTNCC, lblSDTNCCValue, lblGhiChu, lblGhiChuValue, lblTrangThai, lbTT);

        // Summary pane
        Label lbSummaryTitle = new Label("TÓM TẮT ĐẶT HÀNG");
        lbSummaryTitle.setFont(Font.font("System Bold", 24));
        lbSummaryTitle.setLayoutX(10);
        lbSummaryTitle.setLayoutY(220);

        Pane summaryPane = new Pane();
        summaryPane.getStyleClass().add("summary-pane");
        summaryPane.setLayoutX(10);
        summaryPane.setLayoutY(260);
        summaryPane.setPrefSize(556, 280);

        Label lblTongTienDat = new Label("Tổng tiền đặt (trước CK/VAT):");
        lblTongTienDat.getStyleClass().add("label-normal");
        lblTongTienDat.setFont(Font.font("System Bold", 12));
        lblTongTienDat.setLayoutX(5);
        lblTongTienDat.setLayoutY(5);

        Label lblTongTienDatValue = new Label("0 VNĐ");
        lblTongTienDatValue.setId("lblTongTienDatValue");
        lblTongTienDatValue.getStyleClass().addAll("bold-label", "value-label");
        lblTongTienDatValue.setLayoutX(240);
        lblTongTienDatValue.setLayoutY(5);

        Label lblChietKhauPD = new Label("Chiết khấu Phiếu đặt:");
        lblChietKhauPD.getStyleClass().add("label-normal");
        lblChietKhauPD.setFont(Font.font("System Bold", 12));
        lblChietKhauPD.setLayoutX(5);
        lblChietKhauPD.setLayoutY(30);

        Label lblChietKhauPDValue = new Label("0 VNĐ");
        lblChietKhauPDValue.setId("lblChietKhauPDValue");
        lblChietKhauPDValue.getStyleClass().addAll("label-discount", "bold-label", "value-label");
        lblChietKhauPDValue.setLayoutX(240);
        lblChietKhauPDValue.setLayoutY(30);

        Label lblThueVAT = new Label("Thuế (VAT):");
        lblThueVAT.getStyleClass().add("label-normal");
        lblThueVAT.setFont(Font.font("System Bold", 12));
        lblThueVAT.setLayoutX(5);
        lblThueVAT.setLayoutY(55);

        Label lblThueVATValue = new Label("0 VNĐ");
        lblThueVATValue.setId("lblThueVATValue");
        lblThueVATValue.getStyleClass().addAll("bold-label", "value-label");
        lblThueVATValue.setLayoutX(240);
        lblThueVATValue.setLayoutY(55);

        Label lblTotalLabel = new Label("TỔNG TIỀN ĐẶT:");
        lblTotalLabel.getStyleClass().addAll("bold-label", "section-label");
        lblTotalLabel.setLayoutX(5);
        lblTotalLabel.setLayoutY(100);

        Label lblTongTienPhaiDatValue = new Label("0 VNĐ");
        lblTongTienPhaiDatValue.setId("lblTongTienPhaiDatValue");
        lblTongTienPhaiDatValue.getStyleClass().addAll("main-value-label", "grand-total-value", "bold-label", "value-label");
        lblTongTienPhaiDatValue.setLayoutX(240);
        lblTongTienPhaiDatValue.setLayoutY(100);

        Label lblPTTT = new Label("Phương thức thanh toán:");
        lblPTTT.getStyleClass().add("label-normal");
        lblPTTT.setFont(Font.font("System Bold", 12));
        lblPTTT.setLayoutX(5);
        lblPTTT.setLayoutY(150);

        Label lblPTTTValue = new Label("Chuyển khoản");
        lblPTTTValue.setId("lblPTTTValue");
        lblPTTTValue.getStyleClass().addAll("bold-label", "value-label");
        lblPTTTValue.setLayoutX(240);
        lblPTTTValue.setLayoutY(150);

        Label lblTienDaThanhToan = new Label("Tiền cọc:");
        lblTienDaThanhToan.getStyleClass().add("label-normal");
        lblTienDaThanhToan.setFont(Font.font("System Bold", 12));
        lblTienDaThanhToan.setLayoutX(5);
        lblTienDaThanhToan.setLayoutY(155);

        Label lblTienDaThanhToanValue = new Label("0 VNĐ");
        lblTienDaThanhToanValue.setId("lblTienDaThanhToanValue");
        lblTienDaThanhToanValue.getStyleClass().addAll("bold-label", "value-label");
        lblTienDaThanhToanValue.setLayoutX(240);
        lblTienDaThanhToanValue.setLayoutY(155);

        Label lblTienConLaiLabel = new Label("Còn phải thanh toán:");
        lblTienConLaiLabel.getStyleClass().add("bold-label");
        lblTienConLaiLabel.setLayoutX(5);
        lblTienConLaiLabel.setLayoutY(190);

        Label lblTienConLaiValue = new Label("0 VNĐ");
        lblTienConLaiValue.setId("lblTienConLaiValue");
        lblTienConLaiValue.getStyleClass().addAll("change-due-value", "bold-label", "value-label");
        lblTienConLaiValue.setLayoutX(240);
        lblTienConLaiValue.setLayoutY(190);

        summaryPane.getChildren().addAll(
                lblTongTienDat, lblTongTienDatValue,
                lblChietKhauPD, lblChietKhauPDValue,
                lblThueVAT, lblThueVATValue,
                lblTotalLabel, lblTongTienPhaiDatValue,
                lblTienDaThanhToan, lblTienDaThanhToanValue,
                lblTienConLaiLabel, lblTienConLaiValue
        );

        // Buttons
        Button btnInPhieuDat = new Button("In Phiếu Đặt");
        btnInPhieuDat.setId("btnInPhieuDat");
        btnInPhieuDat.setLayoutX(6);
        btnInPhieuDat.setLayoutY(570);
        btnInPhieuDat.setPrefSize(543, 53);
        btnInPhieuDat.getStyleClass().add("print-btn");
        btnInPhieuDat.setFont(Font.font("System Bold", 14));

        Button btnDong = new Button("Đóng");
        btnDong.setId("btnDong");
        btnDong.setLayoutX(6);
        btnDong.setLayoutY(633);
        btnDong.setPrefSize(543, 53);
        btnDong.getStyleClass().add("danger-btn");
        btnDong.setFont(Font.font("System Bold", 14));

        Button btnLapHoaDon = new Button("Lập hóa đơn");
        btnLapHoaDon.setId("btnLapHoaDon");
        btnLapHoaDon.setLayoutX(6);
        btnLapHoaDon.setLayoutY(690);
        btnLapHoaDon.setPrefSize(543, 53);
        btnLapHoaDon.getStyleClass().add("primary-btn");
        btnLapHoaDon.setFont(Font.font("System Bold", 14));

        // assemble right pane
        right.getChildren().addAll(lbInfoTitle, infoPane, lbSummaryTitle, summaryPane, btnInPhieuDat, btnDong, btnLapHoaDon);

        split.getItems().addAll(left, right);
        root.getChildren().add(split);

        Scene scene = new Scene(root);

        try {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ChiTietPhieuDat.css")).toExternalForm());
        } catch (Exception ignored) {}

        // Inject controls into controller (best-effort unchecked casts)
        try {
            ctrl.tblChiTietPhieuDat = (TableView) tblChiTietPhieuDat;
            ctrl.colSTT = (TableColumn) colSTT;
            ctrl.colTenSP = (TableColumn) colTenSP;
            ctrl.colSoLuong = (TableColumn) colSoLuong;
            ctrl.colDonVi = (TableColumn) colDonVi;
            ctrl.colDonGia = (TableColumn) colDonGia;
            ctrl.colNhaCungCap = (TableColumn) colNhaCungCap;
            ctrl.colThanhTien = (TableColumn) colThanhTien;
            ctrl.colTT = (TableColumn) colTT;

            ctrl.lblMaPhieuDatValue = lblMaPhieuValue;
            ctrl.lblNgayLapValue = lblNgayLapValue;
            ctrl.lblTenNhanVienValue = lblTenNhanVienValue;
            ctrl.lblTenNCCValue = lblTenNCCValue;
            ctrl.lblSDTNCCValue = lblSDTNCCValue;
            ctrl.lblGhiChuValue = lblGhiChuValue;
            ctrl.lbTT = lbTT;

            ctrl.lblTongTienDatValue = lblTongTienDatValue;
            ctrl.lblChietKhauPDValue = lblChietKhauPDValue;
            ctrl.lblThueVATValue = lblThueVATValue;
            ctrl.lblTongTienPhaiDatValue = lblTongTienPhaiDatValue;
            ctrl.lblPTTTValue = lblPTTTValue;
            ctrl.lblTienDaThanhToanValue = lblTienDaThanhToanValue;
            ctrl.lblTienConLaiValue = lblTienConLaiValue;

            ctrl.btnInPhieuDat = btnInPhieuDat;
            ctrl.btnDong = btnDong;
            ctrl.btnLapHoaDon = btnLapHoaDon;
        } catch (Exception ignored) {
            // best-effort injection; controller fields may differ
        }

        // initialize controller
        ctrl.initialize();

        stage.setTitle("Chi tiết phiếu đặt");
        stage.getIcons().add(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png"
                                )
                        )
                )
        );
        stage.setScene(scene);

    }
}

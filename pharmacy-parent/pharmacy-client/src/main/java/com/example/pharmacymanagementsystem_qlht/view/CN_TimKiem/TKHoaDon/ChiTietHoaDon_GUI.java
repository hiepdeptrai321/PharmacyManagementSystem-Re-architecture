package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoaDon;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.ChiTietHoaDon_Ctrl;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChiTietHoaDon_GUI {

    public void showWithController(Stage stage, ChiTietHoaDon_Ctrl ctrl) {
        AnchorPane root = buildUI(ctrl);
        Scene scene = new Scene(root, 1646, 895);
        try {
            String css = "/com/example/pharmacymanagementsystem_qlht/css/ChiTietHoaDon.css";
            if (getClass().getResource(css) != null)
                scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
        } catch (Exception ignored) {
        }
        stage.setScene(scene);
        stage.setTitle("Chi Tiết Hóa Đơn");
        stage.show();
    }

    private AnchorPane buildUI(ChiTietHoaDon_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14px;");

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPositions(0.69647);

        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);

        AnchorPane leftAnchor = new AnchorPane();
        Pane leftPane = new Pane();
        AnchorPane.setTopAnchor(leftPane, 0.0);
        AnchorPane.setBottomAnchor(leftPane, 0.0);
        AnchorPane.setLeftAnchor(leftPane, 0.0);
        AnchorPane.setRightAnchor(leftPane, 0.0);

        Pane headerPane = new Pane();
        headerPane.setLayoutX(6);
        headerPane.setLayoutY(43);
        headerPane.setPrefSize(857, 29);

        Label lblMaHoaDon = new Label("Mã hóa đơn: ");
        lblMaHoaDon.getStyleClass().add("bold-label");
        lblMaHoaDon.setFont(Font.font(13));
        lblMaHoaDon.setLayoutX(5);
        lblMaHoaDon.setLayoutY(0);

        ctrl.lblMaHoaDonValue = new Label("HD012345");
        ctrl.lblMaHoaDonValue.setLayoutX(97);
        ctrl.lblMaHoaDonValue.setLayoutY(0);
        ctrl.lblMaHoaDonValue.getStyleClass().add("value-label");
        ctrl.lblMaHoaDonValue.setFont(Font.font(13));

        Label lblNgayLap = new Label("Ngày lập: ");
        lblNgayLap.setLayoutX(282);
        lblNgayLap.setLayoutY(0);
        lblNgayLap.getStyleClass().addAll("bold-label", "label-padding");
        lblNgayLap.setFont(Font.font("System Bold", 14));

        ctrl.lblNgayLapValue = new Label("01/10/2025");
        ctrl.lblNgayLapValue.setLayoutX(353);
        ctrl.lblNgayLapValue.setLayoutY(0);
        ctrl.lblNgayLapValue.setPrefSize(174, 20);
        ctrl.lblNgayLapValue.getStyleClass().add("value-label");
        ctrl.lblNgayLapValue.setFont(Font.font(13));

        ctrl.lblLoaiHoaDon = new Label("Hóa đơn cho thuốc kê đơn(OTC):");
        ctrl.lblLoaiHoaDon.setLayoutX(537);
        ctrl.lblLoaiHoaDon.setLayoutY(0);
        ctrl.lblLoaiHoaDon.setPrefSize(219, 20);
        ctrl.lblLoaiHoaDon.getStyleClass().addAll("bold-label", "label-padding");
        ctrl.lblLoaiHoaDon.setFont(Font.font("System Bold", 14));

        ctrl.lblMaDonThuocValue = new Label("0");
        ctrl.lblMaDonThuocValue.setLayoutX(759);
        ctrl.lblMaDonThuocValue.setLayoutY(0);
        ctrl.lblMaDonThuocValue.setPrefSize(99, 20);
        ctrl.lblMaDonThuocValue.getStyleClass().add("value-label");
        ctrl.lblMaDonThuocValue.setFont(Font.font(13));

        headerPane.getChildren().addAll(lblMaHoaDon, ctrl.lblMaHoaDonValue, lblNgayLap, ctrl.lblNgayLapValue, ctrl.lblLoaiHoaDon, ctrl.lblMaDonThuocValue);

        Label lblDanhSachSP = new Label("DANH SÁCH SẢN PHẨM");
        lblDanhSachSP.setLayoutX(9);
        lblDanhSachSP.setLayoutY(69);
        lblDanhSachSP.getStyleClass().add("section-label");

        ctrl.tblChiTietHoaDon = new TableView<>();
        ctrl.tblChiTietHoaDon.setLayoutX(9);
        ctrl.tblChiTietHoaDon.setLayoutY(103);
        ctrl.tblChiTietHoaDon.setPrefSize(1130, 765);
        ctrl.tblChiTietHoaDon.setStyle("-fx-font-size: 14;");
        ctrl.tblChiTietHoaDon.getStyleClass().add("main-table");
        ctrl.tblChiTietHoaDon.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ctrl.colNSTT = new TableColumn<>("STT");
        ctrl.colNSTT.setPrefWidth(50);
        ctrl.colNSTT.setStyle("-fx-alignment: CENTER;");

        ctrl.colNTen = new TableColumn<>("Tên sản phẩm/Hoạt chất");
        ctrl.colNTen.setPrefWidth(345);
        ctrl.colNTen.setStyle("-fx-alignment: CENTER_LEFT;");

        ctrl.colNSL = new TableColumn<>("Số lượng");
        ctrl.colNSL.setPrefWidth(130);
        ctrl.colNSL.setStyle("-fx-alignment: CENTER;");

        ctrl.colNDonVi = new TableColumn<>("Đơn vị");
        ctrl.colNDonVi.setPrefWidth(103);
        ctrl.colNDonVi.setStyle("-fx-alignment: CENTER;");

        ctrl.colNDonGia = new TableColumn<>("Đơn giá");
        ctrl.colNDonGia.setPrefWidth(170);
        ctrl.colNDonGia.setStyle("-fx-alignment: CENTER;");

        ctrl.colNChietKhau = new TableColumn<>("Giảm giá");
        ctrl.colNChietKhau.setPrefWidth(148);
        ctrl.colNChietKhau.setStyle("-fx-alignment: CENTER;");

        ctrl.colNThanhTien = new TableColumn<>("Thành tiền");
        ctrl.colNThanhTien.setPrefWidth(149);
        ctrl.colNThanhTien.setStyle("-fx-alignment: CENTER;");

        // Thêm khai báo cột mới trong Controller
        ctrl.colMaLoHang = new TableColumn<>("Mã lô");
        ctrl.colMaLoHang.setPrefWidth(100);
        ctrl.colMaLoHang.setStyle("-fx-alignment: CENTER;");

        // Điều chỉnh lại width các cột
        ctrl.colNSTT.setPrefWidth(50);

        ctrl.colNTen.setPrefWidth(345);

        ctrl.colMaLoHang.setPrefWidth(100); // Cột mới

        ctrl.colNSL.setPrefWidth(90);  // Giảm từ 130 → 90

        ctrl.colNDonVi.setPrefWidth(80);  // Giảm từ 103 → 80

        ctrl.colNDonGia.setPrefWidth(120);  // Giảm từ 170 → 120

        ctrl.colNChietKhau.setPrefWidth(110);  // Giảm từ 148 → 110

        ctrl.colNThanhTien.setPrefWidth(135);  // Giảm từ 149 → 135

        // Thêm cột vào table theo đúng thứ tự
        ctrl.tblChiTietHoaDon.getColumns().addAll(
                ctrl.colNSTT,
                ctrl.colNTen,
                ctrl.colMaLoHang,  // Thêm cột mới sau Tên SP
                ctrl.colNSL,
                ctrl.colNDonVi,
                ctrl.colNDonGia,
                ctrl.colNChietKhau,
                ctrl.colNThanhTien
        );

        Label lblTitle = new Label("CHI TIẾT HÓA ĐƠN");
        lblTitle.setLayoutX(10);
        lblTitle.setLayoutY(6);
        lblTitle.getStyleClass().add("title-label");

        leftPane.getChildren().addAll(headerPane, lblDanhSachSP, ctrl.tblChiTietHoaDon, lblTitle);
        leftAnchor.getChildren().add(leftPane);

        AnchorPane rightAnchor = new AnchorPane();
        rightAnchor.getStyleClass().add("right-pane-bg");

        Pane rightPane = new Pane();
        rightPane.setLayoutX(22);
        rightPane.setLayoutY(10);
        AnchorPane.setTopAnchor(rightPane, 10.0);
        AnchorPane.setLeftAnchor(rightPane, 22.0);
        AnchorPane.setRightAnchor(rightPane, 10.0);
        AnchorPane.setBottomAnchor(rightPane, 10.0);

        Label lblTTGD = new Label("THÔNG TIN GIAO DỊCH");
        lblTTGD.setLayoutX(0);
        lblTTGD.setLayoutY(0);
        lblTTGD.setFont(Font.font("Arial Bold", 24));

        Pane infoPane = new Pane();
        infoPane.setLayoutX(0);
        infoPane.setLayoutY(41);
        infoPane.setPrefSize(453, 213);
        infoPane.getStyleClass().add("info-pane");

        Label lblTenNV = new Label("Tên nhân viên:");
        lblTenNV.setLayoutX(5);
        lblTenNV.setLayoutY(5);
        lblTenNV.getStyleClass().add("bold-label");

        ctrl.lblTenNhanVienValue = new Label();
        ctrl.lblTenNhanVienValue.setLayoutX(140);
        ctrl.lblTenNhanVienValue.setLayoutY(5);
        ctrl.lblTenNhanVienValue.getStyleClass().add("value-label");

        Label lblTenKH = new Label("Tên khách hàng:");
        lblTenKH.setLayoutX(5);
        lblTenKH.setLayoutY(35);
        lblTenKH.getStyleClass().add("bold-label");

        ctrl.lblTenKhachHangValue = new Label();
        ctrl.lblTenKhachHangValue.setLayoutX(140);
        ctrl.lblTenKhachHangValue.setLayoutY(35);
        ctrl.lblTenKhachHangValue.getStyleClass().add("value-label");

        Label lblSDT = new Label("SĐT Khách hàng:");
        lblSDT.setLayoutX(5);
        lblSDT.setLayoutY(65);
        lblSDT.getStyleClass().add("bold-label");

        ctrl.lblSDTKhachHangValue = new Label();
        ctrl.lblSDTKhachHangValue.setLayoutX(140);
        ctrl.lblSDTKhachHangValue.setLayoutY(65);
        ctrl.lblSDTKhachHangValue.getStyleClass().add("value-label");

        Label lblGhiChu = new Label("Ghi chú:");
        lblGhiChu.setLayoutX(5);
        lblGhiChu.setLayoutY(95);
        lblGhiChu.getStyleClass().add("bold-label");

        ctrl.lblGhiChuValue = new Label("Không có.");
        ctrl.lblGhiChuValue.setLayoutX(140);
        ctrl.lblGhiChuValue.setLayoutY(95);
        ctrl.lblGhiChuValue.setPrefWidth(250);
        ctrl.lblGhiChuValue.getStyleClass().add("value-label");
        ctrl.lblGhiChuValue.setWrapText(true);

        infoPane.getChildren().addAll(lblTenNV, ctrl.lblTenNhanVienValue, lblTenKH, ctrl.lblTenKhachHangValue, lblSDT, ctrl.lblSDTKhachHangValue, lblGhiChu, ctrl.lblGhiChuValue);

        Separator sep = new Separator();
        sep.setLayoutX(0);
        sep.setLayoutY(270);
        sep.setPrefWidth(453);

        Label lblTongKet = new Label("TÓM TẮT THANH TOÁN");
        lblTongKet.setLayoutX(0);
        lblTongKet.setLayoutY(290);
        lblTongKet.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
        lblTongKet.setFont(Font.font(24));

        Pane summaryPane = new Pane();
        summaryPane.setLayoutX(0);
        summaryPane.setLayoutY(330);
        summaryPane.setPrefSize(453, 280);
        summaryPane.getStyleClass().add("summary-pane");

        Label lblGiamSPTitle = new Label("Giảm theo sản phẩm:");
        lblGiamSPTitle.setLayoutX(5);
        lblGiamSPTitle.setLayoutY(10);
        lblGiamSPTitle.getStyleClass().add("label-normal");
        lblGiamSPTitle.setFont(Font.font("System Bold", 14));

        ctrl.lblGiamTheoSP = new Label("0 VNĐ");
        ctrl.lblGiamTheoSP.setLayoutX(240);
        ctrl.lblGiamTheoSP.setLayoutY(10);
        ctrl.lblGiamTheoSP.getStyleClass().addAll("bold-label", "value-label");
        ctrl.lblGiamTheoSP.setFont(Font.font(13));

        Label lblTongHangTitle = new Label("Tổng tiền hàng (trước VAT):");
        lblTongHangTitle.setLayoutX(5);
        lblTongHangTitle.setLayoutY(50);
        lblTongHangTitle.setFont(Font.font("System Bold", 14));
        lblTongHangTitle.getStyleClass().add("label-normal");

        ctrl.lblTongTienHang = new Label("0 VNĐ");
        ctrl.lblTongTienHang.setLayoutX(240);
        ctrl.lblTongTienHang.setLayoutY(50);
        ctrl.lblTongTienHang.getStyleClass().addAll("label-discount", "bold-label", "value-label");
        ctrl.lblTongTienHang.setFont(Font.font(13));

        Label lblVATTitle = new Label("Thuế (VAT):");
        lblVATTitle.setLayoutX(5);
        lblVATTitle.setLayoutY(90);
        lblVATTitle.getStyleClass().add("label-normal");
        lblVATTitle.setFont(Font.font("Arial Bold", 14));

        ctrl.lblVAT = new Label("0 VNĐ");
        ctrl.lblVAT.setLayoutX(240);
        ctrl.lblVAT.setLayoutY(90);
        ctrl.lblVAT.getStyleClass().addAll("bold-label", "value-label");
        ctrl.lblVAT.setFont(Font.font(13));

        Label lblGiamHDTitle = new Label("Giảm theo hóa đơn:");
        lblGiamHDTitle.setLayoutX(5);
        lblGiamHDTitle.setLayoutY(130);
        lblGiamHDTitle.getStyleClass().add("label-normal");
        lblGiamHDTitle.setFont(Font.font("System Bold", 14));

        ctrl.lblGiamTheoHD = new Label("0 VNĐ");
        ctrl.lblGiamTheoHD.setLayoutX(240);
        ctrl.lblGiamTheoHD.setLayoutY(130);
        ctrl.lblGiamTheoHD.getStyleClass().addAll("bold-label", "value-label");
        ctrl.lblGiamTheoHD.setFont(Font.font(13));

        Separator sep2 = new Separator();
        sep2.setLayoutX(0);
        sep2.setLayoutY(170);
        sep2.setPrefWidth(453);

        Label lblTongTT = new Label("TỔNG THANH TOÁN:");
        lblTongTT.setLayoutX(5);
        lblTongTT.setLayoutY(190);
        lblTongTT.setFont(Font.font("System Bold", 15));
        lblTongTT.getStyleClass().addAll("bold-label", "section-label");

        ctrl.lblTongThanhToan = new Label("0 VNĐ");
        ctrl.lblTongThanhToan.setLayoutX(240);
        ctrl.lblTongThanhToan.setLayoutY(190);
        ctrl.lblTongThanhToan.getStyleClass().addAll("main-value-label", "grand-total-value", "bold-label", "value-label");
        ctrl.lblTongThanhToan.setFont(Font.font(15));

        summaryPane.getChildren().addAll(
                lblGiamSPTitle, ctrl.lblGiamTheoSP,
                lblTongHangTitle, ctrl.lblTongTienHang,
                lblVATTitle, ctrl.lblVAT,
                lblGiamHDTitle, ctrl.lblGiamTheoHD,
                sep2, lblTongTT, ctrl.lblTongThanhToan
        );

        Pane buttonPane = new Pane();
        buttonPane.setLayoutX(0);
        buttonPane.setLayoutY(630);
        buttonPane.setPrefSize(453, 220);

        ctrl.btnInHoaDon = new Button("In Hóa Đơn");
        ctrl.btnInHoaDon.setLayoutX(10);
        ctrl.btnInHoaDon.setLayoutY(20);
        ctrl.btnInHoaDon.setPrefSize(433, 70);
        ctrl.btnInHoaDon.getStyleClass().add("print-btn");
        ctrl.btnInHoaDon.setFont(Font.font("System Bold", 14));
        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.color(0.65, 0.65, 0.65));
        ctrl.btnInHoaDon.setEffect(ds1);
        ctrl.btnInHoaDon.setOnAction(e -> ctrl.xuLyXuatPDF());

        ctrl.btnDong = new Button("Hoàn Tất");
        ctrl.btnDong.setLayoutX(10);
        ctrl.btnDong.setLayoutY(110);
        ctrl.btnDong.setPrefSize(433, 70);
        ctrl.btnDong.getStyleClass().add("danger-btn");
        ctrl.btnDong.setFont(Font.font("System Bold", 14));
        DropShadow ds2 = new DropShadow();
        ds2.setColor(Color.color(0.65, 0.65, 0.65));
        ctrl.btnDong.setEffect(ds2);
        ctrl.btnDong.setOnAction(e -> ((Stage) ctrl.btnDong.getScene().getWindow()).close());

        buttonPane.getChildren().addAll(ctrl.btnInHoaDon, ctrl.btnDong);

        rightPane.getChildren().addAll(lblTTGD, infoPane, sep, lblTongKet, summaryPane, buttonPane);
        rightAnchor.getChildren().add(rightPane);

        splitPane.getItems().addAll(leftAnchor, rightAnchor);

        root.getChildren().add(splitPane);

        return root;
    }
}
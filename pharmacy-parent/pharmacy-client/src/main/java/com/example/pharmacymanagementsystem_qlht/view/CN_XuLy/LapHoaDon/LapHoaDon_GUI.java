package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapHoaDon;

import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapHoaDon.LapHoaDon_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public class LapHoaDon_GUI {

    public void showWithController(Stage stage, LapHoaDon_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/LapHoaDon.css")
        ).toExternalForm());

        // --- Title ---
        Label lbTitle = new Label("LẬP HÓA ĐƠN");
        lbTitle.setLayoutX(40);
        lbTitle.setLayoutY(21);
        lbTitle.getStyleClass().add("title-label");

        ImageView imgTitle = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/transaction.png")
        )));
        imgTitle.setFitHeight(53);
        imgTitle.setFitWidth(62);
        imgTitle.setLayoutX(234);
        imgTitle.setLayoutY(6);
        imgTitle.setPreserveRatio(true);

        // --- Search Pane ---
        Pane searchPane = new Pane();
        searchPane.setLayoutX(40);
        searchPane.setLayoutY(66);
        searchPane.setPrefSize(1106, 70);
        searchPane.getStyleClass().add("search-pane");

        Label lblTimSP = new Label("Tìm sản phẩm:");
        lblTimSP.setLayoutX(15);
        lblTimSP.setLayoutY(24);
        lblTimSP.setFont(Font.font("System Bold", 13));

        TextField txtTimThuoc = new TextField();
        txtTimThuoc.setId("txtTimThuoc");
        txtTimThuoc.setLayoutX(118);
        txtTimThuoc.setLayoutY(14);
        txtTimThuoc.setPrefSize(845, 40);
        txtTimThuoc.setPromptText("Nhập tên thuốc/ tên sản phẩm ...");

        Button btnXoaRong = new Button("Xóa rỗng");
        btnXoaRong.setId("btnXoaRong");
        btnXoaRong.setLayoutX(987);
        btnXoaRong.setLayoutY(14);
        btnXoaRong.setPrefSize(100, 40);

        searchPane.getChildren().addAll(lblTimSP, txtTimThuoc, btnXoaRong);

        // --- Table ---
        Label lblChiTiet = new Label("Chi tiết sản phẩm");
        lblChiTiet.setLayoutX(40);
        lblChiTiet.setLayoutY(150);
        lblChiTiet.getStyleClass().add("section-label");

        TableView<ChiTietHoaDon> tblChiTietHD = new TableView<>();
        tblChiTietHD.setId("tblChiTietHD");
        tblChiTietHD.setLayoutX(40);
        tblChiTietHD.setLayoutY(180);
        tblChiTietHD.setPrefSize(1104, 686);
        tblChiTietHD.getStyleClass().add("main-table");

        TableColumn<ChiTietHoaDon, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(50);
        TableColumn<ChiTietHoaDon, String> colTenSP = new TableColumn<>("Tên sản phẩm");
        colTenSP.setPrefWidth(324.6);
        TableColumn<ChiTietHoaDon, Boolean> colKeDon = new TableColumn<>("Kê đơn");
        colKeDon.setPrefWidth(80);
        TableColumn<ChiTietHoaDon, String> colDonGia = new TableColumn<>("Đơn giá");
        colDonGia.setPrefWidth(143.3);
        TableColumn<ChiTietHoaDon, Number> colSL = new TableColumn<>("Số lượng");
        colSL.setPrefWidth(131);
        TableColumn<ChiTietHoaDon, String> colDonVi = new TableColumn<>("Đơn vị");
        colDonVi.setPrefWidth(100);

        TableColumn<ChiTietHoaDon, String> colChietKhau = new TableColumn<>("Giảm giá");
        colChietKhau.setPrefWidth(123);

        TableColumn<ChiTietHoaDon, String> colThanhTien = new TableColumn<>("Thành tiền");
        colThanhTien.setPrefWidth(174);

        TableColumn<ChiTietHoaDon, String> colBo = new TableColumn<>("Bỏ");
        colBo.setPrefWidth(55);
        tblChiTietHD.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tblChiTietHD.getColumns().addAll(colSTT, colTenSP, colKeDon, colDonGia, colSL, colDonVi, colChietKhau, colThanhTien, colBo);

        // --- Info Pane ---
        Pane infoPane = new Pane();
        infoPane.setLayoutX(1181);
        infoPane.setLayoutY(14);
        infoPane.setPrefSize(451, 867);
        infoPane.getStyleClass().add("info-pane");
    
// Tiêu đề
        Label lblTitle = new Label("THÔNG TIN GIAO DỊCH");
        lblTitle.setLayoutX(30);
        lblTitle.setLayoutY(186);
        lblTitle.getStyleClass().add("bold-label");

// ======= Các label và input =======
        Label lblHoTenKHText = new Label("Họ tên khách hàng:");
        lblHoTenKHText.setLayoutX(30);
        lblHoTenKHText.setLayoutY(226);
        lblHoTenKHText.setFont(Font.font(14));

        TextField txtTenKH = new TextField();
        txtTenKH.setLayoutX(191);
        txtTenKH.setLayoutY(220);
        txtTenKH.setPrefSize(208, 27);
        txtTenKH.setEditable(false);
        txtTenKH.setId("txtTenKH");

        Label lblSDTText = new Label("Số điện thoại:");
        lblSDTText.setLayoutX(30);
        lblSDTText.setLayoutY(266);
        lblSDTText.setFont(Font.font(14));

        TextField txtSDT = new TextField();
        txtSDT.setLayoutX(190);
        txtSDT.setLayoutY(260);
        txtSDT.setPrefSize(209, 27);
        txtSDT.setEditable(false);
        txtSDT.setId("txtSDT");

// Nút tìm và thêm khách hàng
        Button btnTimKhachHang = new Button("Tìm khách hàng");
        btnTimKhachHang.setLayoutX(66);
        btnTimKhachHang.setLayoutY(301);
        btnTimKhachHang.setPrefSize(116, 28);
        btnTimKhachHang.setOnAction(e -> ctrl.xuLyTimKhachHang());
        btnTimKhachHang.setId("btnTimKhachHang");

        Button btnThemKH = new Button("Thêm khách hàng");
        btnThemKH.setLayoutX(258);
        btnThemKH.setLayoutY(300);
        btnThemKH.setPrefSize(124, 30);
        btnThemKH.setOnAction(e -> ctrl.xuLyThemKH());
        btnThemKH.setId("btnThemKH");

// Nhập mã đơn thuốc
        Label lblMaDonThuocText = new Label("Nhập mã đơn thuốc:");
        lblMaDonThuocText.setLayoutX(28);
        lblMaDonThuocText.setLayoutY(354);
        lblMaDonThuocText.setFont(Font.font(14));

        TextField txtMaDonThuoc = new TextField();
        txtMaDonThuoc.setLayoutX(191);
        txtMaDonThuoc.setLayoutY(351);
        txtMaDonThuoc.setPrefSize(209, 27);
        txtMaDonThuoc.setId("txtMaDonThuoc");

// Ngày kê đơn
        Label lblNgayLapText = new Label("Ngày kê đơn:");
        lblNgayLapText.setLayoutX(30);
        lblNgayLapText.setLayoutY(398);
        lblNgayLapText.setFont(Font.font(14));

        DatePicker dpNgayLap = new DatePicker();
        dpNgayLap.setLayoutX(191);
        dpNgayLap.setLayoutY(395);
        dpNgayLap.setPrefSize(209, 27);
        dpNgayLap.setId("dpNgayLap");

// Radio OTC
        RadioButton rbOTC = new RadioButton("Không kê đơn (OTC)");
        rbOTC.setLayoutX(231);
        rbOTC.setLayoutY(188);
        rbOTC.setFont(Font.font(14));
        rbOTC.setId("rbOTC");

// Separator
        Separator sep1 = new Separator();
        sep1.setLayoutY(159);
        sep1.setPrefWidth(450);

        Separator sep2 = new Separator();
        sep2.setLayoutY(339);
        sep2.setPrefWidth(450);

        Separator sep3 = new Separator();
        sep3.setLayoutY(442);
        sep3.setPrefWidth(450);

// Ảnh logo
        ImageView imgLogo = new ImageView(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/new.png")));
        imgLogo.setFitWidth(433);
        imgLogo.setFitHeight(145);
        imgLogo.setLayoutX(11);
        imgLogo.setLayoutY(11);
        imgLogo.setPreserveRatio(true);

// ======= Các Label giá trị và mô tả =======
        Label lblGiamGiaText = new Label("Tổng giảm giá:");
        lblGiamGiaText.setLayoutX(30);
        lblGiamGiaText.setLayoutY(471);
        lblGiamGiaText.setFont(Font.font(14));

        Label lblGiamGia = new Label("0 VNĐ");
        lblGiamGia.setLayoutX(203);
        lblGiamGia.setLayoutY(470);
        lblGiamGia.setPrefSize(216, 20);
        lblGiamGia.getStyleClass().add("value-label");
        lblGiamGia.setId("lblGiamGia");

        Label lblTongTienText = new Label("Tổng tiền:");
        lblTongTienText.setLayoutX(30);
        lblTongTienText.setLayoutY(502);
        lblTongTienText.setFont(Font.font(14));

        Label lblTongTien = new Label("0 VNĐ");
        lblTongTien.setLayoutX(202);
        lblTongTien.setLayoutY(501);
        lblTongTien.setPrefSize(210, 20);
        lblTongTien.getStyleClass().add("value-label");
        lblTongTien.setId("lblTongTien");

        Label lblGiamTheoHDText = new Label("Giảm giá theo hóa đơn:");
        lblGiamTheoHDText.setLayoutX(31);
        lblGiamTheoHDText.setLayoutY(532);
        lblGiamTheoHDText.setFont(Font.font(14));

        Label lblGiamTheoHD = new Label("0 VNĐ");
        lblGiamTheoHD.setLayoutX(202);
        lblGiamTheoHD.setLayoutY(531);
        lblGiamTheoHD.setPrefSize(204, 20);
        lblGiamTheoHD.getStyleClass().add("value-label");
        lblGiamTheoHD.setId("lblGiamTheoHD");

        Label lblVATText = new Label("Thuế (VAT 5%):");
        lblVATText.setLayoutX(31);
        lblVATText.setLayoutY(560);
        lblVATText.setFont(Font.font(14));

        Label lblVAT = new Label("0 VNĐ");
        lblVAT.setLayoutX(202);
        lblVAT.setLayoutY(559);
        lblVAT.setPrefSize(204, 20);
        lblVAT.getStyleClass().add("value-label");
        lblVAT.setId("lblVAT");

        Label lblThanhTienText = new Label("Thành tiền:");
        lblThanhTienText.setLayoutX(33);
        lblThanhTienText.setLayoutY(605);
        lblThanhTienText.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label lblThanhTien = new Label("0 VNĐ");
        lblThanhTien.setLayoutX(200);
        lblThanhTien.setLayoutY(601);
        lblThanhTien.setPrefSize(204, 30);
        lblThanhTien.getStyleClass().add("main-value-label");
        lblThanhTien.setId("lblThanhTien");

        Label lblThongBaoTT = new Label("");
        lblThongBaoTT.setLayoutX(310);
        lblThongBaoTT.setLayoutY(610);
        lblThanhTien.setPrefSize(204, 30);

// Phương thức thanh toán
        Label lblPhuongThucText = new Label("Phương thức thanh toán:");
        lblPhuongThucText.setLayoutX(33);
        lblPhuongThucText.setLayoutY(644);
        lblPhuongThucText.setFont(Font.font(14));

        ChoiceBox<String> cbPhuongThucTT = new ChoiceBox<>();
        cbPhuongThucTT.setLayoutX(201);
        cbPhuongThucTT.setLayoutY(640);
        cbPhuongThucTT.setPrefSize(204, 25);
        cbPhuongThucTT.setId("cbPhuongThucTT");

// Pane tiền mặt
        Pane paneTienMat = new Pane();
        paneTienMat.setLayoutX(9);
        paneTienMat.setLayoutY(669);
        paneTienMat.setPrefSize(414, 87);
        paneTienMat.setId("paneTienMat");

        Label lblTienKhachDuaText = new Label("Số tiền khách đưa:");
        lblTienKhachDuaText.setLayoutX(25);
        lblTienKhachDuaText.setLayoutY(10);
        lblTienKhachDuaText.setFont(Font.font(14));

        TextField txtSoTienKhachDua = new TextField();
        txtSoTienKhachDua.setLayoutX(191);
        txtSoTienKhachDua.setLayoutY(4);
        txtSoTienKhachDua.setPrefSize(204, 27);
        txtSoTienKhachDua.setId("txtSoTienKhachDua");

        Label lblTienThuaText = new Label("Tiền thừa:");
        lblTienThuaText.setLayoutX(24);
        lblTienThuaText.setLayoutY(40);
        lblTienThuaText.setFont(Font.font(14));

        Label lblTienThua = new Label("0 VNĐ");
        lblTienThua.setLayoutX(191);
        lblTienThua.setLayoutY(40);
        lblTienThua.setPrefSize(204, 20);
        lblTienThua.getStyleClass().add("value-label");
        lblTienThua.setId("lblTienThua");

        paneTienMat.getChildren().addAll(lblTienKhachDuaText, txtSoTienKhachDua, lblTienThuaText, lblTienThua);

// Nút Thanh toán & Hủy
        Button btnThanhToan = new Button("Thanh toán");
        btnThanhToan.setLayoutX(9);
        btnThanhToan.setLayoutY(755);
        btnThanhToan.setPrefSize(427, 40);
        btnThanhToan.setStyle("-fx-background-color: green;");
        btnThanhToan.getStyleClass().add("success-btn");
        btnThanhToan.setOnAction(e -> ctrl.xuLyThanhToan());
        btnThanhToan.setId("btnThanhToan");

        Button btnHuy = new Button("Hủy");
        btnHuy.setLayoutX(9);
        btnHuy.setLayoutY(803);
        btnHuy.setPrefSize(429, 40);
        btnHuy.setStyle("-fx-background-color: red;");
        btnHuy.getStyleClass().add("print-btn");
        btnHuy.setOnAction(e -> ctrl.xuLyHuy());
        btnHuy.setId("btnHuy");

// ======= Thêm tất cả vào Pane =======
        infoPane.getChildren().addAll(
                lblTitle, rbOTC, imgLogo, sep1, sep2, sep3,
                lblHoTenKHText, txtTenKH, lblSDTText, txtSDT,
                btnTimKhachHang, btnThemKH,
                lblMaDonThuocText, txtMaDonThuoc, lblNgayLapText, dpNgayLap,
                lblGiamGiaText, lblGiamGia, lblTongTienText, lblTongTien,
                lblGiamTheoHDText, lblGiamTheoHD, lblVATText, lblVAT,
                lblThanhTienText, lblThanhTien, lblThongBaoTT, lblPhuongThucText, cbPhuongThucTT,
                paneTienMat, btnThanhToan, btnHuy
        );

        // --- Assemble root ---
        root.getChildren().addAll(lbTitle, imgTitle, searchPane, lblChiTiet, tblChiTietHD, infoPane);

        // --- Scene ---
        Scene scene = new Scene(root);

        // --- Inject controller ---
        ctrl.txtTimThuoc = txtTimThuoc;
        ctrl.tblChiTietHD = tblChiTietHD;
        ctrl.btnXoaRong = btnXoaRong;
        ctrl.colSTT = colSTT;
        ctrl.colTenSP = colTenSP;
        ctrl.colKeDon = colKeDon;
        ctrl.colDonGia = colDonGia;
        ctrl.colSL = colSL;
        ctrl.colDonVi = colDonVi;
        ctrl.colChietKhau = colChietKhau;
        ctrl.colThanhTien = colThanhTien;
        ctrl.colBo = colBo;
        ctrl.txtTenKH = txtTenKH;
        ctrl.txtSDT = txtSDT;
        ctrl.txtMaDonThuoc = txtMaDonThuoc;
        ctrl.dpNgayLap = dpNgayLap;
        ctrl.cbPhuongThucTT = cbPhuongThucTT;
        ctrl.paneTienMat = paneTienMat;
        ctrl.txtSoTienKhachDua = txtSoTienKhachDua;
        ctrl.lblTienThua = lblTienThua;
        ctrl.lblTongTien = lblTongTien;
        ctrl.lblThongBaoTT = lblThongBaoTT;
        ctrl.lblGiamGia = lblGiamGia;
        ctrl.lblVAT = lblVAT;
        ctrl.lblThanhTien = lblThanhTien;
        ctrl.lblGiamTheoHD = lblGiamTheoHD;
        ctrl.rbOTC = rbOTC;
        ctrl.btnThemKH = btnThemKH;
        ctrl.btnThanhToan = btnThanhToan;

        // --- Initialize controller ---
        ctrl.initialize();

        stage.setTitle("Lập hóa đơn");
        stage.setScene(scene);
    }
}
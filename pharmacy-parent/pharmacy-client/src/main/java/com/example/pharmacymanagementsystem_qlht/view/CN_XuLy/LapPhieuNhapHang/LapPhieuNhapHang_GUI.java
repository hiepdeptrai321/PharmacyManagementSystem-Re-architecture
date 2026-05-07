package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuNhapHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang.LapPhieuNhapHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.CTPN_TSPTL_CHTDVT;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietDonViTinh;
import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class LapPhieuNhapHang_GUI extends Application {

    @Override
    public void start(Stage stage) {
        ViewRefs v = buildUIForController();
        Scene scene = new Scene(v.root, 1646, 895);
        addStyles(scene);
        stage.setTitle("Lập phiếu nhập hàng");
        stage.setScene(scene);
        stage.show();
    }

    /** Dùng trong app: tạo UI và gán thẳng control cho controller (không lookup). */
    public void showWithController(Stage stage, LapPhieuNhapHang_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // ===== Gán control cho controller (đúng fx:id) =====
        ctrl.tblNhapThuoc = v.tblNhapThuoc;
        ctrl.colSTT = v.colSTT;
        ctrl.colMaThuoc = v.colMaThuoc;
        ctrl.colTenThuoc = v.colTenThuoc;
        ctrl.colNSX = v.colNSX;
        ctrl.colHanSuDung = v.colHanSuDung;
        ctrl.colLoHang = v.colLoHang;
        ctrl.colDonViNhap = v.colDonViNhap;
        ctrl.colSoLuong = v.colSoLuong;
        ctrl.colDonGiaNhap = v.colDonGiaNhap;
        ctrl.colChietKhau = v.colChietKhau;
        ctrl.colThue = v.colThue;
        ctrl.colThanhTien = v.colThanhTien;
        ctrl.colXoa = v.colXoa;

        ctrl.cbxNCC = v.cbxNCC;
        ctrl.txtNgayNhap = v.txtNgayNhap;
        ctrl.txtMaPhieuNhap = v.txtMaPhieuNhap;
        ctrl.txtTimKiemChiTietDonViTinh = v.txtTimKiemChiTietDonViTinh;

        ctrl.listViewChiTietDonViTinh = v.listViewChiTietDonViTinh;
        ctrl.listViewNhaCungCap = v.listViewNhaCungCap;

        ctrl.txtTongGiaNhap = v.txtTongGiaNhap;
        ctrl.txtTongTienChietKhau = v.txtTongTienChietKhau;
        ctrl.txtTongTienThue = v.txtTongTienThue;
        ctrl.txtThanhTien = v.txtThanhTien;
        ctrl.txtGhiChu = v.txtGhiChu;

        // Map sự kiện onMouseClicked từ FXML
        v.pnThemNCC.setOnMouseClicked(ctrl::btnThemNCCClick);
        v.lblThemThuoc.setOnMouseClicked(ctrl::btnThemThuocClick);
        v.btnHuyPane.setOnMouseClicked(ctrl::btnHuy);
        v.btnLuuPane.setOnMouseClicked(ctrl::btnLuu);
        v.btnNhapHangExcelPane.setOnMouseClicked(ctrl::btnThemThuocByExcel);

        // Nếu controller có initialize()
        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(v.root, 1646, 895);
        addStyles(scene);
        stage.setTitle("Lập phiếu nhập hàng");
        stage.setScene(scene);
    }

    // ================== XÂY UI & GIỮ THAM CHIẾU ==================
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        // Root
        v.root = new Pane();
        v.root.setPrefSize(1646, 895);

        // ===== Title strip =====
        Pane lbtitle = new Pane();
        lbtitle.setId("lbtitle");
        lbtitle.setPrefSize(1646, 44);

        Label lb = new Label("Lập phiếu nhập hàng");
        lb.setLayoutX(29);
        lb.setPrefSize(247, 44);
        lb.setFont(Font.font("System Bold", 25));

        ImageView ivImport = imageView("/com/example/pharmacymanagementsystem_qlht/img/import.png", 38, 38, true);
        ivImport.setLayoutX(287);
        ivImport.setLayoutY(3);
        lbtitle.setStyle(
                "-fx-border-color: transparent transparent #cfcfcf transparent;" +
                        "-fx-border-width: 0 0 1 0;"
        );

        lbtitle.getChildren().addAll(lb, ivImport);

        // ===== Khối lớn bên trái: bảng + thanh trên =====
        Pane leftTop = new Pane();
        leftTop.setLayoutY(44);
        leftTop.setPrefSize(1321, 154);

        // --- thanh trên (NCC, mã PN, ngày nhập, thêm thuốc, tìm DVT, excel) ---
        Pane topBar = new Pane();
        topBar.setPrefSize(1324, 93);

        v.cbxNCC = new ComboBox<>();
        v.cbxNCC.setEditable(true);
        v.cbxNCC.setLayoutX(29);
        v.cbxNCC.setLayoutY(42);
        v.cbxNCC.setPrefSize(257, 30);
        v.cbxNCC.setPromptText("Chọn nhà cung cấp");

        v.pnThemNCC = new Pane();
        v.pnThemNCC.setLayoutX(286);
        v.pnThemNCC.setLayoutY(42);
        v.pnThemNCC.setPrefSize(30, 30);
        Rectangle clip = new Rectangle(30, 30);
        clip.setArcWidth(10);   // 10px radius → arcWidth = radius * 2
        clip.setArcHeight(10);
        v.pnThemNCC.setClip(clip);
        v.pnThemNCC.getStyleClass().add("btnThem");
        Label lblAddNCC = new Label("✚");
        lblAddNCC.setAlignment(Pos.CENTER);
        lblAddNCC.setId("btnThem");
        lblAddNCC.setPrefSize(30, 30);
        lblAddNCC.setFont(Font.font(13));
        // gắn CSS riêng cho control nếu cần
        lblAddNCC.getStylesheets().add(req("/com/example/pharmacymanagementsystem_qlht/css/ThemDonViTinh.css"));
        v.pnThemNCC.getChildren().add(lblAddNCC);

        // Mã phiếu nhập
        v.txtMaPhieuNhap = new TextField();
        v.txtMaPhieuNhap.setLayoutX(338);
        v.txtMaPhieuNhap.setLayoutY(41);
        v.txtMaPhieuNhap.setPrefSize(150, 30);
        Label lbMaPN = new Label("Mã phiếu nhập:");
        lbMaPN.setLayoutX(339);
        lbMaPN.setLayoutY(17);
        lbMaPN.setFont(Font.font(17));

        // Ngày nhập
        v.txtNgayNhap = new DatePicker();
        v.txtNgayNhap.setLayoutX(513);
        v.txtNgayNhap.setLayoutY(42);
        v.txtNgayNhap.setPrefSize(150, 30);
        Label lbNgay = new Label("Ngày nhập:");
        lbNgay.setLayoutX(513);
        lbNgay.setLayoutY(15);
        lbNgay.setFont(Font.font(17));

        // Thêm thuốc
        Label lbThemThuocTitle = new Label("Thêm thuốc:");
        lbThemThuocTitle.setLayoutX(688);
        lbThemThuocTitle.setLayoutY(15);
        lbThemThuocTitle.setFont(Font.font(17));


        v.txtTimKiemChiTietDonViTinh = new TextField();
        v.txtTimKiemChiTietDonViTinh.setLayoutX(688);
        v.txtTimKiemChiTietDonViTinh.setLayoutY(42);
        v.txtTimKiemChiTietDonViTinh.setPrefSize(316, 30);

        v.listViewChiTietDonViTinh = new ListView<>();
        v.listViewChiTietDonViTinh.setId("listViewTimKiemThuoc");
        v.listViewChiTietDonViTinh.setLayoutX(688);
        v.listViewChiTietDonViTinh.setLayoutY(72);
        v.listViewChiTietDonViTinh.setPrefSize(394, 297);
        v.listViewChiTietDonViTinh.setEditable(true);

        Pane pnThemThuoc = new Pane();
        pnThemThuoc.setLayoutX(1004);
        pnThemThuoc.setLayoutY(42);
        pnThemThuoc.setPrefSize(30, 30);
        Rectangle clip2 = new Rectangle(30, 30);
        clip2.setArcWidth(10);   // 10px radius → arcWidth = radius * 2
        clip2.setArcHeight(10);
        pnThemThuoc.setClip(clip2);
        pnThemThuoc.getStyleClass().add("btnThem");
        v.lblThemThuoc = new Label("✚");
        v.lblThemThuoc.setAlignment(Pos.CENTER);
        v.lblThemThuoc.setId("btnThem");
        v.lblThemThuoc.setPrefSize(30, 30);
        v.lblThemThuoc.setFont(Font.font(13));
        v.lblThemThuoc.getStylesheets().add(req("/com/example/pharmacymanagementsystem_qlht/css/ThemDonViTinh.css"));
        pnThemThuoc.getChildren().add(v.lblThemThuoc);

        // Excel area (chỉ UI)
        v.btnNhapHangExcelPane = new Pane();
        v.btnNhapHangExcelPane.setId("btnExcel");
        v.btnNhapHangExcelPane.setLayoutX(1063);
        v.btnNhapHangExcelPane.setLayoutY(42);
        v.btnNhapHangExcelPane.setPrefSize(247, 30);
        v.btnNhapHangExcelPane.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-background-radius: 5px;" +
                        "-fx-border-radius: 5px;" +      // ← BẮT BUỘC
                        "-fx-border-width: 1;" +
                        "-fx-border-color: #027816;"
        );
        v.btnNhapHangExcelPane.setOnMousePressed(e -> {v.btnNhapHangExcelPane.setStyle("-fx-background-color: #e1e1e1;" +
                "-fx-background-radius: 5px;" +
                "-fx-border-radius: 5px;" +      // ← BẮT BUỘC
                "-fx-border-width: 1;" +
                "-fx-border-color: #027816;");});
        v.btnNhapHangExcelPane.setOnMouseReleased(e -> {v.btnNhapHangExcelPane.setStyle("-fx-background-color: #ffffff;" +
                "-fx-background-radius: 5px;" +
                "-fx-border-radius: 5px;" +      // ← BẮT BUỘC
                "-fx-border-width: 1;" +
                "-fx-border-color: #027816;");});
        Label lbExcel = new Label("Thêm phiếu nhập hàng bằng excel");
        lbExcel.setId("lblExcel");
        lbExcel.setLayoutX(35);
        lbExcel.setLayoutY(6);
        lbExcel.setFont(Font.font(13));
        ImageView ivExcel = imageView("/com/example/pharmacymanagementsystem_qlht/img/logoExcel.svg.png", 22, 40, true);
        ivExcel.setLayoutX(5);
        ivExcel.setLayoutY(5);
        v.btnNhapHangExcelPane.getChildren().addAll(lbExcel, ivExcel);

        // Label tiêu đề nhóm
        Label lbNccTitle = new Label("Nhà cung cấp:");
        lbNccTitle.setLayoutX(29);
        lbNccTitle.setLayoutY(15);
        lbNccTitle.setFont(Font.font(17));

        topBar.getChildren().addAll(
                v.cbxNCC, v.pnThemNCC,
                v.txtMaPhieuNhap, lbMaPN,
                v.txtNgayNhap, lbNgay,
                lbThemThuocTitle, v.txtTimKiemChiTietDonViTinh, pnThemThuoc,
                v.btnNhapHangExcelPane, lbNccTitle,
                v.listViewChiTietDonViTinh
        );

        // --- Bảng nhập thuốc ---
        Pane tablePane = new Pane();
        tablePane.setLayoutY(81);
        tablePane.setPrefSize(1324, 757);

        v.tblNhapThuoc = new TableView<>();
        v.tblNhapThuoc.getStyleClass().add("main-table");
        // Thêm CSS phụ LapHoaDon cho bảng (giống FXML gán trực tiếp)
        v.tblNhapThuoc.getStylesheets().add(req("/com/example/pharmacymanagementsystem_qlht/css/LapHoaDon.css"));
        v.tblNhapThuoc.setLayoutX(28);
        v.tblNhapThuoc.setLayoutY(1);
        v.tblNhapThuoc.setPrefSize(1283, 748);

        v.colSTT = new TableColumn<>("STT"); v.colSTT.setPrefWidth(44.33333206176758); v.colSTT.setStyle("-fx-alignment: CENTER;");
        v.colMaThuoc = new TableColumn<>("Mã thuốc"); v.colMaThuoc.setPrefWidth(128.66666412353516); v.colMaThuoc.setStyle("-fx-alignment: CENTER;");
        v.colTenThuoc = new TableColumn<>("Tên thuốc"); v.colTenThuoc.setPrefWidth(202.0); v.colTenThuoc.setStyle("-fx-alignment: CENTER_LEFT;");
        v.colNSX = new TableColumn<>("NSX"); v.colNSX.setPrefWidth(125.0); v.colNSX.setStyle("-fx-alignment: CENTER;");
        v.colHanSuDung = new TableColumn<>("HSD"); v.colHanSuDung.setPrefWidth(114.0); v.colHanSuDung.setStyle("-fx-alignment: CENTER;");
        v.colLoHang = new TableColumn<>("Lô Hàng"); v.colLoHang.setPrefWidth(111.0); v.colLoHang.setStyle("-fx-alignment: CENTER;");
        v.colDonViNhap = new TableColumn<>("Đơn vị nhập"); v.colDonViNhap.setPrefWidth(75.99993896484375); v.colDonViNhap.setStyle("-fx-alignment: CENTER_LEFT;");
        v.colSoLuong = new TableColumn<>("SL"); v.colSoLuong.setPrefWidth(59.4285888671875); v.colSoLuong.setStyle("-fx-alignment: CENTER;");
        v.colDonGiaNhap = new TableColumn<>("Đơn giá nhập"); v.colDonGiaNhap.setPrefWidth(104.0); v.colDonGiaNhap.setStyle("-fx-alignment: CENTER;");
        v.colChietKhau = new TableColumn<>("CK"); v.colChietKhau.setPrefWidth(66.0); v.colChietKhau.setStyle("-fx-alignment: CENTER;");
        v.colThue = new TableColumn<>("Thuế"); v.colThue.setPrefWidth(56.0); v.colThue.setStyle("-fx-alignment: CENTER;");
        v.colThanhTien = new TableColumn<>("Thành tiền"); v.colThanhTien.setPrefWidth(136.0); v.colThanhTien.setStyle("-fx-alignment: CENTER;");
        v.colXoa = new TableColumn<>("");
        v.colXoa.setPrefWidth(66.0);
        v.colXoa.setStyle("-fx-alignment: CENTER;");

        v.tblNhapThuoc.getColumns().addAll(
                v.colSTT, v.colMaThuoc, v.colTenThuoc, v.colNSX, v.colHanSuDung, v.colLoHang,
                v.colDonViNhap, v.colSoLuong, v.colDonGiaNhap, v.colChietKhau, v.colThue, v.colThanhTien, v.colXoa
        );

        tablePane.getChildren().add(v.tblNhapThuoc);
        leftTop.getChildren().addAll(tablePane, topBar);

        // ===== Cột thông tin bên phải =====
        Pane rightColRoot = new Pane();
        rightColRoot.setLayoutX(1295);
        rightColRoot.setLayoutY(6);
        rightColRoot.setPrefSize(337, 838);

        Pane infoPane = new Pane();
        infoPane.getStyleClass().add("info-pane");
        infoPane.getStylesheets().add(req("/com/example/pharmacymanagementsystem_qlht/css/LapHoaDon.css"));
        infoPane.setLayoutX(43);
        infoPane.setLayoutY(65);
        infoPane.setPrefSize(280, 803);

        Label lbInfoTitle = new Label("Thông tin phiếu");
        lbInfoTitle.setLayoutX(48);
        lbInfoTitle.setPrefSize(186, 45);
        lbInfoTitle.setFont(Font.font("System Bold", 24));

        v.btnLuuPane = new Pane();
        v.btnLuuPane.setId("btnXacNhan");
        v.btnLuuPane.getStyleClass().add("btnXacNhan");
        v.btnLuuPane.setLayoutX(15);
        v.btnLuuPane.setLayoutY(653);
        v.btnLuuPane.setPrefSize(250, 57);
        v.btnLuuPane.setStyle("-fx-background-color: #007bff;" + "-fx-background-radius: 10px;");
        v.btnLuuPane.setOnMousePressed(e-> {v.btnLuuPane.setStyle("-fx-background-color: #0056b3;" + "-fx-background-radius: 10px;");});
        v.btnLuuPane.setOnMouseReleased(e-> {v.btnLuuPane.setStyle("-fx-background-color: #007bff;" + "-fx-background-radius: 10px;");});
        Label lbLapPhieu = new Label("Lập phiếu");
        lbLapPhieu.setId("lblTrang");
        lbLapPhieu.setLayoutX(71);
        lbLapPhieu.setLayoutY(12);
        lbLapPhieu.setFont(Font.font("System Bold", 21));
        lbLapPhieu.setStyle("-fx-text-fill: white;"+"-fx-font-weight: bold;");
        v.btnLuuPane.getChildren().add(lbLapPhieu);

        v.btnHuyPane = new Pane();
        v.btnHuyPane.setId("btnHuy");
        v.btnHuyPane.getStyleClass().add("btnXacNhan");
        v.btnHuyPane.setLayoutX(15);
        v.btnHuyPane.setLayoutY(730);
        v.btnHuyPane.setPrefSize(250, 57);
        v.btnHuyPane.setStyle("-fx-background-color: #dc3545;" + "-fx-background-radius: 10px;");
        v.btnHuyPane.setOnMousePressed(e-> {v.btnHuyPane.setStyle("-fx-background-color: #a71d2a;" + "-fx-background-radius: 10px;");});
        v.btnHuyPane.setOnMouseReleased(e-> {v.btnHuyPane.setStyle("-fx-background-color: #dc3545;" + "-fx-background-radius: 10px;");});
        Label lbHuy = new Label("Hủy");
        lbHuy.setId("lblTrang");
        lbHuy.setLayoutX(102);
        lbHuy.setLayoutY(12);
        lbHuy.setFont(Font.font("System Bold", 21));
        lbHuy.setStyle("-fx-text-fill: white;"+"-fx-font-weight: bold;");
        v.btnHuyPane.getChildren().add(lbHuy);

        v.txtTongGiaNhap = new TextField();
        v.txtTongGiaNhap.setEditable(false);
        v.txtTongGiaNhap.setLayoutX(15);
        v.txtTongGiaNhap.setLayoutY(84);
        v.txtTongGiaNhap.setPrefSize(250, 30);
        Label lbDGN = new Label("Đơn giá nhập:");
        lbDGN.setLayoutX(15);
        lbDGN.setLayoutY(56);
        lbDGN.setFont(Font.font(17));

        v.txtTongTienChietKhau = new TextField();
        v.txtTongTienChietKhau.setEditable(false);
        v.txtTongTienChietKhau.setLayoutX(16);
        v.txtTongTienChietKhau.setLayoutY(152);
        v.txtTongTienChietKhau.setPrefSize(250, 30);
        Label lbTTCK = new Label("Tổng tiền chiết khấu:");
        lbTTCK.setLayoutX(16);
        lbTTCK.setLayoutY(124);
        lbTTCK.setFont(Font.font(17));

        v.txtTongTienThue = new TextField();
        v.txtTongTienThue.setEditable(false);
        v.txtTongTienThue.setLayoutX(16);
        v.txtTongTienThue.setLayoutY(221);
        v.txtTongTienThue.setPrefSize(250, 30);
        Label lbTTT = new Label("Tổng tiền thuế:");
        lbTTT.setLayoutX(18);
        lbTTT.setLayoutY(192);
        lbTTT.setFont(Font.font(17));

        v.txtThanhTien = new TextField();
        v.txtThanhTien.setEditable(false);
        v.txtThanhTien.setLayoutX(16);
        v.txtThanhTien.setLayoutY(289);
        v.txtThanhTien.setPrefSize(250, 30);
        Label lbTT = new Label("Thành tiền:");
        lbTT.setLayoutX(19);
        lbTT.setLayoutY(261);
        lbTT.setFont(Font.font(17));

        v.txtGhiChu = new TextArea();
        v.txtGhiChu.setWrapText(true);
        v.txtGhiChu.setLayoutX(14);
        v.txtGhiChu.setLayoutY(364);
        v.txtGhiChu.setPrefSize(250, 125);
        Label lbGC = new Label("Ghi chú:");
        lbGC.setLayoutX(23);
        lbGC.setLayoutY(335);
        lbGC.setFont(Font.font(17));

        infoPane.getChildren().addAll(
                lbInfoTitle,
                v.btnHuyPane, v.btnLuuPane,
                v.txtTongGiaNhap, lbDGN,
                v.txtTongTienChietKhau, lbTTCK,
                v.txtTongTienThue, lbTTT,
                v.txtThanhTien, lbTT,
                v.txtGhiChu, lbGC
        );
        rightColRoot.getChildren().add(infoPane);

        // ===== List nhà cung cấp gợi ý =====
        v.listViewNhaCungCap = new ListView<>();
        v.listViewNhaCungCap.setId("listViewTimKiemThuoc");
        v.listViewNhaCungCap.setEditable(true);
        v.listViewNhaCungCap.setLayoutX(29);
        v.listViewNhaCungCap.setLayoutY(116);
        v.listViewNhaCungCap.setPrefSize(299, 250);

        // Add all root children
        v.root.getChildren().addAll(lbtitle, leftTop, rightColRoot, v.listViewNhaCungCap);

        return v;
    }

    private void addStyles(Scene scene) {
        scene.getStylesheets().add(req("/com/example/pharmacymanagementsystem_qlht/css/LapPhieuNhapHang.css"));
        scene.getStylesheets().add(req("/com/example/pharmacymanagementsystem_qlht/css/LapHoaDon.css"));
        // ThemDonViTinh.css đã gán thêm trực tiếp tại các label/pane cần dùng
    }

    private static String req(String res) {
        return Objects.requireNonNull(
                LapPhieuNhapHang_GUI.class.getResource(res),
                "Không tìm thấy resource: " + res
        ).toExternalForm();
    }

    private static ImageView imageView(String resource, double fitW, double fitH, boolean preserveRatio) {
        ImageView iv = new ImageView(new Image(req(resource)));
        iv.setFitWidth(fitW);
        iv.setFitHeight(fitH);
        iv.setPreserveRatio(preserveRatio);
        iv.setPickOnBounds(true);
        return iv;
    }

    // ================== Giữ tham chiếu control ==================
    private static class ViewRefs {
        Pane root;

        // bảng
        TableView<CTPN_TSPTL_CHTDVT> tblNhapThuoc;
        TableColumn<CTPN_TSPTL_CHTDVT, String> colSTT, colMaThuoc, colTenThuoc,
                colLoHang, colDonViNhap, colThanhTien, colXoa;
        TableColumn<CTPN_TSPTL_CHTDVT, LocalDate> colHanSuDung, colNSX;
        TableColumn<CTPN_TSPTL_CHTDVT, Integer> colSoLuong;
        TableColumn<CTPN_TSPTL_CHTDVT, Double> colDonGiaNhap;
        TableColumn<CTPN_TSPTL_CHTDVT, Float> colChietKhau, colThue;

        // thanh trên
        ComboBox<String> cbxNCC;
        DatePicker txtNgayNhap;
        TextField txtMaPhieuNhap, txtTimKiemChiTietDonViTinh;
        ListView<ChiTietDonViTinh> listViewChiTietDonViTinh;
        Pane pnThemNCC;
        Label lblThemThuoc;

        // cột phải
        TextField txtTongGiaNhap, txtTongTienChietKhau, txtTongTienThue, txtThanhTien;
        TextArea txtGhiChu;
        Pane btnHuyPane, btnLuuPane, btnNhapHangExcelPane;

        // list gợi ý NCC
        ListView<String> listViewNhaCungCap;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

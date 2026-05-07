package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuNhapHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuNhapHang.TimKiemPhieuNhap_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.PhieuNhap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class TKPhieuNhapHang_GUI {

    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        // 1. Root Pane
        v.root = new Pane();
        v.root.setPrefSize(1646, 895);
        v.root.setStyle("-fx-font-size: 14px;");

        // --- [QUAN TRỌNG 1] Set ID để nhận style nền trắng/bóng đổ từ TimKiemHoaDon.css ---
        v.root.setId("mainPane");

        // --- [QUAN TRỌNG 2] Gắn CSS trực tiếp vào Root Pane (giống Thống kê) ---
        applyStylesToRoot(v.root);

        // ===== Tiêu đề =====
        HBox hbTitle = new HBox();
        hbTitle.setLayoutX(10);
        hbTitle.setLayoutY(14);
        hbTitle.setPrefSize(1613, 53);

        Label lbTitle = new Label("Tìm kiếm phiếu nhập hàng");
        lbTitle.getStyleClass().add("title");
        lbTitle.setPrefSize(456, 53);
        lbTitle.setFont(Font.font(36));

        Region rg = new Region();
        rg.setPrefWidth(19);

        ImageView ivTitle = imageView("/com/example/pharmacymanagementsystem_qlht/img/bill-8854.png", 48, 46, true);

        hbTitle.getChildren().addAll(lbTitle, rg, ivTitle);

        Separator sp = new Separator();
        sp.setLayoutX(10);
        sp.setLayoutY(67);
        sp.setPrefWidth(1633);

        // ===== Thanh tìm kiếm nhanh =====
        v.cbxTimKiem = new ComboBox<>();
        v.cbxTimKiem.setLayoutX(10);
        v.cbxTimKiem.setLayoutY(76);
        v.cbxTimKiem.setPrefSize(179, 40);
        v.cbxTimKiem.setPromptText("Tìm theo");
        v.cbxTimKiem.getStyleClass().add("btntim");

        v.txtTimKiem = new TextField();
        v.txtTimKiem.setLayoutX(200);
        v.txtTimKiem.setLayoutY(78);
        v.txtTimKiem.setPrefSize(379, 40);
        v.txtTimKiem.setPromptText("Tìm kiếm");
        v.txtTimKiem.getStyleClass().add("tftim");

        v.btnReset = new Button();
        v.btnReset.setId("btnReset"); // ID này có thể dùng để style riêng nếu cần
        v.btnReset.setLayoutX(590);
        v.btnReset.setLayoutY(78);
        v.btnReset.setPrefSize(52, 40);
        v.btnReset.getStyleClass().add("btntim");

        ImageView ivRefresh = imageView("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png", 34, 20, true);
        v.btnReset.setGraphic(ivRefresh);

        // ===== Khối chính (VBox) =====
        VBox vbMain = new VBox(8);
        vbMain.setLayoutX(10);
        vbMain.setLayoutY(126);
        vbMain.setPrefWidth(1613);

        // --- TitledPane: Bộ lọc bổ sung ---
        StackPane spFilterWrap = new StackPane();
        spFilterWrap.setPrefWidth(1613);

        v.tpBoLoc = new TitledPane();
        v.tpBoLoc.setText("Bộ lọc bổ sung");
        v.tpBoLoc.setAnimated(false);
        v.tpBoLoc.setExpanded(false);
        v.tpBoLoc.setPrefWidth(1613);

        AnchorPane apFilter = new AnchorPane();
        apFilter.setPrefSize(1611, 30);
        apFilter.setMinHeight(50);

        v.cbxChonNhaCC = new ComboBox<>();
        v.cbxChonNhaCC.setLayoutY(7);
        v.cbxChonNhaCC.setLayoutX(7);
        v.cbxChonNhaCC.setPrefSize(302, 37);
        v.cbxChonNhaCC.setPromptText("Chọn nhà cung cấp");
        v.cbxChonNhaCC.getStyleClass().add("btntim");

        v.cbxChonNhaCC.setMinHeight(37);
        v.cbxChonNhaCC.setMaxHeight(37);

        v.cbxChonNhaCC.setStyle(
                "-fx-padding: 0 10 0 10;" +
                        "-fx-alignment: CENTER_LEFT;"
        );


        v.chonNhanVien = new ComboBox<>();
        v.chonNhanVien.setLayoutX(318);
        v.chonNhanVien.setLayoutY(7);
        v.chonNhanVien.setPrefSize(347, 37);
        v.chonNhanVien.setPromptText("Chọn nhân viên");
        v.chonNhanVien.getStyleClass().add("btntim");

        v.chonNhanVien.setMinHeight(37);
        v.chonNhanVien.setMaxHeight(37);

        v.chonNhanVien.setStyle(
                "-fx-padding: 0 10 0 10;" +
                        "-fx-alignment: CENTER_LEFT;"
        );



        Label lbTrangThai = new Label("Trạng thái nhập hàng:");
        lbTrangThai.setLayoutX(693);
        lbTrangThai.setLayoutY(9);
        lbTrangThai.getStyleClass().add("tftim");
        lbTrangThai.setPrefSize(136, 37);

        v.cboxTrangThai = new CheckBox("Hoàn thành");
        v.cboxTrangThai.setLayoutX(841);
        v.cboxTrangThai.setLayoutY(18);

        Label lbTu = new Label("Từ:");
        lbTu.setLayoutX(991);
        lbTu.setLayoutY(7);
        lbTu.getStyleClass().add("tftim");
        lbTu.setPrefSize(27, 37);

        v.txtNgayNhapMin = new DatePicker();
        v.txtNgayNhapMin.setLayoutX(1027);
        v.txtNgayNhapMin.setLayoutY(7);
        v.txtNgayNhapMin.setPrefSize(136, 37);
        v.txtNgayNhapMin.getStyleClass().add("tftim");

        Label lbDen = new Label("Đến:");
        lbDen.setLayoutX(1181);
        lbDen.setLayoutY(7);
        lbDen.getStyleClass().add("tftim");
        lbDen.setPrefSize(33, 37);

        v.txtNgayNhapMax = new DatePicker();
        v.txtNgayNhapMax.setLayoutX(1214);
        v.txtNgayNhapMax.setLayoutY(7);
        v.txtNgayNhapMax.setPrefSize(136, 37);
        v.txtNgayNhapMax.getStyleClass().add("tftim");

        apFilter.getChildren().addAll(
                v.cbxChonNhaCC, v.chonNhanVien, lbTrangThai, v.cboxTrangThai,
                lbTu, v.txtNgayNhapMin, lbDen, v.txtNgayNhapMax
        );
        v.tpBoLoc.setContent(apFilter);
        spFilterWrap.getChildren().add(v.tpBoLoc);

        // --- Bảng dữ liệu ---
        v.tblPhieuNhap = new TableView<>();
        v.tblPhieuNhap.setPrefSize(1613, 707);
        VBox.setVgrow(v.tblPhieuNhap, Priority.ALWAYS);

        v.colMaPN = new TableColumn<>("Mã phiếu nhập");
        v.colMaPN.setPrefWidth(176);
        v.colMaPN.setStyle("-fx-alignment: CENTER;");

        v.colNgayNhap = new TableColumn<>("Ngày nhập");
        v.colNgayNhap.setPrefWidth(217);
        v.colNgayNhap.setStyle("-fx-alignment: CENTER;");

        v.colNhaCungCap = new TableColumn<>("Nhà cung cấp");
        v.colNhaCungCap.setPrefWidth(259);

        v.colNhanVien = new TableColumn<>("Nhân viên");
        v.colNhanVien.setPrefWidth(236);

        v.colGhiChu = new TableColumn<>("Ghi chú");
        v.colGhiChu.setPrefWidth(355);

        v.colTrangThai = new TableColumn<>("Trạng thái");
        v.colTrangThai.setPrefWidth(259);
        v.colTrangThai.setStyle("-fx-alignment: CENTER;");

        v.colChiTiet = new TableColumn<>("Chi tiết");
        v.colChiTiet.setPrefWidth(93);
        v.colChiTiet.setStyle("-fx-alignment: CENTER;");

        v.tblPhieuNhap.getColumns().addAll(
                v.colMaPN, v.colNgayNhap, v.colNhaCungCap, v.colNhanVien,
                v.colGhiChu, v.colTrangThai, v.colChiTiet
        );

        vbMain.getChildren().addAll(spFilterWrap, v.tblPhieuNhap);

        // ===== Add tất cả vào root =====
        v.root.getChildren().addAll(hbTitle, sp, v.cbxTimKiem, v.txtTimKiem, v.btnReset, vbMain);
        return v;
    }

    // Phương thức này thay thế cho addStyles cũ
    private void applyStylesToRoot(Pane root) {
        // 1. TimKiemHoaDon.css (Chứa style cho #mainPane, .title, .btntim...)
        String css1 = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemThuoc.css"),
                "Không tìm thấy css/TimKiemHoaDon.css"
        ).toExternalForm();
        root.getStylesheets().add(css1);

        // 2. QuanLyThuoc.css (Nút reset có thể dùng style từ đây)
        String css2 = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css"),
                "Không tìm thấy css/QuanLyThuoc.css"
        ).toExternalForm();
        root.getStylesheets().add(css2);
    }

    private static ImageView imageView(String resource, double fitW, double fitH, boolean preserveRatio) {
        ImageView iv = new ImageView(new Image(Objects.requireNonNull(
                TKPhieuNhapHang_GUI.class.getResource(resource),
                "Không tìm thấy ảnh: " + resource
        ).toExternalForm()));
        iv.setFitWidth(fitW);
        iv.setFitHeight(fitH);
        iv.setPreserveRatio(preserveRatio);
        iv.setPickOnBounds(true);
        return iv;
    }

    public void showWithController(Stage stage, TimKiemPhieuNhap_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // ==== GÁN CONTROL về controller ====
        ctrl.cbxTimKiem = v.cbxTimKiem;
        ctrl.txtTimKiem = v.txtTimKiem;
        ctrl.tpBoLoc = v.tpBoLoc;
        ctrl.cbxChonNhaCC = v.cbxChonNhaCC;
        ctrl.chonNhanVien = v.chonNhanVien;
        ctrl.cboxTrangThai = v.cboxTrangThai;
        ctrl.txtNgayNhapMin = v.txtNgayNhapMin;
        ctrl.txtNgayNhapMax = v.txtNgayNhapMax;
        ctrl.tblPhieuNhap = v.tblPhieuNhap;
        ctrl.colMaPN = v.colMaPN;
        ctrl.colNgayNhap = v.colNgayNhap;
        ctrl.colNhaCungCap = v.colNhaCungCap;
        ctrl.colNhanVien = v.colNhanVien;
        ctrl.colGhiChu = v.colGhiChu;
        ctrl.colTrangThai = v.colTrangThai;
        ctrl.colChiTiet = v.colChiTiet;

        v.btnReset.setOnAction(ctrl::btnXoaRong);

        try {
            ctrl.initialize();
        } catch (Exception ignore) {}

        // Scene tự động nhận CSS từ v.root
        Scene scene = new Scene(v.root);
        stage.setTitle("Tìm kiếm phiếu nhập hàng");
        stage.setScene(scene);
    }

    // Class giữ tham chiếu UI
    private static class ViewRefs {
        Pane root;
        ComboBox<String> cbxTimKiem;
        TextField txtTimKiem;
        Button btnReset;
        TitledPane tpBoLoc;
        ComboBox<String> cbxChonNhaCC;
        ComboBox<String> chonNhanVien;
        CheckBox cboxTrangThai;
        DatePicker txtNgayNhapMin, txtNgayNhapMax;
        TableView<PhieuNhap> tblPhieuNhap;
        TableColumn<PhieuNhap, String> colMaPN, colNgayNhap, colNhaCungCap, colNhanVien, colGhiChu, colTrangThai;
        TableColumn<PhieuNhap, String> colChiTiet;
    }
}
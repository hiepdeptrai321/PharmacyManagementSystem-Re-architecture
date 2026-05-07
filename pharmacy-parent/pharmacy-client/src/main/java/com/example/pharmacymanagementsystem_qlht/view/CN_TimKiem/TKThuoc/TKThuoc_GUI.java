package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKThuoc;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKThuoc.TimKiemThuoc_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class TKThuoc_GUI {
    StackPane rootTablePane;

    /** Dùng trong app: tạo UI và GÁN trực tiếp control vào controller (không lookup). */
    public void showWithController(Stage stage, TimKiemThuoc_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // --- CẬP NHẬT: Gắn CSS vào Root Pane ---
        // File này cần 2 file CSS: TimKiemThuoc.css và QuanLyThuoc.css

        // 1. Nạp TimKiemThuoc.css
        String cssPath1 = "/com/example/pharmacymanagementsystem_qlht/css/TimKiemThuoc.css";
        java.net.URL cssUrl1 = getClass().getResource(cssPath1);
        if (cssUrl1 != null) {
            v.root.getStylesheets().add(cssUrl1.toExternalForm());
            System.out.println("Đã gắn CSS (TimKiemThuoc) thành công!");
        } else {
            // Fallback path ngắn
            var shortUrl = getClass().getResource("/css/TimKiemThuoc.css");
            if (shortUrl != null) {
                v.root.getStylesheets().add(shortUrl.toExternalForm());
            } else {
                System.err.println("LỖI: Không tìm thấy file CSS: " + cssPath1);
            }
        }

        // 2. Nạp QuanLyThuoc.css (Cho nút Reset/Làm mới)
        String cssPath2 = "/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css";
        java.net.URL cssUrl2 = getClass().getResource(cssPath2);
        if (cssUrl2 != null) {
            v.root.getStylesheets().add(cssUrl2.toExternalForm());
            System.out.println("Đã gắn CSS (QuanLyThuoc) thành công!");
        } else {
            // Fallback path ngắn
            var shortUrl = getClass().getResource("/css/QuanLyThuoc.css");
            if (shortUrl != null) {
                v.root.getStylesheets().add(shortUrl.toExternalForm());
            } else {
                System.err.println("LỖI: Không tìm thấy file CSS: " + cssPath2);
            }
        }

        // ===== GÁN CONTROL VỀ CONTROLLER (đúng fx:id) =====
        ctrl.txtTimKiem     = v.txtTimKiem;
        ctrl.btnReset       = v.btnReset;
        ctrl.cboTimKiem     = v.cboTimKiem;
        ctrl.cbxLoaiHang    = v.cbxLoaiHang;
        ctrl.cbxXuatSu      = v.cbxXuatSu;
        ctrl.txtHamLuongMin = v.txtHamLuongMin;
        ctrl.txtHamLuongMax = v.txtHamLuongMax;

        ctrl.tbl_Thuoc      = v.tbl_Thuoc;
        ctrl.colMaThuoc     = v.colMaThuoc;
        ctrl.colTenThuoc    = v.colTenThuoc;
        ctrl.colHamLuong    = v.colHamLuong;
        ctrl.colSDK_GPNK    = v.colSDK_GPNK;
        ctrl.colXuatXu      = v.colXuatXu;
        ctrl.colLoaiHang    = v.colLoaiHang;
        ctrl.colViTri       = v.colViTri;
        ctrl.colChiTiet     = v.colChiTiet;
        ctrl.rootTablePane  = rootTablePane;

        // map sự kiện từ FXML: onAction="#btnXoaRong"
        v.btnReset.setOnAction(ctrl::btnXoaRong);

        // Nếu controller có initialize()
        try { ctrl.initialize(); } catch (Exception ignore) {}

        // Tạo Scene từ v.root (đã có CSS)
        Scene scene = new Scene(v.root, 1646, 895);
        scene.setCursor(Cursor.DEFAULT);

        stage.setTitle("Tìm kiếm thuốc");
        stage.setScene(scene);
    }

    // ================== XÂY UI & GIỮ THAM CHIẾU ==================
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        // Root Pane
        v.root = new Pane();
        v.root.setPrefSize(1646, 895);
        v.root.setStyle("-fx-font-size: 14px;");

        // VBox tổng
        VBox vbRoot = new VBox();
        vbRoot.setLayoutX(10);
        vbRoot.setLayoutY(14);
        vbRoot.setPrefSize(1630, 872);

        // ===== HBox Title =====
        HBox hbTitle = new HBox();
        hbTitle.setPrefSize(782, 46);

        Label lbTitle = new Label("Tìm kiếm thuốc");
        lbTitle.setId("lbTimKiem");
        lbTitle.getStyleClass().add("title");
        lbTitle.setPrefSize(285, 53);
        lbTitle.setFont(Font.font(36));

        Label lbIconWrap = new Label();
        ImageView ivTitle = imageView("/com/example/pharmacymanagementsystem_qlht/img/pills-3941.png", 43, 48, true);
        lbIconWrap.setGraphic(ivTitle);

        hbTitle.getChildren().addAll(lbTitle, lbIconWrap);

        Separator sepTop = new Separator();

        // ===== Pane thanh tìm + lọc =====
        Pane paneFilters = new Pane();
        paneFilters.setPrefSize(1610, 62);
        VBox.setMargin(paneFilters, new javafx.geometry.Insets(0, 0, 5, 0));

        v.txtTimKiem = new TextField();
        v.txtTimKiem.setLayoutX(199);
        v.txtTimKiem.setLayoutY(9);
        v.txtTimKiem.setPrefSize(477, 40);
        v.txtTimKiem.setPromptText("Nhập thông tin");

        v.btnReset = new Button();
        v.btnReset.setId("btnReset");
        v.btnReset.setLayoutX(1568);
        v.btnReset.setLayoutY(9);
        v.btnReset.setPrefSize(52, 40);
        // graphic refresh
        ImageView ivRefresh = imageView("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png", 34, 20, true);
        v.btnReset.setGraphic(ivRefresh);

        Label lbLoc = new Label("Lọc:");
        lbLoc.setLayoutX(703);
        lbLoc.setLayoutY(18);
        lbLoc.setFont(Font.font("System Bold", 15));

        v.cboTimKiem = new ComboBox<>();
        v.cboTimKiem.setLayoutY(9);
        v.cboTimKiem.setPrefSize(189, 41);
        v.cboTimKiem.setPromptText("Tiêu chí tìm kiếm");

        v.cbxLoaiHang = new ComboBox<>();
        v.cbxLoaiHang.setLayoutX(741);
        v.cbxLoaiHang.setLayoutY(9);
        v.cbxLoaiHang.setPrefSize(203, 40);
        v.cbxLoaiHang.setPromptText("Chọn loại hàng");

        v.cbxXuatSu = new ComboBox<>();
        v.cbxXuatSu.setLayoutX(951);
        v.cbxXuatSu.setLayoutY(9);
        v.cbxXuatSu.setPrefSize(165, 40);
        v.cbxXuatSu.setPromptText("Chọn xuất xứ");

        Label lbHamLuong = new Label("Hàm lượng (mg):");
        lbHamLuong.setLayoutX(1128);
        lbHamLuong.setLayoutY(18);
        lbHamLuong.setFont(Font.font("System Bold", 15));

        v.txtHamLuongMin = new TextField();
        v.txtHamLuongMin.setLayoutX(1247);
        v.txtHamLuongMin.setLayoutY(9);
        v.txtHamLuongMin.setPrefSize(74, 40);
        v.txtHamLuongMin.setPromptText("Từ");

        v.txtHamLuongMax = new TextField();
        v.txtHamLuongMax.setLayoutX(1336);
        v.txtHamLuongMax.setLayoutY(9);
        v.txtHamLuongMax.setPrefSize(74, 40);
        v.txtHamLuongMax.setPromptText("Đến");

        Label lbDash = new Label("--");
        lbDash.setLayoutX(1322);
        lbDash.setLayoutY(21);

        paneFilters.getChildren().addAll(
                v.txtTimKiem, v.btnReset, lbLoc, v.cboTimKiem, v.cbxLoaiHang, v.cbxXuatSu,
                lbHamLuong, v.txtHamLuongMin,  lbDash, v.txtHamLuongMax
        );

        rootTablePane = new StackPane();
        rootTablePane.setId("rootTablePane");

        // position and size like the old table
        rootTablePane.setLayoutX(12);
        rootTablePane.setLayoutY(106);
        rootTablePane.setPrefSize(1559, 749);

        // ===== Bảng dữ liệu =====
        v.tbl_Thuoc = new TableView<>();
        v.tbl_Thuoc.setPrefSize(1559, 749);

        v.colMaThuoc = new TableColumn<>("Mã thuốc");
        v.colMaThuoc.setPrefWidth(141);
        v.colMaThuoc.setStyle("-fx-alignment: CENTER;");

        v.colTenThuoc = new TableColumn<>("Tên Thuốc");
        v.colTenThuoc.setPrefWidth(247);

        v.colHamLuong = new TableColumn<>("Hàm lượng");
        v.colHamLuong.setPrefWidth(141);
        v.colHamLuong.setStyle("-fx-alignment: CENTER;");

        v.colSDK_GPNK = new TableColumn<>("SDK_GPNK");
        v.colSDK_GPNK.setPrefWidth(223);
        v.colSDK_GPNK.setStyle("-fx-alignment: CENTER;");

        v.colXuatXu = new TableColumn<>("Xuất xứ");
        v.colXuatXu.setPrefWidth(239.33331298828125);
        v.colXuatXu.setStyle("-fx-alignment: CENTER;");

        v.colLoaiHang = new TableColumn<>("Loại hàng");
        v.colLoaiHang.setPrefWidth(287.33331298828125);
        v.colLoaiHang.setStyle("-fx-alignment: CENTER;");

        v.colViTri = new TableColumn<>("Vị trí");
        v.colViTri.setPrefWidth(245);

        v.colChiTiet = new TableColumn<>("");
        v.colChiTiet.setPrefWidth(82);
        v.colChiTiet.setStyle("-fx-alignment: CENTER;");

        v.tbl_Thuoc.getColumns().addAll(
                v.colMaThuoc, v.colTenThuoc, v.colHamLuong, v.colSDK_GPNK,
                v.colXuatXu, v.colLoaiHang, v.colViTri, v.colChiTiet
        );
        rootTablePane.getChildren().add(v.tbl_Thuoc);

        vbRoot.getChildren().addAll(hbTitle, sepTop, paneFilters, rootTablePane);
        v.root.getChildren().add(vbRoot);

        return v;
    }

    private static ImageView imageView(String resource, double fitW, double fitH, boolean preserveRatio) {
        try {
            ImageView iv = new ImageView(new Image(Objects.requireNonNull(
                    TKThuoc_GUI.class.getResource(resource)
            ).toExternalForm()));
            iv.setFitWidth(fitW);
            iv.setFitHeight(fitH);
            iv.setPreserveRatio(preserveRatio);
            iv.setPickOnBounds(true);
            return iv;
        } catch (Exception e) {
            // Trả về ảnh rỗng nếu lỗi để không crash app
            System.err.println("Không tìm thấy ảnh: " + resource);
            return new ImageView();
        }
    }

    // ================== Giữ tham chiếu control ==================
    private static class ViewRefs {
        Pane root;

        TextField txtTimKiem;
        Button btnReset;
        ComboBox<String> cboTimKiem, cbxLoaiHang, cbxXuatSu;
        TextField txtHamLuongMin, txtHamLuongMax;

        TableView<Thuoc_SanPham> tbl_Thuoc;
        TableColumn<Thuoc_SanPham, String> colMaThuoc, colTenThuoc, colHamLuong, colSDK_GPNK, colXuatXu, colLoaiHang, colViTri, colChiTiet;
    }
}
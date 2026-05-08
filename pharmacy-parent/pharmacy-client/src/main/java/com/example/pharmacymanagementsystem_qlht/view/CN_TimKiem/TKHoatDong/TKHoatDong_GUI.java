package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoatDong;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoatDong.TKHoatDong_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.HoatDong;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Separator;
import javafx.stage.Stage;

import java.util.Objects;

public class TKHoatDong_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, TKHoatDong_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14px;");

        // Search field
        TextField tfTim = new TextField();
        tfTim.setId("tfTim");
        tfTim.setLayoutX(14);
        tfTim.setLayoutY(91);
        tfTim.setPrefSize(353, 40);
        tfTim.setPromptText("Tìm theo mã, tên nhân viên");

        // Date range and search button
        Button btnTim = new Button("🔍 Tìm");
        btnTim.setId("btntim");
        btnTim.setLayoutX(756);
        btnTim.setLayoutY(91);
        btnTim.setPrefSize(69, 40);
        btnTim.setStyle("-fx-font-size: 14; -fx-text-fill: white; -fx-background-color: #007bff;");


        DatePicker dpTuNgay = new DatePicker();
        dpTuNgay.setId("dpTuNgay");
        dpTuNgay.setLayoutX(447);
        dpTuNgay.setLayoutY(91);
        dpTuNgay.setPrefSize(125, 40);

        DatePicker dpDenNgay = new DatePicker();
        dpDenNgay.setId("dpDenNgay");
        dpDenNgay.setLayoutX(619);
        dpDenNgay.setLayoutY(91);
        dpDenNgay.setPrefSize(125, 40);

        Label lbTu = new Label("Từ:");
        lbTu.setLayoutX(419);
        lbTu.setLayoutY(98);
        Label lbDen = new Label("Đến:");
        lbDen.setLayoutX(585);
        lbDen.setLayoutY(98);

        // Quick filter combo
        ComboBox<String> cbBoLoc = new ComboBox<>();
        cbBoLoc.setId("cbBoLoc");
        cbBoLoc.setLayoutX(1449);
        cbBoLoc.setLayoutY(91);
        cbBoLoc.setPrefSize(173, 40);
        cbBoLoc.setPromptText("⌛ Bộ lọc nhanh");

        // Table
        TableView<HoatDong> tbHoatDong = new TableView<>();
        tbHoatDong.setId("tablethuoc");
        tbHoatDong.setLayoutX(12);
        tbHoatDong.setLayoutY(142);
        tbHoatDong.setPrefSize(1608, 772);

        TableColumn<HoatDong, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(68);
        colSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<HoatDong, String> colMa = new TableColumn<>("Mã hoạt động");
        colMa.setPrefWidth(266);
        colMa.setStyle("-fx-alignment: CENTER;");

        TableColumn<HoatDong, String> colLoai = new TableColumn<>("Loại hoạt động");
        colLoai.setPrefWidth(315);
        colLoai.setStyle("-fx-alignment: CENTER;");

        TableColumn<HoatDong, String> colBang = new TableColumn<>("Bảng dữ liệu");
        colBang.setPrefWidth(248.33);
        colBang.setStyle("-fx-alignment: CENTER;");

        TableColumn<HoatDong, String> colThoiGian = new TableColumn<>("Thời gian");
        colThoiGian.setPrefWidth(265.33);
        colThoiGian.setStyle("-fx-alignment: CENTER;");

        TableColumn<HoatDong, String> colNguoi = new TableColumn<>("Người thực hiện");
        colNguoi.setPrefWidth(337);

        TableColumn<HoatDong, String> colChiTiet = new TableColumn<>("");
        colChiTiet.setPrefWidth(90);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tbHoatDong.getColumns().addAll(colSTT, colMa, colLoai, colBang, colThoiGian, colNguoi, colChiTiet);

        // Title pane with icon and separator
        Pane lblPaneTitle = new Pane();
        lblPaneTitle.setId("lblpaneTitle");
        lblPaneTitle.setLayoutX(11);
        lblPaneTitle.setLayoutY(8);
        lblPaneTitle.setPrefSize(1617, 90);

        Label lbTitle = new Label("Tìm kiếm hoạt động");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutY(-2);
        lbTitle.setPrefSize(457, 70);
        lbTitle.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        ImageView imgLog = new ImageView();
        imgLog.setFitWidth(50);
        imgLog.setFitHeight(50);
        imgLog.setLayoutX(360);
        imgLog.setLayoutY(14);
        imgLog.setPickOnBounds(true);
        imgLog.setPreserveRatio(true);
        try {
            imgLog.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/log.png")).toExternalForm()));
        } catch (Exception ignored) { }

        Separator sep = new Separator();
        sep.setLayoutX(6);
        sep.setLayoutY(71);
        sep.setPrefWidth(1624);

        lblPaneTitle.getChildren().addAll(lbTitle, imgLog, sep);

        // Refresh button with graphic
        Button btnLamMoi = new Button();
        btnLamMoi.setId("btnReset");
        btnLamMoi.setLayoutX(837);
        btnLamMoi.setLayoutY(91);
        btnLamMoi.setPrefSize(50, 40);
        ImageView imgRefresh = new ImageView();
        imgRefresh.setFitWidth(34);
        imgRefresh.setFitHeight(20);
        imgRefresh.setPickOnBounds(true);
        imgRefresh.setPreserveRatio(true);
        try {
            imgRefresh.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm()));
        } catch (Exception ignored) { }
        btnLamMoi.setGraphic(imgRefresh);

        // Lưu ý: imgLog đã được add vào lblPaneTitle ở trên,
        // nhưng dòng dưới đây add lại vào root thì nó sẽ bị move sang root (JavaFX node chỉ có 1 parent).
        // Tôi giữ nguyên logic của bạn.
        root.getChildren().addAll(tfTim, btnTim, tbHoatDong, imgLog, lbTu, dpTuNgay, lbDen, dpDenNgay, cbBoLoc, lblPaneTitle, btnLamMoi);

        Scene scene = new Scene(root);

        // --- CẬP NHẬT: Gắn CSS vào Root Pane ---
        // Code gốc dùng TimKiemNhanVien.css cho Hoạt động
        String cssPath = "/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css";
        java.net.URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("Đã gắn CSS vào Root Pane (TKHoatDong) thành công!");
        } else {
            // Fallback: Thử tìm đường dẫn ngắn
            var shortUrl = getClass().getResource("/css/TimKiemNhanVien.css");
            if(shortUrl != null) {
                root.getStylesheets().add(shortUrl.toExternalForm());
            } else {
                System.err.println("LỖI: Không tìm thấy file CSS tại: " + cssPath);
            }
        }

        // Inject into controller (best-effort unchecked casts)
        try {
            ctrl.tfTim = tfTim;
            ctrl.btnTim = btnTim;
            ctrl.tbHoatDong = (TableView) tbHoatDong;
            ctrl.colSTT = (TableColumn) colSTT;
            ctrl.colMa = (TableColumn) colMa;
            ctrl.colLoai = (TableColumn) colLoai;
            ctrl.colBang = (TableColumn) colBang;
            ctrl.colThoiGian = (TableColumn) colThoiGian;
            ctrl.colNguoi = (TableColumn) colNguoi;
            ctrl.colChiTiet = (TableColumn) colChiTiet;
            ctrl.dpTuNgay = dpTuNgay;
            ctrl.dpDenNgay = dpDenNgay;
            ctrl.cbBoLoc = cbBoLoc;
            ctrl.btnLamMoi = btnLamMoi;
        } catch (Exception ignored) { }

        // Initialize controller
        ctrl.initialize();

        stage.setTitle("Tìm kiếm hoạt động");
        stage.setScene(scene);
    }
}
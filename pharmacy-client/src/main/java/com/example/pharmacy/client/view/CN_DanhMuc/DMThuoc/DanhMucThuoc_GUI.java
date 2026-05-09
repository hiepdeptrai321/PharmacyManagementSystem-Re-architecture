package com.example.pharmacy.client.view.CN_DanhMuc.DMThuoc;

import com.example.pharmacy.client.controller.CN_DanhMuc.DMThuoc.DanhMucThuoc_Ctrl;
import com.example.pharmacy.common.model.Thuoc_SanPham;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class DanhMucThuoc_GUI extends Application {

    // ===== UI fields (giữ state để inject, KHÔNG dùng lookup) =====
    private TextField tfTimThuoc;
    private Button btnTimThuoc, btnThemThuoc, btnLamMoi, btnNhapExcel;
    private TableView<Thuoc_SanPham> tbl_Thuoc;
    private TableColumn<Thuoc_SanPham, String> colSTT, colMaThuoc, colTenThuoc,
            colHamLuong, colSDK_GPNK, colXuatXu, colLoaiHang, colViTri, colChiTiet;
    StackPane rootTablePane;

    @Override
    public void start(Stage stage) {
        // Chạy độc lập để test UI (không controller)
        AnchorPane root = buildUI();
        Scene scene = new Scene(root, 1646, 895);
        addStyles(scene);
        stage.setTitle("Danh mục thuốc");
        stage.setScene(scene);
        stage.show();
    }

    /** Tạo UI, inject vào controller, gắn handler như FXML cũ và hiển thị. */
    public void showWithController(Stage stage, DanhMucThuoc_Ctrl ctrl) {
        AnchorPane root = buildUI();

        // ==== Inject vào controller (tên field như trong FXML) ====
        ctrl.tfTimThuoc   = tfTimThuoc;
        ctrl.btnTimThuoc  = btnTimThuoc;
        ctrl.btnThemThuoc = btnThemThuoc;
        ctrl.btnLamMoi    = btnLamMoi;
        ctrl.tbl_Thuoc    = tbl_Thuoc;

        ctrl.colSTT      = colSTT;
        ctrl.colMaThuoc  = colMaThuoc;
        ctrl.colTenThuoc = colTenThuoc;
        ctrl.colHamLuong = colHamLuong;
        ctrl.colSDK_GPNK = colSDK_GPNK;
        ctrl.colXuatXu   = colXuatXu;
        ctrl.colLoaiHang = colLoaiHang;
        ctrl.colViTri    = colViTri;
        ctrl.colChiTiet  = colChiTiet;

        ctrl.rootTablePane = rootTablePane;

        // ==== Gắn handler giống FXML ====
        // FXML: onMouseClicked="#timThuoc"
        if (btnTimThuoc != null) btnTimThuoc.setOnMouseClicked(e -> ctrl.timThuoc());
        // FXML: onMouseClicked="#timThuoc" cho nút refresh
        if (btnLamMoi != null)   btnLamMoi.setOnMouseClicked(e -> ctrl.timThuoc());
        // FXML: onAction="#themthuoc"
        if (btnThemThuoc != null) btnThemThuoc.setOnAction(e -> ctrl.themthuoc());
        // FXML: onAction="#btnThemThuocByExcel"
        if (btnNhapExcel != null) btnNhapExcel.setOnAction(e -> ctrl.btnThemThuocByExcel());

        // Cho controller hoàn tất cellFactory, listeners, load dữ liệu...
        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(root, 1646, 895);
        addStyles(scene);
        stage.setTitle("Danh mục thuốc");
        stage.setScene(scene);
    }

    // ================== UI Builder ==================
    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14;");

        // --- Title Pane ---
        Pane lblPaneTitle = new Pane();
        lblPaneTitle.setId("lblpaneTitle");
        lblPaneTitle.setPrefSize(1646, 40);

        Label lbTitle = new Label("Danh mục thuốc");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutX(12);
        lbTitle.setLayoutY(0);



        ImageView icon = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacy/client/img/pills-3941.png")
        ).toExternalForm()));
        icon.setFitHeight(38);
        icon.setFitWidth(36);
        icon.setLayoutX(280);

        icon.setPreserveRatio(true);
        icon.setPickOnBounds(true);

        lblPaneTitle.getChildren().addAll(lbTitle, icon);

        // --- Top controls ---
        tfTimThuoc = new TextField();
        tfTimThuoc.setId("tfTimThuoc");
        tfTimThuoc.setLayoutX(12);
        tfTimThuoc.setLayoutY(48);
        tfTimThuoc.setPrefSize(353, 40);
        tfTimThuoc.setPromptText("Tìm theo mã, tên thuốc");

        btnTimThuoc = new Button("🔍 Tìm");
        btnTimThuoc.setId("btnTimThuoc");
        btnTimThuoc.setLayoutX(375);
        btnTimThuoc.setLayoutY(46);


        btnThemThuoc = new Button("✚Thêm thuốc");
        btnThemThuoc.setId("btnThemThuoc");
        btnThemThuoc.setLayoutX(1430);
        btnThemThuoc.setLayoutY(46.285714285714285);
        btnThemThuoc.setPrefSize(144, 40);

        btnNhapExcel = new Button("📥");
        btnNhapExcel.setId("btnNhapExcel");
        btnNhapExcel.setLayoutX(1584);
        btnNhapExcel.setLayoutY(46.285714285714285);

        btnNhapExcel.setMinWidth(46);

        btnLamMoi = new Button();
        btnLamMoi.setId("btnLamMoi");
        btnLamMoi.setLayoutX(452);
        btnLamMoi.setLayoutY(46);


        ImageView imgRefresh = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacy/client/img/refresh-3104.png")
        ).toExternalForm()));
        imgRefresh.setFitHeight(20);
        imgRefresh.setFitWidth(34);
        imgRefresh.setPreserveRatio(true);
        imgRefresh.setPickOnBounds(true);
        btnLamMoi.setGraphic(imgRefresh);



        icon.setLayoutY(5);
        lbTitle.setPrefSize(300, 36);
        lbTitle.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
        btnTimThuoc.setPrefSize(69, 40);
        btnLamMoi.setPrefSize(45, 40);
        btnThemThuoc.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
        btnTimThuoc.setStyle(" -fx-background-color: #0c81ff; -fx-text-fill: white;");
        btnNhapExcel.setPrefSize(45, 40);
        btnNhapExcel.setStyle(" -fx-background-color: #3264FFCC; -fx-text-fill: white;");


        rootTablePane = new StackPane();
        rootTablePane.setId("rootTablePane");

        // position and size like the old table
        rootTablePane.setLayoutX(12);
        rootTablePane.setLayoutY(106);
        rootTablePane.setPrefSize(1622, 781);

        // --- Table ---
        tbl_Thuoc = new TableView<>();
        tbl_Thuoc.setId("tbl_Thuoc");
        tbl_Thuoc.setLayoutX(12);
        tbl_Thuoc.setLayoutY(95);
        tbl_Thuoc.setPrefSize(1618, 786);

        colSTT = new TableColumn<>("STT");
        colSTT.setId("colSTT");
        colSTT.setPrefWidth(54.2857);
        colSTT.setStyle("-fx-alignment: CENTER;");

        colMaThuoc = new TableColumn<>("Mã thuốc");
        colMaThuoc.setId("colMaThuoc");
        colMaThuoc.setPrefWidth(144);
        colMaThuoc.setStyle("-fx-alignment: CENTER;");

        colTenThuoc = new TableColumn<>("Tên Thuốc");
        colTenThuoc.setId("colTenThuoc");
        colTenThuoc.setPrefWidth(283);

        colHamLuong = new TableColumn<>("Hàm lượng");
        colHamLuong.setId("colHamLuong");
        colHamLuong.setPrefWidth(88);

        colSDK_GPNK = new TableColumn<>("SDK_GPNK");
        colSDK_GPNK.setId("colSDK_GPNK");
        colSDK_GPNK.setPrefWidth(243.6667);
        colSDK_GPNK.setStyle("-fx-alignment: CENTER;");

        colXuatXu = new TableColumn<>("Xuất xứ");
        colXuatXu.setId("colXuatXu");
        colXuatXu.setPrefWidth(102);

        colLoaiHang = new TableColumn<>("Loại hàng");
        colLoaiHang.setId("colLoaiHang");
        colLoaiHang.setPrefWidth(315.3334);

        colViTri = new TableColumn<>("Vị trí");
        colViTri.setId("colViTri");
        colViTri.setPrefWidth(258);
        colViTri.setStyle("-fx-alignment: CENTER;");

        colChiTiet = new TableColumn<>("");
        colChiTiet.setId("colChiTiet");
        colChiTiet.setPrefWidth(111);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tbl_Thuoc.getColumns().addAll(
                colSTT, colMaThuoc, colTenThuoc, colHamLuong, colSDK_GPNK,
                colXuatXu, colLoaiHang, colViTri, colChiTiet
        );

        rootTablePane.getChildren().add(tbl_Thuoc);

        root.getChildren().addAll(
                tfTimThuoc, btnTimThuoc, lblPaneTitle, btnThemThuoc, rootTablePane, btnNhapExcel, btnLamMoi
        );

        return root;
    }

    private void addStyles(Scene scene) {
        var css = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacy/client/css/QuanLyThuoc.css"),
                "Không tìm thấy QuanLyThuoc.css trong resources!"
        ).toExternalForm();
        scene.getStylesheets().add(css);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

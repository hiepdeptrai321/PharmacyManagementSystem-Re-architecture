package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap.DanhMucNhaCungCap_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class DanhMucNhaCungCap_GUI extends Application {

    @Override
    public void start(Stage stage) {
        // Test nhanh không cần controller
        AnchorPane root = buildUI();
        Scene scene = new Scene(root, 1646, 895);
        addStyles(scene);
        stage.setTitle("Danh mục nhà cung cấp");
        stage.setScene(scene);
        stage.show();
    }

    /** Dùng trong app: KHÔNG lookup – tạo control và truyền thẳng cho controller */
    public void showWithController(Stage stage, DanhMucNhaCungCap_Ctrl ctrl) {
        ViewRefs v = buildUIForController(); // có sẵn root + control

        // Gán trực tiếp vào field controller (khớp tên như FXML cũ)
        ctrl.txtTimKiem    = v.txtTimKiem;
        ctrl.btnTim        = v.btnTim;
        ctrl.btnLamMoi     = v.btnLamMoi;
        ctrl.btnThemNCC    = v.btnThemNCC;
        ctrl.tblNhaCungCap = v.tblNhaCungCap;

        ctrl.colSTT     = v.colSTT;
        ctrl.colMaNCC   = v.colMaNCC;
        ctrl.colTenNCC  = v.colTenNCC;
        ctrl.colDiaChi  = v.colDiaChi;
        ctrl.colSDT     = v.colSDT;
        ctrl.colEmail   = v.colEmail;
        ctrl.colGhiChu  = v.colGhiChu;
        ctrl.colChiTiet = v.colChiTiet;

        // Nếu controller có initialize() thì gọi
        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(v.root, 1646, 895);
        addStyles(scene);
        stage.setTitle("Danh mục nhà cung cấp");
        stage.setScene(scene);
    }

    // ================== UI cho test độc lập (không controller) ==================
    private AnchorPane buildUI() {
        ViewRefs v = buildUIForController();
        return v.root;
    }

    // ================== UI cho controller (trả về toàn bộ control) ==================
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        v.root = new AnchorPane();
        v.root.setPrefSize(1646, 895);
        v.root.setStyle("-fx-font-size: 14;");

        // ===== Title Pane =====
        Pane lblPaneTitle = new Pane();
        lblPaneTitle.setId("lblpaneTitle");
        lblPaneTitle.setPrefSize(1200, 40);

        Label lbTitle = new Label("Danh mục nhà cung cấp");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutX(10);
        lbTitle.setLayoutY(2);
        lbTitle.setPrefSize(397, 37);
        lbTitle.setStyle("-fx-font-size: 36;");

        ImageView ivTitle = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/supplier-icon-png-9.jpg")
        ).toExternalForm()));
        ivTitle.setFitHeight(47);
        ivTitle.setFitWidth(54);
        ivTitle.setLayoutX(400);
        ivTitle.setLayoutY(-6);
        ivTitle.setPickOnBounds(true);
        ivTitle.setPreserveRatio(true);

        lblPaneTitle.getChildren().addAll(lbTitle, ivTitle);

        // ===== Controls top =====
        v.txtTimKiem = new TextField();
        v.txtTimKiem.setId("txtTimKiem");
        v.txtTimKiem.setLayoutX(12);
        v.txtTimKiem.setLayoutY(47);
        v.txtTimKiem.setPrefSize(767, 40);
        v.txtTimKiem.setPromptText("Tìm theo mã, tên nhà cung cấp");

        v.btnTim = new Button("🔍 Tìm");
        v.btnTim.setId("btntim");
        v.btnTim.setLayoutX(790);
        v.btnTim.setLayoutY(47);
        v.btnTim.setPrefSize(69, 30);

        v.btnLamMoi = new Button();
        v.btnLamMoi.setId("btnReset");
        v.btnLamMoi.setLayoutX(869);
        v.btnLamMoi.setLayoutY(47);
        v.btnLamMoi.setPrefSize(36, 41);
        ImageView imgRefresh = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")
        ).toExternalForm()));
        imgRefresh.setFitHeight(20);
        imgRefresh.setFitWidth(34);
        imgRefresh.setPreserveRatio(true);
        imgRefresh.setPickOnBounds(true);
        v.btnLamMoi.setGraphic(imgRefresh);

        v.btnThemNCC = new Button("✚Thêm nhà cung cấp");
        v.btnThemNCC.setId("btnthemthuoc");
        v.btnThemNCC.setLayoutX(1360);
        v.btnThemNCC.setLayoutY(47);
        v.btnThemNCC.setPrefSize(174, 40);

        Button btnNhap = new Button("📥");
        btnNhap.setId("btnnhapxuat");
        btnNhap.setLayoutX(1541);
        btnNhap.setLayoutY(47);
        btnNhap.setPrefSize(45, 30);
        btnNhap.setMinWidth(46);

        Button btnXuat = new Button("📤");
        btnXuat.setId("btnnhapxuat");
        btnXuat.setLayoutX(1592);
        btnXuat.setLayoutY(47);
        btnXuat.setPrefSize(43, 40);
        btnXuat.setMinWidth(27);


        ivTitle.setFitHeight(40.0);
        ivTitle.setFitWidth(40.0);
        ivTitle.setLayoutX(385.0);
        ivTitle.setLayoutY(6.0);
        lbTitle.setPrefSize(390, 36);
        lbTitle.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
        v.btnTim.setPrefSize(69, 40);
        v.btnLamMoi.setPrefSize(45, 40);
        v.btnThemNCC.setPrefSize(170, 40);
        v.btnThemNCC.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
        v.btnTim.setStyle(" -fx-background-color: #0c81ff; -fx-text-fill: white;");
        btnNhap.setPrefSize(45, 40);
        btnNhap.setStyle(" -fx-background-color: #3264FFCC; -fx-text-fill: white;");
        btnXuat.setPrefSize(45, 40);
        btnXuat.setStyle(" -fx-background-color: #3264FFCC; -fx-text-fill: white;");

        // ===== Table =====
        v.tblNhaCungCap = new TableView<>();
        v.tblNhaCungCap.setId("tablethuoc");
        v.tblNhaCungCap.setLayoutX(12);
        v.tblNhaCungCap.setLayoutY(98);
        v.tblNhaCungCap.setPrefSize(1625, 789);

        v.colSTT = new TableColumn<>("STT");
        v.colSTT.setId("colSTT");
        v.colSTT.setPrefWidth(48.83);
        v.colSTT.setStyle("-fx-alignment: CENTER;");

        v.colMaNCC = new TableColumn<>("Mã NCC");
        v.colMaNCC.setId("colMaNCC");
        v.colMaNCC.setPrefWidth(154);
        v.colMaNCC.setStyle("-fx-alignment: CENTER;");

        v.colTenNCC = new TableColumn<>("Tên NCC");
        v.colTenNCC.setId("colTenNCC");
        v.colTenNCC.setPrefWidth(320);

        v.colDiaChi = new TableColumn<>("Địa chỉ");
        v.colDiaChi.setId("colDiaChi");
        v.colDiaChi.setPrefWidth(179);

        v.colSDT = new TableColumn<>("Số điện thoại");
        v.colSDT.setId("colSDT");
        v.colSDT.setPrefWidth(265);
        v.colSDT.setStyle("-fx-alignment: CENTER;");

        v.colEmail = new TableColumn<>("Email");
        v.colEmail.setId("colEmail");
        v.colEmail.setPrefWidth(213);

        v.colGhiChu = new TableColumn<>("Ghi chú");
        v.colGhiChu.setId("colGhiChu");
        v.colGhiChu.setPrefWidth(313);

        v.colChiTiet = new TableColumn<>("");
        v.colChiTiet.setId("colChiTiet");
        v.colChiTiet.setPrefWidth(109);
        v.colChiTiet.setStyle("-fx-alignment: CENTER;");

        v.tblNhaCungCap.getColumns().addAll(
                v.colSTT, v.colMaNCC, v.colTenNCC, v.colDiaChi, v.colSDT, v.colEmail, v.colGhiChu, v.colChiTiet
        );

        v.root.getChildren().addAll(
                lblPaneTitle, v.txtTimKiem, v.btnTim, v.btnLamMoi, v.btnThemNCC, btnNhap, btnXuat, v.tblNhaCungCap
        );

        return v;
    }

    private void addStyles(Scene scene) {
        var css = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css"),
                "Không tìm thấy QuanLyThuoc.css"
        ).toExternalForm();
        scene.getStylesheets().add(css);
    }

    /** Gói toàn bộ control để truyền cho controller mà KHÔNG cần lookup */
    private static class ViewRefs {
        AnchorPane root;

        TextField txtTimKiem;
        Button btnTim, btnLamMoi, btnThemNCC;

        TableView<NhaCungCap> tblNhaCungCap;
        TableColumn<NhaCungCap, String> colSTT, colMaNCC, colTenNCC, colDiaChi, colSDT, colEmail, colGhiChu, colChiTiet;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

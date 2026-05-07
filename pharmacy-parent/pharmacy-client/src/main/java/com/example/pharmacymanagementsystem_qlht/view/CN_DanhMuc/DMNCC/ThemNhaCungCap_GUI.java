package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap.ThemNhaCungCap_Ctrl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ThemNhaCungCap_GUI extends Application {

    // ==== UI fields: KHÔNG dùng lookup() ====
    private TextField txtTenNCC;
    private TextField txtDiaChi;
    private TextField txtSDT;
    private TextField txtEmail;
    private TextField txtGPKD_SDK;
    private TextField txtTenCongTy;
    private TextField txtMaSoThue;
    private TextArea  txtGhiChu;

    private Button btnThem;
    private Button btnHuy;

    @Override
    public void start(Stage stage) {
        // Chạy độc lập để test UI
        Pane root = buildUI();
        Scene scene = new Scene(root, 582, 343);
        addStyles(scene);
        stage.setTitle("Thêm nhà cung cấp");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Dùng trong app: tạo UI, inject control vào controller, gắn handler, rồi hiển thị.
     */
    public void showWithController(Stage stage, ThemNhaCungCap_Ctrl ctrl) {
        Pane root = buildUI();

        // ===== Inject vào controller (tên field khớp với controller) =====
        ctrl.txtTenNCC    = txtTenNCC;
        ctrl.txtDiaChi    = txtDiaChi;
        ctrl.txtSDT       = txtSDT;
        ctrl.txtEmail     = txtEmail;
        ctrl.txtGPKD_SDK  = txtGPKD_SDK;
        ctrl.txtTenCongTy = txtTenCongTy;
        ctrl.txtMaSoThue  = txtMaSoThue;
        ctrl.txtGhiChu    = txtGhiChu;

        ctrl.btnThem = btnThem;
        ctrl.btnHuy  = btnHuy;

        // ===== Gắn handler giống FXML cũ =====
        // FXML trước đây: onMouseClicked="#btnThemNCC" và "#btnHuy"
        btnThem.setOnAction(e -> ctrl.btnThemNCC());
        btnHuy.setOnAction(e  -> ctrl.btnHuy());

        // Nếu controller có initialize() để set validator/listener mặc định
        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(root, 582, 343);
        addStyles(scene);
        stage.setTitle("Thêm nhà cung cấp");
        stage.setScene(scene);
        stage.show();
    }

    // ================== UI builder ==================
    private Pane buildUI() {
        Pane root = new Pane();
        root.setPrefSize(582, 343);
        root.setStyle("-fx-font-size: 13;");

        Label lbTitle = new Label("Thêm nhà cung cấp");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(13.5);
        lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(555, 31);
        lbTitle.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 18));

        txtTenNCC = new TextField();
        txtTenNCC.setId("txtTenNCC");
        txtTenNCC.setLayoutX(14);
        txtTenNCC.setLayoutY(66);
        txtTenNCC.setPrefSize(273, 25);

        Label lbTen = new Label("Tên nhà cung cấp:");
        lbTen.setLayoutX(13.5);
        lbTen.setLayoutY(49);

        txtDiaChi = new TextField();
        txtDiaChi.setId("txtDiaChi");
        txtDiaChi.setLayoutX(294);
        txtDiaChi.setLayoutY(66);
        txtDiaChi.setPrefSize(273, 25);

        Label lbDiaChi = new Label("Địa chỉ:");
        lbDiaChi.setLayoutX(294);
        lbDiaChi.setLayoutY(49);

        txtSDT = new TextField();
        txtSDT.setId("txtSDT");
        txtSDT.setLayoutX(14);
        txtSDT.setLayoutY(119);
        txtSDT.setPrefSize(142, 25);

        Label lbSDT = new Label("Số điện thoại:");
        lbSDT.setLayoutX(14);
        lbSDT.setLayoutY(102);

        txtEmail = new TextField();
        txtEmail.setId("txtEmail");
        txtEmail.setLayoutX(163);
        txtEmail.setLayoutY(119);
        txtEmail.setPrefSize(225, 25);

        Label lbEmail = new Label("Email:");
        lbEmail.setLayoutX(163);
        lbEmail.setLayoutY(102);

        txtGPKD_SDK = new TextField();
        txtGPKD_SDK.setId("txtGPKD_SDK");
        txtGPKD_SDK.setLayoutX(394);
        txtGPKD_SDK.setLayoutY(119);
        txtGPKD_SDK.setPrefSize(172, 25);

        Label lbGPKD = new Label("GPKD/SDK:");
        lbGPKD.setLayoutX(394);
        lbGPKD.setLayoutY(102);

        txtTenCongTy = new TextField();
        txtTenCongTy.setId("txtTenCongTy");
        txtTenCongTy.setLayoutX(14);
        txtTenCongTy.setLayoutY(173);
        txtTenCongTy.setPrefSize(273, 25);

        Label lbTenCty = new Label("Tên công ty:");
        lbTenCty.setLayoutX(14);
        lbTenCty.setLayoutY(156);

        txtMaSoThue = new TextField();
        txtMaSoThue.setId("txtMaSoThue");
        txtMaSoThue.setLayoutX(294);
        txtMaSoThue.setLayoutY(173);
        txtMaSoThue.setPrefSize(273, 25);

        Label lbMST = new Label("Mã số thuế:");
        lbMST.setLayoutX(296);
        lbMST.setLayoutY(158);

        txtGhiChu = new TextArea();
        txtGhiChu.setId("txtGhiChu");
        txtGhiChu.setLayoutX(14);
        txtGhiChu.setLayoutY(227);
        txtGhiChu.setPrefSize(555, 69);

        Label lbGhiChu = new Label("Ghi chú:");
        lbGhiChu.setLayoutX(14);
        lbGhiChu.setLayoutY(210);

        btnThem = new Button("Thêm");
        btnThem.setId("btnThem");
        btnThem.setLayoutX(517);
        btnThem.setLayoutY(305);

        btnHuy = new Button("Hủy");
        btnHuy.setId("btnHuy");
        btnHuy.setLayoutX(467);
        btnHuy.setLayoutY(305);

        root.getChildren().addAll(
                lbTitle,
                txtTenNCC, lbTen,
                txtDiaChi, lbDiaChi,
                txtSDT, lbSDT,
                txtEmail, lbEmail,
                txtGPKD_SDK, lbGPKD,
                txtTenCongTy, lbTenCty,
                txtMaSoThue, lbMST,
                txtGhiChu, lbGhiChu,
                btnThem, btnHuy
        );

        return root;
    }

    private void addStyles(Scene scene) {
        var css1 = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css"),
                "Không tìm thấy ThemThuoc.css!"
        ).toExternalForm();
        var css2 = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemNhaCungCap.css"),
                "Không tìm thấy ThemNhaCungCap.css!"
        ).toExternalForm();
        scene.getStylesheets().addAll(css1, css2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

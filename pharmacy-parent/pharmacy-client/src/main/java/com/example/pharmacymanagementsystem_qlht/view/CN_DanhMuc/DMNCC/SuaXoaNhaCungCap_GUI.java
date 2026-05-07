package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap.SuaXoaNhaCungCap_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class SuaXoaNhaCungCap_GUI extends Application {

    // ===== UI fields (KHÔNG dùng lookup) =====
    private TextField txtTen;
    private TextField txtDiaChi;
    private TextField txtSDT;
    private TextField txtEmail;
    private TextField txtGPKD_SDK;
    private TextField txtTenCongTy;
    private TextField txtMaSoThue;
    private TextArea  txtGhiChu;

    private Pane NutHuy;
    private Pane nutThem;
    private Pane nutXoa;

    @Override
    public void start(Stage stage) {
        Pane root = buildUI();
        Scene scene = new Scene(root, 582, 337);
        addStyles(scene);
        stage.setTitle("Xóa, sửa nhà cung cấp");
        stage.setScene(scene);
        stage.show();
    }

    /** Inject controller, gọi initialize(), KHÔNG có sẵn dữ liệu NCC. */
    public void showWithController(Stage stage, SuaXoaNhaCungCap_Ctrl ctrl) {
        Pane root = buildUI();
        injectToController(ctrl);
        // Gắn handler click (thay vì onMouseEntered)
        wireHandlers(ctrl);

        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(root, 582, 337);
        addStyles(scene);
        stage.setTitle("Xóa, sửa nhà cung cấp");
        stage.setScene(scene);
        stage.show();
    }

    /** Inject controller + nạp sẵn dữ liệu NCC, rồi hiển thị. */
    public void showWithController(Stage stage, SuaXoaNhaCungCap_Ctrl ctrl, NhaCungCap ncc) {
        Pane root = buildUI();
        injectToController(ctrl);
        wireHandlers(ctrl);

        try { ctrl.initialize(); } catch (Exception ignore) {}
        // Nạp dữ liệu đối tượng NCC vào form
        try { ctrl.loadData(ncc); } catch (Exception ignore) {}

        Scene scene = new Scene(root, 582, 337);
        addStyles(scene);
        stage.setTitle("Xóa, sửa nhà cung cấp");
        stage.setScene(scene);
        stage.show();
    }

    // ===== Helper: inject UI fields vào controller =====
    private void injectToController(SuaXoaNhaCungCap_Ctrl ctrl) {
        ctrl.txtTen        = txtTen;
        ctrl.txtDiaChi     = txtDiaChi;
        ctrl.txtSDT        = txtSDT;
        ctrl.txtEmail      = txtEmail;
        ctrl.txtGPKD_SDK   = txtGPKD_SDK;
        ctrl.txtTenCongTy  = txtTenCongTy;
        ctrl.txtMaSoThue   = txtMaSoThue;
        ctrl.txtGhiChu     = txtGhiChu;

        ctrl.NutHuy        = NutHuy;
        ctrl.nutThem       = nutThem;
        ctrl.nutXoa        = nutXoa;
    }

    // ===== Helper: gắn hành vi click cho 3 nút Pane =====
    private void wireHandlers(SuaXoaNhaCungCap_Ctrl ctrl) {
        if (NutHuy  != null) NutHuy.setOnMouseClicked(e -> ctrl.btnHuy());
        if (nutThem != null) nutThem.setOnMouseClicked(e -> ctrl.CapNhatNCC());
        if (nutXoa  != null) nutXoa.setOnMouseClicked(e -> ctrl.XoaNCC());
    }

    // ================== UI builder ==================
    private Pane buildUI() {
        Pane root = new Pane();
        root.setPrefSize(582, 337);
        root.setStyle("-fx-font-size: 13;");

        // Tiêu đề
        Label lbTitle = new Label("Xóa, sửa nhà cung cấp");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(13.5);
        lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(555, 31);
        lbTitle.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 18));

        // Hàng 1
        txtTen = new TextField();
        txtTen.setId("txtTen");
        txtTen.setLayoutX(14);
        txtTen.setLayoutY(66);
        txtTen.setPrefSize(273, 25);

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

        // Hàng 2
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

        // Hàng 3
        txtTenCongTy = new TextField();
        txtTenCongTy.setId("txtTenCongTy");
        txtTenCongTy.setLayoutX(14);
        txtTenCongTy.setLayoutY(173);
        txtTenCongTy.setPrefSize(273, 25);

        Label lbTenCTy = new Label("Tên công ty:");
        lbTenCTy.setLayoutX(14);
        lbTenCTy.setLayoutY(156);

        txtMaSoThue = new TextField();
        txtMaSoThue.setId("txtMaSoThue");
        txtMaSoThue.setLayoutX(294);
        txtMaSoThue.setLayoutY(173);
        txtMaSoThue.setPrefSize(273, 25);

        Label lbMST = new Label("Mã số thuế:");
        lbMST.setLayoutX(296);
        lbMST.setLayoutY(158);

        // Hàng 4 (Ghi chú)
        txtGhiChu = new TextArea();
        txtGhiChu.setId("txtGhiChu");
        txtGhiChu.setLayoutX(14);
        txtGhiChu.setLayoutY(227);
        txtGhiChu.setPrefSize(555, 69);

        Label lbGhiChu = new Label("Ghi chú:");
        lbGhiChu.setLayoutX(14);
        lbGhiChu.setLayoutY(210);

        // Nút Hủy
        NutHuy = new Pane();
        NutHuy.setId("NutHuy");
        NutHuy.setLayoutX(378);
        NutHuy.setLayoutY(307);
        NutHuy.setPrefSize(91, 25);
        Label txtDen_Bold = new Label("Hủy");
        txtDen_Bold.setId("txtDen_Bold");
        txtDen_Bold.setLayoutX(34);
        txtDen_Bold.setLayoutY(4);
        NutHuy.getChildren().add(txtDen_Bold);

        // Nút Lưu (Cập nhật)
        nutThem = new Pane();
        nutThem.setId("nutThem");
        nutThem.setLayoutX(476);
        nutThem.setLayoutY(307);
        nutThem.setPrefSize(91, 25);
        Label txtTrang_Bold_Luu = new Label("Lưu");
        txtTrang_Bold_Luu.setId("txtTrang_Bold");
        txtTrang_Bold_Luu.setLayoutX(34);
        txtTrang_Bold_Luu.setLayoutY(4);
        nutThem.getChildren().add(txtTrang_Bold_Luu);

        // Nút Xóa
        nutXoa = new Pane();
        nutXoa.setId("nutXoa");
        nutXoa.setLayoutX(16);
        nutXoa.setLayoutY(307);
        nutXoa.setPrefSize(91, 25);
        Label txtTrang_Bold_Xoa = new Label("Xóa");
        txtTrang_Bold_Xoa.setId("txtTrang_Bold");
        txtTrang_Bold_Xoa.setLayoutX(34);
        txtTrang_Bold_Xoa.setLayoutY(4);
        nutXoa.getChildren().add(txtTrang_Bold_Xoa);

        // Add all
        root.getChildren().addAll(
                lbTitle,
                txtTen, lbTen,
                txtDiaChi, lbDiaChi,
                txtSDT, lbSDT,
                txtEmail, lbEmail,
                txtGPKD_SDK, lbGPKD,
                txtTenCongTy, lbTenCTy,
                txtMaSoThue, lbMST,
                txtGhiChu, lbGhiChu,
                NutHuy, nutThem, nutXoa
        );

        return root;
    }

    private void addStyles(Scene scene) {
        var css1 = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css"),
                "Không tìm thấy ThemThuoc.css trong resources!"
        ).toExternalForm();
        var css2 = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css"),
                "Không tìm thấy SuaXoaNhaCungCap.css trong resources!"
        ).toExternalForm();
        scene.getStylesheets().addAll(css1, css2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

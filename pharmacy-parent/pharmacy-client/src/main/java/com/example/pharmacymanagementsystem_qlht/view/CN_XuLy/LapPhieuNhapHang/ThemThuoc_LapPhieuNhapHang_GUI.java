package com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuNhapHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang.ThemThuoc_LapPhieuNhapHang_Ctrl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ThemThuoc_LapPhieuNhapHang_GUI extends Application {

    @Override
    public void start(Stage stage) {
        AnchorPane root = buildUI(); // test nhanh
        Scene scene = new Scene(root, 955, 405);
        addStyles(scene);
        stage.setTitle("Thêm thuốc");
        stage.setScene(scene);
        stage.show();
    }

    /** Dùng trong app: tạo UI và gắn trực tiếp vào controller (không lookup). */
    public void showWithController(Stage stage, ThemThuoc_LapPhieuNhapHang_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // ==== Gán control cho controller (khớp fx:id trong FXML) ====
        ctrl.tfMaThuoc      = v.tfMaThuoc;
        ctrl.tfTenThuoc     = v.tfTenThuoc;
        ctrl.btnThietLapGia = v.btnThietLapGia;
        ctrl.tbDVT          = v.tbDVT;

        ctrl.colDVT     = v.colDVT;
        ctrl.colKH      = v.colKH;
        ctrl.colHeSo    = v.colHeSo;
        ctrl.colGiaNhap = v.colGiaNhap;
        ctrl.colGiaBan  = v.colGiaBan;
        ctrl.colDVCB    = v.colDVCB;
        ctrl.colXoa     = v.colXoa;

        ctrl.btnLuu = v.btnLuu;
        ctrl.btnHuy = v.btnHuy;

        // ==== Gắn handler như trong FXML ====
        // FXML: onAction="#btnThemThuocClick"
        v.btnThemThuoc.setOnAction(ctrl::btnThemThuocClick);

        // FXML: onAction="#btnThietLapGiaClick"
        v.btnThietLapGia.setOnAction(e -> ctrl.btnThietLapGiaClick());

        // FXML: onMouseClicked="#btnLuuClick" / "#btnHuyClick"
        v.btnLuu.setOnMouseClicked(e -> ctrl.btnLuuClick());
        v.btnHuy.setOnMouseClicked(e-> ctrl.btnHuyClick());

        // Nếu controller có initialize()
        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(v.root, 955, 405);
        addStyles(scene);
        stage.setTitle("Thêm thuốc");
        stage.setScene(scene);
        stage.show();
    }

    // ================== UI cho test độc lập ==================
    private AnchorPane buildUI() {
        return buildUIForController().root;
    }

    // ================== UI cho controller (trả về toàn bộ control) ==================
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        v.root = new AnchorPane();
        v.root.setPrefSize(955, 405);

        // Title
        Label lbTitle = new Label("Thêm thuốc");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(14);
        lbTitle.setLayoutY(20);
        lbTitle.setPrefSize(932, 33);
        lbTitle.setFont(Font.font("System Bold", 18));

        // Khối nội dung chính (mô phỏng y như FXML)
        VBox vboxRoot = new VBox();
        vboxRoot.setLayoutX(14);
        vboxRoot.setLayoutY(22);
        vboxRoot.setPrefSize(932, 336);

        // ========== Pane nhập mã/tên thuốc + nút thêm ==========
        Pane paneTop = new Pane();
        paneTop.setPrefSize(932, 70);

        VBox vboxTop = new VBox();
        vboxTop.setLayoutY(1);
        vboxTop.setPrefSize(932, 90);

        Pane paneFields = new Pane();
        paneFields.setPrefSize(932, 133);

        // TextField + Label: Mã thuốc
        v.tfMaThuoc = new TextField();
        v.tfMaThuoc.setLayoutX(67);
        v.tfMaThuoc.setLayoutY(37);
        v.tfMaThuoc.setPrefSize(187, 32);
        v.tfMaThuoc.setStyle("-fx-max-height: 30;");

        Label lbMa = new Label("Mã thuốc:");
        lbMa.setLayoutX(1);
        lbMa.setLayoutY(36);
        lbMa.setPrefHeight(32);
        lbMa.setFont(Font.font(14));

        // TextField + Label: Tên thuốc
        Label lbTen = new Label("Tên thuốc:");
        lbTen.setLayoutX(269);
        lbTen.setLayoutY(35);
        lbTen.setPrefHeight(32);
        lbTen.setFont(Font.font(14));

        v.tfTenThuoc = new TextField();
        v.tfTenThuoc.setLayoutX(338);
        v.tfTenThuoc.setLayoutY(36);
        v.tfTenThuoc.setPrefSize(356, 32);
        v.tfTenThuoc.setStyle("-fx-max-height: 30;");

        // Button: ✚Thêm Thuốc (id="btnthemdvt", onAction="#btnThemThuocClick")
        v.btnThemThuoc = new Button("✚Thêm Thuốc");
        v.btnThemThuoc.setId("btnthemdvt");
        v.btnThemThuoc.setLayoutX(707);
        v.btnThemThuoc.setLayoutY(34);
        v.btnThemThuoc.setMnemonicParsing(false);

        // ImageView medicine
        ImageView ivMed = new ImageView(new Image(req("/com/example/pharmacymanagementsystem_qlht/img/medicine.png")));
        ivMed.setFitHeight(34);
        ivMed.setFitWidth(34);
        ivMed.setLayoutX(110);
        ivMed.setLayoutY(-6);
        ivMed.setPickOnBounds(true);
        ivMed.setPreserveRatio(true);

        paneFields.getChildren().addAll(v.tfMaThuoc, lbMa, lbTen, v.tfTenThuoc, v.btnThemThuoc, ivMed);

        // spacer Pane (theo FXML)
        Pane spacer = new Pane();
        spacer.setPrefSize(932, 52);
        spacer.setStyle("-fx-min-height: 10;");

        vboxTop.getChildren().addAll(paneFields, spacer);
        paneTop.getChildren().add(vboxTop);

        // Region (theo FXML)
        Region region = new Region();
        region.setPrefSize(932, 43);

        // ========== Pane tiêu đề "Đơn vị tính & giá bán" + nút thiết lập ==========
        Pane pnlThongTin = new Pane();
        pnlThongTin.getStyleClass().add("pnlthongtin");
        pnlThongTin.setPrefSize(932, 46);
        pnlThongTin.setStyle("-fx-max-height: 30;");

        Label lbGroup = new Label("Đơn vị tính & giá bán");
        lbGroup.getStyleClass().add("lbtitle");
        lbGroup.setLayoutY(-38);
        lbGroup.setPrefSize(942, 61);
        lbGroup.setFont(Font.font(18));

        v.btnThietLapGia = new Button("✚Thiết lập giá bán & đơn vị tính");
        v.btnThietLapGia.setId("btngiaban");
        v.btnThietLapGia.setLayoutX(707);
        v.btnThietLapGia.setLayoutY(-27);
        v.btnThietLapGia.setMnemonicParsing(false);
        v.btnThietLapGia.setPrefSize(225, 32);

        pnlThongTin.getChildren().addAll(lbGroup, v.btnThietLapGia);

        // ========== Bảng DVT ==========
        VBox boxTable = new VBox();
        boxTable.setPrefSize(932, 188);

        v.tbDVT = new TableView<>();
        v.tbDVT.setPrefSize(932, 224);

        v.colDVT = new TableColumn<>("Đơn vị tính");
        v.colDVT.setPrefWidth(128);

        v.colKH = new TableColumn<>("Kí hiệu");
        v.colKH.setPrefWidth(103);

        v.colHeSo = new TableColumn<>("Hệ số quy đổi");
        v.colHeSo.setPrefWidth(141);

        v.colGiaNhap = new TableColumn<>("Giá nhập");
        v.colGiaNhap.setPrefWidth(186);

        v.colGiaBan = new TableColumn<>("Giá bán");
        v.colGiaBan.setPrefWidth(189);

        v.colDVCB = new TableColumn<>("Đơn vị cơ bản");
        v.colDVCB.setPrefWidth(106);

        v.colXoa = new TableColumn<>("");
        v.colXoa.setPrefWidth(78);

        v.colDVT.setStyle("-fx-alignment: CENTER;");
        v.colKH.setStyle("-fx-alignment: CENTER;");
        v.colHeSo.setStyle("-fx-alignment: CENTER;");
        v.colGiaNhap.setStyle("-fx-alignment: CENTER;");
        v.colGiaBan.setStyle("-fx-alignment: CENTER;");
        v.colDVCB.setStyle("-fx-alignment: CENTER;");
        v.colXoa.setStyle("-fx-alignment: CENTER;");

        v.tbDVT.getColumns().addAll(v.colDVT, v.colKH, v.colHeSo, v.colGiaNhap, v.colGiaBan, v.colDVCB, v.colXoa);
        boxTable.getChildren().add(v.tbDVT);

        // add vào vbox root
        vboxRoot.getChildren().addAll(paneTop, region, pnlThongTin, boxTable);

        // Nút Lưu/Hủy (styleClass="btnthemhuy"), id theo FXML
        v.btnLuu = new Button("Lưu");
        v.btnLuu.setId("btnluu");
        v.btnLuu.getStyleClass().add("btnthemhuy");
        v.btnLuu.setLayoutX(872);
        v.btnLuu.setLayoutY(361);
        v.btnLuu.setPrefSize(69, 32);
        v.btnLuu.setTextFill(javafx.scene.paint.Color.WHITE);
        v.btnLuu.setFont(Font.font("System Bold", 12));

        v.btnHuy = new Button("Hủy");
        v.btnHuy.setId("btnhuy");
        v.btnHuy.getStyleClass().add("btnthemhuy");
        v.btnHuy.setLayoutX(793);
        v.btnHuy.setLayoutY(361);
        v.btnHuy.setPrefSize(69, 32);
        v.btnHuy.setTextFill(javafx.scene.paint.Color.WHITE);
        v.btnHuy.setFont(Font.font("System Bold", 12));

        // Thêm tất cả vào root
        v.root.getChildren().addAll(lbTitle, vboxRoot, v.btnLuu, v.btnHuy);

        return v;
    }

    private void addStyles(Scene scene) {
        scene.getStylesheets().add(req("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css"));
    }

    private static String req(String res) {
        return Objects.requireNonNull(
                ThemThuoc_LapPhieuNhapHang_GUI.class.getResource(res),
                "Không tìm thấy resource: " + res
        ).toExternalForm();
    }

    /** Gói toàn bộ control để truyền cho controller (không cần lookup). */
    private static class ViewRefs {
        AnchorPane root;

        // fx:id theo FXML
        TextField tfMaThuoc, tfTenThuoc;
        Button btnThemThuoc, btnThietLapGia, btnLuu, btnHuy;

        TableView tbDVT; // dùng raw-type để dễ tương thích controller hiện có
        TableColumn colDVT, colKH, colHeSo, colGiaNhap, colGiaBan, colDVCB, colXoa;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

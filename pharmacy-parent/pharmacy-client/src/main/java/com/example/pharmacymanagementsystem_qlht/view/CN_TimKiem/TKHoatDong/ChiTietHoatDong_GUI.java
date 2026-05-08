// java
package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoatDong;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoatDong.ChiTietHoatDong_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ChiTietHoatDong_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, ChiTietHoatDong_Ctrl ctrl) {
        Pane root = new Pane();
        root.setPrefSize(583, 344);
        root.setStyle("-fx-font-size: 13 px;");

        Label lbTitle = new Label("Thông tin chi tiết hoạt động");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(13.5);
        lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(555, 31);
        lbTitle.setFont(Font.font("System Bold", 18));

        Label lblMaHD = new Label("Mã hoạt động");
        lblMaHD.setLayoutX(13.5);
        lblMaHD.setLayoutY(49);

        TextField tfMaHD = new TextField();
        tfMaHD.setId("tfMaHD");
        tfMaHD.setLayoutX(14);
        tfMaHD.setLayoutY(66);
        tfMaHD.setPrefSize(172, 25);

        Label lblLoai = new Label("Loại hoạt động");
        lblLoai.setLayoutX(208);
        lblLoai.setLayoutY(49);

        TextField tfLoaiHD = new TextField();
        tfLoaiHD.setId("tfLoaiHD");
        tfLoaiHD.setLayoutX(208);
        tfLoaiHD.setLayoutY(66);
        tfLoaiHD.setPrefSize(172, 25);

        Label lblThoiGian = new Label("Thời gian");
        lblThoiGian.setLayoutX(398);
        lblThoiGian.setLayoutY(49);

        TextField tfThoiGian = new TextField();
        tfThoiGian.setId("tfThoiGian");
        tfThoiGian.setLayoutX(398);
        tfThoiGian.setLayoutY(66);
        tfThoiGian.setPrefSize(172, 25);

        Label lblMaNV = new Label("Mã nhân viên");
        lblMaNV.setLayoutX(14);
        lblMaNV.setLayoutY(100);

        TextField tfMaNV = new TextField();
        tfMaNV.setId("tfMaNV");
        tfMaNV.setLayoutX(14);
        tfMaNV.setLayoutY(117);
        tfMaNV.setPrefSize(134, 25);

        Label lblTenNV = new Label("Tên nhân viên");
        lblTenNV.setLayoutX(166);
        lblTenNV.setLayoutY(100);

        TextField tfTenNV = new TextField();
        tfTenNV.setId("tfTenNV");
        tfTenNV.setLayoutX(166);
        tfTenNV.setLayoutY(117);
        tfTenNV.setPrefSize(215, 25);

        Label lblBang = new Label("Bảng dữ liệu");
        lblBang.setLayoutX(397);
        lblBang.setLayoutY(100);

        TextField tfBang = new TextField();
        tfBang.setId("tfBang");
        tfBang.setLayoutX(398);
        tfBang.setLayoutY(117);
        tfBang.setPrefSize(172, 25);

        Label lblNoiDung = new Label("Nội dung");
        lblNoiDung.setLayoutX(17);
        lblNoiDung.setLayoutY(163);

        TextArea tfNoiDung = new TextArea();
        tfNoiDung.setId("tfNoiDung");
        tfNoiDung.setLayoutX(17);
        tfNoiDung.setLayoutY(183);
        tfNoiDung.setPrefSize(555, 111);

        Button btnHuy = new Button();
        btnHuy.setId("btnHuy");
        btnHuy.setLayoutX(506);
        btnHuy.setLayoutY(310);
        btnHuy.setPrefSize(63, 25);
        // create graphic label like in FXML
        Label btnDong = new Label("Đóng");
        btnDong.setFont(Font.font("System Bold", 12));
        btnHuy.setGraphic(btnDong);

        root.getChildren().addAll(
                lbTitle,
                lblMaHD, tfMaHD,
                lblLoai, tfLoaiHD,
                lblThoiGian, tfThoiGian,
                lblMaNV, tfMaNV,
                lblTenNV, tfTenNV,
                lblBang, tfBang,
                lblNoiDung, tfNoiDung,
                btnHuy
        );

        Scene scene = new Scene(root);
        try {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css")).toExternalForm());
        } catch (Exception ignored) { }
        try {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css")).toExternalForm());
        } catch (Exception ignored) { }

        // Inject into controller (best-effort)
        try {
            ctrl.tfMaHD = tfMaHD;
            ctrl.tfLoaiHD = tfLoaiHD;
            ctrl.tfThoiGian = tfThoiGian;
            ctrl.tfMaNV = tfMaNV;
            ctrl.tfTenNV = tfTenNV;
            ctrl.tfBang = tfBang;
            ctrl.tfNoiDung = tfNoiDung;
            ctrl.btnHuy = btnHuy;
        } catch (Exception ignored) { }

        // Initialize controller
        ctrl.initialize();

        stage.setTitle("Thông tin chi tiết hoạt động");
        stage.setScene(scene);
        stage.show();
    }
}

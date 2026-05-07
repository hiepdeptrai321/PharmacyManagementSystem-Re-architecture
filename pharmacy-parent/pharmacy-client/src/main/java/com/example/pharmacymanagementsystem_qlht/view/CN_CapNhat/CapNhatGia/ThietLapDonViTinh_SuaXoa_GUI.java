package com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatGia;

import com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatGia.ThietLapDonViTinh_SuaXoa_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class ThietLapDonViTinh_SuaXoa_GUI {

    public void showWithController(Stage stage, ThietLapDonViTinh_SuaXoa_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(407, 286);
        root.setStyle("-fx-font-size: 13;");
        // Title
        Label lbTitle = new Label("                       Thiết lập đơn vị tính");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutX(21);
        lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(365, 26);
        lbTitle.setStyle("-fx-text-fill: #005711; -fx-font-size: 17; -fx-font-weight: bold;");

        // Left labels VBox
        VBox leftVBox = new VBox();
        leftVBox.setLayoutX(21);
        leftVBox.setLayoutY(52);
        leftVBox.setPrefSize(99, 213);
        leftVBox.setSpacing(31);

        Label lblDVT = new Label("Đơn vị tính");
        lblDVT.setStyle("-fx-text-fill: #005711; -fx-font-size: 15;");
        Label lblGiaNhap = new Label("Giá nhập");
        lblGiaNhap.setStyle("-fx-text-fill: #005711; -fx-font-size: 15;");
        Label lblGiaBan = new Label("Giá bán");
        lblGiaBan.setStyle("-fx-text-fill: #005711; -fx-font-size: 15;");
        Label lblHeSo = new Label("Hệ số quy đổi");
        lblHeSo.setStyle("-fx-text-fill: #005711; -fx-font-size: 15;");

        leftVBox.getChildren().addAll(lblDVT, lblGiaNhap, lblGiaBan, lblHeSo);

        // Right input VBox
        VBox rightVBox = new VBox();
        rightVBox.setLayoutX(124);
        rightVBox.setLayoutY(46);
        rightVBox.setPrefSize(263, 188);
        rightVBox.setSpacing(15);

        // ComboBox + add DVT button
        Pane comboPane = new Pane();
        comboPane.setPrefSize(263, 48);
        ComboBox<String> cbDVT = new ComboBox<>();
        cbDVT.setId("cbDVT");
        cbDVT.setLayoutX(1);
        cbDVT.setPrefSize(209, 36);
        cbDVT.setPromptText("Chọn đơn vị");
        cbDVT.getStyleClass().add("tf");

        Button btnThemDVT = new Button("✚");
        btnThemDVT.setId("btnThemDVT");
        btnThemDVT.setLayoutX(222);
        btnThemDVT.setLayoutY(-1);
        btnThemDVT.setPrefSize(41, 36);
        btnThemDVT.setStyle("-fx-font-weight: bold; -fx-font-size: 12;-fx-text-fill: white; -fx-background-color: green");

        comboPane.getChildren().addAll(cbDVT, btnThemDVT);

        TextField tfGiaNhap = new TextField();
        tfGiaNhap.setId("tfGiaNhap");
        tfGiaNhap.setPrefHeight(42);
        tfGiaNhap.getStyleClass().add("tf");

        TextField tfGiaBan = new TextField();
        tfGiaBan.setId("tfGiaBan");
        tfGiaBan.setPrefHeight(42);
        tfGiaBan.getStyleClass().add("tf");

        Pane heSoPane = new Pane();
        heSoPane.setPrefSize(263, 43);
        TextField tfHeSo = new TextField();
        tfHeSo.setId("tfHeSo");
        tfHeSo.setLayoutX(0);
        tfHeSo.setPrefSize(132, 36);
        tfHeSo.getStyleClass().add("tf");

        CheckBox checkDVCB = new CheckBox("Đơn vị cơ bản");
        checkDVCB.setId("checkDVCB");
        checkDVCB.setLayoutX(145);
        checkDVCB.setLayoutY(8);
        checkDVCB.setStyle("-fx-text-fill: #005711; -fx-font-size: 15;");

        heSoPane.getChildren().addAll(tfHeSo, checkDVCB);

        rightVBox.getChildren().addAll(comboPane, tfGiaNhap, tfGiaBan, heSoPane);

        // ImageView
        ImageView img = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/dvt2.png")).toExternalForm())
        );
        img.setFitHeight(46);
        img.setFitWidth(31);
        img.setLayoutX(93);
        img.setLayoutY(4);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);

        // Buttons bottom
        Button btnThem = new Button("Lưu");
        btnThem.setId("btnThem");
        btnThem.setLayoutX(324);
        btnThem.setLayoutY(248);
        btnThem.setPrefSize(69, 32);
        btnThem.getStyleClass().add("btnthemhuy");
        btnThem.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Button btnHuy = new Button("Hủy");
        btnHuy.setId("btnHuy");
        btnHuy.setLayoutX(247);
        btnHuy.setLayoutY(248);
        btnHuy.setPrefSize(69, 32);
        btnHuy.getStyleClass().add("btnthemhuy");

        Button btnXoa = new Button("Xóa");
        btnXoa.setId("btnXoa");
        btnXoa.setLayoutX(21);
        btnXoa.setLayoutY(248);
        btnXoa.setPrefSize(69, 32);
        btnXoa.getStyleClass().add("btnthemhuy");
        btnXoa.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // Assemble
        root.getChildren().addAll(lbTitle, leftVBox, rightVBox, img, btnThem, btnHuy, btnXoa);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemDonViTinh.css")).toExternalForm());

        // Inject controls into controller
        ctrl.cbDVT = cbDVT;
        ctrl.btnThemDVT = btnThemDVT;
        ctrl.tfGiaNhap = tfGiaNhap;
        ctrl.tfGiaBan = tfGiaBan;
        ctrl.tfHeSo = tfHeSo;
        ctrl.checkDVCB = checkDVCB;
        ctrl.btnThem = btnThem;
        ctrl.btnHuy = btnHuy;
        ctrl.btnXoa = btnXoa;

        // Initialize controller logic
        ctrl.initialize();

        stage.setTitle("Thiết lập đơn vị tính");
        stage.setScene(scene);
        stage.show();
    }
}

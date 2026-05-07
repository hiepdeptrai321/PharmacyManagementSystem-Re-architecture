package com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatKhuyenMai;

import com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Date;
import java.util.Objects;

public class CapNhatKhuyenMai_GUI {

    public void showWithController(Stage stage, CapNhatKhuyenMai_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14;");


        TextField tfTimKM = new TextField();
        tfTimKM.setId("tfTimKM");
        tfTimKM.setLayoutX(12);
        tfTimKM.setLayoutY(59);
        tfTimKM.setPrefHeight(40);
        tfTimKM.setPrefWidth(762);
        tfTimKM.setPromptText("Tìm theo mã, tên khuyến mãi");

        Button btnTimKM = new Button("🔍 Tìm");
        btnTimKM.setId("btntim");
        btnTimKM.setLayoutX(789);
        btnTimKM.setLayoutY(59);
        btnTimKM.setPrefHeight(40);
        btnTimKM.setPrefWidth(76);
        btnTimKM.setStyle("-fx-font-size: 14;");
        btnTimKM.getStyleClass().add("btnTim");
        btnTimKM.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/Chung.css")
        ).toExternalForm());

        TableView<Object> tbKM = new TableView<>();
        tbKM.setId("tablethuoc");
        tbKM.setLayoutX(12);
        tbKM.setLayoutY(104);
        tbKM.setPrefSize(1622, 784);

        StackPane rootTablePane = new StackPane();
        rootTablePane.setId("rootTablePane");

        // position and size like the old table
        rootTablePane.setLayoutX(12);
        rootTablePane.setLayoutY(104);
        rootTablePane.setPrefSize(1622, 784);

        TableColumn<Object, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(68);
        colSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colMaKM = new TableColumn<>("Mã khuyến mãi");
        colMaKM.setPrefWidth(185);
        colMaKM.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colTenKM = new TableColumn<>("Tên khuyến mãi");
        colTenKM.setPrefWidth(381);
        colTenKM.setStyle("-fx-alignment: CENTER_LEFT;");

        TableColumn<Object, String> colLoaiKM = new TableColumn<>("Loại khuyến mãi");
        colLoaiKM.setPrefWidth(172);
        colLoaiKM.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colGiaTri = new TableColumn<>("Giá trị");
        colGiaTri.setPrefWidth(185);
        colGiaTri.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colNBD = new TableColumn<>("Ngày bắt đầu");
        colNBD.setPrefWidth(267);
        colNBD.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colNKT = new TableColumn<>("Ngày kết thúc");
        colNKT.setPrefWidth(241);
        colNKT.setStyle("-fx-alignment: CENTER;");

        TableColumn<Object, String> colChiTiet = new TableColumn<>();
        colChiTiet.setPrefWidth(104);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tbKM.getColumns().addAll(colSTT, colMaKM, colTenKM, colLoaiKM, colGiaTri, colNBD, colNKT, colChiTiet);

        rootTablePane.getChildren().add(tbKM);

        Pane lblPane = new Pane();
        lblPane.setId("lblpaneTitle");
        lblPane.setLayoutY(2);
        lblPane.setPrefHeight(40);
        lblPane.setPrefWidth(1655);

        Label lbTitle = new Label("Cập nhật khuyến mãi");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutX(15);
        lbTitle.setLayoutY(0);
        lbTitle.setPrefSize(1655, 36);

        lbTitle.getStyleClass().add("title");
        lbTitle.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/Chung.css")
        ).toExternalForm());

        ImageView imgCoupon = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/discounts.png")).toExternalForm())
        );
        imgCoupon.setFitHeight(50);
        imgCoupon.setFitWidth(122);
        imgCoupon.setLayoutX(390);
        imgCoupon.setLayoutY(3);
        imgCoupon.setPickOnBounds(true);
        imgCoupon.setPreserveRatio(true);

        lblPane.getChildren().addAll(lbTitle, imgCoupon);

        Button btnReset = new Button();
        btnReset.setId("btnReset");
        btnReset.setLayoutX(874);
        btnReset.setLayoutY(59);
        btnReset.setPrefHeight(40);
        btnReset.setPrefWidth(44);
        ImageView imgRefresh = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm())
        );
        imgRefresh.setFitHeight(20);
        imgRefresh.setFitWidth(34);
        imgRefresh.setPickOnBounds(true);
        imgRefresh.setPreserveRatio(true);
        btnReset.setGraphic(imgRefresh);

        root.getChildren().addAll(tfTimKM, btnTimKM, rootTablePane, lblPane, btnReset);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css")).toExternalForm());

        // Inject controls into controller (unchecked casts for generics)
        ctrl.tfTimKM = tfTimKM;
        ctrl.btnTimKM = btnTimKM;
        ctrl.btnReset = btnReset;
        ctrl.tbKM = (TableView<KhuyenMai>)(TableView<?>) tbKM;
        ctrl.colSTT = (TableColumn<KhuyenMai, String>) (TableColumn<?, ?>) colSTT;
        ctrl.colMaKM = (TableColumn<KhuyenMai, String>) (TableColumn<?, ?>) colMaKM;
        ctrl.colTenKM = (TableColumn<KhuyenMai, String>) (TableColumn<?, ?>) colTenKM;
        ctrl.colLoaiKM = (TableColumn<KhuyenMai, String>) (TableColumn<?, ?>) colLoaiKM;
        ctrl.colGiaTri = (TableColumn<KhuyenMai, Float>) (TableColumn<?, ?>) colGiaTri;
        ctrl.colNBD = (TableColumn<KhuyenMai, String>) (TableColumn<?, ?>) colNBD;
        ctrl.colNKT = (TableColumn<KhuyenMai, String>) (TableColumn<?, ?>) colNKT;
        ctrl.colChiTiet = (TableColumn<KhuyenMai, String>) (TableColumn<?, ?>) colChiTiet;
        ctrl.rootTablePane = rootTablePane;

        // Initialize controller logic
        ctrl.initialize();

        stage.setTitle("Cập nhật khuyến mãi");
        stage.setScene(scene);
    }
}

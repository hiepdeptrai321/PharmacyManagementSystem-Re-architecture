package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKhuyenMai;

import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Date;
import java.util.Objects;

public class DanhMucKhuyenMai_GUI {

    public void showWithController(Stage stage, com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhuyenMai.DanhMucKhuyenMai_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14;");

        // Top title pane
        Pane lblPaneTitle = new Pane();
        lblPaneTitle.setId("lblpaneTitle");
        lblPaneTitle.setLayoutX(13);
        lblPaneTitle.setLayoutY(4);
        lblPaneTitle.setPrefSize(1632, 40);

        Label lbTitle = new Label("Danh mục khuyến mãi");
        lbTitle.setId("lbtitle");
        lbTitle.setLayoutY(2);
        lbTitle.setStyle("-fx-font-size: 36;");

        ImageView imgDiscount = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/discounts.png")).toExternalForm())
        );
        imgDiscount.setFitHeight(35);
        imgDiscount.setFitWidth(38);
        imgDiscount.setLayoutX(400);
        imgDiscount.setLayoutY(4);
        imgDiscount.setPickOnBounds(true);
        imgDiscount.setPreserveRatio(true);

        lblPaneTitle.getChildren().addAll(lbTitle, imgDiscount);

        // Search field and buttons
        TextField tfTimKM = new TextField();
        tfTimKM.setId("tfTimKM");
        tfTimKM.setLayoutX(12);
        tfTimKM.setLayoutY(51);
        tfTimKM.setPrefSize(767, 40);
        tfTimKM.setPromptText("Tìm theo mã, tên khuyến mãi");

        Button btnTim = new Button("🔍 Tìm");
        btnTim.setId("btntim");
        btnTim.setLayoutX(789);
        btnTim.setLayoutY(51);
        btnTim.setPrefSize(69, 30);
        btnTim.getStyleClass().add("btn");

        Button btnthemKM = new Button("✚Thêm khuyến mãi");
        btnthemKM.setId("btnthemKM");
        btnthemKM.setLayoutX(1481);
        btnthemKM.setLayoutY(51);
        btnthemKM.setPrefSize(161, 32);
        btnthemKM.getStyleClass().add("btn");

        Button btnLamMoi = new Button();
        btnLamMoi.setId("btnLamMoi");
        btnLamMoi.setLayoutX(862);
        btnLamMoi.setLayoutY(51);
        btnLamMoi.setPrefSize(44, 41);

        ImageView imgRefresh = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm())
        );
        imgRefresh.setFitHeight(20);
        imgRefresh.setFitWidth(34);
        imgRefresh.setPickOnBounds(true);
        imgRefresh.setPreserveRatio(true);
        btnLamMoi.setGraphic(imgRefresh);



        imgDiscount.setFitHeight(40.0);
        imgDiscount.setFitWidth(40.0);
        imgDiscount.setLayoutX(345.0);
        imgDiscount.setLayoutY(6.0);
        lbTitle.setPrefSize(390, 36);
        lbTitle.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");
        btnTim.setPrefSize(69, 40);
        btnLamMoi.setPrefSize(45, 40);
        btnthemKM.setPrefSize(150, 40);
        btnthemKM.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
        btnTim.setStyle(" -fx-background-color: #0c81ff; -fx-text-fill: white;");

        // Table and columns
        TableView<KhuyenMai> tbKM = new TableView<>();
        tbKM.setId("tbKM");
        tbKM.setLayoutX(12);
        tbKM.setLayoutY(100);
        tbKM.setPrefSize(1622, 789);

        TableColumn<KhuyenMai, String> colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(48.83);
        colSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhuyenMai, String> colMaKM = new TableColumn<>("Mã khuyến mãi");
        colMaKM.setPrefWidth(114.17);
        colMaKM.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhuyenMai, String> colTenKM = new TableColumn<>("Tên khuyến mãi");
        colTenKM.setPrefWidth(348);

        TableColumn<KhuyenMai, String> colLoaiKM = new TableColumn<>("Loại khuyến mãi");
        colLoaiKM.setPrefWidth(253);
        colLoaiKM.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhuyenMai, String> colNBD = new TableColumn<>("Ngày bắt đầu");
        colNBD.setPrefWidth(285);
        colNBD.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhuyenMai, String> colNKT = new TableColumn<>("Ngày kết thúc");
        colNKT.setPrefWidth(220);
        colNKT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhuyenMai, String> colNgayTao = new TableColumn<>("Ngày tạo khuyến mãi");
        colNgayTao.setPrefWidth(227);
        colNgayTao.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhuyenMai, String> colChiTiet = new TableColumn<>();
        colChiTiet.setPrefWidth(108);
        colChiTiet.setStyle("-fx-alignment: CENTER;");

        tbKM.getColumns().addAll(colSTT, colMaKM, colTenKM, colLoaiKM, colNBD, colNKT, colNgayTao, colChiTiet);

        root.getChildren().addAll(lblPaneTitle, tfTimKM, btnTim, btnthemKM, btnLamMoi, tbKM);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css")).toExternalForm());

        // Inject into controller (unchecked casts similar to existing pattern)
        ctrl.btnTim = btnTim;
        ctrl.tfTimKM = tfTimKM;
        ctrl.btnthemKM = btnthemKM;
        ctrl.btnLamMoi = btnLamMoi;
        ctrl.tbKM = tbKM;
        ctrl.colSTT = colSTT;
        ctrl.colMaKM = colMaKM;
        ctrl.colTenKM = colTenKM;
        ctrl.colLoaiKM = colLoaiKM;
        ctrl.colNBD = colNBD;
        ctrl.colNKT = colNKT;
        ctrl.colNgayTao = colNgayTao;
        ctrl.colChiTiet = colChiTiet;

        // Initialize controller logic (listeners, data load ...)
        ctrl.initialize();

        stage.setTitle("Danh mục khuyến mãi");
        stage.setScene(scene);
    }
}

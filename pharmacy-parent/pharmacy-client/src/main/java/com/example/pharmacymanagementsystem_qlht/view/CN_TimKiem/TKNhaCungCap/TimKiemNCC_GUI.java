package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKNhaCungCap.TimKiemNCC_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class TimKiemNCC_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, TimKiemNCC_Ctrl ctrl) {
        Pane mainPane = new Pane();
        mainPane.setId("mainPane");
        mainPane.setPrefHeight(895.0);
        mainPane.setPrefWidth(1646.0);
        // Sửa lỗi cú pháp nhỏ: "14 px" -> "14px"
        mainPane.setStyle("-fx-font-size: 14px;");

        VBox vBoxContainer = new VBox();
        vBoxContainer.setLayoutX(10.0);
        vBoxContainer.setLayoutY(14.0);

        // --- HBox Tiêu đề ---
        HBox hbTitle = new HBox();
        hbTitle.setPrefHeight(62.0);
        hbTitle.setPrefWidth(1170.0);

        Label lbTitle = new Label("Tìm kiếm nhà cung cấp");
        lbTitle.setId("lbTimKiem");
        lbTitle.getStyleClass().add("title");
        lbTitle.setPrefHeight(53.0);
        lbTitle.setPrefWidth(419.0);
        lbTitle.setFont(Font.font(36.0));

        ImageView imgTitle = new ImageView();
        try {
            imgTitle = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/delivery-truck-9146.png")).toExternalForm()));
        } catch (Exception ignored) {}

        imgTitle.setFitHeight(53.0);
        imgTitle.setFitWidth(51.0);
        imgTitle.setPickOnBounds(true);
        imgTitle.setPreserveRatio(true);
        HBox.setMargin(imgTitle, new Insets(5.0, 0, 0, 0));

        hbTitle.getChildren().addAll(lbTitle, new Label(), imgTitle);

        Separator separator = new Separator();
        separator.setPrefWidth(200.0);

        // --- HBox Thanh tìm kiếm ---
        HBox hbSearch = new HBox();
        hbSearch.setPrefHeight(64.0);
        hbSearch.setPrefWidth(1615.0);
        VBox.setMargin(hbSearch, new Insets(0, 0, 10.0, 0));

        ComboBox<String> cboTimKiem = new ComboBox<>();
        cboTimKiem.setId("cboTimKiem");
        cboTimKiem.setPrefHeight(40.0);
        cboTimKiem.setPrefWidth(250.0);
        cboTimKiem.setPromptText("Tìm kiếm theo");
        HBox.setMargin(cboTimKiem, new Insets(10.0, 5.0, 0, 0));

        TextField txtTimKiem = new TextField();
        txtTimKiem.setId("txtTimKiem");
        txtTimKiem.setPrefHeight(41.0);
        txtTimKiem.setPrefWidth(368.0);
        txtTimKiem.setPromptText("Nhập thông tin");
        HBox.setMargin(txtTimKiem, new Insets(10.0, 0, 0, 0));

        Button btnTim = new Button("Tìm");
        btnTim.setId("btnTim");
        btnTim.setDefaultButton(true);
        btnTim.setPrefHeight(40.0);
        btnTim.setPrefWidth(72.0);
        HBox.setMargin(btnTim, new Insets(10.0, 10.0, 0, 10.0));

        ImageView imgSearch = new ImageView();
        try {
            imgSearch = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png")).toExternalForm()));
        } catch (Exception ignored) {}

        imgSearch.setFitHeight(20.0);
        imgSearch.setFitWidth(150.0);
        imgSearch.setPickOnBounds(true);
        imgSearch.setPreserveRatio(true);
        btnTim.setGraphic(imgSearch);

        Button btnLamMoi = new Button();
        btnLamMoi.setId("btnReset");
        btnLamMoi.setPrefHeight(41.0);
        btnLamMoi.setPrefWidth(52.0);
        HBox.setMargin(btnLamMoi, new Insets(10.0, 0, 0, 5.0));

        ImageView imgRefresh = new ImageView();
        try {
            imgRefresh = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")).toExternalForm()));
        } catch (Exception ignored) {}

        imgRefresh.setId("imgReset");
        imgRefresh.setFitHeight(20.0);
        imgRefresh.setFitWidth(34.0);
        imgRefresh.setPickOnBounds(true);
        imgRefresh.setPreserveRatio(true);
        btnLamMoi.setGraphic(imgRefresh);

        hbSearch.getChildren().addAll(cboTimKiem, txtTimKiem, btnTim, btnLamMoi);

        // --- Bảng (trong ScrollPane) ---
        ScrollPane scrollPane = new ScrollPane();
        TableView<NhaCungCap> tbNCC = new TableView<>();
        tbNCC.setId("tbNCC");
        tbNCC.setPrefHeight(738.0);
        tbNCC.setPrefWidth(1613.0);

        TableColumn<NhaCungCap, String> cotSTT = new TableColumn<>("STT");
        cotSTT.setPrefWidth(64.0);
        cotSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<NhaCungCap, String> cotMNCC = new TableColumn<>("Mã nhà cung cấp");
        cotMNCC.setPrefWidth(152.0);
        cotMNCC.setStyle("-fx-alignment: CENTER;");

        TableColumn<NhaCungCap, String> cotTenNCC = new TableColumn<>("Tên nhà cung cấp");
        cotTenNCC.setPrefWidth(331.0);

        TableColumn<NhaCungCap, String> cotEmil = new TableColumn<>("Email");
        cotEmil.setMinWidth(0.0);
        cotEmil.setPrefWidth(280.0);

        TableColumn<NhaCungCap, String> cotSDT = new TableColumn<>("Số điện thoại");
        cotSDT.setMinWidth(6.0);
        cotSDT.setPrefWidth(256.0);
        cotSDT.setStyle("-fx-alignment: CENTER;");

        TableColumn<NhaCungCap, String> cotDiaChi = new TableColumn<>("Địa chỉ");
        cotDiaChi.setMinWidth(0.0);
        cotDiaChi.setPrefWidth(415.0);

        TableColumn<NhaCungCap, String> cotChiTiet = new TableColumn<>();
        cotChiTiet.setMinWidth(0.0);
        cotChiTiet.setPrefWidth(100.0);
        cotChiTiet.setStyle("-fx-alignment: CENTER;");

        tbNCC.getColumns().addAll(cotSTT, cotMNCC, cotTenNCC, cotEmil, cotSDT, cotDiaChi, cotChiTiet);
        scrollPane.setContent(tbNCC);

        // Add all children to VBox
        vBoxContainer.getChildren().addAll(hbTitle, separator, hbSearch, scrollPane);
        mainPane.getChildren().add(vBoxContainer);

        // --- Scene ---
        Scene scene = new Scene(mainPane);

        // --- CẬP NHẬT: Gắn CSS vào Root Pane ---
        // Lưu ý: Code gốc bạn dùng TimKiemNhanVien.css cho TimKiemNCC, tôi giữ nguyên như cũ.
        String cssPath = "/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css";
        java.net.URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl != null) {
            mainPane.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            // Fallback: Thử tìm đường dẫn ngắn
            var shortUrl = getClass().getResource("/css/TimKiemNhanVien.css");
            if(shortUrl != null) {
                mainPane.getStylesheets().add(shortUrl.toExternalForm());
            } else {
                System.err.println("LỖI: Không tìm thấy file CSS tại: " + cssPath);
            }
        }

        // --- Tiêm vào Controller ---
        try {
            ctrl.cboTimKiem = cboTimKiem;
            ctrl.txtTimKiem = txtTimKiem;
            ctrl.btnTim = btnTim;
            ctrl.btnLamMoi = btnLamMoi;
            ctrl.tbNCC = (TableView<NhaCungCap>) tbNCC;
            ctrl.cotSTT = cotSTT;
            ctrl.cotMNCC = cotMNCC;
            ctrl.cotTenNCC = cotTenNCC;
            ctrl.cotEmil = cotEmil;
            ctrl.cotSDT = cotSDT;
            ctrl.cotDiaChi = cotDiaChi;
            ctrl.cotChiTiet = cotChiTiet;
        } catch (Exception ignored) {}

        // --- Khởi chạy Controller ---
        ctrl.initialize();

        stage.setTitle("Tìm kiếm nhà cung cấp");
        stage.setScene(scene);
    }
}
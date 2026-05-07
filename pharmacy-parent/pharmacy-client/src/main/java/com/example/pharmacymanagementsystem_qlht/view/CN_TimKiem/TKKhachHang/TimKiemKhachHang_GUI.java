package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKKhachHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKKhachHang.TimKiemKhachHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
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

public class TimKiemKhachHang_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, TimKiemKhachHang_Ctrl ctrl) {
        Pane mainPane = new Pane();
        mainPane.setId("mainPane");
        mainPane.setPrefHeight(895.0);
        mainPane.setPrefWidth(1646.0);
        // Sửa lỗi dư khoảng trắng: "14 px" -> "14px"
        mainPane.setStyle("-fx-font-size: 14px;");

        VBox vBoxContainer = new VBox();
        vBoxContainer.setLayoutX(10.0);
        vBoxContainer.setLayoutY(14.0);

        // --- HBox Tiêu đề ---
        HBox hbTitle = new HBox();
        hbTitle.setPrefHeight(62.0);
        hbTitle.setPrefWidth(1170.0);

        Label lbTitle = new Label("Tìm kiếm khách hàng");
        lbTitle.setId("lbTimKiem");
        lbTitle.getStyleClass().add("title");
        lbTitle.setPrefHeight(54.0);
        lbTitle.setPrefWidth(381.0);
        lbTitle.setFont(Font.font(36.0));

        ImageView imgTitle = new ImageView();
        try {
            imgTitle = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/free-icon-user-group-296.png")).toExternalForm()));
        } catch (Exception ignored) {}

        imgTitle.setFitHeight(51.0);
        imgTitle.setFitWidth(67.0);
        imgTitle.setPickOnBounds(true);
        imgTitle.setPreserveRatio(true);
        HBox.setMargin(imgTitle, new Insets(5.0, 0, 0, 0));

        hbTitle.getChildren().addAll(lbTitle, new Label(), imgTitle);

        Separator separator = new Separator();
        separator.setPrefWidth(200.0);

        // --- HBox Thanh tìm kiếm ---
        HBox hbSearch = new HBox();
        hbSearch.setPrefHeight(65.0);
        hbSearch.setPrefWidth(1617.0);
        VBox.setMargin(hbSearch, new Insets(0, 0, 10.0, 0));

        ComboBox<String> cboTimKiem = new ComboBox<>();
        cboTimKiem.setId("cboTimKiem");
        cboTimKiem.setPrefHeight(40.0);
        cboTimKiem.setPrefWidth(265.0);
        cboTimKiem.setPromptText("Tìm kiếm theo");
        HBox.setMargin(cboTimKiem, new Insets(10.0, 5.0, 0, 0));

        TextField txtTimKiem = new TextField();
        txtTimKiem.setId("txtTimKiem");
        txtTimKiem.setPrefHeight(44.0);
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
        TableView<KhachHang> tbKhachHang = new TableView<>();
        tbKhachHang.setId("tbKhachHang");
        tbKhachHang.setPrefHeight(723.0);
        tbKhachHang.setPrefWidth(1615.0);

        TableColumn<KhachHang, String> cotSTT = new TableColumn<>("STT");
        cotSTT.setPrefWidth(61.0);
        cotSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotMaKH = new TableColumn<>("Mã KH");
        cotMaKH.setPrefWidth(142.0);
        cotMaKH.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotTenKH = new TableColumn<>("Tên khách hàng");
        cotTenKH.setPrefWidth(273.0);

        TableColumn<KhachHang, String> cotNgaySinh = new TableColumn<>("Ngày sinh");
        cotNgaySinh.setPrefWidth(172.0);
        cotNgaySinh.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotEmail = new TableColumn<>("Email");
        cotEmail.setMinWidth(0.0);
        cotEmail.setPrefWidth(251.0);

        TableColumn<KhachHang, String> cotSDT = new TableColumn<>("SDT");
        cotSDT.setMinWidth(6.0);
        cotSDT.setPrefWidth(240.0);
        cotSDT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotGT = new TableColumn<>("Giới tính");
        cotGT.setPrefWidth(131.33);
        cotGT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotDiaChi = new TableColumn<>("Địa chỉ");
        cotDiaChi.setMinWidth(0.0);
        cotDiaChi.setPrefWidth(345.33);

        tbKhachHang.getColumns().addAll(cotSTT, cotMaKH, cotTenKH, cotNgaySinh, cotEmail, cotSDT, cotGT, cotDiaChi);
        scrollPane.setContent(tbKhachHang);

        // Add all children to VBox
        vBoxContainer.getChildren().addAll(hbTitle, separator, hbSearch, scrollPane);
        mainPane.getChildren().add(vBoxContainer);

        // --- Scene ---
        Scene scene = new Scene(mainPane);

        // --- CẬP NHẬT: Gắn CSS vào Root Pane ---
        // Sử dụng lại CSS TimKiemNhanVien.css như trong code gốc của bạn
        String cssPath = "/com/example/pharmacymanagementsystem_qlht/css/TimKiemHoaDon.css";
        java.net.URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl != null) {
            mainPane.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("Đã gắn CSS vào Root Pane (TimKiemKhachHang) thành công!");
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
        ctrl.mainPane = mainPane;
        ctrl.cboTimKiem = cboTimKiem;
        ctrl.txtTimKiem = txtTimKiem;
        ctrl.btnTim = btnTim;
        ctrl.btnLamMoi = btnLamMoi;

        try {
            ctrl.tbKhachHang = (TableView<KhachHang>) tbKhachHang;
        } catch (Exception e) {}

        ctrl.cotSTT = cotSTT;
        ctrl.cotMaKH = cotMaKH;
        ctrl.cotTenKH = cotTenKH;
        ctrl.cotNgaySinh = cotNgaySinh;
        ctrl.cotEmail = cotEmail;
        ctrl.cotSDT = cotSDT;
        ctrl.cotGT = cotGT;
        ctrl.cotDiaChi = cotDiaChi;

        // --- Khởi chạy Controller ---
        ctrl.initialize();

        stage.setTitle("Tìm kiếm khách hàng");
        stage.setScene(scene);
    }
}
package com.example.pharmacy.client.view.CN_ThongKe;

import com.example.pharmacy.client.controller.CN_ThongKe.ThongKeXNT_Ctrl;
import com.example.pharmacy.common.model.ThongKeTonKhoDto;
import com.example.pharmacy.common.model.ThuocHetHanDto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ThongKeXNT_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, ThongKeXNT_Ctrl ctrl) {

        // --- Tạo các thành phần giao diện ---

        // 1. Bảng Tồn kho
        TableView<ThongKeTonKhoDto> tbTon = new TableView<>();
        TableColumn<ThongKeTonKhoDto, Integer> ColTDK = new TableColumn<>("Tồn đầu kỳ");
        TableColumn<ThongKeTonKhoDto, String> colDVT = new TableColumn<>("ĐVT");
        TableColumn<ThongKeTonKhoDto, String> colMaThuoc = new TableColumn<>("Mã thuốc");
        TableColumn<ThongKeTonKhoDto, Integer> colNTK = new TableColumn<>("Nhập trong kỳ");
        TableColumn<ThongKeTonKhoDto, String> colTenThuoc = new TableColumn<>("Tên thuốc");
        TableColumn<ThongKeTonKhoDto, Integer> colTCK = new TableColumn<>("Tồn cuối kỳ");
        TableColumn<ThongKeTonKhoDto, Integer> colXTK = new TableColumn<>("Xuất trong kỳ");

        // 2. Bảng Hết hạn
        TableView<ThuocHetHanDto> tbHetHan = new TableView<>();
        TableColumn<ThuocHetHanDto, String> colMaThuocHH = new TableColumn<>("Mã Thuốc");
        TableColumn<ThuocHetHanDto, LocalDate> colNgayHH = new TableColumn<>("Ngày hết hạn");
        TableColumn<ThuocHetHanDto, Integer> colSoLuong = new TableColumn<>("Số Lượng");
        TableColumn<ThuocHetHanDto, String> cotTenThuocHH = new TableColumn<>("Tên Thuốc");

        // Panel bên trái (Giữ nguyên)
        Button btnXuat = new Button("Xuất File 💾");
        ComboBox<String> cboThoiGian = new ComboBox<>();
        ComboBox<String> cboXuat = new ComboBox<>();
        DatePicker dateDen = new DatePicker();
        DatePicker dateTu = new DatePicker();
        TextField txtTimNhanh = new TextField();
        Label lblTu = new Label("Từ:");
        Label lblDen = new Label("Đến:");

        // --- CẤU HÌNH BẢNG (PHẦN SỬA ĐỔI) ---

        // BẢNG 1: TỒN KHO
        tbTon.getColumns().addAll(colMaThuoc, colTenThuoc, colDVT, ColTDK, colNTK, colXTK, colTCK);

        // [SỬA 1] Giảm width xuống 1190 (cũ là 1223) để hở lề phải khoảng 30px
        tbTon.setPrefWidth(1190.0);

        tbTon.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tbTon.setPrefHeight(538.0);

        // BẢNG 2: HẾT HẠN
        tbHetHan.getColumns().addAll(colMaThuocHH, cotTenThuocHH, colSoLuong, colNgayHH);

        // [SỬA 3] Chỉnh width thành 1190 cho bằng bảng trên (cũ là 1161 bị lệch)
        tbHetHan.setPrefWidth(1190.0);

        // [SỬA 4] Thêm chính sách co giãn cột
        tbHetHan.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tbHetHan.setPrefHeight(200.0);


        // --- Dựng Panel bên trái (Giữ nguyên kích thước cũ) ---
        VBox leftVBox = new VBox();
        leftVBox.setPrefHeight(863.0);
        leftVBox.setPrefWidth(394.0); // Giữ nguyên kích thước panel trái
        leftVBox.getStyleClass().add("vbox");

        // ... (Code tạo nội dung panel trái giữ nguyên y hệt của bạn) ...
        Label titleLabel = new Label("Thống kê XNT");
        titleLabel.getStyleClass().add("title");
        ImageView titleIcon = createIcon("/com/example/pharmacy/client/img/boxes-11430.png", 40, 44);
        HBox titleHBox = new HBox(titleLabel, new Label("", titleIcon));
        titleHBox.setAlignment(Pos.CENTER_LEFT);
        Separator separator = new Separator();
        separator.setPrefWidth(200.0);
        Label searchLabel = new Label("Tìm nhanh");
        searchLabel.getStyleClass().add("header-label");
        txtTimNhanh.setPromptText("Nhập mã, tên thuốc...");
        txtTimNhanh.setPrefHeight(40.0);
        Label timeLabel = new Label("Thời gian");
        timeLabel.getStyleClass().add("header-label");
        VBox.setMargin(timeLabel, new Insets(10.0, 0, 0, 0));
        cboThoiGian.setPrefHeight(49.0); cboThoiGian.setPrefWidth(378.0);
        lblTu.getStyleClass().add("header-label");
        dateTu.setPrefHeight(45.0); dateTu.setPrefWidth(377.0);
        lblDen.getStyleClass().add("header-label");
        dateDen.setPrefHeight(45.0); dateDen.setPrefWidth(376.0);
        Label exportLabel = new Label("Xuất file");
        exportLabel.getStyleClass().add("header-label");
        VBox.setMargin(exportLabel, new Insets(10.0, 0, 0, 0));
        cboXuat.setPrefHeight(49.0); cboXuat.setPrefWidth(375.0);
        cboXuat.setPromptText("Chọn định dạng");
        btnXuat.setMnemonicParsing(false);
        btnXuat.setPrefHeight(62.0); btnXuat.setPrefWidth(438.0);
        VBox.setMargin(btnXuat, new Insets(10.0, 0, 0, 0));

        leftVBox.getChildren().addAll(titleHBox, separator, searchLabel, txtTimNhanh,
                timeLabel, cboThoiGian, lblTu, dateTu, lblDen, dateDen, exportLabel, cboXuat, btnXuat);

        // --- Dựng VBox bên phải ---
        VBox rightVBox = new VBox();
        rightVBox.getStyleClass().add("vbox");

        Label warningLabel = new Label("Những sản phẩm đã, sắp hết hạn ⚠");
        warningLabel.getStyleClass().add("sub-title");
        VBox.setMargin(warningLabel, new Insets(10.0, 0, 5.0, 0)); // Bỏ margin trái 10px để thẳng hàng

        rightVBox.getChildren().addAll(tbTon, warningLabel, tbHetHan);

        // --- Dựng HBox gốc ---
        HBox mainHBox = new HBox(15, leftVBox, rightVBox);
        mainHBox.setLayoutX(14.0);
        mainHBox.setLayoutY(14.0);

        // --- Dựng Pane gốc (Giữ nguyên kích thước to 1646) ---
        Pane root = new Pane();
        root.setPrefHeight(895.0);
        root.setPrefWidth(1646.0); // Giữ nguyên kích thước tổng
        root.setId("mainPane");
        root.getChildren().add(mainHBox);

        // --- Tiêm vào Controller ---
        ctrl.tbTon = tbTon;
        ctrl.ColTDK = ColTDK;
        ctrl.colDVT = colDVT;
        ctrl.colMaThuoc = colMaThuoc;
        ctrl.colNTK = colNTK;
        ctrl.colTenThuoc = colTenThuoc;
        ctrl.colTCK = colTCK;
        ctrl.colXTK = colXTK;
        ctrl.tbHetHan = tbHetHan;
        ctrl.colMaThuocHH = colMaThuocHH;
        ctrl.colNgayHH = colNgayHH;
        ctrl.colSoLuong = colSoLuong;
        ctrl.cotTenThuocHH = cotTenThuocHH;
        ctrl.btnXuat = btnXuat;
        ctrl.cboThoiGian = cboThoiGian;
        ctrl.cboXuat = cboXuat;
        ctrl.dateDen = dateDen;
        ctrl.dateTu = dateTu;
        ctrl.txtTimNhanh = txtTimNhanh;
        ctrl.lblTu = lblTu;
        ctrl.lblDen = lblDen;

        // --- Tạo Scene ---
        Scene scene = new Scene(root);
        String cssPath = "/com/example/pharmacy/client/css/ThongKeBanHangDto.css";
        java.net.URL cssUrl = getClass().getResource(cssPath);
        if (cssUrl != null) root.getStylesheets().add(cssUrl.toExternalForm());

        stage.setScene(scene);
        ctrl.initialize();
        stage.setTitle("Báo cáo Xuất - Nhập - Tồn");
    }

    private ImageView createIcon(String path, double height, double width) {
        try {
            Image image = new Image(getClass().getResourceAsStream(path));
            ImageView icon = new ImageView(image);
            icon.setFitHeight(height);
            icon.setFitWidth(width);
            icon.setPreserveRatio(true);
            return icon;
        } catch (Exception e) {
            return new ImageView();
        }
    }
}
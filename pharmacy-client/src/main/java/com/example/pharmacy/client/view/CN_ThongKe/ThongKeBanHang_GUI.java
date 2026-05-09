package com.example.pharmacy.client.view.CN_ThongKe;

import com.example.pharmacy.client.controller.CN_ThongKe.ThongKeBanHang_Ctrl;
import com.example.pharmacy.common.model.HoaDonDisplayDto;
import com.example.pharmacy.common.model.ThongKeBanHangDto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ThongKeBanHang_GUI {

    @SuppressWarnings("unchecked")
    public void showWithController(Stage stage, ThongKeBanHang_Ctrl ctrl) {
        // --- 1. Tạo các thành phần giao diện ---
        Button btnBang = new Button("Bảng");
        Button btnBieuDo = new Button("Biểu đồ");
        Button btnXuat = new Button("Xuất File 💾");

        ComboBox<String> cboThoiGian = new ComboBox<>();
        ComboBox<String> cboXuatfile = new ComboBox<>();
        DatePicker dateTu = new DatePicker();
        DatePicker dateDen = new DatePicker();

        Label lblTu = new Label("Từ:");
        Label lblDen = new Label("Đến:");

        // Bảng Doanh Thu
        TableView<ThongKeBanHangDto> tableDoanhThu = new TableView<>();
        TableColumn<ThongKeBanHangDto, String> cotTG = new TableColumn<>("Thời gian");
        TableColumn<ThongKeBanHangDto, Integer> cotSLHoaDon = new TableColumn<>("Số lượng HĐ");
        TableColumn<ThongKeBanHangDto, Double> cotTongGT = new TableColumn<>("Tổng giá trị");
        TableColumn<ThongKeBanHangDto, Double> cotGG = new TableColumn<>("Giảm giá");
        TableColumn<ThongKeBanHangDto, Integer> cotDT = new TableColumn<>("Số lượng đơn trả");
        TableColumn<ThongKeBanHangDto, Double> cotGTDonTra = new TableColumn<>("Giá trị đơn trả");
        TableColumn<ThongKeBanHangDto, Double> cotDoanhThu = new TableColumn<>("Doanh thu");

        // Biểu đồ
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chartDoanhThu = new BarChart<>(xAxis, yAxis);

        // Bảng Hóa Đơn
        TableView<HoaDonDisplayDto> tableHoaDon = new TableView<>();
        TableColumn<HoaDonDisplayDto, String> cotMaHoaDon = new TableColumn<>("Mã Hóa Đơn");
        TableColumn<HoaDonDisplayDto, LocalDate> cotNgayLap = new TableColumn<>("Ngày Lập");
        TableColumn<HoaDonDisplayDto, String> cotMaKhachHang = new TableColumn<>("Mã Khách Hàng");
        TableColumn<HoaDonDisplayDto, String> cotMaNhanVien = new TableColumn<>("Mã Nhân Viên");
        TableColumn<HoaDonDisplayDto, Double> cotTongTien = new TableColumn<>("Tổng Tiền");

        // --- 2. Cấu hình Bảng Doanh Thu (Không setStyle cứng nữa) ---
        cotTG.setPrefWidth(147.33);
        cotSLHoaDon.setPrefWidth(170.66);
        cotTongGT.setPrefWidth(129.66);
        cotGG.setPrefWidth(142.99);
        cotDT.setPrefWidth(150.66);
        cotGTDonTra.setPrefWidth(193.33);
        cotDoanhThu.setPrefWidth(229.66);
        tableDoanhThu.getColumns().addAll(cotTG, cotSLHoaDon, cotTongGT, cotGG, cotDT, cotGTDonTra, cotDoanhThu);
        tableDoanhThu.setPrefHeight(510.0);
        tableDoanhThu.setPrefWidth(1161.0);

        // --- 3. Cấu hình Biểu đồ ---
        chartDoanhThu.setPrefHeight(510.0);
        chartDoanhThu.setPrefWidth(1161.0);
        chartDoanhThu.setVisible(false);
        xAxis.setSide(javafx.geometry.Side.BOTTOM);

        // --- 4. Cấu hình Bảng Hóa Đơn ---
        cotMaHoaDon.setPrefWidth(200.0);
        cotNgayLap.setPrefWidth(200.0);
        cotMaKhachHang.setPrefWidth(200.0);
        cotMaNhanVien.setPrefWidth(200.0);
        cotTongTien.setPrefWidth(359.0);
        tableHoaDon.getColumns().addAll(cotMaHoaDon, cotNgayLap, cotMaKhachHang, cotMaNhanVien, cotTongTien);
        tableHoaDon.setPrefHeight(200.0);
        tableHoaDon.setPrefWidth(1161.0);

        // --- 5. Dựng VBox bên trái (Panel điều khiển) ---
        VBox leftVBox = new VBox();
        leftVBox.setPrefHeight(1126.0);
        leftVBox.setPrefWidth(449.0);

        // QUAN TRỌNG: Thêm class vbox để nhận CSS khung trắng, bóng đổ
        leftVBox.getStyleClass().add("vbox");

        Label titleLabel = new Label("Báo cáo bán hàng ");
        titleLabel.getStyleClass().add("title"); // Thêm class title

        ImageView titleIcon = createIcon("/com/example/pharmacy/client/img/bar-chart.png", 33, 40);
        HBox titleHBox = new HBox(titleLabel, new Label("", titleIcon));
        titleHBox.setAlignment(Pos.CENTER_LEFT);

        Separator separator = new Separator();
        separator.setPrefWidth(200.0);

        Label displayLabel = new Label("Kiểu hiển thị");
        displayLabel.getStyleClass().add("header-label");

        // Cấu hình Nút
        btnBang.setId("btnBang"); // ID để nhận màu xanh
        btnBang.setPrefHeight(61.0);
        btnBang.setPrefWidth(103.0);
        btnBang.setGraphic(createIcon("/com/example/pharmacy/client/img/table.png", 40, 38));
        HBox.setMargin(btnBang, new Insets(0, 0, 0, 30.0));

        btnBieuDo.setId("btnBieuDo"); // ID để nhận màu cam
        btnBieuDo.setPrefHeight(62.0);
        btnBieuDo.setPrefWidth(120.0);
        btnBieuDo.setGraphic(createIcon("/com/example/pharmacy/client/img/improvement.png", 35, 34));
        HBox.setMargin(btnBieuDo, new Insets(0, 0, 0, 30.0));

        HBox buttonHBox = new HBox(btnBang, btnBieuDo);
        buttonHBox.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonHBox, new Insets(5.0, 0, 0, 0));

        Label timeLabel = new Label("Thời gian");
        timeLabel.getStyleClass().add("header-label");
        VBox.setMargin(timeLabel, new Insets(10.0, 0, 0, 0));

        cboThoiGian.setPrefHeight(49.0);
        cboThoiGian.setPrefWidth(446.0);
        cboThoiGian.setPromptText("Hôm Nay");

        lblTu.getStyleClass().add("header-label");
        dateTu.setPrefHeight(39.0);
        dateTu.setPrefWidth(442.0);

        lblDen.getStyleClass().add("header-label");
        dateDen.setPrefHeight(39.0);
        dateDen.setPrefWidth(441.0);

        Label exportLabel = new Label("Xuất file");
        exportLabel.getStyleClass().add("header-label");
        VBox.setMargin(exportLabel, new Insets(10.0, 0, 0, 0));

        cboXuatfile.setPrefHeight(49.0);
        cboXuatfile.setPrefWidth(441.0);
        cboXuatfile.setPromptText("Chọn định dạng file");

        btnXuat.setPrefHeight(53.0);
        btnXuat.setPrefWidth(438.0);
        VBox.setMargin(btnXuat, new Insets(10.0, 0, 0, 0));

        leftVBox.getChildren().addAll(
                titleHBox, separator, displayLabel, buttonHBox,
                timeLabel, cboThoiGian, lblTu, dateTu, lblDen, dateDen,
                exportLabel, cboXuatfile, btnXuat
        );

        // --- 6. Dựng VBox bên phải ---
        VBox rightVBox = new VBox();
        rightVBox.setPrefHeight(1126.0);
        rightVBox.setPrefWidth(1161.0);

        // QUAN TRỌNG: Thêm class vbox
        rightVBox.getStyleClass().add("vbox");

        Label revenueLabel = new Label("Doanh thu");
        revenueLabel.getStyleClass().add("header-label");
        revenueLabel.setAlignment(Pos.CENTER);
        revenueLabel.setPrefWidth(1167.0);

        Label invoiceListLabel = new Label("Danh sách hóa đơn");
        // Thay vì set màu cứng, dùng class sub-title
        invoiceListLabel.getStyleClass().add("sub-title");
        VBox.setMargin(invoiceListLabel, new Insets(15.0, 0, 8.0, 10.0));

        rightVBox.getChildren().addAll(revenueLabel, tableDoanhThu, chartDoanhThu, invoiceListLabel, tableHoaDon);

        // --- 7. Dựng HBox chứa 2 VBox ---
        HBox mainHBox = new HBox(15, leftVBox, rightVBox); // Khoảng cách giữa 2 cột là 15

        // --- 8. Pane gốc ---
        Pane root = new Pane();
        root.setPrefHeight(895.0);
        root.setPrefWidth(1646.0);

        // QUAN TRỌNG: Set ID mainPane để nhận nền gradient
        root.setId("mainPane");

        mainHBox.setLayoutX(14.0);
        mainHBox.setLayoutY(14.0);
        root.getChildren().add(mainHBox);

        // --- 9. Tiêm vào Controller ---
        ctrl.btnBang = btnBang;
        ctrl.btnBieuDo = btnBieuDo;
        ctrl.btnXuat = btnXuat;
        ctrl.cboThoiGian = cboThoiGian;
        ctrl.cboXuatfile = cboXuatfile;
        ctrl.dateTu = dateTu;
        ctrl.dateDen = dateDen;
        ctrl.lblTu = lblTu;
        ctrl.lblDen = lblDen;

        ctrl.tableDoanhThu = tableDoanhThu;
        ctrl.cotTG = cotTG;
        ctrl.cotSLHoaDon = cotSLHoaDon;
        ctrl.cotTongGT = cotTongGT;
        ctrl.cotGG = cotGG;
        ctrl.cotDT = cotDT;
        ctrl.cotGTDonTra = cotGTDonTra;
        ctrl.cotDoanhThu = cotDoanhThu;

        ctrl.xAxis = xAxis;
        ctrl.yAxis = yAxis;
        ctrl.chartDoanhThu = chartDoanhThu;

        ctrl.tableHoaDon = tableHoaDon;
        ctrl.cotMaHoaDon = cotMaHoaDon;
        ctrl.cotNgayLap = cotNgayLap;
        ctrl.cotMaKhachHang = cotMaKhachHang;
        ctrl.cotMaNhanVien = cotMaNhanVien;
        ctrl.cotTongTien = cotTongTien;

        // --- BƯỚC 2: Tạo Scene ---
        // (Vẫn giữ dòng này để tránh lỗi NullPointer nếu ViewEmbedder cần Scene)
        Scene scene = new Scene(root);

        // --- SỬA LẠI: Gắn CSS trực tiếp vào ROOT (Quan trọng nhất) ---
        String cssPath = "/com/example/pharmacy/client/css/ThongKeBanHang.css";
        java.net.URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl != null) {
            // CÁCH CŨ (Chỉ add vào Scene -> Sai khi nhúng):
            // scene.getStylesheets().add(cssUrl.toExternalForm());

            // CÁCH MỚI (Add thẳng vào Pane gốc -> Đi đâu cũng có CSS):
            root.getStylesheets().add(cssUrl.toExternalForm());

            System.out.println("Đã gắn CSS vào Root Pane thành công!");
        } else {
            // Thử tìm đường dẫn ngắn nếu đường dẫn dài lỗi
            var shortUrl = getClass().getResource("/css/ThongKeBanHang.css");
            if(shortUrl != null) {
                root.getStylesheets().add(shortUrl.toExternalForm());
            } else {
                System.err.println("Không tìm thấy CSS!");
            }
        }

        // --- BƯỚC 3: Set Scene vào Stage ---
        stage.setScene(scene);

        // --- BƯỚC 4: Khởi tạo dữ liệu ---
        ctrl.initialize();
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

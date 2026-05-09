package com.example.pharmacy.client.view.CN_ThongKe;

import com.example.pharmacy.client.controller.CN_ThongKe.ThongKeTopSanPham_Ctrl;
import com.example.pharmacy.common.model.ThongKeTopSanPhamDto;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThongKeTopSanPham_GUI {

    public void showWithController(Stage stage, ThongKeTopSanPham_Ctrl ctrl) {

        // --- 1. PANEL TRÁI (CONTROLS) ---
        VBox leftVBox = new VBox();
        leftVBox.setPrefHeight(1126.0);
        leftVBox.setPrefWidth(449.0);
        leftVBox.getStyleClass().add("vbox");
        leftVBox.setSpacing(10);

        Label titleLabel = new Label("Xếp hạng thuốc");
        titleLabel.getStyleClass().add("title");
        ImageView titleIcon = createIcon("/com/example/pharmacy/client/img/bar-chart.png", 33, 40);
        HBox titleHBox = new HBox(titleLabel,new Label("", titleIcon));

        // Controls
        ComboBox<String> cboTieuChi = new ComboBox<>();
        cboTieuChi.setPrefWidth(446.0); cboTieuChi.setPrefHeight(49.0);

        ComboBox<String> cboThoiGian = new ComboBox<>();
        cboThoiGian.setPrefWidth(446.0); cboThoiGian.setPrefHeight(49.0);

        ComboBox<Integer> cboTop = new ComboBox<>();
        cboTop.setPrefWidth(446.0); cboTop.setPrefHeight(49.0);

        DatePicker dateTu = new DatePicker();
        DatePicker dateDen = new DatePicker();
        Label lblTu = new Label("Từ:");
        Label lblDen = new Label("Đến:");
        dateTu.setPrefWidth(446.0); dateTu.setPrefHeight(40.0);
        dateDen.setPrefWidth(446.0); dateDen.setPrefHeight(40.0);

        ComboBox<String> cboXuatFile = new ComboBox<>();
        cboXuatFile.setPrefWidth(446.0); cboXuatFile.setPrefHeight(49.0);
        cboXuatFile.setPromptText("Chọn định dạng file");

        // --- SỬA NÚT TẠI ĐÂY ---
        Button btnXuat = new Button("Xuất File 💾");
        btnXuat.setPrefWidth(446.0); btnXuat.setPrefHeight(55.0);
        // KHÔNG set ID "btnBang" nữa -> Nó sẽ nhận màu xanh biển mặc định (.button)
        // btnXuat.setId("btnBang"); <--- ĐÃ XÓA

        Label lblTieuChi = new Label("Tiêu chí"); lblTieuChi.getStyleClass().add("header-label");
        Label lblThoiGian = new Label("Thời gian"); lblThoiGian.getStyleClass().add("header-label");
        Label lblTop = new Label("Top hiển thị"); lblTop.getStyleClass().add("header-label");
        Label lblXuat = new Label("Xuất file"); lblXuat.getStyleClass().add("header-label");

        leftVBox.getChildren().addAll(
                titleHBox, new Separator(),
                lblTieuChi, cboTieuChi,
                lblThoiGian, cboThoiGian,
                lblTu, dateTu, lblDen, dateDen,
                lblTop, cboTop,
                lblXuat, cboXuatFile, btnXuat
        );

        // --- 2. PANEL PHẢI (DISPLAY) ---
        VBox rightVBox = new VBox();
        rightVBox.setPrefWidth(1161.0);
        rightVBox.getStyleClass().add("vbox");

        // --- SỬA CHART TẠI ĐÂY ---
        HBox chartArea = new HBox(10);
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Tỷ trọng (Top 5)");

        // CẤU HÌNH ĐỂ HIỆN CHỮ XUNG QUANH
        pieChart.setLabelsVisible(true);  // BẮT BUỘC TRUE để hiện chữ xung quanh
        pieChart.setLabelLineLength(20);  // Độ dài đường kẻ chỉ dẫn
        pieChart.setLegendVisible(true);  // Vẫn hiện chú thích
        pieChart.setLegendSide(Side.BOTTOM); // Chú thích nằm dưới (hoặc RIGHT)

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("So sánh (Top 5)");
        barChart.setLegendVisible(false);

        HBox.setHgrow(pieChart, Priority.ALWAYS);
        HBox.setHgrow(barChart, Priority.ALWAYS);
        chartArea.getChildren().addAll(pieChart, barChart);

        // --- SỬA LABEL "BIỂU ĐỒ TRỰC QUAN" TẠI ĐÂY ---
        Label lblChartHeader = new Label("Biểu đồ trực quan");
        // Set style trực tiếp cho To và Đậm
        lblChartHeader.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2d3a4b;");

        // Table
        Label listLabel = new Label("Danh sách chi tiết");
        listLabel.getStyleClass().add("sub-title"); // Màu đỏ, to

        TableView<ThongKeTopSanPhamDto> table = new TableView<>();
        table.setPrefHeight(300);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ThongKeTopSanPhamDto, Integer> colSTT = new TableColumn<>("Hạng");
        TableColumn<ThongKeTopSanPhamDto, String> colMa = new TableColumn<>("Mã thuốc");
        TableColumn<ThongKeTopSanPhamDto, String> colTen = new TableColumn<>("Tên thuốc");
        TableColumn<ThongKeTopSanPhamDto, String> colDVT = new TableColumn<>("ĐVT");
        TableColumn<ThongKeTopSanPhamDto, Integer> colSL = new TableColumn<>("Số lượng bán");
        TableColumn<ThongKeTopSanPhamDto, Double> colTien = new TableColumn<>("Tổng tiền");

        colSTT.setMinWidth(50);
        colMa.setMinWidth(100);
        colTen.setMinWidth(250);
        colDVT.setMinWidth(80);
        colSL.setMinWidth(100);
        colTien.setMinWidth(150);

        table.getColumns().addAll(colSTT, colMa, colTen, colDVT, colSL, colTien);

        // Thêm các thành phần vào Panel phải
        rightVBox.getChildren().addAll(
                lblChartHeader, // Label đã sửa style
                chartArea,
                new Separator(),
                listLabel,
                table
        );

        // --- 3. ROOT & SCENE ---
        HBox mainHBox = new HBox(15, leftVBox, rightVBox);
        mainHBox.setLayoutX(14.0); mainHBox.setLayoutY(14.0);

        Pane root = new Pane();
        root.setPrefSize(1646, 895);
        root.setId("mainPane");
        root.getChildren().add(mainHBox);

        // --- 4. INJECT ---
        ctrl.cboTieuChi = cboTieuChi;
        ctrl.cboThoiGian = cboThoiGian;
        ctrl.cboTop = cboTop;
        ctrl.cboXuatFile = cboXuatFile;
        ctrl.dateTu = dateTu;
        ctrl.dateDen = dateDen;
        ctrl.lblTu = lblTu;
        ctrl.lblDen = lblDen;
        ctrl.btnXuat = btnXuat;

        ctrl.pieChart = pieChart;
        ctrl.barChart = barChart;
        ctrl.xAxis = xAxis;
        ctrl.yAxis = yAxis;

        ctrl.table = table;
        ctrl.colSTT = colSTT;
        ctrl.colMa = colMa;
        ctrl.colTen = colTen;
        ctrl.colDVT = colDVT;
        ctrl.colSL = colSL;
        ctrl.colTien = colTien;

        Scene scene = new Scene(root);
        String cssPath = "/com/example/pharmacy/client/css/ThongKeBanHangDto.css";
        java.net.URL cssUrl = getClass().getResource(cssPath);
        if (cssUrl != null) root.getStylesheets().add(cssUrl.toExternalForm());

        stage.setScene(scene);
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
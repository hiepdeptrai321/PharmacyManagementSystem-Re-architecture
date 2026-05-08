package com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe;

// Sửa đường dẫn package của View
import com.example.pharmacymanagementsystem_qlht.view.CN_ThongKe.ThongKeBanHang_GUI;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.io.font.constants.StandardFonts;

import java.text.DecimalFormat;
import javafx.scene.control.TableCell;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeBanHang;
import com.example.pharmacymanagementsystem_qlht.model.HoaDonDisplay;
import com.example.pharmacymanagementsystem_qlht.service.ThongKeService;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.time.LocalDate;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class ThongKeBanHang_Ctrl extends Application {

    // --- 1. KHAI BÁO PUBLIC CÁC THÀNH PHẦN GIAO DIỆN ---
    // Panel trái
    public Button btnBang;
    public Button btnBieuDo;
    public Button btnXuat;
    public ComboBox<String> cboThoiGian;
    public ComboBox<String> cboXuatfile;
    public DatePicker dateTu;
    public DatePicker dateDen;
    public Label lblTu;
    public Label lblDen;
    // Bảng Doanh Thu
    public TableView<ThongKeBanHang> tableDoanhThu;
    public TableColumn<ThongKeBanHang, String> cotTG;
    public TableColumn<ThongKeBanHang, Integer> cotSLHoaDon;
    public TableColumn<ThongKeBanHang, Double> cotTongGT;
    public TableColumn<ThongKeBanHang, Double> cotGG;
    public TableColumn<ThongKeBanHang, Integer> cotDT;
    public TableColumn<ThongKeBanHang, Double> cotGTDonTra;
    public TableColumn<ThongKeBanHang, Double> cotDoanhThu;
    // Biểu đồ
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    public BarChart<String, Number> chartDoanhThu;
    // Bảng Hóa Đơn
    public TableView<HoaDonDisplay> tableHoaDon;
    public TableColumn<HoaDonDisplay, String> cotMaHoaDon;
    public TableColumn<HoaDonDisplay, LocalDate> cotNgayLap;
    public TableColumn<HoaDonDisplay, String> cotMaKhachHang;
    public TableColumn<HoaDonDisplay, String> cotMaNhanVien;
    public TableColumn<HoaDonDisplay, Double> cotTongTien;

    // --- 2. CÁC BIẾN LOGIC (GIỮ NGUYÊN) ---
    private final ThongKeService thongKeService = new ThongKeService();
    private ObservableList<ThongKeBanHang> listThongKe;
    private ObservableList<HoaDonDisplay> listHoaDon;
    private DecimalFormat formatter;

    // Bỏ `private ThongKeBanHang_GUI view;`
    // Bỏ constructor

    @Override
    public void start(Stage stage) throws Exception {
        // --- ĐÃ THAY THẾ LOGIC start() ---
        new ThongKeBanHang_GUI().showWithController(stage, this);
    }

    // Đổi tên `setupLogic()` thành `initialize()`
    public void initialize() {
        // Gắn sự kiện (đổi view.btnXuat -> btnXuat)
        btnXuat.setOnAction(e -> xuatFile(e));
        btnBang.setOnAction(e -> hienThiBang(e));
        btnBieuDo.setOnAction(e -> hienThiBieuDo(e));

        // Binding
        chartDoanhThu.managedProperty().bind(chartDoanhThu.visibleProperty());
        tableDoanhThu.managedProperty().bind(tableDoanhThu.visibleProperty());

        formatter = new DecimalFormat("#,##0");

        // Setup ComboBoxes
        cboThoiGian.getItems().addAll("Hôm nay", "Tuần này", "Tháng này", "Năm Nay", "Tùy chọn");
        cboXuatfile.getItems().addAll("Excel", "PDF");

        // Cấu hình biểu đồ
        chartDoanhThu.setTitle("Biểu đồ Doanh thu");
        xAxis.setLabel("Thời gian");
        yAxis.setLabel("Doanh thu (VNĐ)");
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return formatter.format(object.doubleValue());
            }
        });

        setupTables();

        // Setup ẩn/hiện DatePicker
        lblTu.setVisible(false);
        dateTu.setVisible(false);
        lblDen.setVisible(false);
        dateDen.setVisible(false);
        lblTu.managedProperty().bind(lblTu.visibleProperty());
        dateTu.managedProperty().bind(dateTu.visibleProperty());
        lblDen.managedProperty().bind(lblDen.visibleProperty());
        dateDen.managedProperty().bind(dateDen.visibleProperty());

        // Gắn Listeners
        cboThoiGian.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                boolean isCustom = newValue.equals("Tùy chọn");
                lblTu.setVisible(isCustom);
                dateTu.setVisible(isCustom);
                lblDen.setVisible(isCustom);
                dateDen.setVisible(isCustom);
                if (isCustom) {
                    attemptAutoLoadTuyChon();
                } else {
                    loadData(newValue);
                }
            }
        });
        dateTu.valueProperty().addListener((options, oldValue, newValue) -> attemptAutoLoadTuyChon());
        dateDen.valueProperty().addListener((options, oldValue, newValue) -> attemptAutoLoadTuyChon());

        // Tải dữ liệu ban đầu
        cboThoiGian.setValue("Hôm nay");
        chartDoanhThu.setAnimated(false);
        btnXuat.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                refreshData();
            }
        });

    }

    private void setupTables() {
        // --- Setup Bảng Doanh Thu ---
        cotTG.setCellValueFactory(new PropertyValueFactory<>("thoiGian"));
        cotSLHoaDon.setCellValueFactory(new PropertyValueFactory<>("soLuongHoaDon"));
        cotTongGT.setCellValueFactory(new PropertyValueFactory<>("tongGiaTri"));
        cotTongGT.setCellFactory(col -> createFormattedCell(formatter));
        cotGG.setCellValueFactory(new PropertyValueFactory<>("giamGia"));
        cotGG.setCellFactory(col -> createFormattedCell(formatter));
        cotDT.setCellValueFactory(new PropertyValueFactory<>("soLuongDonTra"));
        cotGTDonTra.setCellValueFactory(new PropertyValueFactory<>("giaTriDonTra"));
        cotGTDonTra.setCellFactory(col -> createFormattedCell(formatter));
        cotDoanhThu.setCellValueFactory(new PropertyValueFactory<>("doanhThu"));
        cotDoanhThu.setCellFactory(col -> createFormattedCell(formatter));

        // --- Setup Bảng Hóa Đơn ---
        cotMaHoaDon.setCellValueFactory(new PropertyValueFactory<>("maHD"));
        cotNgayLap.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
        cotMaKhachHang.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        cotMaNhanVien.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        cotTongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
        cotTongTien.setCellFactory(col -> createFormattedCell(formatter));
    }

    // --- TẤT CẢ CÁC HÀM LOGIC CÒN LẠI GIỮ NGUYÊN (CHỈ BỎ `view.`) ---

    private <T> TableCell<T, Double> createFormattedCell(DecimalFormat formatter) {
        return new TableCell<T, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        };
    }

    private void loadData(String thoiGian) {
        listThongKe = FXCollections.observableArrayList(thongKeService.getThongKeBanHang(thoiGian));
        tableDoanhThu.setItems(listThongKe);

        listHoaDon = FXCollections.observableArrayList(thongKeService.getHoaDonTheoThoiGian(thoiGian));
        tableHoaDon.setItems(listHoaDon);

        updateChart(listThongKe);
        chartDoanhThu.setVisible(false);
        tableDoanhThu.setVisible(true);

        if (listThongKe.size() > 12) {
            xAxis.setTickLabelRotation(-90);
        } else {
            xAxis.setTickLabelRotation(-20);
        }
    }

    private void attemptAutoLoadTuyChon() {
        String selectedTime = cboThoiGian.getValue();
        if (selectedTime == null || !selectedTime.equals("Tùy chọn")) return;

        LocalDate tuNgay = dateTu.getValue();
        LocalDate denNgay = dateDen.getValue();
        if (tuNgay == null || denNgay == null) return;

        if (tuNgay.isAfter(denNgay)) {
            tableDoanhThu.getItems().clear();
            tableHoaDon.getItems().clear();
            chartDoanhThu.getData().clear();
            return;
        }
        loadDataTuyChon(tuNgay, denNgay);
    }

    private void loadDataTuyChon(LocalDate tuNgay, LocalDate denNgay) {
        listThongKe = FXCollections.observableArrayList(thongKeService.getThongKeBanHang(tuNgay, denNgay));
        tableDoanhThu.setItems(listThongKe);

        listHoaDon = FXCollections.observableArrayList(thongKeService.getHoaDonTheoTuyChon(tuNgay, denNgay));
        tableHoaDon.setItems(listHoaDon);

        updateChart(listThongKe);
        chartDoanhThu.setVisible(false);
        tableDoanhThu.setVisible(true);

        if (listThongKe.size() > 12) {
            xAxis.setTickLabelRotation(-90);
        } else {
            xAxis.setTickLabelRotation(-20);
        }
    }

    private void updateChart(ObservableList<ThongKeBanHang> data) {
        chartDoanhThu.getData().clear();
        if (xAxis != null) xAxis.getCategories().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Doanh thu");

        double yAxisMax = 0;
        for (ThongKeBanHang tk : data) {
            if (tk.getDoanhThu() > yAxisMax) {
                yAxisMax = tk.getDoanhThu();
            }
        }
        final double max = (yAxisMax == 0) ? 1.0 : yAxisMax;

        ObservableList<String> categories = FXCollections.observableArrayList();
        for (ThongKeBanHang tk : data) {
            String label = tk.getThoiGian() == null ? "" : tk.getThoiGian();
            categories.add(label);

            double yValue = tk.getDoanhThu();
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(label, yValue);

            dataPoint.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    Tooltip.install(newNode, new Tooltip(
                            dataPoint.getXValue() + "\nDoanh thu: " + formatter.format(dataPoint.getYValue()) + " VNĐ"
                    ));

                    Text dataText = new Text(formatter.format(yValue));
                    StackPane bar = (StackPane) newNode;
                    bar.getChildren().removeIf(node -> node instanceof Text);

                    if (yValue < max * 0.07) {
                        dataText.setStyle("-fx-font-size: 10px; -fx-fill: black; -fx-font-weight: bold;");
                        StackPane.setAlignment(dataText, Pos.TOP_CENTER);
                        StackPane.setMargin(dataText, new Insets(-15, 0, 0, 0));
                    }
                    else {
                        dataText.setStyle("-fx-font-size: 10px; -fx-fill: white; -fx-font-weight: bold;");
                        StackPane.setAlignment(dataText, Pos.TOP_CENTER);
                        StackPane.setMargin(dataText, new Insets(5, 0, 0, 0));
                    }

                    bar.getChildren().add(dataText);
                }
            });

            series.getData().add(dataPoint);
        }

        if (xAxis != null) xAxis.setCategories(categories);
        chartDoanhThu.getData().add(series);
    }

    private void xuatFile(ActionEvent event) {
        String selectedFormat = cboXuatfile.getValue();
        if (selectedFormat == null) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn định dạng", "Vui lòng chọn định dạng file (Excel hoặc PDF) để xuất.");
            return;
        }
        if ((listThongKe == null || listThongKe.isEmpty()) && (listHoaDon == null || listHoaDon.isEmpty())) {
            showAlert(Alert.AlertType.INFORMATION, "Không có dữ liệu", "Không có dữ liệu thống kê để xuất.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lưu file thống kê");
        fileChooser.setInitialFileName("BaoCao_" + LocalDate.now());

        if (selectedFormat.equals("Excel")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx"));
            File file = fileChooser.showSaveDialog(btnXuat.getScene().getWindow());
            if (file != null) {
                try {
                    xuatExcel(file);
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xuất file Excel thành công!");
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra khi xuất file Excel: " + e.getMessage());
                }
            }
        } else if (selectedFormat.equals("PDF")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));
            File file = fileChooser.showSaveDialog(btnXuat.getScene().getWindow());
            if (file != null) {
                try {
                    xuatPDF(file);
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xuất file PDF thành công!");
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra khi xuất file PDF: " + e.getMessage());
                }
            }
        }
    }

    private void xuatExcel(File file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));

            Sheet sheetDT = workbook.createSheet("Thong ke Doanh thu");
            String[] headersDT = {
                    cotTG.getText(), cotSLHoaDon.getText(), cotTongGT.getText(),
                    cotGG.getText(), cotDT.getText(), cotGTDonTra.getText(), cotDoanhThu.getText()
            };
            Row headerRowDT = sheetDT.createRow(0);
            for (int i = 0; i < headersDT.length; i++) {
                Cell cell = headerRowDT.createCell(i);
                cell.setCellValue(headersDT[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowNumDT = 1;
            for (ThongKeBanHang tk : listThongKe) {
                Row row = sheetDT.createRow(rowNumDT++);
                row.createCell(0).setCellValue(tk.getThoiGian());
                row.createCell(1).setCellValue(tk.getSoLuongHoaDon());
                row.createCell(2).setCellValue(tk.getTongGiaTri());
                row.createCell(3).setCellValue(tk.getGiamGia());
                row.createCell(4).setCellValue(tk.getSoLuongDonTra());
                row.createCell(5).setCellValue(tk.getGiaTriDonTra());
                row.createCell(6).setCellValue(tk.getDoanhThu());
            }
            for (int i = 0; i < headersDT.length; i++) {
                sheetDT.autoSizeColumn(i);
            }

            Sheet sheetHD = workbook.createSheet("Danh sach Hoa don");
            String[] headersHD = {
                    cotMaHoaDon.getText(), cotNgayLap.getText(),
                    cotMaKhachHang.getText(), cotMaNhanVien.getText(), cotTongTien.getText()
            };
            Row headerRowHD = sheetHD.createRow(0);
            for (int i = 0; i < headersHD.length; i++) {
                Cell cell = headerRowHD.createCell(i);
                cell.setCellValue(headersHD[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowNumHD = 1;
            for (HoaDonDisplay hd : listHoaDon) {
                Row row = sheetHD.createRow(rowNumHD++);
                row.createCell(0).setCellValue(hd.getMaHD());

                Cell dateCell = row.createCell(1);
                dateCell.setCellValue(hd.getNgayLap());
                dateCell.setCellStyle(dateCellStyle);

                row.createCell(2).setCellValue(hd.getMaKH());
                row.createCell(3).setCellValue(hd.getMaNV());
                row.createCell(4).setCellValue(hd.getTongTien());
            }
            for (int i = 0; i < headersHD.length; i++) {
                sheetHD.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
        }
    }

    public static final String FONT_PATH = "C:/Windows/Fonts/arial.ttf";

    private void xuatPDF(File file) throws IOException {
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FONT_PATH, "Identity-H");
        } catch (IOException e) {
            System.err.println("Không tìm thấy font tại: " + FONT_PATH + ". Sử dụng font mặc định.");
            font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        }
        document.setFont(font);

        document.add(new Paragraph("BÁO CÁO THỐNG KÊ BÁN HÀNG")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("I. Thống kê Doanh thu")
                .setFontSize(14)
                .setBold()
                .setMarginTop(15));
        float[] columnWidthsDT = {2, 1, 1, 1, 1, 1, 1};
        Table tableDT = new Table(UnitValue.createPercentArray(columnWidthsDT));
        tableDT.setWidth(UnitValue.createPercentValue(100));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotTG.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotSLHoaDon.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotTongGT.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotGG.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotDT.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotGTDonTra.getText()).setBold()));
        tableDT.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotDoanhThu.getText()).setBold()));
        for (ThongKeBanHang tk : listThongKe) {
            tableDT.addCell(tk.getThoiGian());
            tableDT.addCell(String.valueOf(tk.getSoLuongHoaDon()));
            tableDT.addCell(formatter.format(tk.getTongGiaTri()));
            tableDT.addCell(formatter.format(tk.getGiamGia()));
            tableDT.addCell(String.valueOf(tk.getSoLuongDonTra()));
            tableDT.addCell(formatter.format(tk.getGiaTriDonTra()));
            tableDT.addCell(formatter.format(tk.getDoanhThu()));
        }
        document.add(tableDT);

        document.add(new Paragraph("II. Danh sách Hóa đơn")
                .setFontSize(14)
                .setBold()
                .setMarginTop(15));
        float[] columnWidthsHD = {2, 2, 2, 2, 2}; // 5 cột
        Table tableHD = new Table(UnitValue.createPercentArray(columnWidthsHD));
        tableHD.setWidth(UnitValue.createPercentValue(100));
        tableHD.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotMaHoaDon.getText()).setBold()));
        tableHD.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotNgayLap.getText()).setBold()));
        tableHD.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotMaKhachHang.getText()).setBold()));
        tableHD.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotMaNhanVien.getText()).setBold()));
        tableHD.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(cotTongTien.getText()).setBold()));
        for (HoaDonDisplay hd : listHoaDon) {
            tableHD.addCell(hd.getMaHD());
            tableHD.addCell(hd.getNgayLap().toString());
            tableHD.addCell(hd.getMaKH() != null ? hd.getMaKH() : "");
            tableHD.addCell(hd.getMaNV() != null ? hd.getMaNV() : "");
            tableHD.addCell(formatter.format(hd.getTongTien()));
        }
        document.add(tableHD);

        document.close();
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void hienThiBieuDo(ActionEvent event) {
        chartDoanhThu.setVisible(true);
        tableDoanhThu.setVisible(false);
    }

    private void hienThiBang(ActionEvent event) {
        chartDoanhThu.setVisible(false);
        tableDoanhThu.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void refreshData() {
        if (cboThoiGian.getValue().equals("Tùy chọn")) {
            attemptAutoLoadTuyChon();
        } else {
            loadData(cboThoiGian.getValue());
        }
    }
}

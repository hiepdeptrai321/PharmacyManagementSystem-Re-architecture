package com.example.pharmacy.client.controller.CN_ThongKe;

// Các import logic (DAO, Model, POI, iText) giữ nguyên
import com.example.pharmacy.common.model.ThongKeTonKhoDto;
import com.example.pharmacy.common.model.ThuocHetHanDto;
import com.example.pharmacy.client.service.ThongKeService;

import com.example.pharmacy.client.view.CN_ThongKe.ThongKeXNT_GUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

// Các import POI và iText giữ nguyên
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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


public class ThongKeXNT_Ctrl extends Application {

    // --- 1. KHAI BÁO PUBLIC CÁC THÀNH PHẦN GIAO DIỆN ---
    // Bảng Tồn kho
    public TableView<ThongKeTonKhoDto> tbTon;
    public TableColumn<ThongKeTonKhoDto, Integer> ColTDK;
    public TableColumn<ThongKeTonKhoDto, String> colDVT;
    public TableColumn<ThongKeTonKhoDto, String> colMaThuoc;
    public TableColumn<ThongKeTonKhoDto, Integer> colNTK;
    public TableColumn<ThongKeTonKhoDto, String> colTenThuoc;
    public TableColumn<ThongKeTonKhoDto, Integer> colTCK;
    public TableColumn<ThongKeTonKhoDto, Integer> colXTK;
    // Bảng Hết hạn
    public TableView<ThuocHetHanDto> tbHetHan;
    public TableColumn<ThuocHetHanDto, String> colMaThuocHH;
    public TableColumn<ThuocHetHanDto, LocalDate> colNgayHH;
    public TableColumn<ThuocHetHanDto, Integer> colSoLuong;
    public TableColumn<ThuocHetHanDto, String> cotTenThuocHH;
    // Panel bên trái
    public Button btnXuat;
    public ComboBox<String> cboThoiGian;
    public ComboBox<String> cboXuat;
    public DatePicker dateDen;
    public DatePicker dateTu;
    public TextField txtTimNhanh;
    public Label lblTu;
    public Label lblDen;

    // --- 2. CÁC BIẾN LOGIC (GIỮ NGUYÊN) ---
    private ObservableList<ThongKeTonKhoDto> masterDataTonKho = FXCollections.observableArrayList();
    private ObservableList<ThuocHetHanDto> dataHetHan = FXCollections.observableArrayList();
    private final ThongKeService thongKeService = new ThongKeService();
    public static final String FONT_PATH = "C:/Windows/Fonts/arial.ttf";


    // --- 3. HÀM START (SỬA ĐỔI ĐỂ GỌI GUI) ---
    @Override
    public void start(Stage primaryStage) throws IOException {
        // 1. Khởi tạo View
        ThongKeXNT_GUI view = new ThongKeXNT_GUI();

        // 2. Dựng giao diện từ View
        // (GUI sẽ tự tạo Scene, gán CSS, gọi initialize, và show stage)
        view.showWithController(primaryStage, this);
    }

    // --- 4. HÀM INITIALIZE (ĐỔI TÊN TỪ setupLogic) ---
    public void initialize() {
        // 1. Cấu hình các bảng
        setupThongKeTable();
        setupHetHanTable();
        setupComboBoxes();
        setupSearchFilter();

        // 2. Cấu hình binding
        lblTu.managedProperty().bind(lblTu.visibleProperty());
        dateTu.managedProperty().bind(dateTu.visibleProperty());
        lblDen.managedProperty().bind(lblDen.visibleProperty());
        dateDen.managedProperty().bind(dateDen.visibleProperty());

        // 3. Tải dữ liệu ban đầu
        loadDataHetHan();
        cboThoiGian.setValue("Hôm nay");

        // 4. Gắn sự kiện
        btnXuat.setOnAction(e -> xuatFile(e));
        btnXuat.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                refreshData();
            }
        });
    }

    // --- 5. CÁC HÀM SETUP (GIỮ NGUYÊN, CHỈ BỎ `view.`) ---
    private void setupThongKeTable() {
        colMaThuoc.setCellValueFactory(new PropertyValueFactory<>("maThuoc"));
        colTenThuoc.setCellValueFactory(new PropertyValueFactory<>("tenThuoc"));
        colDVT.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        ColTDK.setCellValueFactory(new PropertyValueFactory<>("tonDauKy"));
        colNTK.setCellValueFactory(new PropertyValueFactory<>("nhapTrongKy"));
        colXTK.setCellValueFactory(new PropertyValueFactory<>("xuatTrongKy"));
        colTCK.setCellValueFactory(new PropertyValueFactory<>("tonCuoiKy"));
    }

    private void setupHetHanTable() {
        colMaThuocHH.setCellValueFactory(new PropertyValueFactory<>("maThuocHH"));
        cotTenThuocHH.setCellValueFactory(new PropertyValueFactory<>("tenThuocHH"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colNgayHH.setCellValueFactory(new PropertyValueFactory<>("ngayHetHan"));
        tbHetHan.setItems(dataHetHan); // Gắn danh sách dữ liệu vào bảng
    }

    private void setupComboBoxes() {
        cboThoiGian.setItems(FXCollections.observableArrayList(
                "Hôm nay", "Tuần này", "Tháng này", "Năm nay", "Tùy chọn"
        ));

        cboXuat.setItems(FXCollections.observableArrayList(
                "Excel",
                "PDF"
        ));
        cboXuat.setValue("Excel"); // Đặt giá trị mặc định

        cboThoiGian.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                handleThoiGianChange();
            }
        });

        dateTu.setOnAction(e -> handleDateChange());
        dateDen.setOnAction(e -> handleDateChange());

        lblTu.setVisible(false);
        dateTu.setVisible(false);
        lblDen.setVisible(false);
        dateDen.setVisible(false);
    }

    private void setupSearchFilter() {
        FilteredList<ThongKeTonKhoDto> filteredData = new FilteredList<>(masterDataTonKho, p -> true);

        txtTimNhanh.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(thongKe -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (thongKe.getMaThuoc().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return thongKe.getTenThuoc().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<ThongKeTonKhoDto> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbTon.comparatorProperty());
        tbTon.setItems(sortedData);
    }

    // --- 6. CÁC HÀM XỬ LÝ LOGIC (GIỮ NGUYÊN, CHỈ BỎ `view.`) ---
    private void handleThoiGianChange() {
        String selected = cboThoiGian.getValue();
        if (selected == null) return;

        LocalDate today = LocalDate.now();
        LocalDate tu = null;
        LocalDate den = null;

        if (selected.equals("Tùy chọn")) {
            lblTu.setVisible(true);
            dateTu.setVisible(true);
            lblDen.setVisible(true);
            dateDen.setVisible(true);

            if (dateTu.getValue() != null && dateDen.getValue() != null) {
                loadDataThongKe(dateTu.getValue(), dateDen.getValue());
            } else {
                masterDataTonKho.clear();
            }
            return;
        }

        // Nếu không phải "Tùy chọn", ẩn các control
        lblTu.setVisible(false);
        dateTu.setVisible(false);
        lblDen.setVisible(false);
        dateDen.setVisible(false);

        switch (selected) {
            case "Hôm nay":
                tu = today;
                den = today;
                break;
            case "Tuần này":
                tu = today.with(DayOfWeek.MONDAY);
                den = today.with(DayOfWeek.SUNDAY);
                break;
            case "Tháng này":
                tu = today.withDayOfMonth(1);
                den = today.withDayOfMonth(today.lengthOfMonth());
                break;
            case "Năm nay":
                tu = today.withDayOfYear(1);
                den = today.withDayOfYear(today.lengthOfYear());
                break;
        }

        dateTu.setValue(tu);
        dateDen.setValue(den);
        loadDataThongKe(tu, den);
    }

    private void handleDateChange() {
        if ("Tùy chọn".equals(cboThoiGian.getValue())) {
            LocalDate tu = dateTu.getValue();
            LocalDate den = dateDen.getValue();

            if (tu != null && den != null) {
                if (den.isBefore(tu)) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi chọn ngày", "Ngày kết thúc không được trước ngày bắt đầu.");
                    masterDataTonKho.clear();
                } else {
                    loadDataThongKe(tu, den);
                }
            }
        }
    }

    private void loadDataThongKe(LocalDate tu, LocalDate den) {
        masterDataTonKho.clear();
        try {
            masterDataTonKho.setAll(thongKeService.getThongKeXNT(tu, den));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi CSDL", "Không thể tải dữ liệu thống kê: " + e.getMessage());
        }
    }

    private void loadDataHetHan() {
        dataHetHan.clear();
        try {
            dataHetHan.setAll(thongKeService.getThuocHetHan());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi CSDL", "Không thể tải danh sách thuốc hết hạn: " + e.getMessage());
        }
    }

    // --- 7. CÁC HÀM XUẤT FILE (GIỮ NGUYÊN, CHỈ BỎ `view.`) ---
    private void xuatFile(ActionEvent event) {
        String selectedFormat = cboXuat.getValue();
        if (selectedFormat == null) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn định dạng", "Vui lòng chọn định dạng file (Excel hoặc PDF) để xuất.");
            return;
        }

        if (masterDataTonKho.isEmpty() && dataHetHan.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Không có dữ liệu", "Không có dữ liệu để xuất.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lưu báo cáo");
        fileChooser.setInitialFileName("BaoCao_XNT_" + LocalDate.now());

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
    public void refreshData() {
        loadDataHetHan();
        handleThoiGianChange();
    }
    private void xuatExcel(File file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);

            Sheet sheet1 = workbook.createSheet("Thong Ke XNT");
            String[] headers1 = {"Mã thuốc", "Tên thuốc", "ĐVT", "Tồn đầu kỳ", "Nhập trong kỳ", "Xuất trong kỳ", "Tồn cuối kỳ"};

            Row headerRow1 = sheet1.createRow(0);
            for (int i = 0; i < headers1.length; i++) {
                Cell cell = headerRow1.createCell(i);
                cell.setCellValue(headers1[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum1 = 1;
            for (ThongKeTonKhoDto tk : masterDataTonKho) {
                Row row = sheet1.createRow(rowNum1++);
                row.createCell(0).setCellValue(tk.getMaThuoc());
                row.createCell(1).setCellValue(tk.getTenThuoc());
                row.createCell(2).setCellValue(tk.getDvt());
                row.createCell(3).setCellValue(tk.getTonDauKy());
                row.createCell(4).setCellValue(tk.getNhapTrongKy());
                row.createCell(5).setCellValue(tk.getXuatTrongKy());
                row.createCell(6).setCellValue(tk.getTonCuoiKy());
            }

            for (int i = 0; i < headers1.length; i++) {
                sheet1.autoSizeColumn(i);
            }
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
        }
    }

    private void xuatPDF(File file) throws IOException {
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        pdf.setDefaultPageSize(com.itextpdf.kernel.geom.PageSize.A4.rotate());

        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FONT_PATH);
        } catch (IOException e) {
            System.err.println("Không tìm thấy font tại: " + FONT_PATH + ". Sử dụng font mặc định.");
            font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        }
        document.setFont(font);

        document.add(new Paragraph("BÁO CÁO XUẤT - NHẬP - TỒN")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("I. Thống kê Xuất-Nhập-Tồn")
                .setFontSize(14)
                .setBold()
                .setMarginTop(15));

        float[] columnWidths1 = {1.2f, 3f, 1f, 1.5f, 1.5f, 1.5f, 1.5f};
        Table table1 = new Table(UnitValue.createPercentArray(columnWidths1));
        table1.setWidth(UnitValue.createPercentValue(90));

        table1.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Mã thuốc").setBold()));
        table1.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Tên thuốc").setBold()));
        table1.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("ĐVT").setBold()));
        table1.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Tồn đầu kỳ").setBold()));
        table1.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Nhập trong kỳ").setBold()));
        table1.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Xuất trong kỳ").setBold()));
        table1.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Tồn cuối kỳ").setBold()));

        for (ThongKeTonKhoDto tk : masterDataTonKho) {
            table1.addCell(tk.getMaThuoc());
            table1.addCell(tk.getTenThuoc());
            table1.addCell(tk.getDvt());
            table1.addCell(String.valueOf(tk.getTonDauKy()));
            table1.addCell(String.valueOf(tk.getNhapTrongKy()));
            table1.addCell(String.valueOf(tk.getXuatTrongKy()));
            table1.addCell(String.valueOf(tk.getTonCuoiKy()));
        }
        document.add(table1);

        document.add(new Paragraph("Báo cáo được tạo ngày: " + LocalDate.now())
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(10)
                .setMarginTop(15));

        document.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

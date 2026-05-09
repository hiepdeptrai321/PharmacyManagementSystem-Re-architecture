package com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuTraHang;

import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.ChiTietPhieuTraHangDto;
import com.example.pharmacy.common.model.HoaDonDto;
import com.example.pharmacy.common.model.PhieuTraHangDto;
import com.example.pharmacy.client.service.DoiTraService;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.example.pharmacy.client.TienIch.TuyChinhAlert.hien;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class ChiTietPhieuTraHang_Ctrl {
    private static final String FONT_PATH = "C:/Windows/Fonts/arial.ttf";

    public PhieuTraHangDto phieuTraHang;
    public TableColumn<ChiTietPhieuTraHangDto, String> colDonGia;
    public Button btnDong;
    public Button btnInPhieuTra;
    public Label lblTongTienHDGocValue;
    public Label lblSoTienTraLaiValue;
    public TableColumn<ChiTietPhieuTraHangDto, String> colLyDo;
    public TableColumn<ChiTietPhieuTraHangDto, String> colSTT;
    public TableColumn<ChiTietPhieuTraHangDto, String> colSoLuong;
    public TableColumn<ChiTietPhieuTraHangDto, String> colTenSP;
    public TableColumn<ChiTietPhieuTraHangDto, Double> colThanhTien;
    public Label lblGhiChuValue;
    public Label lblMaPhieuTraValue;
    public Label lblNgayLapValue;
    public Label lblSDTKH;
    public Label lblSDTKhachHangValue;
    public Label lblTenKH;
    public Label lblTenKhachHangValue;
    public Label lblTenNV;
    public Label lblTenNhanVienValue;
    public TableView<ChiTietPhieuTraHangDto> tblChiTietPhieuTra;

    private final DoiTraService doiTraService = new DoiTraService();
    private boolean uiReady;
    private boolean initialized;

    public void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        btnDong.setOnAction(e -> ((Stage) btnDong.getScene().getWindow()).close());
        btnInPhieuTra.setOnAction(e -> xuLyXuatPDFPhieuTra());
    }

    public void onUIReady() {
        uiReady = true;
        tryRender();
    }

    public void setPhieuTraHang(PhieuTraHangDto phieuTraHang) {
        this.phieuTraHang = phieuTraHang;
        tryRender();
    }

    private void tryRender() {
        if (!uiReady || phieuTraHang == null) {
            return;
        }
        hienThiThongTin();
    }

    private void hienThiThongTin() {
        lblMaPhieuTraValue.setText(safe(phieuTraHang.getMaPT()));
        lblNgayLapValue.setText(phieuTraHang.getNgayLap() == null
                ? ""
                : new SimpleDateFormat("dd/MM/yyyy HH:mm").format(phieuTraHang.getNgayLap()));
        lblTenNhanVienValue.setText(phieuTraHang.getNhanVien() == null ? "" : safe(phieuTraHang.getNhanVien().getTenNV()));

        if (phieuTraHang.getKhachHang() != null) {
            lblTenKhachHangValue.setText(safe(phieuTraHang.getKhachHang().getTenKH()));
            lblSDTKhachHangValue.setText(safe(phieuTraHang.getKhachHang().getSdt()));
        } else {
            lblTenKhachHangValue.setText("Khach le");
            lblSDTKhachHangValue.setText("");
        }
        lblGhiChuValue.setText(safe(phieuTraHang.getGhiChu()));

        List<ChiTietPhieuTraHangDto> details = doiTraService.findChiTietPhieuTraByMaPT(phieuTraHang.getMaPT());
        tblChiTietPhieuTra.getItems().setAll(details);

        colSTT.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(tblChiTietPhieuTra.getItems().indexOf(cd.getValue()) + 1)));
        colTenSP.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getThuoc() == null ? "" : safe(cd.getValue().getThuoc().getTenThuoc())
        ));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colDonGia.setCellValueFactory(cd -> new SimpleStringProperty(formatVNDTable(cd.getValue().getDonGia())));
        colLyDo.setCellValueFactory(cd -> new SimpleStringProperty(safe(cd.getValue().getLyDoTra())));
        colThanhTien.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getThanhTienTra()));
        colThanhTien.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatVNDTable(item));
                setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
            }
        });

        HoaDonDto hoaDonGoc = phieuTraHang.getHoaDon();
        double tongTienHDGoc = 0;
        if (hoaDonGoc != null && hoaDonGoc.getMaHD() != null) {
            List<ChiTietHoaDonDto> invoiceDetails = doiTraService.findHoaDonDetailsForDoiTra(hoaDonGoc.getMaHD());
            tongTienHDGoc = invoiceDetails.stream().mapToDouble(ChiTietHoaDonDto::tinhThanhTien).sum();
        }
        lblTongTienHDGocValue.setText(formatVNDTable(tongTienHDGoc));
        double tongTienTra = details.stream().mapToDouble(ChiTietPhieuTraHangDto::getThanhTienTra).sum();
        lblSoTienTraLaiValue.setText(formatVNDTable(tongTienTra));
    }

    public void xuLyXuatPDFPhieuTra() {
        if (phieuTraHang == null || tblChiTietPhieuTra.getItems().isEmpty()) {
            hien(INFORMATION, "Khong co du lieu", "Khong co phieu tra hang de xuat PDF.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Luu Phieu Tra Hang PDF");
        chooser.setInitialFileName("PhieuTra_" + phieuTraHang.getMaPT() + "_" + LocalDate.now());
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));

        Stage stage = (Stage) btnInPhieuTra.getScene().getWindow();
        File file = chooser.showSaveDialog(stage);
        if (file == null) {
            return;
        }

        try {
            xuatPhieuTraPDF(file);
            hien(INFORMATION, "Thanh cong", "Xuat PDF phieu tra hang thanh cong!");
        } catch (Exception exception) {
            exception.printStackTrace();
            hien(ERROR, "Loi", "Xuat PDF that bai: " + exception.getMessage());
        }
    }

    private void xuatPhieuTraPDF(File file) throws Exception {
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FONT_PATH, PdfEncodings.IDENTITY_H);
        } catch (IOException exception) {
            font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        }
        document.setFont(font);

        try (InputStream inputStream = getClass().getResourceAsStream("/com/example/pharmacy/client/img/logo.png")) {
            if (inputStream != null) {
                ImageData data = ImageDataFactory.create(inputStream.readAllBytes());
                Image logo = new Image(data);
                logo.scaleToFit(120, 120);
                logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                document.add(logo);
            }
        }

        document.add(new Paragraph("QUOC KHANH PHARMACY").setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("PHIEU TRA HANG").setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Ma phieu tra: " + safe(phieuTraHang.getMaPT())).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Ngay lap: " + lblNgayLapValue.getText()).setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\nTHONG TIN GIAO DICH").setBold().setFontSize(14));
        document.add(new Paragraph("Khach hang: " + lblTenKhachHangValue.getText()));
        document.add(new Paragraph("SDT: " + lblSDTKhachHangValue.getText()));
        document.add(new Paragraph("Nhan vien lap: " + lblTenNhanVienValue.getText()));

        document.add(new Paragraph("\nCHI TIET TRA HANG").setBold().setFontSize(14));
        float[] widths = {1, 4, 1.5f, 2.5f, 2.5f};
        Table table = new Table(UnitValue.createPercentArray(widths)).setWidth(UnitValue.createPercentValue(100));
        addHeader(table, "STT");
        addHeader(table, "Ten thuoc");
        addHeader(table, "SL");
        addHeader(table, "Don gia");
        addHeader(table, "Thanh tien");

        int stt = 1;
        double tongTra = 0;
        for (ChiTietPhieuTraHangDto item : tblChiTietPhieuTra.getItems()) {
            table.addCell(String.valueOf(stt++));
            table.addCell(item.getThuoc() == null ? "" : safe(item.getThuoc().getTenThuoc()));
            table.addCell(String.valueOf(item.getSoLuong())).setTextAlignment(TextAlignment.CENTER);
            table.addCell(formatVNDTable(item.getDonGia())).setTextAlignment(TextAlignment.RIGHT);
            table.addCell(formatVNDTable(item.getThanhTienTra())).setTextAlignment(TextAlignment.RIGHT);
            tongTra += item.getThanhTienTra();
        }
        document.add(table);

        document.add(new Paragraph("\nTONG TIEN TRA LAI: " + formatVNDTable(tongTra))
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph("\n\nNguoi lap phieu").setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph(lblTenNhanVienValue.getText()).setTextAlignment(TextAlignment.RIGHT).setItalic());
        document.close();
    }

    private void addHeader(Table table, String text) {
        table.addHeaderCell(new Cell()
                .add(new Paragraph(text).setBold())
                .setBackgroundColor(new DeviceRgb(230, 230, 230))
                .setTextAlignment(TextAlignment.CENTER));
    }

    private String formatVNDTable(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(Math.round(Math.max(0, amount))) + " VND";
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}

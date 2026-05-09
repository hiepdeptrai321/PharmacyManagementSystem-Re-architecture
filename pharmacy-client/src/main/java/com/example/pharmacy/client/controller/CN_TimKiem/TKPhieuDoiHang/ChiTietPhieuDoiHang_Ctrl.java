package com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuDoiHang;

import com.example.pharmacy.client.TienIch.DoiNgay;
import com.example.pharmacy.common.model.ChiTietPhieuDoiHangDto;
import com.example.pharmacy.common.model.PhieuDoiHangDto;
import com.example.pharmacy.client.service.DoiTraService;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
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
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import static com.example.pharmacy.client.TienIch.TuyChinhAlert.hien;

public class ChiTietPhieuDoiHang_Ctrl extends Application {
    private static final String FONT_PATH = "C:/Windows/Fonts/arial.ttf";

    public Button btnDong;
    public Button btnInPhieuDoi;
    public TableColumn<ChiTietPhieuDoiHangDto, String> colLyDo;
    public TableColumn<ChiTietPhieuDoiHangDto, String> colSTT;
    public TableColumn<ChiTietPhieuDoiHangDto, String> colSoLuong;
    public TableColumn<ChiTietPhieuDoiHangDto, String> colDonVi;
    public TableColumn<ChiTietPhieuDoiHangDto, String> colTenSP;
    public Label lblGhiChuValue;
    public Label lblMaPhieuDoiValue;
    public Label lblNgayLapValue;
    public Label lblSDTKhachHangValue;
    public Label lblTenKhachHangValue;
    public Label lblTenNhanVienValue;
    public TableView<ChiTietPhieuDoiHangDto> tblChiTietPhieuDoi;

    private final DoiTraService doiTraService = new DoiTraService();
    private PhieuDoiHangDto phieuDoiHang;
    private boolean initialized;

    @Override
    public void start(Stage stage) throws Exception {
        new com.example.pharmacy.client.view.CN_TimKiem.TKPhieuDoi.ChiTietPhieuDoiHang_GUI()
                .showWithController(stage, this);
    }

    public void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        btnDong.setOnAction(e -> ((Stage) btnDong.getScene().getWindow()).close());
        Platform.runLater(() -> {
            // no-op, keeps parity with old timing-sensitive init
        });
        btnInPhieuDoi.setOnAction(e -> xuLyXuatPDFDoiHang());
    }

    public void setPhieuDoiHang(PhieuDoiHangDto phieuDoiHang) {
        this.phieuDoiHang = phieuDoiHang;
        hienThiThongTin();
    }

    private void hienThiThongTin() {
        if (phieuDoiHang == null) {
            return;
        }

        lblMaPhieuDoiValue.setText(safe(phieuDoiHang.getMaPD()));
        lblNgayLapValue.setText(phieuDoiHang.getNgayLap() == null ? "" : DoiNgay.dinhDangThoiGian(phieuDoiHang.getNgayLap()));
        lblTenNhanVienValue.setText(phieuDoiHang.getNhanVien() == null ? "" : safe(phieuDoiHang.getNhanVien().getTenNV()));
        lblTenKhachHangValue.setText(phieuDoiHang.getKhachHang() == null ? "Khach le" : safe(phieuDoiHang.getKhachHang().getTenKH()));
        lblSDTKhachHangValue.setText(phieuDoiHang.getKhachHang() == null ? "" : safe(phieuDoiHang.getKhachHang().getSdt()));
        lblGhiChuValue.setText(safe(phieuDoiHang.getGhiChu()));

        List<ChiTietPhieuDoiHangDto> details = doiTraService.findChiTietPhieuDoiByMaPD(phieuDoiHang.getMaPD());
        tblChiTietPhieuDoi.getItems().setAll(details);

        colSTT.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(tblChiTietPhieuDoi.getItems().indexOf(cd.getValue()) + 1)));
        colTenSP.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getThuoc() == null ? "" : safe(cd.getValue().getThuoc().getTenThuoc())
        ));
        colSoLuong.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(cd.getValue().getSoLuong())));
        colDonVi.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getDvt() == null ? "" : safe(cd.getValue().getDvt().getTenDonViTinh())
        ));
        colLyDo.setCellValueFactory(cd -> new SimpleStringProperty(safe(cd.getValue().getLyDoDoi())));
    }

    public void xuLyXuatPDFDoiHang() {
        if (phieuDoiHang == null || tblChiTietPhieuDoi.getItems().isEmpty()) {
            hien(Alert.AlertType.INFORMATION, "Khong co du lieu", "Khong co phieu doi hang de xuat PDF.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Luu Phieu Doi Hang PDF");
        chooser.setInitialFileName("PhieuDoi_" + phieuDoiHang.getMaPD() + "_" + java.time.LocalDate.now());
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));

        Stage stage = (Stage) btnInPhieuDoi.getScene().getWindow();
        File file = chooser.showSaveDialog(stage);
        if (file == null) {
            return;
        }

        try {
            xuatPhieuDoiPDF(file);
            hien(Alert.AlertType.INFORMATION, "Thanh cong", "Xuat PDF phieu doi hang thanh cong!");
        } catch (Exception exception) {
            exception.printStackTrace();
            hien(Alert.AlertType.ERROR, "Loi", "Xuat PDF that bai: " + exception.getMessage());
        }
    }

    private void xuatPhieuDoiPDF(File file) throws Exception {
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FONT_PATH, PdfEncodings.IDENTITY_H);
        } catch (Exception exception) {
            font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        }
        document.setFont(font);

        try (InputStream is = getClass().getResourceAsStream("/com/example/pharmacy/client/img/logo.png")) {
            if (is != null) {
                Image logo = new Image(ImageDataFactory.create(is.readAllBytes()));
                logo.scaleToFit(120, 120);
                logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                document.add(logo);
            }
        }

        document.add(new Paragraph("QUOC KHANH PHARMACY").setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("PHIEU DOI HANG").setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Ma phieu doi: " + safe(phieuDoiHang.getMaPD())).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Ngay lap: " + lblNgayLapValue.getText()).setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\nTHONG TIN KHACH HANG").setBold());
        document.add(new Paragraph("Khach hang: " + lblTenKhachHangValue.getText()));
        document.add(new Paragraph("SDT: " + lblSDTKhachHangValue.getText()));
        document.add(new Paragraph("Nhan vien lap: " + lblTenNhanVienValue.getText()));

        document.add(new Paragraph("\nCHI TIET DOI HANG").setBold());
        float[] widths = {1, 4, 2, 2, 3};
        Table table = new Table(UnitValue.createPercentArray(widths)).setWidth(UnitValue.createPercentValue(100));
        addHeader(table, "STT");
        addHeader(table, "Ten thuoc");
        addHeader(table, "So luong");
        addHeader(table, "Don vi");
        addHeader(table, "Ly do doi");

        int stt = 1;
        for (ChiTietPhieuDoiHangDto item : tblChiTietPhieuDoi.getItems()) {
            table.addCell(String.valueOf(stt++));
            table.addCell(item.getThuoc() == null ? "" : safe(item.getThuoc().getTenThuoc()));
            table.addCell(String.valueOf(item.getSoLuong())).setTextAlignment(TextAlignment.CENTER);
            table.addCell(item.getDvt() == null ? "" : safe(item.getDvt().getTenDonViTinh())).setTextAlignment(TextAlignment.CENTER);
            table.addCell(safe(item.getLyDoDoi()));
        }

        document.add(table);
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

    private String safe(String value) {
        return value == null ? "" : value;
    }
}

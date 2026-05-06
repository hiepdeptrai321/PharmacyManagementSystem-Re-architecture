package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon;

import com.example.pharmacymanagementsystem_qlht.dao.*;
import com.example.pharmacymanagementsystem_qlht.model.*;
import com.example.pharmacymanagementsystem_qlht.session.SessionContext;
import com.example.pharmacymanagementsystem_qlht.session.UserContext;
import com.example.pharmacymanagementsystem_qlht.service.ApDungKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.service.DichVuKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoaDon.ChiTietHoaDon_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import com.itextpdf.layout.element.Image; // Cho logo
import com.itextpdf.io.image.ImageDataFactory; // Cho logo
import com.itextpdf.layout.properties.HorizontalAlignment; // Cho logo
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import static com.example.pharmacymanagementsystem_qlht.TienIch.TuyChinhAlert.hien;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class ChiTietHoaDon_Ctrl extends Application {
    public TableView<ChiTietHoaDon> tblChiTietHoaDon;
    public TableColumn<ChiTietHoaDon, Number> colNSTT;
    public TableColumn<ChiTietHoaDon, String> colNTen;
    public TableColumn<ChiTietHoaDon, Integer> colNSL;
    public TableColumn<ChiTietHoaDon, String> colNDonVi;
    public TableColumn<ChiTietHoaDon, Double> colNDonGia;
    public TableColumn<ChiTietHoaDon, Double> colNChietKhau;
    public TableColumn<ChiTietHoaDon, Double> colNThanhTien;
    public TableColumn<ChiTietHoaDon,String> colMaLoHang;
    public Label lblMaHoaDonValue;
    public Label lblNgayLapValue;
    public Label lblTenNhanVienValue;
    public Label lblTenKhachHangValue;
    public Label lblSDTKhachHangValue;
    public Label lblGhiChuValue;
    public Label lblTongTienHang;
    public Label lblGiamTheoSP;
    public Label lblGiamTheoHD;
    public Label lblVAT;
    public Label lblTongThanhToan;
    public static final String FONT_PATH = "C:/Windows/Fonts/arial.ttf";
    public Button btnDong;
    public Button btnInHoaDon;
    public Label lblLoaiHoaDon;
    public Label lblMaDonThuocTitle;
    public Label lblMaDonThuocValue;


    private HoaDon hoaDon;
    private final HoaDon_Dao hdDao = new HoaDon_Dao();
    private final ChiTietHoaDon_Dao cthdDao = new ChiTietHoaDon_Dao();
    private final ChiTietDonViTinh_Dao ctdvtDao = new ChiTietDonViTinh_Dao();
    //private final Map<String, String> baseUnitCache = new HashMap<>();
    private final Map<String, String> dvtCache = new HashMap<>(); // Dùng cache này để tra cứu DVT
    private final DonViTinh_Dao dvtDao = new DonViTinh_Dao();
    private final DichVuKhuyenMai kmService = new DichVuKhuyenMai();
    private final Thuoc_SanPham_Dao spDao = new Thuoc_SanPham_Dao();
    private final Map<String, String> tenSpCache = new HashMap<>();
    @Override
    public void start(Stage stage) throws Exception {
        ChiTietHoaDon_GUI gui = new ChiTietHoaDon_GUI();
        gui.showWithController(stage, this);
        initialize();
    }

    public void initialize() {
        btnDong.setOnAction(e -> ((Stage) btnDong.getScene().getWindow()).close());
        btnInHoaDon.setOnAction(e -> xuLyXuatPDF());
        Platform.runLater(()->{
            Stage dialog = (Stage) lblMaHoaDonValue.getScene().getWindow();
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        });
    }


    public void setHoaDon(HoaDon hd) {
        this.hoaDon = hd;
        hienThiThongTin();
    }


    private void hienThiThongTin() {
        if (hoaDon == null) return;

        if (lblMaHoaDonValue != null) lblMaHoaDonValue.setText(safeStr(hoaDon.getMaHD()));
        if (lblNgayLapValue != null) {
            if (hoaDon.getNgayLap() != null) {
                var fmt = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
                lblNgayLapValue.setText(fmt.format(hoaDon.getNgayLap()));
            } else lblNgayLapValue.setText("");
        }
        if (lblTenNhanVienValue != null)
            lblTenNhanVienValue.setText(hoaDon.getMaNV() != null ? safeStr(hoaDon.getMaNV().getTenNV()) : "");
        if (hoaDon.getMaKH() != null) {
            if (lblTenKhachHangValue != null) lblTenKhachHangValue.setText(safeStr(hoaDon.getMaKH().getTenKH()));
            if (lblSDTKhachHangValue != null) lblSDTKhachHangValue.setText(safeStr(hoaDon.getMaKH().getSdt()));
        } else {
            if (lblTenKhachHangValue != null) lblTenKhachHangValue.setText("Khách lẻ");
            if (lblSDTKhachHangValue != null) lblSDTKhachHangValue.setText("");
        }
        if (hoaDon.getLoaiHoaDon() != null) {
            boolean isETC = hoaDon.getLoaiHoaDon().equalsIgnoreCase("ETC");

            if (lblLoaiHoaDon != null) {
                lblLoaiHoaDon.setText(isETC ? "Hóa đơn kê đơn (ETC)" : "Hóa đơn không kê đơn (OTC)");
            }

            if (lblMaDonThuocTitle != null) {
                lblMaDonThuocTitle.setVisible(isETC);
                lblMaDonThuocTitle.setManaged(isETC);
            }
            if (lblMaDonThuocValue != null) {
                lblMaDonThuocValue.setVisible(isETC);
                lblMaDonThuocValue.setManaged(isETC);
                if (isETC) {
                    lblMaDonThuocValue.setText(safeStr(hoaDon.getMaDonThuoc()));
                }
            }
        } else {
            if (lblLoaiHoaDon != null) lblLoaiHoaDon.setText("Không kê đơn (OTC)");
            if (lblMaDonThuocTitle != null) lblMaDonThuocTitle.setVisible(false);
            if (lblMaDonThuocValue != null) lblMaDonThuocValue.setVisible(false);
        }
        if (lblGhiChuValue != null) lblGhiChuValue.setText("");

        List<ChiTietHoaDon> list = cthdDao.selectByMaHD(hoaDon.getMaHD());
        tblChiTietHoaDon.setItems(FXCollections.observableArrayList(list));

        if (colNSTT != null) {
            colNSTT.setCellValueFactory(cd ->
                    new ReadOnlyObjectWrapper<>(tblChiTietHoaDon.getItems().indexOf(cd.getValue()) + 1));
        }

        if (colNTen != null) {
            colNTen.setCellValueFactory(cel -> {
                ChiTietHoaDon row = cel.getValue();
                Thuoc_SP_TheoLo lo = row.getLoHang();
                String ten = (lo != null && lo.getThuoc() != null) ? safeStr(lo.getThuoc().getTenThuoc()) : "";
                String suffix = giftSuffix(row);
                return new SimpleStringProperty(ten + suffix);
            });
        }

        // === THÊM MỚI: Cột Mã Lô Hàng ===
        if (colMaLoHang != null) {
            colMaLoHang.setCellValueFactory(cel -> {
                ChiTietHoaDon row = cel.getValue();
                String maLo = "";
                if (row != null && row.getLoHang() != null) {
                    maLo = safeStr(row.getLoHang().getMaLH());
                }
                return new SimpleStringProperty(maLo);
            });
        }

        if (colNSL != null) {
            colNSL.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        }

        if (colNDonVi != null) {
            colNDonVi.setCellValueFactory(cel -> {
                String tenDVT = tenDonViGiaoDich(cel.getValue());
                return new SimpleStringProperty(tenDVT);
            });
        }

        if (colNDonGia != null) {
            colNDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
            colNDonGia.setCellFactory(tc -> new TableCell<>() {
                @Override protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : formatVNDTable(item == null ? 0 : item));
                    setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
                }
            });
        }

        if (colNChietKhau != null) {
            colNChietKhau.setCellValueFactory(new PropertyValueFactory<>("giamGia"));
            colNChietKhau.setCellFactory(tc -> new TableCell<>() {
                @Override protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : formatVNDTable(item == null ? 0 : item));
                    setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
                }
            });
        }

        if (colNThanhTien != null) {
            colNThanhTien.setCellValueFactory(cel -> {
                ChiTietHoaDon r = cel.getValue();
                double tt = Math.max(0, r.getSoLuong() * r.getDonGia() - r.getGiamGia());
                return new ReadOnlyObjectWrapper<>(tt);
            });
            colNThanhTien.setCellFactory(tc -> new TableCell<>() {
                @Override protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : formatVNDTable(item == null ? 0 : item));
                    setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
                }
            });
        }

        BigDecimal tongHang = BigDecimal.ZERO;
        BigDecimal giamTheoSp = BigDecimal.ZERO;
        for (ChiTietHoaDon r : list) {
            BigDecimal line = BigDecimal.valueOf(r.getDonGia()).multiply(BigDecimal.valueOf(r.getSoLuong()));
            tongHang = tongHang.add(line);
            giamTheoSp = giamTheoSp.add(BigDecimal.valueOf(Math.max(0, r.getGiamGia())));
        }

        BigDecimal baseTruocVAT = tongHang.subtract(giamTheoSp).max(BigDecimal.ZERO);
        BigDecimal giamTheoHoaDon = BigDecimal.ZERO;
        BigDecimal baseSauHD = baseTruocVAT.subtract(giamTheoHoaDon).max(BigDecimal.ZERO);
        BigDecimal vat = baseSauHD.multiply(new BigDecimal("0.05")).setScale(0, RoundingMode.HALF_UP);
        BigDecimal tongThanhToan = baseSauHD.add(vat);

        if (lblGiamTheoSP != null)      lblGiamTheoSP.setText(formatVNDLabel(giamTheoSp));
        if (lblTongTienHang != null)    lblTongTienHang.setText(formatVNDLabel(baseTruocVAT));
        if (lblGiamTheoHD != null)      lblGiamTheoHD.setText(formatVNDLabel(giamTheoHoaDon));
        if (lblVAT != null)             lblVAT.setText(formatVNDLabel(vat));
        if (lblTongThanhToan != null)   lblTongThanhToan.setText(formatVNDLabel(tongThanhToan));
    }


    private String tenDonViGiaoDich(ChiTietHoaDon cthd) {
        if (cthd == null || cthd.getDvt() == null) {
            return "";
        }

        DonViTinh dvt = cthd.getDvt();

        // 1. Ưu tiên lấy tên đã được DAO load sẵn (nếu có)
        if (dvt.getTenDonViTinh() != null && !dvt.getTenDonViTinh().isBlank()) {
            return dvt.getTenDonViTinh();
        }

        // 2. Nếu DAO chỉ load MaDVT, chúng ta dùng cache/DAO để tra cứu
        String maDVT = dvt.getMaDVT();
        if (maDVT == null || maDVT.isBlank()) {
            return ""; // Không có mã DVT
        }

        // 3. Kiểm tra cache
        if (dvtCache.containsKey(maDVT)) {
            return dvtCache.get(maDVT);
        }

        // 4. Tra cứu DAO (fallback nếu DAO ở Bước 2 chưa load hết)
        try {
            DonViTinh fullDVT = dvtDao.selectById(maDVT);
            if (fullDVT != null && fullDVT.getTenDonViTinh() != null) {
                String ten = fullDVT.getTenDonViTinh();
                dvtCache.put(maDVT, ten); // Lưu vào cache
                return ten;
            }
        } catch (Exception e) {
            // Lỗi tra cứu
            e.printStackTrace();
        }

        // 5. Nếu thất bại, trả về chính cái mã
        dvtCache.put(maDVT, maDVT);
        return maDVT;
    }
    private String giftSuffix(ChiTietHoaDon row) {
        if (row == null || row.getLoHang() == null || row.getLoHang().getThuoc() == null) return "";
        Thuoc_SanPham sp = row.getLoHang().getThuoc();
        String maThuoc = sp.getMaThuoc();
        if (maThuoc == null || maThuoc.isBlank()) return "";

        int soLuong = Math.max(0, row.getSoLuong());
        BigDecimal donGia = BigDecimal.valueOf(Math.max(0, row.getDonGia()));
        LocalDate ngay = hoaDon != null && hoaDon.getNgayLap() != null
                ? hoaDon.getNgayLap().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : LocalDate.now();

        ApDungKhuyenMai ap = kmService.apDungChoSP(maThuoc, soLuong, donGia, ngay);
        if (ap == null || ap.getFreeItems().isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        for (String maTang : ap.getFreeItems().keySet()) {
            String name = getTenSP(maTang);
            if (name != null && !name.isBlank()) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(name);
            }
        }
        return sb.length() > 0 ? " (có tặng kèm " + sb + ")" : "";
    }

    private String getTenSP(String ma) {
        if (ma == null) return null;
        if (tenSpCache.containsKey(ma)) return tenSpCache.get(ma);
        Thuoc_SanPham sp = spDao.selectById(ma);
        String ten = (sp != null && sp.getTenThuoc() != null) ? sp.getTenThuoc() : "";
        tenSpCache.put(ma, ten);
        return ten;
    }
    public static String formatVNDTable(double v) {
        DecimalFormat df = new DecimalFormat("#,##0");
        df.setGroupingUsed(true);
        return df.format(Math.max(0, Math.round(v))) + " đ";
    }

    public static String formatVNDLabel(BigDecimal v) {
        DecimalFormat df = new DecimalFormat("#,##0");
        df.setGroupingUsed(true);
        return df.format(v.max(BigDecimal.ZERO)) + " VND";
    }

    private static String formatVNDLabel(double v) {
        return formatVNDLabel(BigDecimal.valueOf(Math.max(0, Math.round(v))));
    }

    private static String safeStr(String s) { return s == null ? "" : s; }



    public void xuLyXuatPDF() {
        // 1. Kiểm tra dữ liệu
        if (hoaDon == null || tblChiTietHoaDon.getItems() == null || tblChiTietHoaDon.getItems().isEmpty()) {
            hien(INFORMATION, "Không có dữ liệu", "Không có hóa đơn hoặc chi tiết hóa đơn để in.");
            return;
        }

        // 2. Mở FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lưu Hóa Đơn PDF");
        fileChooser.setInitialFileName("HoaDon_" + hoaDon.getMaHD() + "_" + LocalDate.now());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));

        Stage stage = (Stage) btnInHoaDon.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        // 3. Gọi hàm xuất PDF
        if (file != null) {
            try {
                xuatHoaDonPDF(file);
                hien(INFORMATION, "Thành công", "Xuất file PDF hóa đơn thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                hien(ERROR, "Lỗi", "Có lỗi xảy ra khi xuất file PDF: " + e.getMessage());
            }
        }
    }

    private void xuatHoaDonPDF(File file) throws IOException {
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // 1. Thiết lập Font
        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FONT_PATH);
        } catch (IOException e) {
            System.err.println("Không tìm thấy font tại: " + FONT_PATH + ". Sử dụng font mặc định.");
            font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        }
        document.setFont(font);

        // 2. Header (Logo + Thông tin nhà thuốc)
        try {
            String logoPath = "/com/example/pharmacymanagementsystem_qlht/img/logo.png";
            InputStream is = getClass().getResourceAsStream(logoPath);
            if (is != null) {
                byte[] bytes = is.readAllBytes();
                com.itextpdf.io.image.ImageData imageData = com.itextpdf.io.image.ImageDataFactory.create(bytes);
                com.itextpdf.layout.element.Image logo = new com.itextpdf.layout.element.Image(imageData);
                logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                logo.scaleToFit(120f, 120f);
                document.add(logo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        document.add(new Paragraph("Quốc Khánh Pharmacy")
                .setFontSize(18).setBold().setTextAlignment(TextAlignment.CENTER).setMarginTop(5));
        document.add(new Paragraph("Địa chỉ: Số 12 Đường Nguyễn Văn Bảo, Phường 4, Gò Vấp, TP Hồ Chí Minh")
                .setFontSize(10).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Hotline: 1800 6868")
                .setFontSize(10).setTextAlignment(TextAlignment.CENTER));

        // 3. Tiêu đề Hóa đơn và Thông tin
        document.add(new Paragraph("HOÁ ĐƠN BÁN LẺ")
                .setFontSize(18).setBold().setTextAlignment(TextAlignment.CENTER).setMarginTop(15));

        document.add(new Paragraph("Mã hóa đơn: " + lblMaHoaDonValue.getText())
                .setTextAlignment(TextAlignment.CENTER).setFontSize(10));
        document.add(new Paragraph("Ngày lập: " + lblNgayLapValue.getText())
                .setTextAlignment(TextAlignment.CENTER).setFontSize(10));

        // 4. Mã đơn thuốc (NẾU CÓ)
        if (hoaDon.getLoaiHoaDon() != null && hoaDon.getLoaiHoaDon().equalsIgnoreCase("ETC")) {
            String maDonThuoc = lblMaDonThuocValue.getText();
            if (maDonThuoc != null && !maDonThuoc.isBlank()) {
                document.add(new Paragraph("Mã đơn thuốc: " + maDonThuoc)
                        .setTextAlignment(TextAlignment.CENTER).setFontSize(10));
            }
        }

        // 5. Thông tin khách hàng và nhân viên
        document.add(new Paragraph("THÔNG TIN GIAO DỊCH")
                .setFontSize(14).setBold().setMarginTop(15));
        document.add(new Paragraph("Khách hàng: " + lblTenKhachHangValue.getText()));
        document.add(new Paragraph("Số điện thoại: " + lblSDTKhachHangValue.getText()));
        document.add(new Paragraph("Nhân viên: " + lblTenNhanVienValue.getText()));

        String tenNhanVienIn = "Không rõ";
        UserContext currentUser = SessionContext.getCurrentUser();
        if (currentUser != null && currentUser.getFullName() != null) {
            tenNhanVienIn = currentUser.getFullName();
        }

        // 6. Bảng chi tiết sản phẩm - THÊM CỘT MÃ LÔ
        document.add(new Paragraph("DANH SÁCH SẢN PHẨM")
                .setFontSize(14).setBold().setMarginTop(15));

        // Điều chỉnh số cột từ 7 → 8 và width tương ứng
        float[] columnWidths = {0.8f, 4.5f, 2f, 1.2f, 1.5f, 2f, 2f, 2.5f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        // Headers của bảng - THÊM CỘT MÃ LÔ
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("STT").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Tên sản phẩm").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Mã lô").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("SL").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("ĐVT").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Đơn giá").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Giảm giá").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Thành tiền").setBold()));

        // Dữ liệu bảng - THÊM DỮ LIỆU MÃ LÔ
        int stt = 1;
        for (ChiTietHoaDon cthd : tblChiTietHoaDon.getItems()) {
            String tenSP = "";
            String maLo = "";
            if (cthd.getLoHang() != null) {
                if (cthd.getLoHang().getThuoc() != null) {
                    tenSP = safeStr(cthd.getLoHang().getThuoc().getTenThuoc()) + giftSuffix(cthd);
                }
                maLo = safeStr(cthd.getLoHang().getMaLH());
            }

            int soLuong = cthd.getSoLuong();
            String donVi = tenDonViGiaoDich(cthd);
            double donGia = cthd.getDonGia();
            double chietKhau = cthd.getGiamGia();
            double thanhTien = Math.max(0, (soLuong * donGia) - chietKhau);

            table.addCell(String.valueOf(stt++));
            table.addCell(tenSP);
            table.addCell(maLo).setTextAlignment(TextAlignment.CENTER);
            table.addCell(String.valueOf(soLuong)).setTextAlignment(TextAlignment.CENTER);
            table.addCell(donVi).setTextAlignment(TextAlignment.CENTER);
            table.addCell(formatVNDTable(donGia)).setTextAlignment(TextAlignment.RIGHT);
            table.addCell(formatVNDTable(chietKhau)).setTextAlignment(TextAlignment.RIGHT);
            table.addCell(formatVNDTable(thanhTien)).setTextAlignment(TextAlignment.RIGHT);
        }
        document.add(table);

        // 7. Tổng kết
        document.add(new Paragraph("Tổng tiền hàng: " + lblTongTienHang.getText().replace(" VND", " đ"))
                .setTextAlignment(TextAlignment.RIGHT).setMarginTop(10));
        document.add(new Paragraph("Tổng giảm giá SP: " + lblGiamTheoSP.getText().replace(" VND", " đ"))
                .setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("Giảm giá theo HĐ: " + lblGiamTheoHD.getText().replace(" VND", " đ"))
                .setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("Thuế (VAT 5%): " + lblVAT.getText().replace(" VND", " đ"))
                .setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("TỔNG THANH TOÁN: " + lblTongThanhToan.getText().replace(" VND", " đ"))
                .setTextAlignment(TextAlignment.RIGHT).setBold().setFontSize(14));

        // 8. Đóng file
        document.close();
    }



}

package com.example.pharmacymanagementsystem_qlht.controller;

import com.example.pharmacymanagementsystem_qlht.TienIch.VNDFormatter;
import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapHoaDon.LapHoaDon_Ctrl;
import com.example.pharmacymanagementsystem_qlht.dao.ThongKe_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SP_TheoLo_Dao;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeBanHang;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.session.SessionContext;
import com.example.pharmacymanagementsystem_qlht.session.UserContext;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapHoaDon.LapHoaDon_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CuaSoChinh_QuanLy_GUI;
import com.example.pharmacymanagementsystem_qlht.view.DangNhap_GUI;
import com.example.pharmacymanagementsystem_qlht.view.TrangChu_GUI;
import com.example.pharmacymanagementsystem_qlht.view.ViewEmbedder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CuaSoChinh_NhanVien_Ctrl{

    //  1. KHAI BÁO THÀNH PHẦN GIAO DIỆN (FXML)
    public static CuaSoChinh_NhanVien_Ctrl instance;
    public Pane pnlChung;
    public Menu menuTimKiem;
    public Menu menuDanhMuc;
    public Menu menuCapNhat;
    public Menu menuThongKe;
    public Menu menuXuLy;
    public Label txtNguoiDung;
    public Label txtNgayThangNam;
    public TableView<Thuoc_SP_TheoLo> tblThuocHetHan;
    public TableColumn<Thuoc_SP_TheoLo, String> colMaThuocHetHan;
    public TableColumn<Thuoc_SP_TheoLo, String> colLoHangHetHan;
    public TableColumn<Thuoc_SP_TheoLo, java.sql.Date> colHSDHetHan;
    public TableView<Thuoc_SP_TheoLo> tblThuocSapHetHan;
    public TableColumn<Thuoc_SP_TheoLo, String> colMaThuocSapHetHan;
    public TableColumn<Thuoc_SP_TheoLo, String> colLoHangSapHetHan;
    public TableColumn<Thuoc_SP_TheoLo, java.sql.Date> colHSDSapHetHan;
    public Label lbl_SoLuongHangHetHan;
    public Label lbl_SoLuongHangSapHetHan;
    public LineChart chartDoanhThuThangNay;
    public Label lblDoanhThuThangTruoc;
    public Label lblDoanhThuThangNay;
    public Label lblHoaDonThangTruoc;
    public Label lblHoaDonThangNay;
    public Pane pnlThongTin;
    public Pane pnlNguoiDung;
    public TextField txtVaiTroNhanVien;
    public Label lblVaiTro;
    private Parent lapHoaDonRoot;
    private LapHoaDon_Ctrl lapHoaDonCtrl;

    //  2. KHAI BÁO BIẾN TOÀN CỤC
    private int viTri;
    private final List<Thuoc_SP_TheoLo> listThuocHetHan = new Thuoc_SP_TheoLo_Dao().selectHangDaHetHan();
    private final List<Thuoc_SP_TheoLo> listThuocSapHetHan = new Thuoc_SP_TheoLo_Dao().selectHangSapHetHan();

    // Cache các Node đã embed để chuyển tab nhanh
    private final Map<String, Parent> cacheViews = new HashMap<>();

    public static CuaSoChinh_NhanVien_Ctrl instanceNV;

    //  3. HÀM KHỞI TẠO
    public void initialize() {
        instanceNV = this;
        instance = this;
        setNgayGio(txtNgayThangNam);
        loadTableThuocHetHan();
        loadTableThuocSapHetHan();
        setThongKeLabelsAndData();
        setupGlobalShortcuts();
        UserContext currentUser = SessionContext.getCurrentUser();
        if (currentUser != null) {
            txtNguoiDung.setText("Người dùng: " + currentUser.getFullName());
        }
        pnlThongTin.setVisible(false);
    }

    // ----------------------- HẠ TẦNG EMBED -----------------------
    // trong CuaSoChinh_QuanLy_Ctrl
    private Node currentEmbedded;

    //  Hàm hiển thị nội dung vào pane chính
    private void showInMainPane(Parent content) {
        if (pnlChung == null || content == null) return;

        // clear cũ
        pnlChung.getChildren().clear();

        // ràng buộc kích thước để không bị 0x0
        if (content instanceof Region r) {
            r.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            r.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            r.prefWidthProperty().bind(pnlChung.widthProperty());
            r.prefHeightProperty().bind(pnlChung.heightProperty());
        } else {
            // fallback cho Node không phải Region
            content.resizeRelocate(0, 0, pnlChung.getWidth(), pnlChung.getHeight());
            pnlChung.widthProperty().addListener((o, ov, nv) ->
                    content.resizeRelocate(0, 0, nv.doubleValue(), pnlChung.getHeight()));
            pnlChung.heightProperty().addListener((o, ov, nv) ->
                    content.resizeRelocate(0, 0, pnlChung.getWidth(), nv.doubleValue()));
        }

        pnlChung.getChildren().add(content);
        currentEmbedded = content;

        // ép CSS + layout ngay để thấy liền, không cần chuyển tab mới vẽ
        pnlChung.applyCss();
        pnlChung.layout();
    }

    //  Hàm load view vào pane chính
    private void loadViewEmbedded(int menuIndex, String cacheKey, Object gui, Object ctrl) {
        viTri = menuIndex;
        selectMenu(viTri);
//        Parent root = cacheViews.computeIfAbsent(cacheKey, k ->
//                ViewEmbedder.buildFromShowWithController(gui, ctrl));
        Parent root = ViewEmbedder.buildFromShowWithController(gui, ctrl);
        showInMainPane(root);
        pnlThongTin.setVisible(false);
        pnlChung.requestFocus();
    }

    // ----------------------- BẢNG HẾT HẠN / THỐNG KÊ -----------------------
//  Hàm tải dữ liệu vào bảng thuốc hết hạn
    public void loadTableThuocHetHan() {
        ObservableList<Thuoc_SP_TheoLo> data = tblThuocHetHan.getItems();
        if (data == null) {
            data = FXCollections.observableArrayList();
            tblThuocHetHan.setItems(data);
        }
        data.setAll(listThuocHetHan);

        lbl_SoLuongHangHetHan.setText("Số lượng lô hàng hết hạn: " + listThuocHetHan.size());

        colMaThuocHetHan.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getThuoc().getMaThuoc()));
        colLoHangHetHan.setCellValueFactory(new PropertyValueFactory<>("maLH"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colHSDHetHan.setCellValueFactory(new PropertyValueFactory<>("hsd"));
        colHSDHetHan.setCellFactory(column -> new TableCell<>() {
            @Override protected void updateItem(java.sql.Date item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dateFormatter.format(item.toLocalDate()));
            }
        });
    }

    //  Hàm load dữ liệu thuốc sắp hết hạn
    public void loadTableThuocSapHetHan() {
        ObservableList<Thuoc_SP_TheoLo> data = tblThuocSapHetHan.getItems();
        if (data == null) {
            data = FXCollections.observableArrayList();
            tblThuocSapHetHan.setItems(data);
        }
        data.setAll(listThuocSapHetHan);

        lbl_SoLuongHangSapHetHan.setText("Số lượng lô hàng sắp hết hạn: " + listThuocSapHetHan.size());

        colMaThuocSapHetHan.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getThuoc().getMaThuoc()));
        colLoHangSapHetHan.setCellValueFactory(new PropertyValueFactory<>("maLH"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colHSDSapHetHan.setCellValueFactory(new PropertyValueFactory<>("hsd"));
        colHSDSapHetHan.setCellFactory(column -> new TableCell<>() {
            @Override protected void updateItem(java.sql.Date item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dateFormatter.format(item.toLocalDate()));
            }
        });
    }

    //  Hàm thiết lập ngày giờ hiện tại lên nhãn
    private void setNgayGio(Label lblNgayGio) {
        var localeVN = new java.util.Locale("vi", "VN");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy HH:mm:ss", localeVN);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    LocalDateTime now = LocalDateTime.now();
                    lblNgayGio.setText(now.format(formatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    //  Hàm thiết lập dữ liệu và nhãn cho phần thống kê
    private void setThongKeLabelsAndData() {
        if (lblHoaDonThangNay == null || lblHoaDonThangTruoc == null
                || lblDoanhThuThangNay == null || lblDoanhThuThangTruoc == null
                || chartDoanhThuThangNay == null) return;

        ThongKe_Dao tkDao = new ThongKe_Dao();
        LocalDate now = LocalDate.now();

        LocalDate startThis = now.withDayOfMonth(1);
        LocalDate endThis = now.withDayOfMonth(now.lengthOfMonth());
        LocalDate startPrev = startThis.minusMonths(1);
        LocalDate endPrev = startThis.minusDays(1);

        List<ThongKeBanHang> dataThis = tkDao.getThongKeBanHang_TuyChon(startThis, endThis);
        List<ThongKeBanHang> dataPrev = tkDao.getThongKeBanHang_TuyChon(startPrev, endPrev);

        int invoicesThis = dataThis.stream().mapToInt(ThongKeBanHang::getSoLuongHoaDon).sum();
        double revenueThis = dataThis.stream().mapToDouble(ThongKeBanHang::getDoanhThu).sum();

        int invoicesPrev = dataPrev.stream().mapToInt(ThongKeBanHang::getSoLuongHoaDon).sum();
        double revenuePrev = dataPrev.stream().mapToDouble(ThongKeBanHang::getDoanhThu).sum();

        VNDFormatter vndFormatter = new VNDFormatter();
        lblHoaDonThangNay.setText(invoicesThis + " Hóa đơn");
        lblHoaDonThangTruoc.setText(invoicesPrev + " Hóa đơn");
        lblDoanhThuThangNay.setText(vndFormatter.format(revenueThis));
        lblDoanhThuThangTruoc.setText(vndFormatter.format(revenuePrev));

        chartDoanhThuThangNay.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Doanh thu");
        chartDoanhThuThangNay.setLegendVisible(false);
        for (ThongKeBanHang tk : dataThis) {
            String label = tk.getThoiGian() == null ? "" : tk.getThoiGian();
            series.getData().add(new XYChart.Data<>(label, tk.getDoanhThu()));
        }
        chartDoanhThuThangNay.getData().add(series);
        chartDoanhThuThangNay.setAnimated(false);
    }

    private boolean viewWired = false;

    private boolean menusReady() {
        return menuTimKiem != null && menuDanhMuc != null
                && menuCapNhat != null && menuThongKe != null && menuXuLy != null;
    }

    public void markViewWired() {  // gọi sau khi GUI gán xong control
        this.viewWired = true;
    }


    //  Hàm chọn menu để thay đổi màu thanh menu khi chọn
    public void selectMenu(int viTriGiaoDien) {
        if (!viewWired || !menusReady()) {
            System.out.println("selectMenu() skipped: view not wired yet");
            return;
        }
        switch (viTriGiaoDien) {
            case 0 -> {
                menuTimKiem.setStyle("-fx-background-color: #1e90ff");
                menuDanhMuc.setStyle("-fx-background-color: #1e90ff");
                menuCapNhat.setStyle("-fx-background-color: #1e90ff");
                menuThongKe.setStyle("-fx-background-color: #1e90ff");
                menuXuLy.setStyle("-fx-background-color: #1e90ff");
            }
            case 1 -> {
                menuTimKiem.setStyle("-fx-background-color: #003cff");
                menuDanhMuc.setStyle("-fx-background-color: #1e90ff");
                menuCapNhat.setStyle("-fx-background-color: #1e90ff");
                menuThongKe.setStyle("-fx-background-color: #1e90ff");
                menuXuLy.setStyle("-fx-background-color: #1e90ff");
            }
            case 2 -> {
                menuTimKiem.setStyle("-fx-background-color: #1e90ff");
                menuDanhMuc.setStyle("-fx-background-color: #003cff");
                menuCapNhat.setStyle("-fx-background-color: #1e90ff");
                menuThongKe.setStyle("-fx-background-color: #1e90ff");
                menuXuLy.setStyle("-fx-background-color: #1e90ff");
            }
            case 3 -> {
                menuTimKiem.setStyle("-fx-background-color: #1e90ff");
                menuDanhMuc.setStyle("-fx-background-color: #1e90ff");
                menuCapNhat.setStyle("-fx-background-color: #003cff");
                menuThongKe.setStyle("-fx-background-color: #1e90ff");
                menuXuLy.setStyle("-fx-background-color: #1e90ff");
            }
            case 4 -> {
                menuTimKiem.setStyle("-fx-background-color: #1e90ff");
                menuDanhMuc.setStyle("-fx-background-color: #1e90ff");
                menuCapNhat.setStyle("-fx-background-color: #1e90ff");
                menuThongKe.setStyle("-fx-background-color: #003cff");
                menuXuLy.setStyle("-fx-background-color: #1e90ff");
            }
            case 5 -> {
                menuTimKiem.setStyle("-fx-background-color: #1e90ff");
                menuDanhMuc.setStyle("-fx-background-color: #1e90ff");
                menuCapNhat.setStyle("-fx-background-color: #1e90ff");
                menuThongKe.setStyle("-fx-background-color: #1e90ff");
                menuXuLy.setStyle("-fx-background-color: #003cff");
            }
        }
    }

    //  5. CÁC HÀM XỬ LÝ SỰ KIỆN —> EMBED GUI SẴN CÓ
    public void AnhChuyenTrangChu(MouseEvent e) {
        viTri = 0;
        selectMenu(viTri);

        Parent root = ViewEmbedder.buildFromShowWithController(
                new TrangChu_GUI(),
                new TrangChu_Ctrl()
        );

        showInMainPane(root);
    }

    //  5.1.Chức năng tìm kiếm
    public void timKiemHoaDon(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_HOADON",
                new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoaDon.TKHoaDon_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.TimKiemHoaDon_Ctrl());
    }

    public void timKiemPhieuNhap(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_PN",
                new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuNhapHang.TKPhieuNhapHang_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuNhapHang.TimKiemPhieuNhap_Ctrl());
    }

    public void timKiemPhieuDoiHang(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_PDOI",
                new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi.TKPhieuDoiHang_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang.TKPhieuDoiHang_Ctrl());
    }

    public void timKiemPhieuTraHang(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_PTRA",
                new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuTra.TKPhieuTraHang_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang.TKPhieuTraHang_Ctrl());
    }

    public void timKiemPhieuDatHang(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_PDAT",
                new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDatHang.TKPhieuDatHang_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDatHang.TKPhieuDatHang_Ctrl());
    }

    public void timKiemNhaCungCap(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_NCC",
                new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap.TimKiemNCC_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKNhaCungCap.TimKiemNCC_Ctrl());
    }

    public void timKiemThuoc(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_THUOC",
                new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKThuoc.TKThuoc_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKThuoc.TimKiemThuoc_Ctrl());
    }

    public void timKiemThuocTrongKho(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_THUOCTRONGKHO",
                new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKThuocTrongKho.TKThuocTrongKho_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKThuocTrongKho.TimKiemThuocTrongKho_Ctrl());
    }

    public void timKiemKhachHang(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_KH",
                new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKKhachHang.TimKiemKhachHang_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKKhachHang.TimKiemKhachHang_Ctrl());
    }

    public void timKiemHoatDong(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_HD",
                new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKHoatDong.TKHoatDong_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoatDong.TKHoatDong_Ctrl());
    }

    //  5.2.Chức năng danh mục
    public void danhMucThuoc(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_THUOC",
                new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMThuoc.DanhMucThuoc_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.DanhMucThuoc_Ctrl());
    }

    public void danhMucNhanVien(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_NV",
                new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_Ctrl());
    }

    public void danhMucKhachHang(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_KH",
                new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKhachHang.DanhMucKhachHang_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhachHang.DanhMucKhachHang_Ctrl());
    }

    public void danhMucKeHang(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_KE",
                new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.DanhMucKeHang_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang.DanhMucKeHang_Ctrl());
    }

    public void danhMucNhaCungCap(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_NCC",
                new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC.DanhMucNhaCungCap_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap.DanhMucNhaCungCap_Ctrl());
    }

    public void danhMucKhuyenMai(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_KM",
                new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKhuyenMai.DanhMucKhuyenMai_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhuyenMai.DanhMucKhuyenMai_Ctrl());
    }

    public void danhMucNhomDuocLy(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_NDL",
                new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy.DanhMucNhomDuocLy_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy.DanhMucNhomDuocLy_Ctrl());
    }

    //  5.3.Chức năng cập nhật
    public void CapNhatGiaBan(ActionEvent actionEvent) {
        loadViewEmbedded(3, "CN_GIA",
                new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatGia.CapNhatGiaThuoc_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatGia.CapNhatGiaThuoc_Ctrl());
    }

    public void capNhatTonKho(ActionEvent actionEvent) {
        loadViewEmbedded(3, "CN_TON",
                new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatSoLuong.CapNhatSoLuongThuoc_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatSoLuong.CapNhatSoLuongThuoc_Ctrl());
    }

    public void capNhatKhuyenMai(ActionEvent actionEvent) {
        loadViewEmbedded(3, "CN_KM",
                new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_Ctrl());
    }

    //  5.4.Chức năng thống kê và báo cáo
    public void thongKeDoanhThu(ActionEvent actionEvent) {
        loadViewEmbedded(4, "TK_DT",
                new com.example.pharmacymanagementsystem_qlht.view.CN_ThongKe.ThongKeBanHang_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeBanHang_Ctrl());
    }

    public void thongKeXuatNhap(ActionEvent actionEvent) {
        loadViewEmbedded(4, "TK_XNT",
                new com.example.pharmacymanagementsystem_qlht.view.CN_ThongKe.ThongKeXNT_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeXNT_Ctrl());
    }

    //  5.5.Chức năng xử lý
    public void lapHoaDon(ActionEvent actionEvent) {
        loadViewEmbedded(5, "XL_HD",
                new com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapHoaDon.LapHoaDon_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapHoaDon.LapHoaDon_Ctrl());
    }

    public void lapPhieuDoiHang(ActionEvent actionEvent) {
        loadViewEmbedded(5, "XL_PDOI",
                new com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuDoi.LapPhieuDoi_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuDoi.LapPhieuDoiHang_Ctrl());
    }

    public void lapPhieuTraHang(ActionEvent actionEvent) {
        loadViewEmbedded(5, "XL_PTRA",
                new com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuTra.LapPhieuTra_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuTra.LapPhieuTraHang_Ctrl());
    }

    public void lapPhieuDatHang(ActionEvent actionEvent) {
        loadViewEmbedded(5, "XL_PDAT",
                new com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuDat.LapPhieuDat_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuDatHang.LapPhieuDatHang_Ctrl());
    }

    public void nhapHang(ActionEvent actionEvent) {
        loadViewEmbedded(5, "XL_PNHAP",
                new com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuNhapHang.LapPhieuNhapHang_GUI(),
                new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang.LapPhieuNhapHang_Ctrl());
    }

    private void addShortcut(Scene scene, KeyCodeCombination keyCombination, Runnable action) {
        scene.getAccelerators().put(keyCombination, action);
    }

    //  Hàm thiết lập phím tắt toàn cục (đã đổi sang embed)
    private void setupGlobalShortcuts() {
        Platform.runLater(() -> {
            Scene scene = pnlChung.getScene();
            if (scene == null) {
                System.err.println("Không thể lấy Scene để gán phím tắt.");
                return;
            }

            addShortcut(scene, new KeyCodeCombination(KeyCode.F1), () -> AnhChuyenTrangChu(null));
            addShortcut(scene, new KeyCodeCombination(KeyCode.F2), () -> lapHoaDon(null));
            addShortcut(scene, new KeyCodeCombination(KeyCode.F3), () -> lapPhieuDatHang(null));
            addShortcut(scene, new KeyCodeCombination(KeyCode.F4), () -> nhapHang(null));
            addShortcut(scene, new KeyCodeCombination(KeyCode.F5), () -> timKiemKhachHang(null));
            addShortcut(scene, new KeyCodeCombination(KeyCode.F6), () -> timKiemThuoc(null));
            addShortcut(scene, new KeyCodeCombination(KeyCode.F7), () -> CapNhatGiaBan(null));
            addShortcut(scene, new KeyCodeCombination(KeyCode.F8), () -> capNhatTonKho(null));
        });
        pnlChung.requestFocus();
    }

    //  Hàm xử lý sự kiện khi click vào panel người dùng
    public void pnlNguoiDungClick(MouseEvent mouseEvent) {
        pnlThongTin.setVisible(!pnlThongTin.isVisible());
        UserContext currentUser = SessionContext.getCurrentUser();
        lblVaiTro.setText(currentUser == null ? "" : currentUser.getRole());
    }

    //  Hàm xử lý sự kiện đăng xuất
    public void btnDangXuatClick(ActionEvent actionEvent) {
        try {
            SessionContext.clear();

            // Mở màn Login TRƯỚC
            DangNhap_Ctrl loginCtrl = new DangNhap_Ctrl();
            Stage loginStage = new Stage();
            loginStage.setTitle("Đăng nhập hệ thống quản lý nhà thuốc");
            new DangNhap_GUI().showWithController(loginStage, loginCtrl);
            loginStage.centerOnScreen();
            loginStage.show(); // bắt buộc phải show

            // Tìm & đóng cửa sổ hiện tại: ưu tiên lấy từ actionEvent,
            // nếu null thì fallback lấy từ pnlChung (hoặc bất kỳ control nào của cửa sổ hiện tại)
            javafx.stage.Window currentWindow = null;
            if (actionEvent != null && actionEvent.getSource() instanceof javafx.scene.Node n) {
                if (n.getScene() != null) currentWindow = n.getScene().getWindow();
            }
            if (currentWindow == null && pnlChung != null && pnlChung.getScene() != null) {
                currentWindow = pnlChung.getScene().getWindow();
            }
            if (currentWindow instanceof Stage s) {
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openLapHoaDonWithMa(String maPhieuDat) {
        viTri = 5;
        selectMenu(viTri);

        if (lapHoaDonCtrl == null) {
            lapHoaDonCtrl = new LapHoaDon_Ctrl();
            lapHoaDonCtrl.setMaPhieuDat(maPhieuDat);
            lapHoaDonRoot = ViewEmbedder.buildFromShowWithController(new LapHoaDon_GUI(), lapHoaDonCtrl);

        }

        showInMainPane(lapHoaDonRoot);
        pnlThongTin.setVisible(false);
        pnlChung.requestFocus();
    }

    public void dong(){
        Stage stage = (Stage) pnlThongTin.getScene().getWindow();
        stage.close();
    }
}

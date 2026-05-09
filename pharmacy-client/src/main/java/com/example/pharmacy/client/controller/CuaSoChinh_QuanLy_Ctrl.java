package com.example.pharmacy.client.controller;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.ReportClientService;
import com.example.pharmacy.client.service.RmiReportClientService;
import com.example.pharmacy.client.service.TonKhoClientService;
import com.example.pharmacy.client.service.RmiTonKhoClientService;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.client.TienIch.VNDFormatter;
import com.example.pharmacy.common.model.ThongKeBanHangDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.client.session.SessionContext;
import com.example.pharmacy.common.session.UserContext;
import com.example.pharmacy.client.view.CuaSoChinh_QuanLy_GUI;
import com.example.pharmacy.client.view.DangNhap_GUI;
import com.example.pharmacy.client.view.ViewEmbedder;
import com.example.pharmacy.client.view.CN_XuLy.CaiDatDto.caiDat_GUI;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CuaSoChinh_QuanLy_Ctrl extends Application {

    public static CuaSoChinh_QuanLy_Ctrl instance;
    public Pane pnlChung;
    public Menu menuTimKiem;
    public Menu menuDanhMuc;
    public Menu menuCapNhat;
    public Menu menuThongKe;
    public Menu menuXuLy;
    public Label txtNguoiDung;
    public Label txtNgayThangNam;
    public TableView<Thuoc_SP_TheoLoDto> tblThuocHetHan;
    public TableColumn<Thuoc_SP_TheoLoDto, String> colMaThuocHetHan;
    public TableColumn<Thuoc_SP_TheoLoDto, String> colLoHangHetHan;
    public TableColumn<Thuoc_SP_TheoLoDto, java.sql.Date> colHSDHetHan;
    public TableView<Thuoc_SP_TheoLoDto> tblThuocSapHetHan;
    public TableColumn<Thuoc_SP_TheoLoDto, String> colMaThuocSapHetHan;
    public TableColumn<Thuoc_SP_TheoLoDto, String> colLoHangSapHetHan;
    public TableColumn<Thuoc_SP_TheoLoDto, java.sql.Date> colHSDSapHetHan;
    public Label lbl_SoLuongHangHetHan;
    public Label lbl_SoLuongHangSapHetHan;
    public LineChart chartDoanhThuThangNay;
    public Label lblDoanhThuThangTruoc;
    public Label lblDoanhThuThangNay;
    public Label lblHoaDonThangTruoc;
    public Label lblHoaDonThangNay;
    public Pane pnlThongTin;
    public Pane pnlNguoiDung;
    public Label lblVaiTro;

    private int viTri;
    private Node currentEmbedded;
    private boolean viewWired = false;
    private Parent defaultDashboard;

    private final RmiClientProvider rmiProvider = new RmiClientProvider();

    // ===================== KHỞI TẠO =====================
    public void initialize() {
        instance = this;
        setNgayGio(txtNgayThangNam);

        if (pnlChung != null && !pnlChung.getChildren().isEmpty()) {
            defaultDashboard = (Parent) pnlChung.getChildren().get(0);
        }

        loadDashboardData();
        setupGlobalShortcuts();

        UserContext currentUser = SessionContext.getCurrentUser();
        if (currentUser != null) {
            txtNguoiDung.setText("Người dùng: " + currentUser.getFullName());
        }
        pnlThongTin.setVisible(false);
    }

    private void loadDashboardData() {
        try {
            ReportClientService reportService = new RmiReportClientService(rmiProvider);
            DateRangeRequest thisMonth = new DateRangeRequest();
            LocalDate now = LocalDate.now();
            thisMonth.setFromDate(now.withDayOfMonth(1));
            thisMonth.setToDate(now.withDayOfMonth(now.lengthOfMonth()));

            DateRangeRequest prevMonth = new DateRangeRequest();
            LocalDate startPrev = now.withDayOfMonth(1).minusMonths(1);
            prevMonth.setFromDate(startPrev);
            prevMonth.setToDate(now.withDayOfMonth(1).minusDays(1));

            List<ThongKeBanHangDto> dataThis = reportService.getThongKeBanHangByDateRange(thisMonth);
            List<ThongKeBanHangDto> dataPrev = reportService.getThongKeBanHangByDateRange(prevMonth);

            if (dataThis != null && dataPrev != null) {
                int invoicesThis = dataThis.stream().mapToInt(ThongKeBanHangDto::getSoLuongHoaDon).sum();
                double revenueThis = dataThis.stream().mapToDouble(ThongKeBanHangDto::getDoanhThu).sum();
                int invoicesPrev = dataPrev.stream().mapToInt(ThongKeBanHangDto::getSoLuongHoaDon).sum();
                double revenuePrev = dataPrev.stream().mapToDouble(ThongKeBanHangDto::getDoanhThu).sum();

                VNDFormatter vndFormatter = new VNDFormatter();
                lblHoaDonThangNay.setText(invoicesThis + " Hóa đơn");
                lblHoaDonThangTruoc.setText(invoicesPrev + " Hóa đơn");
                lblDoanhThuThangNay.setText(vndFormatter.format(revenueThis));
                lblDoanhThuThangTruoc.setText(vndFormatter.format(revenuePrev));

                chartDoanhThuThangNay.getData().clear();
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Doanh thu");
                chartDoanhThuThangNay.setLegendVisible(false);
                for (ThongKeBanHangDto tk : dataThis) {
                    String label = tk.getThoiGian() == null ? "" : tk.getThoiGian();
                    series.getData().add(new XYChart.Data<>(label, tk.getDoanhThu()));
                }
                chartDoanhThuThangNay.getData().add(series);
                chartDoanhThuThangNay.setAnimated(false);
            }
        } catch (Exception e) {
            System.err.println("Chua the tai du lieu dashboard tu server: " + e.getMessage());
        }
    }

    // ===================== HẠ TẦNG EMBED =====================
    private void showInMainPane(Parent content) {
        if (pnlChung == null || content == null) return;

        pnlChung.getChildren().clear();

        if (content instanceof Region r) {
            r.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            r.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            r.prefWidthProperty().bind(pnlChung.widthProperty());
            r.prefHeightProperty().bind(pnlChung.heightProperty());
        } else {
            content.resizeRelocate(0, 0, pnlChung.getWidth(), pnlChung.getHeight());
            pnlChung.widthProperty().addListener((o, ov, nv) -> content.resizeRelocate(0, 0, nv.doubleValue(), pnlChung.getHeight()));
            pnlChung.heightProperty().addListener((o, ov, nv) -> content.resizeRelocate(0, 0, pnlChung.getWidth(), nv.doubleValue()));
        }

        pnlChung.getChildren().add(content);
        currentEmbedded = content;

        pnlChung.applyCss();
        pnlChung.layout();
    }

    private void loadViewEmbedded(int menuIndex, String cacheKey, Object gui, Object ctrl) {
        viTri = menuIndex;
        selectMenu(viTri);
        Parent root = ViewEmbedder.buildFromShowWithController(gui, ctrl);
        showInMainPane(root);
        pnlThongTin.setVisible(false);
        pnlChung.requestFocus();
    }

    public void markViewWired() {
        this.viewWired = true;
    }

    private boolean menusReady() {
        return menuTimKiem != null && menuDanhMuc != null
                && menuCapNhat != null && menuThongKe != null && menuXuLy != null;
    }

    public void selectMenu(int viTriGiaoDien) {
        if (!viewWired || !menusReady()) return;
        String active = "-fx-background-color: #003cff";
        String normal = "-fx-background-color: #1e90ff";
        menuTimKiem.setStyle(viTriGiaoDien == 1 ? active : normal);
        menuDanhMuc.setStyle(viTriGiaoDien == 2 ? active : normal);
        menuCapNhat.setStyle(viTriGiaoDien == 3 ? active : normal);
        menuThongKe.setStyle(viTriGiaoDien == 4 ? active : normal);
        menuXuLy.setStyle(viTriGiaoDien == 5 ? active : normal);
    }

    // ===================== TRANG CHỦ =====================
    public void AnhChuyenTrangChu(MouseEvent e) {
        viTri = 0;
        selectMenu(viTri);
        if (defaultDashboard != null) {
            showInMainPane(defaultDashboard);
        }
    }

    // ===================== TÌM KIẾM =====================
    public void timKiemHoaDon(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_HOADON",
                new com.example.pharmacy.client.view.CN_TimKiem.TKHoaDon.TKHoaDon_GUI(),
                new com.example.pharmacy.client.controller.CN_TimKiem.TKHoaDon.TimKiemHoaDon_Ctrl());
    }

    public void timKiemPhieuNhap(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_PN",
                new com.example.pharmacy.client.view.CN_TimKiem.TKPhieuNhapHang.TKPhieuNhapHang_GUI(),
                new com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuNhapHang.TimKiemPhieuNhap_Ctrl());
    }

    public void timKiemPhieuDoiHang(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_PDOI",
                new com.example.pharmacy.client.view.CN_TimKiem.TKPhieuDoi.TKPhieuDoiHang_GUI(),
                new com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuDoiHang.TKPhieuDoiHang_Ctrl());
    }

    public void timKiemPhieuTraHang(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_PTRA",
                new com.example.pharmacy.client.view.CN_TimKiem.TKPhieuTra.TKPhieuTraHang_GUI(),
                new com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuTraHang.TKPhieuTraHang_Ctrl());
    }

    public void timKiemPhieuDatHang(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_PDAT",
                new com.example.pharmacy.client.view.CN_TimKiem.TKPhieuDatHang.TKPhieuDatHang_GUI(),
                new com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuDatHang.TKPhieuDatHang_Ctrl());
    }

    public void timKiemNhaCungCap(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_NCC",
                new com.example.pharmacy.client.view.CN_TimKiem.TKNhaCungCap.TimKiemNCC_GUI(),
                new com.example.pharmacy.client.controller.CN_TimKiem.TKNhaCungCap.TimKiemNCC_Ctrl());
    }

    public void timKiemThuoc(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_THUOC",
                new com.example.pharmacy.client.view.CN_TimKiem.TKThuoc.TKThuoc_GUI(),
                new com.example.pharmacy.client.controller.CN_TimKiem.TKThuoc.TimKiemThuoc_Ctrl());
    }

    public void timKiemThuocTrongKho(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_THUOCTRONGKHO",
                new com.example.pharmacy.client.view.CN_TimKiem.TKThuocTrongKho.TKThuocTrongKho_GUI(),
                new com.example.pharmacy.client.controller.CN_TimKiem.TKThuocTrongKho.TimKiemThuocTrongKho_Ctrl());
    }

    public void timKiemKhachHang(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_KH",
                new com.example.pharmacy.client.view.CN_TimKiem.TKKhachHang.TimKiemKhachHang_GUI(),
                new com.example.pharmacy.client.controller.CN_TimKiem.TKKhachHang.TimKiemKhachHang_Ctrl());
    }

    public void timKiemHoatDong(ActionEvent actionEvent) {
        loadViewEmbedded(1, "TK_HD",
                new com.example.pharmacy.client.view.CN_TimKiem.TKHoatDong.TKHoatDong_GUI(),
                new com.example.pharmacy.client.controller.CN_TimKiem.TKHoatDong.TKHoatDong_Ctrl());
    }

    // ===================== DANH MỤC =====================
    public void danhMucThuoc(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_THUOC",
                new com.example.pharmacy.client.view.CN_DanhMuc.DMThuoc.DanhMucThuoc_GUI(),
                new com.example.pharmacy.client.controller.CN_DanhMuc.DMThuoc.DanhMucThuoc_Ctrl());
    }

    public void danhMucNhanVien(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_NV",
                new com.example.pharmacy.client.view.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_GUI(),
                new com.example.pharmacy.client.controller.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_Ctrl());
    }

    public void danhMucKhachHang(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_KH",
                new com.example.pharmacy.client.view.CN_DanhMuc.DMKhachHang.DanhMucKhachHang_GUI(),
                new com.example.pharmacy.client.controller.CN_DanhMuc.DMKhachHang.DanhMucKhachHang_Ctrl());
    }

    public void danhMucKeHang(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_KE",
                new com.example.pharmacy.client.view.CN_DanhMuc.DMKeHang.DanhMucKeHang_GUI(),
                new com.example.pharmacy.client.controller.CN_DanhMuc.DMKeHang.DanhMucKeHang_Ctrl());
    }

    public void danhMucNhaCungCap(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_NCC",
                new com.example.pharmacy.client.view.CN_DanhMuc.DMNCC.DanhMucNhaCungCap_GUI(),
                new com.example.pharmacy.client.controller.CN_DanhMuc.DMNhaCungCap.DanhMucNhaCungCap_Ctrl());
    }

    public void danhMucKhuyenMai(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_KM",
                new com.example.pharmacy.client.view.CN_DanhMuc.DMKhuyenMai.DanhMucKhuyenMai_GUI(),
                new com.example.pharmacy.client.controller.CN_DanhMuc.DMKhuyenMai.DanhMucKhuyenMai_Ctrl());
    }

    public void danhMucNhomDuocLy(ActionEvent actionEvent) {
        loadViewEmbedded(2, "DM_NDL",
                new com.example.pharmacy.client.view.CN_DanhMuc.DMNhomDuocLy.DanhMucNhomDuocLy_GUI(),
                new com.example.pharmacy.client.controller.CN_DanhMuc.DMNhomDuocLy.DanhMucNhomDuocLy_Ctrl());
    }

    // ===================== CẬP NHẬT =====================
    public void capNhatGiaBan(ActionEvent actionEvent) {
        loadViewEmbedded(3, "CN_GIA",
                new com.example.pharmacy.client.view.CN_DanhMuc.DMThuoc.DanhMucThuoc_GUI(),
                new com.example.pharmacy.client.controller.CN_DanhMuc.DMThuoc.DanhMucThuoc_Ctrl());
    }

    public void capNhatTonKho(ActionEvent actionEvent) {
        loadViewEmbedded(3, "CN_TON",
                new com.example.pharmacy.client.view.CN_CapNhat.CapNhatSoLuong.CapNhatSoLuongThuoc_GUI(),
                new com.example.pharmacy.client.controller.CN_CapNhat.CapNhatSoLuong.CapNhatSoLuongThuoc_Ctrl());
    }

    public void capNhatKhuyenMai(ActionEvent actionEvent) {
        loadViewEmbedded(3, "CN_KM",
                new com.example.pharmacy.client.view.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_GUI(),
                new com.example.pharmacy.client.controller.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_Ctrl());
    }

    // ===================== THỐNG KÊ =====================
    public void thongKeDoanhThu(ActionEvent actionEvent) {
        loadViewEmbedded(4, "TK_DT",
                new com.example.pharmacy.client.view.CN_ThongKe.ThongKeBanHang_GUI(),
                new com.example.pharmacy.client.controller.CN_ThongKe.ThongKeBanHang_Ctrl());
    }

    public void thongKeXuatNhap(ActionEvent actionEvent) {
        loadViewEmbedded(4, "TK_XNT",
                new com.example.pharmacy.client.view.CN_ThongKe.ThongKeXNT_GUI(),
                new com.example.pharmacy.client.controller.CN_ThongKe.ThongKeXNT_Ctrl());
    }

    public void thongKeTopSanPham(ActionEvent actionEvent) {
        loadViewEmbedded(4, "TK_TOP_SP",
                new com.example.pharmacy.client.view.CN_ThongKe.ThongKeTopSanPham_GUI(),
                new com.example.pharmacy.client.controller.CN_ThongKe.ThongKeTopSanPham_Ctrl());
    }

    // ===================== XỬ LÝ =====================
    public void lapHoaDon(ActionEvent actionEvent) {
        loadViewEmbedded(5, "XL_HD",
                new com.example.pharmacy.client.view.CN_XuLy.LapHoaDon.LapHoaDon_GUI(),
                new com.example.pharmacy.client.controller.CN_XuLy.LapHoaDon.LapHoaDon_Ctrl());
    }

    public void lapPhieuDoiHang(ActionEvent actionEvent) {
        loadViewEmbedded(5, "XL_PDOI",
                new com.example.pharmacy.client.view.CN_XuLy.LapPhieuDoi.LapPhieuDoi_GUI(),
                new com.example.pharmacy.client.controller.CN_XuLy.LapPhieuDoi.LapPhieuDoiHang_Ctrl());
    }

    public void lapPhieuTraHang(ActionEvent actionEvent) {
        loadViewEmbedded(5, "XL_PTRA",
                new com.example.pharmacy.client.view.CN_XuLy.LapPhieuTra.LapPhieuTra_GUI(),
                new com.example.pharmacy.client.controller.CN_XuLy.LapPhieuTra.LapPhieuTraHang_Ctrl());
    }

    public void lapPhieuDatHang(ActionEvent actionEvent) {
        loadViewEmbedded(5, "XL_PDAT",
                new com.example.pharmacy.client.view.CN_XuLy.LapPhieuDat.LapPhieuDat_GUI(),
                new com.example.pharmacy.client.controller.CN_XuLy.LapPhieuDatHang.LapPhieuDatHang_Ctrl());
    }

    public void nhapHang(ActionEvent actionEvent) {
        loadViewEmbedded(5, "XL_PNHAP",
                new com.example.pharmacy.client.view.CN_XuLy.LapPhieuNhapHang.LapPhieuNhapHang_GUI(),
                new com.example.pharmacy.client.controller.CN_XuLy.LapPhieuNhapHang.LapPhieuNhapHang_Ctrl());
    }

    // ===================== HỖ TRỢ =====================
    private void setNgayGio(Label lblNgayGio) {
        var localeVN = new java.util.Locale("vi", "VN");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy HH:mm:ss", localeVN);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> lblNgayGio.setText(LocalDateTime.now().format(formatter))),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setupGlobalShortcuts() {
        Platform.runLater(() -> {
            Scene scene = pnlChung.getScene();
            if (scene == null) return;
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F1), () -> AnhChuyenTrangChu(null));
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F2), () -> lapHoaDon(null));
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F3), () -> lapPhieuDatHang(null));
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F4), () -> nhapHang(null));
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F5), () -> timKiemKhachHang(null));
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F6), () -> timKiemThuoc(null));
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F7), () -> capNhatGiaBan(null));
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F8), () -> capNhatTonKho(null));
        });
        pnlChung.requestFocus();
    }

    public void pnlNguoiDungClick(MouseEvent mouseEvent) {
        pnlThongTin.setVisible(!pnlThongTin.isVisible());
        UserContext currentUser = SessionContext.getCurrentUser();
        lblVaiTro.setText(currentUser == null ? "" : currentUser.getRole());
    }

    public void btnDangXuatClick(ActionEvent actionEvent) {
        try {
            SessionContext.clear();
            DangNhap_Ctrl loginCtrl = new DangNhap_Ctrl();
            Stage loginStage = new Stage();
            loginStage.setTitle("Đăng nhập hệ thống quản lý nhà thuốc");
            new DangNhap_GUI().showWithController(loginStage, loginCtrl);
            loginStage.centerOnScreen();
            loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacy/client/img/logoNguyenBan.png")));
            loginStage.show();

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

    public void btnCaiDatClick(ActionEvent actionEvent) {
        pnlThongTin.setVisible(false);
        caiDat_GUI view = new caiDat_GUI();
        Parent root = view.createUI();
        Scene scene = new Scene(root, 310, 150);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/pharmacy/client/img/logoNguyenBan.png"))));
        stage.initOwner(txtNgayThangNam.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.setTitle("Cài đặt");
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        new CuaSoChinh_QuanLy_GUI().showWithController(stage, this);
    }
}

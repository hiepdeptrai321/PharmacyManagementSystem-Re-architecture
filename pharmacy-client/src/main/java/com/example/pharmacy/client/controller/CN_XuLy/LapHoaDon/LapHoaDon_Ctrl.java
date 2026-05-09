package com.example.pharmacy.client.controller.CN_XuLy.LapHoaDon;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.common.request.CreateHoaDonLineRequest;
import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.client.TienIch.VNDFormatter;
import com.example.pharmacy.client.controller.CN_DanhMuc.DMKhachHang.ThemKhachHang_Ctrl;
import com.example.pharmacy.client.controller.CN_TimKiem.TKHoaDon.ChiTietHoaDon_Ctrl;
import com.example.pharmacy.client.controller.CN_TimKiem.TKKhachHang.TimKiemKhachHangTrongHD_Ctrl;
import com.example.pharmacy.common.model.ChiTietDonViTinh;
import com.example.pharmacy.common.model.ChiTietHoaDon;
import com.example.pharmacy.common.model.ChiTietPhieuDatHang;
import com.example.pharmacy.common.model.DonViTinh;
import com.example.pharmacy.common.model.HoaDon;
import com.example.pharmacy.common.model.KhachHang;
import com.example.pharmacy.common.model.PhieuDatHang;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLo;
import com.example.pharmacy.common.model.Thuoc_SanPham;
import com.example.pharmacy.client.service.ApDungKhuyenMai;
import com.example.pharmacy.client.service.DichVuKhuyenMai;
import com.example.pharmacy.client.service.HoaDonService;
import com.example.pharmacy.client.service.PhieuDatHangService;
import com.example.pharmacy.client.service.ThuocService;
import com.example.pharmacy.client.session.SessionContext;
import com.example.pharmacy.client.view.CN_DanhMuc.DMKhachHang.ThemKhachHang_GUI;
import com.example.pharmacy.client.view.CN_TimKiem.TKHoaDon.ChiTietHoaDon_GUI;
import com.example.pharmacy.client.view.CN_TimKiem.TKKhachHang.TimKiemKhachHangTrongHD_GUI;
import com.example.pharmacy.client.view.CN_XuLy.LapHoaDon.LapHoaDon_GUI;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;

public class LapHoaDon_Ctrl extends Application {
    public ObservableList<ChiTietHoaDon> dsChiTietHD = FXCollections.observableArrayList();
    public Button btnXoaRong;
    public Button btnThemKH;
    public DatePicker dpNgayLap;
    public ChoiceBox<String> cbPhuongThucTT;
    public Pane paneTienMat;
    public TextField txtTimThuoc;
    public TextField txtTenKH;
    public TextField txtSDT;
    public TableView<ChiTietHoaDon> tblChiTietHD;
    public TableColumn<ChiTietHoaDon, String> colSTT;
    public TableColumn<ChiTietHoaDon, String> colTenSP;
    public TableColumn<ChiTietHoaDon, Boolean> colKeDon;
    public TableColumn<ChiTietHoaDon, Number> colSL;
    public TableColumn<ChiTietHoaDon, String> colDonVi;
    public TableColumn<ChiTietHoaDon, String> colDonGia;
    public TableColumn<ChiTietHoaDon, String> colChietKhau;
    public TableColumn<ChiTietHoaDon, String> colThanhTien;
    public TableColumn<ChiTietHoaDon, String> colBo;
    public Label lblTongTien;
    public Label lblGiamGia;
    public Label lblThongBaoTT;
    public Label lblVAT;
    public Label lblThanhTien;
    public TextField txtSoTienKhachDua;
    public Label lblTienThua;
    public Button btnThanhToan;
    public Label lblGiamTheoHD;
    public RadioButton rbOTC;
    public TextField txtMaDonThuoc;

    private final ContextMenu goiYMenu = new ContextMenu();
    private final PauseTransition pause = new PauseTransition(Duration.millis(250));
    private final IdentityHashMap<ChiTietHoaDon, ChiTietDonViTinh> dvtTheoDong = new IdentityHashMap<>();
    private final ThuocService thuocService = new ThuocService();
    private final HoaDonService hoaDonService = new HoaDonService();
    private final PhieuDatHangService phieuDatHangService = new PhieuDatHangService();
    private final DichVuKhuyenMai dichVuKhuyenMai = new DichVuKhuyenMai();
    private final VNDFormatter vndFormatter = new VNDFormatter();

    private KhachHang khachHang;
    private BigDecimal currentInvoiceDiscount = BigDecimal.ZERO;
    private String maPhieuDat;

    @Override
    public void start(Stage stage) {
        LapHoaDon_GUI gui = new LapHoaDon_GUI();
        gui.showWithController(stage, this);
    }

    public void initialize() {
        tblChiTietHD.setItems(dsChiTietHD);
        dpNgayLap.setValue(LocalDate.now());
        dpNgayLap.setEditable(false);
        cbPhuongThucTT.getItems().setAll("Tien mat", "Chuyen khoan");
        cbPhuongThucTT.setValue("Tien mat");
        paneTienMat.setVisible(true);
        paneTienMat.setManaged(true);
        applyLoaiHoaDonUI(rbOTC.isSelected());

        rbOTC.textProperty().bind(javafx.beans.binding.Bindings.when(rbOTC.selectedProperty())
                .then("Ke don (ETC)")
                .otherwise("Khong ke don (OTC)"));
        rbOTC.selectedProperty().addListener((obs, oldVal, newVal) -> applyLoaiHoaDonUI(newVal));

        btnThemKH.setOnAction(e -> xuLyThemKH());
        btnThanhToan.setOnAction(e -> xuLyThanhToan());
        btnXoaRong.setOnAction(e -> resetForm());
        cbPhuongThucTT.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean isCash = !"Chuyen khoan".equalsIgnoreCase(newVal);
            paneTienMat.setVisible(isCash);
            paneTienMat.setManaged(isCash);
        });

        vndFormatter.applyNumberFormatter(txtSoTienKhachDua);
        txtSoTienKhachDua.textProperty().addListener((obs, oldVal, newVal) -> recalculateTotals());
        dsChiTietHD.addListener((ListChangeListener<ChiTietHoaDon>) change -> recalculateTotals());
        configureTable();
        setupMedicineSuggestions();
        recalculateTotals();

        if (maPhieuDat != null) {
            loadDataFromMaPhieuDat(maPhieuDat);
        }
    }

    public void setMaPhieuDat(String maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    public void xuLyTimKhachHang() {
        Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
        TimKiemKhachHangTrongHD_Ctrl ctrl = new TimKiemKhachHangTrongHD_Ctrl();
        TimKiemKhachHangTrongHD_GUI gui = new TimKiemKhachHangTrongHD_GUI();
        gui.showWithController(stage, ctrl);
        ctrl.setOnSelected(kh -> {
            khachHang = kh;
            txtTenKH.setText(kh.getTenKH());
            txtSDT.setText(kh.getSdt());
            stage.close();
        });
        stage.show();
    }

    public void xuLyThemKH() {
        Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
        ThemKhachHang_Ctrl ctrl = new ThemKhachHang_Ctrl();
        ThemKhachHang_GUI gui = new ThemKhachHang_GUI();
        gui.showWithController(stage, ctrl);
        ctrl.setOnSaved(kh -> {
            khachHang = kh;
            txtTenKH.setText(kh.getTenKH());
            txtSDT.setText(kh.getSdt());
        });
        stage.show();
    }

    public void xuLyThanhToan() {
        try {
            if (dsChiTietHD.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Vui long them it nhat mot san pham.");
                return;
            }
            if (SessionContext.getCurrentUser() == null) {
                showAlert(Alert.AlertType.ERROR, "Phien dang nhap khong hop le.");
                return;
            }
            if (rbOTC.isSelected() && isBlank(txtMaDonThuoc.getText())) {
                showAlert(Alert.AlertType.WARNING, "Hoa don ETC bat buoc phai co ma don thuoc.");
                return;
            }

            BigDecimal tongThanhToan = currentThanhTien();
            BigDecimal tienKhachDua = BigDecimal.valueOf(vndFormatter.parseFormattedNumber(txtSoTienKhachDua.getText()));
            if ("Tien mat".equalsIgnoreCase(cbPhuongThucTT.getValue()) && tienKhachDua.compareTo(tongThanhToan) < 0) {
                showAlert(Alert.AlertType.WARNING, "So tien khach dua chua du de thanh toan.");
                return;
            }

            CreateHoaDonRequest request = buildRequest();
            String maHoaDon = hoaDonService.createInvoice(request, SessionContext.requireCurrentUser());
            HoaDon hoaDonMoi = hoaDonService.findById(maHoaDon);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thanh cong");
            alert.setHeaderText(null);
            alert.setContentText("Da lap hoa don " + maHoaDon + " thanh cong.");
            alert.showAndWait();

            if (hoaDonMoi != null) {
                openInvoiceDetail(hoaDonMoi);
            }
            resetForm();
        } catch (Exception exception) {
            exception.printStackTrace();
            showAlert(Alert.AlertType.ERROR, exception.getMessage() == null ? "Lap hoa don that bai." : exception.getMessage());
        }
    }

    public void xuLyHuy() {
        resetForm();
    }

    public void loadDataFromMaPhieuDat(String maPhieuDat) {
        PhieuDatHang phieuDatHang = phieuDatHangService.findById(maPhieuDat);
        List<ChiTietPhieuDatHang> details = phieuDatHangService.findDetailsByMaPhieuDat(maPhieuDat);
        if (phieuDatHang == null || details.isEmpty()) {
            return;
        }

        this.maPhieuDat = maPhieuDat;
        this.khachHang = phieuDatHang.getKhachHang();
        if (khachHang != null) {
            txtTenKH.setText(safe(khachHang.getTenKH()));
            txtSDT.setText(safe(khachHang.getSdt()));
        }

        dsChiTietHD.clear();
        dvtTheoDong.clear();
        List<Thuoc_SanPham> dsThuoc = thuocService.findAll();
        for (ChiTietPhieuDatHang detail : details) {
            Thuoc_SanPham thuoc = dsThuoc.stream()
                    .filter(item -> item.getMaThuoc() != null && item.getMaThuoc().equalsIgnoreCase(detail.getThuoc().getMaThuoc()))
                    .findFirst()
                    .orElse(detail.getThuoc());

            ChiTietDonViTinh dvt = resolveDvt(thuoc, detail.getDvt());
            if (dvt == null || dvt.getDvt() == null) {
                continue;
            }

            ChiTietHoaDon row = new ChiTietHoaDon();
            Thuoc_SP_TheoLo loHang = new Thuoc_SP_TheoLo();
            loHang.setThuoc(thuoc);
            row.setLoHang(loHang);
            row.setDvt(dvt.getDvt());
            row.setSoLuong(detail.getSoLuong());
            row.setDonGia(detail.getDonGia());
            row.setGiamGia(detail.getGiamGia());
            dsChiTietHD.add(row);
            dvtTheoDong.put(row, dvt);
        }
        recalculateTotals();
    }

    private void configureTable() {
        colSTT.setCellValueFactory(cell -> new ReadOnlyStringWrapper(String.valueOf(tblChiTietHD.getItems().indexOf(cell.getValue()) + 1)));
        colTenSP.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().getLoHang() != null && cell.getValue().getLoHang().getThuoc() != null
                        ? safe(cell.getValue().getLoHang().getThuoc().getTenThuoc())
                        : ""
        ));
        colKeDon.setCellValueFactory(cell -> new javafx.beans.property.SimpleBooleanProperty(
                cell.getValue().getLoHang() != null
                        && cell.getValue().getLoHang().getThuoc() != null
                        && cell.getValue().getLoHang().getThuoc().isETC()
        ));
        colKeDon.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : Boolean.TRUE.equals(item) ? "Co" : "Khong");
            }
        });
        colDonVi.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().getDvt() == null ? "" : safe(cell.getValue().getDvt().getTenDonViTinh())
        ));
        colDonGia.setCellValueFactory(cell -> new ReadOnlyStringWrapper(formatVnd(cell.getValue().getDonGia())));
        colChietKhau.setCellValueFactory(cell -> new ReadOnlyStringWrapper(formatVnd(cell.getValue().getGiamGia())));
        colThanhTien.setCellValueFactory(cell -> new ReadOnlyStringWrapper(formatVnd(tinhThanhTien(cell.getValue()).doubleValue())));

        colSL.setCellFactory(tc -> new TableCell<>() {
            private final Button btnDec = new Button("-");
            private final Button btnInc = new Button("+");
            private final TextField tf = new TextField();
            private final HBox box = new HBox(6, btnDec, tf, btnInc);

            {
                tf.setPrefWidth(56);
                tf.setStyle("-fx-alignment: center-right;");
                btnDec.setOnAction(e -> adjust(-1));
                btnInc.setOnAction(e -> adjust(1));
                tf.setOnAction(e -> commitFromText());
                tf.focusedProperty().addListener((obs, oldFocused, nowFocused) -> {
                    if (!nowFocused) {
                        commitFromText();
                    }
                });
            }

            private void adjust(int delta) {
                ChiTietHoaDon row = currentRow();
                if (row == null) {
                    return;
                }
                row.setSoLuong(Math.max(1, row.getSoLuong() + delta));
                applyPromotion(row);
                tf.setText(String.valueOf(row.getSoLuong()));
                tblChiTietHD.refresh();
                recalculateTotals();
            }

            private void commitFromText() {
                ChiTietHoaDon row = currentRow();
                if (row == null) {
                    return;
                }
                try {
                    row.setSoLuong(Math.max(1, Integer.parseInt(tf.getText().trim())));
                } catch (NumberFormatException exception) {
                    row.setSoLuong(Math.max(1, row.getSoLuong()));
                }
                applyPromotion(row);
                tf.setText(String.valueOf(row.getSoLuong()));
                tblChiTietHD.refresh();
                recalculateTotals();
            }

            private ChiTietHoaDon currentRow() {
                return getIndex() >= 0 && getIndex() < getTableView().getItems().size()
                        ? getTableView().getItems().get(getIndex())
                        : null;
            }

            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ChiTietHoaDon row = currentRow();
                    tf.setText(row == null ? "1" : String.valueOf(row.getSoLuong()));
                    setGraphic(box);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        });

        colBo.setCellFactory(tc -> new TableCell<>() {
            private final Button btnXoa = new Button("X");

            {
                btnXoa.setStyle("-fx-background-color: #d80202; -fx-text-fill: white; -fx-font-weight: bold");
                btnXoa.setOnAction(e -> {
                    if (getIndex() < 0 || getIndex() >= tblChiTietHD.getItems().size()) {
                        return;
                    }
                    ChiTietHoaDon row = tblChiTietHD.getItems().get(getIndex());
                    dsChiTietHD.remove(row);
                    dvtTheoDong.remove(row);
                    recalculateTotals();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnXoa);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        });
    }

    private void setupMedicineSuggestions() {
        pause.setOnFinished(evt -> showSuggestions(txtTimThuoc.getText()));
        txtTimThuoc.textProperty().addListener((obs, oldText, newText) -> pause.playFromStart());
        txtTimThuoc.focusedProperty().addListener((obs, oldFocused, nowFocused) -> {
            if (!nowFocused) {
                goiYMenu.hide();
            }
        });
    }

    private void showSuggestions(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim();
        if (tuKhoa.isEmpty()) {
            goiYMenu.hide();
            return;
        }

        List<Thuoc_SanPham> results = thuocService.searchByKeyword(tuKhoa).stream().limit(8).toList();
        if (results.isEmpty()) {
            goiYMenu.hide();
            return;
        }

        goiYMenu.getItems().clear();
        goiYMenu.setStyle("-fx-min-width: 500; -fx-pref-width: 500;");
        for (Thuoc_SanPham thuoc : results) {
            String tenDvt = thuoc.getTenDVTCoBan() == null ? "" : thuoc.getTenDVTCoBan();
            Label nameLbl = new Label(thuoc.getTenThuoc());
            nameLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            Label infoLbl = new Label(tenDvt.isBlank() ? "" : " | " + tenDvt);
            infoLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            HBox row = new HBox(10, nameLbl, infoLbl, spacer);
            row.setStyle("-fx-padding: 8 14; -fx-min-height: 38;");

            CustomMenuItem item = new CustomMenuItem(row, true);
            item.setOnAction(ae -> {
                txtTimThuoc.clear();
                goiYMenu.hide();
                themThuocVaoBang(thuoc);
            });
            goiYMenu.getItems().add(item);
        }

        if (!goiYMenu.isShowing()) {
            goiYMenu.show(txtTimThuoc, Side.BOTTOM, 0, 0);
        }
    }

    private void themThuocVaoBang(Thuoc_SanPham thuoc) {
        if (thuoc == null) {
            return;
        }
        ChiTietDonViTinh dvt = resolveDefaultDvt(thuoc);
        if (dvt == null || dvt.getDvt() == null) {
            showAlert(Alert.AlertType.WARNING, "Thuoc chua co don vi tinh co ban.");
            return;
        }

        for (ChiTietHoaDon row : dsChiTietHD) {
            if (row.getLoHang() != null
                    && row.getLoHang().getThuoc() != null
                    && Objects.equals(row.getLoHang().getThuoc().getMaThuoc(), thuoc.getMaThuoc())
                    && row.getDvt() != null
                    && Objects.equals(row.getDvt().getMaDVT(), dvt.getDvt().getMaDVT())) {
                row.setSoLuong(row.getSoLuong() + 1);
                applyPromotion(row);
                tblChiTietHD.refresh();
                recalculateTotals();
                return;
            }
        }

        ChiTietHoaDon detail = new ChiTietHoaDon();
        Thuoc_SP_TheoLo loHang = new Thuoc_SP_TheoLo();
        loHang.setThuoc(thuoc);
        detail.setLoHang(loHang);
        detail.setDvt(dvt.getDvt());
        detail.setSoLuong(1);
        detail.setDonGia(dvt.getGiaBan());
        detail.setGiamGia(0);
        dsChiTietHD.add(detail);
        dvtTheoDong.put(detail, dvt);
        applyPromotion(detail);
        recalculateTotals();
    }

    private void applyPromotion(ChiTietHoaDon row) {
        if (row == null || row.getLoHang() == null || row.getLoHang().getThuoc() == null) {
            return;
        }
        ChiTietDonViTinh dvt = dvtTheoDong.get(row);
        double heSo = dvt == null ? 1 : Math.max(1d, dvt.getHeSoQuyDoi());
        int soLuongBase = Math.max(1, (int) Math.round(row.getSoLuong() * heSo));
        ApDungKhuyenMai apDung = dichVuKhuyenMai.apDungChoSP(
                row.getLoHang().getThuoc().getMaThuoc(),
                soLuongBase,
                BigDecimal.valueOf(Math.max(0, row.getDonGia())),
                dpNgayLap.getValue() == null ? LocalDate.now() : dpNgayLap.getValue()
        );
        row.setGiamGia(apDung.getDiscount().setScale(0, RoundingMode.HALF_UP).doubleValue());
    }

    private void recalculateTotals() {
        BigDecimal tongTienHang = BigDecimal.ZERO;
        BigDecimal tongGiamGiaSanPham = BigDecimal.ZERO;
        for (ChiTietHoaDon row : dsChiTietHD) {
            BigDecimal line = BigDecimal.valueOf(row.getDonGia()).multiply(BigDecimal.valueOf(row.getSoLuong()));
            tongTienHang = tongTienHang.add(line);
            tongGiamGiaSanPham = tongGiamGiaSanPham.add(BigDecimal.valueOf(Math.max(0, row.getGiamGia())));
        }

        BigDecimal baseSauKmSp = tongTienHang.subtract(tongGiamGiaSanPham).max(BigDecimal.ZERO);
        currentInvoiceDiscount = dichVuKhuyenMai.apDungChoHoaDon(
                baseSauKmSp,
                dpNgayLap.getValue() == null ? LocalDate.now() : dpNgayLap.getValue()
        ).getDiscount().setScale(0, RoundingMode.HALF_UP);
        BigDecimal baseSauKmHd = baseSauKmSp.subtract(currentInvoiceDiscount).max(BigDecimal.ZERO);
        BigDecimal vat = baseSauKmHd.multiply(new BigDecimal("0.05")).setScale(0, RoundingMode.HALF_UP);
        BigDecimal thanhTien = baseSauKmHd.add(vat);
        BigDecimal tienKhachDua = BigDecimal.valueOf(vndFormatter.parseFormattedNumber(txtSoTienKhachDua.getText()));
        BigDecimal tienThua = tienKhachDua.subtract(thanhTien).max(BigDecimal.ZERO);

        lblTongTien.setText(formatVnd(tongTienHang.doubleValue()));
        lblGiamGia.setText(formatVnd(tongGiamGiaSanPham.doubleValue()));
        lblGiamTheoHD.setText(formatVnd(currentInvoiceDiscount.doubleValue()));
        lblVAT.setText(formatVnd(vat.doubleValue()));
        lblThanhTien.setText(formatVnd(thanhTien.doubleValue()));
        lblTienThua.setText(formatVnd(tienThua.doubleValue()));
    }

    private BigDecimal currentThanhTien() {
        return parseVndLabel(lblThanhTien.getText());
    }

    private CreateHoaDonRequest buildRequest() {
        CreateHoaDonRequest request = new CreateHoaDonRequest();
        request.setMaKhachHang(khachHang == null ? null : khachHang.getMaKH());
        request.setNgayLap(LocalDateTime.now());
        request.setLoaiHoaDon(rbOTC.isSelected() ? "ETC" : "OTC");
        request.setMaDonThuoc(rbOTC.isSelected() ? txtMaDonThuoc.getText().trim() : null);
        request.setPhuongThucThanhToan(cbPhuongThucTT.getValue());
        request.setTienKhachDua(BigDecimal.valueOf(vndFormatter.parseFormattedNumber(txtSoTienKhachDua.getText())));
        request.setVatRate(new BigDecimal("0.05"));
        request.setDiscountInvoiceAmount(currentInvoiceDiscount);
        request.setMaPhieuDat(maPhieuDat);

        for (ChiTietHoaDon row : dsChiTietHD) {
            CreateHoaDonLineRequest line = new CreateHoaDonLineRequest();
            line.setMaThuoc(row.getLoHang() == null || row.getLoHang().getThuoc() == null ? null : row.getLoHang().getThuoc().getMaThuoc());
            line.setMaDvt(row.getDvt() == null ? null : row.getDvt().getMaDVT());
            line.setSoLuong(row.getSoLuong());
            line.setDonGia(BigDecimal.valueOf(Math.max(0, row.getDonGia())));
            line.setGiamGia(BigDecimal.valueOf(Math.max(0, row.getGiamGia())));
            line.setTenThuoc(row.getLoHang() == null || row.getLoHang().getThuoc() == null ? null : row.getLoHang().getThuoc().getTenThuoc());
            request.getLines().add(line);
        }
        return request;
    }

    private void openInvoiceDetail(HoaDon hoaDon) {
        try {
            ChiTietHoaDon_Ctrl ctrl = new ChiTietHoaDon_Ctrl();
            ChiTietHoaDon_GUI gui = new ChiTietHoaDon_GUI();
            Stage dialog = new Stage();
        TuyChinhAlert.setAppIcon(dialog);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(btnThanhToan.getScene().getWindow());
            dialog.setTitle("Chi tiet hoa don " + hoaDon.getMaHD());
            gui.showWithController(dialog, ctrl);
            ctrl.setHoaDon(hoaDon);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void resetForm() {
        dsChiTietHD.clear();
        dvtTheoDong.clear();
        khachHang = null;
        maPhieuDat = null;
        txtTimThuoc.clear();
        txtTenKH.clear();
        txtSDT.clear();
        txtMaDonThuoc.clear();
        txtSoTienKhachDua.clear();
        dpNgayLap.setValue(LocalDate.now());
        cbPhuongThucTT.setValue("Tien mat");
        paneTienMat.setVisible(true);
        paneTienMat.setManaged(true);
        rbOTC.setSelected(false);
        currentInvoiceDiscount = BigDecimal.ZERO;
        recalculateTotals();
    }

    private ChiTietDonViTinh resolveDefaultDvt(Thuoc_SanPham thuoc) {
        if (thuoc == null || thuoc.getDsCTDVT() == null || thuoc.getDsCTDVT().isEmpty()) {
            return null;
        }
        return thuoc.getDsCTDVT().stream()
                .filter(ChiTietDonViTinh::isDonViCoBan)
                .findFirst()
                .orElse(thuoc.getDsCTDVT().get(0));
    }

    private ChiTietDonViTinh resolveDvt(Thuoc_SanPham thuoc, String maDvt) {
        if (thuoc == null || thuoc.getDsCTDVT() == null) {
            return null;
        }
        return thuoc.getDsCTDVT().stream()
                .filter(ct -> ct.getDvt() != null && ct.getDvt().getMaDVT() != null && ct.getDvt().getMaDVT().equalsIgnoreCase(maDvt))
                .findFirst()
                .orElse(resolveDefaultDvt(thuoc));
    }

    private BigDecimal tinhThanhTien(ChiTietHoaDon row) {
        BigDecimal line = BigDecimal.valueOf(row.getDonGia()).multiply(BigDecimal.valueOf(row.getSoLuong()));
        return line.subtract(BigDecimal.valueOf(Math.max(0, row.getGiamGia()))).max(BigDecimal.ZERO);
    }

    private BigDecimal parseVndLabel(String text) {
        String digits = text == null ? "" : text.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(digits);
    }

    private void applyLoaiHoaDonUI(boolean isEtc) {
        txtMaDonThuoc.setDisable(!isEtc);
        txtMaDonThuoc.setEditable(isEtc);
        if (!isEtc) {
            txtMaDonThuoc.clear();
        }
    }

    private String formatVnd(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        decimalFormat.setGroupingUsed(true);
        return decimalFormat.format(Math.max(0, Math.round(value))) + " VND";
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Loi" : "Thong bao");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

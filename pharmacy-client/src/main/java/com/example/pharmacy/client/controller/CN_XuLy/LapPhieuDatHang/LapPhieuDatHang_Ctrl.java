package com.example.pharmacy.client.controller.CN_XuLy.LapPhieuDatHang;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.client.TienIch.VNDFormatter;
import com.example.pharmacy.client.controller.CN_DanhMuc.DMKhachHang.ThemKhachHang_Ctrl;
import com.example.pharmacy.client.controller.CN_TimKiem.TKKhachHang.TimKiemKhachHangTrongHD_Ctrl;
import com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuDatHang.ChiTietPhieuDatHang_Ctrl;
import com.example.pharmacy.common.model.ChiTietDonViTinh;
import com.example.pharmacy.common.model.ChiTietPhieuDatHang;
import com.example.pharmacy.common.model.KhachHang;
import com.example.pharmacy.common.model.PhieuDatHang;
import com.example.pharmacy.common.model.Thuoc_SanPham;
import com.example.pharmacy.client.service.PhieuDatHangService;
import com.example.pharmacy.client.service.ThuocService;
import com.example.pharmacy.client.session.SessionContext;
import com.example.pharmacy.client.view.CN_DanhMuc.DMKhachHang.ThemKhachHang_GUI;
import com.example.pharmacy.client.view.CN_TimKiem.TKKhachHang.TimKiemKhachHangTrongHD_GUI;
import com.example.pharmacy.client.view.CN_TimKiem.TKPhieuDatHang.ChiTietPhieuDatHang_GUI;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.IdentityHashMap;
import java.util.List;

public class LapPhieuDatHang_Ctrl {
    public Button btnLamMoi;
    public Button btnDatHangVaIn;
    public Pane searchPane;
    public TextField tfTimSanPham;
    public TableView<ChiTietPhieuDatHang> tbSanPham;
    public TableColumn<ChiTietPhieuDatHang, String> colSTT;
    public TableColumn<ChiTietPhieuDatHang, String> colTenSP;
    public TableColumn<ChiTietPhieuDatHang, String> colSoLuong;
    public TableColumn<ChiTietPhieuDatHang, String> colDonVi;
    public TableColumn<ChiTietPhieuDatHang, String> colDonGia;
    public TableColumn<ChiTietPhieuDatHang, String> colThanhTien;
    public TableColumn<ChiTietPhieuDatHang, String> colXoa;
    public Pane infoPane;
    public TextField tfMa;
    public DatePicker dpNgayLap;
    public TextField tfTenKH;
    public Label lbTongTien;
    public Label lbThue;
    public Label lbTongTT;
    public TextField tfTienCoc;
    public Label lbTienThieu;
    public TextArea tfGhiChu;
    public Button btnDatHang;
    public Button btnHuy;
    public TextField tfSDT;
    public TextField tfMaDon;
    public ComboBox<String> cbLoaiDon;
    public Button btnTimKH;
    public Button btnThemKH;

    public KhachHang khachHang;

    private final ContextMenu goiYMenu = new ContextMenu();
    private final PauseTransition pause = new PauseTransition(Duration.millis(250));
    private final ObservableList<ChiTietPhieuDatHang> dsChiTietPD = FXCollections.observableArrayList();
    private final IdentityHashMap<ChiTietPhieuDatHang, ChiTietDonViTinh> dvtTheoDong = new IdentityHashMap<>();
    private final ThuocService thuocService = new ThuocService();
    private final PhieuDatHangService phieuDatHangService = new PhieuDatHangService();
    private final VNDFormatter vndFormatter = new VNDFormatter();

    public void initialize() {
        if (tbSanPham != null) {
            tbSanPham.setItems(dsChiTietPD);
            tbSanPham.setPlaceholder(new Label("Chua co san pham nao"));
            cauHinhCotBang();
        }
        if (dpNgayLap != null) {
            dpNgayLap.setValue(LocalDate.now());
            dpNgayLap.setEditable(false);
        }
        if (tfMa != null) {
            tfMa.setEditable(false);
            tfMa.setText(phieuDatHangService.generateNewMaPhieuDatHang());
        }
        if (lbTongTien != null) lbTongTien.setText("0 VND");
        if (lbThue != null) lbThue.setText("0 VND");
        if (lbTongTT != null) lbTongTT.setText("0 VND");
        if (lbTienThieu != null) lbTienThieu.setText("0 VND");

        if (btnDatHang != null) btnDatHang.setOnAction(e -> onDatHang(false));
        if (btnDatHangVaIn != null) btnDatHangVaIn.setOnAction(e -> onDatHang(true));
        if (btnHuy != null) btnHuy.setOnAction(e -> onHuy());
        if (btnLamMoi != null) btnLamMoi.setOnAction(e -> resetForm());
        if (btnTimKH != null) btnTimKH.setOnAction(e -> xuLyTimKhachHang());
        if (btnThemKH != null) btnThemKH.setOnAction(e -> xuLyThemKH());

        loadCbLoaiDon();
        kiemTraLoaiDon();
        initTienCocEvents();
        xuLyGoiYSanPham();

        dsChiTietPD.addListener((ListChangeListener<ChiTietPhieuDatHang>) change -> capNhatTongTienPhieuDat());
    }

    public void loadCbLoaiDon() {
        if (cbLoaiDon == null) {
            return;
        }
        cbLoaiDon.getItems().setAll("Kê đơn", "Không kê đơn");
        cbLoaiDon.getSelectionModel().select("Không kê đơn");
    }

    public void kiemTraLoaiDon() {
        if (cbLoaiDon == null || tfMaDon == null) {
            return;
        }
        tfMaDon.setEditable(false);
        cbLoaiDon.setOnAction(e -> {
            boolean keDon = "Kê đơn".equalsIgnoreCase(cbLoaiDon.getSelectionModel().getSelectedItem());
            tfMaDon.setEditable(keDon);
            if (!keDon) {
                tfMaDon.clear();
            }
        });
    }

    private void xuLyGoiYSanPham() {
        if (tfTimSanPham == null) {
            return;
        }
        pause.setOnFinished(evt -> showSuggestions(tfTimSanPham.getText()));
        tfTimSanPham.textProperty().addListener((obs, oldText, newText) -> pause.playFromStart());
        tfTimSanPham.focusedProperty().addListener((obs, oldFocused, nowFocused) -> {
            if (!nowFocused) {
                Platform.runLater(goiYMenu::hide);
            }
        });
    }

    private void showSuggestions(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim();
        if (tuKhoa.isEmpty()) {
            goiYMenu.hide();
            return;
        }

        List<Thuoc_SanPham> results = thuocService.searchByKeyword(tuKhoa).stream().limit(5).toList();
        if (results.isEmpty()) {
            goiYMenu.hide();
            return;
        }

        goiYMenu.getItems().clear();
        goiYMenu.setStyle("-fx-min-width: 500; -fx-pref-width: 500;");
        for (Thuoc_SanPham sp : results) {
            String tenDvt = sp.getTenDVTCoBan() == null ? "" : sp.getTenDVTCoBan();
            Label nameLbl = new Label(sp.getTenThuoc());
            nameLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            Label infoLbl = new Label(tenDvt.isBlank() ? "" : " | " + tenDvt);
            infoLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            HBox row = new HBox(10, nameLbl, infoLbl, spacer);
            row.setStyle("-fx-padding: 8 14; -fx-min-height: 38;");

            CustomMenuItem item = new CustomMenuItem(row, true);
            item.setOnAction(ae -> {
                tfTimSanPham.clear();
                goiYMenu.hide();
                themVaoBang(sp, tenDvt);
            });
            goiYMenu.getItems().add(item);
        }

        if (!goiYMenu.isShowing()) {
            goiYMenu.show(tfTimSanPham, Side.BOTTOM, 0, 0);
        }
    }

    private void themVaoBang(Thuoc_SanPham sp, String tenDvtChon) {
        if (sp == null) {
            return;
        }
        ChiTietDonViTinh selected = chonDonViTheoTen(sp, tenDvtChon);
        if (selected == null || selected.getDvt() == null) {
            showAlert(Alert.AlertType.WARNING, "Can bo sung don vi tinh co ban cho thuoc truoc khi dat hang.");
            return;
        }

        for (ChiTietPhieuDatHang row : dsChiTietPD) {
            ChiTietDonViTinh currentDvt = dvtTheoDong.get(row);
            if (row.getThuoc() != null
                    && sp.getMaThuoc().equals(row.getThuoc().getMaThuoc())
                    && currentDvt != null
                    && currentDvt.getDvt() != null
                    && selected.getDvt().getMaDVT().equals(currentDvt.getDvt().getMaDVT())) {
                row.setSoLuong(row.getSoLuong() + 1);
                tbSanPham.refresh();
                capNhatTongTienPhieuDat();
                return;
            }
        }

        ChiTietPhieuDatHang detail = new ChiTietPhieuDatHang();
        detail.setThuoc(sp);
        detail.setSoLuong(1);
        detail.setDonGia(selected.getGiaBan());
        detail.setGiamGia(0.0);
        detail.setDvt(selected.getDvt().getMaDVT());
        detail.setTrangThai(false);
        dsChiTietPD.add(detail);
        dvtTheoDong.put(detail, selected);
        capNhatTongTienPhieuDat();
    }

    private ChiTietDonViTinh chonDonViTheoTen(Thuoc_SanPham sp, String tenDvt) {
        if (sp == null || sp.getDsCTDVT() == null || sp.getDsCTDVT().isEmpty()) {
            return null;
        }
        if (tenDvt != null && !tenDvt.isBlank()) {
            for (ChiTietDonViTinh ct : sp.getDsCTDVT()) {
                if (ct.getDvt() != null && tenDvt.equalsIgnoreCase(ct.getDvt().getTenDonViTinh())) {
                    return ct;
                }
            }
        }
        for (ChiTietDonViTinh ct : sp.getDsCTDVT()) {
            if (ct.isDonViCoBan()) {
                return ct;
            }
        }
        return sp.getDsCTDVT().get(0);
    }

    private void cauHinhCotBang() {
        colSTT.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(String.valueOf(tbSanPham.getItems().indexOf(cellData.getValue()) + 1))
        );
        colTenSP.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getThuoc() == null ? "" : cellData.getValue().getThuoc().getTenThuoc())
        );
        colDonVi.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(layTenDonVi(cellData.getValue())));
        colDonGia.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(formatVnd(cellData.getValue().getDonGia()))
        );
        colThanhTien.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(formatVnd(tinhThanhTien(cellData.getValue())))
        );

        colSoLuong.setCellFactory(tc -> new TableCell<>() {
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
                ChiTietPhieuDatHang row = getCurrentRow();
                if (row == null) {
                    return;
                }
                row.setSoLuong(Math.max(1, row.getSoLuong() + delta));
                tf.setText(String.valueOf(row.getSoLuong()));
                tbSanPham.refresh();
                capNhatTongTienPhieuDat();
            }

            private void commitFromText() {
                ChiTietPhieuDatHang row = getCurrentRow();
                if (row == null) {
                    return;
                }
                try {
                    row.setSoLuong(Math.max(1, Integer.parseInt(tf.getText().trim())));
                } catch (NumberFormatException ex) {
                    row.setSoLuong(Math.max(1, row.getSoLuong()));
                }
                tf.setText(String.valueOf(row.getSoLuong()));
                tbSanPham.refresh();
                capNhatTongTienPhieuDat();
            }

            private ChiTietPhieuDatHang getCurrentRow() {
                return getIndex() >= 0 && getIndex() < getTableView().getItems().size()
                        ? getTableView().getItems().get(getIndex())
                        : null;
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ChiTietPhieuDatHang row = getCurrentRow();
                    tf.setText(row == null ? "1" : String.valueOf(row.getSoLuong()));
                    setGraphic(box);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        });

        colXoa.setCellFactory(tc -> new TableCell<>() {
            private final Button btnXoa = new Button("X");

            {
                btnXoa.setStyle("-fx-background-color: #d80202; -fx-text-fill: white; -fx-font-weight: bold");
                btnXoa.setOnAction(e -> {
                    if (getIndex() < 0 || getIndex() >= tbSanPham.getItems().size()) {
                        return;
                    }
                    ChiTietPhieuDatHang row = tbSanPham.getItems().get(getIndex());
                    dsChiTietPD.remove(row);
                    dvtTheoDong.remove(row);
                    capNhatTongTienPhieuDat();
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

    private String layTenDonVi(ChiTietPhieuDatHang row) {
        ChiTietDonViTinh ct = dvtTheoDong.get(row);
        if (ct != null && ct.getDvt() != null) {
            return ct.getDvt().getTenDonViTinh();
        }
        return "";
    }

    private double tinhThanhTien(ChiTietPhieuDatHang row) {
        double thanhTien = row.getSoLuong() * row.getDonGia();
        if (row.getGiamGia() > 0) {
            thanhTien = thanhTien - row.getGiamGia();
        }
        return Math.max(0, thanhTien);
    }

    private void xuLyTimKhachHang() {
        Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
        TimKiemKhachHangTrongHD_Ctrl ctrl = new TimKiemKhachHangTrongHD_Ctrl();
        TimKiemKhachHangTrongHD_GUI gui = new TimKiemKhachHangTrongHD_GUI();
        gui.showWithController(stage, ctrl);
        ctrl.setOnSelected(kh -> {
            khachHang = kh;
            if (tfTenKH != null) tfTenKH.setText(kh.getTenKH());
            if (tfSDT != null) tfSDT.setText(kh.getSdt());
            stage.close();
        });
        stage.show();
    }

    private void xuLyThemKH() {
        Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
        ThemKhachHang_Ctrl ctrl = new ThemKhachHang_Ctrl();
        ThemKhachHang_GUI view = new ThemKhachHang_GUI();
        view.showWithController(stage, ctrl);
        ctrl.setOnSaved(kh -> {
            khachHang = kh;
            if (tfTenKH != null) tfTenKH.setText(kh.getTenKH());
            if (tfSDT != null) tfSDT.setText(kh.getSdt());
        });
        stage.initOwner(btnThemKH.getScene().getWindow());
        stage.show();
    }

    private void initTienCocEvents() {
        if (tfTienCoc == null) {
            return;
        }
        vndFormatter.applyNumberFormatter(tfTienCoc);
        tfTienCoc.textProperty().addListener((obs, oldVal, newVal) -> capNhatTongTienPhieuDat());
    }

    private void capNhatTongTienPhieuDat() {
        BigDecimal tongTienHang = BigDecimal.ZERO;
        for (ChiTietPhieuDatHang row : dsChiTietPD) {
            tongTienHang = tongTienHang.add(BigDecimal.valueOf(tinhThanhTien(row)));
        }
        BigDecimal vat = tongTienHang.multiply(new BigDecimal("0.05")).setScale(0, RoundingMode.HALF_UP);
        BigDecimal tongThanhToan = tongTienHang.add(vat);
        BigDecimal tienCoc = BigDecimal.valueOf(vndFormatter.parseFormattedNumber(tfTienCoc == null ? "" : tfTienCoc.getText()));
        BigDecimal conThieu = tongThanhToan.subtract(tienCoc).max(BigDecimal.ZERO);

        if (lbTongTien != null) lbTongTien.setText(formatVnd(tongTienHang.doubleValue()));
        if (lbThue != null) lbThue.setText(formatVnd(vat.doubleValue()));
        if (lbTongTT != null) lbTongTT.setText(formatVnd(tongThanhToan.doubleValue()));
        if (lbTienThieu != null) lbTienThieu.setText(formatVnd(conThieu.doubleValue()));
    }

    private void onDatHang(boolean yeuCauIn) {
        if (dsChiTietPD.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui long them it nhat mot san pham.");
            return;
        }
        if (khachHang == null || khachHang.getMaKH() == null || khachHang.getMaKH().isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Vui long chon khach hang truoc khi dat hang.");
            return;
        }
        if ("Kê đơn".equalsIgnoreCase(cbLoaiDon.getValue()) && (tfMaDon.getText() == null || tfMaDon.getText().isBlank())) {
            showAlert(Alert.AlertType.WARNING, "Da chon loai 'Ke don' nen can nhap ma don thuoc.");
            return;
        }

        PhieuDatHang phieuDatHang = new PhieuDatHang();
        phieuDatHang.setMaPDat(tfMa == null ? null : tfMa.getText());
        phieuDatHang.setNgayLap(Timestamp.valueOf((dpNgayLap == null || dpNgayLap.getValue() == null
                ? LocalDate.now()
                : dpNgayLap.getValue()).atStartOfDay()));
        phieuDatHang.setSoTienCoc(vndFormatter.parseFormattedNumber(tfTienCoc == null ? "" : tfTienCoc.getText()));
        phieuDatHang.setGhiChu(tfGhiChu == null ? null : tfGhiChu.getText());
        phieuDatHang.setKhachHang(khachHang);

        String maPhieuDat = phieuDatHangService.create(phieuDatHang, List.copyOf(dsChiTietPD), SessionContext.requireCurrentUser());
        PhieuDatHang saved = phieuDatHangService.findById(maPhieuDat);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thanh cong");
        alert.setHeaderText(null);
        alert.setContentText(yeuCauIn
                ? "Da lap phieu dat hang " + maPhieuDat + ". Chuc nang in phieu se noi tiep sau."
                : "Da lap phieu dat hang " + maPhieuDat + ".");
        alert.showAndWait();

        try {
            ChiTietPhieuDatHang_Ctrl detailCtrl = new ChiTietPhieuDatHang_Ctrl();
            ChiTietPhieuDatHang_GUI gui = new ChiTietPhieuDatHang_GUI();
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnDatHang.getScene().getWindow());
            detailCtrl.setPhieuDatHang(saved);
            gui.showWithController(stage, detailCtrl);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

        resetForm();
    }

    public void onHuy() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xac nhan huy");
        confirm.setHeaderText(null);
        confirm.setContentText("Ban co chac chan muon huy phieu dat hang khong?");
        if (confirm.showAndWait().filter(ButtonType.OK::equals).isPresent()) {
            resetForm();
        }
    }

    public void resetForm() {
        if (tfMa != null) tfMa.setText(phieuDatHangService.generateNewMaPhieuDatHang());
        if (dpNgayLap != null) dpNgayLap.setValue(LocalDate.now());
        if (tfTenKH != null) tfTenKH.clear();
        if (tfSDT != null) tfSDT.clear();
        if (tfTienCoc != null) tfTienCoc.clear();
        if (tfGhiChu != null) tfGhiChu.clear();
        if (tfMaDon != null) tfMaDon.clear();
        if (tfTimSanPham != null) tfTimSanPham.clear();
        khachHang = null;
        dsChiTietPD.clear();
        dvtTheoDong.clear();
        capNhatTongTienPhieuDat();
    }

    private String formatVnd(double value) {
        return String.format("%,.0f VND", value);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Loi" : "Thong bao");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

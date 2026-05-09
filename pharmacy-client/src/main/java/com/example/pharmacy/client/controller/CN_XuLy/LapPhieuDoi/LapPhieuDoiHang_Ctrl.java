package com.example.pharmacy.client.controller.CN_XuLy.LapPhieuDoi;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.client.controller.CN_DanhMuc.DMKhachHang.ThemKhachHang_Ctrl;
import com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuDoiHang.ChiTietPhieuDoiHang_Ctrl;
import com.example.pharmacy.common.model.ChiTietHoaDon;
import com.example.pharmacy.common.model.DonViTinh;
import com.example.pharmacy.common.model.HoaDon;
import com.example.pharmacy.common.model.PhieuDoiHang;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLo;
import com.example.pharmacy.client.service.DoiHangItem;
import com.example.pharmacy.client.service.DoiTraService;
import com.example.pharmacy.client.session.SessionContext;
import com.example.pharmacy.common.session.UserContext;
import com.example.pharmacy.client.view.CN_DanhMuc.DMKhachHang.ThemKhachHang_GUI;
import com.example.pharmacy.client.view.CN_TimKiem.TKPhieuDoi.ChiTietPhieuDoiHang_GUI;
import com.example.pharmacy.client.view.CN_XuLy.LapPhieuDoi.LapPhieuDoi_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.pharmacy.client.TienIch.TuyChinhAlert.hien;
import static com.example.pharmacy.client.TienIch.TuyChinhAlert.hoi;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;

public class LapPhieuDoiHang_Ctrl extends Application {
    public TableView<ChiTietHoaDon> tblSanPhamGoc;
    public TableColumn<ChiTietHoaDon, String> colSTTGoc;
    public TableColumn<ChiTietHoaDon, String> colTenSPGoc;
    public TableColumn<ChiTietHoaDon, String> colSoLuongGoc;
    public TableColumn<ChiTietHoaDon, String> colDonViGoc;
    public TableColumn<ChiTietHoaDon, String> colDonGiaGoc;
    public TableColumn<ChiTietHoaDon, String> colGiamGiaGoc;
    public TableColumn<ChiTietHoaDon, String> colThanhTienGoc;
    public TableColumn<ChiTietHoaDon, Void> colDoi;

    public TableView<DoiHangItem> tblSanPhamDoi;
    public TableColumn<DoiHangItem, String> colSTTDoi;
    public TableColumn<DoiHangItem, String> colTenSPDoi;
    public TableColumn<DoiHangItem, Number> colSoLuongDoi;
    public TableColumn<DoiHangItem, String> colDonViDoi;
    public TableColumn<DoiHangItem, String> colLyDo;
    public TableColumn<DoiHangItem, Void> colBo;

    public TextField txtTimHoaDonGoc;
    public Button btnTimHD;
    public Button btnDoi;
    public Button btnHuy;
    public TextField lblMaHDGoc;
    public TextField lblTenKH;
    public TextField lblSDT;
    public DatePicker dpNgayLapPhieu;
    public TextArea txtGhiChu;

    private final ObservableList<ChiTietHoaDon> dsGoc = FXCollections.observableArrayList();
    private final ObservableList<DoiHangItem> dsDoi = FXCollections.observableArrayList();
    private final Map<String, DoiHangItem> doiByKey = new HashMap<>();
    private final DoiTraService doiTraService = new DoiTraService();

    private HoaDon hoaDonGoc;
    private boolean initialized;

    @Override
    public void start(Stage stage) {
        new LapPhieuDoi_GUI().showWithController(stage, this);
        stage.show();
    }

    public void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;

        if (dpNgayLapPhieu != null) {
            dpNgayLapPhieu.setValue(LocalDate.now());
        }
        if (txtTimHoaDonGoc != null) {
            txtTimHoaDonGoc.setOnAction(e -> xuLyTimHoaDonGoc());
        }
        if (btnTimHD != null) {
            btnTimHD.setOnAction(e -> xuLyTimHoaDonGoc());
        }
        if (btnDoi != null) {
            btnDoi.setOnAction(e -> xuLyDoiHang());
        }
        if (btnHuy != null) {
            btnHuy.setOnAction(e -> xuLyHuy());
        }

        Platform.runLater(() -> {
            if (txtTimHoaDonGoc != null && txtTimHoaDonGoc.getParent() != null) {
                txtTimHoaDonGoc.getParent().requestFocus();
            }
        });

        if (tblSanPhamGoc != null) {
            tblSanPhamGoc.setItems(dsGoc);
            tblSanPhamGoc.setPlaceholder(new Label("Chua co san pham goc"));
            safeSetConstrainedResize(tblSanPhamGoc);
        }
        if (tblSanPhamDoi != null) {
            tblSanPhamDoi.setItems(dsDoi);
            tblSanPhamDoi.setEditable(true);
            tblSanPhamDoi.setPlaceholder(new Label("Chua co chi tiet doi hang"));
            safeSetConstrainedResize(tblSanPhamDoi);
        }

        setupTblGocColumns();
        setupTblDoiColumns();
    }

    private void safeSetConstrainedResize(TableView<?> tableView) {
        try {
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (Exception ignored) {
        }
    }

    private void setupTblGocColumns() {
        tblSanPhamGoc.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(ChiTietHoaDon item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("");
                    return;
                }
                setStyle(tinhSoLuongConDoi(item) <= 0
                        ? "-fx-background-color: #ffe6e6; -fx-text-fill: #b00020;"
                        : "");
            }
        });

        if (colSTTGoc != null) {
            colSTTGoc.setCellFactory(tc -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : String.valueOf(getIndex() + 1));
                }
            });
        }
        if (colTenSPGoc != null) {
            colTenSPGoc.setCellValueFactory(cd -> Bindings.createStringBinding(() -> tenSP(cd.getValue())));
        }
        if (colSoLuongGoc != null) {
            colSoLuongGoc.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(String.valueOf(cd.getValue().getSoLuong())));
            alignRight(colSoLuongGoc);
        }
        if (colDonViGoc != null) {
            colDonViGoc.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(tenDonVi(cd.getValue())));
        }
        if (colDonGiaGoc != null) {
            colDonGiaGoc.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(vnd(cd.getValue().getDonGia())));
            alignRight(colDonGiaGoc);
        }
        if (colGiamGiaGoc != null) {
            colGiamGiaGoc.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(vnd(cd.getValue().getGiamGia())));
            alignRight(colGiamGiaGoc);
        }
        if (colThanhTienGoc != null) {
            colThanhTienGoc.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(vnd(cd.getValue().tinhThanhTien())));
            alignRight(colThanhTienGoc);
        }
        if (colDoi != null) {
            colDoi.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(null));
            colDoi.setCellFactory(tc -> new TableCell<>() {
                private final Button btn = new Button("↓");

                {
                    btn.getStyleClass().add("btn-doi");
                    setStyle("-fx-alignment: center;");
                    btn.setOnAction(e -> {
                        ChiTietHoaDon chiTietHoaDon = getTableView().getItems().get(getIndex());
                        if (chiTietHoaDon == null) {
                            return;
                        }
                        if (tinhSoLuongConDoi(chiTietHoaDon) <= 0) {
                            hien(WARNING, "San pham da doi het", "San pham nay da duoc doi het so luong cho phep.");
                            return;
                        }
                        xuLyChuyenSangDoi(chiTietHoaDon);
                        tblSanPhamGoc.refresh();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        return;
                    }
                    ChiTietHoaDon chiTietHoaDon = getTableView().getItems().get(getIndex());
                    int slConDoi = tinhSoLuongConDoi(chiTietHoaDon);
                    btn.setDisable(slConDoi <= 0);
                    btn.setOpacity(slConDoi <= 0 ? 0.4 : 1.0);
                    btn.setTooltip(new Tooltip(slConDoi <= 0
                            ? "San pham da duoc doi het"
                            : "Chuyen sang danh sach doi"));
                    setGraphic(btn);
                }
            });
        }
    }

    private void setupTblDoiColumns() {
        if (colSTTDoi != null) {
            colSTTDoi.setCellFactory(tc -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : String.valueOf(getIndex() + 1));
                }
            });
        }
        if (colTenSPDoi != null) {
            colTenSPDoi.setCellValueFactory(cd -> Bindings.createStringBinding(() -> tenSP(cd.getValue().getGoc())));
        }
        if (colDonViDoi != null) {
            colDonViDoi.setCellValueFactory(cd -> Bindings.createStringBinding(() -> tenDonVi(cd.getValue().getGoc())));
        }
        if (colSoLuongDoi != null) {
            colSoLuongDoi.setCellValueFactory(cd -> cd.getValue().soLuongDoiProperty());
            colSoLuongDoi.setCellFactory(tc -> new TableCell<>() {
                private final Button btnMinus = new Button("-");
                private final Button btnPlus = new Button("+");
                private final TextField tf = new TextField();
                private final HBox box = new HBox(6, btnMinus, tf, btnPlus);

                {
                    tf.setPrefWidth(56);
                    tf.setMaxWidth(56);
                    tf.setStyle("-fx-alignment: center-right;");
                    tf.setTextFormatter(new TextFormatter<>(change ->
                            change.getControlNewText().matches("\\d{0,6}") ? change : null));
                    btnMinus.setOnAction(e -> adjust(-1));
                    btnPlus.setOnAction(e -> adjust(1));
                    tf.setOnAction(e -> commitFromText());
                    tf.focusedProperty().addListener((obs, oldValue, newValue) -> {
                        if (!newValue) {
                            commitFromText();
                        }
                    });
                }

                private void adjust(int delta) {
                    DoiHangItem item = getCurrentItem();
                    if (item == null) {
                        return;
                    }
                    int max = maxSoLuongCoTheChon(item);
                    int target = Math.max(1, Math.min(max, item.getSoLuongDoi() + delta));
                    item.setSoLuongDoi(target);
                    tf.setText(String.valueOf(target));
                    tblSanPhamGoc.refresh();
                }

                private void commitFromText() {
                    DoiHangItem item = getCurrentItem();
                    if (item == null) {
                        return;
                    }
                    try {
                        int value = Integer.parseInt(tf.getText());
                        int max = maxSoLuongCoTheChon(item);
                        value = Math.max(1, Math.min(max, value));
                        item.setSoLuongDoi(value);
                        tf.setText(String.valueOf(value));
                        tblSanPhamGoc.refresh();
                    } catch (NumberFormatException exception) {
                        tf.setText(String.valueOf(item.getSoLuongDoi()));
                    }
                }

                private DoiHangItem getCurrentItem() {
                    int index = getIndex();
                    if (index < 0 || index >= tblSanPhamDoi.getItems().size()) {
                        return null;
                    }
                    return tblSanPhamDoi.getItems().get(index);
                }

                @Override
                protected void updateItem(Number item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                        return;
                    }
                    DoiHangItem doiHangItem = getCurrentItem();
                    if (doiHangItem != null) {
                        tf.setText(String.valueOf(doiHangItem.getSoLuongDoi()));
                    }
                    setGraphic(box);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            });
        }
        if (colLyDo != null) {
            colLyDo.setCellValueFactory(cd -> cd.getValue().lyDoProperty());
            colLyDo.setCellFactory(tc -> {
                TextFieldTableCell<DoiHangItem, String> cell = new TextFieldTableCell<>(new DefaultStringConverter());
                cell.setAlignment(Pos.CENTER_LEFT);
                cell.setStyle("-fx-padding: 0 6 0 6;");
                return cell;
            });
            colLyDo.setOnEditCommit(event -> {
                DoiHangItem item = event.getRowValue();
                if (item != null) {
                    item.setLyDo(event.getNewValue());
                }
            });
        }
        if (colBo != null) {
            colBo.setCellFactory(tc -> new TableCell<>() {
                private final Button btn = new Button("✕");

                {
                    btn.getStyleClass().add("btn-bo");
                    btn.setOnAction(e -> {
                        DoiHangItem item = getCurrentItem();
                        if (item == null || item.getGoc() == null) {
                            return;
                        }
                        doiByKey.remove(keyDoi(item.getGoc()));
                        dsDoi.remove(item);
                        tblSanPhamGoc.refresh();
                    });
                }

                private DoiHangItem getCurrentItem() {
                    int index = getIndex();
                    if (index < 0 || index >= tblSanPhamDoi.getItems().size()) {
                        return null;
                    }
                    return tblSanPhamDoi.getItems().get(index);
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : btn);
                }
            });
        }
    }

    public void xuLyTimHoaDonGoc() {
        String maHoaDon = txtTimHoaDonGoc == null ? "" : txtTimHoaDonGoc.getText().trim();
        if (maHoaDon.isBlank()) {
            hien(WARNING, "Ma hoa don goc khong the trong", "Vui long nhap ma hoa don goc.");
            return;
        }

        try {
            HoaDon hd = doiTraService.findHoaDonGocForDoiTra(maHoaDon);
            if (hd == null) {
                hien(WARNING, "Khong tim thay hoa don goc", "Vui long kiem tra lai ma hoa don.");
                return;
            }
            List<ChiTietHoaDon> lines = doiTraService.findHoaDonDetailsForDoiTra(maHoaDon);
            if (lines.isEmpty()) {
                hien(WARNING, "Khong co san pham", "Hoa don khong co chi tiet.");
                return;
            }

            LocalDate ngayLapHoaDon = hd.getNgayLap().toLocalDateTime().toLocalDate();
            if (ngayLapHoaDon.isBefore(LocalDate.now().minusDays(7))) {
                hien(WARNING, "Hoa don da qua han 7 ngay", "Vui long kiem tra lai.");
                return;
            }

            this.hoaDonGoc = hd;
            dsDoi.clear();
            doiByKey.clear();
            dsGoc.setAll(lines);
            fillInvoiceInfo(hd);

            if (chuaCoKhachHang(hd)) {
                hien(WARNING,
                        "Hoa don chua co khach hang",
                        "Hoa don nay la khach le. Vui long bo sung thong tin khach hang de tiep tuc.");
                moFormThemKhachHang(hd);
                return;
            }

            if (hoaDonDaDoiHet()) {
                hien(WARNING, "Hoa don da doi het hang", "Tat ca san pham trong hoa don nay da duoc doi het.");
            }

            tblSanPhamGoc.refresh();
        } catch (Exception exception) {
            exception.printStackTrace();
            hien(ERROR, "Loi tai hoa don goc", "Khong the tai hoa don goc. Vui long thu lai.");
        }
    }

    public void xuLyDoiHang() {
        if (hoaDonGoc == null || hoaDonGoc.getMaHD() == null) {
            hien(WARNING, "Chua chon hoa don goc", "Vui long tim hoa don goc truoc khi doi hang.");
            return;
        }
        if (dsDoi.isEmpty()) {
            hien(WARNING, "Danh sach doi hang trong", "Vui long them san pham de doi.");
            return;
        }
        if (chuaCoKhachHang(hoaDonGoc)) {
            hien(WARNING, "Chua co khach hang", "Vui long bo sung thong tin khach hang truoc khi doi / tra.");
            moFormThemKhachHang(hoaDonGoc);
            return;
        }
        for (DoiHangItem item : dsDoi) {
            if (item.getLyDo() == null || item.getLyDo().isBlank()) {
                hien(WARNING, "Thieu ly do doi hang", "Vui long nhap ly do doi cho san pham: " + tenSP(item.getGoc()));
                tblSanPhamDoi.getSelectionModel().select(item);
                tblSanPhamDoi.scrollTo(item);
                return;
            }
        }

        UserContext currentUser = SessionContext.getCurrentUser();
        if (currentUser == null) {
            hien(ERROR, "Loi dang nhap", "Chua xac dinh nhan vien dang dang nhap.");
            return;
        }

        try {
            String maPhieuDoi = doiTraService.createPhieuDoi(
                    doiTraService.buildCreatePhieuDoiRequest(hoaDonGoc, List.copyOf(dsDoi), txtGhiChu.getText()),
                    currentUser
            );

            Optional<javafx.scene.control.ButtonType> result = hoi(
                    "Thanh cong",
                    "Lap phieu doi hang thanh cong",
                    "Ban co muon xem chi tiet phieu doi hang khong?"
            );
            if (result.orElse(javafx.scene.control.ButtonType.CANCEL).getButtonData()
                    == javafx.scene.control.ButtonBar.ButtonData.OK_DONE) {
                moChiTietPhieuDoi(maPhieuDoi);
            }
            xuLyHuy();
        } catch (Exception exception) {
            exception.printStackTrace();
            hien(ERROR, "Loi khi doi hang",
                    exception.getMessage() == null ? "Da xay ra loi trong qua trinh doi hang." : exception.getMessage());
        }
    }

    public void xuLyHuy() {
        dsDoi.clear();
        dsGoc.clear();
        doiByKey.clear();
        hoaDonGoc = null;
        if (lblTenKH != null) {
            lblTenKH.clear();
        }
        if (lblSDT != null) {
            lblSDT.clear();
        }
        if (lblMaHDGoc != null) {
            lblMaHDGoc.clear();
        }
        if (txtGhiChu != null) {
            txtGhiChu.clear();
        }
        if (txtTimHoaDonGoc != null) {
            txtTimHoaDonGoc.clear();
            txtTimHoaDonGoc.setPromptText("Nhap ma hoa don goc...");
        }
        if (dpNgayLapPhieu != null) {
            dpNgayLapPhieu.setValue(LocalDate.now());
        }
    }

    private void xuLyChuyenSangDoi(ChiTietHoaDon chiTietHoaDon) {
        int slConDoi = tinhSoLuongConDoi(chiTietHoaDon);
        if (slConDoi <= 0) {
            hien(WARNING, "San pham da doi het", "San pham nay da duoc doi het so luong cho phep.");
            return;
        }

        String key = keyDoi(chiTietHoaDon);
        DoiHangItem item = doiByKey.get(key);
        if (item == null) {
            item = new DoiHangItem(chiTietHoaDon, 1, "");
            dsDoi.add(item);
            doiByKey.put(key, item);
        } else {
            int max = maxSoLuongCoTheChon(item);
            item.setSoLuongDoi(Math.min(max, item.getSoLuongDoi() + 1));
        }
        tblSanPhamDoi.refresh();
    }

    private void moFormThemKhachHang(HoaDon hd) {
        try {
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.setTitle("Them khach hang");
            stage.initOwner(btnTimHD.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);

            ThemKhachHang_Ctrl ctrl = new ThemKhachHang_Ctrl();
            ctrl.setOnSaved(khachHang -> {
                UserContext actor = SessionContext.getCurrentUser();
                doiTraService.attachKhachHangToHoaDon(hd.getMaHD(), khachHang.getMaKH(), actor);
                HoaDon updated = doiTraService.findHoaDonGocForDoiTra(hd.getMaHD());
                if (updated != null) {
                    hoaDonGoc = updated;
                    fillInvoiceInfo(updated);
                }
                hien(INFORMATION, "Thanh cong", "Da bo sung thong tin khach hang.");
            });

            new ThemKhachHang_GUI().showWithController(stage, ctrl);
            stage.showAndWait();
        } catch (Exception exception) {
            exception.printStackTrace();
            hien(ERROR, "Loi", "Khong the mo form them khach hang");
        }
    }

    private void moChiTietPhieuDoi(String maPhieuDoi) {
        try {
            PhieuDoiHang phieuDoiHang = doiTraService.findPhieuDoiById(maPhieuDoi);
            if (phieuDoiHang == null) {
                hien(ERROR, "Loi", "Khong tim thay phieu doi vua tao.");
                return;
            }
            ChiTietPhieuDoiHang_Ctrl ctrl = new ChiTietPhieuDoiHang_Ctrl();
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.setTitle("Chi tiet phieu doi " + phieuDoiHang.getMaPD());
            new ChiTietPhieuDoiHang_GUI().showWithController(stage, ctrl);
            ctrl.setPhieuDoiHang(phieuDoiHang);
        } catch (Exception exception) {
            exception.printStackTrace();
            hien(ERROR, "Loi", "Khong the mo chi tiet phieu doi hang.");
        }
    }

    private void fillInvoiceInfo(HoaDon hd) {
        if (lblMaHDGoc != null) {
            lblMaHDGoc.setText(safe(hd.getMaHD()));
        }
        if (lblTenKH != null) {
            lblTenKH.setText(hd.getMaKH() == null ? "" : safe(hd.getMaKH().getTenKH()));
        }
        if (lblSDT != null) {
            lblSDT.setText(hd.getMaKH() == null ? "" : safe(hd.getMaKH().getSdt()));
        }
        if (dpNgayLapPhieu != null) {
            dpNgayLapPhieu.setValue(LocalDate.now());
        }
    }

    private boolean hoaDonDaDoiHet() {
        return dsGoc.stream().noneMatch(line -> tinhSoLuongConDoi(line) > 0);
    }

    private int tinhSoLuongConDoi(ChiTietHoaDon chiTietHoaDon) {
        if (chiTietHoaDon == null || chiTietHoaDon.getHoaDon() == null || chiTietHoaDon.getLoHang() == null || chiTietHoaDon.getDvt() == null) {
            return 0;
        }
        int daDoi = doiTraService.getSoLuongDaDoi(
                chiTietHoaDon.getHoaDon().getMaHD(),
                chiTietHoaDon.getLoHang().getMaLH(),
                chiTietHoaDon.getDvt().getMaDVT()
        );
        int dangChon = dsDoi.stream()
                .filter(item -> sameInvoiceLine(item.getGoc(), chiTietHoaDon))
                .mapToInt(DoiHangItem::getSoLuongDoi)
                .sum();
        return Math.max(0, chiTietHoaDon.getSoLuong() - daDoi - dangChon);
    }

    private int maxSoLuongCoTheChon(DoiHangItem currentItem) {
        ChiTietHoaDon goc = currentItem.getGoc();
        if (goc == null || goc.getHoaDon() == null || goc.getLoHang() == null || goc.getDvt() == null) {
            return currentItem.getSoLuongDoi();
        }
        int daDoi = doiTraService.getSoLuongDaDoi(
                goc.getHoaDon().getMaHD(),
                goc.getLoHang().getMaLH(),
                goc.getDvt().getMaDVT()
        );
        int dangChonKhac = dsDoi.stream()
                .filter(item -> item != currentItem && sameInvoiceLine(item.getGoc(), goc))
                .mapToInt(DoiHangItem::getSoLuongDoi)
                .sum();
        return Math.max(1, goc.getSoLuong() - daDoi - dangChonKhac);
    }

    private boolean sameInvoiceLine(ChiTietHoaDon left, ChiTietHoaDon right) {
        if (left == null || right == null || left.getHoaDon() == null || right.getHoaDon() == null
                || left.getLoHang() == null || right.getLoHang() == null
                || left.getDvt() == null || right.getDvt() == null) {
            return false;
        }
        return safe(left.getHoaDon().getMaHD()).equalsIgnoreCase(safe(right.getHoaDon().getMaHD()))
                && safe(left.getLoHang().getMaLH()).equalsIgnoreCase(safe(right.getLoHang().getMaLH()))
                && safe(left.getDvt().getMaDVT()).equalsIgnoreCase(safe(right.getDvt().getMaDVT()));
    }

    private boolean chuaCoKhachHang(HoaDon hd) {
        return hd == null || hd.getMaKH() == null || safe(hd.getMaKH().getMaKH()).isBlank();
    }

    private String keyDoi(ChiTietHoaDon chiTietHoaDon) {
        return safe(chiTietHoaDon.getHoaDon().getMaHD()) + "_"
                + safe(chiTietHoaDon.getLoHang().getMaLH()) + "_"
                + safe(chiTietHoaDon.getDvt().getMaDVT());
    }

    private String tenSP(ChiTietHoaDon row) {
        if (row == null || row.getLoHang() == null) {
            return "";
        }
        Thuoc_SP_TheoLo lo = row.getLoHang();
        return lo.getThuoc() != null ? safe(lo.getThuoc().getTenThuoc()) : "";
    }

    private String tenDonVi(ChiTietHoaDon row) {
        if (row == null) {
            return "";
        }
        DonViTinh donViTinh = row.getDvt();
        if (donViTinh != null && donViTinh.getTenDonViTinh() != null) {
            return donViTinh.getTenDonViTinh();
        }
        return "";
    }

    private String vnd(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(Math.round(Math.max(0, value))) + " VND";
    }

    private <S, T> void alignRight(TableColumn<S, T> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(item));
                setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
            }
        });
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}

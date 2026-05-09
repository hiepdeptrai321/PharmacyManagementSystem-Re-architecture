package com.example.pharmacy.client.controller.CN_XuLy.LapPhieuTra;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.client.controller.CN_DanhMuc.DMKhachHang.ThemKhachHang_Ctrl;
import com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuTraHang.ChiTietPhieuTraHang_Ctrl;
import com.example.pharmacy.common.model.ChiTietHoaDon;
import com.example.pharmacy.common.model.DonViTinh;
import com.example.pharmacy.common.model.HoaDon;
import com.example.pharmacy.common.model.KhachHang;
import com.example.pharmacy.common.model.PhieuTraHang;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLo;
import com.example.pharmacy.client.service.DoiTraService;
import com.example.pharmacy.client.service.TraHangItem;
import com.example.pharmacy.client.session.SessionContext;
import com.example.pharmacy.common.session.UserContext;
import com.example.pharmacy.client.view.CN_DanhMuc.DMKhachHang.ThemKhachHang_GUI;
import com.example.pharmacy.client.view.CN_TimKiem.TKPhieuTra.ChiTietPhieuTraHang_GUI;
import com.example.pharmacy.client.view.CN_XuLy.LapPhieuTra.LapPhieuTra_GUI;
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

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.pharmacy.client.TienIch.TuyChinhAlert.hien;
import static com.example.pharmacy.client.TienIch.TuyChinhAlert.hoi;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;

public class LapPhieuTraHang_Ctrl extends Application {
    public TextField lblMaHDGoc;
    public TextField lblTenKH;
    public TextField lblSDT;
    public DatePicker dpNgayLapPhieu;
    public Label lblTongTienGoc;
    public Label lblTongTienTraLai;
    public Label lblVAT;
    public Label lblSoTienTraLai;
    public TextArea txtGhiChu;
    public TextField txtTimHoaDon;
    public Button btnTimHoaDon;
    public Button btnTraHang;
    public Button btnHuy;

    public TableView<ChiTietHoaDon> tblSanPhamHoaDon;
    public TableColumn<ChiTietHoaDon, String> colSTTGoc;
    public TableColumn<ChiTietHoaDon, String> colTenSPGoc;
    public TableColumn<ChiTietHoaDon, String> colSLGoc;
    public TableColumn<ChiTietHoaDon, String> colDonViGoc;
    public TableColumn<ChiTietHoaDon, String> colDonGiaGoc;
    public TableColumn<ChiTietHoaDon, String> colGiamGiaGoc;
    public TableColumn<ChiTietHoaDon, String> colThanhTienGoc;
    public TableColumn<ChiTietHoaDon, Void> colTra;

    public TableView<TraHangItem> tblChiTietTraHang;
    public TableColumn<TraHangItem, String> colSTTTra;
    public TableColumn<TraHangItem, String> colTenSPTra;
    public TableColumn<TraHangItem, Number> colSLTra;
    public TableColumn<TraHangItem, String> colLyDo;
    public TableColumn<TraHangItem, String> colDonViTra;
    public TableColumn<TraHangItem, Double> colDonGiaTra;
    public TableColumn<TraHangItem, Double> colThanhTienTra;
    public TableColumn<TraHangItem, Void> colBo;

    private final ObservableList<ChiTietHoaDon> dsGoc = FXCollections.observableArrayList();
    private final ObservableList<TraHangItem> dsTra = FXCollections.observableArrayList();
    private final Map<String, TraHangItem> traByKey = new HashMap<>();
    private final DoiTraService doiTraService = new DoiTraService();

    private HoaDon hoaDonGoc;
    private boolean initialized;

    @Override
    public void start(Stage stage) {
        new LapPhieuTra_GUI().showWithController(stage, this);
        stage.show();
    }

    public void initialize(URL location, ResourceBundle resources) {
        initialize();
    }

    public void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;

        if (dpNgayLapPhieu != null) {
            dpNgayLapPhieu.setValue(LocalDate.now());
        }
        if (btnTimHoaDon != null) {
            btnTimHoaDon.setOnAction(e -> xuLyTimHDGoc());
        }
        if (txtTimHoaDon != null) {
            txtTimHoaDon.setOnAction(e -> xuLyTimHDGoc());
        }
        if (btnTraHang != null) {
            btnTraHang.setOnAction(e -> xuLyTraHang());
        }
        if (btnHuy != null) {
            btnHuy.setOnAction(e -> xuLyHuy());
        }

        Platform.runLater(() -> {
            if (txtTimHoaDon != null && txtTimHoaDon.getParent() != null) {
                txtTimHoaDon.getParent().requestFocus();
            }
        });

        if (tblSanPhamHoaDon != null) {
            tblSanPhamHoaDon.setItems(dsGoc);
            tblSanPhamHoaDon.setPlaceholder(new Label("Chua co san pham goc"));
            safeSetConstrainedResize(tblSanPhamHoaDon);
        }
        if (tblChiTietTraHang != null) {
            tblChiTietTraHang.setItems(dsTra);
            tblChiTietTraHang.setEditable(true);
            tblChiTietTraHang.setPlaceholder(new Label("Chua co chi tiet tra hang"));
            safeSetConstrainedResize(tblChiTietTraHang);
        }

        setupTblGocColumns();
        setupTblTraColumns();
    }

    private void safeSetConstrainedResize(TableView<?> tableView) {
        try {
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (Exception ignored) {
        }
    }

    private void setupTblGocColumns() {
        tblSanPhamHoaDon.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(ChiTietHoaDon item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("");
                    return;
                }
                setStyle(tinhSoLuongConTra(item) <= 0
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
        if (colSLGoc != null) {
            colSLGoc.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(String.valueOf(cd.getValue().getSoLuong())));
            alignRight(colSLGoc);
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
        if (colTra != null) {
            colTra.setCellFactory(tc -> new TableCell<>() {
                private final Button btn = new Button("↓");

                {
                    btn.getStyleClass().add("btn-doi");
                    setStyle("-fx-alignment: center;");
                    btn.setOnAction(e -> {
                        ChiTietHoaDon goc = getTableView().getItems().get(getIndex());
                        if (goc == null) {
                            return;
                        }
                        if (tinhSoLuongConTra(goc) <= 0) {
                            hien(WARNING, "Khong the tra", "San pham nay da duoc tra het.");
                            return;
                        }
                        xuLyChuyenSangTra(goc);
                        getTableView().refresh();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        return;
                    }
                    ChiTietHoaDon goc = getTableView().getItems().get(getIndex());
                    int slConTra = tinhSoLuongConTra(goc);
                    btn.setDisable(slConTra <= 0);
                    btn.setOpacity(slConTra <= 0 ? 0.5 : 1.0);
                    btn.setTooltip(new Tooltip(slConTra <= 0
                            ? "San pham da duoc tra het"
                            : "Chuyen sang danh sach tra"));
                    setGraphic(btn);
                }
            });
        }
    }

    private void setupTblTraColumns() {
        if (colSTTTra != null) {
            colSTTTra.setCellFactory(tc -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : String.valueOf(getIndex() + 1));
                }
            });
        }
        if (colTenSPTra != null) {
            colTenSPTra.setCellValueFactory(cd -> Bindings.createStringBinding(() -> tenSP(cd.getValue().getGoc())));
        }
        if (colDonViTra != null) {
            colDonViTra.setCellValueFactory(cd -> Bindings.createStringBinding(() -> tenDonVi(cd.getValue().getGoc())));
        }
        if (colSLTra != null) {
            colSLTra.setCellValueFactory(cd -> cd.getValue().soLuongTraProperty());
            colSLTra.setCellFactory(tc -> new TableCell<>() {
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
                    TraHangItem vm = getCurrentItem();
                    if (vm == null) {
                        return;
                    }
                    int max = maxSoLuongCoTheChon(vm);
                    int target = Math.max(1, Math.min(max, vm.getSoLuongTra() + delta));
                    vm.setSoLuongTra(target);
                    tf.setText(String.valueOf(target));
                    capNhatTienTra();
                    tblSanPhamHoaDon.refresh();
                }

                private void commitFromText() {
                    TraHangItem vm = getCurrentItem();
                    if (vm == null) {
                        return;
                    }
                    try {
                        int value = Integer.parseInt(tf.getText());
                        int max = maxSoLuongCoTheChon(vm);
                        value = Math.max(1, Math.min(max, value));
                        vm.setSoLuongTra(value);
                        tf.setText(String.valueOf(value));
                        capNhatTienTra();
                        tblSanPhamHoaDon.refresh();
                    } catch (NumberFormatException exception) {
                        tf.setText(String.valueOf(vm.getSoLuongTra()));
                    }
                }

                private TraHangItem getCurrentItem() {
                    int index = getIndex();
                    if (index < 0 || index >= tblChiTietTraHang.getItems().size()) {
                        return null;
                    }
                    return tblChiTietTraHang.getItems().get(index);
                }

                @Override
                protected void updateItem(Number item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                        return;
                    }
                    TraHangItem vm = getCurrentItem();
                    if (vm != null) {
                        tf.setText(String.valueOf(vm.getSoLuongTra()));
                    }
                    setGraphic(box);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            });
        }
        if (colDonGiaTra != null) {
            colDonGiaTra.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getDonGiaUnit()));
            colDonGiaTra.setCellFactory(tc -> new TableCell<>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : vnd(item));
                    setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
                }
            });
        }
        if (colThanhTienTra != null) {
            colThanhTienTra.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getThanhTienTra()));
            colThanhTienTra.setCellFactory(tc -> new TableCell<>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : vnd(item));
                    setStyle(empty ? "" : "-fx-alignment: CENTER-RIGHT;");
                }
            });
        }
        if (colLyDo != null) {
            colLyDo.setCellValueFactory(cd -> cd.getValue().lyDoProperty());
            colLyDo.setCellFactory(tc -> {
                TextFieldTableCell<TraHangItem, String> cell = new TextFieldTableCell<>(new DefaultStringConverter());
                cell.setAlignment(Pos.CENTER_LEFT);
                cell.setStyle("-fx-padding: 0 6 0 6;");
                return cell;
            });
            colLyDo.setOnEditCommit(event -> {
                TraHangItem item = event.getRowValue();
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
                        TraHangItem item = getCurrentItem();
                        if (item == null || item.getGoc() == null) {
                            return;
                        }
                        traByKey.remove(keyTra(item.getGoc()));
                        dsTra.remove(item);
                        capNhatTienTra();
                        tblSanPhamHoaDon.refresh();
                    });
                }

                private TraHangItem getCurrentItem() {
                    int index = getIndex();
                    if (index < 0 || index >= tblChiTietTraHang.getItems().size()) {
                        return null;
                    }
                    return tblChiTietTraHang.getItems().get(index);
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : btn);
                }
            });
        }
    }

    public void xuLyTimHDGoc() {
        String maHoaDon = txtTimHoaDon == null ? "" : txtTimHoaDon.getText().trim();
        if (maHoaDon.isBlank()) {
            hien(WARNING, "Thieu thong tin", "Vui long nhap ma hoa don.");
            return;
        }

        try {
            HoaDon hd = doiTraService.findHoaDonGocForDoiTra(maHoaDon);
            if (hd == null) {
                hien(WARNING, "Khong tim thay", "Hoa don khong ton tai.");
                return;
            }

            List<ChiTietHoaDon> lines = doiTraService.findHoaDonDetailsForDoiTra(maHoaDon);
            if (lines.isEmpty()) {
                hien(WARNING, "Khong co san pham", "Hoa don khong co chi tiet.");
                return;
            }

            if (lines.stream().anyMatch(detail -> detail.getGiamGia() > 0)) {
                hien(WARNING,
                        "Hoa don co ap dung giam gia",
                        "Khong the thuc hien tra hang voi hoa don nay.");
                return;
            }

            LocalDate ngayLapHoaDon = hd.getNgayLap().toLocalDateTime().toLocalDate();
            if (ngayLapHoaDon.isBefore(LocalDate.now().minusDays(7))) {
                hien(WARNING, "Qua han", "Hoa don da qua 7 ngay, khong the tra hang.");
                return;
            }

            this.hoaDonGoc = hd;
            dsTra.clear();
            traByKey.clear();
            dsGoc.setAll(lines);

            fillInvoiceInfo(hd);

            if (chuaCoKhachHang(hd)) {
                hien(WARNING,
                        "Hoa don chua co khach hang",
                        "Hoa don nay la khach le. Vui long bo sung thong tin khach hang de tiep tuc.");
                moFormThemKhachHang(hd);
                return;
            }

            boolean conTraDuoc = lines.stream().anyMatch(line -> tinhSoLuongConTra(line) > 0);
            if (!conTraDuoc) {
                hien(INFORMATION, "Khong the tra hang", "Cac san pham trong hoa don nay da duoc tra het.");
            }

            capNhatTongTienGoc(lines);
            capNhatTienTra();
            tblSanPhamHoaDon.refresh();
        } catch (Exception exception) {
            exception.printStackTrace();
            hien(ERROR, "Loi", "Khong the tai hoa don.");
        }
    }

    public void xuLyTraHang() {
        if (hoaDonGoc == null || hoaDonGoc.getMaHD() == null) {
            hien(WARNING, "Thieu thong tin", "Chua chon hoa don goc.");
            return;
        }
        if (dsTra.isEmpty()) {
            hien(WARNING, "Chua co san pham", "Vui long chon san pham can tra.");
            return;
        }
        if (chuaCoKhachHang(hoaDonGoc)) {
            hien(WARNING, "Chua co khach hang", "Vui long bo sung thong tin khach hang truoc khi doi/tra.");
            moFormThemKhachHang(hoaDonGoc);
            return;
        }

        for (TraHangItem item : dsTra) {
            if (item.getLyDo() == null || item.getLyDo().isBlank()) {
                hien(WARNING, "Thieu ly do tra hang",
                        "Vui long nhap ly do tra cho san pham: " + tenSP(item.getGoc()));
                tblChiTietTraHang.getSelectionModel().select(item);
                tblChiTietTraHang.scrollTo(item);
                return;
            }
        }

        UserContext currentUser = SessionContext.getCurrentUser();
        if (currentUser == null) {
            hien(ERROR, "Loi dang nhap", "Chua xac dinh nhan vien dang dang nhap.");
            return;
        }

        try {
            String maPhieuTra = doiTraService.createPhieuTra(
                    doiTraService.buildCreatePhieuTraRequest(hoaDonGoc, List.copyOf(dsTra), txtGhiChu.getText()),
                    currentUser
            );

            Optional<javafx.scene.control.ButtonType> result = hoi(
                    "Thanh cong",
                    "Lap phieu tra thanh cong",
                    "Ban co muon xem chi tiet phieu tra khong?"
            );
            if (result.orElse(javafx.scene.control.ButtonType.CANCEL).getButtonData()
                    == javafx.scene.control.ButtonBar.ButtonData.OK_DONE) {
                moChiTietPhieuTra(maPhieuTra);
            }
            xuLyHuy();
        } catch (Exception exception) {
            exception.printStackTrace();
            hien(ERROR, "Loi", exception.getMessage() == null ? "Khong the luu phieu tra." : exception.getMessage());
        }
    }

    public void xuLyHuy() {
        dsGoc.clear();
        dsTra.clear();
        traByKey.clear();
        hoaDonGoc = null;
        if (lblMaHDGoc != null) {
            lblMaHDGoc.clear();
        }
        if (lblTenKH != null) {
            lblTenKH.clear();
        }
        if (lblSDT != null) {
            lblSDT.clear();
        }
        if (dpNgayLapPhieu != null) {
            dpNgayLapPhieu.setValue(LocalDate.now());
        }
        if (txtGhiChu != null) {
            txtGhiChu.clear();
        }
        if (txtTimHoaDon != null) {
            txtTimHoaDon.clear();
        }
        if (lblTongTienGoc != null) {
            lblTongTienGoc.setText("");
        }
        if (lblTongTienTraLai != null) {
            lblTongTienTraLai.setText("");
        }
        if (lblSoTienTraLai != null) {
            lblSoTienTraLai.setText("");
        }
    }

    private void moFormThemKhachHang(HoaDon hd) {
        try {
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.setTitle("Them khach hang");
            stage.initOwner(btnTimHoaDon.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);

            ThemKhachHang_Ctrl ctrl = new ThemKhachHang_Ctrl();
            ctrl.setOnSaved(kh -> {
                UserContext actor = SessionContext.getCurrentUser();
                doiTraService.attachKhachHangToHoaDon(hd.getMaHD(), kh.getMaKH(), actor);
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
            hien(ERROR, "Loi", "Khong the mo form them khach hang.");
        }
    }

    private void moChiTietPhieuTra(String maPhieuTra) {
        try {
            PhieuTraHang phieuTraHang = doiTraService.findPhieuTraById(maPhieuTra);
            if (phieuTraHang == null) {
                hien(ERROR, "Loi", "Khong tim thay phieu tra vua tao.");
                return;
            }
            ChiTietPhieuTraHang_Ctrl ctrl = new ChiTietPhieuTraHang_Ctrl();
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.setTitle("Chi tiet phieu tra " + phieuTraHang.getMaPT());
            ChiTietPhieuTraHang_GUI.showWithController(stage, ctrl);
            ctrl.setPhieuTraHang(phieuTraHang);
        } catch (Exception exception) {
            exception.printStackTrace();
            hien(ERROR, "Loi", "Khong the mo chi tiet phieu tra.");
        }
    }

    private void xuLyChuyenSangTra(ChiTietHoaDon chiTietHoaDon) {
        int slConTra = tinhSoLuongConTra(chiTietHoaDon);
        if (slConTra <= 0) {
            hien(WARNING, "Khong the tra", "San pham nay da duoc tra het.");
            return;
        }
        String key = keyTra(chiTietHoaDon);
        TraHangItem vm = traByKey.get(key);
        if (vm == null) {
            vm = new TraHangItem(chiTietHoaDon, 1, "");
            dsTra.add(vm);
            traByKey.put(key, vm);
        } else {
            int max = maxSoLuongCoTheChon(vm);
            vm.setSoLuongTra(Math.min(max, vm.getSoLuongTra() + 1));
        }
        capNhatTienTra();
        tblChiTietTraHang.refresh();
    }

    private int tinhSoLuongConTra(ChiTietHoaDon chiTietHoaDon) {
        if (chiTietHoaDon == null || chiTietHoaDon.getHoaDon() == null || chiTietHoaDon.getLoHang() == null || chiTietHoaDon.getDvt() == null) {
            return 0;
        }
        int slBan = chiTietHoaDon.getSoLuong();
        int slDaTra = doiTraService.getSoLuongDaTra(
                chiTietHoaDon.getHoaDon().getMaHD(),
                chiTietHoaDon.getLoHang().getMaLH(),
                chiTietHoaDon.getDvt().getMaDVT()
        );
        int slDangChon = dsTra.stream()
                .filter(item -> sameInvoiceLine(item.getGoc(), chiTietHoaDon))
                .mapToInt(TraHangItem::getSoLuongTra)
                .sum();
        return Math.max(0, slBan - slDaTra - slDangChon);
    }

    private int maxSoLuongCoTheChon(TraHangItem currentItem) {
        ChiTietHoaDon goc = currentItem.getGoc();
        if (goc == null || goc.getHoaDon() == null || goc.getLoHang() == null || goc.getDvt() == null) {
            return currentItem.getSoLuongTra();
        }
        int slBan = goc.getSoLuong();
        int slDaTra = doiTraService.getSoLuongDaTra(
                goc.getHoaDon().getMaHD(),
                goc.getLoHang().getMaLH(),
                goc.getDvt().getMaDVT()
        );
        int slDangChonKhac = dsTra.stream()
                .filter(item -> item != currentItem && sameInvoiceLine(item.getGoc(), goc))
                .mapToInt(TraHangItem::getSoLuongTra)
                .sum();
        return Math.max(1, slBan - slDaTra - slDangChonKhac);
    }

    private boolean sameInvoiceLine(ChiTietHoaDon left, ChiTietHoaDon right) {
        if (left == null || right == null || left.getLoHang() == null || right.getLoHang() == null
                || left.getDvt() == null || right.getDvt() == null || left.getHoaDon() == null || right.getHoaDon() == null) {
            return false;
        }
        return safe(left.getHoaDon().getMaHD()).equalsIgnoreCase(safe(right.getHoaDon().getMaHD()))
                && safe(left.getLoHang().getMaLH()).equalsIgnoreCase(safe(right.getLoHang().getMaLH()))
                && safe(left.getDvt().getMaDVT()).equalsIgnoreCase(safe(right.getDvt().getMaDVT()));
    }

    private String keyTra(ChiTietHoaDon chiTietHoaDon) {
        return safe(chiTietHoaDon.getHoaDon().getMaHD()) + "_"
                + safe(chiTietHoaDon.getLoHang().getMaLH()) + "_"
                + safe(chiTietHoaDon.getDvt().getMaDVT());
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

    private void capNhatTongTienGoc(List<ChiTietHoaDon> lines) {
        double tong = lines.stream().mapToDouble(ChiTietHoaDon::tinhThanhTien).sum();
        if (lblTongTienGoc != null) {
            lblTongTienGoc.setText(vnd(tong));
        }
    }

    private void capNhatTienTra() {
        double tongTienTra = dsTra.stream().mapToDouble(TraHangItem::getThanhTienTra).sum();
        if (lblTongTienTraLai != null) {
            lblTongTienTraLai.setText(vnd(tongTienTra));
        }
        if (lblSoTienTraLai != null) {
            lblSoTienTraLai.setText(vnd(tongTienTra));
        }
    }

    private boolean chuaCoKhachHang(HoaDon hd) {
        return hd == null || hd.getMaKH() == null || safe(hd.getMaKH().getMaKH()).isBlank();
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
        DonViTinh dvt = row.getDvt();
        if (dvt != null && dvt.getTenDonViTinh() != null) {
            return dvt.getTenDonViTinh();
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

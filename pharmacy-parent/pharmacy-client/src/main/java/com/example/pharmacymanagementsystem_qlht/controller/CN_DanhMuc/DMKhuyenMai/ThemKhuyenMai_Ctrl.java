package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhuyenMai;

import com.example.pharmacymanagementsystem_qlht.model.ChiTietKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.LoaiKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TangKem;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import com.example.pharmacymanagementsystem_qlht.service.KhuyenMaiService;
import com.example.pharmacymanagementsystem_qlht.service.ThuocService;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThemKhuyenMai_Ctrl {

    @FXML public TextField tfTenKM;
    @FXML public ComboBox<LoaiKhuyenMai> cbLoaiKM;
    @FXML public TextField tfGiaTri;
    @FXML public DatePicker dpTuNgay;
    @FXML public DatePicker dpDenNgay;
    @FXML public TextField tfMoTa;
    @FXML public Tab tabHoaDon;
    @FXML public TextField tfGiaTriHoaDon;

    @FXML public TableView<ChiTietKhuyenMai> tbDSThuoc;
    @FXML public TableColumn<ChiTietKhuyenMai, String> colMaThuoc;
    @FXML public TableColumn<ChiTietKhuyenMai, String> colTenThuoc;
    @FXML public TableColumn<ChiTietKhuyenMai, Integer> colSLAP;
    @FXML public TableColumn<ChiTietKhuyenMai, Integer> colSLTD;
    @FXML public TableColumn<ChiTietKhuyenMai, Void> colXoaCT;

    @FXML public TabPane tabPaneProducts;
    @FXML public Tab tabTangKem;
    @FXML public Tab tabThuoc;
    @FXML public TableView<Thuoc_SP_TangKem> tbTangKem;
    @FXML public TableColumn<Thuoc_SP_TangKem, String> colMaQua;
    @FXML public TableColumn<Thuoc_SP_TangKem, String> colTenQua;
    @FXML public TableColumn<Thuoc_SP_TangKem, Integer> colSLTang;
    @FXML public TableColumn<Thuoc_SP_TangKem, Void> colXoaQua;

    @FXML public TextField tfTimThuoc;
    @FXML public ListView<Thuoc_SanPham> listViewThuoc;

    @FXML public TextField tfTimQua;
    @FXML public ListView<Thuoc_SanPham> listViewQua;

    @FXML public Button btnThem;
    @FXML public Button btnHuy;

    @FXML public TableColumn<ChiTietKhuyenMai, String> colDonVi;
    @FXML public TableColumn<Thuoc_SP_TangKem, String> colDonViQua;

    private final ObservableList<ChiTietKhuyenMai> ctItems = FXCollections.observableArrayList();
    private final ObservableList<Thuoc_SP_TangKem> giftItems = FXCollections.observableArrayList();
    private final ObservableList<Thuoc_SanPham> allThuoc = FXCollections.observableArrayList();

    private final KhuyenMaiService khuyenMaiService = new KhuyenMaiService();
    private final ThuocService thuocService = new ThuocService();

    private javafx.beans.value.ChangeListener<Boolean> giaTriFocusListener;
    private boolean giaTriFormattingEnabled = false;
    private final Locale vnLocale = new Locale("vi", "VN");
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(vnLocale);
    private final java.util.Map<String, String> dvtCache = new java.util.HashMap<>();

    @FXML
    public void initialize() {
        List<LoaiKhuyenMai> loaiKMList = khuyenMaiService.findAllLoaiKhuyenMai();
        cbLoaiKM.getItems().setAll(loaiKMList);
        cbLoaiKM.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(LoaiKhuyenMai item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTenLoai());
            }
        });
        cbLoaiKM.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(LoaiKhuyenMai item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTenLoai());
            }
        });

        loadAllThuoc();
        setupThuocTable();
        setupGiftTable();
        initThuocListView();
        initQuaListView();

        cbLoaiKM.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateGiftTabVisibility();
            updateTabsBasedOnLoaiKM();
            if (tfGiaTri != null) {
                tfGiaTri.clear();
            }
        });

        updateGiftTabVisibility();
        updateTabsBasedOnLoaiKM();

        if (tfGiaTriHoaDon != null) {
            setupCurrencyField(tfGiaTriHoaDon);
        }
        setupEnterKeyHandlers();

        btnHuy.setOnAction(e -> btnHuyClick());
        btnThem.setOnAction(e -> btnThemClick());
    }

    private void loadAllThuoc() {
        List<Thuoc_SanPham> ds = thuocService.findAll();
        if (ds != null) {
            allThuoc.setAll(ds);
        }
        dvtCache.clear();
        for (Thuoc_SanPham thuoc : allThuoc) {
            String maThuoc = thuoc.getMaThuoc();
            String tenDVT = thuoc.getTenDVTCoBan() != null ? thuoc.getTenDVTCoBan() : "";
            dvtCache.put(maThuoc, tenDVT);
        }
    }

    private void setupThuocTable() {
        if (tbDSThuoc == null) {
            return;
        }
        tbDSThuoc.setItems(ctItems);
        tbDSThuoc.setEditable(true);

        colMaThuoc.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue() != null && cd.getValue().getThuoc() != null ? cd.getValue().getThuoc().getMaThuoc() : ""
        ));
        colTenThuoc.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue() != null && cd.getValue().getThuoc() != null ? cd.getValue().getThuoc().getTenThuoc() : ""
        ));
        colSLAP.setCellValueFactory(cd -> new SimpleIntegerProperty(
                cd.getValue() != null ? cd.getValue().getSlApDung() : 0
        ).asObject());
        colSLAP.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colSLAP.setOnEditCommit(e -> {
            ChiTietKhuyenMai row = e.getRowValue();
            row.setSlApDung(e.getNewValue() == null ? 0 : Math.max(0, e.getNewValue()));
            tbDSThuoc.refresh();
        });

        colSLTD.setCellValueFactory(cd -> new SimpleIntegerProperty(
                cd.getValue() != null ? cd.getValue().getSoHDToiDa() : 0
        ).asObject());
        colSLTD.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colSLTD.setOnEditCommit(e -> {
            ChiTietKhuyenMai row = e.getRowValue();
            row.setSoHDToiDa(e.getNewValue() == null ? 0 : Math.max(0, e.getNewValue()));
            tbDSThuoc.refresh();
        });

        colXoaCT.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Xoa");
            {
                btn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 4px;");
                btn.setOnAction(ev -> {
                    ChiTietKhuyenMai item = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(item);
                });
            }
            @Override protected void updateItem(Void value, boolean empty) {
                super.updateItem(value, empty);
                setGraphic(empty ? null : btn);
            }
        });

        colDonVi.setCellValueFactory(cd -> new SimpleStringProperty(getTenDvtByMaThuoc(
                cd.getValue() == null || cd.getValue().getThuoc() == null ? null : cd.getValue().getThuoc().getMaThuoc()
        )));
    }

    private void setupGiftTable() {
        if (tbTangKem == null) {
            return;
        }
        tbTangKem.setItems(giftItems);
        tbTangKem.setEditable(true);

        colMaQua.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue() != null && cd.getValue().getThuocTangKem() != null ? cd.getValue().getThuocTangKem().getMaThuoc() : ""
        ));
        colTenQua.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue() != null && cd.getValue().getThuocTangKem() != null ? cd.getValue().getThuocTangKem().getTenThuoc() : ""
        ));
        colSLTang.setCellValueFactory(cd -> new SimpleIntegerProperty(
                cd.getValue() != null ? cd.getValue().getSoLuong() : 0
        ).asObject());
        colSLTang.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colSLTang.setOnEditCommit(e -> {
            Thuoc_SP_TangKem row = e.getRowValue();
            row.setSoLuong(e.getNewValue() == null ? 0 : Math.max(0, e.getNewValue()));
            tbTangKem.refresh();
        });

        colXoaQua.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Xoa");
            {
                btn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 4px;");
                btn.setOnAction(ev -> {
                    Thuoc_SP_TangKem item = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(item);
                });
            }
            @Override protected void updateItem(Void value, boolean empty) {
                super.updateItem(value, empty);
                setGraphic(empty ? null : btn);
            }
        });

        colDonViQua.setCellValueFactory(cd -> new SimpleStringProperty(getTenDvtByMaThuoc(
                cd.getValue() == null || cd.getValue().getThuocTangKem() == null ? null : cd.getValue().getThuocTangKem().getMaThuoc()
        )));
    }

    private void initThuocListView() {
        if (tfTimThuoc == null || listViewThuoc == null) {
            return;
        }
        listViewThuoc.setVisible(false);
        listViewThuoc.setManaged(false);
        listViewThuoc.setPrefHeight(0);
        listViewThuoc.setItems(allThuoc);
        listViewThuoc.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Thuoc_SanPham item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getMaThuoc() + " - " + item.getTenThuoc());
            }
        });

        tfTimThuoc.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.trim().isEmpty()) {
                String keyword = newVal.toLowerCase();
                ObservableList<Thuoc_SanPham> filtered = FXCollections.observableArrayList();
                for (Thuoc_SanPham sp : allThuoc) {
                    String ma = sp.getMaThuoc() == null ? "" : sp.getMaThuoc().toLowerCase();
                    String ten = sp.getTenThuoc() == null ? "" : sp.getTenThuoc().toLowerCase();
                    if (ma.contains(keyword) || ten.contains(keyword)) {
                        filtered.add(sp);
                    }
                }
                listViewThuoc.setItems(filtered);
                listViewThuoc.setVisible(!filtered.isEmpty());
                listViewThuoc.setManaged(!filtered.isEmpty());
                listViewThuoc.setPrefHeight(filtered.isEmpty() ? 0 : 160);
                listViewThuoc.toFront();
            } else {
                listViewThuoc.setVisible(false);
                listViewThuoc.setManaged(false);
                listViewThuoc.setPrefHeight(0);
                listViewThuoc.setItems(allThuoc);
            }
        });

        listViewThuoc.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                addThuocToCTKM(newSel);
                tfTimThuoc.clear();
                listViewThuoc.setVisible(false);
                listViewThuoc.setPrefHeight(0);
                Platform.runLater(() -> {
                    listViewThuoc.getSelectionModel().clearSelection();
                    listViewThuoc.refresh();
                });
            }
        });
    }

    private void initQuaListView() {
        if (tfTimQua == null || listViewQua == null) {
            return;
        }
        listViewQua.setVisible(false);
        listViewQua.setManaged(false);
        listViewQua.setPrefHeight(0);
        listViewQua.setItems(allThuoc);
        listViewQua.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Thuoc_SanPham item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getMaThuoc() + " - " + item.getTenThuoc());
            }
        });

        tfTimQua.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.trim().isEmpty()) {
                String keyword = newVal.toLowerCase();
                ObservableList<Thuoc_SanPham> filtered = FXCollections.observableArrayList();
                for (Thuoc_SanPham sp : allThuoc) {
                    String ma = sp.getMaThuoc() == null ? "" : sp.getMaThuoc().toLowerCase();
                    String ten = sp.getTenThuoc() == null ? "" : sp.getTenThuoc().toLowerCase();
                    if (ma.contains(keyword) || ten.contains(keyword)) {
                        filtered.add(sp);
                    }
                }
                listViewQua.setItems(filtered);
                listViewQua.setVisible(!filtered.isEmpty());
                listViewQua.setManaged(!filtered.isEmpty());
                listViewQua.setPrefHeight(filtered.isEmpty() ? 0 : 160);
                listViewQua.toFront();
            } else {
                listViewQua.setVisible(false);
                listViewQua.setManaged(false);
                listViewQua.setPrefHeight(0);
                listViewQua.setItems(allThuoc);
            }
        });

        listViewQua.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                addGiftItem(newSel);
                tfTimQua.clear();
                listViewQua.setVisible(false);
                listViewQua.setPrefHeight(0);
                Platform.runLater(() -> {
                    listViewQua.getSelectionModel().clearSelection();
                    listViewQua.refresh();
                });
            }
        });
    }

    private void addThuocToCTKM(Thuoc_SanPham sp) {
        if (sp == null) {
            return;
        }
        boolean exists = ctItems.stream().anyMatch(ct ->
                ct.getThuoc() != null && sp.getMaThuoc().equals(ct.getThuoc().getMaThuoc())
        );
        if (exists) {
            return;
        }

        ChiTietKhuyenMai ct = new ChiTietKhuyenMai();
        ct.setThuoc(sp);
        ct.setSlApDung(1);
        ct.setSoHDToiDa(1);
        ctItems.add(ct);
    }

    private void addGiftItem(Thuoc_SanPham sp) {
        if (sp == null) {
            return;
        }
        boolean exists = giftItems.stream().anyMatch(g ->
                g.getThuocTangKem() != null && sp.getMaThuoc().equals(g.getThuocTangKem().getMaThuoc())
        );
        if (exists) {
            return;
        }

        Thuoc_SP_TangKem gift = new Thuoc_SP_TangKem();
        gift.setThuocTangKem(sp);
        gift.setSoLuong(1);
        giftItems.add(gift);
    }

    private void updateGiftTabVisibility() {
        if (tabTangKem == null || cbLoaiKM == null) {
            return;
        }
        LoaiKhuyenMai selected = cbLoaiKM.getValue();
        boolean enable = selected != null && "LKM001".equalsIgnoreCase(selected.getMaLoai());
        tabTangKem.setDisable(!enable);
        if (tfGiaTri != null) {
            tfGiaTri.setDisable(enable);
            if (enable) {
                tfGiaTri.setText("");
            }
        }
    }

    @FXML
    private void updateTabsBasedOnLoaiKM() {
        if (cbLoaiKM == null) {
            return;
        }
        LoaiKhuyenMai selected = cbLoaiKM.getValue();
        String maLoai = selected == null ? null : selected.getMaLoai();
        boolean isInvoiceType = maLoai != null && ("LKM004".equalsIgnoreCase(maLoai) || "LKM005".equalsIgnoreCase(maLoai));

        Tab localTabThuoc = null;
        if (tabPaneProducts != null && !tabPaneProducts.getTabs().isEmpty()) {
            localTabThuoc = tabPaneProducts.getTabs().get(0);
        }
        if (localTabThuoc == null) {
            localTabThuoc = this.tabThuoc;
        }

        if (tabHoaDon != null) {
            tabHoaDon.setDisable(!isInvoiceType);
        }
        if (localTabThuoc != null) {
            localTabThuoc.setDisable(isInvoiceType);
        }
        if (isInvoiceType && tfGiaTriHoaDon != null) {
            tfGiaTriHoaDon.setText("");
        }

        boolean enableGiaTriFmt = maLoai != null && ("LKM002".equalsIgnoreCase(maLoai) || "LKM004".equalsIgnoreCase(maLoai));
        enableGiaTriFormatting(enableGiaTriFmt);

        if (tfGiaTri != null) {
            if ("LKM001".equalsIgnoreCase(maLoai)) {
                tfGiaTri.setDisable(true);
                tfGiaTri.setText("");
            } else {
                tfGiaTri.setDisable(false);
                if (enableGiaTriFmt) {
                    double value = parseCurrencyToDouble(tfGiaTri.getText());
                    tfGiaTri.setText(formatVND(value));
                }
            }
        }
    }

    @FXML
    public void btnThemClick() {
        Window owner = btnThem.getScene().getWindow();

        if (cbLoaiKM.getValue() == null || tfTenKM.getText().isBlank()
                || dpTuNgay.getValue() == null || dpDenNgay.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, owner, "Thieu du lieu", "Vui long nhap day du thong tin bat buoc.");
            return;
        }

        if (!tabTangKem.isDisabled() && giftItems.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, owner, "Thieu du lieu", "Vui long them qua tang cho khuyen mai.");
            return;
        }

        float giaTri;
        double giaTriHoaDon;

        try {
            String maLoai = cbLoaiKM.getValue().getMaLoai();
            if ("LKM004".equalsIgnoreCase(maLoai) || "LKM005".equalsIgnoreCase(maLoai)) {
                giaTri = (float) parseCurrencyToDouble(tfGiaTri == null ? "0" : tfGiaTri.getText());
                giaTriHoaDon = parseCurrencyToDouble(tfGiaTriHoaDon == null ? "0" : tfGiaTriHoaDon.getText());
            } else if ("LKM001".equalsIgnoreCase(maLoai)) {
                giaTri = 0f;
                giaTriHoaDon = 0d;
            } else {
                giaTri = (float) parseCurrencyToDouble(tfGiaTri == null ? "0" : tfGiaTri.getText());
                giaTriHoaDon = 0d;
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, owner, "Gia tri khong hop le", "Gia tri phai la so.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Xac nhan them khuyen mai?", ButtonType.YES, ButtonType.NO);
        confirm.initOwner(owner);
        confirm.setHeaderText("Xac nhan");
        confirm.showAndWait().ifPresent(res -> {
            if (res != ButtonType.YES) {
                return;
            }
            KhuyenMai km = new KhuyenMai(
                    khuyenMaiService.generateNewMaKM(),
                    cbLoaiKM.getValue(),
                    tfTenKM.getText().trim(),
                    giaTri,
                    java.sql.Date.valueOf(dpTuNgay.getValue()),
                    java.sql.Date.valueOf(dpDenNgay.getValue()),
                    tfMoTa.getText() == null ? "" : tfMoTa.getText().trim()
            );
            km.setGiaTriApDung(giaTriHoaDon);

            boolean created = khuyenMaiService.create(km, new ArrayList<>(ctItems), new ArrayList<>(giftItems));
            if (created) {
                showAlert(Alert.AlertType.INFORMATION, owner, "Thanh cong", "Them khuyen mai thanh cong.");
                btnHuyClick();
            } else {
                showAlert(Alert.AlertType.ERROR, owner, "Loi", "Khong the them khuyen mai tren server.");
            }
        });
    }

    @FXML
    public void btnHuyClick() {
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, Window owner, String header, String content) {
        Alert alert = new Alert(type, content, ButtonType.OK);
        alert.initOwner(owner);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    private String formatVND(double value) {
        return currencyFormat.format(value);
    }

    private double parseCurrencyToDouble(String text) {
        if (text == null) {
            return 0d;
        }
        String cleaned = text.replaceAll("[^\\d,.-]", "");
        if (cleaned.isEmpty()) {
            return 0d;
        }
        cleaned = cleaned.replaceAll("\\.", "");
        cleaned = cleaned.replace(',', '.');
        try {
            return Double.parseDouble(cleaned);
        } catch (Exception ex) {
            return 0d;
        }
    }

    private void setupCurrencyField(TextField tf) {
        if (tf == null) {
            return;
        }
        tf.focusedProperty().addListener((obs, oldF, newF) -> {
            if (newF) {
                String raw = tf.getText();
                if (raw != null) {
                    String cleaned = raw.replaceAll("[^\\d,.-]", "");
                    cleaned = cleaned.replaceAll("\\.", "");
                    cleaned = cleaned.replace(',', '.');
                    tf.setText(cleaned);
                    Platform.runLater(() -> tf.positionCaret(tf.getText().length()));
                }
            } else {
                double value = parseCurrencyToDouble(tf.getText());
                tf.setText(formatVND(value));
            }
        });
        if (tf.getText() != null && !tf.getText().isBlank()) {
            double value = parseCurrencyToDouble(tf.getText());
            tf.setText(formatVND(value));
        }
    }

    private void enableGiaTriFormatting(boolean enable) {
        if (tfGiaTri == null) {
            return;
        }
        if (enable && !giaTriFormattingEnabled) {
            giaTriFocusListener = (obs, oldF, newF) -> {
                if (newF) {
                    String raw = tfGiaTri.getText();
                    if (raw != null) {
                        String cleaned = raw.replaceAll("[^\\d,.-]", "");
                        cleaned = cleaned.replaceAll("\\.", "");
                        cleaned = cleaned.replace(',', '.');
                        tfGiaTri.setText(cleaned);
                        Platform.runLater(() -> tfGiaTri.positionCaret(tfGiaTri.getText().length()));
                    }
                } else {
                    double value = parseCurrencyToDouble(tfGiaTri.getText());
                    tfGiaTri.setText(formatVND(value));
                }
            };
            tfGiaTri.focusedProperty().addListener(giaTriFocusListener);
            double value = parseCurrencyToDouble(tfGiaTri.getText());
            tfGiaTri.setText(formatVND(value));
            giaTriFormattingEnabled = true;
        } else if (!enable && giaTriFormattingEnabled) {
            tfGiaTri.focusedProperty().removeListener(giaTriFocusListener);
            giaTriFocusListener = null;
            giaTriFormattingEnabled = false;
            String cleaned = tfGiaTri.getText() == null ? "" : tfGiaTri.getText().replaceAll("[^\\d,.-]", "");
            cleaned = cleaned.replaceAll("\\.", "");
            cleaned = cleaned.replace(',', '.');
            if (cleaned.isEmpty()) {
                cleaned = "0";
            }
            tfGiaTri.setText(cleaned);
        } else if (!enable) {
            String cleaned = tfGiaTri.getText() == null ? "" : tfGiaTri.getText().replaceAll("[^\\d,.-]", "");
            cleaned = cleaned.replaceAll("\\.", "");
            cleaned = cleaned.replace(',', '.');
            if (cleaned.isEmpty()) {
                cleaned = "0";
            }
            tfGiaTri.setText(cleaned);
        }
    }

    private void setupEnterKeyHandlers() {
        addEnterHandler(tfTenKM, () -> {
            if (cbLoaiKM != null) {
                cbLoaiKM.requestFocus();
            }
        });
        addEnterHandlerForComboBox(cbLoaiKM, () -> {
            if (tfGiaTri != null) {
                tfGiaTri.requestFocus();
            }
        });
        addEnterHandler(tfGiaTri, () -> {
            if (dpTuNgay != null) {
                dpTuNgay.requestFocus();
            }
        });
        addEnterHandlerDatePicker(dpTuNgay, () -> {
            if (dpDenNgay != null) {
                dpDenNgay.requestFocus();
            }
        });
        addEnterHandlerDatePicker(dpDenNgay, () -> {
            if (tfMoTa != null) {
                tfMoTa.requestFocus();
            }
        });
        addEnterHandler(tfMoTa, () -> {
            if (tabHoaDon != null && !tabHoaDon.isDisable() && tfGiaTriHoaDon != null) {
                tfGiaTriHoaDon.requestFocus();
            } else if (tfTimThuoc != null) {
                tfTimThuoc.requestFocus();
            }
        });
        addEnterHandler(tfGiaTriHoaDon, () -> {
            if (tfTimThuoc != null) {
                tfTimThuoc.requestFocus();
            } else if (btnThem != null) {
                btnThem.fire();
            }
        });
        addEnterHandler(tfTimThuoc, () -> {
            if (listViewThuoc != null && listViewThuoc.isVisible() && !listViewThuoc.getItems().isEmpty()) {
                listViewThuoc.getSelectionModel().select(0);
                addThuocToCTKM(listViewThuoc.getSelectionModel().getSelectedItem());
                tfTimThuoc.clear();
                listViewThuoc.setVisible(false);
                listViewThuoc.setPrefHeight(0);
            } else if (tfTimQua != null) {
                tfTimQua.requestFocus();
            }
        });
        addEnterHandler(tfTimQua, () -> {
            if (listViewQua != null && listViewQua.isVisible() && !listViewQua.getItems().isEmpty()) {
                listViewQua.getSelectionModel().select(0);
                addGiftItem(listViewQua.getSelectionModel().getSelectedItem());
                tfTimQua.clear();
                listViewQua.setVisible(false);
                listViewQua.setPrefHeight(0);
            } else if (btnThem != null) {
                btnThem.requestFocus();
            }
        });
        if (btnThem != null) {
            btnThem.setDefaultButton(true);
        }
    }

    private void addEnterHandler(TextField tf, Runnable action) {
        if (tf == null) {
            return;
        }
        tf.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                ev.consume();
                action.run();
            }
        });
    }

    private <T> void addEnterHandlerForComboBox(ComboBox<T> cb, Runnable action) {
        if (cb == null) {
            return;
        }
        cb.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                ev.consume();
                action.run();
            }
        });
    }

    private void addEnterHandlerDatePicker(DatePicker dp, Runnable action) {
        if (dp == null) {
            return;
        }
        dp.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                ev.consume();
                action.run();
            }
        });
    }

    private String getTenDvtByMaThuoc(String maThuoc) {
        if (maThuoc == null || maThuoc.isBlank()) {
            return "";
        }
        String dvt = dvtCache.get(maThuoc);
        if (dvt == null) {
            try {
                dvt = thuocService.getTenDVTByMaThuoc(maThuoc);
            } catch (Exception ex) {
                dvt = "";
            }
            dvtCache.put(maThuoc, dvt == null ? "" : dvt);
        }
        return dvt == null ? "" : dvt;
    }
}

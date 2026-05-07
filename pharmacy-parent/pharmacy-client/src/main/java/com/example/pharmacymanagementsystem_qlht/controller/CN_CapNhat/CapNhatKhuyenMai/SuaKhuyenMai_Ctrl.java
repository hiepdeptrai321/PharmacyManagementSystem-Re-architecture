package com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatKhuyenMai;

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
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SuaKhuyenMai_Ctrl {

    public Tab tabThuoc;
    public Button btnLuu;
    public Button btnHuy;
    public TableView<ChiTietKhuyenMai> tbDSThuoc;
    public TableColumn<ChiTietKhuyenMai, String> colMaThuoc;
    public TableColumn<ChiTietKhuyenMai, String> colTenThuoc;
    public TableColumn<ChiTietKhuyenMai, Integer> colSLAP;
    public TableColumn<ChiTietKhuyenMai, Integer> colSLTD;
    public TableColumn<ChiTietKhuyenMai, Void> colXoaCT;
    public TabPane tabPaneProducts;
    public Tab tabTangKem;
    public Tab tabHoaDon;
    public TableView<Thuoc_SP_TangKem> tbTangKem;
    public TableColumn<Thuoc_SP_TangKem, String> colMaQua;
    public TableColumn<Thuoc_SP_TangKem, String> colTenQua;
    public TableColumn<Thuoc_SP_TangKem, Integer> colSLTang;
    public TableColumn<Thuoc_SP_TangKem, Void> colXoaQua;
    public TableColumn<ChiTietKhuyenMai, String> colDonVi;
    public TableColumn<Thuoc_SP_TangKem, String> colDonViQua;
    public TextField tfTimThuoc;
    public ListView<Thuoc_SanPham> listViewThuoc;
    public TextField tfTimQua;
    public ListView<Thuoc_SanPham> listViewQua;
    public TextField tfTenKM;
    public TextField tfMaKM;
    public ComboBox<String> cbLoaiKM;
    public TextField tfGiaTri;
    public DatePicker dpTuNgay;
    public DatePicker dpDenNgay;
    public TextField tfMoTa;
    public TextField tfGiaTriHoaDon;

    private final ObservableList<ChiTietKhuyenMai> ctItems = FXCollections.observableArrayList();
    private final ObservableList<Thuoc_SP_TangKem> giftItems = FXCollections.observableArrayList();
    private final ObservableList<Thuoc_SanPham> allThuoc = FXCollections.observableArrayList();
    private final Locale vnLocale = new Locale("vi", "VN");
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(vnLocale);
    private ChangeListener<Boolean> giaTriFocusListener;
    private boolean giaTriFormattingEnabled = false;
    private final ThuocService thuocService = new ThuocService();
    private final KhuyenMaiService khuyenMaiService = new KhuyenMaiService();
    private final java.util.Map<String, String> dvtCache = new java.util.HashMap<>();

    public void initialize() {
        if (cbLoaiKM != null) {
            cbLoaiKM.getItems().setAll(
                    khuyenMaiService.findAllLoaiKhuyenMai().stream()
                            .map(LoaiKhuyenMai::getTenLoai)
                            .toList()
            );
            cbLoaiKM.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item);
                }
            });
        }

        loadAllThuoc();
        setupThuocTable();
        setupGiftTable();
        initThuocListView();
        initQuaListView();

        if (cbLoaiKM != null) {
            cbLoaiKM.valueProperty().addListener((obs, o, n) -> {
                updateGiftTabVisibility();
                updateTabsBasedOnLoaiTen(n);
            });
            updateGiftTabVisibility();
            updateTabsBasedOnLoaiTen(cbLoaiKM.getValue());
        }
        if (cbLoaiKM != null) {
            cbLoaiKM.setEditable(false);
        }

        setupCurrencyField(tfGiaTriHoaDon);
        btnHuy.setOnAction(e -> btnHuyClick());
        btnLuu.setOnAction(e -> btnLuuClick());
    }

    private void loadAllThuoc() {
        List<Thuoc_SanPham> ds = thuocService.findAll();
        if (ds != null) {
            allThuoc.setAll(ds);
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
        listViewThuoc.setCellFactory(data -> new ListCell<>() {
            @Override protected void updateItem(Thuoc_SanPham item, boolean empty) {
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
        listViewQua.setCellFactory(data -> new ListCell<>() {
            @Override protected void updateItem(Thuoc_SanPham item, boolean empty) {
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
        LoaiKhuyenMai loaiKhuyenMai = khuyenMaiService.findLoaiKhuyenMaiByTen(cbLoaiKM.getValue());
        boolean enable = loaiKhuyenMai != null && "LKM001".equalsIgnoreCase(loaiKhuyenMai.getMaLoai());
        tabTangKem.setDisable(!enable);
    }

    public void loadDatatbCTKM(List<ChiTietKhuyenMai> list) {
        ctItems.setAll(list == null ? List.of() : list);
    }

    public void loadDatatbQuaTang(List<Thuoc_SP_TangKem> list) {
        giftItems.setAll(list == null ? List.of() : list);
    }

    public void loadData(KhuyenMai km) {
        if (km == null) {
            return;
        }
        if (tfMaKM != null) {
            tfMaKM.setText(km.getMaKM());
        }
        if (tfTenKM != null) {
            tfTenKM.setText(km.getTenKM());
        }
        if (cbLoaiKM != null && km.getLoaiKM() != null) {
            cbLoaiKM.setValue(km.getLoaiKM().getTenLoai());
        }
        if (dpTuNgay != null && km.getNgayBatDau() != null) {
            dpTuNgay.setValue(km.getNgayBatDau().toLocalDate());
        }
        if (dpDenNgay != null && km.getNgayKetThuc() != null) {
            dpDenNgay.setValue(km.getNgayKetThuc().toLocalDate());
        }
        if (tfMoTa != null) {
            tfMoTa.setText(km.getMoTa());
        }
        if (tfGiaTriHoaDon != null) {
            tfGiaTriHoaDon.setText(formatVND(km.getGiaTriApDung()));
        }

        if (km.getLoaiKM() != null && km.getLoaiKM().getMaLoai() != null) {
            String maLoai = km.getLoaiKM().getMaLoai();
            updateTabsBasedOnMaLoai(maLoai);
            boolean shouldFormatGiaTri = "LKM002".equalsIgnoreCase(maLoai) || "LKM004".equalsIgnoreCase(maLoai);
            if (tfGiaTri != null) {
                if (shouldFormatGiaTri) {
                    tfGiaTri.setText(formatVND(km.getGiaTriKM()));
                } else {
                    tfGiaTri.setText(String.valueOf(km.getGiaTriKM()));
                }
            }
            if ("LKM001".equalsIgnoreCase(maLoai)) {
                tfGiaTri.setEditable(false);
            } else {
                tfGiaTri.setEditable(true);
            }
        } else {
            updateTabsBasedOnLoaiTen(cbLoaiKM == null ? null : cbLoaiKM.getValue());
            if (tfGiaTri != null) {
                tfGiaTri.setText(String.valueOf(km.getGiaTriKM()));
            }
        }
    }

    private void updateTabsBasedOnMaLoai(String maLoai) {
        if (tabPaneProducts == null) {
            return;
        }
        Tab localTabThuoc = tabPaneProducts.getTabs().isEmpty() ? null : tabPaneProducts.getTabs().get(0);
        boolean isInvoice = "LKM004".equalsIgnoreCase(maLoai) || "LKM005".equalsIgnoreCase(maLoai);
        if (localTabThuoc != null) {
            localTabThuoc.setDisable(isInvoice);
        }
        if (tabHoaDon != null) {
            tabHoaDon.setDisable(!isInvoice);
        }
        boolean enableGiaTriFmt = "LKM002".equalsIgnoreCase(maLoai) || "LKM004".equalsIgnoreCase(maLoai);
        enableGiaTriFormatting(enableGiaTriFmt);
    }

    private void updateTabsBasedOnLoaiTen(String tenLoai) {
        if (tenLoai == null) {
            updateTabsBasedOnMaLoai(null);
            return;
        }
        LoaiKhuyenMai loai = khuyenMaiService.findLoaiKhuyenMaiByTen(tenLoai);
        if (loai != null && loai.getMaLoai() != null) {
            updateTabsBasedOnMaLoai(loai.getMaLoai());
            return;
        }

        boolean isInvoiceByName = tenLoai.toLowerCase().contains("hoa don");
        Tab localTabThuoc = tabPaneProducts == null || tabPaneProducts.getTabs().isEmpty() ? null : tabPaneProducts.getTabs().get(0);
        if (localTabThuoc != null) {
            localTabThuoc.setDisable(isInvoiceByName);
        }
        if (tabTangKem != null) {
            tabTangKem.setDisable(isInvoiceByName);
        }
        if (tabHoaDon != null) {
            tabHoaDon.setDisable(!isInvoiceByName);
        }
        enableGiaTriFormatting(false);
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

    public void btnHuyClick() {
        Stage stage = (Stage) (btnHuy != null ? btnHuy.getScene().getWindow()
                : (tfTenKM != null ? tfTenKM.getScene().getWindow() : null));
        if (stage != null) {
            stage.close();
        }
    }

    public void btnLuuClick() {
        try {
            String maKM = tfMaKM.getText();
            String tenKM = tfTenKM.getText();
            String loaiTen = cbLoaiKM == null ? null : cbLoaiKM.getValue();
            String moTa = tfMoTa == null ? "" : tfMoTa.getText();
            java.sql.Date tuNgay = dpTuNgay == null || dpTuNgay.getValue() == null ? null : java.sql.Date.valueOf(dpTuNgay.getValue());
            java.sql.Date denNgay = dpDenNgay == null || dpDenNgay.getValue() == null ? null : java.sql.Date.valueOf(dpDenNgay.getValue());
            LoaiKhuyenMai loai = loaiTen == null ? null : khuyenMaiService.findLoaiKhuyenMaiByTen(loaiTen);

            float giaTriKMVal;
            double giaTriApDungVal;
            if (loai != null && loai.getMaLoai() != null
                    && ("LKM004".equalsIgnoreCase(loai.getMaLoai()) || "LKM005".equalsIgnoreCase(loai.getMaLoai()))) {
                giaTriKMVal = (float) parseCurrencyToDouble(tfGiaTri == null ? "0" : tfGiaTri.getText());
                giaTriApDungVal = parseCurrencyToDouble(tfGiaTriHoaDon == null ? "0" : tfGiaTriHoaDon.getText());
            } else if (loai != null && "LKM001".equalsIgnoreCase(loai.getMaLoai())) {
                giaTriKMVal = 0f;
                giaTriApDungVal = 0d;
            } else {
                giaTriKMVal = (float) parseCurrencyToDouble2(tfGiaTri == null ? "0" : tfGiaTri.getText());
                giaTriApDungVal = 0d;
            }

            KhuyenMai km = new KhuyenMai(maKM, loai, tenKM, giaTriKMVal, tuNgay, denNgay, moTa);
            km.setGiaTriApDung(giaTriApDungVal);

            boolean updated = khuyenMaiService.update(km, List.copyOf(ctItems), List.copyOf(giftItems));
            if (updated) {
                btnHuyClick();
            } else {
                new Alert(Alert.AlertType.ERROR, "Khong the cap nhat khuyen mai tren server.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private double parseCurrencyToDouble2(String text) {
        if (text == null) {
            return 0d;
        }
        String cleaned = text.replaceAll("[^\\d,.-]", "");
        if (cleaned.isEmpty()) {
            return 0d;
        }
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

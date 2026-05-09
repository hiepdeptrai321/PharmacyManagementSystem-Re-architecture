package com.example.pharmacy.client.controller.CN_XuLy.LapPhieuNhapHang;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.common.request.PhieuNhapItemRequest;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacy.client.TienIch.VNDFormatter;
import com.example.pharmacy.client.controller.CN_DanhMuc.DMNhaCungCap.ThemNhaCungCap_Ctrl;
import com.example.pharmacy.common.model.CTPN_TSPTL_CHTDVTDto;
import com.example.pharmacy.common.model.ChiTietDonViTinhDto;
import com.example.pharmacy.common.model.ChiTietPhieuNhapDto;
import com.example.pharmacy.common.model.NhaCungCapDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;
import com.example.pharmacy.client.service.NhaCungCapService;
import com.example.pharmacy.client.service.PhieuNhapService;
import com.example.pharmacy.client.service.ThuocService;
import com.example.pharmacy.client.session.SessionContext;
import com.example.pharmacy.client.view.CN_DanhMuc.DMNCC.ThemNhaCungCap_GUI;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class LapPhieuNhapHang_Ctrl {
    public TableColumn<CTPN_TSPTL_CHTDVTDto, String> colSTT;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, String> colMaThuoc;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, String> colTenThuoc;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, String> colLoHang;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, LocalDate> colHanSuDung;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, Integer> colSoLuong;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, Double> colDonGiaNhap;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, Float> colChietKhau;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, Float> colThue;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, String> colXoa;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, String> colDonViNhap;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, LocalDate> colNSX;
    public TableColumn<CTPN_TSPTL_CHTDVTDto, String> colThanhTien;
    public TableView<CTPN_TSPTL_CHTDVTDto> tblNhapThuoc;
    public ComboBox<String> cbxNCC;
    public TextField txtMaPhieuNhap;
    public DatePicker txtNgayNhap;
    public TextArea txtGhiChu;
    public TextField txtTongGiaNhap;
    public TextField txtTongTienChietKhau;
    public TextField txtTongTienThue;
    public TextField txtThanhTien;
    public TextField txtTimKiemChiTietDonViTinh;
    public ListView<String> listViewNhaCungCap;
    public ListView<ChiTietDonViTinhDto> listViewChiTietDonViTinh;

    private final ObservableList<CTPN_TSPTL_CHTDVTDto> listNhapThuoc = FXCollections.observableArrayList();
    private ObservableList<ChiTietDonViTinhDto> allChiTietDonViTinh = FXCollections.observableArrayList();
    private ObservableList<NhaCungCapDto> listNCC = FXCollections.observableArrayList();
    private final NhaCungCapService nhaCungCapService = new NhaCungCapService();
    private final ThuocService thuocService = new ThuocService();
    private final PhieuNhapService phieuNhapService = new PhieuNhapService();
    private final VNDFormatter vndFormatter = new VNDFormatter();
    private NhaCungCapDto ncc;
    private int maLoHienTai = 0;

    public void initialize() {
        loadTable();
        taiDanhSachNCC();
        txtNgayNhap.setValue(LocalDate.now());
        txtMaPhieuNhap.setText(phieuNhapService.generateNewMaPhieuNhap());
        txtMaPhieuNhap.setEditable(false);

        Platform.runLater(this::loadDataAsync);
        timKiemNhaCungCap();
        timKiemDonViTinh();
        suKienThemChiTietDonViTinhVaoBang();
        listenerListNhapThuoc();
    }

    private void loadDataAsync() {
        List<ChiTietDonViTinhDto> data = thuocService.findAll().stream()
                .flatMap(thuoc -> thuoc.getDsCTDVT().stream())
                .toList();
        allChiTietDonViTinh = FXCollections.observableArrayList(data);
        listViewChiTietDonViTinh.setItems(allChiTietDonViTinh);
        listViewChiTietDonViTinh.setCellFactory(view -> new ListCell<>() {
            @Override
            protected void updateItem(ChiTietDonViTinhDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getThuoc().getMaThuoc() + " - " + item.getThuoc().getTenThuoc() + " - " + item.getDvt().getTenDonViTinh());
                }
            }
        });
    }

    public void taiDanhSachNCC() {
        listNCC = FXCollections.observableArrayList(nhaCungCapService.findAll());
        ObservableList<String> nccList = FXCollections.observableArrayList();
        for (NhaCungCapDto item : listNCC) {
            nccList.add(item.getMaNCC() + " - " + item.getTenNCC());
        }
        cbxNCC.setItems(nccList);
        cbxNCC.setEditable(true);
    }

    public void timKiemNhaCungCap() {
        listViewNhaCungCap.setVisible(false);
        cbxNCC.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cbxNCC.getEditor().setText(newVal);
                ncc = listNCC.stream()
                        .filter(item -> (item.getMaNCC() + " - " + item.getTenNCC()).equals(newVal))
                        .findFirst()
                        .orElse(null);
                listViewNhaCungCap.setVisible(false);
            }
        });
        cbxNCC.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.trim().isEmpty()) {
                listViewNhaCungCap.setVisible(false);
                return;
            }
            String keyword = newVal.toLowerCase().trim();
            ObservableList<String> filtered = FXCollections.observableArrayList();
            for (NhaCungCapDto item : listNCC) {
                String label = item.getMaNCC() + " - " + item.getTenNCC();
                if (label.toLowerCase().contains(keyword)) {
                    filtered.add(label);
                }
            }
            listViewNhaCungCap.setItems(filtered);
            listViewNhaCungCap.setVisible(!filtered.isEmpty());
        });
        listViewNhaCungCap.setOnMouseClicked(event -> {
            String selected = listViewNhaCungCap.getSelectionModel().getSelectedItem();
            if (selected != null) {
                cbxNCC.getEditor().setText(selected);
                ncc = listNCC.stream()
                        .filter(item -> (item.getMaNCC() + " - " + item.getTenNCC()).equals(selected))
                        .findFirst()
                        .orElse(null);
                listViewNhaCungCap.setVisible(false);
            }
        });
    }

    public void timKiemDonViTinh() {
        listViewChiTietDonViTinh.setVisible(false);
        txtTimKiemChiTietDonViTinh.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.trim().isEmpty()) {
                listViewChiTietDonViTinh.setVisible(false);
                return;
            }
            String keyword = newVal.toLowerCase().trim();
            ObservableList<ChiTietDonViTinhDto> filtered = FXCollections.observableArrayList();
            for (ChiTietDonViTinhDto item : allChiTietDonViTinh) {
                if (item.getThuoc().getMaThuoc().toLowerCase().contains(keyword)
                        || item.getThuoc().getTenThuoc().toLowerCase().contains(keyword)
                        || item.getDvt().getTenDonViTinh().toLowerCase().contains(keyword)) {
                    filtered.add(item);
                }
            }
            listViewChiTietDonViTinh.setItems(filtered);
            listViewChiTietDonViTinh.setVisible(!filtered.isEmpty());
        });
    }

    public void btnThemThuocClick(MouseEvent mouseEvent) {
        showInfo("Hay dung o tim kiem de them thuoc vao phieu nhap. Popup them thuoc bo sung se noi lai sau.");
    }

    public void loadTable() {
        tblNhapThuoc.setItems(listNhapThuoc);
        tblNhapThuoc.setEditable(true);
        colSTT.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(String.valueOf(tblNhapThuoc.getItems().indexOf(cellData.getValue()) + 1))
        );
        colMaThuoc.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getChiTietDonViTinh().getThuoc().getMaThuoc())
        );
        colTenThuoc.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getChiTietDonViTinh().getThuoc().getTenThuoc())
        );
        colDonViNhap.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getChiTietDonViTinh().getDvt().getTenDonViTinh())
        );
        colLoHang.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getChiTietSP_theoLo().getMaLH())
        );
        colNSX.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(
                cellData.getValue().getChiTietSP_theoLo().getNsx() == null
                        ? null
                        : cellData.getValue().getChiTietSP_theoLo().getNsx().toLocalDate()));
        colHanSuDung.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(
                cellData.getValue().getChiTietSP_theoLo().getHsd() == null
                        ? null
                        : cellData.getValue().getChiTietSP_theoLo().getHsd().toLocalDate()));

        setDatePickerCell(colNSX, true);
        setDatePickerCell(colHanSuDung, false);
        setIntegerCell(colSoLuong, CTPN_TSPTL_CHTDVTDto::getChiTietPhieuNhap, (row, value) -> row.getChiTietPhieuNhap().setSoLuong(Math.max(1, value)));
        setDoubleCell(colDonGiaNhap, CTPN_TSPTL_CHTDVTDto::getChiTietPhieuNhap, (row, value) -> row.getChiTietPhieuNhap().setGiaNhap(Math.max(0, value)));
        setFloatCell(colChietKhau, CTPN_TSPTL_CHTDVTDto::getChiTietPhieuNhap, (row, value) -> row.getChiTietPhieuNhap().setChietKhau(Math.max(0, value)), "Chiet khau");
        setFloatCell(colThue, CTPN_TSPTL_CHTDVTDto::getChiTietPhieuNhap, (row, value) -> row.getChiTietPhieuNhap().setThue(Math.max(0, value)), "Thue");
        colThanhTien.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(formatVnd(tinhTongDong(cellData.getValue()))));
        colXoa.setCellFactory(tc -> new TableCell<>() {
            private final Button btnXoa = new Button("X");

            {
                btnXoa.setStyle("-fx-background-color: #d80202; -fx-text-fill: white; -fx-font-weight: bold");
                btnXoa.setOnAction(e -> {
                    if (getIndex() < 0 || getIndex() >= listNhapThuoc.size()) {
                        return;
                    }
                    listNhapThuoc.remove(getIndex());
                    capNhatTongTien();
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

    private void setDatePickerCell(TableColumn<CTPN_TSPTL_CHTDVTDto, LocalDate> column, boolean nsxColumn) {
        column.setCellFactory(col -> new TableCell<>() {
            private final DatePicker datePicker = new DatePicker();
            private boolean updating = false;

            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                datePicker.setConverter(new StringConverter<>() {
                    @Override
                    public String toString(LocalDate object) {
                        return object == null ? "" : object.format(formatter);
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        return (string == null || string.isBlank()) ? null : LocalDate.parse(string, formatter);
                    }
                });
                datePicker.setEditable(false);
                datePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
                    if (updating) return;
                    CTPN_TSPTL_CHTDVTDto row = getCurrentRow();
                    if (row == null || newVal == null) return;
                    Thuoc_SP_TheoLoDto lo = row.getChiTietSP_theoLo();
                    if (nsxColumn) {
                        if (lo.getHsd() != null && newVal.isAfter(lo.getHsd().toLocalDate())) {
                            showWarning("NSX khong duoc sau HSD.");
                            updating = true;
                            datePicker.setValue(oldVal);
                            updating = false;
                            return;
                        }
                        lo.setNsx(Date.valueOf(newVal));
                    } else {
                        if (lo.getNsx() != null && newVal.isBefore(lo.getNsx().toLocalDate())) {
                            showWarning("HSD khong duoc truoc NSX.");
                            updating = true;
                            datePicker.setValue(oldVal);
                            updating = false;
                            return;
                        }
                        lo.setHsd(Date.valueOf(newVal));
                    }
                });
            }

            private CTPN_TSPTL_CHTDVTDto getCurrentRow() {
                return getIndex() >= 0 && getIndex() < tblNhapThuoc.getItems().size()
                        ? tblNhapThuoc.getItems().get(getIndex())
                        : null;
            }

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    updating = true;
                    datePicker.setValue(item);
                    updating = false;
                    setGraphic(datePicker);
                }
            }
        });
    }

    private interface DetailAccessor<T> {
        ChiTietPhieuNhapDto get(CTPN_TSPTL_CHTDVTDto row);
    }

    private interface RowValueSetter<T extends Number> {
        void set(CTPN_TSPTL_CHTDVTDto row, T value);
    }

    private void setIntegerCell(TableColumn<CTPN_TSPTL_CHTDVTDto, Integer> column,
                                DetailAccessor<Integer> accessor,
                                RowValueSetter<Integer> setter) {
        column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(accessor.get(cellData.getValue()).getSoLuong()));
        column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer object) {
                return object == null ? "1" : String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                try {
                    return Integer.parseInt(string.trim());
                } catch (Exception ex) {
                    return null;
                }
            }
        }));
        column.setOnEditCommit(event -> {
            Integer newVal = event.getNewValue();
            if (newVal == null) {
                showWarning("So luong nhap khong hop le.");
                tblNhapThuoc.refresh();
                return;
            }
            if (newVal <= 0) {
                showWarning("So luong phai lon hon 0.");
                tblNhapThuoc.refresh();
                return;
            }
            setter.set(event.getRowValue(), newVal);
            tblNhapThuoc.refresh();
            capNhatTongTien();
        });
    }

    private void setDoubleCell(TableColumn<CTPN_TSPTL_CHTDVTDto, Double> column,
                               DetailAccessor<Double> accessor,
                               RowValueSetter<Double> setter) {
        column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(accessor.get(cellData.getValue()).getGiaNhap()));
        column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Double object) {
                return object == null ? "0" : String.valueOf(object);
            }

            @Override
            public Double fromString(String string) {
                try {
                    return Double.parseDouble(string.trim());
                } catch (Exception ex) {
                    return null;
                }
            }
        }));
        column.setOnEditCommit(event -> {
            Double newVal = event.getNewValue();
            if (newVal == null) {
                showWarning("Don gia nhap khong hop le.");
                tblNhapThuoc.refresh();
                return;
            }
            if (newVal < 0) {
                showWarning("Don gia nhap khong duoc am.");
                tblNhapThuoc.refresh();
                return;
            }
            setter.set(event.getRowValue(), newVal);
            tblNhapThuoc.refresh();
            capNhatTongTien();
        });
    }

    private void setFloatCell(TableColumn<CTPN_TSPTL_CHTDVTDto, Float> column,
                              DetailAccessor<Float> accessor,
                              RowValueSetter<Float> setter,
                              String fieldName) {
        column.setCellValueFactory(cellData -> {
            ChiTietPhieuNhapDto detail = accessor.get(cellData.getValue());
            float value = column == colChietKhau ? detail.getChietKhau() : detail.getThue();
            return new ReadOnlyObjectWrapper<>(value);
        });
        column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Float object) {
                return object == null ? "0" : String.valueOf(object);
            }

            @Override
            public Float fromString(String string) {
                try {
                    return Float.parseFloat(string.trim());
                } catch (Exception ex) {
                    return null;
                }
            }
        }));
        column.setOnEditCommit(event -> {
            Float newVal = event.getNewValue();
            if (newVal == null) {
                showWarning(fieldName + " khong hop le.");
                tblNhapThuoc.refresh();
                return;
            }
            if (newVal < 0) {
                showWarning(fieldName + " khong duoc am.");
                tblNhapThuoc.refresh();
                return;
            }
            setter.set(event.getRowValue(), newVal);
            tblNhapThuoc.refresh();
            capNhatTongTien();
        });
    }

    public void suKienThemChiTietDonViTinhVaoBang() {
        listViewChiTietDonViTinh.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                return;
            }
            txtTimKiemChiTietDonViTinh.clear();
            listViewChiTietDonViTinh.setVisible(false);

            CTPN_TSPTL_CHTDVTDto item = new CTPN_TSPTL_CHTDVTDto();
            ChiTietDonViTinhDto ctdvt = new ChiTietDonViTinhDto();
            ctdvt.setThuoc(newVal.getThuoc());
            ctdvt.setDvt(newVal.getDvt());
            ctdvt.setHeSoQuyDoi(newVal.getHeSoQuyDoi());
            ctdvt.setGiaNhap(newVal.getGiaNhap());
            ctdvt.setGiaBan(newVal.getGiaBan());
            ctdvt.setDonViCoBan(newVal.isDonViCoBan());

            String maLo = String.format("TMP-LH-%04d", ++maLoHienTai);
            ChiTietPhieuNhapDto ctpn = new ChiTietPhieuNhapDto();
            ctpn.setThuoc(newVal.getThuoc());
            ctpn.setMaLH(maLo);
            ctpn.setSoLuong(1);
            ctpn.setGiaNhap(newVal.getGiaNhap());
            ctpn.setChietKhau(0);
            ctpn.setThue(0);

            Thuoc_SP_TheoLoDto lo = new Thuoc_SP_TheoLoDto();
            lo.setThuoc(newVal.getThuoc());
            lo.setMaLH(maLo);
            lo.setNsx(Date.valueOf(LocalDate.now()));
            lo.setHsd(Date.valueOf(LocalDate.now().plusMonths(12)));
            lo.setSoLuongTon(ctpn.getSoLuong());

            item.setChiTietDonViTinh(ctdvt);
            item.setChiTietPhieuNhap(ctpn);
            item.setChiTietSP_theoLo(lo);

            themVaoBangNhap(item);
            listViewChiTietDonViTinh.getSelectionModel().clearSelection();
        });
    }

    public void themVaoBangNhap(CTPN_TSPTL_CHTDVTDto ct) {
        listNhapThuoc.add(ct);
        tblNhapThuoc.refresh();
        capNhatTongTien();
    }

    private void listenerListNhapThuoc() {
        listNhapThuoc.addListener((ListChangeListener<CTPN_TSPTL_CHTDVTDto>) change -> capNhatTongTien());
    }

    private void capNhatTongTien() {
        double tongGiaNhap = 0;
        double tongTienChietKhau = 0;
        double tongTienThue = 0;
        double thanhTien = 0;

        for (CTPN_TSPTL_CHTDVTDto item : listNhapThuoc) {
            ChiTietPhieuNhapDto ctpn = item.getChiTietPhieuNhap();
            double tienHang = ctpn.getGiaNhap() * ctpn.getSoLuong();
            double tienChietKhau = tienHang * (ctpn.getChietKhau() / 100.0);
            double tienThue = (tienHang - tienChietKhau) * (ctpn.getThue() / 100.0);
            double tongDong = tienHang - tienChietKhau + tienThue;
            tongGiaNhap += tienHang;
            tongTienChietKhau += tienChietKhau;
            tongTienThue += tienThue;
            thanhTien += tongDong;
        }

        txtTongGiaNhap.setText(formatVnd(tongGiaNhap));
        txtTongTienChietKhau.setText(formatVnd(tongTienChietKhau));
        txtTongTienThue.setText(formatVnd(tongTienThue));
        txtThanhTien.setText(formatVnd(thanhTien));
    }

    private double tinhTongDong(CTPN_TSPTL_CHTDVTDto item) {
        ChiTietPhieuNhapDto ctpn = item.getChiTietPhieuNhap();
        double tienHang = ctpn.getGiaNhap() * ctpn.getSoLuong();
        double tienChietKhau = tienHang * (ctpn.getChietKhau() / 100.0);
        double tienThue = (tienHang - tienChietKhau) * (ctpn.getThue() / 100.0);
        return tienHang - tienChietKhau + tienThue;
    }

    public void btnLuu(MouseEvent mouseEvent) {
        if (listNhapThuoc.isEmpty()) {
            showWarning("Vui long them thuoc vao phieu nhap.");
            return;
        }
        if (ncc == null) {
            showWarning("Vui long chon nha cung cap.");
            return;
        }
        for (CTPN_TSPTL_CHTDVTDto item : listNhapThuoc) {
            ChiTietPhieuNhapDto ctpn = item.getChiTietPhieuNhap();
            Thuoc_SP_TheoLoDto lo = item.getChiTietSP_theoLo();
            if (lo.getNsx() == null || lo.getHsd() == null) {
                showWarning("NSX va HSD bat buoc phai co cho tat ca cac dong.");
                return;
            }
            if (lo.getHsd().before(lo.getNsx())) {
                showWarning("HSD khong duoc som hon NSX.");
                return;
            }
            if (ctpn.getSoLuong() <= 0) {
                showWarning("So luong nhap phai lon hon 0.");
                return;
            }
            if (ctpn.getGiaNhap() < 0) {
                showWarning("Gia nhap khong duoc am.");
                return;
            }
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xac nhan luu");
        confirm.setHeaderText(null);
        confirm.setContentText("Ban co chac chan muon luu phieu nhap nay khong?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        PhieuNhapRequest request = new PhieuNhapRequest();
        request.setMaPhieuNhap(txtMaPhieuNhap.getText());
        request.setNgayNhap(txtNgayNhap.getValue() == null ? LocalDate.now() : txtNgayNhap.getValue());
        request.setGhiChu(txtGhiChu.getText());
        request.setMaNhaCungCap(ncc.getMaNCC());

        for (CTPN_TSPTL_CHTDVTDto item : listNhapThuoc) {
            PhieuNhapItemRequest row = new PhieuNhapItemRequest();
            row.setMaThuoc(item.getChiTietDonViTinh().getThuoc().getMaThuoc());
            row.setMaLo(null);
            row.setMaDvt(item.getChiTietDonViTinh().getDvt().getMaDVT());
            row.setSoLuong(item.getChiTietPhieuNhap().getSoLuong());
            row.setGiaNhap(BigDecimal.valueOf(item.getChiTietPhieuNhap().getGiaNhap()));
            row.setChietKhau(BigDecimal.valueOf(item.getChiTietPhieuNhap().getChietKhau()));
            row.setThue(BigDecimal.valueOf(item.getChiTietPhieuNhap().getThue()));
            row.setGiaBanCapNhat(BigDecimal.valueOf(item.getChiTietDonViTinh().getGiaBan()));
            row.setNsx(item.getChiTietSP_theoLo().getNsx().toLocalDate());
            row.setHsd(item.getChiTietSP_theoLo().getHsd().toLocalDate());
            request.getItems().add(row);
        }

        String maPhieuNhap = phieuNhapService.createPurchaseOrder(request, SessionContext.requireCurrentUser());
        showInfo("Da luu phieu nhap " + maPhieuNhap + " thanh cong.");
        clearAll();
    }

    private void clearAll() {
        txtMaPhieuNhap.setText(phieuNhapService.generateNewMaPhieuNhap());
        txtGhiChu.clear();
        cbxNCC.getEditor().clear();
        cbxNCC.setValue(null);
        ncc = null;
        txtNgayNhap.setValue(LocalDate.now());
        listNhapThuoc.clear();
        tblNhapThuoc.refresh();
        maLoHienTai = 0;
        capNhatTongTien();
    }

    public void btnHuy(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xac nhan huy");
        alert.setHeaderText(null);
        alert.setContentText("Ban co chac chan muon huy phieu nhap nay khong? Tat ca du lieu se bi xoa.");
        if (alert.showAndWait().filter(ButtonType.OK::equals).isPresent()) {
            clearAll();
            showInfo("Phieu nhap da duoc huy.");
        }
    }

    public void btnThemNCCClick(MouseEvent mouseEvent) {
        try {
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            ThemNhaCungCap_GUI gui = new ThemNhaCungCap_GUI();
            ThemNhaCungCap_Ctrl ctrl = new ThemNhaCungCap_Ctrl();
            ctrl.setOnNhaCungCapCreated(this::taiDanhSachNCC);
            gui.showWithController(stage, ctrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnThemThuocByExcel(MouseEvent mouseEvent) {
        showInfo("Nhap hang bang Excel dang duoc noi lai o batch sau. Hien tai hay them thuoc bang o tim kiem.");
    }

    public void themThuocTuExcel(List<CTPN_TSPTL_CHTDVTDto> excelList) {
        for (CTPN_TSPTL_CHTDVTDto item : excelList) {
            themVaoBangNhap(item);
        }
    }

    public boolean isSameThuoc(Thuoc_SanPhamDto left, Thuoc_SanPhamDto right) {
        if (left == null || right == null) {
            return false;
        }
        return equalsIgnoreCase(left.getTenThuoc(), right.getTenThuoc())
                && equalsIgnoreCase(left.getDonViHamLuong(), right.getDonViHamLuong())
                && equalsIgnoreCase(left.getDuongDung(), right.getDuongDung())
                && equalsIgnoreCase(left.getQuyCachDongGoi(), right.getQuyCachDongGoi())
                && equalsIgnoreCase(left.getHangSX(), right.getHangSX())
                && equalsIgnoreCase(left.getNuocSX(), right.getNuocSX())
                && Math.abs(left.getHamLuong() - right.getHamLuong()) < 0.0001;
    }

    private boolean equalsIgnoreCase(String a, String b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.trim().equalsIgnoreCase(b.trim());
    }

    private String formatVnd(double value) {
        return String.format("%,.0f VND", value);
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Canh bao");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thong bao");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

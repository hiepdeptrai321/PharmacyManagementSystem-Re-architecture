package com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuNhapHang;

import com.example.pharmacy.client.TienIch.DoiNgay;
import com.example.pharmacy.common.model.PhieuNhap;
import com.example.pharmacy.client.service.NhaCungCapService;
import com.example.pharmacy.client.service.NhanVienService;
import com.example.pharmacy.client.service.PhieuNhapService;
import com.example.pharmacy.client.view.CN_TimKiem.TKPhieuNhapHang.ChiTietPhieuNhapHang_GUI;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.util.stream.Collectors;

public class TimKiemPhieuNhap_Ctrl {
    public TableColumn<PhieuNhap, String> colChiTiet;
    public ComboBox<String> cbxTimKiem;
    public TextField txtTimKiem;
    public DatePicker txtNgayNhapMax;
    public DatePicker txtNgayNhapMin;
    public ComboBox<String> chonNhanVien;
    public ComboBox<String> cbxChonNhaCC;
    public CheckBox cboxTrangThai;
    public TableView<PhieuNhap> tblPhieuNhap;
    public TableColumn<PhieuNhap, String> colMaPN;
    public TableColumn<PhieuNhap, String> colNhaCungCap;
    public TableColumn<PhieuNhap, String> colNgayNhap;
    public TableColumn<PhieuNhap, String> colTrangThai;
    public TableColumn<PhieuNhap, String> colGhiChu;
    public TableColumn<PhieuNhap, String> colNhanVien;
    public TitledPane tpBoLoc;

    private final ObservableList<PhieuNhap> duLieuChinh = FXCollections.observableArrayList();
    private FilteredList<PhieuNhap> duLieu;
    private boolean tpBoLocOnClick = false;

    private final PhieuNhapService phieuNhapService = new PhieuNhapService();
    private final NhaCungCapService nhaCungCapService = new NhaCungCapService();
    private final NhanVienService nhanVienService = new NhanVienService();

    public void initialize() {
        duLieuChinh.setAll(phieuNhapService.findAll());
        duLieu = new FilteredList<>(duLieuChinh, phieuNhap -> true);
        tblPhieuNhap.setItems(duLieu);

        cbxTimKiem.getItems().addAll("Loại tìm kiếm", "Mã phiếu nhập", "Nhà cung cấp", "Nhan vien");
        cbxTimKiem.setValue("Loại tìm kiếm");
        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> timKiemText());

        cbxChonNhaCC.getItems().add("Chọn nhà cung cấp");
        cbxChonNhaCC.getItems().addAll(
                nhaCungCapService.findAll().stream()
                        .map(nhaCungCap -> nhaCungCap.getTenNCC())
                        .collect(Collectors.toList())
        );
        cbxChonNhaCC.setValue("Chọn nhà cung cấp");

        chonNhanVien.getItems().add("Chon nhan vien");
        chonNhanVien.getItems().addAll(
                nhanVienService.findAll().stream()
                        .map(nhanVien -> nhanVien.getTenNV())
                        .collect(Collectors.toList())
        );
        chonNhanVien.setValue("Chon nhan vien");

        cbxChonNhaCC.setOnAction(event -> loc());
        chonNhanVien.setOnAction(event -> loc());
        cboxTrangThai.setOnAction(event -> loc());
        txtNgayNhapMin.setOnAction(event -> loc());
        txtNgayNhapMax.setOnAction(event -> loc());

        tpBoLoc.setExpanded(false);
        tpBoLoc.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            if (isNowExpanded) {
                tpBoLoc.setMinHeight(Region.USE_COMPUTED_SIZE);
                tpBoLoc.setPrefHeight(Region.USE_COMPUTED_SIZE);
            } else {
                tpBoLoc.setMaxHeight(41);
            }
            tpBoLoc.requestLayout();
        });

        tpBoLoc.setOnMouseClicked(event -> {
            tpBoLocOnClick = !tpBoLocOnClick;
            tblPhieuNhap.setPrefSize(1613, tpBoLocOnClick ? 660 : 707);
        });

        Platform.runLater(this::loadTable);
    }

    public void loadTable() {
        colMaPN.setCellValueFactory(new PropertyValueFactory<>("maPN"));
        colNhaCungCap.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getNhaCungCap().getTenNCC())
        );
        colNhaCungCap.setCellFactory(col -> leftAlignedCell());
        colNgayNhap.setCellValueFactory(cellData ->
                new SimpleStringProperty(DoiNgay.dinhDangNgay(cellData.getValue().getNgayNhap()))
        );
        colTrangThai.setCellValueFactory(cd ->
                new SimpleStringProperty(Boolean.TRUE.equals(cd.getValue().getTrangThai()) ? "Hoan tat" : "Chua hoan tat")
        );
        colGhiChu.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
        colGhiChu.setCellFactory(col -> leftAlignedCell());
        colNhanVien.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getNhanVien().getTenNV())
        );
        colNhanVien.setCellFactory(col -> leftAlignedCell());
        colChiTiet.setCellFactory(cel -> new TableCell<>() {
            private final Button btn = new Button("Chi tiet");

            {
                btn.setOnAction(event -> btnChiTietClick(getTableView().getItems().get(getIndex())));
                btn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                btn.getStyleClass().add("btn");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private TableCell<PhieuNhap, String> leftAlignedCell() {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        };
    }

    private void btnChiTietClick(PhieuNhap phieuNhap) {
        try {
            ChiTietPhieuNhap_Ctrl ctrl = new ChiTietPhieuNhap_Ctrl();
            ChiTietPhieuNhapHang_GUI gui = new ChiTietPhieuNhapHang_GUI();

            Stage dialog = new Stage();
            dialog.initOwner(txtTimKiem.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiet phieu nhap hang");
            dialog.getIcons().add(new javafx.scene.image.Image(
                    getClass().getResourceAsStream("/com/example/pharmacy/client/img/logoNguyenBan.png"))
            );

            gui.showWithController(dialog, ctrl);
            ctrl.load(phieuNhap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void timKiemText() {
        String text = txtTimKiem.getText() == null ? "" : txtTimKiem.getText().toLowerCase();
        if (text.isEmpty()) {
            duLieu.setPredicate(phieuNhap -> true);
            return;
        }
        switch (cbxTimKiem.getValue()) {
            case "Mã phiếu nhập" -> duLieu.setPredicate(phieuNhap -> containsIgnoreCase(phieuNhap.getMaPN(), text));
            case "Nhà cung cấp" -> duLieu.setPredicate(phieuNhap -> containsIgnoreCase(phieuNhap.getNhaCungCap().getTenNCC(), text));
            case "Nhan vien" -> duLieu.setPredicate(phieuNhap -> containsIgnoreCase(phieuNhap.getNhanVien().getTenNV(), text));
            default -> duLieu.setPredicate(phieuNhap -> true);
        }
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private void loc() {
        String ncc = cbxChonNhaCC.getValue();
        String nhanVien = chonNhanVien.getValue();
        Boolean trangThai = cboxTrangThai.isSelected() ? true : null;
        Timestamp ngayMin = txtNgayNhapMin.getValue() != null ? Timestamp.valueOf(txtNgayNhapMin.getValue().atStartOfDay()) : null;
        Timestamp ngayMax = txtNgayNhapMax.getValue() != null ? Timestamp.valueOf(txtNgayNhapMax.getValue().atStartOfDay()) : null;

        duLieu.setPredicate(phieuNhap -> {
            boolean matchesNcc = ncc == null || "Chọn nhà cung cấp".equals(ncc)
                    || phieuNhap.getNhaCungCap().getTenNCC().equals(ncc);
            boolean matchesNhanVien = nhanVien == null || "Chon nhan vien".equals(nhanVien)
                    || phieuNhap.getNhanVien().getTenNV().equals(nhanVien);
            boolean matchesTrangThai = trangThai == null || phieuNhap.getTrangThai().equals(trangThai);
            boolean matchesNgayMin = ngayMin == null
                    || !phieuNhap.getNgayNhap().isBefore(ngayMin.toLocalDateTime().toLocalDate());
            boolean matchesNgayMax = ngayMax == null
                    || !phieuNhap.getNgayNhap().isAfter(ngayMax.toLocalDateTime().toLocalDate());
            return matchesNcc && matchesNhanVien && matchesTrangThai && matchesNgayMin && matchesNgayMax;
        });
    }

    public void btnXoaRong(ActionEvent actionEvent) {
        txtTimKiem.clear();
        cbxChonNhaCC.setValue("Chọn nhà cung cấp");
        chonNhanVien.setValue("Chon nhan vien");
        cboxTrangThai.setSelected(false);
        txtNgayNhapMin.setValue(null);
        txtNgayNhapMax.setValue(null);
        loc();
    }
}

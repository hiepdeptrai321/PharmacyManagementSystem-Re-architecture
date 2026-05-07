package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDatHang;

import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;
import com.example.pharmacymanagementsystem_qlht.TienIch.VNDFormatter;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDatHang;
import com.example.pharmacymanagementsystem_qlht.service.PhieuDatHangService;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDatHang.ChiTietPhieuDatHang_GUI;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class TKPhieuDatHang_Ctrl {
    public TableView<PhieuDatHang> tblPD;
    public TableColumn<PhieuDatHang, Number> colSTT;
    public TableColumn<PhieuDatHang, String> colMaPD;
    public TableColumn<PhieuDatHang, String> colNgayLap;
    public TableColumn<PhieuDatHang, String> colTenKH;
    public TableColumn<PhieuDatHang, String> colSdtKH;
    public TableColumn<PhieuDatHang, String> colTenNV;
    public TableColumn<PhieuDatHang, String> colSoTienCoc;
    public TableColumn<PhieuDatHang, String> colTT;
    public TableColumn<PhieuDatHang, String> colChiTiet;
    public ComboBox<String> cboTimKiem;
    public TextField txtNoiDungTimKiem;
    public DatePicker dpTuNgay;
    public DatePicker dpDenNgay;
    public ComboBox<String> cbLoc;
    public Button btnTimKiem;
    public Button btnHuyBo;

    private final PhieuDatHangService phieuDatHangService = new PhieuDatHangService();

    public void initialize() {
        cboTimKiem.getItems().addAll("Ma phieu dat", "Ten khach hang", "SDT khach hang", "Ten nhan vien");
        cboTimKiem.setValue("Tieu chi");
        cbLoc.getItems().addAll("Tat ca", "Hom nay", "7 ngay gan nhat", "Thang nay", "Nam nay");
        cbLoc.setValue("Bo loc nhanh");

        tblPD.setRowFactory(tv -> {
            TableRow<PhieuDatHang> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    btnChiTietClick(row.getItem());
                }
            });
            return row;
        });

        btnTimKiem.setOnAction(e -> timKiem());
        btnHuyBo.setOnAction(e -> lamMoi());
        cbLoc.setOnAction(e -> boLocNhanh());

        Platform.runLater(this::loadTable);
    }

    public void loadTable() {
        List<PhieuDatHang> list = phieuDatHangService.findAll();
        ObservableList<PhieuDatHang> data = FXCollections.observableArrayList(list);
        colSTT.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(tblPD.getItems().indexOf(cellData.getValue()) + 1)
        );
        colSTT.setSortable(false);
        colMaPD.setCellValueFactory(new PropertyValueFactory<>("maPDat"));
        colNgayLap.setCellValueFactory(cellData ->
                new SimpleStringProperty(DoiNgay.dinhDangThoiGian(cellData.getValue().getNgayLap()))
        );
        colTenKH.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKhachHang().getTenKH()));
        colSdtKH.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKhachHang().getSdt()));
        colSoTienCoc.setCellValueFactory(cellData ->
                new SimpleStringProperty(VNDFormatter.format(cellData.getValue().getSoTienCoc()))
        );
        colTenKH.setCellFactory(col -> leftAlignedCell());
        colTenNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNhanVien().getTenNV()));
        colTenNV.setCellFactory(col -> leftAlignedCell());
        colChiTiet.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Xem");

            {
                btn.setOnAction(event -> btnChiTietClick(getTableView().getItems().get(getIndex())));
                btn.setStyle("-fx-background-color: #188dfb; -fx-text-fill: white");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        colTT.setCellValueFactory(cellData -> new SimpleStringProperty(getTrangThaiText(cellData.getValue().getTrangthai())));
        tblPD.setItems(data);
    }

    private TableCell<PhieuDatHang, String> leftAlignedCell() {
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

    private String getTrangThaiText(int trangThai) {
        return switch (trangThai) {
            case 0 -> "Chua co hang";
            case 1 -> "San hang";
            default -> "Da hoan thanh";
        };
    }

    private void btnChiTietClick(PhieuDatHang phieuDatHang) {
        try {
            ChiTietPhieuDatHang_Ctrl ctrl = new ChiTietPhieuDatHang_Ctrl();
            Stage stage = new Stage();
            stage.setTitle("Chi tiet phieu dat hang");
            stage.initModality(Modality.APPLICATION_MODAL);
            ctrl.setPhieuDatHang(phieuDatHangService.findById(phieuDatHang.getMaPDat()));
            new ChiTietPhieuDatHang_GUI().showWithController(stage, ctrl);
            stage.getIcons().add(new Image(
                    getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void timKiem() {
        String tieuChi = cboTimKiem.getValue();
        String noiDung = txtNoiDungTimKiem.getText() == null ? "" : txtNoiDungTimKiem.getText().trim().toLowerCase();
        var tuNgay = dpTuNgay == null ? null : dpTuNgay.getValue();
        var denNgay = dpDenNgay == null ? null : dpDenNgay.getValue();

        List<PhieuDatHang> filtered = phieuDatHangService.findAll().stream()
                .filter(phieuDatHang -> matchCriteria(phieuDatHang, tieuChi, noiDung))
                .filter(phieuDatHang -> tuNgay == null || !phieuDatHang.getNgayLap().toLocalDateTime().toLocalDate().isBefore(tuNgay))
                .filter(phieuDatHang -> denNgay == null || !phieuDatHang.getNgayLap().toLocalDateTime().toLocalDate().isAfter(denNgay))
                .toList();
        tblPD.setItems(FXCollections.observableArrayList(filtered));
    }

    private boolean matchCriteria(PhieuDatHang phieuDatHang, String tieuChi, String noiDung) {
        if (noiDung.isEmpty()) {
            return true;
        }
        return switch (tieuChi) {
            case "Ma phieu dat" -> containsIgnoreCase(phieuDatHang.getMaPDat(), noiDung);
            case "Ten khach hang" -> phieuDatHang.getKhachHang() != null
                    && containsIgnoreCase(phieuDatHang.getKhachHang().getTenKH(), noiDung);
            case "SDT khach hang" -> phieuDatHang.getKhachHang() != null
                    && containsIgnoreCase(phieuDatHang.getKhachHang().getSdt(), noiDung);
            case "Ten nhan vien" -> phieuDatHang.getNhanVien() != null
                    && containsIgnoreCase(phieuDatHang.getNhanVien().getTenNV(), noiDung);
            default -> true;
        };
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private void lamMoi() {
        txtNoiDungTimKiem.clear();
        cboTimKiem.setValue("Tieu chi");
        if (dpTuNgay != null) dpTuNgay.setValue(null);
        if (dpDenNgay != null) dpDenNgay.setValue(null);
        cbLoc.setValue("Tat ca");
        loadTable();
    }

    private void boLocNhanh() {
        String loc = cbLoc.getValue();
        var today = java.time.LocalDate.now();
        if (loc == null) {
            return;
        }
        switch (loc) {
            case "Tat ca" -> {
                if (dpTuNgay != null) dpTuNgay.setValue(null);
                if (dpDenNgay != null) dpDenNgay.setValue(null);
            }
            case "Hom nay" -> {
                if (dpTuNgay != null) dpTuNgay.setValue(today);
                if (dpDenNgay != null) dpDenNgay.setValue(today);
            }
            case "7 ngay gan nhat" -> {
                if (dpTuNgay != null) dpTuNgay.setValue(today.minusDays(6));
                if (dpDenNgay != null) dpDenNgay.setValue(today);
            }
            case "Thang nay" -> {
                if (dpTuNgay != null) dpTuNgay.setValue(today.withDayOfMonth(1));
                if (dpDenNgay != null) dpDenNgay.setValue(today);
            }
            case "Nam nay" -> {
                if (dpTuNgay != null) dpTuNgay.setValue(today.withDayOfYear(1));
                if (dpDenNgay != null) dpDenNgay.setValue(today);
            }
            default -> {
            }
        }
        timKiem();
    }
}

package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang;

import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDoiHang;
import com.example.pharmacymanagementsystem_qlht.service.DoiTraService;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi.ChiTietPhieuDoiHang_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDoi.TKPhieuDoiHang_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class TKPhieuDoiHang_Ctrl extends Application {
    public TableView<PhieuDoiHang> tblPD;
    public TableColumn<PhieuDoiHang, Number> colSTT;
    public TableColumn<PhieuDoiHang, String> colMaPD;
    public TableColumn<PhieuDoiHang, String> colMaHD;
    public TableColumn<PhieuDoiHang, String> colNgayLap;
    public TableColumn<PhieuDoiHang, String> colTenKH;
    public TableColumn<PhieuDoiHang, String> colSdtKH;
    public TableColumn<PhieuDoiHang, String> colTenNV;
    public TableColumn<PhieuDoiHang, String> colChiTiet;
    public ComboBox<String> cboTimKiem;
    public TextField txtNoiDungTimKiem;
    public DatePicker dpTuNgay;
    public DatePicker dpDenNgay;
    public ComboBox<String> cbLoc;
    public Button btnTimKiem;
    public Button btnHuyBo;

    private final DoiTraService doiTraService = new DoiTraService();
    private boolean initialized;

    @Override
    public void start(Stage stage) {
        new TKPhieuDoiHang_GUI().showWithController(stage, this);
        stage.show();
    }

    public void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;

        cboTimKiem.getItems().addAll(
                "Ma phieu doi", "Ma hoa don", "Ten khach hang", "SDT khach hang", "Ten nhan vien", "Ngay lap"
        );
        cboTimKiem.setValue("Tieu chi");
        cbLoc.getItems().addAll(
                "Tat ca", "Hom nay", "7 ngay gan nhat", "Thang nay", "Nam nay"
        );
        cbLoc.setValue("⌛ Bo loc nhanh");

        tblPD.setRowFactory(tv -> {
            TableRow<PhieuDoiHang> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    moChiTiet(row.getItem());
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
        List<PhieuDoiHang> list = doiTraService.findAllPhieuDoi();
        tblPD.setItems(FXCollections.observableArrayList(list));

        colSTT.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(tblPD.getItems().indexOf(cd.getValue()) + 1));
        colMaPD.setCellValueFactory(new PropertyValueFactory<>("maPD"));
        colMaHD.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getHoaDon() == null ? "" : safe(cd.getValue().getHoaDon().getMaHD())
        ));
        colNgayLap.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getNgayLap() == null ? "" : DoiNgay.dinhDangThoiGian(cd.getValue().getNgayLap())
        ));
        colTenKH.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getKhachHang() == null ? "" : safe(cd.getValue().getKhachHang().getTenKH())
        ));
        colTenKH.setCellFactory(col -> alignedLeftCell());
        colSdtKH.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getKhachHang() == null ? "" : safe(cd.getValue().getKhachHang().getSdt())
        ));
        colTenNV.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getNhanVien() == null ? "" : safe(cd.getValue().getNhanVien().getTenNV())
        ));
        colTenNV.setCellFactory(col -> alignedLeftCell());
        colChiTiet.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Xem");

            {
                btn.setOnAction(event -> moChiTiet(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
                setText(null);
            }
        });
    }

    private void timKiem() {
        String tieuChi = cboTimKiem.getValue();
        String noiDung = txtNoiDungTimKiem.getText().trim().toLowerCase();
        var tuNgay = dpTuNgay == null ? null : dpTuNgay.getValue();
        var denNgay = dpDenNgay == null ? null : dpDenNgay.getValue();
        List<PhieuDoiHang> list = doiTraService.findAllPhieuDoi();

        if ((tieuChi == null || "Tieu chi".equals(tieuChi)) && (tuNgay == null && denNgay == null) && noiDung.isEmpty()) {
            loadTable();
            return;
        }

        List<PhieuDoiHang> filtered = list.stream().filter(phieuDoiHang -> {
            boolean match = true;
            switch (tieuChi) {
                case "Ma phieu doi" -> match = safe(phieuDoiHang.getMaPD()).toLowerCase().contains(noiDung);
                case "Ma hoa don" -> match = phieuDoiHang.getHoaDon() != null
                        && safe(phieuDoiHang.getHoaDon().getMaHD()).toLowerCase().contains(noiDung);
                case "Ten khach hang" -> match = phieuDoiHang.getKhachHang() != null
                        && safe(phieuDoiHang.getKhachHang().getTenKH()).toLowerCase().contains(noiDung);
                case "SDT khach hang" -> match = phieuDoiHang.getKhachHang() != null
                        && safe(phieuDoiHang.getKhachHang().getSdt()).toLowerCase().contains(noiDung);
                case "Ten nhan vien" -> match = phieuDoiHang.getNhanVien() != null
                        && safe(phieuDoiHang.getNhanVien().getTenNV()).toLowerCase().contains(noiDung);
                case "Ngay lap" -> match = phieuDoiHang.getNgayLap() != null
                        && DoiNgay.dinhDangThoiGian(phieuDoiHang.getNgayLap()).toLowerCase().contains(noiDung);
                default -> {
                }
            }

            if (tuNgay != null && phieuDoiHang.getNgayLap() != null) {
                match = match && !phieuDoiHang.getNgayLap().toLocalDateTime().toLocalDate().isBefore(tuNgay);
            }
            if (denNgay != null && phieuDoiHang.getNgayLap() != null) {
                match = match && !phieuDoiHang.getNgayLap().toLocalDateTime().toLocalDate().isAfter(denNgay);
            }
            return match;
        }).toList();

        tblPD.setItems(FXCollections.observableArrayList(filtered));
    }

    private void lamMoi() {
        txtNoiDungTimKiem.clear();
        cboTimKiem.setValue("Tieu chi");
        if (dpTuNgay != null) {
            dpTuNgay.setValue(null);
        }
        if (dpDenNgay != null) {
            dpDenNgay.setValue(null);
        }
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
                dpTuNgay.setValue(null);
                dpDenNgay.setValue(null);
            }
            case "Hom nay" -> {
                dpTuNgay.setValue(today);
                dpDenNgay.setValue(today);
            }
            case "7 ngay gan nhat" -> {
                dpTuNgay.setValue(today.minusDays(6));
                dpDenNgay.setValue(today);
            }
            case "Thang nay" -> {
                dpTuNgay.setValue(today.withDayOfMonth(1));
                dpDenNgay.setValue(today);
            }
            case "Nam nay" -> {
                dpTuNgay.setValue(today.withDayOfYear(1));
                dpDenNgay.setValue(today);
            }
            default -> {
            }
        }
        timKiem();
    }

    private void moChiTiet(PhieuDoiHang phieuDoiHang) {
        try {
            ChiTietPhieuDoiHang_Ctrl ctrl = new ChiTietPhieuDoiHang_Ctrl();
            Stage dialog = new Stage();
            dialog.initOwner(btnTimKiem.getScene().getWindow());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiet phieu doi hang");
            new ChiTietPhieuDoiHang_GUI().showWithController(dialog, ctrl);
            ctrl.setPhieuDoiHang(phieuDoiHang);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private TableCell<PhieuDoiHang, String> alignedLeftCell() {
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

    private String safe(String value) {
        return value == null ? "" : value;
    }
}

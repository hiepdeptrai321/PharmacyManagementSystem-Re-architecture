package com.example.pharmacy.client.controller.CN_TimKiem.TKPhieuDoiHang;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.client.TienIch.DoiNgay;
import com.example.pharmacy.common.model.PhieuDoiHangDto;
import com.example.pharmacy.client.service.DoiTraService;
import com.example.pharmacy.client.view.CN_TimKiem.TKPhieuDoi.ChiTietPhieuDoiHang_GUI;
import com.example.pharmacy.client.view.CN_TimKiem.TKPhieuDoi.TKPhieuDoiHang_GUI;
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
    public TableView<PhieuDoiHangDto> tblPD;
    public TableColumn<PhieuDoiHangDto, Number> colSTT;
    public TableColumn<PhieuDoiHangDto, String> colMaPD;
    public TableColumn<PhieuDoiHangDto, String> colMaHD;
    public TableColumn<PhieuDoiHangDto, String> colNgayLap;
    public TableColumn<PhieuDoiHangDto, String> colTenKH;
    public TableColumn<PhieuDoiHangDto, String> colSdtKH;
    public TableColumn<PhieuDoiHangDto, String> colTenNV;
    public TableColumn<PhieuDoiHangDto, String> colChiTiet;
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
                "Mã phiếu đổi", "Mã hóa đơn", "Tên khách hàng", "SĐT khách hàng", "Tên nhân viên", "Ngày lập"
        );
        cboTimKiem.setValue("Tiêu chí");
        cbLoc.getItems().addAll(
                "Tất cả", "Hôm nay", "7 ngày gần nhất", "Tháng này", "Năm nay"
        );
        cbLoc.setValue("⌛ Bo loc nhanh");

        tblPD.setRowFactory(tv -> {
            TableRow<PhieuDoiHangDto> row = new TableRow<>();
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
        List<PhieuDoiHangDto> list = doiTraService.findAllPhieuDoi();
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
        List<PhieuDoiHangDto> list = doiTraService.findAllPhieuDoi();

        if ((tieuChi == null || "Tiêu chí".equals(tieuChi)) && (tuNgay == null && denNgay == null) && noiDung.isEmpty()) {
            loadTable();
            return;
        }

        List<PhieuDoiHangDto> filtered = list.stream().filter(phieuDoiHang -> {
            boolean match = true;
            switch (tieuChi) {
                case "Mã phiếu đổi" -> match = safe(phieuDoiHang.getMaPD()).toLowerCase().contains(noiDung);
                case "Mã hóa đơn" -> match = phieuDoiHang.getHoaDon() != null
                        && safe(phieuDoiHang.getHoaDon().getMaHD()).toLowerCase().contains(noiDung);
                case "Tên khách hàng" -> match = phieuDoiHang.getKhachHang() != null
                        && safe(phieuDoiHang.getKhachHang().getTenKH()).toLowerCase().contains(noiDung);
                case "SĐT khách hàng" -> match = phieuDoiHang.getKhachHang() != null
                        && safe(phieuDoiHang.getKhachHang().getSdt()).toLowerCase().contains(noiDung);
                case "Tên nhân viên" -> match = phieuDoiHang.getNhanVien() != null
                        && safe(phieuDoiHang.getNhanVien().getTenNV()).toLowerCase().contains(noiDung);
                case "Ngày lập" -> match = phieuDoiHang.getNgayLap() != null
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
        cboTimKiem.setValue("Tiêu chí");
        if (dpTuNgay != null) {
            dpTuNgay.setValue(null);
        }
        if (dpDenNgay != null) {
            dpDenNgay.setValue(null);
        }
        cbLoc.setValue("Tất cả");
        loadTable();
    }

    private void boLocNhanh() {
        String loc = cbLoc.getValue();
        var today = java.time.LocalDate.now();
        if (loc == null) {
            return;
        }
        switch (loc) {
            case "Tất cả" -> {
                dpTuNgay.setValue(null);
                dpDenNgay.setValue(null);
            }
            case "Hôm nay" -> {
                dpTuNgay.setValue(today);
                dpDenNgay.setValue(today);
            }
            case "7 ngày gần nhất" -> {
                dpTuNgay.setValue(today.minusDays(6));
                dpDenNgay.setValue(today);
            }
            case "Tháng này" -> {
                dpTuNgay.setValue(today.withDayOfMonth(1));
                dpDenNgay.setValue(today);
            }
            case "Năm nay" -> {
                dpTuNgay.setValue(today.withDayOfYear(1));
                dpDenNgay.setValue(today);
            }
            default -> {
            }
        }
        timKiem();
    }

    private void moChiTiet(PhieuDoiHangDto phieuDoiHang) {
        try {
            ChiTietPhieuDoiHang_Ctrl ctrl = new ChiTietPhieuDoiHang_Ctrl();
            Stage dialog = new Stage();
        TuyChinhAlert.setAppIcon(dialog);
            dialog.initOwner(btnTimKiem.getScene().getWindow());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiet phieu doi hang");
            new ChiTietPhieuDoiHang_GUI().showWithController(dialog, ctrl);
            ctrl.setPhieuDoiHang(phieuDoiHang);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private TableCell<PhieuDoiHangDto, String> alignedLeftCell() {
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

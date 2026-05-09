
package com.example.pharmacy.client.controller.CN_TimKiem.TKHoatDong;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.common.model.HoatDongDto;
import com.example.pharmacy.common.model.NhanVienDto;
import com.example.pharmacy.client.service.HoatDongService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class TKHoatDong_Ctrl {

    public TextField tfTim;
    public Button btnTim;
    public Button btnLamMoi;
    public TableView<HoatDongDto> tbHoatDong;
    public TableColumn<HoatDongDto, String> colSTT;
    public TableColumn<HoatDongDto, String> colMa;
    public TableColumn<HoatDongDto, String> colLoai;
    public TableColumn<HoatDongDto, String> colBang;
    public TableColumn<HoatDongDto, String> colThoiGian;
    public TableColumn<HoatDongDto, String> colNguoi;
    public TableColumn<HoatDongDto, String> colChiTiet;
    public DatePicker dpTuNgay;
    public DatePicker dpDenNgay;
    public ComboBox<String> cbBoLoc;

    private final HoatDongService hoatDongService = new HoatDongService();
    private final SimpleDateFormat tsFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    public void start(Stage stage) {
        new com.example.pharmacy.client.view.CN_TimKiem.TKHoatDong.TKHoatDong_GUI()
                .showWithController(stage, this);
    }

    public void initialize() {
        configureColumns();
        configureFilters();
        if (btnTim != null) btnTim.setOnAction(e -> applySearch());
        tfTim.setOnAction(e-> applySearch());
        Platform.runLater(()-> {
            loadTable();
        });
        btnLamMoi.setOnAction(e-> LamMoi());
    }

    private void configureFilters() {
        cbBoLoc.setItems(FXCollections.observableArrayList("Tất cả", "Hôm nay", "Tuần này", "Tháng này"));
        cbBoLoc.getSelectionModel().selectFirst();

        if (tfTim != null) {
            tfTim.setOnAction(e -> applySearch());
            tfTim.setOnKeyPressed(k -> { if (k.getCode() == KeyCode.ENTER) applySearch(); });
        }

        if (dpTuNgay != null) dpTuNgay.valueProperty().addListener((obs, o, n) -> applySearch());
        if (dpDenNgay != null) dpDenNgay.valueProperty().addListener((obs, o, n) -> applySearch());

        if (cbBoLoc != null) cbBoLoc.setOnAction(e -> {
            String sel = cbBoLoc.getValue();
            setRangeForPreset(sel);
            applySearch();
        });
    }

    private void setRangeForPreset(String preset) {
        if (preset == null || "Tất cả".equals(preset)) {
            if (dpTuNgay != null) dpTuNgay.setValue(null);
            if (dpDenNgay != null) dpDenNgay.setValue(null);
            return;
        }
        LocalDate now = LocalDate.now();
        switch (preset) {
            case "Hôm nay" -> {
                if (dpTuNgay != null) dpTuNgay.setValue(now);
                if (dpDenNgay != null) dpDenNgay.setValue(now);
            }
            case "Tuần này" -> {
                LocalDate start = now.with(DayOfWeek.MONDAY);
                LocalDate end = now.with(DayOfWeek.SUNDAY);
                if (dpTuNgay != null) dpTuNgay.setValue(start);
                if (dpDenNgay != null) dpDenNgay.setValue(end);
            }
            case "Tháng này" -> {
                LocalDate start = now.withDayOfMonth(1);
                LocalDate end = now.withDayOfMonth(now.lengthOfMonth());
                if (dpTuNgay != null) dpTuNgay.setValue(start);
                if (dpDenNgay != null) dpDenNgay.setValue(end);
            }
            default -> {
                if (dpTuNgay != null) dpTuNgay.setValue(null);
                if (dpDenNgay != null) dpDenNgay.setValue(null);
            }
        }
    }

    private void configureColumns() {
        colSTT.setCellValueFactory(cellData -> {
            int idx = cellData.getTableView().getItems().indexOf(cellData.getValue());
            String display = idx < 0 ? "" : String.valueOf(idx + 1);
            return new javafx.beans.property.SimpleStringProperty(display);
        });

        colMa.setCellValueFactory(new PropertyValueFactory<>("maHD"));
        colLoai.setCellValueFactory(new PropertyValueFactory<>("loaiHD"));
        colBang.setCellValueFactory(new PropertyValueFactory<>("bang"));

        colThoiGian.setCellValueFactory(cd -> {
            Timestamp t = cd.getValue().getThoiGian();
            String s = t == null ? "" : tsFormat.format(t);
            return new javafx.beans.property.SimpleStringProperty(s);
        });

        colNguoi.setCellValueFactory(cd -> {
            NhanVienDto nv = cd.getValue().getNhanVien();
            String s = nv == null ? "" : nv.getTenNV();
            return new javafx.beans.property.SimpleStringProperty(s);
        });
        colNguoi.setCellFactory(col -> new TableCell<HoatDongDto, String>() {
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
        });

        colChiTiet.setCellFactory(col -> new TableCell<HoatDongDto, String>() {
            private final Button btn = new Button("Chi tiết");
            {
                btn.setOnAction(event -> {
                    HoatDongDto hd = getTableView().getItems().get(getIndex());
                    showDetails(hd);
                });
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

    private void loadTable() {
        List<HoatDongDto> list = hoatDongService.findAll();
        tbHoatDong.setItems(FXCollections.observableArrayList(list));
        tbHoatDong.refresh();
    }

    private void applySearch() {
        String keyword = tfTim == null ? "" : (tfTim.getText() == null ? "" : tfTim.getText().trim().toLowerCase());

        LocalDate fromDate = dpTuNgay == null ? null : dpTuNgay.getValue();
        LocalDate toDate = dpDenNgay == null ? null : dpDenNgay.getValue();

        List<HoatDongDto> filtered = hoatDongService.search(keyword, fromDate, toDate).stream()
                .filter(h -> {
                    if (keyword.isEmpty()) {
                        return true;
                    }
                    boolean ma = h.getMaHD() != null && h.getMaHD().toLowerCase().contains(keyword);
                    boolean nv = h.getNhanVien() != null && h.getNhanVien().toString().toLowerCase().contains(keyword);
                    return ma || nv;
                })
                .toList();

        tbHoatDong.setItems(FXCollections.observableArrayList(filtered));
        tbHoatDong.refresh();
    }

    private void showDetails(HoatDongDto hd) {
        Stage chiTiet = new Stage();
        TuyChinhAlert.setAppIcon(chiTiet);
        try {
            ChiTietHoatDong_Ctrl ctrl = new ChiTietHoatDong_Ctrl();
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.setTitle("Chi tiết hoạt động");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            // show UI built in code (SuaGiaThuoc_GUI)
            new com.example.pharmacy.client.view.CN_TimKiem.TKHoatDong.ChiTietHoatDong_GUI()
                    .showWithController(stage, ctrl);
            ctrl.loadData(hd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LamMoi() {
        tfTim.clear();
        loadTable();
    }

}

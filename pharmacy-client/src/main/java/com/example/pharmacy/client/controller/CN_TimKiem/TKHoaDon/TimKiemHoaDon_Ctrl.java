package com.example.pharmacy.client.controller.CN_TimKiem.TKHoaDon;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.client.TienIch.DoiNgay;
import com.example.pharmacy.common.model.HoaDon;
import com.example.pharmacy.client.service.HoaDonService;
import com.example.pharmacy.client.view.CN_TimKiem.TKHoaDon.ChiTietHoaDon_GUI;
import com.example.pharmacy.client.view.CN_TimKiem.TKHoaDon.TKHoaDon_GUI;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class TimKiemHoaDon_Ctrl extends Application {
    public TableView<HoaDon> tblHD;
    public TableColumn<HoaDon, String> colMaHD;
    public TableColumn<HoaDon, String> colNgayLap;
    public TableColumn<HoaDon, String> colTenKH;
    public TableColumn<HoaDon, String> colSdtKH;
    public TableColumn<HoaDon, String> colTenNV;
    public TableColumn<HoaDon, Integer> colSLP;
    public TableColumn<HoaDon, String> colChiTiet;

    public ComboBox<String> cboTieuChiTimKiem;
    public TextField txtNoiDungTimKiem;
    public DatePicker dpTuNgay;
    public DatePicker dpDenNgay;
    public ComboBox<String> cboBoLocNhanh;
    public Button btnTimKiem;
    public Button btnHuyBo;

    public StackPane rootTablePane;

    private final HoaDonService hoaDonService = new HoaDonService();

    @Override
    public void start(Stage stage) {
        TKHoaDon_GUI.showWithController(stage, this);
    }

    public void initialize() {
        cboTieuChiTimKiem.getItems().setAll(
                "Mã hóa đơn",
                "Tên khách hàng",
                "SĐT khách hàng",
                "Tên nhân viên",
                "Khách vãng lai"
        );
        cboTieuChiTimKiem.setValue("Mã hóa đơn");

        cboBoLocNhanh.getItems().setAll(
                "Tất cả",
                "Hôm nay",
                "7 ngày gần nhất",
                "Tháng này",
                "Năm nay"
        );
        cboBoLocNhanh.setValue("Tất cả");

        btnTimKiem.setOnAction(e -> timKiem());
        btnHuyBo.setOnAction(e -> lamMoi());
        cboBoLocNhanh.setOnAction(e -> boLocNhanh());
        txtNoiDungTimKiem.textProperty().addListener((obs, oldVal, newVal) -> timKiem());

        tblHD.setRowFactory(tv -> {
            TableRow<HoaDon> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    btnChiTietClick(row.getItem());
                }
            });
            return row;
        });

        loadTable();
    }

    public void loadTable() {
        ObservableList<HoaDon> data = FXCollections.observableArrayList(hoaDonService.findAll());
        colMaHD.setCellValueFactory(new PropertyValueFactory<>("maHD"));
        colNgayLap.setCellValueFactory(cellData ->
                new SimpleStringProperty(DoiNgay.dinhDangThoiGian(cellData.getValue().getNgayLap()))
        );
        colTenKH.setCellValueFactory(cellData -> {
            HoaDon hoaDon = cellData.getValue();
            String ten = hoaDon.getMaKH() == null || hoaDon.getMaKH().getTenKH() == null || hoaDon.getMaKH().getTenKH().isBlank()
                    ? "Khách vãng lai"
                    : hoaDon.getMaKH().getTenKH();
            return new SimpleStringProperty(ten);
        });
        colSdtKH.setCellValueFactory(cellData -> {
            HoaDon hoaDon = cellData.getValue();
            String sdt = hoaDon.getMaKH() == null ? "" : safe(hoaDon.getMaKH().getSdt());
            return new SimpleStringProperty(sdt);
        });
        colTenNV.setCellValueFactory(cellData -> {
            HoaDon hoaDon = cellData.getValue();
            String ten = hoaDon.getMaNV() == null ? "" : safe(hoaDon.getMaNV().getTenNV());
            return new SimpleStringProperty(ten);
        });
        colSLP.setCellValueFactory(cellData -> new SimpleIntegerProperty(0).asObject());
        colChiTiet.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            private final Button btn = new Button("Xem");

            {
                btn.setOnAction(event -> {
                    HoaDon hd = getTableView().getItems().get(getIndex());
                    btnChiTietClick(hd);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        tblHD.setItems(data);
    }

    private void btnChiTietClick(HoaDon hoaDon) {
        try {
            ChiTietHoaDon_Ctrl ctrl = new ChiTietHoaDon_Ctrl();
            ChiTietHoaDon_GUI gui = new ChiTietHoaDon_GUI();
            Stage dialog = new Stage();
        TuyChinhAlert.setAppIcon(dialog);
            dialog.initOwner(btnTimKiem.getScene().getWindow());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiet hoa don " + hoaDon.getMaHD());
            gui.showWithController(dialog, ctrl);
            ctrl.setHoaDon(hoaDon);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void timKiem() {
        String criteria = cboTieuChiTimKiem.getValue();
        String keyword = txtNoiDungTimKiem.getText();
        List<HoaDon> filtered = hoaDonService.search(buildSearch(criteria, keyword));
        tblHD.setItems(FXCollections.observableArrayList(filtered));
    }

    private com.example.pharmacy.common.request.HoaDonSearchRequest buildSearch(String criteria, String keyword) {
        com.example.pharmacy.common.request.HoaDonSearchRequest request = new com.example.pharmacy.common.request.HoaDonSearchRequest();
        request.setCriteria(criteria);
        request.setKeyword(keyword);
        request.setFromDate(dpTuNgay.getValue());
        request.setToDate(dpDenNgay.getValue());
        return request;
    }

    private void lamMoi() {
        txtNoiDungTimKiem.clear();
        cboTieuChiTimKiem.setValue("Mã hóa đơn");
        dpTuNgay.setValue(null);
        dpDenNgay.setValue(null);
        cboBoLocNhanh.setValue("Tất cả");
        loadTable();
    }

    private void boLocNhanh() {
        java.time.LocalDate today = java.time.LocalDate.now();
        String value = cboBoLocNhanh.getValue();
        if ("Hôm nay".equals(value)) {
            dpTuNgay.setValue(today);
            dpDenNgay.setValue(today);
        } else if ("7 ngày gần nhất".equals(value)) {
            dpTuNgay.setValue(today.minusDays(6));
            dpDenNgay.setValue(today);
        } else if ("Tháng này".equals(value)) {
            dpTuNgay.setValue(today.withDayOfMonth(1));
            dpDenNgay.setValue(today);
        } else if ("Năm nay".equals(value)) {
            dpTuNgay.setValue(today.withDayOfYear(1));
            dpDenNgay.setValue(today);
        } else {
            dpTuNgay.setValue(null);
            dpDenNgay.setValue(null);
        }
        timKiem();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}

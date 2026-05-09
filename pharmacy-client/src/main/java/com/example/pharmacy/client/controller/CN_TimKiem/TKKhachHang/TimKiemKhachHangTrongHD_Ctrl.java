package com.example.pharmacy.client.controller.CN_TimKiem.TKKhachHang;

import com.example.pharmacy.client.TienIch.DoiNgay;
import com.example.pharmacy.common.model.KhachHangDto;
import com.example.pharmacy.client.service.KhachHangService;
import com.example.pharmacy.client.view.CN_TimKiem.TKKhachHang.TimKiemKhachHangTrongHD_GUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class TimKiemKhachHangTrongHD_Ctrl extends Application {
    public Button btnLamMoi;
    public Button btnTim;
    public ComboBox<String> cboTimKiem;
    public TableColumn<KhachHangDto, String> cotDiaChi;
    public TableColumn<KhachHangDto, String> cotEmail;
    public TableColumn<KhachHangDto, String> cotGT;
    public TableColumn<KhachHangDto, String> cotMaKH;
    public TableColumn<KhachHangDto, String> cotNgaySinh;
    public TableColumn<KhachHangDto, String> cotSDT;
    public TableColumn<KhachHangDto, String> cotTenKH;
    public TableColumn<KhachHangDto, String> cotSTT;
    public TableView<KhachHangDto> tbKhachHang;
    public Pane mainPane;
    public TextField txtTimKiem;

    private final KhachHangService khachHangService = new KhachHangService();
    private Consumer<KhachHangDto> onSelected;

    public void setOnSelected(Consumer<KhachHangDto> onSelected) {
        this.onSelected = onSelected;
    }

    @Override
    public void start(Stage stage) throws Exception {
        TimKiemKhachHangTrongHD_GUI gui = new TimKiemKhachHangTrongHD_GUI();
        gui.showWithController(stage, this);
    }

    public void initialize() {
        cboTimKiem.getItems().addAll(
                "Theo mã, tên khách hàng",
                "Theo email",
                "Theo SDT"
        );
        cboTimKiem.setValue("Theo mã, tên khách hàng");
        loadTable();
        btnLamMoi.setOnAction(e -> LamMoi());
        btnTim.setOnAction(e -> TimKiem());
        tbKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> chonKhachHang(newSel));
        tbKhachHang.setRowFactory(tv -> {
            TableRow<KhachHangDto> row = new TableRow<>();
            row.setOnMouseEntered(e -> {
                if (!row.isEmpty()) {
                    row.setStyle("-fx-background-color: #f2f2f2;");
                }
            });
            row.setOnMouseExited(e -> row.setStyle(""));
            return row;
        });
    }

    public void chonKhachHang(KhachHangDto kh) {
        if (kh == null) {
            return;
        }
        if (onSelected != null) {
            onSelected.accept(kh);
        }
        if (tbKhachHang != null && tbKhachHang.getScene() != null) {
            Stage st = (Stage) tbKhachHang.getScene().getWindow();
            if (st != null) {
                st.close();
            }
        }
    }

    public void loadTable() {
        List<KhachHangDto> list = khachHangService.findAll();
        ObservableList<KhachHangDto> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbKhachHang.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        cotTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        cotGT.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        cotNgaySinh.setCellValueFactory(cellData -> new SimpleStringProperty(DoiNgay.dinhDangNgay(cellData.getValue().getNgaySinh())));
        cotSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        cotEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cotDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        tbKhachHang.setItems(data);
    }

    public void LamMoi() {
        txtTimKiem.clear();
        cboTimKiem.setValue("Theo mã, tên khách hàng");
        loadTable();
    }

    public void TimKiem() {
        String criteria = cboTimKiem.getValue();
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<KhachHangDto> filtered = khachHangService.search(criteria, keyword);
        tbKhachHang.setItems(FXCollections.observableArrayList(filtered));
    }
}

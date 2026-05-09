package com.example.pharmacy.client.controller.CN_TimKiem.TKKhachHang;

import com.example.pharmacy.client.TienIch.DoiNgay;
import com.example.pharmacy.common.model.KhachHang;
import com.example.pharmacy.client.service.KhachHangService;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class TimKiemKhachHang_Ctrl extends Application {
    public Button btnLamMoi;
    public Button btnTim;
    public ComboBox<String> cboTimKiem;
    public TableColumn<KhachHang, String> cotDiaChi;
    public TableColumn<KhachHang, String> cotEmail;
    public TableColumn<KhachHang, String> cotGT;
    public TableColumn<KhachHang, String> cotMaKH;
    public TableColumn<KhachHang, String> cotNgaySinh;
    public TableColumn<KhachHang, String> cotSDT;
    public TableColumn<KhachHang, String> cotTenKH;
    public TableColumn<KhachHang, String> cotSTT;
    public TableView<KhachHang> tbKhachHang;
    public Pane mainPane;
    public TextField txtTimKiem;

    private final KhachHangService khachHangService = new KhachHangService();
    private Consumer<KhachHang> onSelected;

    public void setOnSelected(Consumer<KhachHang> onSelected) {
        this.onSelected = onSelected;
    }

    @FXML
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
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        new com.example.pharmacy.client.view.CN_TimKiem.TKKhachHang.TimKiemKhachHang_GUI()
                .showWithController(primaryStage, this);
    }

    public void loadTable() {
        List<KhachHang> list = khachHangService.findAll();
        ObservableList<KhachHang> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbKhachHang.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        cotTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        cotTenKH.setCellFactory(col -> new TableCell<>() {
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
        cotGT.setCellValueFactory(cellData -> {
            Boolean gt = cellData.getValue().getGioiTinh();
            String text = Boolean.TRUE.equals(gt) ? "Nam" : "Nữ";
            return new SimpleStringProperty(text);
        });
        cotNgaySinh.setCellValueFactory(cellData -> new SimpleStringProperty(DoiNgay.dinhDangNgay(cellData.getValue().getNgaySinh())));
        cotSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        cotEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cotEmail.setCellFactory(col -> new TableCell<>() {
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
        cotDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        cotDiaChi.setCellFactory(col -> new TableCell<>() {
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
        tbKhachHang.setItems(data);
    }

    @FXML
    private void LamMoi() {
        txtTimKiem.clear();
        cboTimKiem.setValue("Theo mã, tên khách hàng");
        loadTable();
    }

    @FXML
    private void TimKiem() {
        String criteria = cboTimKiem.getValue();
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<KhachHang> filtered = khachHangService.search(criteria, keyword);
        tbKhachHang.setItems(FXCollections.observableArrayList(filtered));
    }
}

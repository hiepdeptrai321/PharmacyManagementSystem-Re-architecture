package com.example.pharmacy.client.controller.CN_DanhMuc.DMNhanVien;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.common.model.NhanVienDto;
import com.example.pharmacy.client.service.NhanVienService;
import com.example.pharmacy.client.view.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_GUI;
import com.example.pharmacy.client.view.CN_DanhMuc.DMNhanVien.ThemNhanVien_GUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class DanhMucNhanVien_Ctrl extends Application {
    public TableView<NhanVienDto> tblNhanVien;
    public TableColumn<NhanVienDto, String> colSTT;
    public TableColumn<NhanVienDto, String> colMaNV;
    public TableColumn<NhanVienDto, String> colTenNV;
    public TableColumn<NhanVienDto, String> colGioiTinh;
    public TableColumn<NhanVienDto, String> colSDT;
    public TableColumn<NhanVienDto, String> colNgaySinh;
    public TableColumn<NhanVienDto, String> colEmail;
    public TableColumn<NhanVienDto, String> colDiaChi;
    public TableColumn<NhanVienDto, String> colTrangThai;
    public TableColumn<NhanVienDto, String> colCapNhat;
    public Button btnLamMoi;
    public Button btnTim;
    public TextField txtTim;

    private final NhanVienService nhanVienService = new NhanVienService();

    @Override
    public void start(Stage stage) {
        new DanhMucNhanVien_GUI().showWithController(stage, this);
    }

    public void initialize() {
        txtTim.setOnAction(event -> TimKiem());
        btnTim.setOnAction(event -> TimKiem());
        btnLamMoi.setOnAction(event -> LamMoi());
        configureColumns();
        loadData();
    }

    public void loadData() {
        ObservableList<NhanVienDto> data = FXCollections.observableArrayList(nhanVienService.findAll());
        tblNhanVien.setItems(data);
        tblNhanVien.refresh();
    }

    public void btnCapNhat(NhanVienDto nhanVien) {
        try {
            NhanVienDto copy = new NhanVienDto(nhanVien);
            var gui = new com.example.pharmacy.client.view.CN_DanhMuc.DMNhanVien.SuaXoaNhanVien_GUI();
            var ctrl = new com.example.pharmacy.client.controller.CN_DanhMuc.DMNhanVien.SuaXoaNhanVien_Ctrl();

            Stage dialog = new Stage();
        TuyChinhAlert.setAppIcon(dialog);
            dialog.initOwner(txtTim.getScene().getWindow());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setTitle("Cap nhat nhan vien");

            gui.showWithController(dialog, ctrl, copy);
            ctrl.setParent(this);
            ctrl.load(copy);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void btnThemNhanVien(ActionEvent actionEvent) {
        try {
            var gui = new ThemNhanVien_GUI();
            var ctrl = new ThemNhanVien_Ctrl();

            Stage dialog = new Stage();
        TuyChinhAlert.setAppIcon(dialog);
            dialog.initOwner(txtTim.getScene().getWindow());
            dialog.setTitle("Them nhan vien");
            dialog.getIcons().add(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/com/example/pharmacy/client/img/logoNguyenBan.png")
            )));

            ctrl.setParent(this);
            gui.showWithController(dialog, ctrl);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void LamMoi() {
        txtTim.clear();
        loadData();
    }

    private void TimKiem() {
        String keyword = txtTim.getText();
        List<NhanVienDto> filtered = nhanVienService.searchByKeyword(keyword);
        tblNhanVien.setItems(FXCollections.observableArrayList(filtered));
    }

    private void configureColumns() {
        colSTT.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : Integer.toString(getIndex() + 1));
                setGraphic(null);
            }
        });

        colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        colTenNV.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
        colGioiTinh.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isGioiTinh() ? "Nu" : "Nam"));
        colSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        colNgaySinh.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNgaySinh() == null ? "" : cellData.getValue().getNgaySinh().toString()));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        colTrangThai.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isTrangThai() ? "Đang làm việc" : "Đã nghỉ việc"));
        colCapNhat.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Cap nhat");

            {
                btn.setOnAction(event -> {
                    NhanVienDto nhanVien = getTableView().getItems().get(getIndex());
                    btnCapNhat(nhanVien);
                });
                btn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }
}

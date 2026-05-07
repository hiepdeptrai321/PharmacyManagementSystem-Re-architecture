package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import com.example.pharmacymanagementsystem_qlht.service.NhaCungCapService;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC.DanhMucNhaCungCap_GUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class DanhMucNhaCungCap_Ctrl extends Application {

    public TableColumn colChiTietNhaCungCap;
    public TableColumn<NhaCungCap, String> colChiTiet;
    public TableView<NhaCungCap> tblNhaCungCap;
    public TableColumn<NhaCungCap, String> colMaNCC;
    public TableColumn<NhaCungCap, String> colTenNCC;
    public TableColumn<NhaCungCap, String> colDiaChi;
    public TableColumn<NhaCungCap, String> colSDT;
    public TableColumn<NhaCungCap, String> colEmail;
    public TableColumn<NhaCungCap, String> colGhiChu;
    private TableColumn<NhaCungCap, String> colTenCongTy;
    public TableColumn<NhaCungCap, String> colSTT;
    public TextField txtTimKiem;
    public Button btnLamMoi;
    public Button btnThemNCC;
    public Button btnTim;

    private final NhaCungCapService nhaCungCapService = new NhaCungCapService();

    @FXML
    public void initialize() {
        loadNhaCungCap();
        btnLamMoi.setOnAction(e -> LamMoi());
        btnTim.setOnAction(e -> TimKiem());
        txtTimKiem.setOnAction(e -> TimKiem());
        btnThemNCC.setOnAction(e -> btnThemNCC());
    }

    public void loadNhaCungCap() {
        List<NhaCungCap> list = nhaCungCapService.findAll();

        colSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tblNhaCungCap.getItems().indexOf(cellData.getValue()) + 1))
        );
        colMaNCC.setCellValueFactory(new PropertyValueFactory<>("maNCC"));
        colTenNCC.setCellValueFactory(new PropertyValueFactory<>("tenNCC"));
        colTenNCC.setCellFactory(col -> new TableCell<>() {
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
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        colSDT.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGhiChu.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
        colChiTiet.setCellFactory(cel -> new TableCell<>() {
            private final Button btn = new Button("Chi tiáº¿t");
            {
                btn.setOnAction(event -> {
                    NhaCungCap ncc = getTableView().getItems().get(getIndex());
                    suaXoaNhaCungCap(ncc);
                });
                btn.setStyle("-fx-text-fill: white;-fx-background-color: #218cff;");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        tblNhaCungCap.getItems().setAll(list);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new DanhMucNhaCungCap_GUI()
                .showWithController(stage, this);
    }

    private void suaXoaNhaCungCap(NhaCungCap ncc) {
        try {
            var gui = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC.SuaXoaNhaCungCap_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap.SuaXoaNhaCungCap_Ctrl();

            ctrl.setDanhMucNhaCungCap_ctrl(this);
            ctrl.loadData(ncc);

            Stage dialog = new Stage();
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiáº¿t nhÃ  cung cáº¥p");

            gui.showWithController(dialog, ctrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnThemNCC() {
        try {
            var gui = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNCC.ThemNhaCungCap_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap.ThemNhaCungCap_Ctrl();

            ctrl.setParentCtrl(this);

            Stage dialog = new Stage();
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("ThÃªm nhÃ  cung cáº¥p");

            gui.showWithController(dialog, ctrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void TimKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<NhaCungCap> filtered = nhaCungCapService.searchByKeyword(keyword);
        tblNhaCungCap.getItems().setAll(filtered);
    }

    public void refreshTable() {
        loadNhaCungCap();
    }

    private void LamMoi() {
        txtTimKiem.clear();
        loadNhaCungCap();
    }
}

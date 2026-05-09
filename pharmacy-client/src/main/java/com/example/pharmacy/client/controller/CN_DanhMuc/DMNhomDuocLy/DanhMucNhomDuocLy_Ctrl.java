package com.example.pharmacy.client.controller.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.common.model.NhomDuocLyDto;
import com.example.pharmacy.client.service.NhomDuocLyService;
import com.example.pharmacy.client.view.CN_DanhMuc.DMNhomDuocLy.ThemNhomDuocLy_GUI;
import com.example.pharmacy.client.view.CN_DanhMuc.DMNhomDuocLy.XoaSuaNhomDuocLy_GUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class DanhMucNhomDuocLy_Ctrl extends Application {
    public Button btnThem;
    public Button btnLamMoi;
    public Button btnTim;
    public Button btnXoa;
    public TableColumn<NhomDuocLyDto, String> cotMaNDL;
    public TableColumn<NhomDuocLyDto, String> cotSTT;
    public TableColumn<NhomDuocLyDto, String> cotTenNDL;
    public TableColumn<NhomDuocLyDto, String> colChiTiet;
    public TableView<NhomDuocLyDto> tbNhomDuocLy;
    public TextField txtTimKiem;

    private final NhomDuocLyService nhomDuocLyService = new NhomDuocLyService();

    @Override
    public void start(Stage stage) throws Exception {
        new com.example.pharmacy.client.view.CN_DanhMuc.DMNhomDuocLy.DanhMucNhomDuocLy_GUI()
                .showWithController(stage, this);
    }

    public void initialize() {
        loadTable();
        btnTim.setOnAction(e -> timKiem());
        btnLamMoi.setOnAction(e -> lamMoi());
        btnThem.setOnAction(e -> btnThemClick());
        txtTimKiem.setOnAction(e -> timKiem());
    }

    public void loadTable() {
        List<NhomDuocLyDto> list = nhomDuocLyService.findAll();
        ObservableList<NhomDuocLyDto> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbNhomDuocLy.getItems().indexOf(cellData.getValue()) + 1)));
        cotMaNDL.setCellValueFactory(new PropertyValueFactory<>("maNDL"));
        cotTenNDL.setCellValueFactory(new PropertyValueFactory<>("tenNDL"));
        cotTenNDL.setCellFactory(col -> new TableCell<>() {
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
        colChiTiet.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Chi tiet");
            {
                btn.setOnAction(event -> {
                    NhomDuocLyDto ndl = getTableView().getItems().get(getIndex());
                    btnChiTietClick(ndl);
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
        tbNhomDuocLy.setItems(data);
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<NhomDuocLyDto> filtered = nhomDuocLyService.searchByKeyword(keyword);
        tbNhomDuocLy.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void lamMoi() {
        txtTimKiem.clear();
        loadTable();
    }

    public void btnThemClick() {
        try {
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.initOwner(btnThem.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Them nhom duoc ly");

            ThemNhomDuocLy_Ctrl ctrl = new ThemNhomDuocLy_Ctrl();
            new ThemNhomDuocLy_GUI().showWithController(stage, ctrl);

            stage.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnChiTietClick(NhomDuocLyDto ndl) {
        try {
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.initOwner(tbNhomDuocLy.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Chi tiet nhom duoc ly");

            SuaXoaNhomDuocLy ctrl = new SuaXoaNhomDuocLy();
            ctrl.hienThiThongTin(ndl);
            new XoaSuaNhomDuocLy_GUI().showWithController(stage, ctrl);

            stage.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

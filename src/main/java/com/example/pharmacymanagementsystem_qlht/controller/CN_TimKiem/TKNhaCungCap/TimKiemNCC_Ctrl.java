package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import com.example.pharmacymanagementsystem_qlht.service.NhaCungCapService;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap.ChiTietNhaCungCap_GUI;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.stage.Stage;

import java.util.List;

public class TimKiemNCC_Ctrl extends Application {
    public ComboBox<String> cboTimKiem;
    public TextField txtTimKiem;
    public Button btnTim;
    public Button btnLamMoi;
    public TableColumn<NhaCungCap, String> cotDiaChi;
    public TableColumn<NhaCungCap, String> cotEmil;
    public TableColumn<NhaCungCap, String> cotMNCC;
    public TableColumn<NhaCungCap, String> cotSDT;
    public TableColumn<NhaCungCap, String> cotSTT;
    public TableColumn<NhaCungCap, String> cotTenNCC;
    public TableColumn<NhaCungCap, String> cotChiTiet;
    public TableView<NhaCungCap> tbNCC;

    private final NhaCungCapService nhaCungCapService = new NhaCungCapService();

    @FXML
    public void initialize() {
        cboTimKiem.getItems().addAll(
                "Theo mÃ£, tÃªn nhÃ  cung cáº¥p",
                "Theo email",
                "Theo SDT"
        );
        cboTimKiem.setValue("Theo mÃ£, tÃªn nhÃ  cung cáº¥p");

        btnTim.setOnAction(e -> TimKiem());
        btnLamMoi.setOnAction(e -> LamMoi());

        Platform.runLater(this::loadTable);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKNhaCungCap.TimKiemNCC_GUI()
                .showWithController(stage, this);
    }

    public void loadTable() {
        List<NhaCungCap> list = nhaCungCapService.findAll();
        ObservableList<NhaCungCap> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbNCC.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMNCC.setCellValueFactory(new PropertyValueFactory<>("maNCC"));
        cotTenNCC.setCellValueFactory(new PropertyValueFactory<>("tenNCC"));
        cotTenNCC.setCellFactory(col -> new TableCell<>() {
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
        cotSDT.setCellValueFactory(new PropertyValueFactory<>("SDT"));
        cotEmil.setCellValueFactory(new PropertyValueFactory<>("email"));
        cotEmil.setCellFactory(col -> new TableCell<>() {
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

        cotChiTiet.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Chi tiáº¿t");
            {
                btn.setOnAction(event -> {
                    NhaCungCap ncc = getTableView().getItems().get(getIndex());
                    btnChiTietClick(ncc);
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

        tbNCC.setItems(data);
    }

    public void btnChiTietClick(NhaCungCap ncc) {
        try {
            Stage dialog = new Stage();
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiáº¿t nhÃ  cung cáº¥p");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));

            ChiTietNhaCungCap_Ctrl ctrl = new ChiTietNhaCungCap_Ctrl();
            ctrl.hienThiThongTin(ncc);
            new ChiTietNhaCungCap_GUI().showWithController(dialog, ctrl);

            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void TimKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<NhaCungCap> filtered = nhaCungCapService.searchByKeyword(keyword);
        tbNCC.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void LamMoi() {
        txtTimKiem.clear();
        cboTimKiem.setValue("Theo mÃ£, tÃªn nhÃ  cung cáº¥p");
        loadTable();
    }
}

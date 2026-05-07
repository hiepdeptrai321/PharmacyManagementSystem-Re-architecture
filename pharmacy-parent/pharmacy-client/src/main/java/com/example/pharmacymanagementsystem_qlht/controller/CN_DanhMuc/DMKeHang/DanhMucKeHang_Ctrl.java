package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.model.KeHang;
import com.example.pharmacymanagementsystem_qlht.service.KeHangService;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.ThemKe_GUI;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.XoaSuaKeHang_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class DanhMucKeHang_Ctrl extends Application {
    public Button btnThem;
    public Button btnLamMoi;
    public Button btnTim;
    public TableColumn<KeHang, String> cotMaKe;
    public TableColumn<KeHang, String> cotSTT;
    public TableColumn<KeHang, String> cotTenKe;
    public TableColumn<KeHang, String> colChiTiet;
    public TableView<KeHang> tblKeHang;
    public TextField txtTimKiem;

    private final KeHangService keHangService = new KeHangService();

    @Override
    public void start(Stage stage) throws Exception {
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.DanhMucKeHang_GUI()
                .showWithController(stage, this);
    }

    public void initialize() {
        btnTim.setOnAction(e -> TimKiem());
        btnLamMoi.setOnAction(e -> LamMoi());
        btnThem.setOnAction(e -> btnThemClick(new KeHang()));
        txtTimKiem.setOnAction(e -> TimKiem());
        Platform.runLater(this::loadTable);
    }

    public void loadTable() {
        List<KeHang> list = keHangService.findAll();
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tblKeHang.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMaKe.setCellValueFactory(new PropertyValueFactory<>("maKe"));
        cotTenKe.setCellValueFactory(new PropertyValueFactory<>("tenKe"));
        cotTenKe.setCellFactory(col -> new TableCell<>() {
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
            private final Button btn = new Button("Chi tiáº¿t");
            {
                btn.setOnAction(event -> {
                    KeHang kh = getTableView().getItems().get(getIndex());
                    btnChiTietClick(kh);
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
        tblKeHang.getItems().setAll(list);
    }

    private void TimKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<KeHang> filtered = keHangService.searchByKeyword(keyword);
        tblKeHang.getItems().setAll(filtered);
    }

    private void LamMoi() {
        txtTimKiem.clear();
        loadTable();
    }

    public void btnThemClick(KeHang ke) {
        try {
            Stage dialog = new Stage();
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("ThÃªm ká»‡ hÃ ng");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));

            ThemKe_Ctrl themCtrl = new ThemKe_Ctrl();
            new ThemKe_GUI().showWithController(dialog, themCtrl);

            dialog.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnChiTietClick(KeHang kh) {
        try {
            Stage dialog = new Stage();
            dialog.initOwner(btnLamMoi.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiáº¿t ká»‡ hÃ ng");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));

            XoaSuaKeHang_Ctrl ctrl = new XoaSuaKeHang_Ctrl();
            ctrl.hienThiThongTin(kh);
            new XoaSuaKeHang_GUI().showWithController(dialog, ctrl);

            dialog.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

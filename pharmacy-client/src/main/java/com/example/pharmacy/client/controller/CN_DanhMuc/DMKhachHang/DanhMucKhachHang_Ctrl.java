package com.example.pharmacy.client.controller.CN_DanhMuc.DMKhachHang;

import com.example.pharmacy.common.model.KhachHangDto;
import com.example.pharmacy.client.service.KhachHangService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML; // (Có thể giữ lại)
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

// Import các file GUI và Ctrl cho cửa sổ con
import com.example.pharmacy.client.view.CN_DanhMuc.DMKhachHang.ThemKhachHang_GUI;
import com.example.pharmacy.client.view.CN_DanhMuc.DMKhachHang.SuaXoaKhachHang_GUI;

public class DanhMucKhachHang_Ctrl extends Application {
    // --- ĐÃ CHUYỂN SANG PUBLIC ---
    public Button btnTim;
    public Button btnLamMoi;
    public Button btnthemKH;
    public TableColumn<KhachHangDto, String> cotChiTiet;
    public TableColumn<KhachHangDto, String> cotGioiTinh;
    public TableColumn<KhachHangDto, String> cotMaKH;
    public TableColumn<KhachHangDto, String> cotDiaChi;
    public TableColumn<KhachHangDto, String> cotSDT;
    public TableColumn<KhachHangDto, String> cotSTT;
    public TableColumn<KhachHangDto, String> cotTenKH;
    public TableView<KhachHangDto> tbKhachHang;
    public TextField txtTim;

    private final KhachHangService khachHangService = new KhachHangService();

    @Override
    public void start(Stage stage) throws Exception {
        // --- ĐÃ THAY THẾ FXML LOADER ---
        new com.example.pharmacy.client.view.CN_DanhMuc.DMKhachHang.DanhMucKhachHang_GUI()
                .showWithController(stage, this);
    }

    // Gán sự kiện (được gọi bởi GUI)
    public void initialize() {
        btnLamMoi.setOnAction(e -> LamMoi());
        btnTim.setOnAction(e -> TimKiem());
        btnthemKH.setOnAction(e -> btnThemClick(new KhachHangDto()));
        txtTim.setOnAction(e -> TimKiem());

        Platform.runLater(()->{
            loadTable();
        });
    }

    // --- LOGIC GIỮ NGUYÊN ---
    public void loadTable() {
        List<KhachHangDto> list = khachHangService.findAll();
        ObservableList<KhachHangDto> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbKhachHang.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMaKH.setCellValueFactory(new PropertyValueFactory<>("MaKH"));
        cotTenKH.setCellValueFactory(new PropertyValueFactory<>("TenKH"));
        cotTenKH.setCellFactory(col -> new TableCell<KhachHangDto, String>() {
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
        cotGioiTinh.setCellValueFactory(cellData -> {
            Boolean gt = cellData.getValue().getGioiTinh();
            String gioiTinhText = (gt != null && gt) ? "Nam" : "Nữ";
            return new SimpleStringProperty(gioiTinhText);
        });
        cotGioiTinh.setCellFactory(col -> new TableCell<KhachHangDto, String>() {
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

        cotDiaChi.setCellValueFactory(new PropertyValueFactory<>("DiaChi"));
        cotDiaChi.setCellFactory(col -> new TableCell<KhachHangDto, String>() {
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
        cotSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));

        cotChiTiet.setCellFactory(col -> new TableCell<KhachHangDto, String>() {
            private final Button btn = new Button("Chi tiết");
            {
                btn.setOnAction(event -> {
                    KhachHangDto kh = getTableView().getItems().get(getIndex());
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
        tbKhachHang.setItems(data);
    }

    // --- ĐÃ CẬP NHẬT: Gọi GUI thuần ---
    public void btnChiTietClick(KhachHangDto kh) {
        try {
            Stage dialog = new Stage();
            dialog.initOwner(txtTim.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiết khách hàng");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacy/client/img/logoNguyenBan.png")));

            // Code GUI thuần mới:
            ChiTietKhachHang_Ctrl ctrl = new ChiTietKhachHang_Ctrl();
            ctrl.hienThiThongTin(kh); // GỌI TRƯỚC
            new SuaXoaKhachHang_GUI().showWithController(dialog, ctrl);

            dialog.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- ĐÃ CẬP NHẬT: Gọi GUI thuần ---
    public void btnThemClick(KhachHangDto kh) {
        try {
            Stage dialog = new Stage();
            dialog.initOwner(tbKhachHang.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Thêm khách hàng");
            dialog.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/pharmacy/client/img/logoNguyenBan.png")));

            // Code GUI thuần mới:
            ThemKhachHang_Ctrl ctrl = new ThemKhachHang_Ctrl();
            new ThemKhachHang_GUI().showWithController(dialog, ctrl);

            dialog.showAndWait();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void LamMoi() {
        txtTim.clear();
        loadTable();
    }

    private void TimKiem() {
        String keyword = txtTim.getText().trim().toLowerCase();
        List<KhachHangDto> filtered = khachHangService.searchByKeyword(keyword);
        tbKhachHang.setItems(FXCollections.observableArrayList(filtered));
    }
}

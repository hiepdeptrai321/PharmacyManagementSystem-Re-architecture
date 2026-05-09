package com.example.pharmacy.client.controller.CN_DanhMuc.DMKhuyenMai;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.common.model.KhuyenMaiDto;
import com.example.pharmacy.client.service.KhuyenMaiService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class DanhMucKhuyenMai_Ctrl extends Application {

    @FXML public TableView<KhuyenMaiDto> tbKM;
    public TextField tfTimKM;
    @FXML public Button btnthemKM;
    @FXML public TableColumn<KhuyenMaiDto, String> colChiTiet;
    @FXML public TableColumn<KhuyenMaiDto, String> colSTT;
    @FXML public TableColumn<KhuyenMaiDto, String> colMaKM;
    @FXML public TableColumn<KhuyenMaiDto, String> colTenKM;
    @FXML public TableColumn<KhuyenMaiDto, String> colLoaiKM;
    @FXML public TableColumn<KhuyenMaiDto, Float> colGiaTri;
    @FXML public TableColumn<KhuyenMaiDto, String> colNBD;
    @FXML public TableColumn<KhuyenMaiDto, String> colNKT;
    @FXML public TableColumn<KhuyenMaiDto, String> colNgayTao;
    @FXML public Button btnLamMoi;
    public Button btnTim;

    private final KhuyenMaiService khuyenMaiService = new KhuyenMaiService();

    public void initialize() {
        tfTimKM.setOnAction(e -> timKhuyenMai());
        btnLamMoi.setOnAction(e -> lamMoi());
        btnthemKM.setOnAction(e -> btnThemKMClick());
        btnTim.setOnAction(e -> timKhuyenMai());
        Platform.runLater(this::loadTable);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new com.example.pharmacy.client.view.CN_DanhMuc.DMKhuyenMai.DanhMucKhuyenMai_GUI()
                .showWithController(stage, this);
    }

    public void loadTable() {
        List<KhuyenMaiDto> list = khuyenMaiService.findAll();
        ObservableList<KhuyenMaiDto> data = FXCollections.observableArrayList(list);

        colSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbKM.getItems().indexOf(cellData.getValue()) + 1))
        );
        colMaKM.setCellValueFactory(new PropertyValueFactory<>("maKM"));
        colTenKM.setCellValueFactory(new PropertyValueFactory<>("tenKM"));
        colTenKM.setCellFactory(col -> new TableCell<>() {
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
        colLoaiKM.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getLoaiKM() == null ? "" : cellData.getValue().getLoaiKM().getMaLoai()
        ));
        colNBD.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getNgayBatDau() == null
                        ? ""
                        : com.example.pharmacy.client.TienIch.DoiNgay.dinhDangNgay(cellData.getValue().getNgayBatDau())
        ));
        colNKT.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getNgayKetThuc() == null
                        ? ""
                        : com.example.pharmacy.client.TienIch.DoiNgay.dinhDangNgay(cellData.getValue().getNgayKetThuc())
        ));
        colNgayTao.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getNgayTao() == null
                        ? ""
                        : com.example.pharmacy.client.TienIch.DoiNgay.dinhDangNgay(cellData.getValue().getNgayTao())
        ));
        colChiTiet.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Xoa");
            {
                btn.setOnAction(event -> {
                    KhuyenMaiDto km = getTableView().getItems().get(getIndex());
                    btnXoaClick(km);
                });
                btn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                btn.getStyleClass().add("btn");
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tbKM.setItems(data);
    }

    public void btnXoaClick(KhuyenMaiDto km) {
        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Xac nhan xoa khuyen mai " + km.getMaKM() + "?",
                ButtonType.YES,
                ButtonType.NO
        );
        confirm.setHeaderText("Xac nhan xoa");
        confirm.showAndWait().ifPresent(res -> {
            if (res != ButtonType.YES) {
                return;
            }

            boolean deleted = khuyenMaiService.deleteByMaKM(km.getMaKM());
            if (deleted) {
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Da xoa khuyen mai.", ButtonType.OK).showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Khong the xoa khuyen mai tren server.", ButtonType.OK).showAndWait();
            }
        });
    }

    public void btnThemKMClick() {
        try {
            ThemKhuyenMai_Ctrl ctrl = new ThemKhuyenMai_Ctrl();
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.setTitle("Them khuyen mai");
            stage.initOwner(btnthemKM.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setOnHidden(e -> loadTable());
            new com.example.pharmacy.client.view.CN_DanhMuc.DMKhuyenMai.ThemKhuyenMai_GUI()
                    .showWithController(stage, ctrl);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void lamMoi() {
        tfTimKM.clear();
        loadTable();
    }

    public void timKhuyenMai() {
        String keyword = tfTimKM.getText().trim().toLowerCase();
        List<KhuyenMaiDto> filtered = khuyenMaiService.searchByKeyword(keyword);
        tbKM.setItems(FXCollections.observableArrayList(filtered));
    }
}

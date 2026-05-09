package com.example.pharmacy.client.controller.CN_CapNhat.CapNhatKhuyenMai;

import com.example.pharmacy.client.TienIch.TuyChinhAlert;
import com.example.pharmacy.client.TienIch.LoadingOverlay;
import com.example.pharmacy.common.model.KhuyenMai;
import com.example.pharmacy.client.service.KhuyenMaiService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class CapNhatKhuyenMai_Ctrl {

    public TableView<KhuyenMai> tbKM;
    public TextField tfTimKM;
    public Button btnTimKM;
    public TableColumn<KhuyenMai, String> colChiTiet;
    public TableColumn<KhuyenMai, String> colSTT;
    public TableColumn<KhuyenMai, String> colMaKM;
    public TableColumn<KhuyenMai, String> colTenKM;
    public TableColumn<KhuyenMai, String> colLoaiKM;
    public TableColumn<KhuyenMai, Float> colGiaTri;
    public TableColumn<KhuyenMai, String> colNBD;
    public TableColumn<KhuyenMai, String> colNKT;
    public Button btnReset;
    public StackPane rootTablePane;

    private final KhuyenMaiService khuyenMaiService = new KhuyenMaiService();
    private LoadingOverlay loadingOverlay;

    public void initialize() {
        tfTimKM.setOnAction(e -> timKhuyenMai());
        btnReset.setOnAction(e -> lamMoi());
        btnTimKM.setOnAction(e -> timKhuyenMai());
        tbKM.setPlaceholder(new Label(""));

        loadingOverlay = new LoadingOverlay();
        rootTablePane.getChildren().add(loadingOverlay);
        Platform.runLater(this::loadTable);
    }

    public void loadTable() {
        runWithLoading(() -> {
            List<KhuyenMai> list = khuyenMaiService.findAll();
            ObservableList<KhuyenMai> data = FXCollections.observableArrayList(list);
            Platform.runLater(() -> {
                colSTT.setCellValueFactory(cellData ->
                        new SimpleStringProperty(String.valueOf(tbKM.getItems().indexOf(cellData.getValue()) + 1)));
                colMaKM.setCellValueFactory(new PropertyValueFactory<>("maKM"));
                colTenKM.setCellValueFactory(new PropertyValueFactory<>("tenKM"));
                colLoaiKM.setCellValueFactory(cellData -> new SimpleStringProperty(
                        cellData.getValue().getLoaiKM() == null ? "" : cellData.getValue().getLoaiKM().getMaLoai()
                ));
                colGiaTri.setCellValueFactory(new PropertyValueFactory<>("giaTriKM"));
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
                colChiTiet.setCellFactory(col -> new TableCell<>() {
                    private final Button btn = new Button("Chi tiet");
                    {
                        btn.setOnAction(event -> btnChiTietClick(getTableView().getItems().get(getIndex())));
                        btn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        btn.getStyleClass().add("btn");
                    }
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : btn);
                    }
                });
                tbKM.setItems(data);
            });
        });
    }

    public void btnChiTietClick(KhuyenMai km) {
        try {
            SuaKhuyenMai_Ctrl ctrl = new SuaKhuyenMai_Ctrl();
            Stage stage = new Stage();
        TuyChinhAlert.setAppIcon(stage);
            stage.setTitle("Sua khuyen mai");
            stage.initOwner(tbKM.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);

            new com.example.pharmacy.client.view.CN_CapNhat.CapNhatKhuyenMai.SuaKhuyenMai_GUI()
                    .showWithController(stage, ctrl);
            ctrl.loadData(km);
            ctrl.loadDatatbCTKM(khuyenMaiService.findChiTietByMaKM(km.getMaKM()));
            if (km.getLoaiKM() != null && "LKM001".equalsIgnoreCase(km.getLoaiKM().getMaLoai())) {
                ctrl.loadDatatbQuaTang(khuyenMaiService.findQuaTangByMaKM(km.getMaKM()));
            }
            stage.setResizable(true);
            stage.show();
            stage.setOnHidden(e -> loadTable());
            Platform.runLater(stage::sizeToScene);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void timKhuyenMai() {
        runWithLoading(() -> {
            String keyword = tfTimKM.getText().trim().toLowerCase();
            List<KhuyenMai> filtered = khuyenMaiService.searchByKeyword(keyword);
            Platform.runLater(() -> tbKM.setItems(FXCollections.observableArrayList(filtered)));
        });
    }

    private void runWithLoading(Runnable backgroundJob) {
        loadingOverlay.show();

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                backgroundJob.run();
                return null;
            }
        };

        task.setOnSucceeded(e -> loadingOverlay.hide());
        task.setOnFailed(e -> loadingOverlay.hide());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void lamMoi() {
        tfTimKM.clear();
        loadTable();
    }
}

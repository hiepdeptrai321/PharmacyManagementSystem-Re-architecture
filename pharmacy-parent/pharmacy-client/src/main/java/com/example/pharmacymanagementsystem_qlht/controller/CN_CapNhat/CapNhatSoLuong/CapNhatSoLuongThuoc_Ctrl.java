package com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatSoLuong;

import com.example.pharmacymanagementsystem_qlht.TienIch.LoadingOverlay;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.service.TonKhoService;
import com.example.pharmacymanagementsystem_qlht.service.ThuocService;
import javafx.application.Application;
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
import javafx.stage.Stage;

import java.util.List;

public class CapNhatSoLuongThuoc_Ctrl extends Application {
    public TextField tfTimThuoc;
    public Button btnTimThuoc;
    public TableView<Thuoc_SP_TheoLo> tbThuoc;
    public TableColumn<Thuoc_SP_TheoLo, String> colSTT;
    public TableColumn<Thuoc_SP_TheoLo, String> colMaThuoc;
    public TableColumn<Thuoc_SP_TheoLo, String> colTenThuoc;
    public TableColumn<Thuoc_SP_TheoLo, String> colDVT;
    public TableColumn<Thuoc_SP_TheoLo, Integer> colSLTon;
    public TableColumn<Thuoc_SP_TheoLo, String> colMaLo;
    public TableColumn<Thuoc_SP_TheoLo, String> colChiTiet;
    public Button btnLamMoi;
    public StackPane rootTablePane;

    private final TonKhoService tonKhoService = new TonKhoService();
    private final ThuocService thuocService = new ThuocService();
    private LoadingOverlay loadingOverlay;

    @Override
    public void start(Stage stage) throws Exception {
        new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatSoLuong.CapNhatSoLuongThuoc_GUI()
                .showWithController(stage, this);
    }

    public void initialize() {
        tfTimThuoc.setOnAction(e -> timThuoc());
        btnTimThuoc.setOnAction(e -> timThuoc());
        btnLamMoi.setOnAction(e -> lamMoi());

        loadingOverlay = new LoadingOverlay();
        rootTablePane.getChildren().add(loadingOverlay);
        tbThuoc.setPlaceholder(new Label(""));

        Platform.runLater(this::loadTable);
    }

    public void loadTable() {
        runWithLoading(() -> {
            List<Thuoc_SP_TheoLo> list = tonKhoService.findAllLots();
            ObservableList<Thuoc_SP_TheoLo> data = FXCollections.observableArrayList(list);

            Platform.runLater(() -> {
                colSTT.setCellValueFactory(cellData ->
                        new SimpleStringProperty(String.valueOf(tbThuoc.getItems().indexOf(cellData.getValue()) + 1))
                );
                colMaThuoc.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getThuoc().getMaThuoc())
                );
                colTenThuoc.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getThuoc().getTenThuoc())
                );
                colDVT.setCellValueFactory(cellData ->
                        new SimpleStringProperty(getTenDvt(cellData.getValue()))
                );
                colSLTon.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));
                colMaLo.setCellValueFactory(new PropertyValueFactory<>("maLH"));
                colChiTiet.setCellFactory(col -> new TableCell<>() {
                    private final Button btn = new Button("Sua");

                    {
                        btn.setOnAction(event -> {
                            Thuoc_SP_TheoLo thuocLo = getTableView().getItems().get(getIndex());
                            showSuaSoLuongThuoc(thuocLo);
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

                tbThuoc.setItems(data);
            });
        });
    }

    public void timThuoc() {
        String keyword = tfTimThuoc.getText() == null ? "" : tfTimThuoc.getText().trim().toLowerCase();
        runWithLoading(() -> {
            List<Thuoc_SP_TheoLo> list = tonKhoService.findAllLots().stream()
                    .filter(thuocTheoLo -> keyword.isEmpty() || matchesKeyword(thuocTheoLo, keyword))
                    .toList();
            ObservableList<Thuoc_SP_TheoLo> data = FXCollections.observableArrayList(list);
            Platform.runLater(() -> tbThuoc.setItems(data));
        });
    }

    private boolean matchesKeyword(Thuoc_SP_TheoLo thuocTheoLo, String keyword) {
        return containsIgnoreCase(thuocTheoLo.getMaLH(), keyword)
                || containsIgnoreCase(thuocTheoLo.getThuoc().getMaThuoc(), keyword)
                || containsIgnoreCase(thuocTheoLo.getThuoc().getTenThuoc(), keyword);
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    private String getTenDvt(Thuoc_SP_TheoLo thuocTheoLo) {
        if (thuocTheoLo == null || thuocTheoLo.getThuoc() == null) {
            return "";
        }
        String tenDvt = thuocTheoLo.getThuoc().getTenDVTCoBan();
        if (tenDvt == null || tenDvt.isBlank()) {
            tenDvt = thuocService.getTenDVTByMaThuoc(thuocTheoLo.getThuoc().getMaThuoc());
        }
        return tenDvt == null ? "" : tenDvt;
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

    private void showSuaSoLuongThuoc(Thuoc_SP_TheoLo thuocLo) {
        try {
            SuaSoLuongThuoc_Ctrl controller = new SuaSoLuongThuoc_Ctrl();
            Stage stage = new Stage();
            stage.setTitle("Sua so luong thuoc");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.setOnHidden(e -> loadTable());

            new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatSoLuong.SuaSoLuongThuoc_GUI()
                    .showWithController(stage, controller);
            controller.setThuoc(thuocLo);
            tbThuoc.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void lamMoi() {
        tfTimThuoc.clear();
        loadTable();
    }
}

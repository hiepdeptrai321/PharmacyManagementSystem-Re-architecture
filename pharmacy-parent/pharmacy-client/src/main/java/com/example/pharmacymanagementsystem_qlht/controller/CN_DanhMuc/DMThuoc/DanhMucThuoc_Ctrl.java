package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc;

import com.example.pharmacymanagementsystem_qlht.TienIch.LoadingOverlay;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import com.example.pharmacymanagementsystem_qlht.service.ThuocService;
import com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMThuoc.DanhMucThuoc_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class DanhMucThuoc_Ctrl extends Application {

    public TableColumn<Thuoc_SanPham, String> colSTT;
    public TableColumn<Thuoc_SanPham, String> colMaThuoc;
    public TableColumn<Thuoc_SanPham, String> colTenThuoc;
    public TableColumn<Thuoc_SanPham, String> colChiTiet;
    public TableColumn<Thuoc_SanPham, String> colHamLuong;
    public TableColumn<Thuoc_SanPham, String> colSDK_GPNK;
    public TableColumn<Thuoc_SanPham, String> colXuatXu;
    public TableColumn<Thuoc_SanPham, String> colLoaiHang;
    public TableColumn<Thuoc_SanPham, String> colViTri;
    public TableView<Thuoc_SanPham> tbl_Thuoc;
    public TextField tfTimThuoc;
    public Button btnTimThuoc;
    public Button btnThemThuoc;
    public Button btnLamMoi;
    public StackPane rootTablePane;

    private final ThuocService thuocService = new ThuocService();
    private LoadingOverlay loadingOverlay;
    private List<Thuoc_SanPham> list;
    private ObservableList<Thuoc_SanPham> data;

    @Override
    public void start(Stage stage) throws Exception {
        new DanhMucThuoc_GUI().showWithController(stage, this);
    }

    public void initialize() {
        btnLamMoi.setOnAction(e -> lamMoi());
        tfTimThuoc.setOnAction(e -> timThuoc());
        btnTimThuoc.setOnAction(e -> timThuoc());
        loadingOverlay = new LoadingOverlay();
        rootTablePane.getChildren().add(loadingOverlay);
        tbl_Thuoc.setPlaceholder(new Label(""));
        Platform.runLater(this::loadTable);
    }

    public void loadTable() {
        runWithLoading(() -> {
            list = thuocService.findAll();
            data = FXCollections.observableArrayList(list);
            Platform.runLater(() -> {
                colSTT.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : Integer.toString(getIndex() + 1));
                        setGraphic(null);
                    }
                });
                colMaThuoc.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getMaThuoc()));
                colTenThuoc.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getTenThuoc()));
                colHamLuong.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getHamLuongDonVi()));
                colSDK_GPNK.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getSDK_GPNK()));
                colXuatXu.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getNuocSX()));
                colLoaiHang.setCellValueFactory(cd -> new SimpleStringProperty(
                        cd.getValue().getLoaiHang() != null ? cd.getValue().getLoaiHang().getTenLoaiHang() : ""));
                colViTri.setCellValueFactory(cd -> new SimpleStringProperty(
                        cd.getValue().getVitri() != null ? cd.getValue().getVitri().getTenKe() : ""));

                colTenThuoc.setCellFactory(col -> alignedCell());
                colHamLuong.setCellFactory(col -> alignedCell());
                colXuatXu.setCellFactory(col -> alignedCell());
                colLoaiHang.setCellFactory(col -> alignedCell());

                colChiTiet.setCellFactory(col -> new TableCell<>() {
                    private final Button btn = new Button("Chi tiết");

                    {
                        btn.setOnAction(event -> {
                            Thuoc_SanPham thuoc = getTableView().getItems().get(getIndex());
                            moCapNhat(thuoc);
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

                tbl_Thuoc.setItems(data);
            });
        });
    }

    private TableCell<Thuoc_SanPham, String> alignedCell() {
        return new TableCell<>() {
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
        };
    }

    public void timThuoc() {
        runWithLoading(() -> {
            if (list == null) {
                list = thuocService.findAll();
            }
            String keyword = tfTimThuoc.getText() == null ? "" : tfTimThuoc.getText().trim().toLowerCase();
            List<Thuoc_SanPham> dsThuocLoc;
            if (keyword.isEmpty()) {
                dsThuocLoc = list;
            } else {
                dsThuocLoc = list.stream()
                        .filter(thuoc -> containsIgnoreCase(thuoc.getTenThuoc(), keyword)
                                || containsIgnoreCase(thuoc.getMaThuoc(), keyword))
                        .toList();
            }
            ObservableList<Thuoc_SanPham> dataLoc = FXCollections.observableArrayList(dsThuocLoc);
            Platform.runLater(() -> tbl_Thuoc.setItems(dataLoc));
        });
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    public void themthuoc() {
        try {
            var gui = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMThuoc.ThemThuoc_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.ThemThuoc_Ctrl();

            Stage dialog = new Stage();
            dialog.initOwner(tbl_Thuoc.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Thêm thuốc");

            gui.showWithController(dialog, ctrl);
            ctrl.setParent(this);
            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moCapNhat(Thuoc_SanPham thuoc) {
        try {
            if (thuoc == null) {
                return;
            }

            var gui = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMThuoc.SuaXoaThuoc_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.SuaXoaThuoc_Ctrl();

            Stage dialog = new Stage();
            dialog.initOwner(tbl_Thuoc.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiết thuốc");

            gui.showWithController(dialog, ctrl);
            ctrl.setParent(this);
            ctrl.load(thuoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refestTable() {
        loadTable();
    }

    @FXML
    private void lamMoi() {
        tfTimThuoc.clear();
        loadTable();
    }

    public void btnThemThuocByExcel() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tạm chưa hỗ trợ");
        alert.setHeaderText(null);
        alert.setContentText("Chức năng thêm thuốc bằng file Excel sẽ được migrate ở đợt sau cùng module import/export.");
        alert.showAndWait();
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
}

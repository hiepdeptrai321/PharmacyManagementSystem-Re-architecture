package com.example.pharmacy.client.controller.CN_TimKiem.TKThuocTrongKho;

import com.example.pharmacy.client.TienIch.LoadingOverlay;
import com.example.pharmacy.common.model.ThuocTonKhoDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.client.service.ThuocService;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class TimKiemThuocTrongKho_Ctrl {
    public TextField tfTimThuoc;
    public Button btnTimThuoc;
    public TableView<Object> tbThuoc;
    public TableColumn<Object, String> colSTT;
    public TableColumn<Object, String> colMaThuoc;
    public TableColumn<Object, String> colTenThuoc;
    public TableColumn<Object, String> colDVT;
    public TableColumn<Object, Integer> colSLTon;
    public TableColumn<Object, String> colMaLo;
    public TableColumn<Object, String> colChiTiet;
    public Button btnLamMoi;
    public ToggleButton hienThiTheoLo;
    public TableColumn<Object, Integer> colSoLoTon;
    public TableColumn<Object, String> colNSX;
    public TableColumn<Object, String> colHSD;
    public StackPane rootTablePane;

    private final ThuocService thuocService = new ThuocService();
    private Timeline colorTimeline;
    private Color current;
    private LoadingOverlay loadingOverlay;

    public void start(Stage stage) throws Exception {
        new com.example.pharmacy.client.view.CN_TimKiem.TKThuocTrongKho.TKThuocTrongKho_GUI()
                .showWithController(stage, this);
    }

    public void initialize() {
        tfTimThuoc.setOnAction(e -> timThuoc());
        btnLamMoi.setOnAction(e -> lamMoi());
        btnTimThuoc.setOnAction(e -> timThuoc());
        hienThiTheoLo.setOnAction(e -> onToggleHienThiTheoLo());
        hienThiTheoLo.setSelected(true);
        loadingOverlay = new LoadingOverlay();
        rootTablePane.getChildren().add(loadingOverlay);
        tbThuoc.setPlaceholder(new Label(""));
        Platform.runLater(this::loadTable);
    }

    public void loadTable() {
        runWithLoading(() -> {
            boolean isTheoLo = hienThiTheoLo != null && hienThiTheoLo.isSelected();
            if (isTheoLo) {
                List<Thuoc_SP_TheoLoDto> list = thuocService.getAllTheoLo();
                ObservableList<Object> data = FXCollections.observableArrayList(list);
                Platform.runLater(() -> {
                    colSTT.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(tbThuoc.getItems().indexOf(cellData.getValue()) + 1)));
                    colMaThuoc.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLoDto lo && lo.getThuoc() != null) {
                            return new SimpleStringProperty(lo.getThuoc().getMaThuoc());
                        }
                        return new SimpleStringProperty("");
                    });
                    colTenThuoc.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLoDto lo && lo.getThuoc() != null) {
                            return new SimpleStringProperty(lo.getThuoc().getTenThuoc());
                        }
                        return new SimpleStringProperty("");
                    });
                    colDVT.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLoDto lo && lo.getThuoc() != null) {
                            return new SimpleStringProperty(thuocService.getTenDVTByMaThuoc(lo.getThuoc().getMaThuoc()));
                        }
                        return new SimpleStringProperty("");
                    });
                    colMaLo.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLoDto lo) {
                            return new SimpleStringProperty(lo.getMaLH());
                        }
                        return new SimpleStringProperty("");
                    });
                    colNSX.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLoDto lo && lo.getNsx() != null) {
                            return new SimpleStringProperty(
                                    com.example.pharmacy.client.TienIch.DoiNgay.dinhDangNgay(lo.getNsx()));
                        }
                        return new SimpleStringProperty("");
                    });
                    colHSD.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLoDto lo && lo.getHsd() != null) {
                            return new SimpleStringProperty(
                                    com.example.pharmacy.client.TienIch.DoiNgay.dinhDangNgay(lo.getHsd()));
                        }
                        return new SimpleStringProperty("");
                    });
                    colSLTon.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof Thuoc_SP_TheoLoDto lo) {
                            return new javafx.beans.property.SimpleIntegerProperty(lo.getSoLuongTon()).asObject();
                        }
                        return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
                    });
                    tbThuoc.setItems(data);
                });
            } else {
                List<ThuocTonKhoDto> list = thuocService.getThuocTonKho();
                ObservableList<Object> data = FXCollections.observableArrayList(list);
                Platform.runLater(() -> {
                    colSTT.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(tbThuoc.getItems().indexOf(cellData.getValue()) + 1)));
                    colMaThuoc.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof ThuocTonKhoDto thuoc) {
                            return new SimpleStringProperty(thuoc.getMaThuoc());
                        }
                        return new SimpleStringProperty("");
                    });
                    colTenThuoc.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof ThuocTonKhoDto thuoc) {
                            return new SimpleStringProperty(thuoc.getTenThuoc());
                        }
                        return new SimpleStringProperty("");
                    });
                    colDVT.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof ThuocTonKhoDto thuoc) {
                            return new SimpleStringProperty(thuoc.getDonViTinh());
                        }
                        return new SimpleStringProperty("");
                    });
                    colSoLoTon.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof ThuocTonKhoDto thuoc) {
                            return new javafx.beans.property.SimpleIntegerProperty(thuoc.getSoLoTon()).asObject();
                        }
                        return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
                    });
                    colSLTon.setCellValueFactory(cellData -> {
                        if (cellData.getValue() instanceof ThuocTonKhoDto thuoc) {
                            return new javafx.beans.property.SimpleIntegerProperty(thuoc.getTongSoLuongTon()).asObject();
                        }
                        return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
                    });
                    tbThuoc.setItems(data);
                });
            }
        });
    }

    public void timThuoc() {
        String keyword = tfTimThuoc.getText() == null ? "" : tfTimThuoc.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadTable();
            return;
        }

        runWithLoading(() -> {
            boolean isTheoLo = hienThiTheoLo != null && hienThiTheoLo.isSelected();
            ObservableList<Object> filteredList = FXCollections.observableArrayList();

            if (isTheoLo) {
                for (Object item : tbThuoc.getItems()) {
                    if (item instanceof Thuoc_SP_TheoLoDto lo && lo.getThuoc() != null) {
                        String maThuoc = safeLower(lo.getThuoc().getMaThuoc());
                        String tenThuoc = safeLower(lo.getThuoc().getTenThuoc());
                        if (maThuoc.contains(keyword) || tenThuoc.contains(keyword)) {
                            filteredList.add(item);
                        }
                    }
                }
            } else {
                for (Object item : tbThuoc.getItems()) {
                    if (item instanceof ThuocTonKhoDto thuoc) {
                        String maThuoc = safeLower(thuoc.getMaThuoc());
                        String tenThuoc = safeLower(thuoc.getTenThuoc());
                        if (maThuoc.contains(keyword) || tenThuoc.contains(keyword)) {
                            filteredList.add(item);
                        }
                    }
                }
            }

            Platform.runLater(() -> tbThuoc.setItems(filteredList));
        });
    }

    private String safeLower(String value) {
        return value == null ? "" : value.toLowerCase();
    }

    private void lamMoi() {
        tfTimThuoc.clear();
        loadTable();
    }

    public void onToggleHienThiTheoLo() {
        if (hienThiTheoLo == null) {
            return;
        }

        boolean selected = hienThiTheoLo.isSelected();
        updateToggleAppearance(selected);

        if (selected) {
            colMaLo.setVisible(true);
            colNSX.setVisible(true);
            colHSD.setVisible(true);
            colSoLoTon.setVisible(false);
            tbThuoc.getColumns().setAll(colSTT, colMaThuoc, colTenThuoc, colDVT, colMaLo, colNSX, colHSD, colSLTon);
        } else {
            colMaLo.setVisible(false);
            colNSX.setVisible(false);
            colHSD.setVisible(false);
            colSoLoTon.setVisible(true);
            tbThuoc.getColumns().setAll(colSTT, colMaThuoc, colTenThuoc, colDVT, colSoLoTon, colSLTon);
        }
        loadTable();
    }

    private void updateToggleAppearance(boolean selected) {
        hienThiTheoLo.setText(selected ? "Theo lô hàng" : "Theo sản phẩm");
        hienThiTheoLo.setTextFill(Color.WHITE);

        Color green = Color.web("#36983b");
        Color orange = Color.web("#ec6a02");
        Color target = selected ? green : orange;

        hienThiTheoLo.setStyle("");
        animateBackground(hienThiTheoLo, current, target);
        current = target;
    }

    private void animateBackground(Control btn, Color from, Color to) {
        if (colorTimeline != null) {
            colorTimeline.stop();
        }

        if (from == null) {
            Color currentColor = null;
            if (btn.getBackground() != null
                    && !btn.getBackground().getFills().isEmpty()
                    && btn.getBackground().getFills().get(0).getFill() instanceof Color color) {
                currentColor = color;
            }
            from = currentColor != null ? currentColor : to;
        }

        ObjectProperty<Color> color = new SimpleObjectProperty<>(from);
        color.addListener((obs, oldColor, newColor) -> btn.setBackground(
                new Background(new BackgroundFill(newColor, new CornerRadii(25), Insets.EMPTY))));

        colorTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(color, from)),
                new KeyFrame(Duration.millis(200), new KeyValue(color, to, Interpolator.EASE_BOTH))
        );
        colorTimeline.play();
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

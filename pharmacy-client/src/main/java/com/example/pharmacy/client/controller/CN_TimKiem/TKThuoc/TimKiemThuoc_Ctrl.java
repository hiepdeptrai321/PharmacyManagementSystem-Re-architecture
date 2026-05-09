package com.example.pharmacy.client.controller.CN_TimKiem.TKThuoc;

import com.example.pharmacy.client.TienIch.LoadingOverlay;
import com.example.pharmacy.common.model.LoaiHangDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;
import com.example.pharmacy.client.service.ThuocService;
import com.example.pharmacy.client.view.CN_TimKiem.TKThuoc.ChiTietThuoc_GUI;
import com.example.pharmacy.client.view.CN_TimKiem.TKThuoc.TKThuoc_GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class TimKiemThuoc_Ctrl extends Application {

    public TableColumn<Thuoc_SanPhamDto, String> colTenThuoc;
    public TableColumn<Thuoc_SanPhamDto, String> colMaThuoc;
    public TableColumn<Thuoc_SanPhamDto, String> colHamLuong;
    public TableColumn<Thuoc_SanPhamDto, String> colXuatXu;
    public TableColumn<Thuoc_SanPhamDto, String> colSDK_GPNK;
    public TableColumn<Thuoc_SanPhamDto, String> colLoaiHang;
    public TableColumn<Thuoc_SanPhamDto, String> colViTri;
    public TableColumn<Thuoc_SanPhamDto, String> colChiTiet;
    public TableView<Thuoc_SanPhamDto> tbl_Thuoc;
    public ComboBox<String> cbxLoaiHang;
    public ComboBox<String> cbxXuatSu;
    public TextField txtHamLuongMin;
    public TextField txtHamLuongMax;
    public ComboBox<String> cboTimKiem;
    public TextField txtTimKiem;
    public Button btnReset;
    public StackPane rootTablePane;

    private final ThuocService thuocService = new ThuocService();
    private final ObservableList<Thuoc_SanPhamDto> duLieuChinh = FXCollections.observableArrayList();
    private FilteredList<Thuoc_SanPhamDto> duLieu;
    private LoadingOverlay loadingOverlay;

    @Override
    public void start(Stage primaryStage) throws Exception {
        new TKThuoc_GUI().showWithController(primaryStage, this);
    }

    public void initialize() {
        loadingOverlay = new LoadingOverlay();
        rootTablePane.getChildren().add(loadingOverlay);
        tbl_Thuoc.setPlaceholder(new Label(""));

        duLieu = new FilteredList<>(duLieuChinh, sp -> true);
        tbl_Thuoc.setItems(duLieu);

        cboTimKiem.getItems().setAll("Loại tìm kiếm", "Mã thuốc", "Tên thuốc", "Nước sản xuất", "Loại hàng", "Vị trí");
        cboTimKiem.setValue("Loại tìm kiếm");
        cbxLoaiHang.getItems().setAll("Chọn loại hàng");
        cbxLoaiHang.setValue("Chọn loại hàng");
        cbxXuatSu.getItems().setAll("Chọn xuất xứ");
        cbxXuatSu.setValue("Chọn xuất xứ");

        txtTimKiem.setOnAction(event -> timKiemTxt());
        cboTimKiem.valueProperty().addListener((o, ov, nv) -> timKiemTxt());
        cbxLoaiHang.setOnAction(e -> timKiemLoc());
        cbxXuatSu.setOnAction(e -> timKiemLoc());
        txtHamLuongMin.textProperty().addListener((o, ov, nv) -> timKiemLoc());
        txtHamLuongMax.textProperty().addListener((o, ov, nv) -> timKiemLoc());

        loadTable();
        taiDuLieu();
    }

    private void taiDuLieu() {
        Task<List<Thuoc_SanPhamDto>> task = new Task<>() {
            @Override
            protected List<Thuoc_SanPhamDto> call() {
                return thuocService.findAll();
            }
        };

        task.setOnRunning(e -> loadingOverlay.show());
        task.setOnSucceeded(e -> Platform.runLater(() -> {
            List<Thuoc_SanPhamDto> list = task.getValue();
            duLieuChinh.setAll(list);

            List<String> loaiHangs = list.stream()
                    .map(Thuoc_SanPhamDto::getLoaiHang)
                    .filter(Objects::nonNull)
                    .map(LoaiHangDto::getTenLoaiHang)
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .distinct()
                    .sorted()
                    .toList();

            List<String> xuatXus = list.stream()
                    .map(Thuoc_SanPhamDto::getNuocSX)
                    .filter(s -> s != null && !s.isBlank())
                    .distinct()
                    .sorted()
                    .toList();

            cbxLoaiHang.getItems().setAll("Chọn loại hàng");
            cbxLoaiHang.getItems().addAll(loaiHangs);

            cbxXuatSu.getItems().setAll("Chọn xuất xứ");
            cbxXuatSu.getItems().addAll(xuatXus);

            loadingOverlay.hide();
        }));
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            Platform.runLater(loadingOverlay::hide);
        });

        Thread thread = new Thread(task, "LoadThuocSanPham");
        thread.setDaemon(true);
        thread.start();
    }

    private void btnChiTietClick(Thuoc_SanPhamDto sp) {
        try {
            ChiTietThuoc_Ctrl ctrl = new ChiTietThuoc_Ctrl();
            ChiTietThuoc_GUI gui = new ChiTietThuoc_GUI();

            Stage dialog = new Stage();
            dialog.initOwner(txtTimKiem.getScene().getWindow());
            dialog.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialog.setTitle("Chi tiết thuốc");
            dialog.getIcons().add(new javafx.scene.image.Image(
                    getClass().getResourceAsStream("/com/example/pharmacy/client/img/logoNguyenBan.png")));

            gui.showWithController(dialog, ctrl);
            ctrl.initialize(sp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnXoaRong(ActionEvent actionEvent) {
        txtTimKiem.clear();
        cbxLoaiHang.setValue("Chọn loại hàng");
        cbxXuatSu.setValue("Chọn xuất xứ");
        txtHamLuongMin.clear();
        txtHamLuongMax.clear();
        duLieu.setPredicate(sp -> true);
    }

    private void loadTable() {
        colMaThuoc.setCellValueFactory(new PropertyValueFactory<>("maThuoc"));
        colTenThuoc.setCellValueFactory(new PropertyValueFactory<>("tenThuoc"));
        colHamLuong.setCellValueFactory(new PropertyValueFactory<>("hamLuongDonVi"));
        colXuatXu.setCellValueFactory(new PropertyValueFactory<>("nuocSX"));
        colSDK_GPNK.setCellValueFactory(new PropertyValueFactory<>("SDK_GPNK"));
        colLoaiHang.setCellValueFactory(cel -> new SimpleStringProperty(
                cel.getValue().getLoaiHang() != null ? cel.getValue().getLoaiHang().getTenLoaiHang() : ""));
        colViTri.setCellValueFactory(cel -> new SimpleStringProperty(
                cel.getValue().getVitri() != null ? cel.getValue().getVitri().getTenKe() : ""));
        colChiTiet.setCellFactory(cel -> new TableCell<>() {
            private final Button btn = new Button("Chi tiết");

            {
                btn.setOnAction(event -> {
                    Thuoc_SanPhamDto temp = getTableView().getItems().get(getIndex());
                    btnChiTietClick(temp);
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
    }

    private void timKiemLoc() {
        String loaiHang = cbxLoaiHang.getValue();
        String xuatXu = cbxXuatSu.getValue();
        Double hamLuongMin = parseDoubleOrNull(txtHamLuongMin.getText());
        Double hamLuongMax = parseDoubleOrNull(txtHamLuongMax.getText());

        duLieu.setPredicate(sp -> {
            boolean matchesLoaiHang = loaiHang == null
                    || loaiHang.isEmpty()
                    || "Chọn loại hàng".equals(loaiHang)
                    || (sp.getLoaiHang() != null && loaiHang.equals(sp.getLoaiHang().getTenLoaiHang()));

            boolean matchesXuatXu = xuatXu == null
                    || xuatXu.isEmpty()
                    || "Chọn xuất xứ".equals(xuatXu)
                    || xuatXu.equals(sp.getNuocSX());

            boolean matchesHamLuong = true;
            if (hamLuongMin != null) {
                matchesHamLuong = sp.getHamLuong() >= hamLuongMin;
            }
            if (hamLuongMax != null) {
                matchesHamLuong = matchesHamLuong && sp.getHamLuong() <= hamLuongMax;
            }

            return matchesLoaiHang && matchesXuatXu && matchesHamLuong;
        });
    }

    private Double parseDoubleOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private void timKiemTxt() {
        String filterType = cboTimKiem.getValue();
        String filterText = txtTimKiem.getText() == null ? "" : txtTimKiem.getText().toLowerCase().trim();

        duLieu.setPredicate(thuoc -> {
            if (filterText.isEmpty() || filterType == null || "Loại tìm kiếm".equals(filterType)) {
                return true;
            }

            return switch (filterType) {
                case "Mã thuốc" -> containsIgnoreCase(thuoc.getMaThuoc(), filterText);
                case "Tên thuốc" -> containsIgnoreCase(thuoc.getTenThuoc(), filterText);
                case "Nước sản xuất" -> containsIgnoreCase(thuoc.getNuocSX(), filterText);
                case "Loại hàng" -> thuoc.getLoaiHang() != null
                        && containsIgnoreCase(thuoc.getLoaiHang().getTenLoaiHang(), filterText);
                case "Vị trí" -> thuoc.getVitri() != null
                        && containsIgnoreCase(thuoc.getVitri().getTenKe(), filterText);
                default -> false;
            };
        });
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }
}

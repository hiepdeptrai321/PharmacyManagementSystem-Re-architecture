package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKKhachHang;

import com.example.pharmacymanagementsystem_qlht.TienIch.DoiNgay;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import com.example.pharmacymanagementsystem_qlht.service.KhachHangService;
import com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKKhachHang.TimKiemKhachHangTrongHD_GUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class TimKiemKhachHangTrongHD_Ctrl extends Application {
    public Button btnLamMoi;
    public Button btnTim;
    public ComboBox<String> cboTimKiem;
    public TableColumn<KhachHang, String> cotDiaChi;
    public TableColumn<KhachHang, String> cotEmail;
    public TableColumn<KhachHang, String> cotGT;
    public TableColumn<KhachHang, String> cotMaKH;
    public TableColumn<KhachHang, String> cotNgaySinh;
    public TableColumn<KhachHang, String> cotSDT;
    public TableColumn<KhachHang, String> cotTenKH;
    public TableColumn<KhachHang, String> cotSTT;
    public TableView<KhachHang> tbKhachHang;
    public Pane mainPane;
    public TextField txtTimKiem;

    private final KhachHangService khachHangService = new KhachHangService();
    private Consumer<KhachHang> onSelected;

    public void setOnSelected(Consumer<KhachHang> onSelected) {
        this.onSelected = onSelected;
    }

    @Override
    public void start(Stage stage) throws Exception {
        TimKiemKhachHangTrongHD_GUI gui = new TimKiemKhachHangTrongHD_GUI();
        gui.showWithController(stage, this);
    }

    public void initialize() {
        cboTimKiem.getItems().addAll(
                "Theo mÃ£, tÃªn khÃ¡ch hÃ ng",
                "Theo email",
                "Theo SDT"
        );
        cboTimKiem.setValue("Theo mÃ£, tÃªn khÃ¡ch hÃ ng");
        loadTable();
        btnLamMoi.setOnAction(e -> LamMoi());
        btnTim.setOnAction(e -> TimKiem());
        tbKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> chonKhachHang(newSel));
        tbKhachHang.setRowFactory(tv -> {
            TableRow<KhachHang> row = new TableRow<>();
            row.setOnMouseEntered(e -> {
                if (!row.isEmpty()) {
                    row.setStyle("-fx-background-color: #f2f2f2;");
                }
            });
            row.setOnMouseExited(e -> row.setStyle(""));
            return row;
        });
    }

    public void chonKhachHang(KhachHang kh) {
        if (kh == null) {
            return;
        }
        if (onSelected != null) {
            onSelected.accept(kh);
        }
        if (tbKhachHang != null && tbKhachHang.getScene() != null) {
            Stage st = (Stage) tbKhachHang.getScene().getWindow();
            if (st != null) {
                st.close();
            }
        }
    }

    public void loadTable() {
        List<KhachHang> list = khachHangService.findAll();
        ObservableList<KhachHang> data = FXCollections.observableArrayList(list);
        cotSTT.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(tbKhachHang.getItems().indexOf(cellData.getValue()) + 1))
        );
        cotMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        cotTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        cotGT.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        cotNgaySinh.setCellValueFactory(cellData -> new SimpleStringProperty(DoiNgay.dinhDangNgay(cellData.getValue().getNgaySinh())));
        cotSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        cotEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cotDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        tbKhachHang.setItems(data);
    }

    public void LamMoi() {
        txtTimKiem.clear();
        cboTimKiem.setValue("Theo mÃ£, tÃªn khÃ¡ch hÃ ng");
        loadTable();
    }

    public void TimKiem() {
        String criteria = cboTimKiem.getValue();
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<KhachHang> filtered = khachHangService.search(criteria, keyword);
        tbKhachHang.setItems(FXCollections.observableArrayList(filtered));
    }
}

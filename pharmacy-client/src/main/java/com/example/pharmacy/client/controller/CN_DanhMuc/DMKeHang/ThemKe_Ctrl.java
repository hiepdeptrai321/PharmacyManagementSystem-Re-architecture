package com.example.pharmacy.client.controller.CN_DanhMuc.DMKeHang;

import com.example.pharmacy.common.model.KeHang;
import com.example.pharmacy.client.service.KeHangService;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ThemKe_Ctrl extends Application {
    public Button btnHuy;
    public Button btnThem;
    public TextArea txtMoTa;
    public TextField txtTenKe;

    private final KeHangService keHangService = new KeHangService();

    public void initialize() {
        btnThem.setOnAction(e -> themKe());
        btnHuy.setOnAction(e -> btnHuyClick());
    }

    @Override
    public void start(Stage stage) throws IOException {
        new com.example.pharmacy.client.view.CN_DanhMuc.DMKeHang.ThemKe_GUI()
                .showWithController(stage, this);

        stage.setTitle("Thêm k�? hàng");
        stage.show();
    }

    public void btnHuyClick() {
        Stage stage = (Stage) txtTenKe.getScene().getWindow();
        stage.close();
    }

    void themKe() {
        String maKe = keHangService.generateNewMaKeHang();
        String tenKe = txtTenKe.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (tenKe.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập tên k�? hàng!");
            return;
        }

        KeHang keHang = new KeHang(maKe, tenKe, moTa);
        boolean success = keHangService.create(keHang);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thêm k�? hàng thành công!\nMã: " + maKe);
            txtTenKe.clear();
            txtMoTa.clear();
            dongCuaSo();
        } else {
            showAlert(Alert.AlertType.ERROR, "Thêm k�? hàng thất bại!");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void dongCuaSo() {
        Stage stage = (Stage) txtTenKe.getScene().getWindow();
        stage.close();
    }
}

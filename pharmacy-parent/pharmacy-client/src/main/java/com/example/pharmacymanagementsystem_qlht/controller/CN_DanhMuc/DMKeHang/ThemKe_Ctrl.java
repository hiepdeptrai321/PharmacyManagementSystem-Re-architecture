package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.model.KeHang;
import com.example.pharmacymanagementsystem_qlht.service.KeHangService;
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
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.ThemKe_GUI()
                .showWithController(stage, this);

        stage.setTitle("ThÃªm ká»‡ hÃ ng");
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
            showAlert(Alert.AlertType.WARNING, "Vui lÃ²ng nháº­p tÃªn ká»‡ hÃ ng!");
            return;
        }

        KeHang keHang = new KeHang(maKe, tenKe, moTa);
        boolean success = keHangService.create(keHang);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "ThÃªm ká»‡ hÃ ng thÃ nh cÃ´ng!\nMÃ£: " + maKe);
            txtTenKe.clear();
            txtMoTa.clear();
            dongCuaSo();
        } else {
            showAlert(Alert.AlertType.ERROR, "ThÃªm ká»‡ hÃ ng tháº¥t báº¡i!");
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

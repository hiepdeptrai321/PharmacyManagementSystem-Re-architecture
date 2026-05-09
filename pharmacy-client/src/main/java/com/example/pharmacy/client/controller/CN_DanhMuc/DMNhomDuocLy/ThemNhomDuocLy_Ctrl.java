package com.example.pharmacy.client.controller.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacy.common.model.NhomDuocLyDto;
import com.example.pharmacy.client.service.NhomDuocLyService;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ThemNhomDuocLy_Ctrl extends Application {
    public Button btnHuy;
    public Button btnThem;
    public TextArea txtMoTa;
    public TextField txtTenNhomDuocLy;

    private final NhomDuocLyService nhomDuocLyService = new NhomDuocLyService();

    @FXML
    public void initialize() {
        btnThem.setOnAction(e -> themNDL());
        btnHuy.setOnAction(e -> btnHuyClick());
    }

    @Override
    public void start(Stage stage) throws IOException {
        new com.example.pharmacy.client.view.CN_DanhMuc.DMNhomDuocLy.ThemNhomDuocLy_GUI()
                .showWithController(stage, this);

        stage.setTitle("Them nhom duoc ly");
        stage.show();
    }

    public void btnHuyClick() {
        dongCuaSo();
    }

    @FXML
    void themNDL() {
        String maNDL = nhomDuocLyService.generateNewMaNhomDL();
        String tenNDL = txtTenNhomDuocLy.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (tenNDL.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui long nhap ten nhom duoc ly!");
            return;
        }

        NhomDuocLyDto nhomDuocLy = new NhomDuocLyDto(maNDL, tenNDL, moTa);
        boolean success = nhomDuocLyService.create(nhomDuocLy);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Them nhom duoc ly thanh cong!\nMa: " + maNDL);
            txtTenNhomDuocLy.clear();
            txtMoTa.clear();
            dongCuaSo();
            return;
        }

        showAlert(Alert.AlertType.ERROR, "Them nhom duoc ly that bai!");
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void dongCuaSo() {
        Stage stage = (Stage) txtTenNhomDuocLy.getScene().getWindow();
        stage.close();
    }
}

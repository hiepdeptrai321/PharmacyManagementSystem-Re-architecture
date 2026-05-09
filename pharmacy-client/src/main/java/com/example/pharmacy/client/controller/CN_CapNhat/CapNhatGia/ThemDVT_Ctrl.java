package com.example.pharmacy.client.controller.CN_CapNhat.CapNhatGia;

import com.example.pharmacy.common.model.DonViTinh;
import com.example.pharmacy.client.service.DonViTinhService;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ThemDVT_Ctrl{
    public Button btnHuy;
    public Button btnThem;
    public TextField txtKyHieu;
    public TextField txtTenDVT;
    private final DonViTinhService donViTinhService = new DonViTinhService();
    public void initialize() {
        btnThem.setOnAction(e -> themDVT());
        btnHuy.setOnAction(e -> btnHuyClick());
    }

    public void btnHuyClick(){
        Stage stage = (Stage) txtTenDVT.getScene().getWindow();
        stage.close();
    }

    void themDVT() {
        // Sinh mã tự động
        String maDVT = donViTinhService.generatekeyDonViTinh();
        String tenDVT = txtTenDVT.getText().trim();
        String kyHieu = txtKyHieu.getText().trim();

        if (tenDVT.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập tên kệ hàng!");
            return;
        }

        DonViTinh donViTinh = new DonViTinh(maDVT, tenDVT, kyHieu);
        boolean success = donViTinhService.insert(donViTinh);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thêm đơn vị tính thành công!\nMã: " + maDVT);
            txtTenDVT.clear();
            txtKyHieu.clear();
            dongCuaSo();
        } else {
            showAlert(Alert.AlertType.ERROR, "Thêm đơn vị tính thất bại!");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void dongCuaSo() {
        Stage stage = (Stage) txtTenDVT.getScene().getWindow();
        stage.close();
    }
}


package com.example.pharmacy.client.controller.CN_DanhMuc.DMNhanVien;

import com.example.pharmacy.common.model.NhanVien;
import com.example.pharmacy.client.service.NhanVienService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SuaTaiKhoan_Ctrl {
    public TextField txtTaiKhoan;
    public TextField txtMatKhau;
    public NhanVien nhanVien;
    public boolean isSaved = false;

    private final NhanVienService nhanVienService = new NhanVienService();

    public void initialize() {
    }

    public void loadTaiKhoan(NhanVien nhanVien) {
        txtTaiKhoan.setText(nhanVien.getTaiKhoan());
        txtMatKhau.setText(nhanVien.getMatKhau());
        this.nhanVien = nhanVien;
    }

    public NhanVien getUpdatedNhanVien() {
        return nhanVien;
    }

    public void btnLuu(ActionEvent actionEvent) {
        String taiKhoan = txtTaiKhoan.getText() == null ? "" : txtTaiKhoan.getText().trim();
        String matKhau = txtMatKhau.getText() == null ? "" : txtMatKhau.getText();

        if (taiKhoan.isEmpty()) {
            showError("Tai khoan khong duoc de trong!");
            return;
        }
        if (matKhau.isEmpty()) {
            showError("Mat khau khong duoc de trong!");
            return;
        }
        if (matKhau.length() < 6) {
            showError("Mat khau phai co it nhat 6 ky tu!");
            return;
        }
        if (!nhanVienService.isUsernameAvailable(taiKhoan, nhanVien.getMaNV())) {
            showError("Tai khoan da ton tai!");
            return;
        }

        isSaved = true;
        nhanVien.setTaiKhoan(taiKhoan);
        nhanVien.setMatKhau(matKhau);
        dong();
    }

    public void btnHuy(ActionEvent actionEvent) {
        isSaved = false;
        dong();
    }

    private void dong() {
        Stage stage = (Stage) txtTaiKhoan.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Loi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

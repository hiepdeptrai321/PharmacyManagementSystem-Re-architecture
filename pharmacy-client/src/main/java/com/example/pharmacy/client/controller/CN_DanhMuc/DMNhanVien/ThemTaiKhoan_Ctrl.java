package com.example.pharmacy.client.controller.CN_DanhMuc.DMNhanVien;

import com.example.pharmacy.common.enums.UserRole;
import com.example.pharmacy.common.model.NhanVien;
import com.example.pharmacy.client.service.NhanVienService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ThemTaiKhoan_Ctrl {
    public TextField txtTaiKhoan;
    public TextField txtMatKhau;

    public NhanVien nhanVien;
    public boolean isSaved = false;

    private final NhanVienService nhanVienService = new NhanVienService();

    public void initialize() {
    }

    public void loadTaiKhoan(NhanVien nv) {
        nhanVien = nv == null ? new NhanVien() : new NhanVien(nv);
        if (nhanVien.getTaiKhoan() != null) {
            txtTaiKhoan.setText(nhanVien.getTaiKhoan());
        }
        if (nhanVien.getMatKhau() != null) {
            txtMatKhau.setText(nhanVien.getMatKhau());
        }
    }

    public NhanVien getUpdatedNhanVien() {
        return nhanVien;
    }

    public void btnLuu(ActionEvent actionEvent) {
        if (!kiemTraHopLe()) {
            return;
        }

        if (nhanVien == null) {
            nhanVien = new NhanVien();
        }
        nhanVien.setTaiKhoan(txtTaiKhoan.getText().trim());
        nhanVien.setMatKhau(txtMatKhau.getText());
        if (nhanVien.getVaiTro() == null || nhanVien.getVaiTro().isBlank()) {
            nhanVien.setVaiTro(UserRole.STAFF.toLegacyValue());
        }

        isSaved = true;
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

    private boolean kiemTraHopLe() {
        String taiKhoanNhap = txtTaiKhoan.getText() == null ? "" : txtTaiKhoan.getText().trim();
        String matKhauNhap = txtMatKhau.getText() == null ? "" : txtMatKhau.getText();

        if (taiKhoanNhap.isEmpty()) {
            showErr("Tai khoan khong duoc de trong!");
            return false;
        }
        if (matKhauNhap.isEmpty()) {
            showErr("Mat khau khong duoc de trong!");
            return false;
        }
        if (matKhauNhap.length() < 6) {
            showErr("Mat khau phai co it nhat 6 ky tu!");
            return false;
        }
        if (!nhanVienService.isUsernameAvailable(taiKhoanNhap, null)) {
            showErr("Tai khoan da ton tai!");
            return false;
        }
        return true;
    }

    private void showErr(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Loi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

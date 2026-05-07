package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien;

import com.example.pharmacy.common.enums.UserRole;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import com.example.pharmacymanagementsystem_qlht.service.NhanVienService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class ThemNhanVien_Ctrl {
    public TextField txtTenNV;
    public TextField txtSDT;
    public TextField txtEmail;
    public ComboBox<String> cbxGioiTinh;
    public TextField txtDiaChi;
    public DatePicker txtNgaySinh;

    private final NhanVienService nhanVienService = new NhanVienService();
    private NhanVien nhanVien = new NhanVien();
    public DanhMucNhanVien_Ctrl danhMucNhanVien_Ctrl;

    public void initialize() {
        cbxGioiTinh.getItems().setAll("Chon gioi tinh", "Nam", "Nu");
        cbxGioiTinh.getSelectionModel().selectFirst();
        nhanVien = new NhanVien();
    }

    public void btnThemTaiKhoan(ActionEvent actionEvent) {
        try {
            NhanVien copy = nhanVien != null ? new NhanVien(nhanVien) : new NhanVien();

            var gui = new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien.ThemTaiKhoan_GUI();
            var ctrl = new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.ThemTaiKhoan_Ctrl();

            Stage dialog = new Stage();
            dialog.initOwner(cbxGioiTinh.getScene().getWindow());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setTitle("Them tai khoan");

            gui.showWithController(dialog, ctrl);
            ctrl.loadTaiKhoan(copy);
            dialog.showAndWait();

            if (ctrl.isSaved) {
                NhanVien updated = ctrl.getUpdatedNhanVien();
                if (updated != null) {
                    nhanVien.setTaiKhoan(updated.getTaiKhoan());
                    nhanVien.setMatKhau(updated.getMatKhau());
                    nhanVien.setVaiTro(updated.getVaiTro());
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void btnThem(ActionEvent actionEvent) {
        if (!kiemTraHopLe()) {
            return;
        }

        NhanVien nv = new NhanVien();
        nv.setTenNV(txtTenNV.getText().trim());
        nv.setSdt(txtSDT.getText().trim());
        nv.setEmail(txtEmail.getText().trim());
        nv.setNgaySinh(Date.valueOf(txtNgaySinh.getValue()));
        nv.setGioiTinh("Nu".equals(cbxGioiTinh.getValue()));
        nv.setDiaChi(txtDiaChi.getText().trim());
        nv.setTrangThai(true);
        nv.setNgayVaoLam(Date.valueOf(LocalDate.now()));
        nv.setNgayNghiLam(null);
        nv.setTaiKhoan(nhanVien.getTaiKhoan());
        nv.setMatKhau(nhanVien.getMatKhau());
        nv.setTrangThaiXoa(false);
        nv.setVaiTro(nhanVien.getVaiTro() == null || nhanVien.getVaiTro().isBlank()
                ? UserRole.STAFF.toLegacyValue()
                : nhanVien.getVaiTro());

        boolean created = nhanVienService.create(nv);
        if (!created) {
            showError("Khong the tao nhan vien. Vui long kiem tra tai khoan da ton tai hoac server chua san sang.");
            return;
        }

        if (danhMucNhanVien_Ctrl != null) {
            danhMucNhanVien_Ctrl.loadData();
        }
        dong();
    }

    public void btnHuy(ActionEvent actionEvent) {
        dong();
    }

    public void setParent(DanhMucNhanVien_Ctrl parent) {
        this.danhMucNhanVien_Ctrl = parent;
    }

    private void dong() {
        Stage stage = (Stage) txtTenNV.getScene().getWindow();
        stage.close();
    }

    private boolean kiemTraHopLe() {
        if (txtTenNV.getText().trim().isEmpty()) {
            showError("Ten nhan vien khong duoc de trong!");
            return false;
        }
        if (txtSDT.getText().trim().isEmpty()) {
            showError("So dien thoai khong duoc de trong!");
            return false;
        }
        if (txtNgaySinh.getValue() == null) {
            showError("Ngay sinh khong duoc de trong!");
            return false;
        }
        if (cbxGioiTinh.getSelectionModel().getSelectedIndex() == 0) {
            showError("Vui long chon gioi tinh!");
            return false;
        }
        if (nhanVien.getTaiKhoan() == null || nhanVien.getTaiKhoan().isBlank()
                || nhanVien.getMatKhau() == null || nhanVien.getMatKhau().isBlank()) {
            showError("Vui long them tai khoan cho nhan vien!");
            return false;
        }
        if (!txtSDT.getText().trim().matches("0\\d{9}")) {
            showError("So dien thoai khong hop le!");
            return false;
        }
        if (!txtEmail.getText().trim().isEmpty()
                && !txtEmail.getText().trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            showError("Email khong hop le!");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Loi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

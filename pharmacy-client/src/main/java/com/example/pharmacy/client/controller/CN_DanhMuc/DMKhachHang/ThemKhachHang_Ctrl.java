package com.example.pharmacy.client.controller.CN_DanhMuc.DMKhachHang;

import com.example.pharmacy.common.model.KhachHangDto;
import com.example.pharmacy.client.service.KhachHangService;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.function.Consumer;

public class ThemKhachHang_Ctrl {

    public TextField txtTenKH;
    public DatePicker dpNgaySinh;
    public TextField txtSDT;
    public TextField txtEmail;
    public TextField txtDiaChi;
    public ComboBox<String> cbGioiTinh;
    public Label lblMessage;
    public Button btnThem;
    public Button btnHuy;

    public Label errTenKH;
    public Label errNgaySinh;
    public Label errSDT;
    public Label errEmail;
    public Label errDiaChi;
    public Label errGioiTinh;

    private final KhachHangService khachHangService = new KhachHangService();
    private Consumer<KhachHangDto> onSaved;

    public void setOnSaved(Consumer<KhachHangDto> onSaved) {
        this.onSaved = onSaved;
    }

    public void init() {
        cbGioiTinh.getItems().addAll("Nam", "Nữ");
        clearErrors();

        btnThem.setOnAction(e -> handleSave());
        btnHuy.setOnAction(e -> handleCancel());
    }

    private void clearErrors() {
        errTenKH.setText("");
        errTenKH.setVisible(false);
        errNgaySinh.setText("");
        errNgaySinh.setVisible(false);
        errSDT.setText("");
        errSDT.setVisible(false);
        errEmail.setText("");
        errEmail.setVisible(false);
        errDiaChi.setText("");
        errDiaChi.setVisible(false);
        errGioiTinh.setText("");
        errGioiTinh.setVisible(false);

        lblMessage.setText("");
    }

    private void setError(Label lbl, String msg) {
        lbl.setText(msg);
        lbl.setVisible(true);
    }

    public void handleSave() {
        clearErrors();

        String ten = txtTenKH.getText().trim();
        String sdt = txtSDT.getText().trim();
        String email = txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        LocalDate ns = dpNgaySinh.getValue();
        String gioiTinh = cbGioiTinh.getValue();

        boolean valid = true;

        if (ten.isEmpty()) {
            setError(errTenKH, "Không �'ược bỏ tr�'ng");
            valid = false;
        }
        if (gioiTinh == null) {
            setError(errGioiTinh, "Chọn gi�>i tính");
            valid = false;
        }
        if (ns == null) {
            setError(errNgaySinh, "Chọn ngày sinh");
            valid = false;
        }
        if (!sdt.matches("\\d{10}")) {
            setError(errSDT, "SĐT phải 10 s�'");
            valid = false;
        }
        if (email.isEmpty()) {
            setError(errEmail, "Không �'ược r�-ng");
            valid = false;
        } else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            setError(errEmail, "Email không hợp l�?");
            valid = false;
        }
        if (diaChi.isEmpty()) {
            setError(errDiaChi, "Không �'ược r�-ng");
            valid = false;
        }

        if (!valid) {
            return;
        }

        KhachHangDto kh = new KhachHangDto();
        kh.setMaKH(khachHangService.generateNewMaKH());
        kh.setTenKH(ten);
        kh.setSdt(sdt);
        kh.setEmail(email);
        kh.setDiaChi(diaChi);
        kh.setNgaySinh(ns);
        kh.setGioiTinh("Nam".equals(gioiTinh));
        kh.setTrangThai(true);

        if (!khachHangService.create(kh)) {
            lblMessage.setText("L�-i cơ s�Y dữ li�?u");
            return;
        }

        if (onSaved != null) {
            onSaved.accept(kh);
        }

        Stage st = (Stage) btnThem.getScene().getWindow();
        st.close();
    }

    public void handleCancel() {
        Stage st = (Stage) btnHuy.getScene().getWindow();
        st.close();
    }
}

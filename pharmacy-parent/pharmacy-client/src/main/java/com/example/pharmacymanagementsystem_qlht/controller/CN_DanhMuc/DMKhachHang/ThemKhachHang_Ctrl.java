package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhachHang;

import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import com.example.pharmacymanagementsystem_qlht.service.KhachHangService;
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
    private Consumer<KhachHang> onSaved;

    public void setOnSaved(Consumer<KhachHang> onSaved) {
        this.onSaved = onSaved;
    }

    public void init() {
        cbGioiTinh.getItems().addAll("Nam", "Ná»¯");
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
            setError(errTenKH, "KhÃ´ng Ä‘Æ°á»£c bá» trá»‘ng");
            valid = false;
        }
        if (gioiTinh == null) {
            setError(errGioiTinh, "Chá»n giá»›i tÃ­nh");
            valid = false;
        }
        if (ns == null) {
            setError(errNgaySinh, "Chá»n ngÃ y sinh");
            valid = false;
        }
        if (!sdt.matches("\\d{10}")) {
            setError(errSDT, "SÄT pháº£i 10 sá»‘");
            valid = false;
        }
        if (email.isEmpty()) {
            setError(errEmail, "KhÃ´ng Ä‘Æ°á»£c rá»—ng");
            valid = false;
        } else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            setError(errEmail, "Email khÃ´ng há»£p lá»‡");
            valid = false;
        }
        if (diaChi.isEmpty()) {
            setError(errDiaChi, "KhÃ´ng Ä‘Æ°á»£c rá»—ng");
            valid = false;
        }

        if (!valid) {
            return;
        }

        KhachHang kh = new KhachHang();
        kh.setMaKH(khachHangService.generateNewMaKH());
        kh.setTenKH(ten);
        kh.setSdt(sdt);
        kh.setEmail(email);
        kh.setDiaChi(diaChi);
        kh.setNgaySinh(ns);
        kh.setGioiTinh("Nam".equals(gioiTinh));
        kh.setTrangThai(true);

        if (!khachHangService.create(kh)) {
            lblMessage.setText("Lá»—i cÆ¡ sá»Ÿ dá»¯ liá»‡u");
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

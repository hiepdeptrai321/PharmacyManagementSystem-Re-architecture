package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhachHang;

import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import com.example.pharmacymanagementsystem_qlht.service.KhachHangService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ChiTietKhachHang_Ctrl extends Application {

    public Button btnLuu;
    public Button btnHuy;
    public Button btnXoa;
    public TextField txtDiaChi;
    public TextField txtEmail;
    public DatePicker txtNgaySinh;
    public TextField txtSDT;
    public TextField txtTenKH;
    public ComboBox<String> cboGioiTinh;
    public Label errTenKH;
    public Label errDiaChi;
    public Label errEmail;
    public Label errSDT;

    private final KhachHangService khachHangService = new KhachHangService();
    private KhachHang khachHang;

    @Override
    public void start(Stage stage) throws Exception {
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKhachHang.SuaXoaKhachHang_GUI()
                .showWithController(stage, this);

        stage.setTitle("Chi tiáº¿t khÃ¡ch hÃ ng");
        stage.show();
    }

    public void initialize() {
        cboGioiTinh.setItems(FXCollections.observableArrayList("Nam", "Ná»¯"));
        btnHuy.setOnAction(e -> HuyClick());
        btnXoa.setOnAction(e -> XoaClick());
        btnLuu.setOnAction(e -> LuuClick());

        if (khachHang != null) {
            txtTenKH.setText(khachHang.getTenKH());
            txtDiaChi.setText(khachHang.getDiaChi() != null ? khachHang.getDiaChi() : "");
            txtEmail.setText(khachHang.getEmail() != null ? khachHang.getEmail() : "");
            txtSDT.setText(khachHang.getSdt() != null ? khachHang.getSdt() : "");
            txtNgaySinh.setValue(khachHang.getNgaySinh());
            cboGioiTinh.setValue(Boolean.TRUE.equals(khachHang.getGioiTinh()) ? "Nam" : "Ná»¯");
        }
    }

    public void hienThiThongTin(KhachHang kh) {
        if (kh != null) {
            this.khachHang = kh;
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        errTenKH.setText("");
        errSDT.setText("");
        errEmail.setText("");
        errDiaChi.setText("");

        if (txtTenKH.getText().trim().isEmpty()) {
            errTenKH.setText("TÃªn khÃ¡ch hÃ ng khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.");
            isValid = false;
        }

        String sdt = txtSDT.getText().trim();
        if (sdt.isEmpty()) {
            errSDT.setText("Sá»‘ Ä‘iá»‡n thoáº¡i khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.");
            isValid = false;
        } else if (!sdt.matches("\\d{10}")) {
            errSDT.setText("Sá»‘ Ä‘iá»‡n thoáº¡i khÃ´ng há»£p lá»‡ (10sá»‘).");
            isValid = false;
        }

        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            errEmail.setText("Email khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.");
            isValid = false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errEmail.setText("Email khÃ´ng há»£p lá»‡.");
            isValid = false;
        }

        if (txtDiaChi.getText().trim().isEmpty()) {
            errDiaChi.setText("Äá»‹a chá»‰ khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.");
            isValid = false;
        }

        return isValid;
    }

    @FXML
    private void LuuClick() {
        try {
            if (!validateFields()) {
                return;
            }

            if (khachHang == null) {
                khachHang = new KhachHang();
            }

            khachHang.setTenKH(txtTenKH.getText().trim());
            khachHang.setDiaChi(txtDiaChi.getText().trim());
            khachHang.setEmail(txtEmail.getText().trim());
            khachHang.setSdt(txtSDT.getText().trim());
            khachHang.setNgaySinh(txtNgaySinh.getValue() != null ? txtNgaySinh.getValue() : LocalDate.now());
            khachHang.setGioiTinh("Nam".equals(cboGioiTinh.getValue()));
            khachHang.setTrangThai(true);

            boolean success = khachHangService.save(khachHang);
            if (success) {
                thongBao("LÆ°u thÃ nh cÃ´ng!");
                dongCuaSo();
            } else {
                thongBao("LÆ°u tháº¥t báº¡i!");
            }
        } catch (Exception e) {
            thongBao("Lá»—i khi lÆ°u: " + e.getMessage());
        }
    }

    @FXML
    private void XoaClick() {
        if (khachHang == null || khachHang.getMaKH() == null) {
            thongBao("ChÆ°a chá»n khÃ¡ch hÃ ng Ä‘á»ƒ xÃ³a!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("XÃ¡c nháº­n xÃ³a");
        alert.setHeaderText("Báº¡n cÃ³ cháº¯c muá»‘n xÃ³a khÃ¡ch hÃ ng nÃ y?");
        alert.setContentText("KhÃ¡ch hÃ ng sáº½ Ä‘Æ°á»£c áº©n (tráº¡ng thÃ¡i = 0).");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean result = khachHangService.deleteById(khachHang.getMaKH());
                if (result) {
                    thongBao("ÄÃ£ xÃ³a khÃ¡ch hÃ ng!");
                    dongCuaSo();
                } else {
                    thongBao("XÃ³a tháº¥t báº¡i!");
                }
            }
        });
    }

    @FXML
    private void HuyClick() {
        dongCuaSo();
    }

    private void thongBao(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ThÃ´ng bÃ¡o");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void dongCuaSo() {
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        stage.close();
    }
}

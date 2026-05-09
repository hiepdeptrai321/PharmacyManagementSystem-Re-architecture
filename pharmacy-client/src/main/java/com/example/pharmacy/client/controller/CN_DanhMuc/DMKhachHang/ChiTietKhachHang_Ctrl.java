package com.example.pharmacy.client.controller.CN_DanhMuc.DMKhachHang;

import com.example.pharmacy.common.model.KhachHangDto;
import com.example.pharmacy.client.service.KhachHangService;
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
    private KhachHangDto khachHang;

    @Override
    public void start(Stage stage) throws Exception {
        new com.example.pharmacy.client.view.CN_DanhMuc.DMKhachHang.SuaXoaKhachHang_GUI()
                .showWithController(stage, this);

        stage.setTitle("Chi tiết khách hàng");
        stage.show();
    }

    public void initialize() {
        cboGioiTinh.setItems(FXCollections.observableArrayList("Nam", "Nữ"));
        btnHuy.setOnAction(e -> HuyClick());
        btnXoa.setOnAction(e -> XoaClick());
        btnLuu.setOnAction(e -> LuuClick());

        if (khachHang != null) {
            txtTenKH.setText(khachHang.getTenKH());
            txtDiaChi.setText(khachHang.getDiaChi() != null ? khachHang.getDiaChi() : "");
            txtEmail.setText(khachHang.getEmail() != null ? khachHang.getEmail() : "");
            txtSDT.setText(khachHang.getSdt() != null ? khachHang.getSdt() : "");
            txtNgaySinh.setValue(khachHang.getNgaySinh());
            cboGioiTinh.setValue(Boolean.TRUE.equals(khachHang.getGioiTinh()) ? "Nam" : "Nữ");
        }
    }

    public void hienThiThongTin(KhachHangDto kh) {
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
            errTenKH.setText("Tên khách hàng không �'ược �'�f tr�'ng.");
            isValid = false;
        }

        String sdt = txtSDT.getText().trim();
        if (sdt.isEmpty()) {
            errSDT.setText("S�' �'i�?n thoại không �'ược �'�f tr�'ng.");
            isValid = false;
        } else if (!sdt.matches("\\d{10}")) {
            errSDT.setText("S�' �'i�?n thoại không hợp l�? (10s�').");
            isValid = false;
        }

        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            errEmail.setText("Email không �'ược �'�f tr�'ng.");
            isValid = false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errEmail.setText("Email không hợp l�?.");
            isValid = false;
        }

        if (txtDiaChi.getText().trim().isEmpty()) {
            errDiaChi.setText("Đ�<a ch�? không �'ược �'�f tr�'ng.");
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
                khachHang = new KhachHangDto();
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
                thongBao("Lưu thành công!");
                dongCuaSo();
            } else {
                thongBao("Lưu thất bại!");
            }
        } catch (Exception e) {
            thongBao("L�-i khi lưu: " + e.getMessage());
        }
    }

    @FXML
    private void XoaClick() {
        if (khachHang == null || khachHang.getMaKH() == null) {
            thongBao("Chưa chọn khách hàng �'�f xóa!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc mu�'n xóa khách hàng này?");
        alert.setContentText("Khách hàng sẽ �'ược ẩn (trạng thái = 0).");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean result = khachHangService.deleteById(khachHang.getMaKH());
                if (result) {
                    thongBao("Đã xóa khách hàng!");
                    dongCuaSo();
                } else {
                    thongBao("Xóa thất bại!");
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
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void dongCuaSo() {
        Stage stage = (Stage) btnHuy.getScene().getWindow();
        stage.close();
    }
}

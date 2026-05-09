package com.example.pharmacy.client.controller.CN_DanhMuc.DMKeHang;

import com.example.pharmacy.common.model.KeHangDto;
import com.example.pharmacy.client.service.KeHangService;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class XoaSuaKeHang_Ctrl extends Application {

    public Pane btnLuu;
    public Pane btnXoa;
    public TextArea txtMota;
    public TextField txtTenKe;

    private final KeHangService keHangService = new KeHangService();
    private KeHangDto keHangHienTai;

    @Override
    public void start(Stage stage) throws IOException {
        new com.example.pharmacy.client.view.CN_DanhMuc.DMKeHang.XoaSuaKeHang_GUI()
                .showWithController(stage, this);

        stage.setTitle("Chi tiết k�? hàng");
        stage.show();
    }

    public void hienThiThongTin(KeHangDto kh) {
        if (kh != null) {
            keHangHienTai = kh;
        }
    }

    public void initialize() {
        btnLuu.setOnMouseClicked(event -> luuKeHang());
        btnXoa.setOnMouseClicked(event -> xoaKeHang());

        if (keHangHienTai != null) {
            txtTenKe.setText(keHangHienTai.getTenKe());
            txtMota.setText(keHangHienTai.getMoTa() != null ? keHangHienTai.getMoTa() : "");
        }
    }

    private void luuKeHang() {
        if (keHangHienTai == null) {
            showAlert("L�-i", "Không có dữ li�?u k�? hàng �'�f cập nhật!", Alert.AlertType.ERROR);
            return;
        }

        String tenKe = txtTenKe.getText().trim();
        String moTa = txtMota.getText().trim();

        if (tenKe.isEmpty()) {
            showAlert("L�-i", "Tên k�? không �'ược �'�f tr�'ng!", Alert.AlertType.ERROR);
            return;
        }

        keHangHienTai.setTenKe(tenKe);
        keHangHienTai.setMoTa(moTa);

        boolean success = keHangService.update(keHangHienTai);
        if (success) {
            showAlert("Thành công", "Cập nhật k�? hàng thành công!", Alert.AlertType.INFORMATION);
            dongCuaSo();
        } else {
            showAlert("Thất bại", "Không th�f cập nhật k�? hàng!", Alert.AlertType.ERROR);
        }
    }

    private void xoaKeHang() {
        if (keHangHienTai == null) {
            showAlert("L�-i", "Không có dữ li�?u k�? hàng �'�f xóa!", Alert.AlertType.ERROR);
            return;
        }

        try {
            List<String> thuocTrongKe = keHangService.getThuocTrongKe(keHangHienTai.getMaKe());
            if (thuocTrongKe != null && !thuocTrongKe.isEmpty()) {
                showThuocConTrongKe(thuocTrongKe);
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Xác nhận");
            confirm.setHeaderText("Bạn có chắc mu�'n xóa k�? này?");
            confirm.setContentText("Tên k�?: " + keHangHienTai.getTenKe());
            if (confirm.showAndWait().orElse(null) != ButtonType.OK) {
                return;
            }

            boolean success = keHangService.deleteById(keHangHienTai.getMaKe());
            if (success) {
                showAlert("Thành công", "Đã xóa k�? hàng thành công!", Alert.AlertType.INFORMATION);
                dongCuaSo();
            } else {
                showAlert("Thất bại", "Không th�f xóa k�? hàng!", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("L�-i h�? th�'ng", "Đã xảy ra l�-i khi xóa: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showThuocConTrongKe(List<String> thuocTrongKe) {
        StringBuilder sb = new StringBuilder("K�? này vẫn còn các thu�'c sau:\n\n");
        for (String tenThuoc : thuocTrongKe) {
            sb.append("- ").append(tenThuoc).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Không th�f xóa k�? hàng");
        alert.setHeaderText("Không th�f xóa! K�? này vẫn còn thu�'c.");

        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefWidth(400);
        textArea.setPrefHeight(250);

        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void dongCuaSo() {
        Stage stage = (Stage) txtTenKe.getScene().getWindow();
        stage.close();
    }
}

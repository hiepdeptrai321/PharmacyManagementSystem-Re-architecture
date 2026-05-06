package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang;

import com.example.pharmacymanagementsystem_qlht.model.KeHang;
import com.example.pharmacymanagementsystem_qlht.service.KeHangService;
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
    private KeHang keHangHienTai;

    @Override
    public void start(Stage stage) throws IOException {
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMKeHang.XoaSuaKeHang_GUI()
                .showWithController(stage, this);

        stage.setTitle("Chi tiáº¿t ká»‡ hÃ ng");
        stage.show();
    }

    public void hienThiThongTin(KeHang kh) {
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
            showAlert("Lá»—i", "KhÃ´ng cÃ³ dá»¯ liá»‡u ká»‡ hÃ ng Ä‘á»ƒ cáº­p nháº­t!", Alert.AlertType.ERROR);
            return;
        }

        String tenKe = txtTenKe.getText().trim();
        String moTa = txtMota.getText().trim();

        if (tenKe.isEmpty()) {
            showAlert("Lá»—i", "TÃªn ká»‡ khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng!", Alert.AlertType.ERROR);
            return;
        }

        keHangHienTai.setTenKe(tenKe);
        keHangHienTai.setMoTa(moTa);

        boolean success = keHangService.update(keHangHienTai);
        if (success) {
            showAlert("ThÃ nh cÃ´ng", "Cáº­p nháº­t ká»‡ hÃ ng thÃ nh cÃ´ng!", Alert.AlertType.INFORMATION);
            dongCuaSo();
        } else {
            showAlert("Tháº¥t báº¡i", "KhÃ´ng thá»ƒ cáº­p nháº­t ká»‡ hÃ ng!", Alert.AlertType.ERROR);
        }
    }

    private void xoaKeHang() {
        if (keHangHienTai == null) {
            showAlert("Lá»—i", "KhÃ´ng cÃ³ dá»¯ liá»‡u ká»‡ hÃ ng Ä‘á»ƒ xÃ³a!", Alert.AlertType.ERROR);
            return;
        }

        try {
            List<String> thuocTrongKe = keHangService.getThuocTrongKe(keHangHienTai.getMaKe());
            if (thuocTrongKe != null && !thuocTrongKe.isEmpty()) {
                showThuocConTrongKe(thuocTrongKe);
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("XÃ¡c nháº­n");
            confirm.setHeaderText("Báº¡n cÃ³ cháº¯c muá»‘n xÃ³a ká»‡ nÃ y?");
            confirm.setContentText("TÃªn ká»‡: " + keHangHienTai.getTenKe());
            if (confirm.showAndWait().orElse(null) != ButtonType.OK) {
                return;
            }

            boolean success = keHangService.deleteById(keHangHienTai.getMaKe());
            if (success) {
                showAlert("ThÃ nh cÃ´ng", "ÄÃ£ xÃ³a ká»‡ hÃ ng thÃ nh cÃ´ng!", Alert.AlertType.INFORMATION);
                dongCuaSo();
            } else {
                showAlert("Tháº¥t báº¡i", "KhÃ´ng thá»ƒ xÃ³a ká»‡ hÃ ng!", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lá»—i há»‡ thá»‘ng", "ÄÃ£ xáº£y ra lá»—i khi xÃ³a: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showThuocConTrongKe(List<String> thuocTrongKe) {
        StringBuilder sb = new StringBuilder("Ká»‡ nÃ y váº«n cÃ²n cÃ¡c thuá»‘c sau:\n\n");
        for (String tenThuoc : thuocTrongKe) {
            sb.append("- ").append(tenThuoc).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("KhÃ´ng thá»ƒ xÃ³a ká»‡ hÃ ng");
        alert.setHeaderText("KhÃ´ng thá»ƒ xÃ³a! Ká»‡ nÃ y váº«n cÃ²n thuá»‘c.");

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

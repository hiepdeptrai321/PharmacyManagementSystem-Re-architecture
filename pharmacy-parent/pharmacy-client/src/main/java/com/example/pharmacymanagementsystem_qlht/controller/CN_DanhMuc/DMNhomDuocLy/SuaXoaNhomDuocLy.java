package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy;

import com.example.pharmacymanagementsystem_qlht.model.NhomDuocLy;
import com.example.pharmacymanagementsystem_qlht.service.NhomDuocLyService;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SuaXoaNhomDuocLy extends Application {

    public Pane btnLuu;
    public Pane btnXoa;
    public TextArea txtMota;
    public TextField txtTenNDL;

    private final NhomDuocLyService nhomDuocLyService = new NhomDuocLyService();
    private NhomDuocLy nhomDuocLyHienTai;

    @Override
    public void start(Stage stage) throws IOException {
        new com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhomDuocLy.XoaSuaNhomDuocLy_GUI()
                .showWithController(stage, this);

        stage.setTitle("Chi tiet nhom duoc ly");
        stage.show();
    }

    public void initialize() {
        btnLuu.setOnMouseClicked(event -> luuNDL());
        btnXoa.setOnMouseClicked(event -> xoaNDL());

        if (nhomDuocLyHienTai != null) {
            txtTenNDL.setText(nhomDuocLyHienTai.getTenNDL());
            txtMota.setText(nhomDuocLyHienTai.getMoTa() != null ? nhomDuocLyHienTai.getMoTa() : "");
        }
    }

    public void hienThiThongTin(NhomDuocLy ndl) {
        if (ndl != null) {
            this.nhomDuocLyHienTai = ndl;
        }
    }

    private void luuNDL() {
        if (nhomDuocLyHienTai == null) {
            showAlert("Loi", "Khong co du lieu nhom duoc ly de cap nhat!", Alert.AlertType.ERROR);
            return;
        }

        String tenNDL = txtTenNDL.getText().trim();
        String moTa = txtMota.getText().trim();

        if (tenNDL.isEmpty()) {
            showAlert("Loi", "Ten nhom duoc ly khong duoc de trong!", Alert.AlertType.ERROR);
            return;
        }

        nhomDuocLyHienTai.setTenNDL(tenNDL);
        nhomDuocLyHienTai.setMoTa(moTa);

        boolean success = nhomDuocLyService.update(nhomDuocLyHienTai);
        if (success) {
            showAlert("Thanh cong", "Cap nhat nhom duoc ly thanh cong!", Alert.AlertType.INFORMATION);
            dongCuaSo();
            return;
        }

        showAlert("That bai", "Khong the cap nhat nhom duoc ly!", Alert.AlertType.ERROR);
    }

    private void xoaNDL() {
        if (nhomDuocLyHienTai == null) {
            showAlert("Loi", "Khong co du lieu de xoa!", Alert.AlertType.ERROR);
            return;
        }

        try {
            List<String> thuocTrongNDL =
                    nhomDuocLyService.findThuocNamesByNhomDuocLy(nhomDuocLyHienTai.getMaNDL());

            if (thuocTrongNDL != null && !thuocTrongNDL.isEmpty()) {
                showThuocConTrongNDL(thuocTrongNDL);
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Xac nhan");
            confirm.setHeaderText("Ban co chac muon xoa nhom duoc ly nay?");
            confirm.setContentText("Ten nhom duoc ly: " + nhomDuocLyHienTai.getTenNDL());
            if (confirm.showAndWait().orElse(null) != ButtonType.OK) {
                return;
            }

            boolean success = nhomDuocLyService.deleteById(nhomDuocLyHienTai.getMaNDL());
            if (success) {
                showAlert("Thanh cong", "Da xoa nhom duoc ly thanh cong!", Alert.AlertType.INFORMATION);
                dongCuaSo();
                return;
            }

            showAlert("That bai", "Khong the xoa nhom duoc ly!", Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Loi he thong", "Da xay ra loi khi xoa: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showThuocConTrongNDL(List<String> thuocTrongNDL) {
        StringBuilder sb = new StringBuilder("Nhom duoc ly nay van con cac thuoc sau:\n\n");
        for (String tenThuoc : thuocTrongNDL) {
            sb.append("- ").append(tenThuoc).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Khong the xoa nhom duoc ly");
        alert.setHeaderText("Khong the xoa! nhom duoc ly nay van con thuoc.");

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
        Stage stage = (Stage) txtTenNDL.getScene().getWindow();
        stage.close();
    }
}

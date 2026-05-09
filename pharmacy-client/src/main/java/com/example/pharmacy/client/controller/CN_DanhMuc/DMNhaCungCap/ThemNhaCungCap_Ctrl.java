package com.example.pharmacy.client.controller.CN_DanhMuc.DMNhaCungCap;

import com.example.pharmacy.common.model.NhaCungCapDto;
import com.example.pharmacy.client.service.NhaCungCapService;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ThemNhaCungCap_Ctrl {

    private DanhMucNhaCungCap_Ctrl danhMucNhaCungCap_Ctrl;
    private Runnable onNhaCungCapCreated;
    public TextField txtTenNCC;
    public TextField txtDiaChi;
    public TextField txtSDT;
    public TextField txtEmail;
    public TextField txtGPKD_SDK;
    public TextField txtTenCongTy;
    public TextField txtMaSoThue;
    public TextArea txtGhiChu;
    public Button btnThem;
    public Button btnHuy;
    private final NhaCungCapService nhaCungCapService = new NhaCungCapService();

    public void initialize() {
        btnThem.setOnAction(e -> btnThemNCC());
        btnHuy.setOnAction(e -> btnHuy());
    }

    public void setParentCtrl(DanhMucNhaCungCap_Ctrl parent) {
        this.danhMucNhaCungCap_Ctrl = parent;
    }

    public void setOnNhaCungCapCreated(Runnable onNhaCungCapCreated) {
        this.onNhaCungCapCreated = onNhaCungCapCreated;
    }

    public void btnHuy() {
        dong();
    }

    public void btnThemNCC() {
        NhaCungCapDto ncc = new NhaCungCapDto();
        ncc.setTenNCC(txtTenNCC.getText());
        ncc.setDiaChi(txtDiaChi.getText());
        ncc.setSDT(txtSDT.getText());
        ncc.setEmail(txtEmail.getText());
        ncc.setGPKD(txtGPKD_SDK.getText());
        ncc.setTenCongTy(txtTenCongTy.getText());
        ncc.setMSThue(txtMaSoThue.getText());
        ncc.setGhiChu(txtGhiChu.getText());

        if (!kiemTraHopLe(ncc)) {
            return;
        }

        if (nhaCungCapService.create(ncc)) {
            hienThongBao("Thanh cong", "Them nha cung cap thanh cong!");
            if (danhMucNhaCungCap_Ctrl != null) {
                danhMucNhaCungCap_Ctrl.refreshTable();
            } else if (onNhaCungCapCreated != null) {
                onNhaCungCapCreated.run();
            }
            dong();
            return;
        }

        hienThongBao("Loi", "Them nha cung cap that bai!");
    }

    private void dong() {
        Stage stage = (Stage) txtTenNCC.getScene().getWindow();
        stage.close();
    }

    private boolean kiemTraHopLe(NhaCungCapDto ncc) {
        if (ncc.getTenNCC().isEmpty()) {
            hienThongBao("Loi", "Ten nha cung cap khong duoc de trong!");
            return false;
        }

        if (ncc.getSDT().isEmpty()) {
            hienThongBao("Loi", "So dien thoai khong duoc de trong!");
            return false;
        }

        if (!ncc.getSDT().matches("0\\d{9}")) {
            hienThongBao("Loi", "So dien thoai khong hop le! So dien thoai phai bat dau bang 0 va co 10 chu so.");
            return false;
        }

        if (!ncc.getEmail().isEmpty() &&
                !ncc.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            hienThongBao("Loi", "Email khong hop le!");
            return false;
        }

        if (!ncc.getMSThue().isEmpty() && !ncc.getMSThue().matches("\\d{10}")) {
            hienThongBao("Loi", "Ma so thue khong hop le! Ma so thue phai gom 10 chu so.");
            return false;
        }

        return true;
    }

    private void hienThongBao(String title, String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText(message);
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();
    }
}

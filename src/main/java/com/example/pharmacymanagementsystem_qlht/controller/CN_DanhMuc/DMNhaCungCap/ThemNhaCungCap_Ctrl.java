package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang.LapPhieuNhapHang_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import com.example.pharmacymanagementsystem_qlht.service.NhaCungCapService;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ThemNhaCungCap_Ctrl {

    private DanhMucNhaCungCap_Ctrl danhMucNhaCungCap_Ctrl;
    private LapPhieuNhapHang_Ctrl lapPhieuNhapHang_Ctrl;
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
    NhaCungCap ncc = new NhaCungCap();

    private final NhaCungCapService nhaCungCapService = new NhaCungCapService();

    public void initialize() {
        btnThem.setOnAction(e -> btnThemNCC());
        btnHuy.setOnAction(e -> btnHuy());
    }

    public void setParentCtrl(DanhMucNhaCungCap_Ctrl parent) {
        this.danhMucNhaCungCap_Ctrl = parent;
    }

    public void setLapPhieuNhapHang_Ctrl(LapPhieuNhapHang_Ctrl lapPhieuNhapHang_Ctrl) {
        this.lapPhieuNhapHang_Ctrl = lapPhieuNhapHang_Ctrl;
    }

    public void btnHuy() {
        dong();
    }

    public void btnThemNCC() {
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
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("ThÃ nh cÃ´ng");
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContentText("ThÃªm nhÃ  cung cáº¥p thÃ nh cÃ´ng!");
            dialogPane.getButtonTypes().addAll(ButtonType.OK);
            dialog.setDialogPane(dialogPane);
            dialog.showAndWait();
            if (danhMucNhaCungCap_Ctrl != null) {
                danhMucNhaCungCap_Ctrl.refreshTable();
            } else if (lapPhieuNhapHang_Ctrl != null) {
                lapPhieuNhapHang_Ctrl.taiDanhSachNCC();
            }
        } else {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Lá»—i");
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContentText("ThÃªm nhÃ  cung cáº¥p tháº¥t báº¡i!");
            dialogPane.getButtonTypes().addAll(ButtonType.OK);
            dialog.setDialogPane(dialogPane);
            dialog.showAndWait();
        }
        dong();
    }

    private void dong() {
        Stage stage = (Stage) txtTenNCC.getScene().getWindow();
        stage.close();
    }

    private boolean kiemTraHopLe(NhaCungCap ncc) {
        if (ncc.getTenNCC().isEmpty()) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Lá»—i");
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContentText("TÃªn nhÃ  cung cáº¥p khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng!");
            dialogPane.getButtonTypes().addAll(ButtonType.OK);
            dialog.setDialogPane(dialogPane);
            dialog.showAndWait();
            return false;
        } else if (ncc.getSDT().isEmpty()) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Lá»—i");
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContentText("Sá»‘ Ä‘iá»‡n thoáº¡i khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng!");
            dialogPane.getButtonTypes().addAll(ButtonType.OK);
            dialog.setDialogPane(dialogPane);
            dialog.showAndWait();
            return false;
        }
        if (!ncc.getSDT().matches("0\\d{9}")) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Lá»—i");
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContentText("Sá»‘ Ä‘iá»‡n thoáº¡i khÃ´ng há»£p lá»‡! Sá»‘ Ä‘iá»‡n thoáº¡i pháº£i báº¯t Ä‘áº§u báº±ng sá»‘ 0 vÃ  cÃ³ 10 chá»¯ sá»‘.");
            dialogPane.getButtonTypes().addAll(ButtonType.OK);
            dialog.setDialogPane(dialogPane);
            dialog.showAndWait();
            return false;
        } else if (!ncc.getEmail().isEmpty() && !ncc.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Lá»—i");
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContentText("Email khÃ´ng há»£p lá»‡!");
            dialogPane.getButtonTypes().addAll(ButtonType.OK);
            dialog.setDialogPane(dialogPane);
            dialog.showAndWait();
            return false;
        } else if (!ncc.getMSThue().isEmpty() && !ncc.getMSThue().matches("\\d{10}")) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Lá»—i");
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContentText("MÃ£ sá»‘ thuáº¿ khÃ´ng há»£p lá»‡! MÃ£ sá»‘ thuáº¿ pháº£i gá»“m 10 chá»¯ sá»‘.");
            dialogPane.getButtonTypes().addAll(ButtonType.OK);
            dialog.setDialogPane(dialogPane);
            dialog.showAndWait();
            return false;
        } else {
            return true;
        }
    }
}

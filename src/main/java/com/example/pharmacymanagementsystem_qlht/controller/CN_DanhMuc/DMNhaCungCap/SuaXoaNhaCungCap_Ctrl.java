package com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import com.example.pharmacymanagementsystem_qlht.service.NhaCungCapService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

public class SuaXoaNhaCungCap_Ctrl {
    @FXML
    public TextField txtTen;
    @FXML
    public TextField txtDiaChi;
    @FXML
    public TextField txtSDT;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtGPKD_SDK;
    @FXML
    public TextField txtTenCongTy;
    @FXML
    public TextField txtMaSoThue;
    @FXML
    public TextArea txtGhiChu;
    @FXML
    public Pane NutHuy;
    @FXML
    public Pane nutThem;
    @FXML
    public Pane nutXoa;

    private NhaCungCap ncc;
    private DanhMucNhaCungCap_Ctrl danhMucNhaCungCap_ctrl;
    private final NhaCungCapService nhaCungCapService = new NhaCungCapService();

    public void initialize() {
        NutHuy.setOnMouseClicked(e -> btnHuy());
        nutThem.setOnMouseClicked(e -> CapNhatNCC());
        nutXoa.setOnMouseClicked(e -> XoaNCC());
        ThemDuLieuThuoc();
    }

    public void loadData(NhaCungCap ncc) {
        this.ncc = ncc;
    }

    public void setDanhMucNhaCungCap_ctrl(DanhMucNhaCungCap_Ctrl ctrl) {
        this.danhMucNhaCungCap_ctrl = ctrl;
    }

    private void ThemDuLieuThuoc() {
        txtTen.setText(ncc.getTenNCC());
        txtDiaChi.setText(ncc.getDiaChi());
        txtSDT.setText(ncc.getSDT());
        txtEmail.setText(ncc.getEmail());
        txtGPKD_SDK.setText(ncc.getGPKD());
        txtTenCongTy.setText(ncc.getTenCongTy());
        txtMaSoThue.setText(ncc.getMSThue());
        txtGhiChu.setText(ncc.getGhiChu());
    }

    public void CapNhatNCC() {
        Dialog<ButtonType> dialogMain = new Dialog<>();
        dialogMain.setTitle("XÃ¡c nháº­n");
        DialogPane dialogTemp = dialogMain.getDialogPane();
        dialogTemp.getButtonTypes().addAll(ButtonType.OK);
        dialogTemp.getButtonTypes().addAll(ButtonType.CANCEL);
        dialogTemp.setContentText("Báº¡n cÃ³ muá»‘n cáº­p nháº­t nhÃ  cung cáº¥p nÃ y khÃ´ng?");
        Optional<ButtonType> result = dialogMain.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ncc.setMaNCC(ncc.getMaNCC());
            ncc.setTenNCC(txtTen.getText());
            ncc.setDiaChi(txtDiaChi.getText());
            ncc.setSDT(txtSDT.getText());
            ncc.setEmail(txtEmail.getText());
            ncc.setGPKD(txtGPKD_SDK.getText());
            ncc.setTenCongTy(txtTenCongTy.getText());
            ncc.setMSThue(txtMaSoThue.getText());
            ncc.setGhiChu(txtGhiChu.getText());

            if (nhaCungCapService.update(ncc)) {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("ThÃ´ng bÃ¡o");
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);
                dialogPane.setContentText("Cáº­p nháº­t nhÃ  cung cáº¥p thÃ nh cÃ´ng!");
                dialog.showAndWait();
                if (danhMucNhaCungCap_ctrl != null) {
                    danhMucNhaCungCap_ctrl.refreshTable();
                }
                dong();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("ThÃ´ng bÃ¡o");
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);
                dialogPane.setContentText("Cáº­p nháº­t nhÃ  cung cáº¥p tháº¥t báº¡i!");
                dialog.showAndWait();
            }
        }
    }

    public void XoaNCC() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("XÃ¡c nháº­n xoÃ¡");
        alert.setHeaderText(null);
        alert.setContentText("Báº¡n cÃ³ cháº¯c muá»‘n xoÃ¡ nhÃ  cung cáº¥p nÃ y khÃ´ng?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            boolean ok = nhaCungCapService.deleteById(ncc.getMaNCC());

            if (ok) {
                new Alert(Alert.AlertType.INFORMATION, "XoÃ¡ thÃ nh cÃ´ng!", ButtonType.OK).showAndWait();
                if (danhMucNhaCungCap_ctrl != null) {
                    danhMucNhaCungCap_ctrl.refreshTable();
                }
                dong();
            } else {
                new Alert(Alert.AlertType.ERROR, "XoÃ¡ tháº¥t báº¡i!", ButtonType.OK).showAndWait();
            }
        }
    }

    public void dong() {
        Stage stage = (Stage) txtTen.getScene().getWindow();
        stage.close();
    }

    public void btnHuy() {
        dong();
    }
}

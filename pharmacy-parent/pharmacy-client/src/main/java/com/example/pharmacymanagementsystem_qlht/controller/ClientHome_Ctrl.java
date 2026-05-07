package com.example.pharmacymanagementsystem_qlht.controller;

import com.example.pharmacymanagementsystem_qlht.session.SessionContext;
import com.example.pharmacymanagementsystem_qlht.session.UserContext;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.CaiDat.caiDat_GUI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientHome_Ctrl {
    public Label lblWelcome;
    public Label lblUsername;
    public Label lblRole;
    public Button btnLogout;
    public Button btnNhanVien;
    public Button btnCaiDat;
    public Button btnDanhMucKhuyenMai;
    public Button btnCapNhatKhuyenMai;

    public void initialize() {
        UserContext currentUser = SessionContext.getCurrentUser();
        if (currentUser != null) {
            lblWelcome.setText("Xin chao, " + currentUser.getFullName());
            lblUsername.setText("Tai khoan: " + currentUser.getUsername());
            lblRole.setText("Vai tro: " + currentUser.getRole());
        } else {
            lblWelcome.setText("Chua co phien dang nhap");
            lblUsername.setText("Tai khoan: -");
            lblRole.setText("Vai tro: -");
        }

        btnLogout.setOnAction(event -> logout());
        if (btnNhanVien != null) {
            btnNhanVien.setOnAction(event -> openNhanVien());
        }
        if (btnCaiDat != null) {
            btnCaiDat.setOnAction(event -> openCaiDat());
        }
        if (btnDanhMucKhuyenMai != null) {
            btnDanhMucKhuyenMai.setOnAction(event -> openDanhMucKhuyenMai());
        }
        if (btnCapNhatKhuyenMai != null) {
            btnCapNhatKhuyenMai.setOnAction(event -> openCapNhatKhuyenMai());
        }
    }

    private void logout() {
        try {
            SessionContext.clear();
            Stage currentStage = (Stage) btnLogout.getScene().getWindow();
            Stage loginStage = new Stage();
            loginStage.setTitle("Dang nhap he thong quan ly nha thuoc");
            loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.view.DangNhap_GUI()
                    .showWithController(loginStage, new DangNhap_Ctrl());
            loginStage.show();
            currentStage.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openNhanVien() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openCaiDat() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Cai dat ung dung");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            stage.setScene(new Scene(new caiDat_GUI().createUI(), 360, 180));
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openDanhMucKhuyenMai() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhuyenMai.DanhMucKhuyenMai_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openCapNhatKhuyenMai() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_GUI()
                    .showWithController(stage, new com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_Ctrl());
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

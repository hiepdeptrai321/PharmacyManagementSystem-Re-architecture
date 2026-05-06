package com.example.pharmacymanagementsystem_qlht.controller;

import com.example.pharmacymanagementsystem_qlht.service.AuthService;
import com.example.pharmacymanagementsystem_qlht.session.LoginResult;
import com.example.pharmacymanagementsystem_qlht.session.SessionContext;
import com.example.pharmacymanagementsystem_qlht.session.UserContext;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class DangNhap_Ctrl extends Application {
    public CheckBox checkDangNhap;
    public Label lbhotline;
    public TextField tfTaiKhoan;
    public TextField tfMatKhau;
    public Button btnAnMK;
    public PasswordField tfMatKhauAn;
    public Button btnDangNhap;
    public DangNhap_Ctrl instance;

    private final Preferences prefs = Preferences.userNodeForPackage(DangNhap_Ctrl.class);
    private final AuthService authService = new AuthService();

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);
        Application.setUserAgentStylesheet(null);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        new com.example.pharmacymanagementsystem_qlht.view.DangNhap_GUI()
                .showWithController(stage, this);
    }

    public void initialize() {
        instance = this;
        tfMatKhauAn.textProperty().addListener((obs, oldText, newText) -> {
            if (!tfMatKhau.isVisible()) {
                return;
            }
            tfMatKhau.setText(newText);
        });
        tfMatKhau.textProperty().addListener((obs, oldText, newText) -> {
            if (!tfMatKhau.isVisible()) {
                return;
            }
            tfMatKhauAn.setText(newText);
        });

        String savedUser = prefs.get("username", "");
        String savedPass = prefs.get("password", "");
        if (!savedUser.isEmpty() && !savedPass.isEmpty()) {
            tfTaiKhoan.setText(savedUser);
            tfMatKhauAn.setText(savedPass);
            checkDangNhap.setSelected(true);
        }

        btnAnMK.setOnAction(e -> anmatkhau());
        btnDangNhap.setOnAction(e -> btnDangNhapClick());
    }

    public void anmatkhau() {
        boolean isVisible = tfMatKhau.isVisible();
        if (isVisible) {
            tfMatKhauAn.setText(tfMatKhau.getText());
            tfMatKhauAn.setVisible(true);
            tfMatKhau.setVisible(false);
            btnAnMK.setText("\uD83D\uDC41\uFE0F\u200D\uD83D\uDDE8\uFE0F");
        } else {
            tfMatKhau.setText(tfMatKhauAn.getText());
            tfMatKhau.setVisible(true);
            tfMatKhauAn.setVisible(false);
            btnAnMK.setText("ðŸ‘");
        }
    }

    public void btnDangNhapClick() {
        String username = tfTaiKhoan.getText();
        String password = tfMatKhau.isVisible() ? tfMatKhau.getText() : tfMatKhauAn.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Vui lòng nhập đầy đủ tài khoản và mật khẩu.");
            return;
        }

        LoginResult loginResult = authService.login(username, password);
        if (!loginResult.isSuccess()) {
            SessionContext.clear();
            showAlert(loginResult.getMessage());
            return;
        }

        UserContext currentUser = loginResult.getUserContext();
        SessionContext.setCurrentUser(currentUser);

        if (checkDangNhap.isSelected()) {
            prefs.put("username", username);
            prefs.put("password", password);
        } else {
            prefs.remove("username");
            prefs.remove("password");
        }

        String role = currentUser.getRole();

        try {
            Stage loginStage = (Stage) btnDangNhap.getScene().getWindow();
            Stage mainStage = new Stage();
            mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));

            if ("Quản lý".equals(role)) {
                CuaSoChinh_QuanLy_Ctrl ctrl = new CuaSoChinh_QuanLy_Ctrl();
                new com.example.pharmacymanagementsystem_qlht.view.CuaSoChinh_QuanLy_GUI()
                        .showWithController(mainStage, ctrl);
            } else {
                CuaSoChinh_NhanVien_Ctrl ctrl = new CuaSoChinh_NhanVien_Ctrl();
                new com.example.pharmacymanagementsystem_qlht.view.CuaSoChinh_NhanVien_GUI()
                        .showWithController(mainStage, ctrl);
            }

            mainStage.show();
            loginStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Có lỗi khi mở cửa sổ chính.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}

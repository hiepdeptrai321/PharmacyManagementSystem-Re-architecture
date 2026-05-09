package com.example.pharmacy.client.controller;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.AuthClientService;
import com.example.pharmacy.client.service.RmiAuthClientService;
import com.example.pharmacy.common.response.LoginResponse;
import com.example.pharmacy.client.session.SessionContext;
import com.example.pharmacy.common.session.UserContext;
import com.example.pharmacy.client.session.UserContextMapper;
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

    private final Preferences prefs = Preferences.userNodeForPackage(DangNhap_Ctrl.class);
    private final AuthClientService authClientService =
            new RmiAuthClientService(new RmiClientProvider(), new com.example.pharmacy.client.session.RmiSessionContext());

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacy/client/img/logoNguyenBan.png")));
        new com.example.pharmacy.client.view.DangNhap_GUI().showWithController(stage, this);
    }

    public void initialize() {
        tfMatKhauAn.textProperty().addListener((obs, oldText, newText) -> {
            if (tfMatKhau.isVisible()) {
                tfMatKhau.setText(newText);
            }
        });
        tfMatKhau.textProperty().addListener((obs, oldText, newText) -> {
            if (tfMatKhau.isVisible()) {
                tfMatKhauAn.setText(newText);
            }
        });

        String savedUser = prefs.get("username", "");
        String savedPass = prefs.get("password", "");
        if (!savedUser.isEmpty() && !savedPass.isEmpty()) {
            tfTaiKhoan.setText(savedUser);
            tfMatKhauAn.setText(savedPass);
            checkDangNhap.setSelected(true);
        }

        btnAnMK.setOnAction(event -> togglePasswordVisibility());
        btnDangNhap.setOnAction(event -> btnDangNhapClick());
    }

    private void togglePasswordVisibility() {
        boolean showingPlainText = tfMatKhau.isVisible();
        if (showingPlainText) {
            tfMatKhauAn.setText(tfMatKhau.getText());
            tfMatKhauAn.setVisible(true);
            tfMatKhau.setVisible(false);
            btnAnMK.setText("Show");
        } else {
            tfMatKhau.setText(tfMatKhauAn.getText());
            tfMatKhau.setVisible(true);
            tfMatKhauAn.setVisible(false);
            btnAnMK.setText("Hide");
        }
    }

    public void btnDangNhapClick() {
        String username = tfTaiKhoan.getText();
        String password = tfMatKhau.isVisible() ? tfMatKhau.getText() : tfMatKhauAn.getText();

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            showAlert("Vui long nhap day du tai khoan va mat khau.");
            return;
        }

        LoginResponse loginResponse = authClientService.login(username, password);
        if (loginResponse == null || !loginResponse.isSuccess() || loginResponse.getUser() == null) {
            SessionContext.clear();
            showAlert(loginResponse == null || loginResponse.getMessage() == null || loginResponse.getMessage().isBlank()
                    ? "Dang nhap that bai."
                    : loginResponse.getMessage());
            return;
        }

        UserContext currentUser = UserContextMapper.toUserContext(loginResponse.getUser());
        SessionContext.setCurrentUser(currentUser);

        if (checkDangNhap.isSelected()) {
            prefs.put("username", username);
            prefs.put("password", password);
        } else {
            prefs.remove("username");
            prefs.remove("password");
        }

        try {
            Stage loginStage = (Stage) btnDangNhap.getScene().getWindow();
            Stage mainStage = new Stage();
            mainStage.setTitle("Pharmacy Client");
            mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacy/client/img/logoNguyenBan.png")));
            new com.example.pharmacy.client.view.CuaSoChinh_QuanLy_GUI()
                    .showWithController(mainStage, new CuaSoChinh_QuanLy_Ctrl());
            mainStage.show();
            loginStage.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            showAlert("Co loi khi mo man hinh chinh client.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}

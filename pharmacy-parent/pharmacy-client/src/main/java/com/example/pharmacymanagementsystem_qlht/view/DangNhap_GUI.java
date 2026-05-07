package com.example.pharmacymanagementsystem_qlht.view;

import com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class DangNhap_GUI {

    public void showWithController(Stage stage, DangNhap_Ctrl ctrl) {
        VBox root = new VBox(12);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(24));

        ImageView logo = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/LogoDung.png")
        ).toExternalForm()));
        logo.setFitWidth(140);
        logo.setFitHeight(140);
        logo.setPreserveRatio(true);

        Label lbTaiKhoan = new Label("Tai khoan");
        TextField tfTaiKhoan = new TextField();
        tfTaiKhoan.setPromptText("Nhap tai khoan");
        tfTaiKhoan.setMaxWidth(280);

        Label lbMatKhau = new Label("Mat khau");
        PasswordField tfMatKhauAn = new PasswordField();
        tfMatKhauAn.setPromptText("Nhap mat khau");
        tfMatKhauAn.setMaxWidth(280);

        TextField tfMatKhau = new TextField();
        tfMatKhau.setPromptText("Nhap mat khau");
        tfMatKhau.setMaxWidth(280);
        tfMatKhau.setVisible(false);
        tfMatKhau.setManaged(false);

        Button btnAnMK = new Button("Show");
        Button btnDangNhap = new Button("Dang nhap");
        CheckBox checkDangNhap = new CheckBox("Ghi nho dang nhap");
        Label lbhotline = new Label("Hotline: 1800 6868");

        root.getChildren().addAll(
                logo,
                lbTaiKhoan,
                tfTaiKhoan,
                lbMatKhau,
                tfMatKhauAn,
                tfMatKhau,
                btnAnMK,
                checkDangNhap,
                btnDangNhap,
                lbhotline
        );

        ctrl.tfTaiKhoan = tfTaiKhoan;
        ctrl.tfMatKhauAn = tfMatKhauAn;
        ctrl.tfMatKhau = tfMatKhau;
        ctrl.btnAnMK = btnAnMK;
        ctrl.checkDangNhap = checkDangNhap;
        ctrl.btnDangNhap = btnDangNhap;
        ctrl.lbhotline = lbhotline;
        ctrl.initialize();

        Scene scene = new Scene(root, 460, 560);
        String css = "/com/example/pharmacymanagementsystem_qlht/css/DangNhap.css";
        if (getClass().getResource(css) != null) {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(css)).toExternalForm());
        }

        stage.setTitle("Dang nhap");
        stage.setScene(scene);
    }
}

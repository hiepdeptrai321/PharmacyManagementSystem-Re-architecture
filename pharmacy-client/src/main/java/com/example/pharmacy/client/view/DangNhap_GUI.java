package com.example.pharmacy.client.view;

import com.example.pharmacy.client.controller.DangNhap_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class DangNhap_GUI {

    public void showWithController(Stage stage, DangNhap_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(488, 649);
        root.setStyle("-fx-font-size: 14 px;");

        TextField tfTaiKhoan = new TextField();
        tfTaiKhoan.setId("tfTaiKhoan");
        tfTaiKhoan.setLayoutX(93);
        tfTaiKhoan.setLayoutY(263);
        tfTaiKhoan.setPrefSize(318, 26);

        PasswordField tfMatKhauAn = new PasswordField();
        tfMatKhauAn.setId("tfMatKhauAn");
        tfMatKhauAn.setLayoutX(93);
        tfMatKhauAn.setLayoutY(327);
        tfMatKhauAn.setPrefSize(318, 26);

        TextField tfMatKhau = new TextField();
        tfMatKhau.setId("tfMatKhau");
        tfMatKhau.setLayoutX(93);
        tfMatKhau.setLayoutY(327);
        tfMatKhau.setPrefSize(318, 26);
        tfMatKhau.setVisible(false);

        Button btnAnMK = new Button("👁️‍🗨️");
        btnAnMK.setId("btnAnMK");
        btnAnMK.setLayoutX(373);
        btnAnMK.setLayoutY(325);
        btnAnMK.setPrefSize(38, 27);
        btnAnMK.setMinWidth(37);
        btnAnMK.setStyle("-fx-background-color: transparent;");

        CheckBox checkDangNhap = new CheckBox("Ghi nhớ đăng nhập");
        checkDangNhap.setId("checkDangNhap");
        checkDangNhap.setLayoutX(93);
        checkDangNhap.setLayoutY(365);
        checkDangNhap.setPrefSize(165, 18);

        Button btnDangNhap = new Button("Đăng nhập");
        btnDangNhap.setId("btnDangNhap");
        btnDangNhap.setLayoutX(168);
        btnDangNhap.setLayoutY(406);
        btnDangNhap.setPrefSize(165, 38);
        btnDangNhap.setStyle("-fx-text-fill: white; -fx-background-color: #007bff;");

        Label lbTaiKhoan = new Label("Tài khoản");
        lbTaiKhoan.setLayoutX(93);
        lbTaiKhoan.setLayoutY(243);
        lbTaiKhoan.setStyle("-fx-text-fill: #676767;");

        Label lbMatKhau = new Label("Mật khẩu");
        lbMatKhau.setLayoutX(93);
        lbMatKhau.setLayoutY(307);
        lbMatKhau.setStyle("-fx-text-fill: #676767;");

        Label lbhotline = new Label("Hotline: 1800 6868 ");
        lbhotline.setId("lbhotline");
        lbhotline.setLayoutX(291);
        lbhotline.setLayoutY(365);

        Label lbFooter = new Label("Nhóm 2_DHKTPM19BTT_Khoa Công nghệ thông tin_Trường DHCN TPHCM");
        lbFooter.setLayoutX(65);
        lbFooter.setLayoutY(627);
        lbFooter.setStyle("-fx-font-size: 11;");

        ImageView logo = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/com/example/pharmacy/client/img/LogoDung.png")).toExternalForm())
        );
        logo.setFitWidth(177);
        logo.setFitHeight(195);
        logo.setLayoutX(160);
        logo.setLayoutY(58);
        logo.setPreserveRatio(true);
        logo.setPickOnBounds(true);

        root.getChildren().addAll(tfTaiKhoan, tfMatKhauAn, tfMatKhau, btnAnMK, checkDangNhap,
                btnDangNhap, lbTaiKhoan, lbMatKhau, lbhotline, lbFooter, logo);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacy/client/css/DangNhap.css")).toExternalForm());

        ctrl.tfTaiKhoan = tfTaiKhoan;
        ctrl.tfMatKhauAn = tfMatKhauAn;
        ctrl.tfMatKhau = tfMatKhau;
        ctrl.btnAnMK = btnAnMK;
        ctrl.checkDangNhap = checkDangNhap;
        ctrl.btnDangNhap = btnDangNhap;
        ctrl.lbhotline = lbhotline;

        ctrl.initialize();

        stage.setTitle("Đăng nhập");
        stage.setScene(scene);
    }
}

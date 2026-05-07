package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.ThemTaiKhoan_Ctrl;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ThemTaiKhoan_GUI {
    private TextField txtTaiKhoan;
    private PasswordField txtMatKhau;
    private Button btnLuu;
    private Button btnHuy;

    public void showWithController(Stage dialog, ThemTaiKhoan_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(407, 171);

        addStylesheetSafely(root, "/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhanVien.css");

        Label lbTitle = new Label("Them tai khoan");
        lbTitle.setLayoutX(157);
        lbTitle.setLayoutY(7);
        lbTitle.setPrefSize(180, 26);
        lbTitle.setTextFill(javafx.scene.paint.Color.web("#0623ff"));
        lbTitle.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 17));
        lbTitle.setPadding(new Insets(0, 0, 10, 0));

        ImageView iv = new ImageView();
        iv.setFitWidth(31);
        iv.setFitHeight(46);
        iv.setLayoutX(117);
        iv.setLayoutY(5);
        iv.setPreserveRatio(true);
        setImageSafely(iv, "/com/example/pharmacymanagementsystem_qlht/img/free-icon-user-login-5867.png");

        VBox vbox = new VBox(10);
        vbox.setLayoutX(25);
        vbox.setLayoutY(43);
        vbox.setPrefSize(365, 98);

        Label lbTK = new Label("Tai khoan");
        lbTK.setPrefSize(100, 30);
        lbTK.setTextFill(javafx.scene.paint.Color.web("#005711"));
        lbTK.setFont(Font.font(15));
        txtTaiKhoan = new TextField();
        txtTaiKhoan.setPrefSize(287, 30);
        HBox rowTK = new HBox(lbTK, txtTaiKhoan);

        Label lbMK = new Label("Mat khau");
        lbMK.setPrefSize(99, 30);
        lbMK.setTextFill(javafx.scene.paint.Color.web("#005711"));
        lbMK.setFont(Font.font(15));
        txtMatKhau = new PasswordField();
        txtMatKhau.setPrefSize(289, 31);
        HBox rowMK = new HBox(lbMK, txtMatKhau);

        vbox.getChildren().addAll(rowTK, rowMK);

        btnLuu = new Button("Luu");
        btnLuu.setLayoutX(330);
        btnLuu.setLayoutY(129);
        btnLuu.setPrefSize(60, 25);

        btnHuy = new Button("Huy");
        btnHuy.setLayoutX(257);
        btnHuy.setLayoutY(129);
        btnHuy.setPrefSize(60, 25);

        root.getChildren().addAll(lbTitle, iv, vbox, btnHuy, btnLuu);

        ctrl.txtTaiKhoan = txtTaiKhoan;
        ctrl.txtMatKhau = txtMatKhau;
        btnLuu.setOnAction(ctrl::btnLuu);
        btnHuy.setOnAction(ctrl::btnHuy);
        ctrl.initialize();

        Scene scene = new Scene(root, 407, 171);
        dialog.setScene(scene);
    }

    private void addStylesheetSafely(AnchorPane root, String abs) {
        var resource = getClass().getResource(abs);
        if (resource != null) {
            root.getStylesheets().add(resource.toExternalForm());
        }
    }

    private void setImageSafely(ImageView view, String abs) {
        var resource = getClass().getResource(abs);
        if (resource != null) {
            view.setImage(new Image(resource.toExternalForm()));
        }
    }
}

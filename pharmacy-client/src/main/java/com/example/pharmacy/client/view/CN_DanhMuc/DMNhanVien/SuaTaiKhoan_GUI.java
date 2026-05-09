package com.example.pharmacy.client.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacy.client.controller.CN_DanhMuc.DMNhanVien.SuaTaiKhoan_Ctrl;
import com.example.pharmacy.common.model.NhanVien;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class SuaTaiKhoan_GUI {
    public TextField txtTaiKhoan;
    public TextField txtMatKhau;
    public Button btnLuu;
    public Button btnHuy;

    public void showWithController(Stage dialog, SuaTaiKhoan_Ctrl ctrl, NhanVien nhanVien) {
        AnchorPane root = buildUI();

        ctrl.txtTaiKhoan = txtTaiKhoan;
        ctrl.txtMatKhau = txtMatKhau;
        ctrl.loadTaiKhoan(nhanVien);

        btnLuu.setOnAction(ctrl::btnLuu);
        btnHuy.setOnAction(ctrl::btnHuy);

        Scene scene = new Scene(root, 407, 182);
        scene.getStylesheets().add(requireCss("/com/example/pharmacy/client/css/SuaXoaNhanVien.css"));
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setScene(scene);
        dialog.setTitle("Sua tai khoan");
        dialog.showAndWait();
    }

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(407, 182);

        Label lbTitle = new Label("Sua tai khoan");
        lbTitle.setTextFill(javafx.scene.paint.Color.web("#0623ff"));
        lbTitle.setFont(Font.font("System Bold", 17));
        AnchorPane.setLeftAnchor(lbTitle, 157.0);
        AnchorPane.setTopAnchor(lbTitle, 10.0);

        ImageView iv = new ImageView(new Image(requireRes("/com/example/pharmacy/client/img/free-icon-user-login-5867.png")));
        iv.setFitHeight(31);
        iv.setFitWidth(31);
        AnchorPane.setLeftAnchor(iv, 117.0);
        AnchorPane.setTopAnchor(iv, 8.0);

        VBox vbox = new VBox();
        vbox.setLayoutX(25);
        vbox.setLayoutY(46);
        vbox.setPrefSize(365, 81);

        HBox rowTK = new HBox();
        rowTK.setSpacing(8);
        Label lbTK = new Label("Tai khoan:");
        lbTK.setPrefSize(89, 26);
        lbTK.setTextFill(javafx.scene.paint.Color.web("#005711"));
        lbTK.setFont(Font.font(15));
        txtTaiKhoan = new TextField();
        txtTaiKhoan.setPrefSize(303, 25);
        rowTK.getChildren().addAll(lbTK, txtTaiKhoan);
        VBox.setMargin(rowTK, new Insets(0, 0, 10, 0));

        HBox rowMK = new HBox();
        rowMK.setSpacing(8);
        Label lbMK = new Label("Mat khau:");
        lbMK.setPrefSize(99, 25);
        lbMK.setTextFill(javafx.scene.paint.Color.web("#005711"));
        lbMK.setFont(Font.font(15));
        txtMatKhau = new TextField();
        txtMatKhau.setPrefSize(312, 25);
        rowMK.getChildren().addAll(lbMK, txtMatKhau);
        VBox.setMargin(rowMK, new Insets(0, 0, 10, 0));

        vbox.getChildren().addAll(rowTK, rowMK);

        btnLuu = new Button("Luu");
        btnLuu.setPrefSize(60, 25);
        AnchorPane.setLeftAnchor(btnLuu, 330.0);
        AnchorPane.setTopAnchor(btnLuu, 141.0);

        btnHuy = new Button("Huy");
        btnHuy.setPrefSize(60, 25);
        AnchorPane.setLeftAnchor(btnHuy, 260.0);
        AnchorPane.setTopAnchor(btnHuy, 141.0);

        root.getChildren().addAll(lbTitle, vbox, iv, btnLuu, btnHuy);
        return root;
    }

    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Khong tim thay CSS: " + path).toExternalForm();
    }

    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Khong tim thay resource: " + path).toExternalForm();
    }
}

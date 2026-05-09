package com.example.pharmacy.client.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacy.client.controller.CN_DanhMuc.DMNhanVien.ThemNhanVien_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class ThemNhanVien_GUI {
    public TextField txtTenNV;
    public TextField txtSDT;
    public TextField txtEmail;
    public TextField txtDiaChi;
    public ComboBox<String> cbxGioiTinh;
    public DatePicker txtNgaySinh;
    public Button btnThem;
    public Button btnThemTaiKhoan;
    public Button btnHuy;

    public void showWithController(Stage dialog, ThemNhanVien_Ctrl ctrl) {
        AnchorPane root = buildUI();

        ctrl.txtTenNV = txtTenNV;
        ctrl.txtSDT = txtSDT;
        ctrl.txtEmail = txtEmail;
        ctrl.txtDiaChi = txtDiaChi;
        ctrl.cbxGioiTinh = cbxGioiTinh;
        ctrl.txtNgaySinh = txtNgaySinh;

        btnThem.setOnAction(ctrl::btnThem);
        btnThemTaiKhoan.setOnAction(ctrl::btnThemTaiKhoan);
        btnHuy.setOnAction(ctrl::btnHuy);
        ctrl.initialize();

        Scene scene = new Scene(root, 456, 273);
        scene.getStylesheets().add(requireCss("/com/example/pharmacy/client/css/SuaXoaNhanVien.css"));

        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(456, 273);

        Label lbTitle = new Label("Them nhan vien");
        lbTitle.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 24));
        AnchorPane.setLeftAnchor(lbTitle, 14.0);
        AnchorPane.setTopAnchor(lbTitle, 8.0);

        ImageView imageView = new ImageView(new Image(requireRes("/com/example/pharmacy/client/img/free-icon-person-and-gear-9847.png")));
        imageView.setFitWidth(41);
        imageView.setFitHeight(49);
        imageView.setPreserveRatio(true);
        AnchorPane.setLeftAnchor(imageView, 200.0);
        AnchorPane.setTopAnchor(imageView, -2.0);

        Separator sep = new Separator();
        sep.setPrefSize(455, 3);
        AnchorPane.setLeftAnchor(sep, 0.0);
        AnchorPane.setTopAnchor(sep, 38.0);

        VBox vboxRoot = new VBox();
        vboxRoot.setPrefSize(431, 178);
        AnchorPane.setLeftAnchor(vboxRoot, 14.0);
        AnchorPane.setTopAnchor(vboxRoot, 36.0);

        VBox vboxInner = new VBox();
        Pane pane = new Pane();
        pane.setPrefSize(632, 178);

        Label lbTen = new Label("Tên nhân viên");
        lbTen.setLayoutX(3);
        lbTen.setLayoutY(15);
        txtTenNV = new TextField();
        txtTenNV.setLayoutX(3);
        txtTenNV.setLayoutY(38);
        txtTenNV.setPrefSize(194, 25);

        Label lbNgaySinh = new Label("Ngay sinh:");
        lbNgaySinh.setLayoutX(207);
        lbNgaySinh.setLayoutY(15);
        txtNgaySinh = new DatePicker();
        txtNgaySinh.setLayoutX(206);
        txtNgaySinh.setLayoutY(38);
        txtNgaySinh.setPrefSize(223, 25);

        Label lbSDT = new Label("So dien thoai:");
        lbSDT.setLayoutX(3);
        lbSDT.setLayoutY(70);
        txtSDT = new TextField();
        txtSDT.setLayoutX(3);
        txtSDT.setLayoutY(94);
        txtSDT.setPrefSize(194, 25);

        Label lbEmail = new Label("Email:");
        lbEmail.setLayoutX(205);
        lbEmail.setLayoutY(69);
        txtEmail = new TextField();
        txtEmail.setLayoutX(206);
        txtEmail.setLayoutY(95);
        txtEmail.setPrefSize(223, 25);

        Label lbDiaChi = new Label("Dia chi");
        lbDiaChi.setLayoutX(4);
        lbDiaChi.setLayoutY(129);
        txtDiaChi = new TextField();
        txtDiaChi.setLayoutX(4);
        txtDiaChi.setLayoutY(152);
        txtDiaChi.setPrefSize(267, 30);

        Label lbGioiTinh = new Label("Gioi tinh:");
        lbGioiTinh.setLayoutX(278);
        lbGioiTinh.setLayoutY(129);
        cbxGioiTinh = new ComboBox<>();
        cbxGioiTinh.setLayoutX(278);
        cbxGioiTinh.setLayoutY(152);
        cbxGioiTinh.setPrefSize(150, 30);

        pane.getChildren().addAll(
                lbTen, txtTenNV, lbNgaySinh, txtNgaySinh, lbSDT, txtSDT, lbEmail, txtEmail, lbDiaChi, txtDiaChi, lbGioiTinh, cbxGioiTinh
        );
        vboxInner.getChildren().add(pane);
        vboxRoot.getChildren().add(vboxInner);

        btnThemTaiKhoan = new Button("Them tai khoan");
        btnThemTaiKhoan.setPrefSize(131, 30);
        AnchorPane.setLeftAnchor(btnThemTaiKhoan, 168.0);
        AnchorPane.setTopAnchor(btnThemTaiKhoan, 229.0);
        btnThemTaiKhoan.setStyle("-fx-background-color: rgb(60,164,248);");

        btnHuy = new Button("Huy");
        btnHuy.setPrefSize(55, 25);
        AnchorPane.setLeftAnchor(btnHuy, 306.0);
        AnchorPane.setTopAnchor(btnHuy, 229.0);
        btnHuy.setStyle("-fx-background-color: rgb(246,72,72);");

        btnThem = new Button("Them");
        btnThem.setPrefSize(73, 25);
        AnchorPane.setLeftAnchor(btnThem, 368.0);
        AnchorPane.setTopAnchor(btnThem, 229.0);
        btnThem.setStyle("-fx-background-color: rgb(122,239,102);");

        root.getChildren().addAll(lbTitle, sep, imageView, vboxRoot, btnThemTaiKhoan, btnHuy, btnThem);
        return root;
    }

    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Khong tim thay CSS: " + path).toExternalForm();
    }

    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Khong tim thay resource: " + path).toExternalForm();
    }
}

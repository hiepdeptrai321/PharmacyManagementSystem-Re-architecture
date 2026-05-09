package com.example.pharmacy.client.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacy.client.controller.CN_DanhMuc.DMNhanVien.ThietLapLuongNV_Ctrl;
import com.example.pharmacy.common.model.LuongNhanVien;
import com.example.pharmacy.common.model.NhanVien;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ThietLapLuongNV_GUI {
    public TextField txtMaNV;
    public TextField txtTenNV;
    public TextField txtLuongHT;
    public TextField txtPhuCapHienTai;
    public TextField txtLuongMoi;
    public TextField txtPhuCapMoi;
    public TextArea txtGhiChu;
    public Button btnLuu;
    public Button btnHuy;

    public void showWithController(Stage stage,
                                   ThietLapLuongNV_Ctrl ctrl,
                                   NhanVien nhanVien,
                                   LuongNhanVien luongHienHanh) {
        AnchorPane root = buildUI();

        ctrl.txtMaNV = txtMaNV;
        ctrl.txtTenNV = txtTenNV;
        ctrl.txtLuongHT = txtLuongHT;
        ctrl.txtPhuCapHienTai = txtPhuCapHienTai;
        ctrl.txtLuongMoi = txtLuongMoi;
        ctrl.txtPhuCapMoi = txtPhuCapMoi;
        ctrl.txtGhiChu = txtGhiChu;

        btnLuu.setOnAction(ctrl::btnLuu);
        btnHuy.setOnAction(ctrl::btnHuy);
        ctrl.initialize(nhanVien, luongHienHanh);

        Scene scene = new Scene(root, 407, 446);
        scene.getStylesheets().add(requireCss("/com/example/pharmacy/client/css/SuaXoaNhanVien.css"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(407, 446);

        Label lbTitle = new Label("Thiet lap luong nhan vien");
        lbTitle.setLayoutX(21);
        lbTitle.setLayoutY(18);
        lbTitle.setPrefSize(300, 26);
        lbTitle.setTextFill(javafx.scene.paint.Paint.valueOf("#0623ff"));
        lbTitle.setFont(Font.font("System Bold", 17));

        ImageView iv = new ImageView(new Image(requireRes("/com/example/pharmacy/client/img/dvt2.png")));
        iv.setFitHeight(46);
        iv.setFitWidth(31);
        iv.setLayoutX(330);
        iv.setLayoutY(16);
        iv.setPreserveRatio(true);

        VBox box = new VBox(10);
        box.setLayoutX(25);
        box.setLayoutY(56);
        box.setPrefSize(354, 332);

        HBox rowMa = new HBox(10);
        Label lbMa = labelLeft("Ma nhan vien:", 150);
        txtMaNV = new TextField();
        txtMaNV.setPrefSize(262, 30);
        txtMaNV.setEditable(false);
        txtMaNV.setDisable(true);
        rowMa.getChildren().addAll(lbMa, txtMaNV);

        HBox rowTen = new HBox(10);
        Label lbTen = labelLeft("Ten nhan vien:", 143);
        txtTenNV = new TextField();
        txtTenNV.setPrefSize(252, 30);
        txtTenNV.setEditable(false);
        rowTen.getChildren().addAll(lbTen, txtTenNV);

        HBox rowLHT = new HBox(10);
        Label lbLHT = labelLeft("Luong hien tai:", 132);
        txtLuongHT = new TextField();
        txtLuongHT.setPrefSize(241, 30);
        txtLuongHT.setEditable(false);
        txtLuongHT.setDisable(true);
        rowLHT.getChildren().addAll(lbLHT, txtLuongHT);

        HBox rowPCHT = new HBox(10);
        Label lbPCHT = labelLeft("Phu cap hien tai:", 124);
        txtPhuCapHienTai = new TextField();
        txtPhuCapHienTai.setPrefSize(230, 30);
        txtPhuCapHienTai.setEditable(false);
        rowPCHT.getChildren().addAll(lbPCHT, txtPhuCapHienTai);

        HBox rowLM = new HBox(10);
        Label lbLM = labelLeft("Luong moi:", 124);
        txtLuongMoi = new TextField();
        txtLuongMoi.setPrefSize(229, 30);
        rowLM.getChildren().addAll(lbLM, txtLuongMoi);

        HBox rowPCM = new HBox(10);
        Label lbPCM = labelLeft("Phu cap moi:", 124);
        txtPhuCapMoi = new TextField();
        txtPhuCapMoi.setPrefSize(229, 30);
        rowPCM.getChildren().addAll(lbPCM, txtPhuCapMoi);

        HBox rowGC = new HBox(10);
        Label lbGC = labelLeft("Ghi chu:", 124);
        txtGhiChu = new TextArea();
        txtGhiChu.setPrefSize(231, 55);
        rowGC.getChildren().addAll(lbGC, txtGhiChu);

        box.getChildren().addAll(rowMa, rowTen, rowLHT, rowPCHT, rowLM, rowPCM, rowGC);

        btnLuu = new Button("Luu");
        btnLuu.setLayoutX(319);
        btnLuu.setLayoutY(396);
        btnLuu.setPrefSize(60, 25);

        btnHuy = new Button("Huy");
        btnHuy.setLayoutX(253);
        btnHuy.setLayoutY(397);
        btnHuy.setPrefSize(60, 25);

        root.getChildren().addAll(lbTitle, iv, box, btnLuu, btnHuy);
        return root;
    }

    private Label labelLeft(String text, double prefWidth) {
        Label label = new Label(text);
        label.setPrefWidth(prefWidth);
        label.setPrefHeight(30);
        label.setTextFill(javafx.scene.paint.Paint.valueOf("#005711"));
        label.setFont(Font.font(15));
        return label;
    }

    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Khong tim thay CSS: " + path).toExternalForm();
    }

    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Khong tim thay resource: " + path).toExternalForm();
    }
}

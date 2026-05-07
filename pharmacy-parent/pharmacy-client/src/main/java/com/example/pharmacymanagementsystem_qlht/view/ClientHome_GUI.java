package com.example.pharmacymanagementsystem_qlht.view;

import com.example.pharmacymanagementsystem_qlht.controller.ClientHome_Ctrl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ClientHome_GUI {

    public void showWithController(Stage stage, ClientHome_Ctrl ctrl) {
        VBox root = new VBox(16);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(32));

        ImageView logo = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/LogoDung.png")
        ).toExternalForm()));
        logo.setFitWidth(140);
        logo.setFitHeight(140);
        logo.setPreserveRatio(true);

        Label lblWelcome = new Label("Xin chao");
        lblWelcome.setFont(Font.font(24));

        Label lblUsername = new Label("Tai khoan: -");
        Label lblRole = new Label("Vai tro: -");
        Label lblNote = new Label("Client multi-module da duoc migrate. Cac man hinh legacy con lai se duoc tach tiep theo tung dot.");
        lblNote.setWrapText(true);
        lblNote.setMaxWidth(420);

        Button btnNhanVien = new Button("Danh muc nhan vien");
        Button btnDanhMucKhuyenMai = new Button("Danh muc khuyen mai");
        Button btnCapNhatKhuyenMai = new Button("Cap nhat khuyen mai");
        Button btnCaiDat = new Button("Cai dat");
        Button btnLogout = new Button("Dang xuat");

        root.getChildren().addAll(
                logo,
                lblWelcome,
                lblUsername,
                lblRole,
                lblNote,
                btnNhanVien,
                btnDanhMucKhuyenMai,
                btnCapNhatKhuyenMai,
                btnCaiDat,
                btnLogout
        );

        ctrl.lblWelcome = lblWelcome;
        ctrl.lblUsername = lblUsername;
        ctrl.lblRole = lblRole;
        ctrl.btnNhanVien = btnNhanVien;
        ctrl.btnDanhMucKhuyenMai = btnDanhMucKhuyenMai;
        ctrl.btnCapNhatKhuyenMai = btnCapNhatKhuyenMai;
        ctrl.btnCaiDat = btnCaiDat;
        ctrl.btnLogout = btnLogout;
        ctrl.initialize();

        stage.setScene(new Scene(root, 560, 500));
    }
}

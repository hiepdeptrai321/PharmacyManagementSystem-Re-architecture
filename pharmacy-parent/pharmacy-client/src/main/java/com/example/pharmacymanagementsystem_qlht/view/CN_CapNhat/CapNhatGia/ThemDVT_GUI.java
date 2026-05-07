package com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatGia;

import com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatGia.ThemDVT_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Objects;

public class ThemDVT_GUI {

    public void showWithController(Stage stage, ThemDVT_Ctrl ctrl) {
        Pane root = new Pane();
        root.setPrefSize(370, 251);
        root.setStyle("-fx-font-size: 13;");

        Label lbTitle = new Label("Thêm đơn vị tính");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(8);
        lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(352, 27);
        lbTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextField txtTenDVT = new TextField();
        txtTenDVT.setId("txtTenDVT");
        txtTenDVT.setLayoutX(14);
        txtTenDVT.setLayoutY(66);
        txtTenDVT.setPrefSize(336, 39);

        Label lbTen = new Label("Tên đơn vị tính");
        lbTen.setLayoutX(13.5);
        lbTen.setLayoutY(49);

        // empty label placeholder from FXML
        Label lbEmpty = new Label();
        lbEmpty.setLayoutX(294);
        lbEmpty.setLayoutY(49);

        Label lbKyHieu = new Label("Ký hiệu");
        lbKyHieu.setLayoutX(14);
        lbKyHieu.setLayoutY(116);

        TextField txtKyHieu = new TextField();
        txtKyHieu.setId("txtKyHieu");
        txtKyHieu.setLayoutX(14);
        txtKyHieu.setLayoutY(135);
        txtKyHieu.setPrefSize(336, 39);

        Button btnThem = new Button();
        btnThem.setId("btnThem");
        btnThem.setLayoutX(270);
        btnThem.setLayoutY(202);
        btnThem.setPrefSize(74, 25);
        Label lblBtnThemGraphic = new Label("Thêm");
        btnThem.setGraphic(lblBtnThemGraphic);

        Button btnHuy = new Button();
        btnHuy.setId("btnHuy");
        btnHuy.setLayoutX(25);
        btnHuy.setLayoutY(202);
        btnHuy.setPrefSize(63, 25);
        Label lblBtnHuyGraphic = new Label("Hủy");
        btnHuy.setGraphic(lblBtnHuyGraphic);

        root.getChildren().addAll(lbTitle, txtTenDVT, lbTen, lbEmpty, lbKyHieu, txtKyHieu, btnThem, btnHuy);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhaCungCap.css")).toExternalForm());

        // Inject controls into controller
        ctrl.txtTenDVT = txtTenDVT;
        ctrl.txtKyHieu = txtKyHieu;
        ctrl.btnThem = btnThem;
        ctrl.btnHuy = btnHuy;

        // initialize controller logic
        ctrl.initialize();

        stage.setTitle("Thêm đơn vị tính");
        stage.setScene(scene);
        stage.show();
    }
}

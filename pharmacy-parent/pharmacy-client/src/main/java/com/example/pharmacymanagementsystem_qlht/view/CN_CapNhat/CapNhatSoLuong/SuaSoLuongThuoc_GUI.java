package com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatSoLuong;

import com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatSoLuong.SuaSoLuongThuoc_Ctrl;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class SuaSoLuongThuoc_GUI {

    public void showWithController(Stage stage, SuaSoLuongThuoc_Ctrl ctrl) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(733, 198);
        root.setStyle("-fx-font-size: 13;");

        Label lbTitle = new Label("Sửa số lượng thuốc");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(14);
        lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(705, 31);

        VBox mainVBox = new VBox();
        mainVBox.setLayoutX(14);
        mainVBox.setLayoutY(36);
        mainVBox.setPrefSize(661, 117);

        Pane topPane = new Pane();
        topPane.setPrefSize(661, 60);

        TextField tfMaThuoc = new TextField();
        tfMaThuoc.setId("tfMaThuoc");
        tfMaThuoc.setEditable(false);
        tfMaThuoc.setLayoutX(67);
        tfMaThuoc.setLayoutY(22);
        tfMaThuoc.setPrefSize(179, 25);
        tfMaThuoc.setStyle("-fx-max-height: 30;");

        Label lbMa = new Label("Mã thuốc:");
        lbMa.setLayoutY(25);

        Label lbTon = new Label("Tồn kho:");
        lbTon.setLayoutX(269);
        lbTon.setLayoutY(25);

        TextField tfSoLuongTon = new TextField();
        tfSoLuongTon.setId("tfSoLuongTon");
        tfSoLuongTon.setLayoutX(332);
        tfSoLuongTon.setLayoutY(22);
        tfSoLuongTon.setPrefSize(107, 25);
        tfSoLuongTon.setStyle("-fx-max-height: 30;");

        Label lbViTri = new Label("Vị trí:");
        lbViTri.setLayoutX(469);
        lbViTri.setLayoutY(25);

        ComboBox<String> cbViTri = new ComboBox<>();
        cbViTri.setId("cbViTri");
        cbViTri.setLayoutX(512);
        cbViTri.setLayoutY(22);
        cbViTri.setPrefSize(192, 25);
        cbViTri.setStyle("-fx-max-height: 30;");

        topPane.getChildren().addAll(tfMaThuoc, lbMa, lbTon, tfSoLuongTon, lbViTri, cbViTri);

        Pane secondPane = new Pane();
        secondPane.setPrefSize(661, 48);

        Label lbTen = new Label("Tên thuốc:");
        lbTen.setLayoutY(26);

        TextField tfTenThuoc = new TextField();
        tfTenThuoc.setId("tfTenThuoc");
        tfTenThuoc.setEditable(false);
        tfTenThuoc.setLayoutX(70);
        tfTenThuoc.setLayoutY(22);
        tfTenThuoc.setPrefSize(327, 25);
        tfTenThuoc.setStyle("-fx-max-height: 30;");

        Label lbLoai = new Label("Loại hàng:");
        lbLoai.setLayoutX(412);
        lbLoai.setLayoutY(25);

        ComboBox<String> cbLoaiHang = new ComboBox<>();
        cbLoaiHang.setId("cbLoaiHang");
        cbLoaiHang.setLayoutX(485);
        cbLoaiHang.setLayoutY(22);
        cbLoaiHang.setPrefSize(220, 25);
        cbLoaiHang.setStyle("-fx-max-height: 30;");

        secondPane.getChildren().addAll(lbTen, tfTenThuoc, lbLoai, cbLoaiHang);

        mainVBox.getChildren().addAll(topPane, secondPane);

        Button btnLuu = new Button("Lưu");
        btnLuu.setId("btnLuu");
        btnLuu.setLayoutX(650);
        btnLuu.setLayoutY(153);
        btnLuu.getStyleClass().add("btnthemhuy");
        btnLuu.setPrefSize(69, 32);

        Button btnHuy = new Button("Hủy");
        btnHuy.setId("btnHuy");
        btnHuy.setLayoutX(573);
        btnHuy.setLayoutY(153);
        btnHuy.getStyleClass().add("btnthemhuy");
        btnHuy.setPrefSize(69, 32);
        btnLuu.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
        btnHuy.setStyle(" -fx-background-color: #f0ad4e; -fx-text-fill: white;");

        root.getChildren().addAll(lbTitle, mainVBox, btnLuu, btnHuy);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css")).toExternalForm());

        // Inject controls into controller
        ctrl.tfMaThuoc = tfMaThuoc;
        ctrl.tfSoLuongTon = tfSoLuongTon;
        ctrl.cbViTri = cbViTri;
        ctrl.tfTenThuoc = tfTenThuoc;
        ctrl.cbLoaiHang = cbLoaiHang;
        ctrl.btnLuu = btnLuu;
        ctrl.btnHuy = btnHuy;

        // Initialize controller logic
        ctrl.initialize();

        stage.setTitle("Sửa số lượng thuốc");
        stage.setScene(scene);
        stage.show();
    }
}

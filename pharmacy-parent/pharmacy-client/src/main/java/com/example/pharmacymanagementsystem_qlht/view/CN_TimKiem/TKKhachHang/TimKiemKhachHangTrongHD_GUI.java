package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKKhachHang;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKKhachHang.TimKiemKhachHangTrongHD_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TimKiemKhachHangTrongHD_GUI {

    public static void showWithController(Stage stage, TimKiemKhachHangTrongHD_Ctrl ctrl) {

        // ====== MAIN PANE ======
        Pane mainPane = new Pane();
        mainPane.setPrefSize(1646, 895);
        mainPane.setStyle("-fx-font-size: 14px;");
        mainPane.getStylesheets().add(
                TimKiemKhachHangTrongHD_GUI.class.getResource(
                        "/com/example/pharmacymanagementsystem_qlht/css/TimKiemNhanVien.css"
                ).toExternalForm()
        );

        // ====== ROOT VBOX ======
        VBox vBox = new VBox();
        vBox.setLayoutX(10);
        vBox.setLayoutY(14);

        // ====== TITLE ======
        HBox hBoxTitle = new HBox();
        hBoxTitle.setPrefSize(1170, 62);

        Label lbTimKiem = new Label("Tìm kiếm khách hàng");
        lbTimKiem.setPrefSize(381, 54);
        lbTimKiem.getStyleClass().add("title");
        lbTimKiem.setFont(new Font(36));

        Label lbEmpty = new Label();

        ImageView ivUser = new ImageView(new Image(
                TimKiemKhachHangTrongHD_GUI.class.getResourceAsStream(
                        "/com/example/pharmacymanagementsystem_qlht/img/free-icon-user-group-296.png"
                )
        ));
        ivUser.setFitWidth(67);
        ivUser.setFitHeight(51);
        HBox.setMargin(ivUser, new Insets(5, 0, 0, 0));

        hBoxTitle.getChildren().addAll(lbTimKiem, lbEmpty, ivUser);

        Separator separator = new Separator();

        // ====== SEARCH BAR ======
        HBox hBoxSearch = new HBox();
        hBoxSearch.setPrefSize(1617, 65);

        ComboBox<String> cboTimKiem = new ComboBox<>();
        cboTimKiem.setPromptText("Tìm kiếm theo");
        cboTimKiem.setPrefSize(265, 40);
        HBox.setMargin(cboTimKiem, new Insets(10, 5, 0, 0));

        TextField txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Nhập thông tin");
        txtTimKiem.setPrefSize(368, 44);
        HBox.setMargin(txtTimKiem, new Insets(10, 0, 0, 0));

        Button btnTim = new Button();
        btnTim.setPrefSize(52, 40);
        btnTim.setDefaultButton(true);
        HBox.setMargin(btnTim, new Insets(10, 10, 0, 10));

        ImageView ivSearch = new ImageView(new Image(
                TimKiemKhachHangTrongHD_GUI.class.getResourceAsStream(
                        "/com/example/pharmacymanagementsystem_qlht/img/free-search-icon-2911-thumb.png"
                )
        ));
        ivSearch.setFitWidth(34);
        ivSearch.setFitHeight(20);
        ivSearch.setCursor(Cursor.DEFAULT);
        btnTim.setGraphic(ivSearch);

        Button btnLamMoi = new Button();
        btnLamMoi.setPrefSize(52, 40);
        btnLamMoi.getStylesheets().add(
                TimKiemKhachHangTrongHD_GUI.class.getResource(
                        "/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css"
                ).toExternalForm()
        );
        HBox.setMargin(btnLamMoi, new Insets(10, 0, 0, 0));

        ImageView ivRefresh = new ImageView(new Image(
                TimKiemKhachHangTrongHD_GUI.class.getResourceAsStream(
                        "/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png"
                )
        ));
        ivRefresh.setFitWidth(34);
        ivRefresh.setFitHeight(20);
        btnLamMoi.setGraphic(ivRefresh);

        hBoxSearch.getChildren().addAll(cboTimKiem, txtTimKiem, btnTim, btnLamMoi);
        VBox.setMargin(hBoxSearch, new Insets(0, 0, 10, 0));

        // ====== TABLE ======
        TableView<KhachHang> tbKhachHang = new TableView<>();
        tbKhachHang.setPrefSize(1615, 723);

        TableColumn<KhachHang, String> cotSTT = new TableColumn<>("STT");
        cotSTT.setPrefWidth(61);
        cotSTT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotMaKH = new TableColumn<>("Mã KH");
        cotMaKH.setPrefWidth(142);
        cotMaKH.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotTenKH = new TableColumn<>("Tên khách hàng");
        cotTenKH.setPrefWidth(273);

        TableColumn<KhachHang, String> cotNgaySinh = new TableColumn<>("Ngày sinh");
        cotNgaySinh.setPrefWidth(172);
        cotNgaySinh.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotEmail = new TableColumn<>("Email");
        cotEmail.setPrefWidth(251);

        TableColumn<KhachHang, String> cotSDT = new TableColumn<>("SDT");
        cotSDT.setPrefWidth(240);
        cotSDT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotGT = new TableColumn<>("Giới tính");
        cotGT.setPrefWidth(131.33);
        cotGT.setStyle("-fx-alignment: CENTER;");

        TableColumn<KhachHang, String> cotDiaChi = new TableColumn<>("Địa chỉ");
        cotDiaChi.setPrefWidth(345.33);

        TableColumn<KhachHang, String> cotMaKH1 = new TableColumn<>("Mã KH");
        cotMaKH1.setPrefWidth(142);
        cotMaKH1.setStyle("-fx-alignment: CENTER;");

        tbKhachHang.getColumns().addAll(
                cotSTT, cotMaKH, cotTenKH, cotNgaySinh,
                cotEmail, cotSDT, cotGT, cotDiaChi, cotMaKH1
        );

        ScrollPane scrollPane = new ScrollPane(tbKhachHang);

        vBox.getChildren().addAll(hBoxTitle, separator, hBoxSearch, scrollPane);
        mainPane.getChildren().add(vBox);

        // ====== MAP VÀO CONTROLLER ======
        ctrl.mainPane = mainPane;
        ctrl.cboTimKiem = cboTimKiem;
        ctrl.txtTimKiem = txtTimKiem;
        ctrl.btnTim = btnTim;
        ctrl.btnLamMoi = btnLamMoi;

        ctrl.tbKhachHang = tbKhachHang;

        ctrl.cotSTT = cotSTT;
        ctrl.cotMaKH = cotMaKH;
        ctrl.cotTenKH = cotTenKH;
        ctrl.cotNgaySinh = cotNgaySinh;
        ctrl.cotEmail = cotEmail;
        ctrl.cotSDT = cotSDT;
        ctrl.cotGT = cotGT;
        ctrl.cotDiaChi = cotDiaChi;
        ctrl.initialize();

        // ====== SCENE ======
        Scene scene = new Scene(mainPane);
        stage.setTitle("Tìm kiếm khách hàng trong hóa đơn");
        stage.setScene(scene);
        stage.show();
    }
}
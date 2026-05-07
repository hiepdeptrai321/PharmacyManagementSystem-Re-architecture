package com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKThuoc;

import com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKThuoc.ChiTietThuoc_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ChiTietThuoc_GUI extends Application {

    @Override
    public void start(Stage stage) {
        ViewRefs v = buildUIForController();
        Scene scene = new Scene(v.root, 996, 506);
        addStyles(scene);
        stage.setTitle("Chi tiết thuốc");
        stage.setScene(scene);
        stage.show();
    }

    /** Dùng trong app: KHÔNG lookup – tạo control và truyền thẳng cho controller */
    public void showWithController(Stage stage, ChiTietThuoc_Ctrl ctrl) {
        ViewRefs v = buildUIForController();

        // ==== GÁN CONTROL về controller (đúng fx:id trong FXML) ====
        ctrl.txtMaThuoc     = v.txtMaThuoc;
        ctrl.txtTenThuoc    = v.txtTenThuoc;
        ctrl.imgThuoc       = v.imgThuoc;
        ctrl.txtHamLuong    = v.txtHamLuong;
        ctrl.txtHangSanXuat = v.txtHangSanXuat;
        ctrl.txtNuocSanXuat = v.txtNuocSanXuat;
        ctrl.txtQCDongGoi   = v.txtQCDongGoi;
        ctrl.txtSDK_GPNK    = v.txtSDK_GPNK;
        ctrl.txtDuongDung   = v.txtDuongDung;
        ctrl.txtViTri       = v.txtViTri;
        ctrl.txtLoaiHang    = v.txtLoaiHang;
        ctrl.txtNhomDuocLy  = v.txtNhomDuocLy;

        ctrl.tblHoatChat    = v.tblHoatChat;
        ctrl.colMaHoatChat  = v.colMaHoatChat;
        ctrl.colTenHoatChat = v.colTenHoatChat;
        ctrl.colHamLuong    = v.colHamLuongTbl; // cùng fx:id "colHamLuong" trong FXML

        // Pane "Thoát" onMouseClicked="#btnThoatClick"
        v.btnHuyPane.setOnMouseClicked(ctrl::btnThoatClick);

        // initialize() nếu có
        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(v.root, 996, 506);
        addStyles(scene);
        stage.setTitle("Chi tiết thuốc");
        stage.setScene(scene);
        stage.show();
    }

    // ================== Build UI & giữ tham chiếu ==================
    private ViewRefs buildUIForController() {
        ViewRefs v = new ViewRefs();

        v.root = new Pane();
        v.root.setPrefSize(996, 506);
        v.root.setStyle("-fx-font-size: 13 px;");

        // Title pane
        Pane paneTitle = new Pane();
        paneTitle.setId("paneTitle");
        paneTitle.setPrefSize(1004, 51);

        Label lbTitle = new Label("Thông tin chung");
        lbTitle.setLayoutX(14);
        lbTitle.setLayoutY(7);
        lbTitle.setPrefSize(198, 36);
        lbTitle.setFont(Font.font("System Bold", 25));
        paneTitle.getChildren().add(lbTitle);

        // Text fields + labels (giữ layout như FXML)
        v.txtMaThuoc = new TextField();
        v.txtMaThuoc.setLayoutX(16);
        v.txtMaThuoc.setLayoutY(70);
        v.txtMaThuoc.setPrefSize(256, 31);

        v.txtTenThuoc = new TextField();
        v.txtTenThuoc.setLayoutX(276);
        v.txtTenThuoc.setLayoutY(70);
        v.txtTenThuoc.setPrefSize(410, 31);

        v.imgThuoc = imageView("/com/example/pharmacymanagementsystem_qlht/img/noimage.jpg", 266, 270, true);
        v.imgThuoc.setLayoutX(707);
        v.imgThuoc.setLayoutY(61);

        Label lbHamLuong = smallLabel("Hàm lượng:", 16, 107);
        v.txtHamLuong = new TextField();
        v.txtHamLuong.setLayoutX(16);
        v.txtHamLuong.setLayoutY(123);
        v.txtHamLuong.setPrefSize(136, 25);

        v.txtHangSanXuat = new TextField();
        v.txtHangSanXuat.setLayoutX(16);
        v.txtHangSanXuat.setLayoutY(179);
        v.txtHangSanXuat.setPrefSize(196, 31);

        Label lbDuongDung = smallLabel("Đường dùng:", 156, 107);
        v.txtDuongDung = new TextField();
        v.txtDuongDung.setLayoutX(156);
        v.txtDuongDung.setLayoutY(123);
        v.txtDuongDung.setPrefSize(198, 25);

        Label lbViTri = smallLabel("Vị trí:", 359, 107);
        v.txtViTri = new TextField();
        v.txtViTri.setLayoutX(359);
        v.txtViTri.setLayoutY(123);
        v.txtViTri.setPrefSize(159, 31);

        v.txtLoaiHang = new TextField();
        v.txtLoaiHang.setLayoutX(522);
        v.txtLoaiHang.setLayoutY(123);
        v.txtLoaiHang.setPrefSize(163, 31);

        Label lbLoaiHang = smallLabel("Loại hàng:", 522, 107);

        Label lbHangSX = smallLabel("Hãng sản xuất:", 16, 161);
        v.txtNuocSanXuat = new TextField();
        v.txtNuocSanXuat.setLayoutX(216);
        v.txtNuocSanXuat.setLayoutY(179);
        v.txtNuocSanXuat.setPrefSize(215, 31);

        Label lbNuocSX = new Label("Nước sản xuất:");
        lbNuocSX.setLayoutX(216);
        lbNuocSX.setLayoutY(161);
        lbNuocSX.setFont(Font.font(13));

        v.txtQCDongGoi = new TextField();
        v.txtQCDongGoi.setLayoutX(435);
        v.txtQCDongGoi.setLayoutY(179);
        v.txtQCDongGoi.setPrefSize(248, 31);

        Label lbQCDG = smallLabel("Quy cách đóng gói", 435, 161);

        v.txtSDK_GPNK = new TextField();
        v.txtSDK_GPNK.setLayoutX(16);
        v.txtSDK_GPNK.setLayoutY(234);
        v.txtSDK_GPNK.setPrefSize(196, 31);

        Label lbSDK = smallLabel("SDK_GPNK:", 16, 217);

        v.txtNhomDuocLy = new TextField();
        v.txtNhomDuocLy.setLayoutX(216);
        v.txtNhomDuocLy.setLayoutY(233);
        v.txtNhomDuocLy.setPrefSize(464, 33);

        Label lbNDL = smallLabel("Nhóm dược lý:", 216, 217);

        // Bảng hoạt chất
        v.tblHoatChat = new TableView<>();
        v.tblHoatChat.setLayoutX(16);
        v.tblHoatChat.setLayoutY(287);
        v.tblHoatChat.setPrefSize(663, 201);

        v.colMaHoatChat = new TableColumn<>("Mã hoạt chất");
        v.colMaHoatChat.setPrefWidth(195.42859268188477);

        v.colTenHoatChat = new TableColumn<>("Tên hoạt chất");
        v.colTenHoatChat.setPrefWidth(249.71429443359375);

        v.colHamLuongTbl = new TableColumn<>("Hàm lượng"); // fx:id colHamLuong
        v.colHamLuongTbl.setPrefWidth(216.42855834960938);

        v.tblHoatChat.getColumns().addAll(v.colMaHoatChat, v.colTenHoatChat, v.colHamLuongTbl);

        // Nhãn phụ
        Label lbMaThuoc = smallLabel("Mã thuốc:", 16, 55);
        Label lbTenThuoc = smallLabel("Tên thuốc:", 276, 55);

        Label lbHinhAnh = new Label("Hình ảnh");
        lbHinhAnh.setLayoutX(814);
        lbHinhAnh.setLayoutY(332);
        lbHinhAnh.setFont(Font.font(15));

        Label lbBangHC = smallLabel("Bảng hoạt chất:", 16, 271);

        // Nút/Pane Thoát
        v.btnHuyPane = new Pane();
        v.btnHuyPane.setId("btnHuy");
        v.btnHuyPane.setLayoutX(871);
        v.btnHuyPane.setLayoutY(452);
        v.btnHuyPane.setPrefSize(102, 36);

        Label lbThoat = new Label("Thoát");
        lbThoat.setId("lblTrang");
        lbThoat.setLayoutX(30);
        lbThoat.setLayoutY(8);
        lbThoat.setFont(Font.font(15));
        v.btnHuyPane.getChildren().add(lbThoat);

        // Add vào root
        v.root.getChildren().addAll(
                paneTitle,
                v.txtMaThuoc, v.txtTenThuoc, v.imgThuoc,
                lbHamLuong, v.txtHamLuong,
                v.txtHangSanXuat, lbDuongDung, v.txtDuongDung,
                lbViTri, v.txtViTri, v.txtLoaiHang, lbLoaiHang,
                lbHangSX, v.txtNuocSanXuat, lbNuocSX,
                v.txtQCDongGoi, lbQCDG,
                v.txtSDK_GPNK, lbSDK,
                v.txtNhomDuocLy, lbNDL,
                v.tblHoatChat,
                lbMaThuoc, lbTenThuoc,
                lbHinhAnh, v.btnHuyPane,
                lbBangHC
        );

        return v;
    }

    private void addStyles(Scene scene) {
        var css = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/TimKiemThuoc.css"),
                "Không tìm thấy css/TimKiemThuoc.css"
        ).toExternalForm();
        scene.getStylesheets().add(css);
    }

    private static Label smallLabel(String text, double x, double y) {
        Label lb = new Label(text);
        lb.setLayoutX(x);
        lb.setLayoutY(y);
        lb.setStyle("-fx-font-size: 13 px;");
        lb.setFont(Font.font(11));
        return lb;
    }

    private static ImageView imageView(String resource, double fitW, double fitH, boolean preserveRatio) {
        ImageView iv = new ImageView(new Image(Objects.requireNonNull(
                ChiTietThuoc_GUI.class.getResource(resource),
                "Không tìm thấy ảnh: " + resource
        ).toExternalForm()));
        iv.setFitWidth(fitW);
        iv.setFitHeight(fitH);
        iv.setPreserveRatio(preserveRatio);
        iv.setPickOnBounds(true);
        return iv;
    }

    // ===== Giữ tham chiếu control =====
    private static class ViewRefs {
        Pane root;

        TextField txtMaThuoc, txtTenThuoc, txtHamLuong, txtHangSanXuat, txtNuocSanXuat,
                txtQCDongGoi, txtSDK_GPNK, txtDuongDung, txtViTri, txtLoaiHang, txtNhomDuocLy;
        ImageView imgThuoc;

        TableView<ChiTietHoatChat> tblHoatChat;
        TableColumn<ChiTietHoatChat, String> colMaHoatChat, colTenHoatChat, colHamLuongTbl;

        Pane btnHuyPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMThuoc;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.SuaXoaThuoc_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;
import com.example.pharmacymanagementsystem_qlht.model.HoatChat;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class SuaXoaThuoc_GUI extends Application {

    // ===== UI fields (giữ state để inject, KHÔNG dùng Refs) =====
    public TextField txtMaThuoc, txtTenThuoc, txtHamLuong, txtDonViHamLuong, txtDuongDung,
            txtHangSanXuat, txtNuocSanXuat, txtQuyCachDongGoi, txtSDK_GPNK, txtTimKiemHoatChat;
    public ComboBox<?> cbxLoaiHang, cbxViTri, cbxNhomDuocLy;
    public ImageView imgThuoc;
    public TableView<ChiTietHoatChat> tblHoatChat;
    public TableColumn<ChiTietHoatChat, String> colMaHoatChat, colTenHoatChat, colHamLuong, colXoa;
    public ListView<HoatChat> listViewHoatChat;
    public Button btnChonAnh, btnXoa, btnHuy, btnLuu;
    public CheckBox cbETC;

    @Override
    public void start(Stage stage) {
        AnchorPane root = buildUI();
        Scene scene = new Scene(root, 1004, 690);
        addStyles(scene);
        stage.setTitle("Sửa/Xóa thuốc");
        stage.setScene(scene);
        stage.show();
    }

    /** Show + inject controller (giống pattern DanhMucThuoc_GUI) */
    public void showWithController(Stage stage, SuaXoaThuoc_Ctrl ctrl) {
        AnchorPane root = buildUI();

        // inject ALL
        ctrl.txtMaThuoc = txtMaThuoc;
        ctrl.txtTenThuoc = txtTenThuoc;
        ctrl.cbxLoaiHang = (ComboBox) cbxLoaiHang;
        ctrl.cbxViTri    = (ComboBox) cbxViTri;
        ctrl.cbxNhomDuocLy = (ComboBox) cbxNhomDuocLy;
        ctrl.cbETC = cbETC;
        ctrl.imgThuoc_SanPham = imgThuoc;

        ctrl.txtHamLuong = txtHamLuong;
        ctrl.txtDonViHamLuong = txtDonViHamLuong;
        ctrl.txtDuongDung = txtDuongDung;
        ctrl.txtHangSanXuat = txtHangSanXuat;
        ctrl.txtNuocSanXuat = txtNuocSanXuat;
        ctrl.txtQuyCachDongGoi = txtQuyCachDongGoi;
        ctrl.txtSDK_GPNK = txtSDK_GPNK;

        ctrl.tblHoatChat = tblHoatChat;
        ctrl.colMaHoatChat = (TableColumn) colMaHoatChat;
        ctrl.colTenHoatChat = (TableColumn) colTenHoatChat;
        ctrl.colHamLuong = (TableColumn) colHamLuong;
        ctrl.colXoa = (TableColumn) colXoa;

        ctrl.txtTimKiemHoatChat = txtTimKiemHoatChat;
        ctrl.listViewHoatChat = listViewHoatChat;

        ctrl.btnXoa = btnXoa;
        ctrl.btnHuy = btnHuy;
        ctrl.btnLuu = btnLuu;
        ctrl.btnChonAnh = btnChonAnh;

        ctrl.initialize();

        Scene scene = new Scene(root, 1004, 690);
        addStyles(scene);
        stage.setScene(scene);
        stage.setTitle("Sửa/Xóa thuốc");
        stage.show();
    }


    /** Build UI theo đúng form, không dùng Refs để tránh null injection */
    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1004, 690);
        root.setStyle("-fx-font-size: 14;");

        Label lbTitle = new Label("Thông tin chung");
        lbTitle.setId("lbtitle");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(14); lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(977, 31);
        lbTitle.setFont(Font.font("System", 18));

        VBox vbox = new VBox();
        vbox.setLayoutX(14); vbox.setLayoutY(36);
        vbox.setPrefSize(977, 305);

        Pane topPane = new Pane(); topPane.setPrefSize(956, 121);
        VBox leftBox = new VBox(); leftBox.setLayoutY(1); leftBox.setPrefSize(668, 136);

        Pane spacer = new Pane(); spacer.setPrefSize(668, 207);
        Pane infoLine = new Pane(); infoLine.setPrefSize(574, 240);

        Label lbTenThuoc = new Label("Tên thuốc"); lbTenThuoc.setLayoutX(209); lbTenThuoc.setLayoutY(-25); lbTenThuoc.setFont(Font.font(14));
        txtTenThuoc = new TextField(); txtTenThuoc.setLayoutX(283); txtTenThuoc.setLayoutY(-28); txtTenThuoc.setPrefSize(360, 25);

        Label lbLoaiHang = new Label("Loại hàng"); lbLoaiHang.setLayoutX(0); lbLoaiHang.setLayoutY(18); lbLoaiHang.setFont(Font.font(14));
        cbxLoaiHang = new ComboBox<>(); cbxLoaiHang.setLayoutX(70); cbxLoaiHang.setLayoutY(15); cbxLoaiHang.setPrefSize(228, 25);

        Label lbViTri = new Label("Vị trí"); lbViTri.setLayoutX(318); lbViTri.setLayoutY(21); lbViTri.setFont(Font.font(14));
        cbxViTri = new ComboBox<>(); cbxViTri.setLayoutX(356); cbxViTri.setLayoutY(16); cbxViTri.setPrefSize(287, 25);

        Label lbMa = new Label("Mã thuốc"); lbMa.setLayoutX(1); lbMa.setLayoutY(-25); lbMa.setFont(Font.font(14));
        txtMaThuoc = new TextField(); txtMaThuoc.setEditable(false); txtMaThuoc.setLayoutX(74); txtMaThuoc.setLayoutY(-28); txtMaThuoc.setPrefSize(120, 25);

        infoLine.getChildren().addAll(lbTenThuoc, txtTenThuoc, lbLoaiHang, cbxLoaiHang, lbViTri, cbxViTri, lbMa, txtMaThuoc);
        leftBox.getChildren().addAll(spacer, infoLine);

        imgThuoc = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/noimage.jpg")
        ).toExternalForm()));
        imgThuoc.setFitHeight(222); imgThuoc.setFitWidth(227);
        imgThuoc.setLayoutX(729); imgThuoc.setLayoutY(12);
        imgThuoc.setPreserveRatio(true); imgThuoc.setPickOnBounds(true);

        topPane.getChildren().addAll(leftBox, imgThuoc);

        // Pane thông tin chi tiết
        Pane detailPane = new Pane(); detailPane.setPrefSize(970, 184);
        detailPane.setId("pnlthongtin");
        detailPane.getStyleClass().add("pnlthongtin");

        Label lbTTCT = new Label("Thông tin chi tiết");
        lbTTCT.setId("lbtitle");
        lbTTCT.getStyleClass().add("lbtitle");lbTTCT.setPrefSize(637,0);
        lbTTCT.setLayoutX(4); lbTTCT.setLayoutY(-6); lbTTCT.setFont(Font.font(18));

        Label lbHL = new Label("Hàm lượng"); lbHL.setLayoutX(4); lbHL.setLayoutY(26); lbHL.setFont(Font.font(13));
        txtHamLuong = new TextField(); txtHamLuong.setLayoutX(4); txtHamLuong.setLayoutY(45); txtHamLuong.setPrefSize(136, 25);

        Label lbDVHL = new Label("Đơn vị hàm lượng"); lbDVHL.setLayoutX(154); lbDVHL.setLayoutY(26); lbDVHL.setFont(Font.font(13));
        txtDonViHamLuong = new TextField(); txtDonViHamLuong.setLayoutX(154); txtDonViHamLuong.setLayoutY(45); txtDonViHamLuong.setPrefSize(104, 30);

        Label lbDuong = new Label("Đường dùng"); lbDuong.setLayoutX(272); lbDuong.setLayoutY(26); lbDuong.setFont(Font.font(13));
        txtDuongDung = new TextField(); txtDuongDung.setLayoutX(272); txtDuongDung.setLayoutY(45); txtDuongDung.setPrefSize(120, 25);

        Label lbQCDG = new Label("Quy cách đóng gói"); lbQCDG.setLayoutX(401); lbQCDG.setLayoutY(26); lbQCDG.setFont(Font.font(13));
        txtQuyCachDongGoi = new TextField(); txtQuyCachDongGoi.setLayoutX(401); txtQuyCachDongGoi.setLayoutY(45); txtQuyCachDongGoi.setPrefSize(242, 25);

        Label lbSDK = new Label("SDK_GPNK"); lbSDK.setLayoutX(401); lbSDK.setLayoutY(76); lbSDK.setFont(Font.font(13));
        txtSDK_GPNK = new TextField(); txtSDK_GPNK.setLayoutX(401); txtSDK_GPNK.setLayoutY(92); txtSDK_GPNK.setPrefSize(242, 25);

        Label lbHSX = new Label("Hãng sản xuất"); lbHSX.setLayoutX(4); lbHSX.setLayoutY(76); lbHSX.setFont(Font.font(13));
        txtHangSanXuat = new TextField(); txtHangSanXuat.setLayoutX(4); txtHangSanXuat.setLayoutY(92); txtHangSanXuat.setPrefSize(228, 25);

        Label lbNSX = new Label("Nước sản xuất"); lbNSX.setLayoutX(247); lbNSX.setLayoutY(76); lbNSX.setFont(Font.font(13));
        txtNuocSanXuat = new TextField(); txtNuocSanXuat.setLayoutX(247); txtNuocSanXuat.setLayoutY(92); txtNuocSanXuat.setPrefSize(143, 25);

        Label lbNDL = new Label("Nhóm dược lý"); lbNDL.setLayoutX(4); lbNDL.setLayoutY(125); lbNDL.setFont(Font.font(13));
        cbxNhomDuocLy = new ComboBox<>(); cbxNhomDuocLy.setLayoutX(4); cbxNhomDuocLy.setLayoutY(142); cbxNhomDuocLy.setPrefSize(388, 30);

        Label lbETC = new Label("ETC: "); lbETC.setLayoutX(401); lbETC.setLayoutY(145); lbETC.setFont(Font.font(14));
        cbETC = new CheckBox();cbETC.setLayoutX(431); cbETC.setLayoutY(142); cbETC.setPrefSize(157, 25);

        btnChonAnh = new Button("Chọn ảnh");
        btnChonAnh.setLayoutX(800); btnChonAnh.setLayoutY(124); btnChonAnh.setPrefSize(92, 32);
        btnChonAnh.setStyle("-fx-background-color: rgba(0, 123, 255, 0.8); \n" +
                "    -fx-border-color: transparent; \n" +
                "    -fx-font-size: 14px;\n" +
                "    -fx-background-radius: 6;\n" +
                "    -fx-cursor: hand;\n" +
                "    -fx-graphic-text-gap: 8;\n" +
                "    -fx-min-width: 80px;\n" +
                "    -fx-min-height: 30px;\n" +
                "    -fx-pref-height: 18px;\n" +
                "    -fx-text-fill: white;");

        detailPane.getChildren().addAll(
                lbTTCT, lbHL, txtHamLuong, lbDVHL, txtDonViHamLuong, lbDuong, txtDuongDung,
                lbQCDG, txtQuyCachDongGoi, lbSDK, txtSDK_GPNK, lbHSX, txtHangSanXuat,
                lbNSX, txtNuocSanXuat, lbNDL, cbxNhomDuocLy, btnChonAnh, lbETC, cbETC
        );

        vbox.getChildren().addAll(topPane, detailPane);

        // Bảng hoạt chất + tìm
        Label lbHC = new Label("Hoạt chất");
        lbHC.setId("lbtitle");lbHC.setPrefSize(637,0);
        lbHC.getStyleClass().add("lbtitle");
        lbHC.setLayoutX(18); lbHC.setLayoutY(334);
        lbHC.setFont(Font.font(18));

        Label lbTim = new Label("Tìm hoạt chất:");
        lbTim.setId("lblChinh");
        lbTim.getStyleClass().add("lblChinh");
        lbTim.setLayoutX(18); lbTim.setLayoutY(378);
        lbTim.setFont(Font.font(14));

        txtTimKiemHoatChat = new TextField(); txtTimKiemHoatChat.setLayoutX(108); txtTimKiemHoatChat.setLayoutY(375); txtTimKiemHoatChat.setPrefSize(388, 30);
        listViewHoatChat = new ListView<>(); listViewHoatChat.setLayoutX(108); listViewHoatChat.setLayoutY(405); listViewHoatChat.setPrefSize(388, 194);

        tblHoatChat = new TableView<>();
        tblHoatChat.setLayoutX(18); tblHoatChat.setLayoutY(411); tblHoatChat.setPrefSize(966, 213);
        colMaHoatChat = new TableColumn<>("Mã hoạt chất"); colMaHoatChat.setPrefWidth(221.14); colMaHoatChat.setStyle("-fx-alignment: CENTER;");
        colTenHoatChat = new TableColumn<>("Tên hoạt chất"); colTenHoatChat.setPrefWidth(448); colTenHoatChat.setStyle("-fx-alignment: CENTER-LEFT;");
        colHamLuong = new TableColumn<>("Hàm lượng"); colHamLuong.setPrefWidth(209.14); colHamLuong.setStyle("-fx-alignment: CENTER");
        colXoa = new TableColumn<>("Xóa"); colXoa.setPrefWidth(86.28); colXoa.setStyle("-fx-alignment: CENTER;");
        tblHoatChat.getColumns().addAll(colMaHoatChat, colTenHoatChat, colHamLuong, colXoa);

        // Nút dưới
        btnXoa = new Button("Xóa"); btnXoa.setLayoutX(17); btnXoa.setLayoutY(641); btnXoa.setPrefSize(69, 32);
        btnXoa.setStyle(" -fx-background-color: #d9534f; -fx-text-fill: white;");
        btnXoa.setAlignment(Pos.CENTER);
        btnHuy = new Button("Hủy"); btnHuy.setLayoutX(836); btnHuy.setLayoutY(641); btnHuy.setPrefSize(69, 32);
        btnHuy.setStyle(" -fx-background-color: #f0ad4e; -fx-text-fill: white;");
        btnLuu = new Button("Lưu"); btnLuu.setLayoutX(915); btnLuu.setLayoutY(641); btnLuu.setPrefSize(69, 32);
        btnLuu.setStyle(" -fx-background-color: #5cb85c; -fx-text-fill: white;");


        // Add all
        root.getChildren().addAll(
                lbTitle, vbox, btnXoa, btnHuy,
                tblHoatChat, lbTim, txtTimKiemHoatChat, listViewHoatChat, lbHC, btnLuu
        );

        return root;
    }

    private void addStyles(Scene scene) {
        var css = Objects.requireNonNull(getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css"), "Không tìm thấy ThemThuoc.css!").toExternalForm();
        scene.getStylesheets().add(css);
    }

    public static void main(String[] args) { launch(args); }
}

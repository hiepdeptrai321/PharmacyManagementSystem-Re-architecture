package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMThuoc;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.ThemThuoc_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;
import com.example.pharmacymanagementsystem_qlht.model.HoatChat;
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
public class ThemThuoc_GUI {

    public TextField txtTenThuoc, txtMaThuoc, txtDonViHamLuong, txtHamLuong,
            txtHangSanXuat, txtNuocSanXuat, txtQuyCachDongGoi,
            txtSDK_GPNK, txtDuongDung, txtTimKiemHoatChat;
    public ComboBox<?> cbxLoaiHang, cbxViTri, cbxNhomDuocLy, cbDVTCB;
    public ImageView imgThuoc_SanPham;
    public TableView<ChiTietHoatChat> tblHoatChat;
    public TableColumn<ChiTietHoatChat, String> colMaHoatChat, colTenHoatChat, colXoa;
    public TableColumn<ChiTietHoatChat, Float> colHamLuong;
    public ListView<HoatChat> listViewHoatChat;
    public Button btnChonAnh, btnThem;
    public CheckBox cbETC;

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1004, 690);
        root.setStyle("-fx-font-size: 14;");

        // Title
        Label lbTitle = new Label("Thông tin chung");
        lbTitle.getStyleClass().add("lbtitle");
        lbTitle.setLayoutX(14); lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(977, 31);
        lbTitle.setFont(Font.font(18));

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

        Label lbViTri = new Label("Vị trí"); lbViTri.setLayoutX(318); lbViTri.setLayoutY(20); lbViTri.setFont(Font.font(14));
        cbxViTri = new ComboBox<>(); cbxViTri.setLayoutX(356); cbxViTri.setLayoutY(16); cbxViTri.setPrefSize(287, 25);

        Label lbMa = new Label("Mã thuốc"); lbMa.setLayoutX(1); lbMa.setLayoutY(-27); lbMa.setFont(Font.font(14));
        txtMaThuoc = new TextField(); txtMaThuoc.setEditable(false); txtMaThuoc.setDisable(true);
        txtMaThuoc.setLayoutX(70); txtMaThuoc.setLayoutY(-28); txtMaThuoc.setPrefSize(120, 25);

        infoLine.getChildren().addAll(lbTenThuoc, txtTenThuoc, lbLoaiHang, cbxLoaiHang, lbViTri, cbxViTri, lbMa, txtMaThuoc);
        leftBox.getChildren().addAll(spacer, infoLine);

        imgThuoc_SanPham = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/noimage.jpg")
        ).toExternalForm()));
        imgThuoc_SanPham.setFitHeight(222); imgThuoc_SanPham.setFitWidth(227);
        imgThuoc_SanPham.setLayoutX(729); imgThuoc_SanPham.setLayoutY(12);
        imgThuoc_SanPham.setPreserveRatio(true); imgThuoc_SanPham.setPickOnBounds(true);

        topPane.getChildren().addAll(leftBox, imgThuoc_SanPham);

        // Pane thông tin chi tiết
        Pane detailPane = new Pane(); detailPane.setPrefSize(970, 184);
        detailPane.getStyleClass().add("pnlthongtin");

        Label lbTTCT = new Label("Thông tin chi tiết");
        lbTTCT.getStyleClass().add("lbtitle");
        lbTTCT.setLayoutX(4); lbTTCT.setLayoutY(-6); lbTTCT.setPrefSize(637,0); lbTTCT.setFont(Font.font(18));

        Label lbHL = new Label("Hàm lượng"); lbHL.setLayoutX(4); lbHL.setLayoutY(31); lbHL.setFont(Font.font(11));
        txtHamLuong = new TextField(); txtHamLuong.setLayoutX(4); txtHamLuong.setLayoutY(45); txtHamLuong.setPrefSize(136, 25);

        Label lbDVHL = new Label("Đơn vị hàm lượng"); lbDVHL.setLayoutX(154); lbDVHL.setLayoutY(31); lbDVHL.setFont(Font.font(11));
        txtDonViHamLuong = new TextField(); txtDonViHamLuong.setLayoutX(154); txtDonViHamLuong.setLayoutY(45); txtDonViHamLuong.setPrefSize(102, 25);

        Label lbDuong = new Label("Đường dùng"); lbDuong.setLayoutX(272); lbDuong.setLayoutY(31); lbDuong.setFont(Font.font(11));
        txtDuongDung = new TextField(); txtDuongDung.setLayoutX(272); txtDuongDung.setLayoutY(45); txtDuongDung.setPrefSize(120, 25);

        Label lbQCDG = new Label("Quy cách đóng gói"); lbQCDG.setLayoutX(401); lbQCDG.setLayoutY(31); lbQCDG.setFont(Font.font(11));
        txtQuyCachDongGoi = new TextField(); txtQuyCachDongGoi.setLayoutX(401); txtQuyCachDongGoi.setLayoutY(45); txtQuyCachDongGoi.setPrefSize(242, 25);

        Label lbSDK = new Label("SDK_GPNK"); lbSDK.setLayoutX(401); lbSDK.setLayoutY(76); lbSDK.setFont(Font.font(11));
        txtSDK_GPNK = new TextField(); txtSDK_GPNK.setLayoutX(401); txtSDK_GPNK.setLayoutY(92); txtSDK_GPNK.setPrefSize(242, 25);

        Label lbHSX = new Label("Hãng sản xuất"); lbHSX.setLayoutX(4); lbHSX.setLayoutY(76); lbHSX.setFont(Font.font(11));
        txtHangSanXuat = new TextField(); txtHangSanXuat.setLayoutX(4); txtHangSanXuat.setLayoutY(92); txtHangSanXuat.setPrefSize(228, 25);

        Label lbNSX = new Label("Nước sản xuất"); lbNSX.setLayoutX(247); lbNSX.setLayoutY(76); lbNSX.setFont(Font.font(11));
        txtNuocSanXuat = new TextField(); txtNuocSanXuat.setLayoutX(247); txtNuocSanXuat.setLayoutY(92); txtNuocSanXuat.setPrefSize(143, 25);

        Label lbNDL = new Label("Nhóm dược lý"); lbNDL.setLayoutX(4); lbNDL.setLayoutY(125); lbNDL.setFont(Font.font(11));
        cbxNhomDuocLy = new ComboBox<>(); cbxNhomDuocLy.setLayoutX(4); cbxNhomDuocLy.setLayoutY(142); cbxNhomDuocLy.setPrefSize(370, 25);

        Label lbDVTCB = new Label("Đơn vị tính (cơ bản)"); lbDVTCB.setLayoutX(386); lbDVTCB.setLayoutY(125); lbDVTCB.setFont(Font.font(11));
        cbDVTCB = new ComboBox<>(); cbDVTCB.setLayoutX(386); cbDVTCB.setLayoutY(142); cbDVTCB.setPrefSize(157, 25);
        cbDVTCB.setPromptText("Chọn đơn vị");

        Label lbETC = new Label("ETC: "); lbETC.setLayoutX(570); lbETC.setLayoutY(145); lbETC.setFont(Font.font(14));
        cbETC = new CheckBox();cbETC.setLayoutX(600); cbETC.setLayoutY(142); cbETC.setPrefSize(157, 25);

        btnChonAnh = new Button("Chọn ảnh");
        btnChonAnh.setId("btnchonanh"); // để CSS match
        btnChonAnh.setLayoutX(800); btnChonAnh.setLayoutY(124); btnChonAnh.setPrefSize(92, 32);
        btnChonAnh.setMinWidth(92); btnChonAnh.setMinHeight(30);

        detailPane.getChildren().addAll(
                lbTTCT, lbHL, txtHamLuong, lbDVHL, txtDonViHamLuong, lbDuong, txtDuongDung,
                lbQCDG, txtQuyCachDongGoi, lbSDK, txtSDK_GPNK, lbHSX, txtHangSanXuat,
                lbNSX, txtNuocSanXuat, lbNDL, cbxNhomDuocLy, lbDVTCB, cbDVTCB, btnChonAnh,lbETC,cbETC
        );

        vbox.getChildren().addAll(topPane, detailPane);

        // Bảng + tìm hoạt chất
        Label lbHC = new Label("Hoạt chất"); lbHC.getStyleClass().add("lbtitle");
        lbHC.setLayoutX(18); lbHC.setLayoutY(334); lbHC.setFont(Font.font(18));lbHC.setPrefSize(637,0);

        Label lbTim = new Label("Tìm hoạt chất:"); lbTim.getStyleClass().add("lblChinh");
        lbTim.setLayoutX(18); lbTim.setLayoutY(378); lbTim.setFont(Font.font(14));

        txtTimKiemHoatChat = new TextField();
        txtTimKiemHoatChat.setId("timKiemThuocC");
        txtTimKiemHoatChat.setLayoutX(108); txtTimKiemHoatChat.setLayoutY(375); txtTimKiemHoatChat.setPrefSize(388, 30);

        listViewHoatChat = new ListView<>();
        listViewHoatChat.setId("listViewTimKiemThuoc");
        listViewHoatChat.setLayoutX(108); listViewHoatChat.setLayoutY(405); listViewHoatChat.setPrefSize(388, 194);

        tblHoatChat = new TableView<>();
        tblHoatChat.setLayoutX(18); tblHoatChat.setLayoutY(411); tblHoatChat.setPrefSize(966, 213);
        colMaHoatChat = new TableColumn<>("Mã hoạt chất"); colMaHoatChat.setPrefWidth(221.14); colMaHoatChat.setStyle("-fx-alignment: CENTER;");
        colTenHoatChat = new TableColumn<>("Tên hoạt chất"); colTenHoatChat.setPrefWidth(448); colTenHoatChat.setStyle("-fx-alignment: CENTER-LEFT;");
        colHamLuong = new TableColumn<>("Hàm lượng"); colHamLuong.setPrefWidth(209.14); colHamLuong.setStyle("-fx-alignment: CENTER");
        colXoa = new TableColumn<>("Xóa"); colXoa.setPrefWidth(86.28); colXoa.setStyle("-fx-alignment: CENTER;");

        tblHoatChat.getColumns().addAll(colMaHoatChat, colTenHoatChat, colHamLuong, colXoa);

        // Nút Lưu (Thêm)
        btnThem = new Button("Thêm");
        btnThem.setId("btnluu"); // CSS
        btnThem.setLayoutX(915); btnThem.setLayoutY(641); btnThem.setPrefSize(69, 32);
        btnThem.setAlignment(Pos.CENTER);

        // Add root
        root.getChildren().addAll(
                lbTitle, vbox,
                tblHoatChat, lbTim, txtTimKiemHoatChat, listViewHoatChat, lbHC,
                btnThem
        );

        return root;
    }

    public void showWithController(Stage stage, ThemThuoc_Ctrl ctrl) {
        AnchorPane root = buildUI();

        // ===== Inject sang controller (trùng fx:id) =====
        ctrl.txtTenThuoc = txtTenThuoc;
        ctrl.txtMaThuoc = txtMaThuoc;
        ctrl.txtDonViHamLuong = txtDonViHamLuong;
        ctrl.txtHamLuong = txtHamLuong;
        ctrl.txtHangSanXuat = txtHangSanXuat;
        ctrl.txtNuocSanXuat = txtNuocSanXuat;
        ctrl.txtQuyCachDongGoi = txtQuyCachDongGoi;
        ctrl.txtSDK_GPNK = txtSDK_GPNK;
        ctrl.txtDuongDung = txtDuongDung;
        ctrl.cbETC = cbETC;

        ctrl.cbxLoaiHang = (ComboBox) cbxLoaiHang;
        ctrl.cbxViTri = (ComboBox) cbxViTri;
        ctrl.cbxNhomDuocLy = (ComboBox) cbxNhomDuocLy;
        try { ctrl.getClass().getField("cbDVTCB").set(ctrl, cbDVTCB); } catch (Exception ignore) {}

        ctrl.imgThuoc_SanPham = imgThuoc_SanPham;

        ctrl.tblHoatChat = tblHoatChat;
        ctrl.colMaHoatChat = (TableColumn) colMaHoatChat;
        ctrl.colTenHoatChat = (TableColumn) colTenHoatChat;
        ctrl.colHamLuong = (TableColumn) colHamLuong;
        ctrl.colXoa = (TableColumn) colXoa;

        ctrl.txtTimKiemHoatChat = txtTimKiemHoatChat;
        ctrl.listViewHoatChat = listViewHoatChat;

        try { ctrl.getClass().getField("btnChonAnh").set(ctrl, btnChonAnh); } catch (Exception ignore) {}
        try { ctrl.getClass().getField("btnThem").set(ctrl, btnThem); } catch (Exception ignore) {}

        // ===== Gắn action giống FXML (không reflection) =====
        // Controller nên có: public void chonFile(ActionEvent e) và public void btnThemThuoc(ActionEvent e)
        btnChonAnh.setOnAction(ctrl::chonFile);
        btnThem.setOnAction(ctrl::btnThemThuoc);

        // Cho controller hoàn tất init (cellFactory, dữ liệu…)
        try { ctrl.initialize(); } catch (Exception ignore) {}

        Scene scene = new Scene(root, 1004, 690);
        addStyles(scene);
        stage.setScene(scene);
        stage.setTitle("Thêm thuốc");
    }

    private void addStyles(Scene scene) {
        var css = Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/css/ThemThuoc.css"),
                "Không tìm thấy ThemThuoc.css!"
        ).toExternalForm();
        scene.getStylesheets().add(css);
    }
}

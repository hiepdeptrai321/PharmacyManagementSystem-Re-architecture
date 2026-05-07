package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.SuaXoaNhanVien_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.LuongNhanVien;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class SuaXoaNhanVien_GUI {
    public TextField txtMaNV;
    public TextField txtTenNV;
    public TextField txtSDT;
    public TextField txtEmail;
    public ComboBox<String> cbxGioiTinh;
    public TextField txtDiaChi;
    public TextField txtNgayBD;
    public TextField txtNgayKT;
    public ComboBox<String> cbxTrangThai;
    public DatePicker txtNgaySinh;
    public Button btnLuu;
    public Button btnXoa;
    public Button btnHuy;
    public Button btnSuaTaiKhoan;
    public Button btnThayDoiLuong;
    public TableView<LuongNhanVien> tblLuongNV;
    public TableColumn<LuongNhanVien, String> colMaLuong;
    public TableColumn<LuongNhanVien, String> colTuNgay;
    public TableColumn<LuongNhanVien, String> colDenNgay;
    public TableColumn<LuongNhanVien, Double> colLuongCoBan;
    public TableColumn<LuongNhanVien, Double> colPhuCap;
    public TableColumn<LuongNhanVien, String> colGhiChu;

    public void showWithController(Stage stage, SuaXoaNhanVien_Ctrl ctrl, NhanVien nhanVien) {
        AnchorPane root = buildUI();

        ctrl.txtMaNV = txtMaNV;
        ctrl.txtTenNV = txtTenNV;
        ctrl.txtSDT = txtSDT;
        ctrl.txtEmail = txtEmail;
        ctrl.cbxGioiTinh = cbxGioiTinh;
        ctrl.txtDiaChi = txtDiaChi;
        ctrl.txtNgayBD = txtNgayBD;
        ctrl.txtNgayKT = txtNgayKT;
        ctrl.cbxTrangThai = cbxTrangThai;
        ctrl.txtNgaySinh = txtNgaySinh;
        ctrl.tblLuongNV = tblLuongNV;
        ctrl.colMaLuong = colMaLuong;
        ctrl.colTuNgay = colTuNgay;
        ctrl.colDenNgay = colDenNgay;
        ctrl.colLuongCoBan = colLuongCoBan;
        ctrl.colPhuCap = colPhuCap;
        ctrl.colGhiChu = colGhiChu;

        btnLuu.setOnAction(ctrl::btnLuu);
        btnXoa.setOnAction(ctrl::btnXoa);
        btnHuy.setOnAction(ctrl::btnHuy);
        btnSuaTaiKhoan.setOnAction(ctrl::btnSuaTaiKhoan);
        btnThayDoiLuong.setOnAction(ctrl::btnThayDoiLuong);

        ctrl.initialize();

        Scene scene = new Scene(root, 804, 582);
        scene.getStylesheets().add(requireCss("/com/example/pharmacymanagementsystem_qlht/css/SuaXoaNhanVien.css"));

        stage.setScene(scene);
        stage.setTitle("Cap nhat nhan vien");
        stage.show();
    }

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(804, 582);

        Label lbTitle = new Label("Cap nhat nhan vien");
        lbTitle.setLayoutX(14);
        lbTitle.setLayoutY(8);
        lbTitle.setPrefSize(226, 35);
        lbTitle.setFont(Font.font("System Bold", 24));

        ImageView ivTitle = new ImageView(new Image(requireRes("/com/example/pharmacymanagementsystem_qlht/img/free-icon-person-and-gear-9847.png")));
        ivTitle.setFitHeight(49);
        ivTitle.setFitWidth(41);
        ivTitle.setLayoutX(233);
        ivTitle.setLayoutY(-2);
        ivTitle.setPreserveRatio(true);

        Separator sep = new Separator();
        sep.setLayoutY(38);
        sep.setPrefWidth(804);

        VBox outer = new VBox();
        outer.setLayoutX(14);
        outer.setLayoutY(36);
        outer.setPrefSize(790, 178);
        VBox box = new VBox();
        Pane pane = new Pane();
        pane.setPrefSize(790, 195);

        Label lbMa = new Label("Ma nhan vien:");
        lbMa.setLayoutX(2);
        lbMa.setLayoutY(15);
        txtMaNV = new TextField();
        txtMaNV.setDisable(true);
        txtMaNV.setLayoutX(2);
        txtMaNV.setLayoutY(38);
        txtMaNV.setPrefSize(315, 25);

        Label lbTen = new Label("Ten nhan vien");
        lbTen.setLayoutX(325);
        lbTen.setLayoutY(15);
        txtTenNV = new TextField();
        txtTenNV.setLayoutX(326);
        txtTenNV.setLayoutY(38);
        txtTenNV.setPrefSize(453, 25);

        Label lbSDT = new Label("So dien thoai:");
        lbSDT.setLayoutX(6);
        lbSDT.setLayoutY(70);
        txtSDT = new TextField();
        txtSDT.setLayoutX(2);
        txtSDT.setLayoutY(94);
        txtSDT.setPrefSize(315, 25);

        Label lbEmail = new Label("Email:");
        lbEmail.setLayoutX(325);
        lbEmail.setLayoutY(70);
        txtEmail = new TextField();
        txtEmail.setLayoutX(326);
        txtEmail.setLayoutY(96);
        txtEmail.setPrefSize(453, 25);

        Label lbGT = new Label("Gioi tinh:");
        lbGT.setLayoutX(5);
        lbGT.setLayoutY(129);
        cbxGioiTinh = new ComboBox<>();
        cbxGioiTinh.setLayoutX(2);
        cbxGioiTinh.setLayoutY(152);
        cbxGioiTinh.setPrefSize(182, 25);

        Label lbDC = new Label("Dia chi");
        lbDC.setLayoutX(199);
        lbDC.setLayoutY(129);
        txtDiaChi = new TextField();
        txtDiaChi.setLayoutX(199);
        txtDiaChi.setLayoutY(152);
        txtDiaChi.setPrefSize(579, 25);

        pane.getChildren().addAll(lbMa, txtMaNV, lbTen, txtTenNV, lbSDT, txtSDT, lbEmail, txtEmail, lbGT, cbxGioiTinh, lbDC, txtDiaChi);
        box.getChildren().add(pane);
        outer.getChildren().add(box);

        Label lbNgaySinh = new Label("Ngay sinh:");
        lbNgaySinh.setLayoutX(14);
        lbNgaySinh.setLayoutY(230);
        txtNgaySinh = new DatePicker();
        txtNgaySinh.setLayoutX(14);
        txtNgaySinh.setLayoutY(258);
        txtNgaySinh.setPrefSize(249, 25);

        Label lbTrangThai = new Label("Trang thai:");
        lbTrangThai.setLayoutX(278);
        lbTrangThai.setLayoutY(230);
        cbxTrangThai = new ComboBox<>();
        cbxTrangThai.setLayoutX(279);
        cbxTrangThai.setLayoutY(258);
        cbxTrangThai.setPrefSize(157, 25);

        Label lbBD = new Label("Ngay vao lam:");
        lbBD.setLayoutX(14);
        lbBD.setLayoutY(298);
        txtNgayBD = new TextField();
        txtNgayBD.setDisable(true);
        txtNgayBD.setLayoutX(14);
        txtNgayBD.setLayoutY(323);
        txtNgayBD.setPrefSize(384, 25);

        Label lbKT = new Label("Ngay nghi lam:");
        lbKT.setLayoutX(407);
        lbKT.setLayoutY(298);
        txtNgayKT = new TextField();
        txtNgayKT.setDisable(true);
        txtNgayKT.setLayoutX(408);
        txtNgayKT.setLayoutY(323);
        txtNgayKT.setPrefSize(384, 25);

        Label lbBangLuong = new Label("Bang luong nhan vien:");
        lbBangLuong.setLayoutX(14);
        lbBangLuong.setLayoutY(360);

        tblLuongNV = new TableView<>();
        tblLuongNV.setLayoutX(14);
        tblLuongNV.setLayoutY(386);
        tblLuongNV.setPrefSize(776, 147);
        colMaLuong = new TableColumn<>("Ma luong");
        colMaLuong.setPrefWidth(102.33);
        colTuNgay = new TableColumn<>("Tu ngay");
        colTuNgay.setPrefWidth(105.33);
        colDenNgay = new TableColumn<>("Den ngay");
        colDenNgay.setPrefWidth(116.0);
        colLuongCoBan = new TableColumn<>("Luong co ban");
        colLuongCoBan.setPrefWidth(147.33);
        colPhuCap = new TableColumn<>("Phu cap");
        colPhuCap.setPrefWidth(142.67);
        colGhiChu = new TableColumn<>("Ghi chu");
        colGhiChu.setPrefWidth(163.33);
        tblLuongNV.getColumns().addAll(colMaLuong, colTuNgay, colDenNgay, colLuongCoBan, colPhuCap, colGhiChu);

        btnXoa = new Button("Xoa");
        btnXoa.setLayoutX(13);
        btnXoa.setLayoutY(546);
        btnXoa.setPrefSize(55, 25);
        btnThayDoiLuong = new Button("Thay doi luong");
        btnThayDoiLuong.setLayoutX(390);
        btnThayDoiLuong.setLayoutY(546);
        btnThayDoiLuong.setPrefSize(132, 30);
        btnSuaTaiKhoan = new Button("Sua tai khoan");
        btnSuaTaiKhoan.setLayoutX(532);
        btnSuaTaiKhoan.setLayoutY(546);
        btnSuaTaiKhoan.setPrefSize(116, 30);
        btnLuu = new Button("Luu");
        btnLuu.setLayoutX(717);
        btnLuu.setLayoutY(546);
        btnLuu.setPrefSize(73, 25);
        btnHuy = new Button("Huy");
        btnHuy.setLayoutX(655);
        btnHuy.setLayoutY(546);
        btnHuy.setPrefSize(55, 25);

        root.getChildren().addAll(
                lbTitle, ivTitle, sep, outer, lbNgaySinh, txtNgaySinh, lbTrangThai, cbxTrangThai,
                lbBD, txtNgayBD, lbKT, txtNgayKT, lbBangLuong, tblLuongNV,
                btnXoa, btnThayDoiLuong, btnSuaTaiKhoan, btnLuu, btnHuy
        );
        return root;
    }

    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Khong tim thay CSS: " + path).toExternalForm();
    }

    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Khong tim thay resource: " + path).toExternalForm();
    }
}

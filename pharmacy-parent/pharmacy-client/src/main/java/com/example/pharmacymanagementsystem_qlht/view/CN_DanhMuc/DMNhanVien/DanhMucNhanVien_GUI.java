package com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien;

import com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_Ctrl;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class DanhMucNhanVien_GUI {
    public TextField txtTim;
    public Button btnTim;
    public Button btnLamMoi;
    public Button btnThemNhanVien;
    public TableView<NhanVien> tblNhanVien;
    public TableColumn<NhanVien, String> colSTT;
    public TableColumn<NhanVien, String> colMaNV;
    public TableColumn<NhanVien, String> colTenNV;
    public TableColumn<NhanVien, String> colGioiTinh;
    public TableColumn<NhanVien, String> colSDT;
    public TableColumn<NhanVien, String> colNgaySinh;
    public TableColumn<NhanVien, String> colEmail;
    public TableColumn<NhanVien, String> colDiaChi;
    public TableColumn<NhanVien, String> colTrangThai;
    public TableColumn<NhanVien, String> colCapNhat;

    public void showWithController(Stage stage, DanhMucNhanVien_Ctrl ctrl) {
        AnchorPane root = buildUI();

        ctrl.txtTim = txtTim;
        ctrl.btnTim = btnTim;
        ctrl.btnLamMoi = btnLamMoi;
        ctrl.tblNhanVien = tblNhanVien;
        ctrl.colSTT = colSTT;
        ctrl.colMaNV = colMaNV;
        ctrl.colTenNV = colTenNV;
        ctrl.colGioiTinh = colGioiTinh;
        ctrl.colSDT = colSDT;
        ctrl.colNgaySinh = colNgaySinh;
        ctrl.colEmail = colEmail;
        ctrl.colDiaChi = colDiaChi;
        ctrl.colTrangThai = colTrangThai;
        ctrl.colCapNhat = colCapNhat;

        btnThemNhanVien.setOnAction(ctrl::btnThemNhanVien);

        ctrl.initialize();

        Scene scene = new Scene(root, 1646, 895);
        scene.getStylesheets().add(requireCss("/com/example/pharmacymanagementsystem_qlht/css/QuanLyThuoc.css"));
        stage.setScene(scene);
        stage.setTitle("Danh muc nhan vien");
    }

    private AnchorPane buildUI() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1646, 895);
        root.setStyle("-fx-font-size: 14;");

        txtTim = new TextField();
        txtTim.setLayoutX(12);
        txtTim.setLayoutY(47);
        txtTim.setPrefSize(766, 40);
        txtTim.setPromptText("Tim theo ma, ten nhan vien");

        btnTim = new Button("Tim");
        btnTim.setLayoutX(790);
        btnTim.setLayoutY(47);
        btnTim.setPrefSize(69, 40);
        btnTim.setStyle("-fx-background-color: #0c81ff; -fx-text-fill: white;");

        btnLamMoi = new Button();
        btnLamMoi.setLayoutX(874);
        btnLamMoi.setLayoutY(47);
        btnLamMoi.setPrefSize(45, 40);
        ImageView ivRefresh = new ImageView(new Image(requireRes("/com/example/pharmacymanagementsystem_qlht/img/refresh-3104.png")));
        ivRefresh.setFitWidth(34);
        ivRefresh.setFitHeight(21);
        ivRefresh.setPreserveRatio(true);
        btnLamMoi.setGraphic(ivRefresh);

        Pane paneTitle = new Pane();
        paneTitle.setPrefSize(1632, 40);
        Label lbTitle = new Label("Danh muc nhan vien");
        lbTitle.setLayoutX(12);
        lbTitle.setLayoutY(0);
        lbTitle.setStyle("-fx-font-size: 32; -fx-font-weight: bold;");

        ImageView ivOfficer = new ImageView(new Image(requireRes("/com/example/pharmacymanagementsystem_qlht/img/officer.png")));
        ivOfficer.setFitHeight(36);
        ivOfficer.setFitWidth(40);
        ivOfficer.setLayoutX(320);
        ivOfficer.setLayoutY(6);
        ivOfficer.setPreserveRatio(true);
        paneTitle.getChildren().addAll(lbTitle, ivOfficer);

        btnThemNhanVien = new Button("Them nhan vien");
        btnThemNhanVien.setLayoutX(1488);
        btnThemNhanVien.setLayoutY(47);
        btnThemNhanVien.setPrefSize(150, 40);
        btnThemNhanVien.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");

        tblNhanVien = new TableView<>();
        tblNhanVien.setLayoutX(12);
        tblNhanVien.setLayoutY(98);
        tblNhanVien.setPrefSize(1624, 789);

        colSTT = new TableColumn<>("STT");
        colSTT.setPrefWidth(48.8);
        colMaNV = new TableColumn<>("Ma nhan vien");
        colMaNV.setPrefWidth(125);
        colTenNV = new TableColumn<>("Ten nhan vien");
        colTenNV.setPrefWidth(224);
        colGioiTinh = new TableColumn<>("Gioi tinh");
        colGioiTinh.setPrefWidth(134);
        colSDT = new TableColumn<>("SDT");
        colSDT.setPrefWidth(191);
        colNgaySinh = new TableColumn<>("Ngay sinh");
        colNgaySinh.setPrefWidth(197);
        colEmail = new TableColumn<>("Email");
        colEmail.setPrefWidth(236);
        colDiaChi = new TableColumn<>("Dia chi");
        colDiaChi.setPrefWidth(210);
        colTrangThai = new TableColumn<>("Trang thai");
        colTrangThai.setPrefWidth(135);
        colCapNhat = new TableColumn<>("Cap nhat");
        colCapNhat.setPrefWidth(108);
        tblNhanVien.getColumns().addAll(
                colSTT, colMaNV, colTenNV, colGioiTinh, colSDT, colNgaySinh, colEmail, colDiaChi, colTrangThai, colCapNhat
        );

        root.getChildren().addAll(txtTim, btnTim, btnLamMoi, paneTitle, btnThemNhanVien, tblNhanVien);
        return root;
    }

    private String requireCss(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Khong tim thay CSS: " + path).toExternalForm();
    }

    private String requireRes(String path) {
        return Objects.requireNonNull(getClass().getResource(path), "Khong tim thay resource: " + path).toExternalForm();
    }
}

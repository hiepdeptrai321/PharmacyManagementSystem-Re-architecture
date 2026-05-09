package com.example.pharmacy.client.view;

import com.example.pharmacy.client.controller.ClientHome_Ctrl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ClientHome_GUI {

    public void showWithController(Stage stage, ClientHome_Ctrl ctrl) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        ImageView logo = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacy/client/img/LogoDung.png")
        ).toExternalForm()));
        logo.setFitWidth(96);
        logo.setFitHeight(96);
        logo.setPreserveRatio(true);

        Label lblWelcome = new Label("Xin chao");
        lblWelcome.setFont(Font.font(24));

        Label lblUsername = new Label("Tai khoan: -");
        Label lblRole = new Label("Vai tro: -");
        Label lblShellStatus = new Label("Dang tai shell moi...");
        lblShellStatus.setWrapText(true);
        lblShellStatus.setMaxWidth(520);

        VBox header = new VBox(6, logo, lblWelcome, lblUsername, lblRole, lblShellStatus);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 16, 0));

        Button btnThuoc = menuButton("Thuoc");
        Button btnDonViTinh = menuButton("Don vi tinh");
        Button btnKeHang = menuButton("Ke hang");
        Button btnKhachHang = menuButton("Khach hang");
        Button btnNhaCungCap = menuButton("Nhà cung cấp");
        Button btnNhanVien = menuButton("Danh muc nhan vien");
        Button btnNhomDuocLy = menuButton("Nhom duoc ly");
        Button btnDanhMucKhuyenMai = menuButton("Danh muc khuyen mai");
        Button btnCapNhatKhuyenMai = menuButton("Cap nhat khuyen mai");
        Button btnCapNhatSoLuong = menuButton("Cap nhat so luong");
        Button btnLapHoaDon = menuButton("Lap hoa don");
        Button btnLapPhieuNhap = menuButton("Lap phieu nhap");
        Button btnLapPhieuDatHang = menuButton("Lap phieu dat");
        Button btnTimKiemThuoc = menuButton("Tim thuoc");
        Button btnTimKiemThuocTrongKho = menuButton("Tim thuoc trong kho");
        Button btnTimKiemKhachHang = menuButton("Tim khach hang");
        Button btnTimKiemNhaCungCap = menuButton("Tim nha cung cap");
        Button btnTimKiemHoaDon = menuButton("Tim kiem hoa don");
        Button btnTimKiemPhieuNhap = menuButton("Tim phieu nhap");
        Button btnTimKiemPhieuDat = menuButton("Tim phieu dat");
        Button btnLapPhieuDoi = menuButton("Lap phieu doi");
        Button btnLapPhieuTra = menuButton("Lap phieu tra");
        Button btnTimKiemPhieuDoi = menuButton("Tim kiem phieu doi");
        Button btnTimKiemPhieuTra = menuButton("Tim kiem phieu tra");
        Button btnThongKeBanHang = menuButton("Thong ke ban hang");
        Button btnThongKeTopSanPham = menuButton("Thong ke top san pham");
        Button btnThongKeXnt = menuButton("Thong ke XNT");
        Button btnThuocHetHan = menuButton("Thuoc het han");
        Button btnThuocSapHetHan = menuButton("Thuoc sap het han");
        Button btnTimKiemHoatDong = menuButton("Tim kiem hoat dong");
        Button btnCaiDat = menuButton("Cai dat");
        Button btnLogout = menuButton("Dang xuat");

        VBox sidebar = new VBox(14,
                createSection("Danh muc",
                        btnThuoc,
                        btnDonViTinh,
                        btnKeHang,
                        btnKhachHang,
                        btnNhaCungCap,
                        btnNhanVien,
                        btnNhomDuocLy,
                        btnDanhMucKhuyenMai),
                createSection("Xu ly",
                        btnLapHoaDon,
                        btnLapPhieuNhap,
                        btnLapPhieuDatHang,
                        btnLapPhieuDoi,
                        btnLapPhieuTra,
                        btnCapNhatKhuyenMai,
                        btnCapNhatSoLuong),
                createSection("Tim kiem",
                        btnTimKiemThuoc,
                        btnTimKiemThuocTrongKho,
                        btnTimKiemKhachHang,
                        btnTimKiemNhaCungCap,
                        btnTimKiemHoaDon,
                        btnTimKiemPhieuNhap,
                        btnTimKiemPhieuDat,
                        btnTimKiemPhieuDoi,
                        btnTimKiemPhieuTra,
                        btnTimKiemHoatDong),
                createSection("Thong ke - Bao cao",
                        btnThongKeBanHang,
                        btnThongKeTopSanPham,
                        btnThongKeXnt,
                        btnThuocHetHan,
                        btnThuocSapHetHan),
                createSection("He thong",
                        btnCaiDat,
                        btnLogout)
        );
        sidebar.setPadding(new Insets(0, 12, 0, 0));

        ScrollPane sidebarScroll = new ScrollPane(sidebar);
        sidebarScroll.setFitToWidth(true);
        sidebarScroll.setPrefViewportWidth(240);
        sidebarScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Label lblIntro = new Label("Main shell moi da noi cac man hinh da migrate theo nhom 0 -> 7. Moi chuc nang se mo trong cua so rieng de giu an toan trong dot cleanup cuoi.");
        lblIntro.setWrapText(true);
        lblIntro.setStyle("-fx-font-size: 14px;");
        Label lblHint = new Label("Neu mot man hinh chua co UI doc lap, shell se mo mot hub/placeholder an toan thay vi de app crash.");
        lblHint.setWrapText(true);
        Label lblModules = new Label("Da noi: Dang nhap, master data, kho, hoa don, doi tra, thong ke va audit. Moi menu mo mot cua so rieng de de smoke-test va cleanup legacy an toan.");
        lblModules.setWrapText(true);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        VBox content = new VBox(16,
                lblIntro,
                lblHint,
                lblModules,
                spacer
        );
        content.setPadding(new Insets(16));
        content.setAlignment(Pos.TOP_LEFT);

        root.setTop(header);
        root.setLeft(sidebarScroll);
        root.setCenter(content);

        ctrl.lblWelcome = lblWelcome;
        ctrl.lblUsername = lblUsername;
        ctrl.lblRole = lblRole;
        ctrl.lblShellStatus = lblShellStatus;
        ctrl.btnThuoc = btnThuoc;
        ctrl.btnDonViTinh = btnDonViTinh;
        ctrl.btnKeHang = btnKeHang;
        ctrl.btnKhachHang = btnKhachHang;
        ctrl.btnNhaCungCap = btnNhaCungCap;
        ctrl.btnNhanVien = btnNhanVien;
        ctrl.btnNhomDuocLy = btnNhomDuocLy;
        ctrl.btnDanhMucKhuyenMai = btnDanhMucKhuyenMai;
        ctrl.btnCapNhatKhuyenMai = btnCapNhatKhuyenMai;
        ctrl.btnCapNhatSoLuong = btnCapNhatSoLuong;
        ctrl.btnLapHoaDon = btnLapHoaDon;
        ctrl.btnLapPhieuNhap = btnLapPhieuNhap;
        ctrl.btnLapPhieuDatHang = btnLapPhieuDatHang;
        ctrl.btnTimKiemThuoc = btnTimKiemThuoc;
        ctrl.btnTimKiemThuocTrongKho = btnTimKiemThuocTrongKho;
        ctrl.btnTimKiemKhachHang = btnTimKiemKhachHang;
        ctrl.btnTimKiemNhaCungCap = btnTimKiemNhaCungCap;
        ctrl.btnTimKiemHoaDon = btnTimKiemHoaDon;
        ctrl.btnTimKiemPhieuNhap = btnTimKiemPhieuNhap;
        ctrl.btnTimKiemPhieuDat = btnTimKiemPhieuDat;
        ctrl.btnLapPhieuDoi = btnLapPhieuDoi;
        ctrl.btnLapPhieuTra = btnLapPhieuTra;
        ctrl.btnTimKiemPhieuDoi = btnTimKiemPhieuDoi;
        ctrl.btnTimKiemPhieuTra = btnTimKiemPhieuTra;
        ctrl.btnThongKeBanHang = btnThongKeBanHang;
        ctrl.btnThongKeTopSanPham = btnThongKeTopSanPham;
        ctrl.btnThongKeXnt = btnThongKeXnt;
        ctrl.btnThuocHetHan = btnThuocHetHan;
        ctrl.btnThuocSapHetHan = btnThuocSapHetHan;
        ctrl.btnTimKiemHoatDong = btnTimKiemHoatDong;
        ctrl.btnCaiDat = btnCaiDat;
        ctrl.btnLogout = btnLogout;
        ctrl.initialize();

        stage.setScene(new Scene(root, 1180, 780));
    }

    private Button menuButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    private VBox createSection(String title, Button... buttons) {
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        VBox box = new VBox(8);
        box.getChildren().add(lblTitle);
        for (Button button : buttons) {
            button.setMaxWidth(Double.MAX_VALUE);
            box.getChildren().add(button);
        }
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-border-color: #d7d7d7; -fx-border-radius: 8; -fx-background-radius: 8;");
        return box;
    }
}

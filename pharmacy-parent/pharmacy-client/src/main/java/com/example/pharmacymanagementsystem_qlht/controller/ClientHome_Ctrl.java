package com.example.pharmacymanagementsystem_qlht.controller;

import com.example.pharmacymanagementsystem_qlht.session.SessionContext;
import com.example.pharmacymanagementsystem_qlht.session.UserContext;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.CaiDat.caiDat_GUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientHome_Ctrl {
    public Label lblWelcome;
    public Label lblUsername;
    public Label lblRole;
    public Label lblShellStatus;
    public Button btnLogout;
    public Button btnThuoc;
    public Button btnDonViTinh;
    public Button btnKeHang;
    public Button btnKhachHang;
    public Button btnNhaCungCap;
    public Button btnNhanVien;
    public Button btnNhomDuocLy;
    public Button btnCaiDat;
    public Button btnDanhMucKhuyenMai;
    public Button btnCapNhatKhuyenMai;
    public Button btnCapNhatSoLuong;
    public Button btnLapHoaDon;
    public Button btnLapPhieuNhap;
    public Button btnLapPhieuDatHang;
    public Button btnTimKiemHoaDon;
    public Button btnTimKiemThuoc;
    public Button btnTimKiemThuocTrongKho;
    public Button btnTimKiemKhachHang;
    public Button btnTimKiemNhaCungCap;
    public Button btnTimKiemPhieuNhap;
    public Button btnTimKiemPhieuDat;
    public Button btnLapPhieuDoi;
    public Button btnLapPhieuTra;
    public Button btnTimKiemPhieuDoi;
    public Button btnTimKiemPhieuTra;
    public Button btnThongKeBanHang;
    public Button btnThongKeTopSanPham;
    public Button btnThongKeXnt;
    public Button btnThuocHetHan;
    public Button btnThuocSapHetHan;
    public Button btnTimKiemHoatDong;

    public void initialize() {
        UserContext currentUser = SessionContext.getCurrentUser();
        if (currentUser != null) {
            lblWelcome.setText("Xin chao, " + currentUser.getFullName());
            lblUsername.setText("Tai khoan: " + currentUser.getUsername());
            lblRole.setText("Vai tro: " + currentUser.getRole());
            if (lblShellStatus != null) {
                lblShellStatus.setText("Da ket noi shell moi. Chon chuc nang o menu ben trai de mo cac man da migrate.");
            }
        } else {
            lblWelcome.setText("Chua co phien dang nhap");
            lblUsername.setText("Tai khoan: -");
            lblRole.setText("Vai tro: -");
            if (lblShellStatus != null) {
                lblShellStatus.setText("Chua co phien dang nhap. Vui long dang nhap lai neu can.");
            }
        }

        btnLogout.setOnAction(event -> logout());
        if (btnThuoc != null) {
            btnThuoc.setOnAction(event -> openThuoc());
        }
        if (btnDonViTinh != null) {
            btnDonViTinh.setOnAction(event -> openDonViTinh());
        }
        if (btnKeHang != null) {
            btnKeHang.setOnAction(event -> openKeHang());
        }
        if (btnKhachHang != null) {
            btnKhachHang.setOnAction(event -> openKhachHang());
        }
        if (btnNhaCungCap != null) {
            btnNhaCungCap.setOnAction(event -> openNhaCungCap());
        }
        if (btnNhanVien != null) {
            btnNhanVien.setOnAction(event -> openNhanVien());
        }
        if (btnNhomDuocLy != null) {
            btnNhomDuocLy.setOnAction(event -> openNhomDuocLy());
        }
        if (btnCaiDat != null) {
            btnCaiDat.setOnAction(event -> openCaiDat());
        }
        if (btnDanhMucKhuyenMai != null) {
            btnDanhMucKhuyenMai.setOnAction(event -> openDanhMucKhuyenMai());
        }
        if (btnCapNhatKhuyenMai != null) {
            btnCapNhatKhuyenMai.setOnAction(event -> openCapNhatKhuyenMai());
        }
        if (btnCapNhatSoLuong != null) {
            btnCapNhatSoLuong.setOnAction(event -> openCapNhatSoLuong());
        }
        if (btnLapHoaDon != null) {
            btnLapHoaDon.setOnAction(event -> openLapHoaDon());
        }
        if (btnLapPhieuNhap != null) {
            btnLapPhieuNhap.setOnAction(event -> openLapPhieuNhap());
        }
        if (btnLapPhieuDatHang != null) {
            btnLapPhieuDatHang.setOnAction(event -> openLapPhieuDatHang());
        }
        if (btnTimKiemHoaDon != null) {
            btnTimKiemHoaDon.setOnAction(event -> openTimKiemHoaDon());
        }
        if (btnTimKiemThuoc != null) {
            btnTimKiemThuoc.setOnAction(event -> openTimKiemThuoc());
        }
        if (btnTimKiemThuocTrongKho != null) {
            btnTimKiemThuocTrongKho.setOnAction(event -> openTimKiemThuocTrongKho());
        }
        if (btnTimKiemKhachHang != null) {
            btnTimKiemKhachHang.setOnAction(event -> openTimKiemKhachHang());
        }
        if (btnTimKiemNhaCungCap != null) {
            btnTimKiemNhaCungCap.setOnAction(event -> openTimKiemNhaCungCap());
        }
        if (btnTimKiemPhieuNhap != null) {
            btnTimKiemPhieuNhap.setOnAction(event -> openTimKiemPhieuNhap());
        }
        if (btnTimKiemPhieuDat != null) {
            btnTimKiemPhieuDat.setOnAction(event -> openTimKiemPhieuDat());
        }
        if (btnLapPhieuDoi != null) {
            btnLapPhieuDoi.setOnAction(event -> openLapPhieuDoi());
        }
        if (btnLapPhieuTra != null) {
            btnLapPhieuTra.setOnAction(event -> openLapPhieuTra());
        }
        if (btnTimKiemPhieuDoi != null) {
            btnTimKiemPhieuDoi.setOnAction(event -> openTimKiemPhieuDoi());
        }
        if (btnTimKiemPhieuTra != null) {
            btnTimKiemPhieuTra.setOnAction(event -> openTimKiemPhieuTra());
        }
        if (btnThongKeBanHang != null) {
            btnThongKeBanHang.setOnAction(event -> openThongKeBanHang());
        }
        if (btnThongKeTopSanPham != null) {
            btnThongKeTopSanPham.setOnAction(event -> openThongKeTopSanPham());
        }
        if (btnThongKeXnt != null) {
            btnThongKeXnt.setOnAction(event -> openThongKeXnt());
        }
        if (btnThuocHetHan != null) {
            btnThuocHetHan.setOnAction(event -> showPlaceholder("Thuoc het han", "Bao cao thuoc het han se duoc tach thanh man hinh rieng o dot cleanup sau. Hien tai co the khai thac du lieu tu report server."));
        }
        if (btnThuocSapHetHan != null) {
            btnThuocSapHetHan.setOnAction(event -> showPlaceholder("Thuoc sap het han", "Bao cao thuoc sap het han se duoc tach thanh man hinh rieng o dot cleanup sau. Hien tai co the khai thac du lieu tu report server."));
        }
        if (btnTimKiemHoatDong != null) {
            btnTimKiemHoatDong.setOnAction(event -> openTimKiemHoatDong());
        }
    }

    private void logout() {
        try {
            SessionContext.clear();
            Stage currentStage = (Stage) btnLogout.getScene().getWindow();
            Stage loginStage = new Stage();
            loginStage.setTitle("Dang nhap he thong quan ly nha thuoc");
            loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.view.DangNhap_GUI()
                    .showWithController(loginStage, new DangNhap_Ctrl());
            loginStage.show();
            currentStage.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private Stage createOwnedStage(String title) {
        Stage stage = new Stage();
        if (btnLogout != null && btnLogout.getScene() != null) {
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
        }
        stage.setTitle(title);
        if (getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png") != null) {
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
        }
        return stage;
    }

    private void openStage(String title, StageConsumer consumer) {
        try {
            Stage stage = createOwnedStage(title);
            consumer.accept(stage);
            if (stage.getScene() != null) {
                stage.show();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            showError("Khong the mo man hinh: " + title);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Mo man hinh that bai");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showPlaceholder(String screenName, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(screenName);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openThuoc() {
        openStage("Danh muc thuoc", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMThuoc.DanhMucThuoc_Ctrl().start(stage));
    }

    private void openDonViTinh() {
        openStage("Don vi tinh", stage -> {
            Label lblTitle = new Label("Don vi tinh");
            lblTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            Label lblDescription = new Label("Cac luong DonViTinh da duoc migrate vao client moi. Ban co the them don vi tinh moi hoac mo Danh muc thuoc de thiet lap don vi tinh theo tung thuoc.");
            lblDescription.setWrapText(true);

            Button btnThemDvt = new Button("Them don vi tinh moi");
            btnThemDvt.setMaxWidth(Double.MAX_VALUE);
            btnThemDvt.setOnAction(event -> openThemDonViTinhDialog());

            Button btnMoThuoc = new Button("Mo Danh muc thuoc de thiet lap");
            btnMoThuoc.setMaxWidth(Double.MAX_VALUE);
            btnMoThuoc.setOnAction(event -> openThuoc());

            VBox root = new VBox(12, lblTitle, lblDescription, btnThemDvt, btnMoThuoc);
            root.setAlignment(Pos.CENTER_LEFT);
            root.setPadding(new Insets(20));
            stage.setScene(new Scene(root, 420, 220));
        });
    }

    private void openThemDonViTinhDialog() {
        openStage("Them don vi tinh", stage -> {
            stage.initModality(Modality.WINDOW_MODAL);
            new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatGia.ThemDVT_GUI()
                    .showWithController(stage, new com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatGia.ThemDVT_Ctrl());
        });
    }

    private void openKeHang() {
        openStage("Danh muc ke hang", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKeHang.DanhMucKeHang_Ctrl().start(stage));
    }

    private void openKhachHang() {
        openStage("Danh muc khach hang", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhachHang.DanhMucKhachHang_Ctrl().start(stage));
    }

    private void openNhaCungCap() {
        openStage("Danh muc nha cung cap", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhaCungCap.DanhMucNhaCungCap_Ctrl().start(stage));
    }

    private void openNhanVien() {
        openStage("Danh muc nhan vien", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_Ctrl().start(stage));
    }

    private void openNhomDuocLy() {
        openStage("Danh muc nhom duoc ly", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhomDuocLy.DanhMucNhomDuocLy_Ctrl().start(stage));
    }

    private void openCaiDat() {
        openStage("Cai dat ung dung", stage -> {
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(new caiDat_GUI().createUI(), 360, 180));
        });
    }

    private void openDanhMucKhuyenMai() {
        openStage("Danh muc khuyen mai", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhuyenMai.DanhMucKhuyenMai_Ctrl().start(stage));
    }

    private void openCapNhatKhuyenMai() {
        openStage("Cap nhat khuyen mai", stage ->
            new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_GUI()
                    .showWithController(stage, new com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_Ctrl()));
    }

    private void openCapNhatSoLuong() {
        openStage("Cap nhat so luong", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatSoLuong.CapNhatSoLuongThuoc_Ctrl().start(stage));
    }

    private void openLapHoaDon() {
        openStage("Lap hoa don", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapHoaDon.LapHoaDon_Ctrl().start(stage));
    }

    private void openLapPhieuNhap() {
        openStage("Lap phieu nhap hang", stage -> new com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuNhapHang.LapPhieuNhapHang_GUI()
                .showWithController(stage, new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuNhapHang.LapPhieuNhapHang_Ctrl()));
    }

    private void openLapPhieuDatHang() {
        openStage("Lap phieu dat hang", stage -> new com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.LapPhieuDat.LapPhieuDat_GUI()
                .showWithController(stage, new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuDatHang.LapPhieuDatHang_Ctrl()));
    }

    private void openTimKiemHoaDon() {
        openStage("Tim kiem hoa don", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.TimKiemHoaDon_Ctrl().start(stage));
    }

    private void openTimKiemThuoc() {
        openStage("Tim kiem thuoc", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKThuoc.TimKiemThuoc_Ctrl().start(stage));
    }

    private void openTimKiemThuocTrongKho() {
        openStage("Tim kiem thuoc trong kho", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKThuocTrongKho.TimKiemThuocTrongKho_Ctrl().start(stage));
    }

    private void openTimKiemKhachHang() {
        openStage("Tim kiem khach hang", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKKhachHang.TimKiemKhachHang_Ctrl().start(stage));
    }

    private void openTimKiemNhaCungCap() {
        openStage("Tim kiem nha cung cap", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKNhaCungCap.TimKiemNCC_Ctrl().start(stage));
    }

    private void openTimKiemPhieuNhap() {
        openStage("Tim kiem phieu nhap", stage -> new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuNhapHang.TKPhieuNhapHang_GUI()
                .showWithController(stage, new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuNhapHang.TimKiemPhieuNhap_Ctrl()));
    }

    private void openTimKiemPhieuDat() {
        openStage("Tim kiem phieu dat", stage -> new com.example.pharmacymanagementsystem_qlht.view.CN_TimKiem.TKPhieuDatHang.TKPhieuDatHang_GUI()
                .showWithController(stage, new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDatHang.TKPhieuDatHang_Ctrl()));
    }

    private void openLapPhieuDoi() {
        openStage("Lap phieu doi", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuDoi.LapPhieuDoiHang_Ctrl().start(stage));
    }

    private void openLapPhieuTra() {
        openStage("Lap phieu tra", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuTra.LapPhieuTraHang_Ctrl().start(stage));
    }

    private void openTimKiemPhieuDoi() {
        openStage("Tim kiem phieu doi", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang.TKPhieuDoiHang_Ctrl().start(stage));
    }

    private void openTimKiemPhieuTra() {
        openStage("Tim kiem phieu tra", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang.TKPhieuTraHang_Ctrl().start(stage));
    }

    private void openThongKeBanHang() {
        openStage("Thong ke ban hang", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeBanHang_Ctrl().start(stage));
    }

    private void openThongKeTopSanPham() {
        openStage("Thong ke top san pham", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeTopSanPham_Ctrl().start(stage));
    }

    private void openThongKeXnt() {
        openStage("Thong ke xuat nhap ton", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeXNT_Ctrl().start(stage));
    }

    private void openTimKiemHoatDong() {
        openStage("Tim kiem hoat dong", stage -> new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoatDong.TKHoatDong_Ctrl().start(stage));
    }

    @FunctionalInterface
    private interface StageConsumer {
        void accept(Stage stage) throws Exception;
    }
}

package com.example.pharmacymanagementsystem_qlht.controller;

import com.example.pharmacymanagementsystem_qlht.session.SessionContext;
import com.example.pharmacymanagementsystem_qlht.session.UserContext;
import com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.CaiDat.caiDat_GUI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientHome_Ctrl {
    public Label lblWelcome;
    public Label lblUsername;
    public Label lblRole;
    public Button btnLogout;
    public Button btnNhanVien;
    public Button btnCaiDat;
    public Button btnDanhMucKhuyenMai;
    public Button btnCapNhatKhuyenMai;
    public Button btnLapHoaDon;
    public Button btnTimKiemHoaDon;
    public Button btnLapPhieuDoi;
    public Button btnLapPhieuTra;
    public Button btnTimKiemPhieuDoi;
    public Button btnTimKiemPhieuTra;
    public Button btnThongKeBanHang;
    public Button btnThongKeTopSanPham;
    public Button btnThongKeXnt;
    public Button btnTimKiemHoatDong;

    public void initialize() {
        UserContext currentUser = SessionContext.getCurrentUser();
        if (currentUser != null) {
            lblWelcome.setText("Xin chao, " + currentUser.getFullName());
            lblUsername.setText("Tai khoan: " + currentUser.getUsername());
            lblRole.setText("Vai tro: " + currentUser.getRole());
        } else {
            lblWelcome.setText("Chua co phien dang nhap");
            lblUsername.setText("Tai khoan: -");
            lblRole.setText("Vai tro: -");
        }

        btnLogout.setOnAction(event -> logout());
        if (btnNhanVien != null) {
            btnNhanVien.setOnAction(event -> openNhanVien());
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
        if (btnLapHoaDon != null) {
            btnLapHoaDon.setOnAction(event -> openLapHoaDon());
        }
        if (btnTimKiemHoaDon != null) {
            btnTimKiemHoaDon.setOnAction(event -> openTimKiemHoaDon());
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

    private void openNhanVien() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.DanhMucNhanVien_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openCaiDat() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Cai dat ung dung");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            stage.setScene(new Scene(new caiDat_GUI().createUI(), 360, 180));
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openDanhMucKhuyenMai() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMKhuyenMai.DanhMucKhuyenMai_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openCapNhatKhuyenMai() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.view.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_GUI()
                    .showWithController(stage, new com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatKhuyenMai.CapNhatKhuyenMai_Ctrl());
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openLapHoaDon() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapHoaDon.LapHoaDon_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openTimKiemHoaDon() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoaDon.TimKiemHoaDon_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openLapPhieuDoi() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuDoi.LapPhieuDoiHang_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openLapPhieuTra() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.LapPhieuTra.LapPhieuTraHang_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openTimKiemPhieuDoi() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuDoiHang.TKPhieuDoiHang_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openTimKiemPhieuTra() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKPhieuTraHang.TKPhieuTraHang_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openThongKeBanHang() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeBanHang_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openThongKeTopSanPham() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeTopSanPham_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openThongKeXnt() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_ThongKe.ThongKeXNT_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void openTimKiemHoatDong() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage) btnLogout.getScene().getWindow());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/pharmacymanagementsystem_qlht/img/logoNguyenBan.png")));
            new com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKHoatDong.TKHoatDong_Ctrl().start(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

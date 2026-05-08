package com.example.pharmacymanagementsystem_qlht.view;

import com.example.pharmacymanagementsystem_qlht.controller.ClientHome_Ctrl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class ClientHome_GUI {

    public void showWithController(Stage stage, ClientHome_Ctrl ctrl) {
        VBox root = new VBox(16);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(32));

        ImageView logo = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResource("/com/example/pharmacymanagementsystem_qlht/img/LogoDung.png")
        ).toExternalForm()));
        logo.setFitWidth(140);
        logo.setFitHeight(140);
        logo.setPreserveRatio(true);

        Label lblWelcome = new Label("Xin chao");
        lblWelcome.setFont(Font.font(24));

        Label lblUsername = new Label("Tai khoan: -");
        Label lblRole = new Label("Vai tro: -");
        Label lblNote = new Label("Client multi-module da duoc migrate. Cac man hinh legacy con lai se duoc tach tiep theo tung dot.");
        lblNote.setWrapText(true);
        lblNote.setMaxWidth(420);

        Button btnNhanVien = new Button("Danh muc nhan vien");
        Button btnDanhMucKhuyenMai = new Button("Danh muc khuyen mai");
        Button btnCapNhatKhuyenMai = new Button("Cap nhat khuyen mai");
        Button btnLapHoaDon = new Button("Lap hoa don");
        Button btnTimKiemHoaDon = new Button("Tim kiem hoa don");
        Button btnLapPhieuDoi = new Button("Lap phieu doi");
        Button btnLapPhieuTra = new Button("Lap phieu tra");
        Button btnTimKiemPhieuDoi = new Button("Tim kiem phieu doi");
        Button btnTimKiemPhieuTra = new Button("Tim kiem phieu tra");
        Button btnThongKeBanHang = new Button("Thong ke ban hang");
        Button btnThongKeTopSanPham = new Button("Thong ke top san pham");
        Button btnThongKeXnt = new Button("Thong ke XNT");
        Button btnTimKiemHoatDong = new Button("Tim kiem hoat dong");
        Button btnCaiDat = new Button("Cai dat");
        Button btnLogout = new Button("Dang xuat");

        root.getChildren().addAll(
                logo,
                lblWelcome,
                lblUsername,
                lblRole,
                lblNote,
                btnNhanVien,
                btnDanhMucKhuyenMai,
                btnCapNhatKhuyenMai,
                btnLapHoaDon,
                btnTimKiemHoaDon,
                btnLapPhieuDoi,
                btnLapPhieuTra,
                btnTimKiemPhieuDoi,
                btnTimKiemPhieuTra,
                btnThongKeBanHang,
                btnThongKeTopSanPham,
                btnThongKeXnt,
                btnTimKiemHoatDong,
                btnCaiDat,
                btnLogout
        );

        ctrl.lblWelcome = lblWelcome;
        ctrl.lblUsername = lblUsername;
        ctrl.lblRole = lblRole;
        ctrl.btnNhanVien = btnNhanVien;
        ctrl.btnDanhMucKhuyenMai = btnDanhMucKhuyenMai;
        ctrl.btnCapNhatKhuyenMai = btnCapNhatKhuyenMai;
        ctrl.btnLapHoaDon = btnLapHoaDon;
        ctrl.btnTimKiemHoaDon = btnTimKiemHoaDon;
        ctrl.btnLapPhieuDoi = btnLapPhieuDoi;
        ctrl.btnLapPhieuTra = btnLapPhieuTra;
        ctrl.btnTimKiemPhieuDoi = btnTimKiemPhieuDoi;
        ctrl.btnTimKiemPhieuTra = btnTimKiemPhieuTra;
        ctrl.btnThongKeBanHang = btnThongKeBanHang;
        ctrl.btnThongKeTopSanPham = btnThongKeTopSanPham;
        ctrl.btnThongKeXnt = btnThongKeXnt;
        ctrl.btnTimKiemHoatDong = btnTimKiemHoatDong;
        ctrl.btnCaiDat = btnCaiDat;
        ctrl.btnLogout = btnLogout;
        ctrl.initialize();

        stage.setScene(new Scene(root, 560, 660));
    }
}

package com.example.pharmacymanagementsystem_qlht.controller.CN_TimKiem.TKNhaCungCap;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;
import javafx.fxml.FXML; // (Có thể giữ lại)
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// Bỏ import Application và Platform vì file này không phải là Application
// import javafx.application.Application;
// import javafx.application.Platform;

public class ChiTietNhaCungCap_Ctrl {
    // --- ĐÃ CHUYỂN SANG PUBLIC ---
    public TextField DiaChi;
    public TextField Email;
    public TextField GPKD;
    public TextArea GhiChu;
    public TextField MST;
    public TextField SDT;
    public TextField TenCongTy;
    public Button btnThoat;
    public TextField maNCC;
    public TextField tenNCC;

    private NhaCungCap nhaCungCap;

    // --- ĐÃ CẬP NHẬT LOGIC `initialize` VÀ `hienThiThongTin` ---

    // 1. Gán sự kiện VÀ điền dữ liệu
    public void initialize() {
        btnThoat.setOnAction(e -> anThoat());

        // Điền dữ liệu vào form (được gọi sau khi hienThiThongTin đã lưu)
        if (nhaCungCap != null) {
            maNCC.setText(nhaCungCap.getMaNCC());
            tenNCC.setText(nhaCungCap.getTenNCC() != null ? nhaCungCap.getTenNCC() : "");
            DiaChi.setText(nhaCungCap.getDiaChi() != null ? nhaCungCap.getDiaChi() : "");
            SDT.setText(nhaCungCap.getSDT() != null ? nhaCungCap.getSDT() : "");
            Email.setText(nhaCungCap.getEmail() != null ? nhaCungCap.getEmail() : "");
            GPKD.setText(nhaCungCap.getGPKD() != null ? nhaCungCap.getGPKD() : "");
            MST.setText(nhaCungCap.getMSThue() != null ? nhaCungCap.getMSThue() : "");
            TenCongTy.setText(nhaCungCap.getTenCongTy() != null ? nhaCungCap.getTenCongTy() : "");
            GhiChu.setText(nhaCungCap.getGhiChu() != null ? nhaCungCap.getGhiChu() : "");
        }

        // (Bỏ Platform.runLater vì icon được gán ở Controller cha)
    }

    // 2. Chỉ lưu dữ liệu
    public void hienThiThongTin(NhaCungCap ncc) {
        if (ncc != null) {
            this.nhaCungCap = ncc;
        }

        // (Bỏ setNhaCungCap vì hienThiThongTin làm điều tương tự)
    }

    // --- LOGIC CÒN LẠI GIỮ NGUYÊN ---
    @FXML
    private void anThoat() {
        Stage stage = (Stage) btnThoat.getScene().getWindow();
        stage.close();
    }
}
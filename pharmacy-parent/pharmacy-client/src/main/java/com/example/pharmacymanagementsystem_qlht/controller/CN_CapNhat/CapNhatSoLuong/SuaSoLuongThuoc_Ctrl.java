package com.example.pharmacymanagementsystem_qlht.controller.CN_CapNhat.CapNhatSoLuong;

import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;
import com.example.pharmacymanagementsystem_qlht.service.TonKhoService;
import com.example.pharmacymanagementsystem_qlht.session.SessionContext;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SuaSoLuongThuoc_Ctrl {
    public Button btnLuu;
    public Button btnHuy;
    public TextField tfMaThuoc;
    public TextField tfTenThuoc;
    public TextField tfSoLuongTon;
    public ComboBox<String> cbViTri;
    public ComboBox<String> cbLoaiHang;

    private final TonKhoService tonKhoService = new TonKhoService();
    private Thuoc_SP_TheoLo thuocTheoLo;

    public void setThuoc(Thuoc_SP_TheoLo thuocLo) {
        if (thuocLo == null) {
            return;
        }
        this.thuocTheoLo = thuocLo;
        Thuoc_SanPham thuoc = thuocLo.getThuoc();
        tfMaThuoc.setText(thuoc.getMaThuoc());
        tfTenThuoc.setText(thuoc.getTenThuoc());
        tfSoLuongTon.setText(String.valueOf(thuocLo.getSoLuongTon()));
        cbViTri.setValue(thuoc.getVitri() == null ? "" : thuoc.getVitri().getTenKe());
        cbLoaiHang.setValue(thuoc.getLoaiHang() == null ? "" : thuoc.getLoaiHang().getTenLoaiHang());
        btnHuy.setOnAction(e -> btnHuyClick());
        btnLuu.setOnAction(e -> btnLuuClick());
    }

    public void initialize() {
        Platform.runLater(() -> tfSoLuongTon.requestFocus());
    }

    private void btnLuuClick() {
        if (thuocTheoLo == null) {
            return;
        }
        try {
            int newSoLuong = Integer.parseInt(tfSoLuongTon.getText().trim());
            thuocTheoLo.setSoLuongTon(newSoLuong);
            tonKhoService.updateLotQuantity(thuocTheoLo, SessionContext.requireCurrentUser());
            closeWindow();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void btnHuyClick() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) tfMaThuoc.getScene().getWindow();
        stage.close();
    }
}

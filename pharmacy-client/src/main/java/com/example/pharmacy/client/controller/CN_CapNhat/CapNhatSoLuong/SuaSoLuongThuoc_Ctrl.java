package com.example.pharmacy.client.controller.CN_CapNhat.CapNhatSoLuong;

import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;
import com.example.pharmacy.client.service.TonKhoService;
import com.example.pharmacy.client.session.SessionContext;
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
    private Thuoc_SP_TheoLoDto thuocTheoLo;

    public void setThuoc(Thuoc_SP_TheoLoDto thuocLo) {
        if (thuocLo == null) {
            return;
        }
        this.thuocTheoLo = thuocLo;
        Thuoc_SanPhamDto thuoc = thuocLo.getThuoc();
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

package com.example.pharmacy.client.controller.CN_DanhMuc.DMNhanVien;

import com.example.pharmacy.client.TienIch.VNDFormatter;
import com.example.pharmacy.common.model.LuongNhanVienDto;
import com.example.pharmacy.common.model.NhanVienDto;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ThietLapLuongNV_Ctrl {
    public TextField txtMaNV;
    public TextField txtTenNV;
    public TextField txtLuongHT;
    public TextArea txtGhiChu;
    public NhanVienDto nhanVien;
    public TextField txtPhuCapHienTai;
    public TextField txtLuongMoi;
    public TextField txtPhuCapMoi;
    public LuongNhanVienDto luongNhanVien;
    public boolean isSaved = false;

    private final VNDFormatter vndFormatter = new VNDFormatter();

    public void initialize(NhanVienDto nhanVien, LuongNhanVienDto luongNhanVien) {
        vndFormatter.applyNumberFormatter(txtLuongMoi);
        vndFormatter.applyNumberFormatter(txtPhuCapMoi);
        txtMaNV.setText(nhanVien.getMaNV());
        txtTenNV.setText(nhanVien.getTenNV());
        txtLuongHT.setText(new VNDFormatter().format(luongNhanVien.getLuongCoBan()));
        txtPhuCapHienTai.setText(new VNDFormatter().format(luongNhanVien.getPhuCap()));
        txtGhiChu.setText(luongNhanVien.getGhiChu() == null ? "" : luongNhanVien.getGhiChu());
        this.nhanVien = nhanVien;
        this.luongNhanVien = luongNhanVien;
    }

    public void btnLuu(ActionEvent actionEvent) {
        isSaved = true;
        luongNhanVien.setLuongCoBan(vndFormatter.parseFormattedNumber(txtLuongMoi.getText()));
        luongNhanVien.setPhuCap(vndFormatter.parseFormattedNumber(txtPhuCapMoi.getText()));
        luongNhanVien.setGhiChu(txtGhiChu.getText());
        dong();
    }

    public void btnHuy(ActionEvent actionEvent) {
        isSaved = false;
        dong();
    }

    private void dong() {
        Stage stage = (Stage) txtMaNV.getScene().getWindow();
        stage.close();
    }
}

package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TraHangItem {
    private final ChiTietHoaDon goc;
    private final IntegerProperty soLuongTra = new SimpleIntegerProperty(1);
    private final StringProperty lyDo = new SimpleStringProperty("");

    public TraHangItem(ChiTietHoaDon goc, int soLuongTra, String lyDo) {
        this.goc = goc;
        this.soLuongTra.set(Math.max(1, soLuongTra));
        this.lyDo.set(lyDo == null ? "" : lyDo.trim());
    }

    public ChiTietHoaDon getGoc() { return goc; }

    public int getSoLuongTra() { return soLuongTra.get(); }
    public void setSoLuongTra(int v) { this.soLuongTra.set(Math.max(1, v)); }
    public IntegerProperty soLuongTraProperty() { return soLuongTra; }

    public String getLyDo() { return lyDo.get(); }
    public void setLyDo(String v) { this.lyDo.set(v == null ? "" : v.trim()); }
    public StringProperty lyDoProperty() { return lyDo; }
    public double getDonGiaUnit() {
        if (goc == null) return 0;
        return goc.getDonGia();
    }

    public double getThanhTienTra() {
        if (goc == null) return 0;
        int slTra = getSoLuongTra();
        double perUnitDiscount = (goc.getSoLuong() > 0) ? Math.max(0, goc.getGiamGia() / (double) goc.getSoLuong()) : 0;
        double giamGiaTra = perUnitDiscount * slTra;
        return Math.max(0, slTra * goc.getDonGia() - giamGiaTra);
    }
}

package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DoiHangItem {
    private final ChiTietHoaDon goc;
    private final IntegerProperty soLuongDoi = new SimpleIntegerProperty(1);
    private final StringProperty lyDo = new SimpleStringProperty("");

    public DoiHangItem(ChiTietHoaDon goc, int soLuongDefault, String lyDoDefault) {
        this.goc = goc;
        setSoLuongDoi(Math.max(1, soLuongDefault));
        setLyDo(lyDoDefault == null ? "" : lyDoDefault);
    }

    public ChiTietHoaDon getGoc() { return goc; }

    public int getSoLuongDoi() { return soLuongDoi.get(); }
    public void setSoLuongDoi(int v) { soLuongDoi.set(v); }
    public IntegerProperty soLuongDoiProperty() { return soLuongDoi; }

    public String getLyDo() { return lyDo.get(); }
    public void setLyDo(String v) { lyDo.set(v); }
    public StringProperty lyDoProperty() { return lyDo; }
}

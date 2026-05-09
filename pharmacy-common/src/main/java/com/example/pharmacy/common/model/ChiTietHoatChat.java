package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ChiTietHoatChat implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private HoatChat hoatChat;
    private Thuoc_SanPham thuoc;
    private float hamLuong;

    public ChiTietHoatChat() {
    }
    public ChiTietHoatChat(HoatChat hoatChat, Thuoc_SanPham thuoc, float hamLuong) {
        this.hoatChat = hoatChat;
        this.thuoc = thuoc;
        this.hamLuong = hamLuong;
    }
//    getters and setters
    public HoatChat getHoatChat() {
        return hoatChat;
    }

    public void setHoatChat(HoatChat hoatChat) {
        this.hoatChat = hoatChat;
    }

    public Thuoc_SanPham getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc_SanPham thuoc) {
        this.thuoc = thuoc;
    }

    public float getHamLuong() {
        return hamLuong;
    }

    public void setHamLuong(float hamLuong) {
        this.hamLuong = hamLuong;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietHoatChat that = (ChiTietHoatChat) o;
        return Objects.equals(hoatChat, that.hoatChat) && Objects.equals(thuoc, that.thuoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hoatChat, thuoc);
    }

    @Override
    public String toString() {
        return "ChiTietHoatChat{" +
                "hoatChat=" + hoatChat +
                ", thuoc=" + thuoc +
                ", hamLuong=" + hamLuong +
                '}';
    }
}

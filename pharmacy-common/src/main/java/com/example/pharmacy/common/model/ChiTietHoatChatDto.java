package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ChiTietHoatChatDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private HoatChatDto hoatChat;
    private Thuoc_SanPhamDto thuoc;
    private float hamLuong;

    public ChiTietHoatChatDto() {
    }
    public ChiTietHoatChatDto(HoatChatDto hoatChat, Thuoc_SanPhamDto thuoc, float hamLuong) {
        this.hoatChat = hoatChat;
        this.thuoc = thuoc;
        this.hamLuong = hamLuong;
    }
//    getters and setters
    public HoatChatDto getHoatChat() {
        return hoatChat;
    }

    public void setHoatChat(HoatChatDto hoatChat) {
        this.hoatChat = hoatChat;
    }

    public Thuoc_SanPhamDto getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc_SanPhamDto thuoc) {
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
        ChiTietHoatChatDto that = (ChiTietHoatChatDto) o;
        return Objects.equals(hoatChat, that.hoatChat) && Objects.equals(thuoc, that.thuoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hoatChat, thuoc);
    }

    @Override
    public String toString() {
        return "ChiTietHoatChatDto{" +
                "hoatChat=" + hoatChat +
                ", thuoc=" + thuoc +
                ", hamLuong=" + hamLuong +
                '}';
    }
}

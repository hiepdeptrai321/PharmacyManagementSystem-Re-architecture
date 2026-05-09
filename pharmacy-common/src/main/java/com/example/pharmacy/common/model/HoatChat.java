package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class HoatChat implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maHoatChat;
    private String tenHoatChat;

    public HoatChat() {
    }

    public HoatChat(String maHoatChat, String tenHoatChat) {
        this.maHoatChat = maHoatChat;
        this.tenHoatChat = tenHoatChat;
    }

    public String getMaHoatChat() {
        return maHoatChat;
    }

    public void setMaHoatChat(String maHoatChat) {
        this.maHoatChat = maHoatChat;
    }

    public String getTenHoatChat() {
        return tenHoatChat;
    }

    public void setTenHoatChat(String tenHoatChat) {
        this.tenHoatChat = tenHoatChat;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HoatChat hoatChat = (HoatChat) o;
        return Objects.equals(maHoatChat, hoatChat.maHoatChat);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maHoatChat);
    }

    @Override
    public String toString() {
        return "HoatChat{" +
                "maHoatChat='" + maHoatChat + '\'' +
                ", tenHoatChat='" + tenHoatChat + '\'' +
                '}';
    }
}

package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChiTietHoatChat")
@IdClass(ChiTietHoatChatId.class)
@Getter @Setter @NoArgsConstructor
public class ChiTietHoatChatEntity {
    @Id
    @Column(name = "MaHoatChat")
    private String maHoatChat;

    @Id
    @Column(name = "MaThuoc")
    private String maThuoc;

    @Column(name = "HamLuong", nullable = false)
    private double hamLuong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaHoatChat", insertable = false, updatable = false)
    private HoatChatEntity hoatChat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaThuoc", insertable = false, updatable = false)
    private ThuocSanPhamEntity thuocSanPham;
}

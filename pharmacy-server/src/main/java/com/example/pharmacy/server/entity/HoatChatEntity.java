package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HoatChat")
@Getter @Setter @NoArgsConstructor
public class HoatChatEntity {
    @Id
    @Column(name = "MaHoatChat", nullable = false, length = 10)
    private String maHoatChat;

    @Column(name = "TenHoatChat", nullable = false, length = 50)
    private String tenHoatChat;
}

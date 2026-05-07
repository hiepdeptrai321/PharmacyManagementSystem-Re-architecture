package com.example.pharmacy.server.repository;

import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;

import java.util.List;

public interface TonKhoRepository {
    List<Thuoc_SP_TheoLo> findAllLots();

    boolean updateLotQuantity(Thuoc_SP_TheoLo thuocTheoLo);
}

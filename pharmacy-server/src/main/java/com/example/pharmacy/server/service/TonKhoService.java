package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLo;

import java.util.List;

public interface TonKhoService {
    List<Thuoc_SP_TheoLo> findAllLots();

    boolean updateLotQuantity(Thuoc_SP_TheoLo thuocTheoLo, UserContext actor);
}

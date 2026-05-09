package com.example.pharmacy.client.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;

import java.util.List;

public interface TonKhoClientService {
    List<Thuoc_SP_TheoLoDto> findAllLots();

    boolean updateLotQuantity(Thuoc_SP_TheoLoDto thuocTheoLo, UserContext actor);
}

package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;

import java.util.List;

public interface TonKhoRepository {
    List<Thuoc_SP_TheoLoDto> findAllLots();

    boolean updateLotQuantity(Thuoc_SP_TheoLoDto thuocTheoLo);
}

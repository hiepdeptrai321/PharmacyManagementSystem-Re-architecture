package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.RmiTonKhoClientService;
import com.example.pharmacy.client.service.TonKhoClientService;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLo;
import com.example.pharmacy.common.session.UserContext;
import com.example.pharmacy.client.session.UserContextMapper;

import java.util.List;

public class TonKhoService {
    private final TonKhoClientService tonKhoClientService =
            new RmiTonKhoClientService(new RmiClientProvider());

    public List<Thuoc_SP_TheoLo> findAllLots() {
        return tonKhoClientService.findAllLots();
    }

    public boolean updateLotQuantity(Thuoc_SP_TheoLo thuocTheoLo, UserContext actor) {
        return tonKhoClientService.updateLotQuantity(thuocTheoLo, UserContextMapper.toRemoteUserContext(actor));
    }
}

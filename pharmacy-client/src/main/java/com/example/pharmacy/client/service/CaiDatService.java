package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.CaiDatClientService;
import com.example.pharmacy.client.service.RmiCaiDatClientService;
import com.example.pharmacy.common.model.CaiDatDto;

import java.util.List;

public class CaiDatService {
    private final CaiDatClientService caiDatClientService =
            new RmiCaiDatClientService(new RmiClientProvider());

    public List<CaiDatDto> findAll() {
        return caiDatClientService.findAll();
    }

    public boolean update(CaiDatDto caiDat) {
        return caiDatClientService.update(caiDat);
    }
}

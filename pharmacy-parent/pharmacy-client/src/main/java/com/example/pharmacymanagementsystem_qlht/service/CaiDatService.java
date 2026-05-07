package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.CaiDatClientService;
import com.example.pharmacy.client.service.RmiCaiDatClientService;
import com.example.pharmacymanagementsystem_qlht.model.CaiDat;

import java.util.List;

public class CaiDatService {
    private final CaiDatClientService caiDatClientService =
            new RmiCaiDatClientService(new RmiClientProvider());

    public List<CaiDat> findAll() {
        return caiDatClientService.findAll();
    }

    public boolean update(CaiDat caiDat) {
        return caiDatClientService.update(caiDat);
    }
}

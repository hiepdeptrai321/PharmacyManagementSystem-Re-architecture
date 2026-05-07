package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacymanagementsystem_qlht.dao.CaiDat_Dao;
import com.example.pharmacymanagementsystem_qlht.model.CaiDat;

import java.util.List;

public class CaiDatService {
    private final CaiDat_Dao caiDatDao = new CaiDat_Dao();

    public List<CaiDat> findAll() {
        return caiDatDao.selectAll();
    }

    public boolean update(CaiDat caiDat) {
        return caiDatDao.update(caiDat);
    }
}

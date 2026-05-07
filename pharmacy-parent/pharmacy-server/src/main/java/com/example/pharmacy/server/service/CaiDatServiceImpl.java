package com.example.pharmacy.server.service;

import com.example.pharmacy.server.entity.ThongSoUngDungEntity;
import com.example.pharmacy.server.repository.CaiDatRepository;
import com.example.pharmacymanagementsystem_qlht.model.CaiDat;

import java.util.List;
import java.util.Objects;

public class CaiDatServiceImpl implements CaiDatService {
    private final CaiDatRepository caiDatRepository;

    public CaiDatServiceImpl(CaiDatRepository caiDatRepository) {
        this.caiDatRepository = Objects.requireNonNull(caiDatRepository, "caiDatRepository must not be null");
    }

    @Override
    public List<CaiDat> findAll() {
        return caiDatRepository.findAll().stream()
                .map(entity -> new CaiDat(entity.getTenThongSo(), entity.getGiaTri()))
                .toList();
    }

    @Override
    public boolean update(CaiDat caiDat) {
        if (caiDat == null || caiDat.getTenThongSo() == null || caiDat.getTenThongSo().isBlank()) {
            return false;
        }
        try {
            ThongSoUngDungEntity entity = new ThongSoUngDungEntity();
            entity.setTenThongSo(caiDat.getTenThongSo());
            entity.setGiaTri(caiDat.getGiaTri());
            return caiDatRepository.update(entity);
        } catch (RuntimeException exception) {
            return false;
        }
    }
}

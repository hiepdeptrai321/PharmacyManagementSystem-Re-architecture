package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.DonViTinhClientService;
import com.example.pharmacy.client.service.RmiDonViTinhClientService;
import com.example.pharmacymanagementsystem_qlht.model.DonViTinh;

import java.util.List;

public class DonViTinhService {
    private final DonViTinhClientService donViTinhClientService =
            new RmiDonViTinhClientService(new RmiClientProvider());

    public List<DonViTinh> findAll() {
        return donViTinhClientService.findAll();
    }

    public DonViTinh findById(String maDvt) {
        return donViTinhClientService.findById(maDvt);
    }

    public DonViTinh selectByTenDVT(String tenDvt) {
        return donViTinhClientService.findByTenDonViTinh(tenDvt);
    }

    public String generatekeyDonViTinh() {
        return donViTinhClientService.generateNewMaDVT();
    }

    public boolean insert(DonViTinh donViTinh) {
        return donViTinhClientService.create(donViTinh);
    }

    public boolean update(DonViTinh donViTinh) {
        return donViTinhClientService.update(donViTinh);
    }

    public boolean deleteById(String maDvt) {
        return donViTinhClientService.deleteById(maDvt);
    }
}

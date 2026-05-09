package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.DonViTinhClientService;
import com.example.pharmacy.client.service.RmiDonViTinhClientService;
import com.example.pharmacy.common.model.DonViTinhDto;

import java.util.List;

public class DonViTinhService {
    private final DonViTinhClientService donViTinhClientService =
            new RmiDonViTinhClientService(new RmiClientProvider());

    public List<DonViTinhDto> findAll() {
        return donViTinhClientService.findAll();
    }

    public DonViTinhDto findById(String maDvt) {
        return donViTinhClientService.findById(maDvt);
    }

    public DonViTinhDto selectByTenDVT(String tenDvt) {
        return donViTinhClientService.findByTenDonViTinh(tenDvt);
    }

    public String generatekeyDonViTinh() {
        return donViTinhClientService.generateNewMaDVT();
    }

    public boolean insert(DonViTinhDto donViTinh) {
        return donViTinhClientService.create(donViTinh);
    }

    public boolean update(DonViTinhDto donViTinh) {
        return donViTinhClientService.update(donViTinh);
    }

    public boolean deleteById(String maDvt) {
        return donViTinhClientService.deleteById(maDvt);
    }
}

package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.CreateThuocRequest;

public interface ThuocCatalogService {
    String createThuocWithBaseUnit(CreateThuocRequest request, UserContext actor);
}

package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.PhieuNhapRequest;

public interface PhieuNhapService {
    String createPurchaseOrder(PhieuNhapRequest request, UserContext actor);
}

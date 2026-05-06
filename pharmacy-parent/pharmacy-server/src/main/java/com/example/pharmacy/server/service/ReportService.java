package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.ExpiringLotDTO;
import com.example.pharmacy.common.dto.RevenuePointDTO;
import com.example.pharmacy.common.dto.TopSellingProductDTO;
import com.example.pharmacy.common.request.DateRangeRequest;

import java.util.List;

public interface ReportService {
    List<RevenuePointDTO> getRevenueByDateRange(DateRangeRequest request);

    List<TopSellingProductDTO> getTopSellingProducts(DateRangeRequest request, int limit);

    List<ExpiringLotDTO> getExpiredLots();

    List<ExpiringLotDTO> getExpiringLots(int thresholdDays);
}

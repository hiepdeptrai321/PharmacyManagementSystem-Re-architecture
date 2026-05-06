package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.dto.ExpiringLotDTO;
import com.example.pharmacy.common.dto.RevenuePointDTO;
import com.example.pharmacy.common.dto.TopSellingProductDTO;
import com.example.pharmacy.common.request.DateRangeRequest;

import java.util.List;

public interface ReportRepository {
    List<RevenuePointDTO> findRevenueByDateRange(DateRangeRequest request);

    List<TopSellingProductDTO> findTopSellingProducts(DateRangeRequest request, int limit);

    List<ExpiringLotDTO> findExpiredLots();

    List<ExpiringLotDTO> findExpiringLots(int thresholdDays);
}

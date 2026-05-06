package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.ExpiringLotDTO;
import com.example.pharmacy.common.dto.RevenuePointDTO;
import com.example.pharmacy.common.dto.TopSellingProductDTO;
import com.example.pharmacy.common.exception.BusinessException;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.server.repository.ReportRepository;

import java.util.List;
import java.util.Objects;

public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = Objects.requireNonNull(reportRepository, "reportRepository must not be null");
    }

    @Override
    public List<RevenuePointDTO> getRevenueByDateRange(DateRangeRequest request) {
        validateDateRangeRequest(request);
        return reportRepository.findRevenueByDateRange(request);
    }

    @Override
    public List<TopSellingProductDTO> getTopSellingProducts(DateRangeRequest request, int limit) {
        validateDateRangeRequest(request);
        if (limit <= 0) {
            throw new BusinessException("Limit phai lon hon 0.");
        }
        return reportRepository.findTopSellingProducts(request, limit);
    }

    @Override
    public List<ExpiringLotDTO> getExpiredLots() {
        return reportRepository.findExpiredLots();
    }

    @Override
    public List<ExpiringLotDTO> getExpiringLots(int thresholdDays) {
        if (thresholdDays < 0) {
            throw new BusinessException("Nguong canh bao het han khong hop le.");
        }
        return reportRepository.findExpiringLots(thresholdDays);
    }

    private void validateDateRangeRequest(DateRangeRequest request) {
        if (request == null || request.getFromDate() == null || request.getToDate() == null) {
            throw new BusinessException("Khoang ngay bao cao khong hop le.");
        }
        if (request.getToDate().isBefore(request.getFromDate())) {
            throw new BusinessException("Ngay ket thuc khong duoc truoc ngay bat dau.");
        }
    }
}

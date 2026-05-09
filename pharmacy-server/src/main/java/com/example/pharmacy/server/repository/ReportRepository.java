package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.dto.ExpiringLotDTO;
import com.example.pharmacy.common.dto.RevenuePointDTO;
import com.example.pharmacy.common.dto.TopSellingProductDTO;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.common.model.HoaDonDisplayDto;
import com.example.pharmacy.common.model.ThongKeBanHangDto;
import com.example.pharmacy.common.model.ThongKeTonKhoDto;
import com.example.pharmacy.common.model.ThongKeTopSanPhamDto;
import com.example.pharmacy.common.model.ThuocHetHanDto;

import java.util.List;

public interface ReportRepository {
    List<ThongKeBanHangDto> findThongKeBanHangByDateRange(DateRangeRequest request, ReportBucket bucket);

    List<HoaDonDisplayDto> findHoaDonByDateRange(DateRangeRequest request);

    List<ThongKeTopSanPhamDto> findTopBanChayByDateRange(DateRangeRequest request, int limit);

    List<ThongKeTopSanPhamDto> findTopDoanhThuByDateRange(DateRangeRequest request, int limit);

    List<ThongKeTonKhoDto> findThongKeXnt(DateRangeRequest request);

    List<ThuocHetHanDto> findThuocHetHan();

    List<RevenuePointDTO> findRevenueByDateRange(DateRangeRequest request);

    List<TopSellingProductDTO> findTopSellingProducts(DateRangeRequest request, int limit);

    List<ExpiringLotDTO> findExpiredLots();

    List<ExpiringLotDTO> findExpiringLots(int thresholdDays);

    enum ReportBucket {
        HOUR,
        DAY,
        MONTH
    }
}

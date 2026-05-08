package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.dto.ExpiringLotDTO;
import com.example.pharmacy.common.dto.RevenuePointDTO;
import com.example.pharmacy.common.dto.TopSellingProductDTO;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacymanagementsystem_qlht.model.HoaDonDisplay;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeBanHang;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeTonKho;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeTopSanPham;
import com.example.pharmacymanagementsystem_qlht.model.ThuocHetHan;

import java.util.List;

public interface ReportRepository {
    List<ThongKeBanHang> findThongKeBanHangByDateRange(DateRangeRequest request, ReportBucket bucket);

    List<HoaDonDisplay> findHoaDonByDateRange(DateRangeRequest request);

    List<ThongKeTopSanPham> findTopBanChayByDateRange(DateRangeRequest request, int limit);

    List<ThongKeTopSanPham> findTopDoanhThuByDateRange(DateRangeRequest request, int limit);

    List<ThongKeTonKho> findThongKeXnt(DateRangeRequest request);

    List<ThuocHetHan> findThuocHetHan();

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

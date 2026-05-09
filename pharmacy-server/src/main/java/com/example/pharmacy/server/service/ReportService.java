package com.example.pharmacy.server.service;

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

public interface ReportService {
    List<ThongKeBanHangDto> getThongKeBanHang(String preset);

    List<ThongKeBanHangDto> getThongKeBanHangByDateRange(DateRangeRequest request);

    List<HoaDonDisplayDto> getHoaDonTheoThoiGian(String preset);

    List<HoaDonDisplayDto> getHoaDonTheoDateRange(DateRangeRequest request);

    List<ThongKeTopSanPhamDto> getTopBanChay(DateRangeRequest request, int limit);

    List<ThongKeTopSanPhamDto> getTopDoanhThu(DateRangeRequest request, int limit);

    List<ThongKeTonKhoDto> getThongKeXnt(DateRangeRequest request);

    List<ThuocHetHanDto> getThuocHetHan();

    List<RevenuePointDTO> getRevenueByDateRange(DateRangeRequest request);

    List<TopSellingProductDTO> getTopSellingProducts(DateRangeRequest request, int limit);

    List<ExpiringLotDTO> getExpiredLots();

    List<ExpiringLotDTO> getExpiringLots(int thresholdDays);
}

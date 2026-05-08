package com.example.pharmacy.server.service;

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

public interface ReportService {
    List<ThongKeBanHang> getThongKeBanHang(String preset);

    List<ThongKeBanHang> getThongKeBanHangByDateRange(DateRangeRequest request);

    List<HoaDonDisplay> getHoaDonTheoThoiGian(String preset);

    List<HoaDonDisplay> getHoaDonTheoDateRange(DateRangeRequest request);

    List<ThongKeTopSanPham> getTopBanChay(DateRangeRequest request, int limit);

    List<ThongKeTopSanPham> getTopDoanhThu(DateRangeRequest request, int limit);

    List<ThongKeTonKho> getThongKeXnt(DateRangeRequest request);

    List<ThuocHetHan> getThuocHetHan();

    List<RevenuePointDTO> getRevenueByDateRange(DateRangeRequest request);

    List<TopSellingProductDTO> getTopSellingProducts(DateRangeRequest request, int limit);

    List<ExpiringLotDTO> getExpiredLots();

    List<ExpiringLotDTO> getExpiringLots(int thresholdDays);
}

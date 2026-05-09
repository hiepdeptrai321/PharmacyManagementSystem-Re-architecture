package com.example.pharmacy.client.service;

import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.common.model.HoaDonDisplay;
import com.example.pharmacy.common.model.ThongKeBanHang;
import com.example.pharmacy.common.model.ThongKeTonKho;
import com.example.pharmacy.common.model.ThongKeTopSanPham;
import com.example.pharmacy.common.model.ThuocHetHan;

import java.util.List;

public interface ReportClientService {
    List<ThongKeBanHang> getThongKeBanHang(String preset);

    List<ThongKeBanHang> getThongKeBanHangByDateRange(DateRangeRequest request);

    List<HoaDonDisplay> getHoaDonTheoThoiGian(String preset);

    List<HoaDonDisplay> getHoaDonTheoDateRange(DateRangeRequest request);

    List<ThongKeTopSanPham> getTopBanChay(DateRangeRequest request, int limit);

    List<ThongKeTopSanPham> getTopDoanhThu(DateRangeRequest request, int limit);

    List<ThongKeTonKho> getThongKeXnt(DateRangeRequest request);

    List<ThuocHetHan> getThuocHetHan();
}

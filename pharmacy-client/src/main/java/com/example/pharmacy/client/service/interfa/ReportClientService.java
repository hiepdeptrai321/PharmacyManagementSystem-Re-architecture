package com.example.pharmacy.client.service.interfa;

import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.common.model.HoaDonDisplayDto;
import com.example.pharmacy.common.model.ThongKeBanHangDto;
import com.example.pharmacy.common.model.ThongKeTonKhoDto;
import com.example.pharmacy.common.model.ThongKeTopSanPhamDto;
import com.example.pharmacy.common.model.ThuocHetHanDto;

import java.util.List;

public interface ReportClientService {
    List<ThongKeBanHangDto> getThongKeBanHang(String preset);

    List<ThongKeBanHangDto> getThongKeBanHangByDateRange(DateRangeRequest request);

    List<HoaDonDisplayDto> getHoaDonTheoThoiGian(String preset);

    List<HoaDonDisplayDto> getHoaDonTheoDateRange(DateRangeRequest request);

    List<ThongKeTopSanPhamDto> getTopBanChay(DateRangeRequest request, int limit);

    List<ThongKeTopSanPhamDto> getTopDoanhThu(DateRangeRequest request, int limit);

    List<ThongKeTonKhoDto> getThongKeXnt(DateRangeRequest request);

    List<ThuocHetHanDto> getThuocHetHan();
}

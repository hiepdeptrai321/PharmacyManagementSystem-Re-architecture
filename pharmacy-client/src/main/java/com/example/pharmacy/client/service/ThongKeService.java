package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.ReportClientService;
import com.example.pharmacy.client.service.RmiReportClientService;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.common.model.HoaDonDisplayDto;
import com.example.pharmacy.common.model.ThongKeBanHangDto;
import com.example.pharmacy.common.model.ThongKeTonKhoDto;
import com.example.pharmacy.common.model.ThongKeTopSanPhamDto;
import com.example.pharmacy.common.model.ThuocHetHanDto;

import java.time.LocalDate;
import java.util.List;

public class ThongKeService {
    private final ReportClientService reportClientService =
            new RmiReportClientService(new RmiClientProvider());

    public List<ThongKeBanHangDto> getThongKeBanHang(String preset) {
        return reportClientService.getThongKeBanHang(preset);
    }

    public List<ThongKeBanHangDto> getThongKeBanHang(LocalDate fromDate, LocalDate toDate) {
        return reportClientService.getThongKeBanHangByDateRange(new DateRangeRequest(fromDate, toDate));
    }

    public List<HoaDonDisplayDto> getHoaDonTheoThoiGian(String preset) {
        return reportClientService.getHoaDonTheoThoiGian(preset);
    }

    public List<HoaDonDisplayDto> getHoaDonTheoTuyChon(LocalDate fromDate, LocalDate toDate) {
        return reportClientService.getHoaDonTheoDateRange(new DateRangeRequest(fromDate, toDate));
    }

    public List<ThongKeTopSanPhamDto> getTopBanChay(LocalDate fromDate, LocalDate toDate, int limit) {
        return reportClientService.getTopBanChay(new DateRangeRequest(fromDate, toDate), limit);
    }

    public List<ThongKeTopSanPhamDto> getTopDoanhThu(LocalDate fromDate, LocalDate toDate, int limit) {
        return reportClientService.getTopDoanhThu(new DateRangeRequest(fromDate, toDate), limit);
    }

    public List<ThongKeTonKhoDto> getThongKeXNT(LocalDate fromDate, LocalDate toDate) {
        return reportClientService.getThongKeXnt(new DateRangeRequest(fromDate, toDate));
    }

    public List<ThuocHetHanDto> getThuocHetHan() {
        return reportClientService.getThuocHetHan();
    }
}

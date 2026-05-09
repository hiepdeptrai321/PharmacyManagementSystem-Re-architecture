package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.ReportClientService;
import com.example.pharmacy.client.service.RmiReportClientService;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.common.model.HoaDonDisplay;
import com.example.pharmacy.common.model.ThongKeBanHang;
import com.example.pharmacy.common.model.ThongKeTonKho;
import com.example.pharmacy.common.model.ThongKeTopSanPham;
import com.example.pharmacy.common.model.ThuocHetHan;

import java.time.LocalDate;
import java.util.List;

public class ThongKeService {
    private final ReportClientService reportClientService =
            new RmiReportClientService(new RmiClientProvider());

    public List<ThongKeBanHang> getThongKeBanHang(String preset) {
        return reportClientService.getThongKeBanHang(preset);
    }

    public List<ThongKeBanHang> getThongKeBanHang(LocalDate fromDate, LocalDate toDate) {
        return reportClientService.getThongKeBanHangByDateRange(new DateRangeRequest(fromDate, toDate));
    }

    public List<HoaDonDisplay> getHoaDonTheoThoiGian(String preset) {
        return reportClientService.getHoaDonTheoThoiGian(preset);
    }

    public List<HoaDonDisplay> getHoaDonTheoTuyChon(LocalDate fromDate, LocalDate toDate) {
        return reportClientService.getHoaDonTheoDateRange(new DateRangeRequest(fromDate, toDate));
    }

    public List<ThongKeTopSanPham> getTopBanChay(LocalDate fromDate, LocalDate toDate, int limit) {
        return reportClientService.getTopBanChay(new DateRangeRequest(fromDate, toDate), limit);
    }

    public List<ThongKeTopSanPham> getTopDoanhThu(LocalDate fromDate, LocalDate toDate, int limit) {
        return reportClientService.getTopDoanhThu(new DateRangeRequest(fromDate, toDate), limit);
    }

    public List<ThongKeTonKho> getThongKeXNT(LocalDate fromDate, LocalDate toDate) {
        return reportClientService.getThongKeXnt(new DateRangeRequest(fromDate, toDate));
    }

    public List<ThuocHetHan> getThuocHetHan() {
        return reportClientService.getThuocHetHan();
    }
}

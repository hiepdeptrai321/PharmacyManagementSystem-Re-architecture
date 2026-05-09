package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.ReportRemote;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.server.service.ReportService;
import com.example.pharmacy.common.model.HoaDonDisplayDto;
import com.example.pharmacy.common.model.ThongKeBanHangDto;
import com.example.pharmacy.common.model.ThongKeTonKhoDto;
import com.example.pharmacy.common.model.ThongKeTopSanPhamDto;
import com.example.pharmacy.common.model.ThuocHetHanDto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class ReportRemoteAdapter extends UnicastRemoteObject implements ReportRemote {
    private final ReportService reportService;

    public ReportRemoteAdapter(ReportService reportService) throws RemoteException {
        super();
        this.reportService = Objects.requireNonNull(reportService, "reportService must not be null");
    }

    @Override
    public List<ThongKeBanHangDto> getThongKeBanHang(String preset) throws RemoteException {
        return reportService.getThongKeBanHang(preset);
    }

    @Override
    public List<ThongKeBanHangDto> getThongKeBanHangByDateRange(DateRangeRequest request) throws RemoteException {
        return reportService.getThongKeBanHangByDateRange(request);
    }

    @Override
    public List<HoaDonDisplayDto> getHoaDonTheoThoiGian(String preset) throws RemoteException {
        return reportService.getHoaDonTheoThoiGian(preset);
    }

    @Override
    public List<HoaDonDisplayDto> getHoaDonTheoDateRange(DateRangeRequest request) throws RemoteException {
        return reportService.getHoaDonTheoDateRange(request);
    }

    @Override
    public List<ThongKeTopSanPhamDto> getTopBanChay(DateRangeRequest request, int limit) throws RemoteException {
        return reportService.getTopBanChay(request, limit);
    }

    @Override
    public List<ThongKeTopSanPhamDto> getTopDoanhThu(DateRangeRequest request, int limit) throws RemoteException {
        return reportService.getTopDoanhThu(request, limit);
    }

    @Override
    public List<ThongKeTonKhoDto> getThongKeXnt(DateRangeRequest request) throws RemoteException {
        return reportService.getThongKeXnt(request);
    }

    @Override
    public List<ThuocHetHanDto> getThuocHetHan() throws RemoteException {
        return reportService.getThuocHetHan();
    }
}

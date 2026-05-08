package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.ReportRemote;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.server.service.ReportService;
import com.example.pharmacymanagementsystem_qlht.model.HoaDonDisplay;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeBanHang;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeTonKho;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeTopSanPham;
import com.example.pharmacymanagementsystem_qlht.model.ThuocHetHan;

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
    public List<ThongKeBanHang> getThongKeBanHang(String preset) throws RemoteException {
        return reportService.getThongKeBanHang(preset);
    }

    @Override
    public List<ThongKeBanHang> getThongKeBanHangByDateRange(DateRangeRequest request) throws RemoteException {
        return reportService.getThongKeBanHangByDateRange(request);
    }

    @Override
    public List<HoaDonDisplay> getHoaDonTheoThoiGian(String preset) throws RemoteException {
        return reportService.getHoaDonTheoThoiGian(preset);
    }

    @Override
    public List<HoaDonDisplay> getHoaDonTheoDateRange(DateRangeRequest request) throws RemoteException {
        return reportService.getHoaDonTheoDateRange(request);
    }

    @Override
    public List<ThongKeTopSanPham> getTopBanChay(DateRangeRequest request, int limit) throws RemoteException {
        return reportService.getTopBanChay(request, limit);
    }

    @Override
    public List<ThongKeTopSanPham> getTopDoanhThu(DateRangeRequest request, int limit) throws RemoteException {
        return reportService.getTopDoanhThu(request, limit);
    }

    @Override
    public List<ThongKeTonKho> getThongKeXnt(DateRangeRequest request) throws RemoteException {
        return reportService.getThongKeXnt(request);
    }

    @Override
    public List<ThuocHetHan> getThuocHetHan() throws RemoteException {
        return reportService.getThuocHetHan();
    }
}

package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.remote.ReportRemote;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacymanagementsystem_qlht.model.HoaDonDisplay;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeBanHang;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeTonKho;
import com.example.pharmacymanagementsystem_qlht.model.ThongKeTopSanPham;
import com.example.pharmacymanagementsystem_qlht.model.ThuocHetHan;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiReportClientService implements ReportClientService {
    private final RmiClientProvider clientProvider;

    public RmiReportClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<ThongKeBanHang> getThongKeBanHang(String preset) {
        try {
            return remote().getThongKeBanHang(preset);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai thong ke ban hang tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<ThongKeBanHang> getThongKeBanHangByDateRange(DateRangeRequest request) {
        try {
            return remote().getThongKeBanHangByDateRange(request);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai thong ke ban hang tuy chon tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<HoaDonDisplay> getHoaDonTheoThoiGian(String preset) {
        try {
            return remote().getHoaDonTheoThoiGian(preset);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach hoa don thong ke tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<HoaDonDisplay> getHoaDonTheoDateRange(DateRangeRequest request) {
        try {
            return remote().getHoaDonTheoDateRange(request);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach hoa don theo tuy chon tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<ThongKeTopSanPham> getTopBanChay(DateRangeRequest request, int limit) {
        try {
            return remote().getTopBanChay(request, limit);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai top ban chay tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<ThongKeTopSanPham> getTopDoanhThu(DateRangeRequest request, int limit) {
        try {
            return remote().getTopDoanhThu(request, limit);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai top doanh thu tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<ThongKeTonKho> getThongKeXnt(DateRangeRequest request) {
        try {
            return remote().getThongKeXnt(request);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai thong ke xuat nhap ton tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<ThuocHetHan> getThuocHetHan() {
        try {
            return remote().getThuocHetHan();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach thuoc het han tu server: " + exception.getMessage());
            return List.of();
        }
    }

    private ReportRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getReportRemote();
    }
}

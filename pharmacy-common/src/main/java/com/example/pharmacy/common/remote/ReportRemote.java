package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.common.model.HoaDonDisplayDto;
import com.example.pharmacy.common.model.ThongKeBanHangDto;
import com.example.pharmacy.common.model.ThongKeTonKhoDto;
import com.example.pharmacy.common.model.ThongKeTopSanPhamDto;
import com.example.pharmacy.common.model.ThuocHetHanDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ReportRemote extends Remote {
    String BINDING_NAME = "ReportRemoteService";

    List<ThongKeBanHangDto> getThongKeBanHang(String preset) throws RemoteException;

    List<ThongKeBanHangDto> getThongKeBanHangByDateRange(DateRangeRequest request) throws RemoteException;

    List<HoaDonDisplayDto> getHoaDonTheoThoiGian(String preset) throws RemoteException;

    List<HoaDonDisplayDto> getHoaDonTheoDateRange(DateRangeRequest request) throws RemoteException;

    List<ThongKeTopSanPhamDto> getTopBanChay(DateRangeRequest request, int limit) throws RemoteException;

    List<ThongKeTopSanPhamDto> getTopDoanhThu(DateRangeRequest request, int limit) throws RemoteException;

    List<ThongKeTonKhoDto> getThongKeXnt(DateRangeRequest request) throws RemoteException;

    List<ThuocHetHanDto> getThuocHetHan() throws RemoteException;
}

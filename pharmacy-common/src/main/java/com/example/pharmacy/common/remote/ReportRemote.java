package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.common.model.HoaDonDisplay;
import com.example.pharmacy.common.model.ThongKeBanHang;
import com.example.pharmacy.common.model.ThongKeTonKho;
import com.example.pharmacy.common.model.ThongKeTopSanPham;
import com.example.pharmacy.common.model.ThuocHetHan;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ReportRemote extends Remote {
    String BINDING_NAME = "ReportRemoteService";

    List<ThongKeBanHang> getThongKeBanHang(String preset) throws RemoteException;

    List<ThongKeBanHang> getThongKeBanHangByDateRange(DateRangeRequest request) throws RemoteException;

    List<HoaDonDisplay> getHoaDonTheoThoiGian(String preset) throws RemoteException;

    List<HoaDonDisplay> getHoaDonTheoDateRange(DateRangeRequest request) throws RemoteException;

    List<ThongKeTopSanPham> getTopBanChay(DateRangeRequest request, int limit) throws RemoteException;

    List<ThongKeTopSanPham> getTopDoanhThu(DateRangeRequest request, int limit) throws RemoteException;

    List<ThongKeTonKho> getThongKeXnt(DateRangeRequest request) throws RemoteException;

    List<ThuocHetHan> getThuocHetHan() throws RemoteException;
}

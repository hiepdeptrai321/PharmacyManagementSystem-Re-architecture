package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.CreatePhieuDoiRequest;
import com.example.pharmacy.common.request.CreatePhieuTraRequest;
import com.example.pharmacy.common.model.ChiTietHoaDon;
import com.example.pharmacy.common.model.ChiTietPhieuDoiHang;
import com.example.pharmacy.common.model.ChiTietPhieuTraHang;
import com.example.pharmacy.common.model.HoaDon;
import com.example.pharmacy.common.model.PhieuDoiHang;
import com.example.pharmacy.common.model.PhieuTraHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DoiTraRemote extends Remote {
    String BINDING_NAME = "DoiTraRemoteService";

    HoaDon findHoaDonGocForDoiTra(String maHoaDon) throws RemoteException;

    List<ChiTietHoaDon> findHoaDonDetailsForDoiTra(String maHoaDon) throws RemoteException;

    int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh) throws RemoteException;

    int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh) throws RemoteException;

    String createPhieuDoi(CreatePhieuDoiRequest request, UserContext actor) throws RemoteException;

    String createPhieuTra(CreatePhieuTraRequest request, UserContext actor) throws RemoteException;

    void attachKhachHangToHoaDon(String maHoaDon, String maKhachHang, UserContext actor) throws RemoteException;

    List<PhieuDoiHang> findAllPhieuDoi() throws RemoteException;

    PhieuDoiHang findPhieuDoiById(String maPhieuDoi) throws RemoteException;

    List<ChiTietPhieuDoiHang> findChiTietPhieuDoiByMaPD(String maPhieuDoi) throws RemoteException;

    List<PhieuTraHang> findAllPhieuTra() throws RemoteException;

    PhieuTraHang findPhieuTraById(String maPhieuTra) throws RemoteException;

    List<ChiTietPhieuTraHang> findChiTietPhieuTraByMaPT(String maPhieuTra) throws RemoteException;
}

package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.CreatePhieuDoiRequest;
import com.example.pharmacy.common.request.CreatePhieuTraRequest;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.ChiTietPhieuDoiHangDto;
import com.example.pharmacy.common.model.ChiTietPhieuTraHangDto;
import com.example.pharmacy.common.model.HoaDonDto;
import com.example.pharmacy.common.model.PhieuDoiHangDto;
import com.example.pharmacy.common.model.PhieuTraHangDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DoiTraRemote extends Remote {
    String BINDING_NAME = "DoiTraRemoteService";

    HoaDonDto findHoaDonGocForDoiTra(String maHoaDon) throws RemoteException;

    List<ChiTietHoaDonDto> findHoaDonDetailsForDoiTra(String maHoaDon) throws RemoteException;

    int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh) throws RemoteException;

    int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh) throws RemoteException;

    String createPhieuDoi(CreatePhieuDoiRequest request, UserContext actor) throws RemoteException;

    String createPhieuTra(CreatePhieuTraRequest request, UserContext actor) throws RemoteException;

    void attachKhachHangToHoaDon(String maHoaDon, String maKhachHang, UserContext actor) throws RemoteException;

    List<PhieuDoiHangDto> findAllPhieuDoi() throws RemoteException;

    PhieuDoiHangDto findPhieuDoiById(String maPhieuDoi) throws RemoteException;

    List<ChiTietPhieuDoiHangDto> findChiTietPhieuDoiByMaPD(String maPhieuDoi) throws RemoteException;

    List<PhieuTraHangDto> findAllPhieuTra() throws RemoteException;

    PhieuTraHangDto findPhieuTraById(String maPhieuTra) throws RemoteException;

    List<ChiTietPhieuTraHangDto> findChiTietPhieuTraByMaPT(String maPhieuTra) throws RemoteException;
}

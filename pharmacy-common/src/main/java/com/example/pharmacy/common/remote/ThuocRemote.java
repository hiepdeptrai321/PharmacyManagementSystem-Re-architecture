package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.ChiTietHoatChatDto;
import com.example.pharmacy.common.model.HoatChatDto;
import com.example.pharmacy.common.model.LoaiHangDto;
import com.example.pharmacy.common.model.ThuocTonKhoDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ThuocRemote extends Remote {
    String BINDING_NAME = "ThuocRemoteService";

    List<Thuoc_SanPhamDto> findAll() throws RemoteException;

    String generateNewMaThuoc() throws RemoteException;

    List<LoaiHangDto> findAllLoaiHang() throws RemoteException;

    List<String> findAllLoaiHangNames() throws RemoteException;

    List<HoatChatDto> findAllHoatChat() throws RemoteException;

    List<ChiTietHoatChatDto> findChiTietHoatChatByMaThuoc(String maThuoc) throws RemoteException;

    boolean create(Thuoc_SanPhamDto thuoc, List<ChiTietHoatChatDto> chiTietHoatChats, String maDonViTinhCoBan) throws RemoteException;

    boolean update(Thuoc_SanPhamDto thuoc, List<ChiTietHoatChatDto> chiTietHoatChats) throws RemoteException;

    boolean softDelete(String maThuoc) throws RemoteException;

    int getTongSoLuongTonByMaThuoc(String maThuoc) throws RemoteException;

    String getTenDonViTinhCoBan(String maThuoc) throws RemoteException;

    List<ThuocTonKhoDto> getThuocTonKho() throws RemoteException;

    List<Thuoc_SP_TheoLoDto> getAllTheoLo() throws RemoteException;
}

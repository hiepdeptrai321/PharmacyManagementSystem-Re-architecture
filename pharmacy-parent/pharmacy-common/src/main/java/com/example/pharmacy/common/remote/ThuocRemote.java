package com.example.pharmacy.common.remote;

import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;
import com.example.pharmacymanagementsystem_qlht.model.HoatChat;
import com.example.pharmacymanagementsystem_qlht.model.LoaiHang;
import com.example.pharmacymanagementsystem_qlht.model.ThuocTonKho;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ThuocRemote extends Remote {
    String BINDING_NAME = "ThuocRemoteService";

    List<Thuoc_SanPham> findAll() throws RemoteException;

    String generateNewMaThuoc() throws RemoteException;

    List<LoaiHang> findAllLoaiHang() throws RemoteException;

    List<String> findAllLoaiHangNames() throws RemoteException;

    List<HoatChat> findAllHoatChat() throws RemoteException;

    List<ChiTietHoatChat> findChiTietHoatChatByMaThuoc(String maThuoc) throws RemoteException;

    boolean create(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats, String maDonViTinhCoBan) throws RemoteException;

    boolean update(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats) throws RemoteException;

    boolean softDelete(String maThuoc) throws RemoteException;

    int getTongSoLuongTonByMaThuoc(String maThuoc) throws RemoteException;

    String getTenDonViTinhCoBan(String maThuoc) throws RemoteException;

    List<ThuocTonKho> getThuocTonKho() throws RemoteException;

    List<Thuoc_SP_TheoLo> getAllTheoLo() throws RemoteException;
}

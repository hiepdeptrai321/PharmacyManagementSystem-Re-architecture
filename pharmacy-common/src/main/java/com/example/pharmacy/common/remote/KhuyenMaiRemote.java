package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.ChiTietKhuyenMai;
import com.example.pharmacy.common.model.KhuyenMai;
import com.example.pharmacy.common.model.LoaiKhuyenMai;
import com.example.pharmacy.common.model.Thuoc_SP_TangKem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

public interface KhuyenMaiRemote extends Remote {
    String BINDING_NAME = "KhuyenMaiRemoteService";

    List<KhuyenMai> findAll() throws RemoteException;

    KhuyenMai findById(String maKhuyenMai) throws RemoteException;

    List<KhuyenMai> searchByKeyword(String keyword) throws RemoteException;

    String generateNewMaKM() throws RemoteException;

    List<LoaiKhuyenMai> findAllLoaiKhuyenMai() throws RemoteException;

    LoaiKhuyenMai findLoaiKhuyenMaiById(String maLoaiKhuyenMai) throws RemoteException;

    LoaiKhuyenMai findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai) throws RemoteException;

    List<ChiTietKhuyenMai> findChiTietByMaKM(String maKhuyenMai) throws RemoteException;

    List<Thuoc_SP_TangKem> findQuaTangByMaKM(String maKhuyenMai) throws RemoteException;

    boolean create(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais)
            throws RemoteException;

    boolean update(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais)
            throws RemoteException;

    boolean deleteByMaKM(String maKhuyenMai) throws RemoteException;

    List<KhuyenMai> findActiveOn(Date ngay) throws RemoteException;

    List<KhuyenMai> findActiveInvoiceOn(Date ngay) throws RemoteException;
}

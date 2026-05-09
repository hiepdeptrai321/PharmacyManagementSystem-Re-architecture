package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.ChiTietKhuyenMaiDto;
import com.example.pharmacy.common.model.KhuyenMaiDto;
import com.example.pharmacy.common.model.LoaiKhuyenMaiDto;
import com.example.pharmacy.common.model.Thuoc_SP_TangKemDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

public interface KhuyenMaiRemote extends Remote {
    String BINDING_NAME = "KhuyenMaiRemoteService";

    List<KhuyenMaiDto> findAll() throws RemoteException;

    KhuyenMaiDto findById(String maKhuyenMai) throws RemoteException;

    List<KhuyenMaiDto> searchByKeyword(String keyword) throws RemoteException;

    String generateNewMaKM() throws RemoteException;

    List<LoaiKhuyenMaiDto> findAllLoaiKhuyenMai() throws RemoteException;

    LoaiKhuyenMaiDto findLoaiKhuyenMaiById(String maLoaiKhuyenMai) throws RemoteException;

    LoaiKhuyenMaiDto findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai) throws RemoteException;

    List<ChiTietKhuyenMaiDto> findChiTietByMaKM(String maKhuyenMai) throws RemoteException;

    List<Thuoc_SP_TangKemDto> findQuaTangByMaKM(String maKhuyenMai) throws RemoteException;

    boolean create(KhuyenMaiDto khuyenMai, List<ChiTietKhuyenMaiDto> chiTietKhuyenMais, List<Thuoc_SP_TangKemDto> quaTangKhuyenMais)
            throws RemoteException;

    boolean update(KhuyenMaiDto khuyenMai, List<ChiTietKhuyenMaiDto> chiTietKhuyenMais, List<Thuoc_SP_TangKemDto> quaTangKhuyenMais)
            throws RemoteException;

    boolean deleteByMaKM(String maKhuyenMai) throws RemoteException;

    List<KhuyenMaiDto> findActiveOn(Date ngay) throws RemoteException;

    List<KhuyenMaiDto> findActiveInvoiceOn(Date ngay) throws RemoteException;
}

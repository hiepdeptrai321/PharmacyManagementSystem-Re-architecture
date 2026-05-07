package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.KhuyenMaiRemote;
import com.example.pharmacy.server.service.KhuyenMaiService;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.LoaiKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TangKem;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class KhuyenMaiRemoteAdapter extends UnicastRemoteObject implements KhuyenMaiRemote {
    private final KhuyenMaiService khuyenMaiService;

    public KhuyenMaiRemoteAdapter(KhuyenMaiService khuyenMaiService) throws RemoteException {
        super();
        this.khuyenMaiService = Objects.requireNonNull(khuyenMaiService, "khuyenMaiService must not be null");
    }

    @Override
    public List<KhuyenMai> findAll() throws RemoteException {
        return khuyenMaiService.findAll();
    }

    @Override
    public KhuyenMai findById(String maKhuyenMai) throws RemoteException {
        return khuyenMaiService.findById(maKhuyenMai);
    }

    @Override
    public List<KhuyenMai> searchByKeyword(String keyword) throws RemoteException {
        return khuyenMaiService.searchByKeyword(keyword);
    }

    @Override
    public String generateNewMaKM() throws RemoteException {
        return khuyenMaiService.generateNewMaKM();
    }

    @Override
    public List<LoaiKhuyenMai> findAllLoaiKhuyenMai() throws RemoteException {
        return khuyenMaiService.findAllLoaiKhuyenMai();
    }

    @Override
    public LoaiKhuyenMai findLoaiKhuyenMaiById(String maLoaiKhuyenMai) throws RemoteException {
        return khuyenMaiService.findLoaiKhuyenMaiById(maLoaiKhuyenMai);
    }

    @Override
    public LoaiKhuyenMai findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai) throws RemoteException {
        return khuyenMaiService.findLoaiKhuyenMaiByTen(tenLoaiKhuyenMai);
    }

    @Override
    public List<ChiTietKhuyenMai> findChiTietByMaKM(String maKhuyenMai) throws RemoteException {
        return khuyenMaiService.findChiTietByMaKM(maKhuyenMai);
    }

    @Override
    public List<Thuoc_SP_TangKem> findQuaTangByMaKM(String maKhuyenMai) throws RemoteException {
        return khuyenMaiService.findQuaTangByMaKM(maKhuyenMai);
    }

    @Override
    public boolean create(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais)
            throws RemoteException {
        return khuyenMaiService.create(khuyenMai, chiTietKhuyenMais, quaTangKhuyenMais);
    }

    @Override
    public boolean update(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais)
            throws RemoteException {
        return khuyenMaiService.update(khuyenMai, chiTietKhuyenMais, quaTangKhuyenMais);
    }

    @Override
    public boolean deleteByMaKM(String maKhuyenMai) throws RemoteException {
        return khuyenMaiService.deleteByMaKM(maKhuyenMai);
    }

    @Override
    public List<KhuyenMai> findActiveOn(Date ngay) throws RemoteException {
        return khuyenMaiService.findActiveOn(ngay);
    }

    @Override
    public List<KhuyenMai> findActiveInvoiceOn(Date ngay) throws RemoteException {
        return khuyenMaiService.findActiveInvoiceOn(ngay);
    }
}

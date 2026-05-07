package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.ThuocRemote;
import com.example.pharmacy.server.service.ThuocService;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;
import com.example.pharmacymanagementsystem_qlht.model.HoatChat;
import com.example.pharmacymanagementsystem_qlht.model.LoaiHang;
import com.example.pharmacymanagementsystem_qlht.model.ThuocTonKho;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class ThuocRemoteAdapter extends UnicastRemoteObject implements ThuocRemote {
    private final ThuocService thuocService;

    public ThuocRemoteAdapter(ThuocService thuocService) throws RemoteException {
        super();
        this.thuocService = Objects.requireNonNull(thuocService, "thuocService must not be null");
    }

    @Override
    public List<Thuoc_SanPham> findAll() throws RemoteException {
        return thuocService.findAll();
    }

    @Override
    public String generateNewMaThuoc() throws RemoteException {
        return thuocService.generateNewMaThuoc();
    }

    @Override
    public List<LoaiHang> findAllLoaiHang() throws RemoteException {
        return thuocService.findAllLoaiHang();
    }

    @Override
    public List<String> findAllLoaiHangNames() throws RemoteException {
        return thuocService.findAllLoaiHangNames();
    }

    @Override
    public List<HoatChat> findAllHoatChat() throws RemoteException {
        return thuocService.findAllHoatChat();
    }

    @Override
    public List<ChiTietHoatChat> findChiTietHoatChatByMaThuoc(String maThuoc) throws RemoteException {
        return thuocService.findChiTietHoatChatByMaThuoc(maThuoc);
    }

    @Override
    public boolean create(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats, String maDonViTinhCoBan) throws RemoteException {
        return thuocService.create(thuoc, chiTietHoatChats, maDonViTinhCoBan);
    }

    @Override
    public boolean update(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats) throws RemoteException {
        return thuocService.update(thuoc, chiTietHoatChats);
    }

    @Override
    public boolean softDelete(String maThuoc) throws RemoteException {
        return thuocService.softDelete(maThuoc);
    }

    @Override
    public int getTongSoLuongTonByMaThuoc(String maThuoc) throws RemoteException {
        return thuocService.getTongSoLuongTonByMaThuoc(maThuoc);
    }

    @Override
    public String getTenDonViTinhCoBan(String maThuoc) throws RemoteException {
        return thuocService.getTenDonViTinhCoBan(maThuoc);
    }

    @Override
    public List<ThuocTonKho> getThuocTonKho() throws RemoteException {
        return thuocService.getThuocTonKho();
    }

    @Override
    public List<Thuoc_SP_TheoLo> getAllTheoLo() throws RemoteException {
        return thuocService.getAllTheoLo();
    }
}

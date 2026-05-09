package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.ThuocRemote;
import com.example.pharmacy.server.service.ThuocService;
import com.example.pharmacy.common.model.ChiTietHoatChatDto;
import com.example.pharmacy.common.model.HoatChatDto;
import com.example.pharmacy.common.model.LoaiHangDto;
import com.example.pharmacy.common.model.ThuocTonKhoDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

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
    public List<Thuoc_SanPhamDto> findAll() throws RemoteException {
        return thuocService.findAll();
    }

    @Override
    public String generateNewMaThuoc() throws RemoteException {
        return thuocService.generateNewMaThuoc();
    }

    @Override
    public List<LoaiHangDto> findAllLoaiHang() throws RemoteException {
        return thuocService.findAllLoaiHang();
    }

    @Override
    public List<String> findAllLoaiHangNames() throws RemoteException {
        return thuocService.findAllLoaiHangNames();
    }

    @Override
    public List<HoatChatDto> findAllHoatChat() throws RemoteException {
        return thuocService.findAllHoatChat();
    }

    @Override
    public List<ChiTietHoatChatDto> findChiTietHoatChatByMaThuoc(String maThuoc) throws RemoteException {
        return thuocService.findChiTietHoatChatByMaThuoc(maThuoc);
    }

    @Override
    public boolean create(Thuoc_SanPhamDto thuoc, List<ChiTietHoatChatDto> chiTietHoatChats, String maDonViTinhCoBan) throws RemoteException {
        return thuocService.create(thuoc, chiTietHoatChats, maDonViTinhCoBan);
    }

    @Override
    public boolean update(Thuoc_SanPhamDto thuoc, List<ChiTietHoatChatDto> chiTietHoatChats) throws RemoteException {
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
    public List<ThuocTonKhoDto> getThuocTonKho() throws RemoteException {
        return thuocService.getThuocTonKho();
    }

    @Override
    public List<Thuoc_SP_TheoLoDto> getAllTheoLo() throws RemoteException {
        return thuocService.getAllTheoLo();
    }
}

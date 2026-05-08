package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.remote.DoiTraRemote;
import com.example.pharmacy.common.request.CreatePhieuDoiRequest;
import com.example.pharmacy.common.request.CreatePhieuTraRequest;
import com.example.pharmacy.server.service.DoiTraService;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDoiHang;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuTraHang;
import com.example.pharmacymanagementsystem_qlht.model.HoaDon;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDoiHang;
import com.example.pharmacymanagementsystem_qlht.model.PhieuTraHang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class DoiTraRemoteAdapter extends UnicastRemoteObject implements DoiTraRemote {
    private final DoiTraService doiTraService;

    public DoiTraRemoteAdapter(DoiTraService doiTraService) throws RemoteException {
        super();
        this.doiTraService = Objects.requireNonNull(doiTraService, "doiTraService must not be null");
    }

    @Override
    public HoaDon findHoaDonGocForDoiTra(String maHoaDon) throws RemoteException {
        return doiTraService.findHoaDonGocForDoiTra(maHoaDon);
    }

    @Override
    public List<ChiTietHoaDon> findHoaDonDetailsForDoiTra(String maHoaDon) throws RemoteException {
        return doiTraService.findHoaDonDetailsForDoiTra(maHoaDon);
    }

    @Override
    public int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh) throws RemoteException {
        return doiTraService.getSoLuongDaDoi(maHoaDon, maLoHang, maDonViTinh);
    }

    @Override
    public int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh) throws RemoteException {
        return doiTraService.getSoLuongDaTra(maHoaDon, maLoHang, maDonViTinh);
    }

    @Override
    public String createPhieuDoi(CreatePhieuDoiRequest request, UserContext actor) throws RemoteException {
        return doiTraService.createPhieuDoi(request, actor);
    }

    @Override
    public String createPhieuTra(CreatePhieuTraRequest request, UserContext actor) throws RemoteException {
        return doiTraService.createPhieuTra(request, actor);
    }

    @Override
    public void attachKhachHangToHoaDon(String maHoaDon, String maKhachHang, UserContext actor) throws RemoteException {
        doiTraService.attachKhachHangToHoaDon(maHoaDon, maKhachHang, actor);
    }

    @Override
    public List<PhieuDoiHang> findAllPhieuDoi() throws RemoteException {
        return doiTraService.findAllPhieuDoi();
    }

    @Override
    public PhieuDoiHang findPhieuDoiById(String maPhieuDoi) throws RemoteException {
        return doiTraService.findPhieuDoiById(maPhieuDoi);
    }

    @Override
    public List<ChiTietPhieuDoiHang> findChiTietPhieuDoiByMaPD(String maPhieuDoi) throws RemoteException {
        return doiTraService.findChiTietPhieuDoiByMaPD(maPhieuDoi);
    }

    @Override
    public List<PhieuTraHang> findAllPhieuTra() throws RemoteException {
        return doiTraService.findAllPhieuTra();
    }

    @Override
    public PhieuTraHang findPhieuTraById(String maPhieuTra) throws RemoteException {
        return doiTraService.findPhieuTraById(maPhieuTra);
    }

    @Override
    public List<ChiTietPhieuTraHang> findChiTietPhieuTraByMaPT(String maPhieuTra) throws RemoteException {
        return doiTraService.findChiTietPhieuTraByMaPT(maPhieuTra);
    }
}

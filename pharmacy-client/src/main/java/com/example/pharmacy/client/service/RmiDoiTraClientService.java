package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.remote.DoiTraRemote;
import com.example.pharmacy.common.request.CreatePhieuDoiRequest;
import com.example.pharmacy.common.request.CreatePhieuTraRequest;
import com.example.pharmacy.common.model.ChiTietHoaDon;
import com.example.pharmacy.common.model.ChiTietPhieuDoiHang;
import com.example.pharmacy.common.model.ChiTietPhieuTraHang;
import com.example.pharmacy.common.model.HoaDon;
import com.example.pharmacy.common.model.PhieuDoiHang;
import com.example.pharmacy.common.model.PhieuTraHang;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiDoiTraClientService implements DoiTraClientService {
    private final RmiClientProvider clientProvider;

    public RmiDoiTraClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public HoaDon findHoaDonGocForDoiTra(String maHoaDon) {
        try {
            return remote().findHoaDonGocForDoiTra(maHoaDon);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai hoa don goc doi/tra tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public List<ChiTietHoaDon> findHoaDonDetailsForDoiTra(String maHoaDon) {
        try {
            return remote().findHoaDonDetailsForDoiTra(maHoaDon);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet hoa don doi/tra tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh) {
        try {
            return remote().getSoLuongDaDoi(maHoaDon, maLoHang, maDonViTinh);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai tong so luong da doi tu server: " + exception.getMessage());
            return 0;
        }
    }

    @Override
    public int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh) {
        try {
            return remote().getSoLuongDaTra(maHoaDon, maLoHang, maDonViTinh);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai tong so luong da tra tu server: " + exception.getMessage());
            return 0;
        }
    }

    @Override
    public String createPhieuDoi(CreatePhieuDoiRequest request, UserContext actor) {
        try {
            return remote().createPhieuDoi(request, actor);
        } catch (RemoteException | NotBoundException exception) {
            throw new IllegalStateException("Khong the tao phieu doi tren server: " + exception.getMessage(), exception);
        }
    }

    @Override
    public String createPhieuTra(CreatePhieuTraRequest request, UserContext actor) {
        try {
            return remote().createPhieuTra(request, actor);
        } catch (RemoteException | NotBoundException exception) {
            throw new IllegalStateException("Khong the tao phieu tra tren server: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void attachKhachHangToHoaDon(String maHoaDon, String maKhachHang, UserContext actor) {
        try {
            remote().attachKhachHangToHoaDon(maHoaDon, maKhachHang, actor);
        } catch (RemoteException | NotBoundException exception) {
            throw new IllegalStateException("Khong the cap nhat khach hang cho hoa don tren server: " + exception.getMessage(), exception);
        }
    }

    @Override
    public List<PhieuDoiHang> findAllPhieuDoi() {
        try {
            return remote().findAllPhieuDoi();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach phieu doi tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public PhieuDoiHang findPhieuDoiById(String maPhieuDoi) {
        try {
            return remote().findPhieuDoiById(maPhieuDoi);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai phieu doi tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public List<ChiTietPhieuDoiHang> findChiTietPhieuDoiByMaPD(String maPhieuDoi) {
        try {
            return remote().findChiTietPhieuDoiByMaPD(maPhieuDoi);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet phieu doi tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<PhieuTraHang> findAllPhieuTra() {
        try {
            return remote().findAllPhieuTra();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach phieu tra tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public PhieuTraHang findPhieuTraById(String maPhieuTra) {
        try {
            return remote().findPhieuTraById(maPhieuTra);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai phieu tra tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public List<ChiTietPhieuTraHang> findChiTietPhieuTraByMaPT(String maPhieuTra) {
        try {
            return remote().findChiTietPhieuTraByMaPT(maPhieuTra);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet phieu tra tu server: " + exception.getMessage());
            return List.of();
        }
    }

    private DoiTraRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getDoiTraRemote();
    }
}

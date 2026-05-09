package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.remote.ThuocRemote;
import com.example.pharmacy.common.model.ChiTietHoatChatDto;
import com.example.pharmacy.common.model.HoatChatDto;
import com.example.pharmacy.common.model.LoaiHangDto;
import com.example.pharmacy.common.model.ThuocTonKhoDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiThuocClientService implements ThuocClientService {
    private final RmiClientProvider clientProvider;

    public RmiThuocClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<Thuoc_SanPhamDto> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach thuoc tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public String generateNewMaThuoc() {
        try {
            return remote().generateNewMaThuoc();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma thuoc moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public List<LoaiHangDto> findAllLoaiHang() {
        try {
            return remote().findAllLoaiHang();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach loai hang tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<String> findAllLoaiHangNames() {
        try {
            return remote().findAllLoaiHangNames();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai loai hang tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<HoatChatDto> findAllHoatChat() {
        try {
            return remote().findAllHoatChat();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai hoat chat tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<ChiTietHoatChatDto> findChiTietHoatChatByMaThuoc(String maThuoc) {
        try {
            return remote().findChiTietHoatChatByMaThuoc(maThuoc);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet hoat chat tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public boolean create(Thuoc_SanPhamDto thuoc, List<ChiTietHoatChatDto> chiTietHoatChats, String maDonViTinhCoBan) {
        try {
            return remote().create(thuoc, chiTietHoatChats, maDonViTinhCoBan);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tao thuoc tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Thuoc_SanPhamDto thuoc, List<ChiTietHoatChatDto> chiTietHoatChats) {
        try {
            return remote().update(thuoc, chiTietHoatChats);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the cap nhat thuoc tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean softDelete(String maThuoc) {
        try {
            return remote().softDelete(maThuoc);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the xoa mem thuoc tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public int getTongSoLuongTonByMaThuoc(String maThuoc) {
        try {
            return remote().getTongSoLuongTonByMaThuoc(maThuoc);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai so luong ton cua thuoc tu server: " + exception.getMessage());
            return 0;
        }
    }

    @Override
    public String getTenDonViTinhCoBan(String maThuoc) {
        try {
            return remote().getTenDonViTinhCoBan(maThuoc);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai don vi tinh co ban cua thuoc tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public List<ThuocTonKhoDto> getThuocTonKho() {
        try {
            return remote().getThuocTonKho();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai ton kho thuoc tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Thuoc_SP_TheoLoDto> getAllTheoLo() {
        try {
            return remote().getAllTheoLo();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach thuoc theo lo tu server: " + exception.getMessage());
            return List.of();
        }
    }

    private ThuocRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getThuocRemote();
    }
}

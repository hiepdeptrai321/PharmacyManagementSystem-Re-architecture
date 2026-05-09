package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.remote.KhuyenMaiRemote;
import com.example.pharmacy.common.model.ChiTietKhuyenMai;
import com.example.pharmacy.common.model.KhuyenMai;
import com.example.pharmacy.common.model.LoaiKhuyenMai;
import com.example.pharmacy.common.model.Thuoc_SP_TangKem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class RmiKhuyenMaiClientService implements KhuyenMaiClientService {
    private final RmiClientProvider clientProvider;

    public RmiKhuyenMaiClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<KhuyenMai> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach khuyen mai tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public KhuyenMai findById(String maKhuyenMai) {
        try {
            return remote().findById(maKhuyenMai);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet khuyen mai tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public List<KhuyenMai> searchByKeyword(String keyword) {
        try {
            return remote().searchByKeyword(keyword);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tim khuyen mai tren server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public String generateNewMaKM() {
        try {
            return remote().generateNewMaKM();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma khuyen mai moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public List<LoaiKhuyenMai> findAllLoaiKhuyenMai() {
        try {
            return remote().findAllLoaiKhuyenMai();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach loai khuyen mai tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public LoaiKhuyenMai findLoaiKhuyenMaiById(String maLoaiKhuyenMai) {
        try {
            return remote().findLoaiKhuyenMaiById(maLoaiKhuyenMai);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai loai khuyen mai theo ma tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public LoaiKhuyenMai findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai) {
        try {
            return remote().findLoaiKhuyenMaiByTen(tenLoaiKhuyenMai);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai loai khuyen mai theo ten tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public List<ChiTietKhuyenMai> findChiTietByMaKM(String maKhuyenMai) {
        try {
            return remote().findChiTietByMaKM(maKhuyenMai);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet khuyen mai tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Thuoc_SP_TangKem> findQuaTangByMaKM(String maKhuyenMai) {
        try {
            return remote().findQuaTangByMaKM(maKhuyenMai);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai qua tang khuyen mai tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public boolean create(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais) {
        try {
            return remote().create(khuyenMai, chiTietKhuyenMais, quaTangKhuyenMais);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tao khuyen mai tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais) {
        try {
            return remote().update(khuyenMai, chiTietKhuyenMais, quaTangKhuyenMais);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the cap nhat khuyen mai tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteByMaKM(String maKhuyenMai) {
        try {
            return remote().deleteByMaKM(maKhuyenMai);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the xoa khuyen mai tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public List<KhuyenMai> findActiveOn(Date ngay) {
        try {
            return remote().findActiveOn(ngay);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai khuyen mai dang ap dung tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<KhuyenMai> findActiveInvoiceOn(Date ngay) {
        try {
            return remote().findActiveInvoiceOn(ngay);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai khuyen mai hoa don dang ap dung tu server: " + exception.getMessage());
            return List.of();
        }
    }

    private KhuyenMaiRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getKhuyenMaiRemote();
    }
}

package com.example.pharmacy.client.rmi;

import com.example.pharmacy.common.remote.AuthRemote;
import com.example.pharmacy.common.remote.CaiDatRemote;
import com.example.pharmacy.common.remote.DonViTinhRemote;
import com.example.pharmacy.common.remote.KeHangRemote;
import com.example.pharmacy.common.remote.KhachHangRemote;
import com.example.pharmacy.common.remote.KhuyenMaiRemote;
import com.example.pharmacy.common.remote.NhaCungCapRemote;
import com.example.pharmacy.common.remote.NhanVienRemote;
import com.example.pharmacy.common.remote.NhomDuocLyRemote;
import com.example.pharmacy.common.remote.PhieuDatHangRemote;
import com.example.pharmacy.common.remote.PhieuNhapRemote;
import com.example.pharmacy.common.remote.TonKhoRemote;
import com.example.pharmacy.common.remote.ThuocRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClientProvider {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 1099;

    private final String host;
    private final int port;

    public RmiClientProvider() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public RmiClientProvider(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public AuthRemote getAuthRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (AuthRemote) registry.lookup(AuthRemote.BINDING_NAME);
    }

    public KhachHangRemote getKhachHangRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (KhachHangRemote) registry.lookup(KhachHangRemote.BINDING_NAME);
    }

    public KhuyenMaiRemote getKhuyenMaiRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (KhuyenMaiRemote) registry.lookup(KhuyenMaiRemote.BINDING_NAME);
    }

    public NhaCungCapRemote getNhaCungCapRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (NhaCungCapRemote) registry.lookup(NhaCungCapRemote.BINDING_NAME);
    }

    public DonViTinhRemote getDonViTinhRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (DonViTinhRemote) registry.lookup(DonViTinhRemote.BINDING_NAME);
    }

    public KeHangRemote getKeHangRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (KeHangRemote) registry.lookup(KeHangRemote.BINDING_NAME);
    }

    public NhomDuocLyRemote getNhomDuocLyRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (NhomDuocLyRemote) registry.lookup(NhomDuocLyRemote.BINDING_NAME);
    }

    public ThuocRemote getThuocRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (ThuocRemote) registry.lookup(ThuocRemote.BINDING_NAME);
    }

    public NhanVienRemote getNhanVienRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (NhanVienRemote) registry.lookup(NhanVienRemote.BINDING_NAME);
    }

    public CaiDatRemote getCaiDatRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (CaiDatRemote) registry.lookup(CaiDatRemote.BINDING_NAME);
    }

    public PhieuNhapRemote getPhieuNhapRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (PhieuNhapRemote) registry.lookup(PhieuNhapRemote.BINDING_NAME);
    }

    public PhieuDatHangRemote getPhieuDatHangRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (PhieuDatHangRemote) registry.lookup(PhieuDatHangRemote.BINDING_NAME);
    }

    public TonKhoRemote getTonKhoRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (TonKhoRemote) registry.lookup(TonKhoRemote.BINDING_NAME);
    }
}

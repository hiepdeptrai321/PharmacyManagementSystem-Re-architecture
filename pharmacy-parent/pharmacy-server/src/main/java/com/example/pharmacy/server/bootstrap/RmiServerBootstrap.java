package com.example.pharmacy.server.bootstrap;

import com.example.pharmacy.common.remote.AuthRemote;
import com.example.pharmacy.common.remote.AuditLogRemote;
import com.example.pharmacy.common.remote.CaiDatRemote;
import com.example.pharmacy.common.remote.DonViTinhRemote;
import com.example.pharmacy.common.remote.DoiTraRemote;
import com.example.pharmacy.common.remote.HoaDonRemote;
import com.example.pharmacy.common.remote.KeHangRemote;
import com.example.pharmacy.common.remote.KhachHangRemote;
import com.example.pharmacy.common.remote.KhuyenMaiRemote;
import com.example.pharmacy.common.remote.NhaCungCapRemote;
import com.example.pharmacy.common.remote.NhanVienRemote;
import com.example.pharmacy.common.remote.NhomDuocLyRemote;
import com.example.pharmacy.common.remote.PhieuDatHangRemote;
import com.example.pharmacy.common.remote.PhieuNhapRemote;
import com.example.pharmacy.common.remote.ReportRemote;
import com.example.pharmacy.common.remote.TonKhoRemote;
import com.example.pharmacy.common.remote.ThuocRemote;
import com.example.pharmacy.server.bootstrap.rmi.AuditLogRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.DonViTinhRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.DoiTraRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.HoaDonRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.KeHangRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.KhachHangRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.KhuyenMaiRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.NhaCungCapRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.NhanVienRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.NhomDuocLyRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.AuthRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.CaiDatRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.PhieuDatHangRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.PhieuNhapRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.ReportRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.TonKhoRemoteAdapter;
import com.example.pharmacy.server.bootstrap.rmi.ThuocRemoteAdapter;
import com.example.pharmacy.server.service.AuditLogQueryService;
import com.example.pharmacy.server.service.AuthService;
import com.example.pharmacy.server.service.CaiDatService;
import com.example.pharmacy.server.service.DonViTinhService;
import com.example.pharmacy.server.service.DoiTraService;
import com.example.pharmacy.server.service.HoaDonService;
import com.example.pharmacy.server.service.KeHangService;
import com.example.pharmacy.server.service.KhachHangService;
import com.example.pharmacy.server.service.KhuyenMaiService;
import com.example.pharmacy.server.service.NhaCungCapService;
import com.example.pharmacy.server.service.NhanVienService;
import com.example.pharmacy.server.service.NhomDuocLyService;
import com.example.pharmacy.server.service.PhieuDatHangService;
import com.example.pharmacy.server.service.PhieuNhapService;
import com.example.pharmacy.server.service.ReportService;
import com.example.pharmacy.server.service.TonKhoService;
import com.example.pharmacy.server.service.ThuocService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public final class RmiServerBootstrap {
    public static final int DEFAULT_PORT = 1099;

    private RmiServerBootstrap() {
    }

    public static void main(String[] args) throws Exception {
        Registry registry = createOrGetRegistry(DEFAULT_PORT);
        ServerComponentFactory componentFactory = new ServerComponentFactory();
        AuthService authService = componentFactory.createJpaAuthService();
        AuthRemote authRemote = new AuthRemoteAdapter(authService);
        KhachHangService khachHangService = componentFactory.createKhachHangService();
        KhachHangRemote khachHangRemote = new KhachHangRemoteAdapter(khachHangService);
        KhuyenMaiService khuyenMaiService = componentFactory.createKhuyenMaiService();
        KhuyenMaiRemote khuyenMaiRemote = new KhuyenMaiRemoteAdapter(khuyenMaiService);
        NhaCungCapService nhaCungCapService = componentFactory.createNhaCungCapService();
        NhaCungCapRemote nhaCungCapRemote = new NhaCungCapRemoteAdapter(nhaCungCapService);
        DonViTinhService donViTinhService = componentFactory.createDonViTinhService();
        DonViTinhRemote donViTinhRemote = new DonViTinhRemoteAdapter(donViTinhService);
        KeHangService keHangService = componentFactory.createKeHangService();
        KeHangRemote keHangRemote = new KeHangRemoteAdapter(keHangService);
        NhomDuocLyService nhomDuocLyService = componentFactory.createNhomDuocLyService();
        NhomDuocLyRemote nhomDuocLyRemote = new NhomDuocLyRemoteAdapter(nhomDuocLyService);
        ThuocService thuocService = componentFactory.createThuocService();
        ThuocRemote thuocRemote = new ThuocRemoteAdapter(thuocService);
        NhanVienService nhanVienService = componentFactory.createNhanVienService();
        NhanVienRemote nhanVienRemote = new NhanVienRemoteAdapter(nhanVienService);
        CaiDatService caiDatService = componentFactory.createCaiDatService();
        CaiDatRemote caiDatRemote = new CaiDatRemoteAdapter(caiDatService);
        PhieuNhapService phieuNhapService = componentFactory.createPhieuNhapService();
        PhieuNhapRemote phieuNhapRemote = new PhieuNhapRemoteAdapter(phieuNhapService);
        PhieuDatHangService phieuDatHangService = componentFactory.createPhieuDatHangService();
        PhieuDatHangRemote phieuDatHangRemote = new PhieuDatHangRemoteAdapter(phieuDatHangService);
        TonKhoService tonKhoService = componentFactory.createTonKhoService();
        TonKhoRemote tonKhoRemote = new TonKhoRemoteAdapter(tonKhoService);
        HoaDonService hoaDonService = componentFactory.createHoaDonService();
        HoaDonRemote hoaDonRemote = new HoaDonRemoteAdapter(hoaDonService);
        DoiTraService doiTraService = componentFactory.createDoiTraService();
        DoiTraRemote doiTraRemote = new DoiTraRemoteAdapter(doiTraService);
        ReportService reportService = componentFactory.createReportService();
        ReportRemote reportRemote = new ReportRemoteAdapter(reportService);
        AuditLogQueryService auditLogQueryService = componentFactory.createAuditLogQueryService();
        AuditLogRemote auditLogRemote = new AuditLogRemoteAdapter(auditLogQueryService);

        registry.rebind(AuthRemote.BINDING_NAME, authRemote);
        registry.rebind(KhachHangRemote.BINDING_NAME, khachHangRemote);
        registry.rebind(KhuyenMaiRemote.BINDING_NAME, khuyenMaiRemote);
        registry.rebind(NhaCungCapRemote.BINDING_NAME, nhaCungCapRemote);
        registry.rebind(DonViTinhRemote.BINDING_NAME, donViTinhRemote);
        registry.rebind(KeHangRemote.BINDING_NAME, keHangRemote);
        registry.rebind(NhomDuocLyRemote.BINDING_NAME, nhomDuocLyRemote);
        registry.rebind(ThuocRemote.BINDING_NAME, thuocRemote);
        registry.rebind(NhanVienRemote.BINDING_NAME, nhanVienRemote);
        registry.rebind(CaiDatRemote.BINDING_NAME, caiDatRemote);
        registry.rebind(PhieuNhapRemote.BINDING_NAME, phieuNhapRemote);
        registry.rebind(PhieuDatHangRemote.BINDING_NAME, phieuDatHangRemote);
        registry.rebind(TonKhoRemote.BINDING_NAME, tonKhoRemote);
        registry.rebind(HoaDonRemote.BINDING_NAME, hoaDonRemote);
        registry.rebind(DoiTraRemote.BINDING_NAME, doiTraRemote);
        registry.rebind(ReportRemote.BINDING_NAME, reportRemote);
        registry.rebind(AuditLogRemote.BINDING_NAME, auditLogRemote);
        System.out.println("RMI server started on port " + DEFAULT_PORT + " with binding " + AuthRemote.BINDING_NAME);
        System.out.println("Persistence unit: pharmacyPU");
        System.out.println("Login POC path: JavaFX client -> RMI -> AuthServiceImpl -> JPA/Hibernate -> MariaDB");
        System.out.println("Master data bindings: "
                + KhachHangRemote.BINDING_NAME + ", "
                + KhuyenMaiRemote.BINDING_NAME + ", "
                + NhaCungCapRemote.BINDING_NAME + ", "
                + DonViTinhRemote.BINDING_NAME + ", "
                + KeHangRemote.BINDING_NAME + ", "
                + NhomDuocLyRemote.BINDING_NAME + ", "
                + ThuocRemote.BINDING_NAME + ", "
                + NhanVienRemote.BINDING_NAME + ", "
                + CaiDatRemote.BINDING_NAME + ", "
                + PhieuNhapRemote.BINDING_NAME + ", "
                + PhieuDatHangRemote.BINDING_NAME + ", "
                + TonKhoRemote.BINDING_NAME + ", "
                + HoaDonRemote.BINDING_NAME + ", "
                + DoiTraRemote.BINDING_NAME + ", "
                + ReportRemote.BINDING_NAME + ", "
                + AuditLogRemote.BINDING_NAME);
    }

    private static Registry createOrGetRegistry(int port) throws RemoteException {
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.list();
            return registry;
        } catch (RemoteException exception) {
            Registry registry = LocateRegistry.getRegistry(port);
            registry.list();
            return registry;
        }
    }
}

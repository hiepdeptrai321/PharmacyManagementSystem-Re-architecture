package com.example.pharmacy.server.bootstrap;

import com.example.pharmacy.server.config.DatabaseProperties;
import com.example.pharmacy.server.config.JpaUtil;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.config.MariaDbConnectionFactory;
import com.example.pharmacy.server.repository.AuditLogRepository;
import com.example.pharmacy.server.repository.CaiDatRepository;
import com.example.pharmacy.server.repository.CodeSequenceRepository;
import com.example.pharmacy.server.repository.DonViTinhRepository;
import com.example.pharmacy.server.repository.EmployeeWriteRepository;
import com.example.pharmacy.server.repository.JpaCaiDatRepository;
import com.example.pharmacy.server.repository.JpaDonViTinhRepository;
import com.example.pharmacy.server.repository.JpaKeHangRepository;
import com.example.pharmacy.server.repository.JpaKhachHangRepository;
import com.example.pharmacy.server.repository.JpaNhaCungCapRepository;
import com.example.pharmacy.server.repository.JpaNhomDuocLyRepository;
import com.example.pharmacy.server.repository.JpaLuongNhanVienRepository;
import com.example.pharmacy.server.repository.JpaNhanVienManagementRepository;
import com.example.pharmacy.server.repository.JpaNhanVienRepository;
import com.example.pharmacy.server.repository.KeHangRepository;
import com.example.pharmacy.server.repository.KhachHangRepository;
import com.example.pharmacy.server.repository.LuongNhanVienRepository;
import com.example.pharmacy.server.repository.MedicineCatalogRepository;
import com.example.pharmacy.server.repository.NhaCungCapRepository;
import com.example.pharmacy.server.repository.NhanVienManagementRepository;
import com.example.pharmacy.server.repository.NhomDuocLyRepository;
import com.example.pharmacy.server.repository.PhieuDatHangRepository;
import com.example.pharmacy.server.repository.PurchaseOrderRepository;
import com.example.pharmacy.server.repository.ReportRepository;
import com.example.pharmacy.server.repository.TonKhoRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcAuditLogRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcCodeSequenceRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcEmployeeWriteRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcMedicineCatalogRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcPhieuDatHangRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcPurchaseOrderRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcReportRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcTonKhoRepository;
import com.example.pharmacy.server.service.AuditService;
import com.example.pharmacy.server.service.AuditServiceImpl;
import com.example.pharmacy.server.service.AuthService;
import com.example.pharmacy.server.service.AuthServiceImpl;
import com.example.pharmacy.server.service.CaiDatService;
import com.example.pharmacy.server.service.CaiDatServiceImpl;
import com.example.pharmacy.server.service.CodeGenerationService;
import com.example.pharmacy.server.service.CodeGenerationServiceImpl;
import com.example.pharmacy.server.service.DonViTinhService;
import com.example.pharmacy.server.service.DonViTinhServiceImpl;
import com.example.pharmacy.server.service.EmployeeManagementService;
import com.example.pharmacy.server.service.EmployeeManagementServiceImpl;
import com.example.pharmacy.server.service.KeHangService;
import com.example.pharmacy.server.service.KeHangServiceImpl;
import com.example.pharmacy.server.service.KhachHangService;
import com.example.pharmacy.server.service.KhachHangServiceImpl;
import com.example.pharmacy.server.service.KhuyenMaiService;
import com.example.pharmacy.server.service.KhuyenMaiServiceImpl;
import com.example.pharmacy.server.service.NhaCungCapService;
import com.example.pharmacy.server.service.NhaCungCapServiceImpl;
import com.example.pharmacy.server.service.NhanVienService;
import com.example.pharmacy.server.service.NhanVienServiceImpl;
import com.example.pharmacy.server.service.NhomDuocLyService;
import com.example.pharmacy.server.service.NhomDuocLyServiceImpl;
import com.example.pharmacy.server.service.PhieuDatHangService;
import com.example.pharmacy.server.service.PhieuDatHangServiceImpl;
import com.example.pharmacy.server.service.PhieuNhapService;
import com.example.pharmacy.server.service.PhieuNhapServiceImpl;
import com.example.pharmacy.server.service.ReportService;
import com.example.pharmacy.server.service.ReportServiceImpl;
import com.example.pharmacy.server.service.TonKhoService;
import com.example.pharmacy.server.service.TonKhoServiceImpl;
import com.example.pharmacy.server.service.ThuocCatalogService;
import com.example.pharmacy.server.service.ThuocCatalogServiceImpl;
import com.example.pharmacy.server.service.ThuocService;
import com.example.pharmacy.server.service.ThuocServiceImpl;
import com.example.pharmacy.server.transaction.JdbcTransactionManager;
import com.example.pharmacy.server.transaction.TransactionManager;

public class ServerComponentFactory {
    private final JdbcConnectionProvider connectionProvider;
    private final TransactionManager transactionManager;

    public ServerComponentFactory() {
        this(DatabaseProperties.fromEnvironment());
    }

    public ServerComponentFactory(DatabaseProperties databaseProperties) {
        MariaDbConnectionFactory connectionFactory = new MariaDbConnectionFactory(databaseProperties);
        this.connectionProvider = new JdbcConnectionProvider(connectionFactory);
        this.transactionManager = new JdbcTransactionManager(connectionFactory);
    }

    public AuthService createJpaAuthService() {
        return new AuthServiceImpl(new JpaNhanVienRepository(JpaUtil.getEntityManagerFactory()), createAuditService());
    }

    public KhachHangService createKhachHangService() {
        return new KhachHangServiceImpl(createKhachHangRepository(), createCodeGenerationService());
    }

    public NhaCungCapService createNhaCungCapService() {
        return new NhaCungCapServiceImpl(createNhaCungCapRepository(), createCodeGenerationService());
    }

    public KhuyenMaiService createKhuyenMaiService() {
        return new KhuyenMaiServiceImpl(createCodeGenerationService());
    }

    public DonViTinhService createDonViTinhService() {
        return new DonViTinhServiceImpl(createDonViTinhRepository(), createCodeGenerationService());
    }

    public KeHangService createKeHangService() {
        return new KeHangServiceImpl(createKeHangRepository(), createCodeGenerationService());
    }

    public NhomDuocLyService createNhomDuocLyService() {
        return new NhomDuocLyServiceImpl(createNhomDuocLyRepository(), createCodeGenerationService());
    }

    public NhanVienService createNhanVienService() {
        return new NhanVienServiceImpl(
                createNhanVienManagementRepository(),
                createLuongNhanVienRepository(),
                createCodeGenerationService()
        );
    }

    public CaiDatService createCaiDatService() {
        return new CaiDatServiceImpl(createCaiDatRepository());
    }

    public EmployeeManagementService createEmployeeManagementService() {
        return new EmployeeManagementServiceImpl(
                transactionManager,
                createEmployeeWriteRepository(),
                createCodeGenerationService(),
                createAuditService()
        );
    }

    public ThuocCatalogService createThuocCatalogService() {
        return new ThuocCatalogServiceImpl(
                transactionManager,
                createMedicineCatalogRepository(),
                createCodeGenerationService(),
                createAuditService()
        );
    }

    public PhieuNhapService createPhieuNhapService() {
        return new PhieuNhapServiceImpl(
                transactionManager,
                createPurchaseOrderRepository(),
                createCodeGenerationService(),
                createAuditService()
        );
    }

    public PhieuDatHangService createPhieuDatHangService() {
        return new PhieuDatHangServiceImpl(
                transactionManager,
                createPhieuDatHangRepository(),
                createCodeGenerationService(),
                createAuditService()
        );
    }

    public TonKhoService createTonKhoService() {
        return new TonKhoServiceImpl(createTonKhoRepository(), createAuditService());
    }

    public ReportService createReportService() {
        return new ReportServiceImpl(createReportRepository());
    }

    public ThuocService createThuocService() {
        return new ThuocServiceImpl(createCodeGenerationService());
    }

    public AuditService createAuditService() {
        return new AuditServiceImpl(createAuditLogRepository(), createCodeGenerationService());
    }

    public CodeGenerationService createCodeGenerationService() {
        return new CodeGenerationServiceImpl(createCodeSequenceRepository(), transactionManager);
    }

    private EmployeeWriteRepository createEmployeeWriteRepository() {
        return new JdbcEmployeeWriteRepository(connectionProvider);
    }

    private MedicineCatalogRepository createMedicineCatalogRepository() {
        return new JdbcMedicineCatalogRepository(connectionProvider);
    }

    private PurchaseOrderRepository createPurchaseOrderRepository() {
        return new JdbcPurchaseOrderRepository(connectionProvider);
    }

    private PhieuDatHangRepository createPhieuDatHangRepository() {
        return new JdbcPhieuDatHangRepository(connectionProvider);
    }

    private TonKhoRepository createTonKhoRepository() {
        return new JdbcTonKhoRepository(connectionProvider);
    }

    private ReportRepository createReportRepository() {
        return new JdbcReportRepository(connectionProvider);
    }

    private CodeSequenceRepository createCodeSequenceRepository() {
        return new JdbcCodeSequenceRepository(connectionProvider);
    }

    private AuditLogRepository createAuditLogRepository() {
        return new JdbcAuditLogRepository(connectionProvider);
    }

    private KhachHangRepository createKhachHangRepository() {
        return new JpaKhachHangRepository(JpaUtil.getEntityManagerFactory());
    }

    private NhaCungCapRepository createNhaCungCapRepository() {
        return new JpaNhaCungCapRepository(JpaUtil.getEntityManagerFactory());
    }

    private DonViTinhRepository createDonViTinhRepository() {
        return new JpaDonViTinhRepository(JpaUtil.getEntityManagerFactory());
    }

    private KeHangRepository createKeHangRepository() {
        return new JpaKeHangRepository(JpaUtil.getEntityManagerFactory());
    }

    private NhomDuocLyRepository createNhomDuocLyRepository() {
        return new JpaNhomDuocLyRepository(JpaUtil.getEntityManagerFactory());
    }

    private NhanVienManagementRepository createNhanVienManagementRepository() {
        return new JpaNhanVienManagementRepository(JpaUtil.getEntityManagerFactory());
    }

    private LuongNhanVienRepository createLuongNhanVienRepository() {
        return new JpaLuongNhanVienRepository(JpaUtil.getEntityManagerFactory());
    }

    private CaiDatRepository createCaiDatRepository() {
        return new JpaCaiDatRepository(JpaUtil.getEntityManagerFactory());
    }
}

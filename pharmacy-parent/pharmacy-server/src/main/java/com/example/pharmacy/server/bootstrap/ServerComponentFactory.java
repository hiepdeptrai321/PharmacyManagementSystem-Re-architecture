package com.example.pharmacy.server.bootstrap;

import com.example.pharmacy.server.config.DatabaseProperties;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.config.MariaDbConnectionFactory;
import com.example.pharmacy.server.repository.AuditLogRepository;
import com.example.pharmacy.server.repository.CodeSequenceRepository;
import com.example.pharmacy.server.repository.EmployeeWriteRepository;
import com.example.pharmacy.server.repository.JdbcNhanVienRepository;
import com.example.pharmacy.server.repository.MedicineCatalogRepository;
import com.example.pharmacy.server.repository.NhanVienRepository;
import com.example.pharmacy.server.repository.PurchaseOrderRepository;
import com.example.pharmacy.server.repository.ReportRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcAuditLogRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcCodeSequenceRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcEmployeeWriteRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcMedicineCatalogRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcPurchaseOrderRepository;
import com.example.pharmacy.server.repository.jdbc.JdbcReportRepository;
import com.example.pharmacy.server.service.AuditService;
import com.example.pharmacy.server.service.AuditServiceImpl;
import com.example.pharmacy.server.service.AuthService;
import com.example.pharmacy.server.service.AuthServiceImpl;
import com.example.pharmacy.server.service.CodeGenerationService;
import com.example.pharmacy.server.service.CodeGenerationServiceImpl;
import com.example.pharmacy.server.service.EmployeeManagementService;
import com.example.pharmacy.server.service.EmployeeManagementServiceImpl;
import com.example.pharmacy.server.service.PhieuNhapService;
import com.example.pharmacy.server.service.PhieuNhapServiceImpl;
import com.example.pharmacy.server.service.ReportService;
import com.example.pharmacy.server.service.ReportServiceImpl;
import com.example.pharmacy.server.service.ThuocCatalogService;
import com.example.pharmacy.server.service.ThuocCatalogServiceImpl;
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

    public AuthService createMariaDbAuthService() {
        return new AuthServiceImpl(createNhanVienRepository(), createAuditService());
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

    public ReportService createReportService() {
        return new ReportServiceImpl(createReportRepository());
    }

    public AuditService createAuditService() {
        return new AuditServiceImpl(createAuditLogRepository(), createCodeGenerationService());
    }

    public CodeGenerationService createCodeGenerationService() {
        return new CodeGenerationServiceImpl(createCodeSequenceRepository(), transactionManager);
    }

    private NhanVienRepository createNhanVienRepository() {
        return new JdbcNhanVienRepository(connectionProvider);
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

    private ReportRepository createReportRepository() {
        return new JdbcReportRepository(connectionProvider);
    }

    private CodeSequenceRepository createCodeSequenceRepository() {
        return new JdbcCodeSequenceRepository(connectionProvider);
    }

    private AuditLogRepository createAuditLogRepository() {
        return new JdbcAuditLogRepository(connectionProvider);
    }
}

package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.AuditLogRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class JdbcAuditLogRepository extends AbstractJdbcRepository implements AuditLogRepository {
    private static final String INSERT_AUDIT_LOG_SQL = """
            INSERT INTO audit_log (
                audit_code,
                user_id,
                username,
                employee_id,
                full_name,
                action,
                entity_name,
                entity_id,
                description,
                created_at
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public JdbcAuditLogRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public void insert(AuditLogDTO auditLog) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_AUDIT_LOG_SQL)) {
                statement.setString(1, auditLog.getAuditCode());
                statement.setString(2, auditLog.getUserId());
                statement.setString(3, auditLog.getUsername());
                statement.setString(4, auditLog.getEmployeeId());
                statement.setString(5, auditLog.getFullName());
                statement.setString(6, auditLog.getAction().name());
                statement.setString(7, auditLog.getEntityName());
                statement.setString(8, auditLog.getEntityId());
                statement.setString(9, auditLog.getDescription());
                statement.setTimestamp(10, Timestamp.valueOf(auditLog.getCreatedAt()));
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert audit log.", exception);
        } finally {
            closeConnection(connection);
        }
    }
}

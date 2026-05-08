package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.enums.AuditAction;
import com.example.pharmacy.common.request.AuditLogSearchRequest;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.AuditLogRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    private static final String BASE_SELECT_SQL = """
            SELECT
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
            FROM audit_log
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

    @Override
    public List<AuditLogDTO> findAuditLogs(AuditLogSearchRequest request) {
        Connection connection = null;
        try {
            connection = getConnection();
            List<Object> args = new ArrayList<>();
            String sql = buildSearchSql(request, args);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                bindArgs(statement, args);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<AuditLogDTO> results = new ArrayList<>();
                    while (resultSet.next()) {
                        results.add(mapAuditLog(resultSet));
                    }
                    return results;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query audit logs.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public AuditLogDTO findAuditLogByCode(String auditCode) {
        if (auditCode == null || auditCode.isBlank()) {
            return null;
        }
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(BASE_SELECT_SQL + " WHERE audit_code = ?")) {
                statement.setString(1, auditCode.trim());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapAuditLog(resultSet);
                    }
                    return null;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query audit log by code.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private String buildSearchSql(AuditLogSearchRequest request, List<Object> args) {
        StringBuilder sql = new StringBuilder(BASE_SELECT_SQL).append(" WHERE 1 = 1");

        if (request != null && request.getKeyword() != null && !request.getKeyword().isBlank()) {
            sql.append("""
                     AND (
                         LOWER(audit_code) LIKE ?
                         OR LOWER(username) LIKE ?
                         OR LOWER(employee_id) LIKE ?
                         OR LOWER(full_name) LIKE ?
                         OR LOWER(entity_name) LIKE ?
                         OR LOWER(entity_id) LIKE ?
                         OR LOWER(description) LIKE ?
                     )
                    """);
            String keyword = "%" + request.getKeyword().trim().toLowerCase() + "%";
            for (int i = 0; i < 7; i++) {
                args.add(keyword);
            }
        }

        if (request != null && request.getFromDate() != null) {
            sql.append(" AND DATE(created_at) >= ?");
            args.add(java.sql.Date.valueOf(request.getFromDate()));
        }

        if (request != null && request.getToDate() != null) {
            sql.append(" AND DATE(created_at) <= ?");
            args.add(java.sql.Date.valueOf(request.getToDate()));
        }

        sql.append(" ORDER BY created_at DESC, audit_code DESC");
        return sql.toString();
    }

    private void bindArgs(PreparedStatement statement, List<Object> args) throws Exception {
        for (int index = 0; index < args.size(); index++) {
            statement.setObject(index + 1, args.get(index));
        }
    }

    private AuditLogDTO mapAuditLog(ResultSet resultSet) throws Exception {
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        return new AuditLogDTO(
                resultSet.getString("audit_code"),
                resultSet.getString("user_id"),
                resultSet.getString("username"),
                resultSet.getString("employee_id"),
                resultSet.getString("full_name"),
                AuditAction.valueOf(resultSet.getString("action")),
                resultSet.getString("entity_name"),
                resultSet.getString("entity_id"),
                resultSet.getString("description"),
                createdAt == null ? null : createdAt.toLocalDateTime()
        );
    }
}

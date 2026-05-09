package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.CodeSequenceDefinition;
import com.example.pharmacy.server.repository.CodeSequenceRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class JdbcCodeSequenceRepository extends AbstractJdbcRepository implements CodeSequenceRepository {
    private static final String LOCK_BY_TYPE_SQL = """
            SELECT code_type, prefix, current_value, padding
            FROM code_sequence
            WHERE code_type = ?
            FOR UPDATE
            """;
    private static final String UPDATE_CURRENT_VALUE_SQL = """
            UPDATE code_sequence
            SET current_value = ?, updated_at = CURRENT_TIMESTAMP
            WHERE code_type = ?
            """;

    public JdbcCodeSequenceRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public Optional<CodeSequenceDefinition> lockByCodeType(BusinessCodeType codeType) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(LOCK_BY_TYPE_SQL)) {
                statement.setString(1, codeType.getCodeType());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return Optional.empty();
                    }
                    return Optional.of(new CodeSequenceDefinition(
                            codeType,
                            resultSet.getString("prefix"),
                            resultSet.getLong("current_value"),
                            resultSet.getInt("padding")
                    ));
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not lock code sequence for " + codeType.getCodeType(), exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void updateCurrentValue(BusinessCodeType codeType, long currentValue) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_CURRENT_VALUE_SQL)) {
                statement.setLong(1, currentValue);
                statement.setString(2, codeType.getCodeType());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not update code sequence for " + codeType.getCodeType(), exception);
        } finally {
            closeConnection(connection);
        }
    }
}

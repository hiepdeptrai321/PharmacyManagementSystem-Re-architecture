package com.example.pharmacy.server.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

public final class JpaUtil {
    public static final String PERSISTENCE_UNIT_NAME = "pharmacyPU";

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = buildEntityManagerFactory();

    private JpaUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

    public static EntityManager createEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void close() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }

    private static EntityManagerFactory buildEntityManagerFactory() {
        Map<String, Object> overrides = new HashMap<>();
        applyOverride(overrides, "jakarta.persistence.jdbc.url", "pharmacy.db.url", "PHARMACY_DB_URL");
        applyOverride(overrides, "jakarta.persistence.jdbc.user", "pharmacy.db.user", "PHARMACY_DB_USER");
        applyOverride(overrides, "jakarta.persistence.jdbc.password", "pharmacy.db.password", "PHARMACY_DB_PASSWORD");
        applyOverride(overrides, "hibernate.hbm2ddl.auto", "pharmacy.jpa.ddl-auto", "PHARMACY_JPA_DDL_AUTO");
        applyOverride(overrides, "hibernate.show_sql", "pharmacy.jpa.show-sql", "PHARMACY_JPA_SHOW_SQL");
        overrides.put("hibernate.format_sql", "true");

        DatabaseProperties effectiveDatabaseProperties = DatabaseProperties.fromEnvironment();
        logResolvedConfiguration(effectiveDatabaseProperties);

        try {
            return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, overrides);
        } catch (RuntimeException exception) {
            throw new IllegalStateException(
                    "Cannot initialize JPA EntityManagerFactory. Check MariaDB service, database name, user/password, and authentication plugin.",
                    exception
            );
        }
    }

    private static void applyOverride(
            Map<String, Object> overrides,
            String persistencePropertyName,
            String systemPropertyName,
            String environmentVariableName
    ) {
        String override = DatabaseProperties.resolveOverride(systemPropertyName, environmentVariableName);
        if (override != null) {
            overrides.put(persistencePropertyName, override);
        }
    }

    private static void logResolvedConfiguration(DatabaseProperties databaseProperties) {
        Map<String, Object> safeConfiguration = new LinkedHashMap<>();
        safeConfiguration.put("persistenceUnit", PERSISTENCE_UNIT_NAME);
        safeConfiguration.put("jdbcUrl", databaseProperties.getUrl());
        safeConfiguration.put("jdbcUser", databaseProperties.getUsername());
        safeConfiguration.put(
                "ddlAuto",
                DatabaseProperties.resolveOverride("pharmacy.jpa.ddl-auto", "PHARMACY_JPA_DDL_AUTO") != null
                        ? DatabaseProperties.resolveOverride("pharmacy.jpa.ddl-auto", "PHARMACY_JPA_DDL_AUTO")
                        : "validate"
        );
        safeConfiguration.put(
                "showSql",
                DatabaseProperties.resolveOverride("pharmacy.jpa.show-sql", "PHARMACY_JPA_SHOW_SQL") != null
                        ? DatabaseProperties.resolveOverride("pharmacy.jpa.show-sql", "PHARMACY_JPA_SHOW_SQL")
                        : "true"
        );
        System.out.println("[JpaUtil] Initializing EntityManagerFactory with config: " + safeConfiguration);
    }
}

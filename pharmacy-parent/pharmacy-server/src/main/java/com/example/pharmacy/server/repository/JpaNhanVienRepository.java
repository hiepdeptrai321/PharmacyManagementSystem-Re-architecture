package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.NhanVienEntity;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class JpaNhanVienRepository implements NhanVienRepository {
    private final EntityManager entityManager;

    public JpaNhanVienRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<NhanVienEntity> findByUsername(String username) {
        throw new UnsupportedOperationException(
                "JPA repository wiring will be implemented in step 2. Inject an EntityManager and replace the in-memory repository."
        );
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}

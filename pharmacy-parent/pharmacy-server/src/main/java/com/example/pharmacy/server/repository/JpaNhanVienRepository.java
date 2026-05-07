package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.config.JpaUtil;
import com.example.pharmacy.server.entity.NhanVienEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Optional;

public class JpaNhanVienRepository implements NhanVienRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaNhanVienRepository() {
        this(JpaUtil.getEntityManagerFactory());
    }

    public JpaNhanVienRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<NhanVienEntity> findByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            """
                            SELECT nv
                            FROM NhanVienEntity nv
                            WHERE nv.taiKhoan = :username
                            """,
                            NhanVienEntity.class
                    )
                    .setParameter("username", username)
                    .getResultStream()
                    .findFirst();
        } finally {
            entityManager.close();
        }
    }
}

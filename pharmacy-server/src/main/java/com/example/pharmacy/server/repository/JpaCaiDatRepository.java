package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.config.JpaUtil;
import com.example.pharmacy.server.entity.ThongSoUngDungEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class JpaCaiDatRepository implements CaiDatRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaCaiDatRepository() {
        this(JpaUtil.getEntityManagerFactory());
    }

    public JpaCaiDatRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<ThongSoUngDungEntity> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            """
                            SELECT ts
                            FROM ThongSoUngDungEntity ts
                            ORDER BY ts.tenThongSo
                            """,
                            ThongSoUngDungEntity.class
                    )
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean update(ThongSoUngDungEntity entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ThongSoUngDungEntity existing = entityManager.find(ThongSoUngDungEntity.class, entity.getTenThongSo());
            if (existing == null) {
                transaction.rollback();
                return false;
            }
            existing.setGiaTri(entity.getGiaTri());
            transaction.commit();
            return true;
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }
}

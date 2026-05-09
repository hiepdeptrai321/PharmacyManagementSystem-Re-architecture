package com.example.pharmacy.server.repository.jpa;

import com.example.pharmacy.server.config.JpaUtil;
import com.example.pharmacy.server.entity.KhachHangEntity;
import com.example.pharmacy.server.repository.KhachHangRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaKhachHangRepository implements KhachHangRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaKhachHangRepository() {
        this(JpaUtil.getEntityManagerFactory());
    }

    public JpaKhachHangRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<KhachHangEntity> findAllActive() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            """
                            SELECT kh
                            FROM KhachHangEntity kh
                            WHERE kh.trangThai = true
                            ORDER BY kh.maKH
                            """,
                            KhachHangEntity.class
                    )
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<KhachHangEntity> findById(String maKhachHang) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(KhachHangEntity.class, maKhachHang));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public KhachHangEntity save(KhachHangEntity entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            return entity;
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public KhachHangEntity update(KhachHangEntity entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            KhachHangEntity merged = entityManager.merge(entity);
            transaction.commit();
            return merged;
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean softDelete(String maKhachHang) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            KhachHangEntity entity = entityManager.find(KhachHangEntity.class, maKhachHang);
            if (entity == null) {
                transaction.rollback();
                return false;
            }
            entity.setTrangThai(false);
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

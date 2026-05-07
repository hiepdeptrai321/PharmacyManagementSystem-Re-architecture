package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.config.JpaUtil;
import com.example.pharmacy.server.entity.KeHangEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaKeHangRepository implements KeHangRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaKeHangRepository() {
        this(JpaUtil.getEntityManagerFactory());
    }

    public JpaKeHangRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<KeHangEntity> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            """
                            SELECT kh
                            FROM KeHangEntity kh
                            ORDER BY kh.maKe
                            """,
                            KeHangEntity.class
                    )
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<KeHangEntity> findById(String maKeHang) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(KeHangEntity.class, maKeHang));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<KeHangEntity> findByTenKe(String tenKe) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<KeHangEntity> result = entityManager.createQuery(
                            """
                            SELECT kh
                            FROM KeHangEntity kh
                            WHERE LOWER(kh.tenKe) = LOWER(:tenKe)
                            """,
                            KeHangEntity.class
                    )
                    .setParameter("tenKe", tenKe)
                    .setMaxResults(1)
                    .getResultList();
            return result.stream().findFirst();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public KeHangEntity save(KeHangEntity entity) {
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
    public KeHangEntity update(KeHangEntity entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            KeHangEntity merged = entityManager.merge(entity);
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
    public boolean deleteById(String maKeHang) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            KeHangEntity entity = entityManager.find(KeHangEntity.class, maKeHang);
            if (entity == null) {
                transaction.rollback();
                return false;
            }
            entityManager.remove(entity);
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

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findThuocNamesByKeHang(String maKeHang) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createNativeQuery(
                            """
                            SELECT TenThuoc
                            FROM Thuoc_SanPham
                            WHERE ViTri = :maKeHang AND TrangThaiXoa = 0
                            ORDER BY TenThuoc
                            """
                    )
                    .setParameter("maKeHang", maKeHang)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
}

package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.config.JpaUtil;
import com.example.pharmacy.server.entity.LuongNhanVienEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaLuongNhanVienRepository implements LuongNhanVienRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaLuongNhanVienRepository() {
        this(JpaUtil.getEntityManagerFactory());
    }

    public JpaLuongNhanVienRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<LuongNhanVienEntity> findByMaNhanVien(String maNhanVien) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            """
                            SELECT lnv
                            FROM LuongNhanVienEntity lnv
                            WHERE lnv.maNV = :maNhanVien
                            ORDER BY lnv.tuNgay DESC, lnv.maLNV DESC
                            """,
                            LuongNhanVienEntity.class
                    )
                    .setParameter("maNhanVien", maNhanVien)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<LuongNhanVienEntity> findById(String maLuongNhanVien) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(LuongNhanVienEntity.class, maLuongNhanVien));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public LuongNhanVienEntity save(LuongNhanVienEntity entity) {
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
    public LuongNhanVienEntity update(LuongNhanVienEntity entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            LuongNhanVienEntity merged = entityManager.merge(entity);
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
}

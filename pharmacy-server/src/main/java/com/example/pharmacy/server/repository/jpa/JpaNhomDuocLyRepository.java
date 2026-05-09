package com.example.pharmacy.server.repository.jpa;

import com.example.pharmacy.server.config.JpaUtil;
import com.example.pharmacy.server.entity.NhomDuocLyEntity;
import com.example.pharmacy.server.repository.NhomDuocLyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaNhomDuocLyRepository implements NhomDuocLyRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaNhomDuocLyRepository() {
        this(JpaUtil.getEntityManagerFactory());
    }

    public JpaNhomDuocLyRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<NhomDuocLyEntity> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            """
                            SELECT ndl
                            FROM NhomDuocLyEntity ndl
                            ORDER BY ndl.maNDL
                            """,
                            NhomDuocLyEntity.class
                    )
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<NhomDuocLyEntity> findById(String maNhomDuocLy) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(NhomDuocLyEntity.class, maNhomDuocLy));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public NhomDuocLyEntity save(NhomDuocLyEntity entity) {
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
    public NhomDuocLyEntity update(NhomDuocLyEntity entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            NhomDuocLyEntity merged = entityManager.merge(entity);
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
    public boolean deleteById(String maNhomDuocLy) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            NhomDuocLyEntity entity = entityManager.find(NhomDuocLyEntity.class, maNhomDuocLy);
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
    public List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createNativeQuery(
                            """
                            SELECT TenThuoc
                            FROM Thuoc_SanPham
                            WHERE MaNDL = :maNhomDuocLy AND TrangThaiXoa = 0
                            ORDER BY TenThuoc
                            """
                    )
                    .setParameter("maNhomDuocLy", maNhomDuocLy)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
}

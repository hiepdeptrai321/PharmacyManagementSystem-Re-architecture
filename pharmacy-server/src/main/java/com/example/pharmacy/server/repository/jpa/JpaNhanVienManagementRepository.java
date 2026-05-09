package com.example.pharmacy.server.repository.jpa;

import com.example.pharmacy.server.config.JpaUtil;
import com.example.pharmacy.server.entity.NhanVienEntity;
import com.example.pharmacy.server.repository.NhanVienManagementRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaNhanVienManagementRepository implements NhanVienManagementRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaNhanVienManagementRepository() {
        this(JpaUtil.getEntityManagerFactory());
    }

    public JpaNhanVienManagementRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<NhanVienEntity> findAllNotDeleted() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            """
                            SELECT nv
                            FROM NhanVienEntity nv
                            WHERE nv.trangThaiXoa = false
                            ORDER BY nv.maNV
                            """,
                            NhanVienEntity.class
                    )
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<NhanVienEntity> findById(String maNhanVien) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(NhanVienEntity.class, maNhanVien));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean existsByUsername(String username, String excludedMaNhanVien) {
        if (username == null || username.isBlank()) {
            return false;
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Long count = entityManager.createQuery(
                            """
                            SELECT COUNT(nv)
                            FROM NhanVienEntity nv
                            WHERE LOWER(nv.taiKhoan) = LOWER(:username)
                              AND (:excludedMaNhanVien IS NULL OR nv.maNV <> :excludedMaNhanVien)
                            """,
                            Long.class
                    )
                    .setParameter("username", username.trim())
                    .setParameter("excludedMaNhanVien", excludedMaNhanVien)
                    .getSingleResult();
            return count != null && count > 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public NhanVienEntity save(NhanVienEntity entity) {
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
    public NhanVienEntity update(NhanVienEntity entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            NhanVienEntity merged = entityManager.merge(entity);
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
    public boolean softDelete(String maNhanVien) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            NhanVienEntity entity = entityManager.find(NhanVienEntity.class, maNhanVien);
            if (entity == null) {
                transaction.rollback();
                return false;
            }
            entity.setTrangThaiXoa(true);
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

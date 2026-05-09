package com.example.pharmacy.server.repository.jpa;

import com.example.pharmacy.server.config.JpaUtil;
import com.example.pharmacy.server.entity.DonViTinhEntity;
import com.example.pharmacy.server.repository.DonViTinhRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaDonViTinhRepository implements DonViTinhRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaDonViTinhRepository() {
        this(JpaUtil.getEntityManagerFactory());
    }

    public JpaDonViTinhRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<DonViTinhEntity> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            """
                            SELECT dvt
                            FROM DonViTinhEntity dvt
                            ORDER BY dvt.maDVT
                            """,
                            DonViTinhEntity.class
                    )
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<DonViTinhEntity> findById(String maDonViTinh) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(DonViTinhEntity.class, maDonViTinh));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<DonViTinhEntity> findByTenDonViTinh(String tenDonViTinh) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            """
                            SELECT dvt
                            FROM DonViTinhEntity dvt
                            WHERE dvt.tenDonViTinh = :tenDonViTinh
                            """,
                            DonViTinhEntity.class
                    )
                    .setParameter("tenDonViTinh", tenDonViTinh)
                    .getResultStream()
                    .findFirst();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public DonViTinhEntity save(DonViTinhEntity entity) {
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
    public DonViTinhEntity update(DonViTinhEntity entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            DonViTinhEntity merged = entityManager.merge(entity);
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
    public boolean deleteById(String maDonViTinh) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            DonViTinhEntity entity = entityManager.find(DonViTinhEntity.class, maDonViTinh);
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
}

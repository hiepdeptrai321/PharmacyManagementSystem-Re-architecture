package com.example.pharmacy.server.repository.jpa;

import com.example.pharmacy.server.config.JpaUtil;
import com.example.pharmacy.server.entity.NhaCungCapEntity;
import com.example.pharmacy.server.repository.NhaCungCapRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaNhaCungCapRepository implements NhaCungCapRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaNhaCungCapRepository() {
        this(JpaUtil.getEntityManagerFactory());
    }

    public JpaNhaCungCapRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<NhaCungCapEntity> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            """
                            SELECT ncc
                            FROM NhaCungCapEntity ncc
                            ORDER BY ncc.maNCC
                            """,
                            NhaCungCapEntity.class
                    )
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<NhaCungCapEntity> findById(String maNhaCungCap) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(NhaCungCapEntity.class, maNhaCungCap));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public NhaCungCapEntity save(NhaCungCapEntity entity) {
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
    public NhaCungCapEntity update(NhaCungCapEntity entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            NhaCungCapEntity merged = entityManager.merge(entity);
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
    public boolean deleteById(String maNhaCungCap) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            NhaCungCapEntity entity = entityManager.find(NhaCungCapEntity.class, maNhaCungCap);
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

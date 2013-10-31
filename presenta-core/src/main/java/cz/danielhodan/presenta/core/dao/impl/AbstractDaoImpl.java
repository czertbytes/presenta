package cz.danielhodan.presenta.core.dao.impl;

import cz.danielhodan.presenta.core.common.CoreException;
import cz.danielhodan.presenta.core.dao.AbstractDao;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
public abstract class AbstractDaoImpl<E, PK> implements AbstractDao<E, PK> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<E> entityClass;

    AbstractDaoImpl(final Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public E insert(E entity) throws CoreException {
        try {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            throw new CoreException(e);
        }

        return entity;
    }

    public E update(E entity) throws CoreException {
        try {
            return entityManager.merge(entity);
        } catch (PersistenceException e) {
            throw new CoreException(e);
        }
    }

    public boolean delete(final PK id) {
        final E entity = findById(id);
        if (entity == null) {
            return false;
        } else {
            entityManager.remove(entity);
            return true;
        }
    }

    @Transactional(readOnly = true)
    public E findById(final PK id) {
        return entityManager.find(entityClass, id);
    }

    @Transactional(readOnly = true)
    public List<E> findAll() {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        final Root<E> entity = criteriaQuery.from(entityClass);

        criteriaQuery.select(entity);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public boolean isAttached(final E entity) {
        return entityManager.contains(entity);
    }
}

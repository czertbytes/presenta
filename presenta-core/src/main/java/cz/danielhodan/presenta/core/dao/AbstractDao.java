package cz.danielhodan.presenta.core.dao;

import cz.danielhodan.presenta.core.common.CoreException;

import java.util.List;

public interface AbstractDao<E, PK> {

    E insert(E entity) throws CoreException;

    E update(E entity) throws CoreException;

    boolean delete(final PK id);

    E findById(final PK id);

    List<E> findAll();

    boolean isAttached(E entity);
}

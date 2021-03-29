package com.epam.forum.model.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.model.entity.Entity;

public interface Repository<T extends Entity> {
	Optional<T> get(Long id) throws RepositoryException;
    void add(T entity) throws RepositoryException;
    void update(T entity) throws RepositoryException;
    void remove(T entity) throws RepositoryException;
    List<T> query(Specification<T> specification) throws RepositoryException;
    List<T> sort(Comparator<T> comparator) throws RepositoryException;
    List<T> getEntities() throws RepositoryException;
}

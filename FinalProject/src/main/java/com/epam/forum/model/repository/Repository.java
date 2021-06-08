package com.epam.forum.model.repository;

import java.util.Comparator;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.model.entity.Entity;
import com.epam.forum.model.repository.spec.Specification;

/**
 * Interface for generic CRUD operations on a repository for a specific type
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 * @param <K> the type of the id of the entity the repository manages
 * @param <T> the domain type the repository manages
 */
public interface Repository<K, T extends Entity> {
	public static Logger logger = LogManager.getLogger();

	/**
	 * Retrieves an entity by its id
	 * 
	 * @param id id of the entity.
	 * @return the entity with the given id or {@link Optional#empty()} if none
	 *         found
	 * @throws RepositoryException
	 */
	Optional<T> findById(K id) throws RepositoryException;

	/**
	 * Creates a given entity
	 * 
	 * @param entity created entity
	 * @throws RepositoryException
	 */
	void create(T entity) throws RepositoryException;

	/**
	 * Updates a given entity
	 * 
	 * @param entity updated entity
	 * @throws RepositoryException
	 */
	void update(T entity) throws RepositoryException;

	/**
	 * Deletes a given entity
	 * 
	 * @param entity deleted entity
	 * @throws RepositoryException
	 */
	void delete(T entity) throws RepositoryException;

	/**
	 * Return all instances of the type which matches specification
	 * 
	 * @param specification {@link Specification} object
	 * @return return all instances of the type which matches specification
	 * @throws RepositoryException
	 */
	Iterable<T> query(Specification<T> specification) throws RepositoryException;

	/**
	 * Returns all entities sorted by the given comparator
	 * 
	 * @param comparator
	 * @return all entities sorted by the given comparator
	 * @throws RepositoryException
	 */
	Iterable<T> sort(Comparator<T> comparator) throws RepositoryException;

	/**
	 * Returns all instances of the type
	 * 
	 * @return all entities
	 * @throws RepositoryException
	 */
	Iterable<T> findAll() throws RepositoryException;
}
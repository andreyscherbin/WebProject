package com.epam.forum.model.service;

import java.util.List;
import java.util.Optional;

import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Section;

/**
 * Interface for section service. This service is responsible for the work
 * with sections
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public interface SectionService {
	
	/**
	 * Returns all sections
	 * 
	 * @return all sections
	 * @throws ServiceException
	 */
	List<Section> findAllSections() throws ServiceException;
	
	/**
	 * Retrieves a section by its id
	 * 
	 * @param sectionId id of the section.
	 * @return the section with the given id or {@link Optional#empty()} if none found
	 * @throws ServiceException
	 */
	Optional<Section> findSectionById(long sectionId) throws ServiceException;

	
	/**
	 * Creates a given section
	 * 
	 * @param section created section
	 * @throws ServiceException
	 */
	void create(Section section) throws ServiceException;

	/**
	 * Deletes a given section
	 * 
	 * @param section deleted section
	 * @throws ServiceException
	 */
	void delete(Section section) throws ServiceException;
}

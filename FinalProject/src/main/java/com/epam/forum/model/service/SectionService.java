package com.epam.forum.model.service;

import java.util.List;
import java.util.Optional;

import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Section;

public interface SectionService {
	List<Section> findAllSections() throws ServiceException;

	Optional<Section> findSectionById(long sectionId) throws ServiceException;

	void create(Section section) throws ServiceException;

	void delete(Section section) throws ServiceException;
}

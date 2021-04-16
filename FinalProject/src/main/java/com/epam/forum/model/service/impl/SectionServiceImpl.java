package com.epam.forum.model.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.implRep.SectionRepositoryImpl;
import com.epam.forum.model.service.SectionService;

public class SectionServiceImpl implements SectionService {

	private static Logger logger = LogManager.getLogger();
	private static final SectionService instance = new SectionServiceImpl();
	private Repository<Long, Section> sectionRepository;

	private SectionServiceImpl() {
		sectionRepository = new SectionRepositoryImpl();
	}

	public static SectionService getInstance() {
		return instance;
	}

	@Override
	public List<Section> findAllSections() throws ServiceException {
		List<Section> sections = null;
		try {
			sections = sectionRepository.findAll();
		} catch (RepositoryException e) {
			throw new ServiceException("findAll sections exception", e);
		}
		return sections;
	}
}

package com.epam.forum.model.service.impl;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.entity.SectionTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.spec.Operation;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;
import com.epam.forum.model.repository.spec.impl.IdSectionSpecification;
import com.epam.forum.model.service.SectionService;

public class SectionServiceImpl implements SectionService {

	private static Logger logger = LogManager.getLogger();
	private static SectionService instance;
	private Repository<Long, Section> sectionRepository;

	private SectionServiceImpl(Repository<Long, Section> sectionRepository) {
		this.sectionRepository = sectionRepository;
	}

	public static SectionService getInstance(Repository<Long, Section> sectionRepository) {
		if (instance == null) {
			instance = new SectionServiceImpl(sectionRepository);
		}
		return instance;
	}

	@Override
	public List<Section> findAllSections() throws ServiceException {
		List<Section> sections = null;
		try {
			sections = (List<Section>) sectionRepository.findAll();
		} catch (RepositoryException e) {
			logger.error("findAll sections exception", e);
			throw new ServiceException("findAll sections exception", e);
		}
		return sections;
	}

	@Override
	public Optional<Section> findSectionById(long sectionId) throws ServiceException {
		Optional<Section> section;
		List<Section> sections;
		try {
			Specification<Section> idSpec = new IdSectionSpecification(
					new SearchCriterion(SectionTable.SECTION_ID, Operation.EQUAL, sectionId));
			sections = (List<Section>) sectionRepository.query(idSpec);
			if (!sections.isEmpty()) {
				section = Optional.of(sections.get(0));
			} else {
				section = Optional.empty();
			}
		} catch (RepositoryException e) {
			logger.error("find section exception with id: " + sectionId, e);
			throw new ServiceException("find section exception with id: " + sectionId, e);
		}
		return section;
	}

	@Override
	public void create(Section section) throws ServiceException {
		try {
			sectionRepository.create(section);
		} catch (RepositoryException e) {
			logger.error("create section exception with section: " + section, e);
			throw new ServiceException("create section exception with section: " + section, e);
		}
	}

	@Override
	public void delete(Section section) throws ServiceException {
		try {
			sectionRepository.delete(section);
		} catch (RepositoryException e) {
			logger.error("delete section exception with section: " + section, e);
			throw new ServiceException("delete section exception with section: " + section, e);
		}
	}
}

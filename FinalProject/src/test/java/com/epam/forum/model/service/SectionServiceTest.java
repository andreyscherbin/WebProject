package com.epam.forum.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.entity.SectionTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.spec.Operation;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;
import com.epam.forum.model.repository.spec.impl.IdSectionSpecification;
import com.epam.forum.model.service.impl.SectionServiceImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class SectionServiceTest {

	SectionService sectionService;

	@Mock
	private Repository<Long, Section> sectionRepository;

	private Section mockedSection;
	private List<Section> mockedSections;

	private static final Long SECTION_ID = 1L;
	Specification<Section> idSpec;

	@BeforeClass
	public void setUp() throws RepositoryException {
		MockitoAnnotations.openMocks(this);		
		idSpec = new IdSectionSpecification(new SearchCriterion(SectionTable.SECTION_ID, Operation.EQUAL, SECTION_ID));
		createMockedSection();
		createMockedSections();
		setupMockedRepository();
		sectionService = SectionServiceImpl.getInstance(sectionRepository);
	}

	@Test
	public void findAllSectionsTest() throws RepositoryException, ServiceException {
		List<Section> resultSections = sectionService.findAllSections();
		verify(sectionRepository, VerificationModeFactory.times(1)).findAll();
		assertEquals(resultSections, mockedSections);
	}

	@Test
	public void findSectionByIdTest() throws ServiceException, RepositoryException {
		Optional<Section> actual = sectionService.findSectionById(SECTION_ID);
		verify(sectionRepository, VerificationModeFactory.times(1)).query(idSpec);
		assertEquals(actual.get().getId(), mockedSection.getId());
	}

	private void createMockedSections() {
		mockedSections = new ArrayList<>();
		mockedSections.add(mockedSection);
	}

	private void createMockedSection() {
		mockedSection = new Section();
		mockedSection.setId(1L);
		mockedSection.setHeader("header");
	}

	private void setupMockedRepository() throws RepositoryException {
		when(sectionRepository.findAll()).thenReturn(mockedSections);
		when(sectionRepository.query(idSpec)).thenReturn(List.of(mockedSection));
	}

	@AfterClass
	public void tierDown() {
		sectionService = null;
		mockedSection = null;
		mockedSections = null;
		idSpec = null;
	}
}

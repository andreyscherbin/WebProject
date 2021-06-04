package com.epam.forum.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.quality.Strictness;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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

	@InjectMocks
	SectionService sectionService;

	@Mock
	private Repository<Long, Section> sectionRepository;

	MockitoSession mockito;

	private Section mockedSection;
	private List<Section> mockedSections;
	private static final Long SECTION_ID = 1L;
	Specification<Section> idSpec;

	@BeforeClass
	public void beforeClass() throws RepositoryException {
		idSpec = new IdSectionSpecification(new SearchCriterion(SectionTable.SECTION_ID, Operation.EQUAL, SECTION_ID));
		createMockedSection();
		createMockedSections();
		sectionService = SectionServiceImpl.getInstance(sectionRepository);
	}

	@BeforeMethod
	public void beforeMethod() throws RepositoryException, ServiceException {
		mockito = Mockito.mockitoSession().initMocks(this).strictness(Strictness.STRICT_STUBS).startMocking();
	}

	@Test
	public void findAllSectionsTest() throws RepositoryException, ServiceException {
		when(sectionRepository.findAll()).thenReturn(mockedSections);
		List<Section> resultSections = sectionService.findAllSections();
		verify(sectionRepository, VerificationModeFactory.times(1)).findAll();
		assertEquals(resultSections, mockedSections);
	}

	@Test
	public void findSectionByIdTest() throws ServiceException, RepositoryException {
		when(sectionRepository.query(idSpec)).thenReturn(List.of(mockedSection));
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

	@AfterMethod
	public void afterMethod() {
		mockito.finishMocking();
	}

	@AfterClass
	public void afterClass() {
		sectionService = null;
		mockedSection = null;
		mockedSections = null;
		idSpec = null;
	}
}

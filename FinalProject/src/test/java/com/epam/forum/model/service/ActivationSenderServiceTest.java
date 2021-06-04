package com.epam.forum.model.service;

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
import com.epam.forum.model.entity.ActivationCode;
import com.epam.forum.model.entity.ActivationCodeTable;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.spec.Operation;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;
import com.epam.forum.model.repository.spec.impl.IdActivationCodeSpecification;
import com.epam.forum.model.service.impl.ActivationSenderServiceImpl;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static org.mockito.Mockito.verify;

public class ActivationSenderServiceTest {

	@InjectMocks
	ActivationSenderService activationSenderService;

	@Mock
	private Repository<String, ActivationCode> activationCodeRepository;

	MockitoSession mockito;

	private ActivationCode mockedActivationCode;
	private static final String ID = "262cdd615c1b5cfd606f27f6755efde4eb07e4ebd48ead00ea086678dc4e9ee3";
	Specification<ActivationCode> idSpec;

	@BeforeMethod
	public void beforeMethod() throws RepositoryException, ServiceException {
		mockito = Mockito.mockitoSession().initMocks(this).strictness(Strictness.STRICT_STUBS).startMocking();
	}

	@BeforeClass
	public void setUp() throws RepositoryException {
		idSpec = new IdActivationCodeSpecification(
				new SearchCriterion(ActivationCodeTable.ACTIVATION_CODE_ID, Operation.EQUAL, ID));
		createMockedActivationCode();
		activationSenderService = ActivationSenderServiceImpl.getInstance(activationCodeRepository);
	}

	@Test
	public void sendActivationCodeTest() {
		User user = new User();
		user.setId(5L);
		user.setUserName("andrei");
		user.setEmail("andrey123scherbin@gmail.com");
		try {
			activationSenderService.sendActivationCode(user);
		} catch (ServiceException e) {
			fail("Should not have thrown any exception if worked correctly", e);
		}
	}

	@Test
	public void findActivationCodeByIdTest() throws ServiceException, RepositoryException {
		when(activationCodeRepository.query(idSpec)).thenReturn(List.of(mockedActivationCode));
		Optional<ActivationCode> actual = activationSenderService.findActivationCodeById(ID);
		verify(activationCodeRepository, VerificationModeFactory.times(1)).query(idSpec);
		assertEquals(actual.get().getId(), mockedActivationCode.getId());
	}

	private void createMockedActivationCode() {
		mockedActivationCode = new ActivationCode();
		mockedActivationCode.setId(ID);
	}

	@AfterMethod
	public void afterMethod() {
		mockito.finishMocking();
	}

	@AfterClass
	public void afterClass() {
		activationSenderService = null;
		mockedActivationCode = null;
		idSpec = null;
	}
}

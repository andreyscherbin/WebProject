package com.epam.forum.model.service;

import org.testng.annotations.Test;
import com.epam.forum.command.builder.UserBuilder;
import com.epam.forum.command.builder.impl.UserBuilderImpl;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.UserTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.spec.Operation;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;
import com.epam.forum.model.repository.spec.impl.EmailUserSpecification;
import com.epam.forum.model.repository.spec.impl.UserNameSpecification;
import com.epam.forum.model.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserServiceTest {

	@InjectMocks
	UserService userService;

	@Mock
	private Repository<Long, User> userRepository;

	MockitoSession mockito;

	private User mockedUser;
	private List<User> mockedUsers;
	private static final String USERNAME = "andrey";
	private static final String EMAIL = "andrey123scherbin@gmail.com";
	private static final String PASSWORD = "4544974Qwerty";
	private static final Long SUCCESS_REGISTRATION = 1L;
	private static final Long EMAIL_ALREADY_IN_USE = 2L;
	private static final Long USERNAME_ALREADY_IN_USE = 3L;
	Specification<User> userNameSpec;
	Specification<User> emailSpec;
	Specification<User> userNameOrEmailSpec;

	@BeforeClass
	public void beforeClass() throws ServiceException {
		userNameSpec = new UserNameSpecification(new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, USERNAME));
		emailSpec = new EmailUserSpecification(new SearchCriterion(UserTable.EMAIL, Operation.EQUAL, EMAIL));
		userNameOrEmailSpec = new UserNameSpecification(
				new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, USERNAME)).or(emailSpec);
		createMockedUser();
		createMockedUsers();
		userService = UserServiceImpl.getInstance(userRepository);
	}

	@BeforeMethod
	public void beforeMethod() throws RepositoryException, ServiceException {
		mockito = Mockito.mockitoSession().initMocks(this).strictness(Strictness.STRICT_STUBS).startMocking();
	}

	@Test
	public void authenticateTest() throws RepositoryException, ServiceException {
		when(userRepository.query(userNameSpec)).thenReturn(List.of(mockedUser));
		Optional<User> actual = userService.authenticate(USERNAME, PASSWORD);
		verify(userRepository, VerificationModeFactory.times(1)).query(userNameSpec);
		assertEquals(actual, Optional.of(mockedUser));
	}

	@Test
	public void registrationSuccessTest() throws ServiceException, RepositoryException {
		when(userRepository.query(userNameOrEmailSpec)).thenReturn(Collections.emptyList());
		Map<Long, List<Optional<User>>> actual = userService.registrate(USERNAME, PASSWORD, EMAIL);
		verify(userRepository, VerificationModeFactory.times(1)).query(userNameOrEmailSpec);
		assertTrue(actual.containsKey(SUCCESS_REGISTRATION));
	}

	@Test
	public void registrationFailedEmailAlreadyInUseTest() throws ServiceException, RepositoryException {
		when(userRepository.query(userNameOrEmailSpec)).thenReturn(List.of(mockedUser));
		Map<Long, List<Optional<User>>> actual = userService.registrate(USERNAME, PASSWORD, EMAIL);
		verify(userRepository, VerificationModeFactory.times(1)).query(userNameOrEmailSpec);
		assertTrue(actual.containsKey(EMAIL_ALREADY_IN_USE));
	}

	@Test
	public void registrationFailedUserNameAlreadyInUseTest() throws ServiceException, RepositoryException {
		when(userRepository.query(userNameOrEmailSpec)).thenReturn(List.of(mockedUser));
		Map<Long, List<Optional<User>>> actual = userService.registrate(USERNAME, PASSWORD, EMAIL);
		verify(userRepository, VerificationModeFactory.times(1)).query(userNameOrEmailSpec);
		assertTrue(actual.containsKey(USERNAME_ALREADY_IN_USE));
	}

	private void createMockedUsers() {
		mockedUsers = new ArrayList<>();
		mockedUsers.add(mockedUser);
	}

	private void createMockedUser() throws ServiceException {
		UserBuilder userBuilder = new UserBuilderImpl();
		userBuilder.buildUsername(USERNAME).buildPassword(PASSWORD).buildEmail(EMAIL)
				.buildRegisterDate(LocalDateTime.now()).buildIsEmailVerifed(false).buildIsActive(true)
				.buildRole(Role.USER);
		mockedUser = userBuilder.getUser();
	}

	@AfterMethod
	public void afterMethod() {
		mockito.finishMocking();
	}

	@AfterClass
	public void afterClass() {
		userService = null;
		mockedUser = null;
		mockedUsers = null;
		userNameSpec = null;
		userNameOrEmailSpec = null;
	}
}

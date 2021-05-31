package com.epam.forum.model.service;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.service.impl.ActivationSenderServiceImpl;

public class ActivationSenderServiceTest {

	ActivationSenderService activationSenderService;

	@BeforeClass
	public void setUp() {
		activationSenderService = ActivationSenderServiceImpl.getInstance();
	}

	@Test
	public void sendActivationCodeTest() throws ServiceException {
		User user = new User();
		user.setId((long) 1);
		user.setUserName("andrey");
		user.setEmail("andrey123scherbin@gmail.com");
		activationSenderService.sendActivationCode(user);
	}

	@AfterClass
	public void tierDown() {
		activationSenderService = null;
	}
}

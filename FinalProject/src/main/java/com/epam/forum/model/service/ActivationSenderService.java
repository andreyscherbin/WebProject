package com.epam.forum.model.service;

import java.util.Optional;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.ActivationCode;
import com.epam.forum.model.entity.User;

public interface ActivationSenderService {
	void sendActivationCode(User user) throws ServiceException;
	Optional<ActivationCode> findActivationCodeById(String id) throws ServiceException;
	void delete(ActivationCode activationCode) throws ServiceException;
}

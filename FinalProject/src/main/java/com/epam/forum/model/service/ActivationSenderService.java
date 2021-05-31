package com.epam.forum.model.service;

import java.util.Optional;

import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.ActivationCode;
import com.epam.forum.model.entity.User;

/**
 * Interface for activation sender service. This service is responsible for the work
 * with activation codes and sending email to the users with them.  
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public interface ActivationSenderService {
	
	/**
	 * Send activation code to the given user
	 * 
	 * @param user the user to whom the email is sent
	 * @throws ServiceException
	 */
	void sendActivationCode(User user) throws ServiceException;
	
	/**
	 * Retrieves an activation code by its id
	 * 
	 * @param id id of the activation code.
	 * @return the activation code with the given id or {@link Optional#empty()} if none found
	 * @throws ServiceException
	 */
	Optional<ActivationCode> findActivationCodeById(String id) throws ServiceException;
	
	/**
	 * Deletes a given activationCode
	 * 
	 * @param activationCode deleted activation code
	 * @throws ServiceException
	 */
	void delete(ActivationCode activationCode) throws ServiceException;
}

package com.epam.forum.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.User;

/**
 * Interface for user service. This service is responsible for the work
 * with users
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public interface UserService {
	
	/**
	 * Retrieves a user by its id
	 * 
	 * @param id id of the user.
	 * @return the user with the given id or {@link Optional#empty()} if none found
	 * @throws ServiceException
	 */
	Optional<User> findUserById(Long id) throws ServiceException;
	
	/**
	 * Returns all users
	 * 
	 * @return all users
	 * @throws ServiceException
	 */
	List<User> findAllUsers() throws ServiceException;
	
	/**
	 * Returns all users which matches userName
	 * 
	 * @param userName userName of the user
	 * @return all users which matches userName
	 * @throws ServiceException
	 */
	List<User> findUsersByUserName(String userName) throws ServiceException;
	
	/**
	 * Authenticates the user 
	 * 
	 * @param userName userName of the user
	 * @param password password of the user
	 * @return the user with the given username and password or {@link Optional#empty()} if authentication failed
	 * @throws ServiceException
	 */
	Optional<User> authenticate(String userName, String password) throws ServiceException;
	
	/**
	 * Registers a user
	 * 
	 * @param userName userName of the user
	 * @param password password of the user
	 * @param email email of the user
	 * @return list with registered user with key 1L or lists of users matching by email and userName with key 2L and 3L
	 * @throws ServiceException
	 */
	Map<Long, List<Optional<User>>> registrate(String userName, String password, String email) throws ServiceException;
	
	/**
	 * Saves a given user
	 * 
	 * @param user saved user
	 * @throws ServiceException
	 */
	void save(User user) throws ServiceException;
	
	/**
	 * Updates last login date a given user
	 * 
	 * @param user updated user
	 * @throws ServiceException
	 */
	void updateLastLoginDate(User user) throws ServiceException;
	
	/**
	 * Changes role a given user
	 * 
	 * @param user changed role user
	 * @throws ServiceException
	 */
	void changeRole(User user) throws ServiceException;
	
	/**
	 * Bans a given user
	 * 
	 * @param user banned user
	 * @throws ServiceException
	 */
	void banUser(User user) throws ServiceException;
	
	/**
	 * Unban a given user
	 * 
	 * @param user unbanned user
	 * @throws ServiceException
	 */
	void unbanUser(User user) throws ServiceException;
}

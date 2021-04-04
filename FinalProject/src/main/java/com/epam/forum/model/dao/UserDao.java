package com.epam.forum.model.dao;

import java.util.List;
import com.epam.forum.exception.DaoException;
import com.epam.forum.model.entity.User;

public interface UserDao extends BaseDao<Long, User> {
	List<User> findUsersByUserName(String patternName) throws DaoException;
}

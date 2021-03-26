package com.epam.forum.model.repository;

import java.util.List;

import com.epam.forum.model.entity.User;

public interface UserRepository {
	User get(Long id);
    void add(User user);
    void update(User user);
    void remove(User user);
    List<User> getUserByUserName(String userName);
}

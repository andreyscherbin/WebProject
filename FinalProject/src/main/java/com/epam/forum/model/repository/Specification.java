package com.epam.forum.model.repository;

import com.epam.forum.model.entity.User;

public interface Specification {
	boolean specify(User user);
}

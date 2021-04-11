package com.epam.forum.model.entity.comparator;

import java.util.Comparator;
import com.epam.forum.model.entity.User;

public enum UserComparator implements Comparator<User> {
	ID {
		@Override
		public int compare(User user1, User user2) {
			return Long.compare(user1.getId(), user2.getId());
		}
	}
}
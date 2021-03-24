package com.epam.forum.comparator;

import java.util.Comparator;

import com.epam.forum.model.entity.Message;

public enum MessageComparator implements Comparator<Message> {
	ID {
		@Override
		public int compare(Message message1, Message message2) {
			return Long.compare(message1.getId(), message2.getId());
		}
	}
}
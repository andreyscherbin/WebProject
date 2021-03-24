package com.epam.forum.model.repository;
import com.epam.forum.model.entity.Message;

public interface Specification {
	boolean specify(Message message);
}

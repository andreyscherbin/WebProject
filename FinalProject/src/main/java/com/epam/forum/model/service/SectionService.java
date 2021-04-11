package com.epam.forum.model.service;

import java.util.List;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Section;

public interface SectionService {
	List<Section> findAllSections() throws ServiceException;
}

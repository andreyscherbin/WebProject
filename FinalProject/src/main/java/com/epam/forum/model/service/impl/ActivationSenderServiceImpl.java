package com.epam.forum.model.service.impl;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.exception.MailException;
import com.epam.forum.exception.RepositoryException;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.ActivationCode;
import com.epam.forum.model.entity.EmailMessage;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.ActivationCodeTable;
import com.epam.forum.model.repository.Operation;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;
import com.epam.forum.model.repository.impl.ActivationCodeRepositoryImpl;
import com.epam.forum.model.repository.impl.IdActivationCodeSpecification;
import com.epam.forum.model.service.ActivationSenderService;
import com.epam.forum.util.activation.Sha256ActivationCodeGenerator;
import com.epam.forum.util.email.EmailMessageFactory;
import com.epam.forum.util.email.MailSender;

public class ActivationSenderServiceImpl implements ActivationSenderService {

	private static Logger logger = LogManager.getLogger();
	private static ActivationSenderService instance;
	private ActivationCode activationCode;
	private Repository<String, ActivationCode> activationCodeRepository;

	private ActivationSenderServiceImpl() {
		activationCodeRepository = new ActivationCodeRepositoryImpl();
	}

	public static ActivationSenderService getInstance() {
		if (instance == null) {
			instance = new ActivationSenderServiceImpl();
		}
		return instance;
	}

	@Override
	public void sendActivationCode(User user) throws ServiceException {
		createActivationCode(user);
		saveActivationCode();
		sendMessage();
	}

	private void createActivationCode(User user) throws ServiceException {
		String generatedCode = Sha256ActivationCodeGenerator.generate("" + Math.random());
		activationCode = new ActivationCode(generatedCode, user);
	}

	private void saveActivationCode() throws ServiceException {
		try {
			activationCodeRepository.create(activationCode);
		} catch (RepositoryException e) {
			logger.error("create activationCode exception with activationCode: " + activationCode, e);
			throw new ServiceException("create activationCode exception with activationCode: " + activationCode, e);
		}
	}

	private void sendMessage() throws ServiceException {
		EmailMessage emailMessage = EmailMessageFactory.createEmailMessage(activationCode);
		try {
			MailSender.sendEmail(emailMessage);
		} catch (MailException e) {
			logger.error("sendEmail exception with emailMessage: " + emailMessage, e);
			throw new ServiceException("sendEmail exception with emailMessage: " + emailMessage, e);
		}
	}

	@Override
	public Optional<ActivationCode> findActivationCodeById(String id) throws ServiceException {
		Optional<ActivationCode> activationCode;
		List<ActivationCode> codes;
		try {
			Specification<ActivationCode> idSpec = new IdActivationCodeSpecification(
					new SearchCriterion(ActivationCodeTable.ACTIVATION_CODE_ID, Operation.EQUAL, id));
			codes = (List<ActivationCode>) activationCodeRepository.query(idSpec);
			if (!codes.isEmpty()) {
				activationCode = Optional.of(codes.get(0));
			} else {
				activationCode = Optional.empty();
			}
		} catch (RepositoryException e) {
			logger.error("find activationCode exception with id: " + id, e);
			throw new ServiceException("find activationCode exception with id: " + id, e);
		}
		return activationCode;
	}

	@Override
	public void delete(ActivationCode activationCode) throws ServiceException {
		try {
			activationCodeRepository.delete(activationCode);
		} catch (RepositoryException e) {
			logger.error("delete activationCode exception with activationCode: " + activationCode, e);
			throw new ServiceException("delete activationCode exception with activationCode: " + activationCode, e);
		}
	}
}

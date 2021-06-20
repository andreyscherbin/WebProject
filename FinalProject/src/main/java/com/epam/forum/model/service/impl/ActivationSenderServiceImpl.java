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
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.spec.Operation;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;
import com.epam.forum.model.repository.spec.impl.IdActivationCodeSpecification;
import com.epam.forum.model.service.ActivationSenderService;
import com.epam.forum.util.email.EmailMessageFactory;
import com.epam.forum.util.email.MailSender;
import com.epam.forum.util.encryptor.DataEncryptor;

public class ActivationSenderServiceImpl implements ActivationSenderService {

	private static Logger logger = LogManager.getLogger();
	private static ActivationSenderService instance;
	private ActivationCode activationCode;
	private Repository<String, ActivationCode> activationCodeRepository;

	private ActivationSenderServiceImpl(Repository<String, ActivationCode> activationCodeRepository) {
		this.activationCodeRepository = activationCodeRepository;
	}

	public static ActivationSenderService getInstance(Repository<String, ActivationCode> activationCodeRepository) {
		if (instance == null) {
			instance = new ActivationSenderServiceImpl(activationCodeRepository);
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
		String generatedCode = DataEncryptor.encryptData("" + Math.random());
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

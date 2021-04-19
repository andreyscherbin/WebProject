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
import com.epam.forum.model.entity.Operation;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.entity.ActivationCodeTable;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.impl.ActivationCodeRepositoryImpl;
import com.epam.forum.model.repository.impl.IdActivationCodeSpecification;
import com.epam.forum.model.service.ActivationSenderService;
import com.epam.forum.util.activation.Sha256ActivationCodeGenerator;
import com.epam.forum.util.email.EmailMessageFactory;
import com.epam.forum.util.email.MailSender;

public class ActivationSenderServiceImpl implements ActivationSenderService {

	private static Logger logger = LogManager.getLogger();
	private static final ActivationSenderService instance = new ActivationSenderServiceImpl();
	private ActivationCode activationCode;
	private Repository<String, ActivationCode> activationCodeRepository;

	private ActivationSenderServiceImpl() {
		activationCodeRepository = new ActivationCodeRepositoryImpl();
	}

	public static ActivationSenderService getInstance() {
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
			throw new ServiceException("create activationCode exception with activationCode: " + activationCode, e);
		}
	}

	private void sendMessage() throws ServiceException {
		EmailMessage emailMessage = EmailMessageFactory.createEmailMessage(activationCode);
		try {
			MailSender.sendEmail(emailMessage);
		} catch (MailException e) {
			throw new ServiceException("sendEmail exception with emailMessage: " + emailMessage, e);
		}
	}

	@Override
	public Optional<ActivationCode> findActivationCodeById(String id) throws ServiceException {
		Optional<ActivationCode> activationCode;
		List<ActivationCode> codes;
		try {
			IdActivationCodeSpecification spec1 = new IdActivationCodeSpecification(
					new SearchCriterion(ActivationCodeTable.ACTIVATION_CODE_ID, Operation.EQUAL, id));
			codes = activationCodeRepository.query(spec1);
			if (!codes.isEmpty()) {
				activationCode = Optional.of(codes.get(0));
			} else {
				activationCode = Optional.empty();
			}
		} catch (RepositoryException e) {
			throw new ServiceException("find activationCode exception with id: " + id, e);
		}
		return activationCode;
	}

	@Override
	public void delete(ActivationCode activationCode) throws ServiceException {
		try {
			activationCodeRepository.delete(activationCode);
		} catch (RepositoryException e) {
			throw new ServiceException("delete activationCode exception with activationCode: " + activationCode, e);
		}
	}
}

package fr.idprocess.common.service;

import javax.mail.MessagingException;

import fr.idprocess.common.util.Email;

public interface EmailService {

	void sendEmail(Email email) throws MessagingException;

}

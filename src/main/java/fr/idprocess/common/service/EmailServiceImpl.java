package fr.idprocess.common.service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import fr.idprocess.common.util.Email;

@Service
public class EmailServiceImpl implements EmailService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Async
	@Override
	public void sendEmail(Email email) throws MessagingException {

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper mimeMsgHelper;
		mimeMsgHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		Context context = new Context();
		context.setVariables(email.getModel());
		context.setVariable("slogan", "slogan");

		mimeMsgHelper.setTo(email.getTo());
		mimeMsgHelper.setSubject(email.getSubject());

		String html = templateEngine.process(email.getTemplate(), context);
		mimeMsgHelper.setText(html, true);
		mimeMsgHelper.addInline("slogan", new ClassPathResource("static/images/common/slogan.png"));

		if ((email.getAttachmentName() != null) && (email.getAttachmentPath() != null)) {
			mimeMsgHelper.addAttachment(email.getAttachmentName(), new ClassPathResource(email.getAttachmentPath()));
		}
		javaMailSender.send(message);
	}
}

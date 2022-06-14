package fr.idprocess.common.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
public class Email {

	private String template;
	private String attachmentName;
	private String attachmentPath;

	private String to;
	private String subject;
	private Map<String, Object> model;
}

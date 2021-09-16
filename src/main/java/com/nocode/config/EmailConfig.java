/**
 * 
 */
package com.nocode.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.nocode.constants.DefProperty;
import com.nocode.constants.MailProperty;
import com.nocode.constants.ResourceFile;
import com.nocode.exception.MailException;
import com.nocode.utils.ILogger;
import com.nocode.utils.PropertyUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailConfig.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
@Configuration
public class EmailConfig implements ILogger {

	/**
	 * Instantiates a new email config.
	 */
	public EmailConfig() {
		PropertyUtil.loadProperties(ResourceFile.MAIL_FILE);
	}

	/**
	 * Gets the java mail sender.
	 *
	 * @return the java mail sender
	 */
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		switch (PropertyUtil.get(MailProperty.HOST).toLowerCase()) {
		case "gmail":
			mailSender.setHost("smtp.gmail.com");
			break;
		case "outlook":
			mailSender.setHost("smtp-mail.outlook.com");
			break;
		case "office365":
			mailSender.setHost("smtp.office365.com");
			break;
		default:
			LOG.error("not a valid host, host should be either gmail or outlook or office365");
			throw new MailException("invalid host");
		}
		mailSender.setPort(587);
		String user = PropertyUtil.get(MailProperty.FROM);
		if (user.length() == 0) {
			LOG.error("enter valid username in mail.properties");
			throw new MailException("invalid username");
		}
		mailSender.setUsername(user);
		String password = PropertyUtil.get(MailProperty.PASSWORD);
		if (password.length() == 0) {
			LOG.error("enter valid password in mail.properties");
			throw new MailException("enter valid password");
		}
		mailSender.setPassword(password);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "false");

		return mailSender;
	}

	/**
	 * Email template.
	 *
	 * @param testcases the testcases
	 * @return the simple mail message
	 */
	@Bean
	public SimpleMailMessage emailTemplate(StringBuilder testcases) {
		SimpleMailMessage message = new SimpleMailMessage();
		String to = PropertyUtil.get(MailProperty.TO);
		if (to.length() == 0)
			throw new MailException("enter valid 'to' address(comma seperated values for multiple emails)");
		message.setTo(splitEmailId(to));
		String cc = PropertyUtil.get(MailProperty.CC);
		if (cc.length() == 0)
			message.setCc(cc.length() != 0 ? splitEmailId(cc) : null);
		message.setFrom(PropertyUtil.get(MailProperty.FROM));
		message.setSubject(
				!PropertyUtil.get(MailProperty.SUB).isEmpty() ? PropertyUtil.get(MailProperty.SUB) : DefProperty.SUB);
		message.setText(!PropertyUtil.get(MailProperty.TEXT).isEmpty()
				? PropertyUtil.get(MailProperty.TEXT) + "\n\n" + testcases
				: DefProperty.TEXT + "\n\n" + testcases);
		return message;
	}

	/**
	 * Split email id.
	 *
	 * @param ids the ids
	 * @return the string[]
	 */
	public String[] splitEmailId(String ids) {
		return ids.split(",");
	}
}

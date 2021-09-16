/**
 * 
 */
package com.nocode.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;

import com.nocode.config.EmailConfig;
import com.nocode.constants.MailProperty;
import com.nocode.constants.ResourceFile;

// TODO: Auto-generated Javadoc
/**
 * The Class SpringMailUtil.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class SpringMailUtil implements ILogger {

	/** The properties. */
	static Properties properties;

	/**
	 * Send mail.
	 *
	 * @param testcases the testcases
	 */
	public static void sendMail(StringBuilder testcases) {
		createFileIfNotExist();
		PropertyUtil.loadProperties(
				System.getProperty("user.dir") + "/src/main/resources/" + ResourceFile.MAIL_FILE.getValue());
		String sendMail = PropertyUtil.get(MailProperty.SEND_MAIL).toLowerCase();
		if (sendMail.equalsIgnoreCase("true")) {
			EmailConfig emailConfig = new EmailConfig();
			JavaMailSender mailSender = emailConfig.getJavaMailSender();
			mailSender.send(emailConfig.emailTemplate(testcases));
			ReportLogger.logMailRequest();
		} else if (sendMail.equalsIgnoreCase("false")) {
			ReportLogger.logMailProperties();
		}
	}

	/**
	 * Create file {@link mail.properties} if doesn't exist in main/resources
	 * folder.
	 *
	 * @return true if file created.
	 */
	private static void createFileIfNotExist() {
		String filePath = System.getProperty("user.dir") + "/src/main/resources/" + ResourceFile.MAIL_FILE.getValue();
		File file;
		try {
			file = new File(filePath);
			if (file.createNewFile()) {
				Properties props = PropertyUtil.loadProperties(filePath);
				properties = props;
				setDefaultProps();
				properties.store(new FileOutputStream(filePath), null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the default props.
	 */
	private static void setDefaultProps() {
		properties.put(MailProperty.SEND_MAIL.getValue(), "false");
		properties.put(MailProperty.HOST.getValue(), "gmail/outlook/office365");
		properties.put(MailProperty.FROM.getValue(), "");
		properties.put(MailProperty.PASSWORD.getValue(), "");
		properties.put(MailProperty.TO.getValue(), "");
		properties.put(MailProperty.CC.getValue(), "");
		properties.put(MailProperty.SUB.getValue(), "");
		properties.put(MailProperty.TEXT.getValue(), "");
	}
}

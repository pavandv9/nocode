/**
 * 
 */
package com.nocode.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.testng.Reporter;

import com.nocode.client.HttpRequest;
import com.nocode.constants.DefProperty;
import com.nocode.constants.MailProperty;
import com.nocode.constants.ResourceFile;
import com.nocode.response.HttpResponse;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
public class Logger implements ILogger {

	private Logger() {
	}

	private static HttpRequest httpRequest;
	public static final String NEW_LINE = System.lineSeparator();
	private static final String FORMAT = "%1$-15s%2$-20s%3$-50s";
	private static final String FORMAT_TEXT = FORMAT + NEW_LINE;
	public static final String SUFFIX = "=============================================================";
	private static final String RESPONSE_BODY = "Response body";

	public static void logRequest(HttpRequest httpRequest) {
		Logger.httpRequest = httpRequest;
		String prefix = NEW_LINE + "=========================  Request  =========================" + NEW_LINE;
		StringBuilder builder = new StringBuilder();
		builder.append(String.format(FORMAT_TEXT, "Http Method", ":", httpRequest.getHttpMethod()));
		builder.append(String.format(FORMAT_TEXT, "Base Url", ":", httpRequest.getBaseUrl()));
		builder.append(String.format(FORMAT_TEXT, "End Point", ":", formatEndPoint()));
		builder.append(String.format(FORMAT_TEXT, "Path Params", ":", prettyMap(httpRequest.getPathParams())));
		builder.append(String.format(FORMAT_TEXT, "Query Paramas", ":", prettyMap(httpRequest.getQueryParams())));
		builder.append(String.format(FORMAT_TEXT, "Headers", ":", prettyMap(httpRequest.getHeaders())));
		builder.append(String.format(FORMAT_TEXT, "Body", ":", NEW_LINE + getRequestBody()));
		String requestLog = prefix + builder.toString() + SUFFIX;
		LOG.info(requestLog);
		Reporter.log(JavaUtil.convertToHtml(prefix) + JavaUtil.convertToHtml(builder.toString()) + SUFFIX);
//		ReportUtil.logReqRes(Status.INFO, requestLog);
	}

	public static void logResponse(HttpResponse response) {
		String prefix = NEW_LINE + "=========================  Response  =========================" + NEW_LINE;
		StringBuilder builder = new StringBuilder();
		builder.append(String.format(FORMAT_TEXT, "Status code", ":", response.getStatusLine().getStatusCode()));
		builder.append(String.format(FORMAT_TEXT, "Status message", ":", response.getStatusLine().getStatusMessage()));
		builder.append(String.format(FORMAT_TEXT, "Headers", ":", prettyMap(response.getHeaders())));
		appendBody(builder, response);
		builder.append(String.format(FORMAT_TEXT, "Find the report here", ":", "attach report link here"));
		String responseLog = prefix + builder.toString() + SUFFIX;
		LOG.info(responseLog);
		Reporter.log(JavaUtil.convertToHtml(prefix) + JavaUtil.convertToHtml(builder.toString()) + SUFFIX);
//		ReportUtil.logReqRes(Status.PASS, responseLog);
	}

	private static String prettyMap(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			sb.append(entry.getKey());
			sb.append('=').append('"');
			sb.append(entry.getValue());
			sb.append('"');
			if (iter.hasNext()) {
				sb.append(',').append(String.format(NEW_LINE + "%35s", ""));
			}
		}
		return sb.toString().isEmpty() ? "<nil>" : sb.toString();
	}

	private static StringBuilder appendBody(StringBuilder builder, HttpResponse response) {
		if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("5")) {
			try {
				builder.append(String.format(FORMAT_TEXT, RESPONSE_BODY, ":",
						NEW_LINE + JavaUtil.prettyJson(response.getBody().toString())));
			} catch (JSONException e) {
				if (e.getMessage().contains("JSONObject text must begin with")) {
					if (httpRequest.getHeaders() != null
							|| httpRequest.getHeaders().get("Accept").toString().contains("xml")) {
						builder.append(String.format(FORMAT_TEXT, RESPONSE_BODY, ":",
								NEW_LINE + JavaUtil.prettyXml(response.getBody().toString())));
					} else {
						JSONObject xmlJsonObject = XML.toJSONObject(response.getBody().toString());
						String jsonBody = xmlJsonObject.toString(4);
						builder.append(String.format(FORMAT_TEXT, RESPONSE_BODY, ":",
								NEW_LINE + JavaUtil.prettyJson(jsonBody)));
					}
				} else
					throw new JSONException(e);
			}
		} else
			builder.append(String.format(FORMAT_TEXT, RESPONSE_BODY, ":", NEW_LINE + response.getBody()));
		return builder;
	}

	private static String formatEndPoint() {
		return (httpRequest.getEndPoint() == null || httpRequest.getEndPoint().isEmpty()) ? "<nil>"
				: httpRequest.getEndPoint();
	}

	private static String getRequestBody() {
		return httpRequest.getContentType().contains("json")
				? JavaUtil.prettyJson(JavaUtil.toJson(httpRequest.getBody()))
				: JavaUtil.prettyXml(JavaUtil.toXml(httpRequest.getBody()));
	}

	public static void logMailProperties() {
		String prefix = NEW_LINE + "=========================  Mail Properties  =========================" + NEW_LINE;
		StringBuilder builder = new StringBuilder();
		builder.append(
				"To send mail fill the data in mail.properties available in src/main/resources, if not available refresh the folder. \nStill not visible create it in same folder and add below data to it");
		builder.append(NEW_LINE + MailProperty.SEND_MAIL.getValue() + "=true/false");
		builder.append(NEW_LINE + MailProperty.HOST.getValue() + "=gmail/outlook/office365");
		builder.append(NEW_LINE + MailProperty.FROM.getValue());
		builder.append(NEW_LINE + MailProperty.PASSWORD.getValue());
		builder.append(NEW_LINE + MailProperty.TO.getValue());
		builder.append(NEW_LINE + MailProperty.CC.getValue());
		builder.append(NEW_LINE + MailProperty.SUB.getValue());
		builder.append(NEW_LINE + MailProperty.TEXT.getValue());
		String mailProps = prefix + builder.toString() + SUFFIX;
		LOG.info(mailProps);
		Reporter.log(JavaUtil.convertToHtml(prefix) + JavaUtil.convertToHtml(builder.toString()) + SUFFIX);
	}

	public static void logMailRequest() {
		String prefix = NEW_LINE + "=========================  Mail Request  =========================" + NEW_LINE;
		StringBuilder builder = new StringBuilder();
		builder.append("Sending mail to below details..." + NEW_LINE);
		PropertyUtil.loadProperties(ResourceFile.MAIL_FILE);
		builder.append(String.format(FORMAT_TEXT, "Host", ":", PropertyUtil.get(MailProperty.HOST)));
		builder.append(String.format(FORMAT_TEXT, "Username", ":", PropertyUtil.get(MailProperty.FROM)));
		builder.append(String.format(FORMAT_TEXT, "To", ":", PropertyUtil.get(MailProperty.TO)));
		builder.append(String.format(FORMAT_TEXT, "Cc", ":", PropertyUtil.get(MailProperty.CC)));
		builder.append(String.format(FORMAT_TEXT, "Subject", ":",
				!PropertyUtil.get(MailProperty.SUB).isEmpty() ? PropertyUtil.get(MailProperty.SUB) : DefProperty.SUB));
		builder.append(String.format(FORMAT_TEXT, "Text", ":",
				!PropertyUtil.get(MailProperty.TEXT).isEmpty() ? PropertyUtil.get(MailProperty.TEXT)
						: DefProperty.TEXT));
		String mailProps = prefix + builder.toString() + SUFFIX;
		LOG.info(mailProps);
		Reporter.log(JavaUtil.convertToHtml(prefix) + JavaUtil.convertToHtml(builder.toString()) + SUFFIX);
	}

}

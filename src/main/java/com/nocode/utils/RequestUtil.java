/**
 * 
 */
package com.nocode.utils;

import java.util.Map;
import java.util.Map.Entry;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestUtil.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class RequestUtil {

	/**
	 * Gets the content type.
	 *
	 * @param headers the headers
	 * @return the content type
	 */
	public static String getContentType(Map<String, Object> headers) {
		String contentType = "";
		for (Entry<String, Object> entry : headers.entrySet()) {
			String key = entry.getKey().trim();
			if (key.equalsIgnoreCase("Content-Type")) {
				contentType = (String) entry.getValue();
			}
		}
		return contentType;
	}

	/**
	 * Gets the authorization.
	 *
	 * @param headers the headers
	 * @return the authorization
	 */
	public static String getAuthorization(Map<String, Object> headers) {
		String authorization = "";
		for (Entry<String, Object> entry : headers.entrySet()) {
			String key = entry.getKey().trim();
			if (key.equalsIgnoreCase("Authorization")) {
				authorization = (String) entry.getValue();
			}
		}
		return authorization;
	}
}

/**
 * 
 */
package com.nocode.response;

import java.util.HashMap;
import java.util.Map;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.nocode.exception.HttpException;
import com.nocode.model.StatusLine;
import com.nocode.utils.ILogger;

// TODO: Auto-generated Javadoc
/**
 * The Class HttpResponse.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public abstract class HttpResponse implements ILogger {

	/** The status code. */
	protected int statusCode;

	/** The status message. */
	protected String statusMessage;

	/** The status line. */
	protected StatusLine statusLine;

	/** The body. */
	protected Object body;

	/** The headers. */
	protected Map<String, Object> headers = new HashMap<String, Object>();

	/**
	 * Set status line.
	 */
	protected abstract void setStatusLine();

	/**
	 * Set response body.
	 */
	protected abstract void setBody();

	/**
	 * Set headers.
	 */
	protected abstract void setHeaders();

	/**
	 * Get status code of response.
	 *
	 * @return the status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Get status message of response.
	 *
	 * @return the status message
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * Get status line.
	 *
	 * @return the status line
	 */
	public StatusLine getStatusLine() {
		return statusLine;
	}

	/**
	 * Get response body.
	 *
	 * @return the body
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * Get all headers.
	 *
	 * @return the headers
	 */
	public Map<String, Object> getHeaders() {
		return headers;
	}

	/**
	 * Parse response body.
	 *
	 * @param jsonpath the jsonpath
	 * @return path of the value
	 */
	public String parse(String jsonpath) {
		String value = "";
		try {
			Object documentContext = Configuration.defaultConfiguration().jsonProvider().parse(getBody().toString());
			value = JsonPath.read(documentContext, jsonpath);
		} catch (PathNotFoundException e) {
			throw new HttpException("JsonPath [\"" + jsonpath + "\"] not found");
		}
		return value;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "HttpResponse [statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", statusLine="
				+ statusLine + ", body=" + body + ", headers=" + headers + "]";
	}

}

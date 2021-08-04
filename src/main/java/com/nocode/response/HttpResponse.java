/**
 * 
 */
package com.nocode.response;

import java.util.HashMap;
import java.util.Map;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.nocode.exception.HttpException;
import com.nocode.model.StatusLine;
import com.nocode.utils.ILogger;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
public abstract class HttpResponse implements ILogger {
	
	protected int statusCode;

	protected String statusMessage;

	protected StatusLine statusLine;

	protected Object body;
	
	protected Map<String, Object> headers = new HashMap<String, Object>();
	
	/**
	 * Set status line
	 */
	protected abstract void setStatusLine();

	/**
	 * Set response body
	 */
	protected abstract void setBody();

	/**
	 * Set headers
	 */
	protected abstract void setHeaders();

	
	/**
	 * Get status code of response
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Get status message of response
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * Get status line
	 */
	public StatusLine getStatusLine() {
		return statusLine;
	}

	/**
	 * Get response body
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * Get all headers
	 */
	public Map<String, Object> getHeaders() {
		return headers;
	}


	/**
	 * Parse response body.
	 * 
	 * @param jsonpath
	 * @return path of the value
	 */
	public String parse(String jsonpath) {
		String value = "";
		try {
			DocumentContext documentContext = JsonPath.parse(getBody().toString());
			value = documentContext.read(jsonpath).toString();
		} catch (PathNotFoundException e) {
			throw new HttpException("JsonPath [\"" + jsonpath + "\"] not found");
		}
		return value;
	}

	@Override
	public String toString() {
		return "HttpResponse [statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", statusLine="
				+ statusLine + ", body=" + body + ", headers=" + headers + "]";
	}
	
}

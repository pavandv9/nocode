package com.nocode.model;

import java.util.Map;

import org.springframework.lang.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Class Request.
 */
public class Request {

	/** The url. */
	@NonNull
	private String url;

	/** The method. */
	@NonNull
	private String method;

	/** The headers. */
	private Map<String, Object> headers;

	/** The queryparam. */
	private Map<String, Object> queryparam;

	/** The testdata. */
	private Object testdata;

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the method.
	 *
	 * @param method the new method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public Map<String, Object> getHeaders() {
		return headers;
	}

	/**
	 * Sets the headers.
	 *
	 * @param headers the headers
	 */
	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}

	/**
	 * Gets the queryparam.
	 *
	 * @return the queryparam
	 */
	public Map<String, Object> getQueryparam() {
		return queryparam;
	}

	/**
	 * Sets the queryparam.
	 *
	 * @param queryparam the queryparam
	 */
	public void setQueryparam(Map<String, Object> queryparam) {
		this.queryparam = queryparam;
	}

	/**
	 * Gets the testdata.
	 *
	 * @return the testdata
	 */
	public Object getTestdata() {
		return testdata;
	}

	/**
	 * Sets the testdata.
	 *
	 * @param testdata the new testdata
	 */
	public void setTestdata(Object testdata) {
		this.testdata = testdata;
	}

}

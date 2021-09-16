package com.nocode.model;

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class TestDataProvider.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class TestDataProvider {

	/** The url. */
	private Map<String, Object> url;
	
	/** The body. */
	private Object body;
	
	/** The statuscode. */
	private int statuscode;
	
	/** The resbody. */
	private Object resbody;

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public Map<String, Object> getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the url
	 */
	public void setUrl(Map<String, Object> url) {
		this.url = url;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param body the new body
	 */
	public void setBody(Object body) {
		this.body = body;
	}
	
	/**
	 * Gets the statuscode.
	 *
	 * @return the statuscode
	 */
	public int getStatuscode() {
		return statuscode;
	}

	/**
	 * Sets the statuscode.
	 *
	 * @param statuscode the new statuscode
	 */
	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	/**
	 * Gets the resbody.
	 *
	 * @return the resbody
	 */
	public Object getResbody() {
		return resbody;
	}

	/**
	 * Sets the resbody.
	 *
	 * @param resbody the new resbody
	 */
	public void setResbody(Object resbody) {
		this.resbody = resbody;
	}

}

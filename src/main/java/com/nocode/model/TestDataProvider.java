package com.nocode.model;

import java.util.Map;

/**
 * @author Pavan.DV
 *
 * @since 1.0.0
 */
public class TestDataProvider {

	private Map<String, Object> url;
	private Object body;
	private int statuscode;
	private Object resbody;

	public Map<String, Object> getUrl() {
		return url;
	}

	public void setUrl(Map<String, Object> url) {
		this.url = url;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
	
	public int getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	public Object getResbody() {
		return resbody;
	}

	public void setResbody(Object resbody) {
		this.resbody = resbody;
	}

}

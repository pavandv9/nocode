package com.nocode.model;

import java.util.Map;

public class TestDataProvider {

	private Map<String, Object> url;
	private Object body;
	private Map<String, Object> resbody;

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

	public Map<String, Object> getResbody() {
		return resbody;
	}

	public void setResbody(Map<String, Object> resbody) {
		this.resbody = resbody;
	}

}

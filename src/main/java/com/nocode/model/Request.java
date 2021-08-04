package com.nocode.model;

import java.util.Map;

import org.springframework.lang.NonNull;

public class Request {

	@NonNull
	private String url;

	@NonNull
	private String method;

	private Map<String, Object> headers;

	private Map<String, Object> queryparam;

	private String testdata;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}

	public Map<String, Object> getQueryparam() {
		return queryparam;
	}

	public void setQueryparam(Map<String, Object> queryparam) {
		this.queryparam = queryparam;
	}

	public String getTestdata() {
		return testdata;
	}

	public void setTestdata(String testdata) {
		this.testdata = testdata;
	}

}

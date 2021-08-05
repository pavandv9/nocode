package com.nocode.model;

import java.util.HashMap;
import java.util.Map;

public class ScenarioSteps {

	private String scenario;
	private String execute;
	private Request request;
	private Validate validate;

	public String getExecute() {
		return execute;
	}

	public void setExecute(String execute) {
		this.execute = execute;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Validate getValidate() {
		return validate;
	}

	public void setValidate(Validate validate) {
		this.validate = validate;
	}

	public ScenarioSteps() {
	}

	public ScenarioSteps(String sceanario, String execute, Request request, Validate validate) {
		this.scenario = sceanario;
		this.execute = execute;
		this.request = request;
		this.validate = validate;
	}

	public ScenarioSteps copy() {
		Request copyRequest = new Request();
		copyRequest.setUrl(this.getRequest().getUrl());
		copyRequest.setMethod(this.getRequest().getMethod());
		copyRequest.setTestdata(this.getRequest().getTestdata());
		if (null != this.getRequest().getHeaders()) {
			Map<String, Object> copyHeaderMap = new HashMap<>();
			this.getRequest().getHeaders().forEach(copyHeaderMap::put);
			copyRequest.setHeaders(copyHeaderMap);
		}
		if (null != this.getRequest().getQueryparam()) {
			Map<String, Object> copyQueryMap = new HashMap<>();
			this.getRequest().getQueryparam().forEach(copyQueryMap::put);
			copyRequest.setQueryparam(copyQueryMap);
		}
		Validate copyValidate = new Validate();
		copyValidate.setStatuscode(this.getValidate().getStatuscode());
		if (null != this.getValidate().getResbody()) {
			Map<String, Object> copyBody = new HashMap<>();
			this.getValidate().getResbody().forEach(copyBody::put);
			copyValidate.setResbody(copyBody);
		}
		return new ScenarioSteps(this.getScenario(), this.getExecute(), copyRequest, copyValidate);
	}

}

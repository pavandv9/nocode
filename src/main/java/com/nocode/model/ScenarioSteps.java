package com.nocode.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavan.DV
 *
 * @since 1.0.0
 */
public class ScenarioSteps {

	private String scenario;
	private String execute;
	private Request request;
	private Validate validate;
	private Steps[] steps;

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

	public Steps[] getSteps() {
		return steps;
	}

	public void setSteps(Steps[] steps) {
		this.steps = steps;
	}

	public ScenarioSteps() {
	}

	public ScenarioSteps(String sceanario, String execute, Request request, Validate validate, Steps[] steps) {
		this.scenario = sceanario;
		this.execute = execute;
		this.request = request;
		this.validate = validate;
		this.steps = steps;
	}

	public ScenarioSteps copy() {
		if (null != this.getRequest() && null != this.getValidate())
			return copyRequestValidateObject();
		else
			return copyStepsObject();
	}

	@SuppressWarnings("unchecked")
	private ScenarioSteps copyRequestValidateObject() {
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
			if (this.getValidate().getResbody() instanceof Map) {
				Map<String, Object> copyResBody = (Map<String, Object>) this.getValidate().getResbody();
				Map<String, Object> copyBody = new HashMap<>();
				copyResBody.forEach(copyBody::put);
				copyValidate.setResbody(copyBody);
			} else
				copyValidate.setResbody(this.getValidate().getResbody());
		}
		return new ScenarioSteps(this.getScenario(), this.getExecute(), copyRequest, copyValidate, this.getSteps());

	}

	@SuppressWarnings("unchecked")
	private ScenarioSteps copyStepsObject() {
		Steps[] copySteps = new Steps[steps.length];
		for (int i = 0; i < steps.length; i++) {
			Request copyRequest = new Request();
			copyRequest.setUrl(this.getSteps()[i].getRequest().getUrl());
			copyRequest.setMethod(this.getSteps()[i].getRequest().getMethod());
			copyRequest.setTestdata(this.getSteps()[i].getRequest().getTestdata());
			if (null != this.getSteps()[i].getRequest().getHeaders()) {
				Map<String, Object> copyHeaderMap = new HashMap<>();
				this.getSteps()[i].getRequest().getHeaders().forEach(copyHeaderMap::put);
				copyRequest.setHeaders(copyHeaderMap);
			}
			if (null != this.getSteps()[i].getRequest().getQueryparam()) {
				Map<String, Object> copyQueryMap = new HashMap<>();
				this.getSteps()[i].getRequest().getQueryparam().forEach(copyQueryMap::put);
				copyRequest.setQueryparam(copyQueryMap);
			}
			Validate copyValidate = new Validate();
			copyValidate.setStatuscode(this.getSteps()[i].getValidate().getStatuscode());
			if (null != this.getSteps()[i].getValidate().getResbody()) {
				if (this.getSteps()[i].getValidate().getResbody() instanceof Map) {
					Map<String, Object> copyResBody = (Map<String, Object>) this.getSteps()[i].getValidate().getResbody();
					Map<String, Object> copyBody = new HashMap<>();
					copyResBody.forEach(copyBody::put);
					copyValidate.setResbody(copyBody);
				} else
					copyValidate.setResbody(this.getSteps()[i].getValidate().getResbody());
			}
			copySteps[i] = new Steps(this.getSteps()[i].getStep(), copyRequest, copyValidate);
		}
		return new ScenarioSteps(this.getScenario(), this.getExecute(), this.getRequest(), this.getValidate(), copySteps);

	}

}

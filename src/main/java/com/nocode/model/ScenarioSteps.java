package com.nocode.model;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenarioSteps.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class ScenarioSteps {

	/** The scenario. */
	private String scenario;
	
	/** The execute. */
	private String execute;
	
	/** The request. */
	private Request request;
	
	/** The validate. */
	private Validate validate;
	
	/** The steps. */
	private Steps[] steps;

	/**
	 * Gets the execute.
	 *
	 * @return the execute
	 */
	public String getExecute() {
		return execute;
	}

	/**
	 * Sets the execute.
	 *
	 * @param execute the new execute
	 */
	public void setExecute(String execute) {
		this.execute = execute;
	}

	/**
	 * Gets the scenario.
	 *
	 * @return the scenario
	 */
	public String getScenario() {
		return scenario;
	}

	/**
	 * Sets the scenario.
	 *
	 * @param scenario the new scenario
	 */
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * Sets the request.
	 *
	 * @param request the new request
	 */
	public void setRequest(Request request) {
		this.request = request;
	}

	/**
	 * Gets the validate.
	 *
	 * @return the validate
	 */
	public Validate getValidate() {
		return validate;
	}

	/**
	 * Sets the validate.
	 *
	 * @param validate the new validate
	 */
	public void setValidate(Validate validate) {
		this.validate = validate;
	}

	/**
	 * Gets the steps.
	 *
	 * @return the steps
	 */
	public Steps[] getSteps() {
		return steps;
	}

	/**
	 * Sets the steps.
	 *
	 * @param steps the new steps
	 */
	public void setSteps(Steps[] steps) {
		this.steps = steps;
	}

	/**
	 * Instantiates a new scenario steps.
	 */
	public ScenarioSteps() {
	}

	/**
	 * Instantiates a new scenario steps.
	 *
	 * @param sceanario the sceanario
	 * @param execute the execute
	 * @param request the request
	 * @param validate the validate
	 * @param steps the steps
	 */
	public ScenarioSteps(String sceanario, String execute, Request request, Validate validate, Steps[] steps) {
		this.scenario = sceanario;
		this.execute = execute;
		this.request = request;
		this.validate = validate;
		this.steps = steps;
	}

	/**
	 * Copy.
	 *
	 * @return the scenario steps
	 */
	public ScenarioSteps copy() {
		if (null != this.getRequest() && null != this.getValidate())
			return copyRequestValidateObject();
		else
			return copyStepsObject();
	}

	/**
	 * Copy request validate object.
	 *
	 * @return the scenario steps
	 */
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

	/**
	 * Copy steps object.
	 *
	 * @return the scenario steps
	 */
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

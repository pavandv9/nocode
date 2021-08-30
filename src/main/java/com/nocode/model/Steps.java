package com.nocode.model;

/**
 * @author Pavan.DV
 *
 * @since 2.0.0
 */
public class Steps {

	private String step;
	private Request request;
	private Validate validate;

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

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
	
	public Steps(){}
	
	public Steps(String step, Request request, Validate validate) {
		this.step = step;
		this.request = request;
		this.validate = validate;
	}
}

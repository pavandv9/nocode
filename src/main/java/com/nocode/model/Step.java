package com.nocode.model;

/**
 * @author Pavan.DV
 *
 * @since 2.0.0
 */
public class Step {

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

}

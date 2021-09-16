package com.nocode.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Steps.
 *
 * @author Pavan.DV
 * @since 2.0.0
 */
public class Steps {

	/** The step. */
	private String step;
	
	/** The request. */
	private Request request;
	
	/** The validate. */
	private Validate validate;

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
	 * Gets the step.
	 *
	 * @return the step
	 */
	public String getStep() {
		return step;
	}

	/**
	 * Sets the step.
	 *
	 * @param step the new step
	 */
	public void setStep(String step) {
		this.step = step;
	}
	
	/**
	 * Instantiates a new steps.
	 */
	public Steps(){}
	
	/**
	 * Instantiates a new steps.
	 *
	 * @param step the step
	 * @param request the request
	 * @param validate the validate
	 */
	public Steps(String step, Request request, Validate validate) {
		this.step = step;
		this.request = request;
		this.validate = validate;
	}
}

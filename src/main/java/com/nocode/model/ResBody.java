package com.nocode.model;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class ResBody.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class ResBody {

	/** The optional parameters. */
	private Map<String, Object> optionalParameters = new HashMap<>();

	/**
	 * Gets the optional parameters.
	 *
	 * @return the optional parameters
	 */
	public Map<String, Object> getOptionalParameters() {
		return optionalParameters;
	}

	/**
	 * Sets the optional parameters.
	 *
	 * @param optionalParameters the optional parameters
	 */
	public void setOptionalParameters(Map<String, Object> optionalParameters) {
		this.optionalParameters = optionalParameters;
	}

}

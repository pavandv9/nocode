package com.nocode.model;

import java.util.HashMap;
import java.util.Map;

public class ResBody {

	private Map<String, Object> optionalParameters = new HashMap<>();

	public Map<String, Object> getOptionalParameters() {
		return optionalParameters;
	}

	public void setOptionalParameters(Map<String, Object> optionalParameters) {
		this.optionalParameters = optionalParameters;
	}

}

package com.nocode.model;

import java.util.Map;

public class Validate {

	private int statuscode;
	
	private Map<String, Object> resbody;

	public int getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	public Map<String, Object> getResbody() {
		return resbody;
	}

	public void setResbody(Map<String, Object> resbody) {
		this.resbody = resbody;
	}
}

package com.nocode.model;

import java.util.List;

public class TestData {

	private String scenarioid;
	private Object body;
	private List<TestDataProvider> dataprovider;

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public String getScenarioid() {
		return scenarioid;
	}

	public void setScenarioid(String scenarioid) {
		this.scenarioid = scenarioid;
	}

	public List<TestDataProvider> getDataprovider() {
		return dataprovider;
	}

	public void setDataprovider(List<TestDataProvider> dataprovider) {
		this.dataprovider = dataprovider;
	}

}

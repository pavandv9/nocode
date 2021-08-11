package com.nocode.model;

import java.util.List;

/**
 * @author Pavan.DV
 *
 * @since 1.0.0
 */
public class TestData {

	private Object body;
	private List<TestDataProvider> dataprovider;

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public List<TestDataProvider> getDataprovider() {
		return dataprovider;
	}

	public void setDataprovider(List<TestDataProvider> dataprovider) {
		this.dataprovider = dataprovider;
	}

}

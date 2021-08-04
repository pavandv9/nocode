package com.nocode.utils;

import static org.testng.Assert.assertEquals;

import java.util.Map.Entry;

import com.nocode.constants.AssertMessage;
import com.nocode.model.Validate;
import com.nocode.response.HttpResponse;

public class ResponseValidator {

	private Validate expected;
	private HttpResponse httpResponse;

	public ResponseValidator(Validate expectedValidator,  HttpResponse httpResponse) {
		this.expected = expectedValidator;
		this.httpResponse = httpResponse;
		validateStatusCode();
		validateResponseBody();
	}

	private void validateResponseBody() {
		String actualJsonBody = httpResponse.getBody().toString();
		for (Entry<String, Object> entrySet : expected.getResbody().entrySet()) {
			String actualValue = JsonUtil.parse(actualJsonBody, entrySet.getKey());
			assertEquals(actualValue, entrySet.getValue(), String.format("%s, %s",AssertMessage.RESPONSE_BODY_FAILED, entrySet.getKey()));
		}
	}

	private void validateStatusCode() {
		assertEquals(httpResponse.getStatusCode(), expected.getStatuscode(), AssertMessage.STATUS_CODE_FAILED);
	}
}

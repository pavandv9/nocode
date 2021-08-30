package com.nocode.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.nocode.constants.AssertMessage;
import com.nocode.constants.ValidatorConstants;
import com.nocode.model.Validate;
import com.nocode.response.HttpResponse;

/**
 * @author Pavan.DV
 *
 * @since 1.0.0
 */
public class ResponseValidator {

	private Validate expected;
	private HttpResponse httpResponse;

	public ResponseValidator(Validate expectedValidator, HttpResponse httpResponse) {
		this.expected = expectedValidator;
		this.httpResponse = httpResponse;
		validateStatusCode();
		validateResponseBody();
	}

	private void validateResponseBody() {
		String actualResBody = httpResponse.getBody().toString();
		if (null != expected.getResbody()) {
			Object resbody = expected.getResbody();
			if (resbody instanceof Map)
				validateFieldsInRespBody(actualResBody, resbody);
			else
				validateNE(actualResBody, resbody.toString());
		}
	}

	private void validateStatusCode() {
		if (expected.getStatuscode() != 0)
			assertEquals(httpResponse.getStatusCode(), expected.getStatuscode(), AssertMessage.STATUS_CODE_FAILED);
	}

	@SuppressWarnings("unchecked")
	private void validateFieldsInRespBody(String actualResBody, Object expectedResBody) {
		Map<String, Object> resMapBody = (Map<String, Object>) expectedResBody;
		for (Entry<String, Object> entrySet : resMapBody.entrySet()) {
			String actualValue = JsonUtil.parse(actualResBody, entrySet.getKey());
			try {
				String expctedResBody = entrySet.getValue().toString();
				ValidatorConstants.valueOf(expctedResBody);
				validateNE(actualValue, expctedResBody);
			} catch (IllegalArgumentException e) {
				if (e.getLocalizedMessage().startsWith("No enum constant com.nocode.constants.ValidatorConstants"))
					assertEquals(actualValue, entrySet.getValue(),
							String.format("%s, %s", AssertMessage.RESPONSE_BODY_FAILED, entrySet.getKey()));
				else
					assertFalse(true, e.getLocalizedMessage());
			}
		}

	}

	private void validateNE(String actualResBody, String expectedResBody) {
		switch (ValidatorConstants.valueOf(expectedResBody)) {
		case NULL:
			assertNull(actualResBody, AssertMessage.RESPONSE_BODY_FAILED);
			break;
		case NOT_NULL:
			assertNotNull(actualResBody, AssertMessage.RESPONSE_BODY_FAILED);
			break;
		case EMPTY:
			assertTrue(new JSONObject(actualResBody).isEmpty(), AssertMessage.RESPONSE_BODY_EMPTY_FAILED);
			break;
		case NOT_EMPTY:
			assertTrue(!new JSONObject(actualResBody).isEmpty(), AssertMessage.RESPONSE_BODY_NOT_EMPTY_FAILED);
			break;
		default:
			break;
		}
	}
}

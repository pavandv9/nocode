/**
 * 
 */
package com.nocode.client;

import java.util.Map;
import java.util.Map.Entry;

import com.nocode.response.HttpResponse;
import com.nocode.utils.JavaUtil;

/**
 * @author Pavan.DV
 *
 * @since 2.0.0
 */
public class MultiStepClient {

	String getUrl(String url, Map<String, HttpResponse> stepWithHttpResponse) {
		if (null != url && !stepWithHttpResponse.isEmpty() && url.contains("{")) {
			String urlWithUnkownParam = JavaUtil.retrieveUnknownParams(url).stream().findFirst().get();
			int iend = urlWithUnkownParam.indexOf(".");
			if (iend != -1) {
				if (urlWithUnkownParam.substring(++iend).startsWith("resbody")) {
					String step = urlWithUnkownParam.substring(0, --iend);
					String resbodyPath = urlWithUnkownParam.substring(++iend);
					int iPath = resbodyPath.indexOf(".");
					String value = stepWithHttpResponse.get(step).parse(resbodyPath.substring(++iPath));
					url = url.replace("{" + urlWithUnkownParam + "}", value);
				}
			}
		}
		return url;
	}

	Map<String, Object> getHeaders(Map<String, Object> headers, Map<String, HttpResponse> stepWithHttpResponse) {
		if (null != headers && !stepWithHttpResponse.isEmpty()) {
			for (Entry<String, Object> header : headers.entrySet()) {
				String headerValue = header.getValue().toString();
				if (headerValue.contains("{")) {
					String urlWithUnkownParam = JavaUtil.retrieveUnknownParams(headerValue).stream().findFirst().get();
					int iend = urlWithUnkownParam.indexOf(".");
					if (iend != -1) {
						if (urlWithUnkownParam.substring(++iend).startsWith("resbody")) {
							String step = urlWithUnkownParam.substring(0, --iend);
							String resbodyPath = urlWithUnkownParam.substring(++iend);
							int iPath = resbodyPath.indexOf(".");
							System.out.println("");
							String value = stepWithHttpResponse.get(step).parse(resbodyPath.substring(++iPath));
							headers.put(header.getKey(), value);
						}
					}
				}
			}
		}
		return headers;
	}

	Map<String, Object> getQueryParams(Map<String, Object> queryParams,
			Map<String, HttpResponse> stepWithHttpResponse) {
		if (null != queryParams && !stepWithHttpResponse.isEmpty()) {
			for (Entry<String, Object> param : queryParams.entrySet()) {
				String paramValue = param.getValue().toString();
				if (paramValue.contains("{")) {
					String urlWithUnkownParam = JavaUtil.retrieveUnknownParams(paramValue).stream().findFirst().get();
					int iend = urlWithUnkownParam.indexOf(".");
					if (iend != -1) {
						if (urlWithUnkownParam.substring(++iend).startsWith("resbody")) {
							String step = urlWithUnkownParam.substring(0, --iend);
							String resbodyPath = urlWithUnkownParam.substring(++iend);
							int iPath = resbodyPath.indexOf(".");
							String value = stepWithHttpResponse.get(step).parse(resbodyPath.substring(++iPath));
							queryParams.put(param.getKey(), value);
						}
					}
				}
			}
		}
		return queryParams;
	}

	Object getRequestBody(Object body, Map<String, HttpResponse> stepWithHttpResponse) {
		if (null != body && !stepWithHttpResponse.isEmpty()) {
			Map<String, Object> mapBody = JavaUtil.getMapFromObject(body);
			for (Entry<String, Object> param : mapBody.entrySet()) {
				String paramValue = param.getValue().toString();
				if (paramValue.contains("{")) {
					String urlWithUnkownParam = JavaUtil.retrieveUnknownParams(paramValue).stream().findFirst().get();
					int iend = urlWithUnkownParam.indexOf(".");
					if (iend != -1) {
						if (urlWithUnkownParam.substring(++iend).startsWith("resbody")) {
							String step = urlWithUnkownParam.substring(0, --iend);
							String resbodyPath = urlWithUnkownParam.substring(++iend);
							int iPath = resbodyPath.indexOf(".");
							String value = stepWithHttpResponse.get(step).parse(resbodyPath.substring(++iPath));
							mapBody.put(param.getKey(), value);
						}
					}
				}
			}
		}
		return body;
	}
}

package com.nocode.client;

import com.nocode.response.HttpResponse;

import lombok.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Interface Request.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public interface Request {

	/**
	 * Execute's the given request.
	 *
	 * @param httpRequest the http request
	 * @return HttpResonse
	 */
	static HttpResponse execute(@NonNull HttpRequest httpRequest) {
		return new HttpConsumer().execute(httpRequest);
	}
}

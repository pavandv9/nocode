/**
 * 
 */
package com.nocode.client;

import com.nocode.response.HttpResponse;

import lombok.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Interface HttpClient.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public interface HttpClient {

	/**
	 * Execute.
	 *
	 * @param httpRequest the http request
	 * @return the http response
	 */
	public HttpResponse execute(@NonNull HttpRequest httpRequest);

	/**
	 * Gets the http request.
	 *
	 * @return the http request
	 */
	public HttpRequest getHttpRequest();

}

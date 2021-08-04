/**
 * 
 */
package com.nocode.client;

import com.nocode.response.HttpResponse;

import lombok.NonNull;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
public interface HttpClient {

	public HttpResponse execute(@NonNull HttpRequest httpRequest);

	public HttpRequest getHttpRequest();

}

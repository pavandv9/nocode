/**
 * 
 */
package com.nocode.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.springframework.lang.NonNull;

import com.nocode.constants.HttpMethod;
import com.nocode.utils.RequestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class HttpRequest.
 *
 * @author Pavan.DV
 * 
 *         <p>
 *         <b>apiNote</b> HttpRequest which forms the request. Pass HttpRequest
 *         object to the execute method which is available in getHttpClient of
 *         ServiceHelper interface.
 * @since 1.0.0
 */
public class HttpRequest {

	/** The body. */
	private Object body;

	/** The base url. */
	private String baseUrl;

	/** The end point. */
	private String endPoint;

	/** The http method. */
	private HttpMethod httpMethod;

	/** The headers. */
	private Map<String, Object> headers = new HashMap<>();

	/** The path params. */
	private Map<String, Object> pathParams = new HashMap<>();

	/** The query params. */
	private Map<String, Object> queryParams = new HashMap<>();

	/**
	 * Get request body.
	 *
	 * @return the body
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * Get request base url.
	 *
	 * @return the base url
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Get request endpoint.
	 *
	 * @return the end point
	 */
	public String getEndPoint() {
		return endPoint;
	}

	/**
	 * Get request httpMethod.
	 *
	 * @return the http method
	 */
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	/**
	 * Get request headers.
	 *
	 * @return the headers
	 */
	public Map<String, Object> getHeaders() {
		return headers;
	}

	/**
	 * Get request path parameters.
	 *
	 * @return the path params
	 */
	public Map<String, Object> getPathParams() {
		return pathParams;
	}

	/**
	 * Get request query parameters.
	 *
	 * @return the query params
	 */
	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	/**
	 * Add base url to the HttpRequest.
	 *
	 * @param url the url
	 * @return this
	 */
	public HttpRequest addBaseUrl(@NonNull String url) {
		if (!url.isEmpty() ) {
			baseUrl = url;
		}
		return this;
	}

	/**
	 * Add base url to the HttpRequest.
	 *
	 * @param url the url
	 * @return this
	 */
	public HttpRequest addBaseUrl(@NonNull Object url) {
		if (!url.toString().isEmpty()) {
			baseUrl = url.toString();
		}
		return this;
	}

	/**
	 * Add path parameters to the HttpRequest.
	 *
	 * @param pathParams the path params
	 * @return this
	 */
	public HttpRequest addPathParamValues(@NonNull Map<String, Object> pathParams) {
		if (!pathParams.isEmpty()) {
			pathParams.forEach((name, value) -> this.pathParams.put(name, value));
		}
		return this;
	}

	/**
	 * Add path parameters to the HttpRequest.
	 * 
	 * <p>
	 * <b>Description</b> Add path parameter name given in end point enclosed by {}.
	 * 
	 * @param name  with or without {}
	 * @param value of path parameter
	 *              <p>
	 *              <b>example</b> addPathParamValue("{pathParamName}",
	 *              pathParamValue) Or <br>
	 *              addPathParamValue("pathParamName", pathParamValue)
	 * @return this
	 */
	public HttpRequest addPathParamValue(@NonNull String name, Object value) {
		this.pathParams.put(name, value);
		return this;
	}

	/**
	 * Add query parameters.
	 *
	 * @param queryParams to the HttpRequest
	 * @return this
	 */
	public HttpRequest addQueryParams(Map<String, Object> queryParams) {
		if (queryParams!=null && !queryParams.isEmpty()) {
			queryParams.forEach((name, value) -> this.queryParams.put(name, value));
		}
		return this;
	}

	/**
	 * Add query parameters to the HttpRequest.
	 *
	 * @param name  of the query parameter
	 * @param value of the query parameter
	 * @return this
	 */
	public HttpRequest addQueryParam(@NonNull String name, Object value) {
		this.queryParams.put(name, value);
		return this;
	}

	/**
	 * Add body to the HttpRequest.
	 *
	 * @param body the body
	 * @return this
	 */
	public HttpRequest addBody(Object body) {
		this.body = body;
		return this;
	}

	/**
	 * Add HttpMethod to the HttpRequest.
	 *
	 * @param httpMethod the http method
	 * @return this
	 */
	public HttpRequest addMethod(@NonNull HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
		return this;
	}

	/**
	 * Add end point to the HttpRequest. <br>
	 * Add path parameters to end point if api request has it, by enclosing in
	 * flower braces {pathParam}
	 *
	 * @param endpoint the endpoint
	 * @return this
	 *         <p>
	 *         <b>example</b> https://example.com/demo/{pathName}
	 */
	public HttpRequest addEndPoint(@NonNull String endpoint) {
		this.endPoint = endpoint;
		return this;
	}

	/**
	 * Add headers to the HttpRequest.
	 *
	 * @param headers the headers
	 * @return this
	 */
	public HttpRequest addHeader(Map<String, Object> headers) {
		if (headers!=null && !headers.isEmpty()) {
			headers.forEach((name, value) -> this.headers.put(name, value));
		}
		return this;
	}

	/**
	 * Add header to the HttpRequest.
	 *
	 * @param name the name
	 * @param value the value
	 * @return this
	 */
	public HttpRequest addHeader(@NonNull String name, Object value) {
		this.headers.put(name, value);
		addHeader(headers);
		return this;
	}

	/**
	 * Add header to the HttpRequest.
	 *
	 * @param name the name
	 * @param value the value
	 * @return this
	 */
	public HttpRequest addHeader(@NonNull HttpHeaders name, Object value) {
		this.headers.put(name.toString(), value);
		addHeader(headers);
		return this;
	}

	/**
	 * Adds the content type.
	 *
	 * @param value the value
	 * @return the http request
	 */
	public HttpRequest addContentType(@NonNull String value) {
		addHeader("Content-Type", value);
		return this;
	}

	/**
	 * Adds the authorization.
	 *
	 * @param value the value
	 * @return the http request
	 */
	public HttpRequest addAuthorization(@NonNull String value) {
		addHeader("Authorization", value);
		return this;
	}

	/**
	 * Get content type.
	 *
	 * @return the content type
	 */
	public String getContentType() {
		return RequestUtil.getContentType(headers);
	}

	/**
	 * Get authorization.
	 *
	 * @return the authorization
	 */
	public String getAuthorization() {
		return RequestUtil.getAuthorization(headers);
	}

}

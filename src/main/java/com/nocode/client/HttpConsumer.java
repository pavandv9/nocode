
/**
 * 
 */
package com.nocode.client;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import com.nocode.constants.ConfigProperty;
import com.nocode.constants.HttpMethod;
import com.nocode.constants.IHeaders;
import com.nocode.exception.HttpException;
import com.nocode.response.Response;
import com.nocode.response.HttpResponse;
import com.nocode.utils.ILogger;
import com.nocode.utils.JavaUtil;
import com.nocode.utils.ReportLogger;
import com.nocode.utils.PropertyUtil;

import lombok.NonNull;

/**
 * @author Pavan.DV
 * 
 * @since 1.0.0
 *
 */
public class HttpConsumer implements HttpClient, ILogger, IHeaders {

	private HttpRequest httpRequest;

	static {
		clearFiles();
	}

	/**
	 * Process HttpRequest
	 * 
	 * @param httpRequest
	 * @return
	 */
	private HttpResponse processRequest() {
		loadConfigFileAndValidateRequest();
		ReportLogger.logRequest(httpRequest);
		CloseableHttpResponse closeableHttpResponse = null;
		HttpResponse httpResponse = null;
		try {
			closeableHttpResponse = getDefaultClient().execute(getHttpUriRequest(httpRequest.getHttpMethod()));
			httpResponse = new Response(closeableHttpResponse);
			ReportLogger.logResponse(httpResponse);
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return httpResponse;
	}

	@Override
	public HttpRequest getHttpRequest() {
		return httpRequest;
	}

	@Override
	public HttpResponse execute(@NonNull HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
		return processRequest();
	}

	/**
	 * Get URI by building url, path parameters and query parameters.
	 * 
	 * @return
	 */
	private URI uri() {
		URI uri = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(buildPathParams());
			for (Entry<String, Object> entrySet : httpRequest.getQueryParams().entrySet()) {
				uriBuilder.setParameter(entrySet.getKey(), entrySet.getValue().toString());
			}
			uri = uriBuilder.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

	/**
	 * Load config file and vlidate request.
	 */
	private void loadConfigFileAndValidateRequest() {
		loadConfig();
		if (httpRequest.getHttpMethod() == null)
			throw new HttpException("HttpMethod not found in the request");
	}

	/**
	 * Build path parameter to url
	 * 
	 * @return
	 */
	private String buildPathParams() {
		String endPointWithPathPrams = httpRequest.getEndPoint();
		String url = httpRequest.getBaseUrl();
		if (endPointWithPathPrams != null) {
			for (Entry<String, Object> pathParam : httpRequest.getPathParams().entrySet()) {
				String key = pathParam.getKey().replace("{", "").replace("}", "");
				endPointWithPathPrams = endPointWithPathPrams.replace("{" + key + "}", pathParam.getValue().toString());
			}
			url = String.format("%1$s%2$s", httpRequest.getBaseUrl(), endPointWithPathPrams);
		}
		return url;
	}

	/**
	 * Set default headers and get CloseableHttpClient.
	 * 
	 * @return CloseableHttpClient
	 */
	private CloseableHttpClient getDefaultClient() {
		CloseableHttpClient client = HttpClients.createDefault();
		HashSet<Header> defaultHeaders = new HashSet<>();
		defaultHeaders.add(new BasicHeader(HttpHeaders.PRAGMA, "no-cache"));
		defaultHeaders.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "no-cache"));
		defaultHeaders.add(new BasicHeader(ACCEPT, APPLICATION_JSON));
		defaultHeaders.add(new BasicHeader(CONTENT_TYPE, APPLICATION_JSON));
		HttpClientBuilder clientBuilder = HttpClients.custom().setDefaultHeaders(defaultHeaders).disableAuthCaching()
				.disableContentCompression();
		clientBuilder.build();
		return client;
	}

	/**
	 * Set headers to the request.
	 * 
	 * @param httpUriRequest
	 */
	private void setHeaders(HttpUriRequest httpUriRequest) {
		for (Entry<String, Object> entry : httpRequest.getHeaders().entrySet())
			httpUriRequest.setHeader(entry.getKey(), entry.getValue().toString());
	}

	private HttpUriRequest getHttpUriRequest(HttpMethod method) {
		HttpUriRequest httpUriRequest = null;
		switch (method) {
		case GET:
			HttpGet httpGet = new HttpGet();
			httpGet.setURI(uri());
			setHeaders(httpGet);
			httpUriRequest = httpGet;
			break;
		case POST:
			HttpPost httpPost = new HttpPost();
			httpPost.setURI(uri());
			httpPost.setEntity(getHttpEntityBody());
			setHeaders(httpPost);
			httpUriRequest = httpPost;
			break;
		case PUT:
			HttpPut httpPut = new HttpPut();
			httpPut.setURI(uri());
			setHeaders(httpPut);
			httpPut.setEntity(getHttpEntityBody());
			httpUriRequest = httpPut;
			break;
		case PATCH:
			HttpPatch httpPatch = new HttpPatch();
			httpPatch.setURI(uri());
			setHeaders(httpPatch);
			httpPatch.setEntity(getHttpEntityBody());
			httpUriRequest = httpPatch;
			break;
		case DELETE:
			HttpDelete httpDelete = new HttpDelete();
			httpDelete.setURI(uri());
			setHeaders(httpDelete);
			httpUriRequest = httpDelete;
			break;
		case OPTIONS:
			HttpOptions httpOptions = new HttpOptions();
			httpOptions.setURI(uri());
			setHeaders(httpOptions);
			httpUriRequest = httpOptions;
			break;
		case HEAD:
			HttpHead httpHead = new HttpHead();
			httpHead.setURI(uri());
			setHeaders(httpHead);
			httpUriRequest = httpHead;
			break;
		default:
			break;
		}
		return httpUriRequest;
	}

	private void loadConfig() {
		loadConfigProperties();
		formatUrlAndEndPoint();
		loadHeaders();
	}

	private void loadConfigProperties() {
		String env = "";
		try {
			env = ConfigManager.get(ConfigProperty.ENV);
		} catch (ExceptionInInitializerError e) {
			LOG.info("config.properties not found, use it for better performance");
		} catch (NoClassDefFoundError e) {
			LOG.debug("Failed to initate static block of ConfigManager");
		}
		try {
			PropertyUtil
					.loadProperties(String.format("%1$s%2$s%3$s", "src/main/resources/env-file/", env, ".properties"));
		} catch (HttpException e) {
			LOG.info(env + ".properties not found, use it for better performance");
		}
		String baseUrl = httpRequest.getBaseUrl();
		String authorization = httpRequest.getAuthorization();
		if (baseUrl == null || baseUrl.isEmpty()) {
			try {
				baseUrl = PropertyUtil.get(ConfigProperty.BASE_URL);
				httpRequest.addBaseUrl(baseUrl);
			} catch (ExceptionInInitializerError e) {
			}
		}
		if (null == baseUrl || baseUrl.isEmpty())
			throw new HttpException("base_url is not set");
		if (baseUrl.contains("{") || baseUrl.contains("}"))
			throw new HttpException("base_url is not valid");
		if (authorization == null || authorization.isEmpty()) {
			try {
				authorization = PropertyUtil.get(ConfigProperty.AUTHORIZATION);
				if (authorization != null && !authorization.isEmpty())
					httpRequest.addAuthorization(authorization);
			} catch (ExceptionInInitializerError | NullPointerException e) {
				// Do nothing
			}
		}
	}

	private void formatUrlAndEndPoint() {
		if (httpRequest.getEndPoint() == null || httpRequest.getEndPoint().isEmpty()) {
			httpRequest.addBaseUrl(httpRequest.getBaseUrl());
		} else if (httpRequest.getBaseUrl().endsWith("/") && httpRequest.getEndPoint().startsWith("/")) {
			httpRequest.addBaseUrl(httpRequest.getBaseUrl());
			httpRequest.addEndPoint(httpRequest.getEndPoint().substring(1));
		} else if (httpRequest.getBaseUrl().endsWith("/") && !httpRequest.getEndPoint().startsWith("/")) {
			httpRequest.addBaseUrl(httpRequest.getBaseUrl());
			httpRequest.addEndPoint(httpRequest.getEndPoint());
		} else if (!httpRequest.getBaseUrl().endsWith("/") && httpRequest.getEndPoint().startsWith("/")) {
			httpRequest.addBaseUrl(httpRequest.getBaseUrl() + "/");
			httpRequest.addEndPoint(httpRequest.getEndPoint().substring(1));
		} else {
			httpRequest.addBaseUrl(httpRequest.getBaseUrl() + "/");
			httpRequest.addEndPoint(httpRequest.getEndPoint());
		}
	}

	private void loadHeaders() {
		if (httpRequest.getHeaders().isEmpty()) {
			httpRequest.addHeader(CONTENT_TYPE, APPLICATION_JSON);
			httpRequest.addHeader(ACCEPT, APPLICATION_JSON);
		}
		if (httpRequest.getContentType() == null || httpRequest.getContentType().isEmpty()) {
			httpRequest.addHeader(CONTENT_TYPE, APPLICATION_JSON);
			httpRequest.addHeader(ACCEPT, APPLICATION_JSON);
		}
	}

	private HttpEntity getHttpEntityBody() {
		HttpEntity entity = null;
		try {
			entity = new StringEntity(JavaUtil.toJson(httpRequest.getBody()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return entity;
	}

	private static void clearFiles() {
		clearTestResultFiles();
	}

	private static void clearTestResultFiles() {
		try {
			File[] files = new File("test-result").listFiles();
			Arrays.sort(files);
			if (files.length >= 10) {
				Arrays.stream(new File(files[0].getAbsolutePath()).listFiles()).forEach(File::delete);
				files[0].delete();
				LOG.debug("1 file cleared from test-result repository.");
			}
		} catch (Exception e) {
			// Do nothing
		}
	}
}

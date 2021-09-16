/**
 * 
 */
package com.nocode.response;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.nocode.exception.HttpException;
import com.nocode.model.StatusLine;

// TODO: Auto-generated Javadoc
/**
 * The Class Response.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public final class Response extends HttpResponse {

	/** The response. */
	CloseableHttpResponse response;

	/**
	 * Instantiates a new response.
	 *
	 * @param closeableHttpResponse the closeable http response
	 */
	public Response(CloseableHttpResponse closeableHttpResponse) {
		super();
		this.response = closeableHttpResponse;
		setHeaders();
		setStatusLine();
		setBody();
	}

	/**
	 * Set status line from response.
	 */
	@Override
	protected void setStatusLine() {
		this.statusLine = new StatusLine(this.response.getStatusLine().getStatusCode(),
				this.response.getStatusLine().getReasonPhrase());
		this.statusCode = response.getStatusLine().getStatusCode();
		this.statusMessage = response.getStatusLine().getReasonPhrase();
	}

	/**
	 * Set body from response.
	 */
	@Override
	protected void setBody() {
		try {
			this.body = EntityUtils.toString(response.getEntity());
		} catch (ParseException | IOException e) {
			throw new HttpException(e.getMessage());
		}
	}

	/**
	 * Set headers from response.
	 */
	@Override
	protected void setHeaders() {
		for (Header header : response.getAllHeaders())
			this.headers.put(header.getName(), header.getValue());
	}
}

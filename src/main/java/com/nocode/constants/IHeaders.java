/**
 * 
 */
package com.nocode.constants;

import org.apache.http.entity.ContentType;

/**
 * @author Pavan.DV
 * @since 1.0.0
 *
 */
public interface IHeaders {

	public final String CONTENT_TYPE = "Content-Type";
	public final String ACCEPT = "Accept";
	public final String APPLICATION_JSON = ContentType.APPLICATION_JSON.toString();
	public final String APPLICATION_XML = ContentType.APPLICATION_XML.toString();

}

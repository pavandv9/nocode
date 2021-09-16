/**
 * 
 */
package com.nocode.constants;

import org.apache.http.entity.ContentType;

// TODO: Auto-generated Javadoc
/**
 * The Interface IHeaders.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public interface IHeaders {

	/** The content type. */
	public final String CONTENT_TYPE = "Content-Type";
	
	/** The accept. */
	public final String ACCEPT = "Accept";
	
	/** The application json. */
	public final String APPLICATION_JSON = ContentType.APPLICATION_JSON.toString();
	
	/** The application xml. */
	public final String APPLICATION_XML = ContentType.APPLICATION_XML.toString();

}

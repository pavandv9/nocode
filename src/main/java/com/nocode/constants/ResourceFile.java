/**
 * 
 */
package com.nocode.constants;

// TODO: Auto-generated Javadoc
/**
 * The Enum ResourceFile.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public enum ResourceFile implements IConfig {

	/** The config file. */
	CONFIG_FILE("config.properties"), /** The mail file. */
 MAIL_FILE("mail.properties"), /** The db file. */
 DB_FILE("db.json"), /** The db nosql file. */
 DB_NOSQL_FILE("db.nosql.json"),
	
	/** The test. */
	TEST("test"), 
 /** The test data. */
 TEST_DATA("testdata");

	/** The value. */
	private String value;

	/**
	 * Instantiates a new resource file.
	 *
	 * @param value the value
	 */
	ResourceFile(String value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	@Override
	public String getValue() {
		return value;
	}
}

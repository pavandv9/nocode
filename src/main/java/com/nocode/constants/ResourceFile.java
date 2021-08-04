/**
 * 
 */
package com.nocode.constants;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
public enum ResourceFile implements IConfig {

	CONFIG_FILE("config.properties"), MAIL_FILE("mail.properties"), DB_FILE("db.json"), DB_NOSQL_FILE("db.nosql.json");

	private String value;

	ResourceFile(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}

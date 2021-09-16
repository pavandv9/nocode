/**
 * 
 */
package com.nocode.client;

import java.util.Properties;
import java.util.Set;

import com.nocode.constants.IConfig;
import com.nocode.constants.ResourceFile;
import com.nocode.utils.PropertyUtil;

import lombok.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigManager.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class ConfigManager {

	/** The props. */
	static Properties props;

	/**
	 * Loading property file.
	 */
	static {
		props = PropertyUtil.loadProperties(ResourceFile.CONFIG_FILE);
	}

	/**
	 * Get value from config.properties file.
	 *
	 * @param key the key
	 * @return value
	 */
	public static String get(@NonNull String key) {
		return (String) props.get(key);
	}

	/**
	 * Get value from config.properties file.
	 *
	 * @param key the key
	 * @return value
	 */
	public static String get(@NonNull IConfig key) {
		return (String) props.get(key.getValue());
	}

	/**
	 * Get all config.properties names.
	 * 
	 * @return set of names
	 */
	public Set<String> getConfigProperyNames() {
		return props.stringPropertyNames();
	}
}

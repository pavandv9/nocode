/**
 * 
 */
package com.nocode.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.nocode.constants.IConfig;
import com.nocode.exception.HttpException;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyUtil.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class PropertyUtil {

	/** The properties. */
	static Properties properties = new Properties();

	/**
	 * Load properties.
	 *
	 * @param file the file
	 * @return the properties
	 */
	public static Properties loadProperties(File file) {
		try {
			properties.load(new FileInputStream(file));
			return properties;
		} catch (IOException e) {
			throw new HttpException("Unable load property file");
		}
	}

	/**
	 * Load properties.
	 *
	 * @param inputStream the input stream
	 * @return the properties
	 */
	public static Properties loadProperties(InputStream inputStream) {
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new HttpException("Unable load property file");
		}
		return properties;
	}

	/**
	 * Load properties.
	 *
	 * @param fileName the file name
	 * @return the properties
	 */
	public static Properties loadProperties(String fileName) {
		return loadProperties(new File(fileName));
	}

	/**
	 * Load properties.
	 *
	 * @param fileName the file name
	 * @return the properties
	 */
	public static Properties loadProperties(IConfig fileName) {
		return loadProperties(new PropertyUtil().getResourceFile(fileName));
	}

	/**
	 * Gets the resource file.
	 *
	 * @param configFileName the config file name
	 * @return the resource file
	 */
	public File getResourceFile(IConfig configFileName) {
		return new File(getClass().getClassLoader().getResource(configFileName.getValue()).getFile());
	}

	/**
	 * Gets the resource file.
	 *
	 * @param configFileName the config file name
	 * @return the resource file
	 */
	public File getResourceFile(String configFileName) {
		return new File(getClass().getClassLoader().getResource(configFileName).getFile());
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the string
	 */
	public static String get(String key) {
		return properties.get(key).toString();
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the string
	 */
	public static String get(IConfig key) {
		return properties.get(key.getValue()).toString();
	}

	/**
	 * Instantiates a new property util.
	 */
	public PropertyUtil() {
	}
}

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

import lombok.NonNull;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
public class PropertyUtil {

	static Properties properties = new Properties();

	public static Properties loadProperties(@NonNull File file) {
		try {
			properties.load(new FileInputStream(file));
			return properties;
		} catch (IOException e) {
			throw new HttpException("Unable load property file");
		}
	}

	public static Properties loadProperties(InputStream inputStream) {
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new HttpException("Unable load property file");
		}
		return properties;
	}

	public static Properties loadProperties(@NonNull String fileName) {
		return loadProperties(new File(fileName));
	}

	public static Properties loadProperties(@NonNull IConfig fileName) {
		return loadProperties(new PropertyUtil().getResourceFile(fileName));
	}

	public File getResourceFile(IConfig configFileName) {
		return new File(getClass().getClassLoader().getResource(configFileName.getValue()).getFile());
	}

	public File getResourceFile(String configFileName) {
		return new File(getClass().getClassLoader().getResource(configFileName).getFile());
	}

	public static String get(String key) {
		return properties.get(key).toString();
	}

	public static String get(IConfig key) {
		return properties.get(key.getValue()).toString();
	}

	public PropertyUtil() {}
}

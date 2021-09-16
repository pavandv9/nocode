/**
 * 
 */
package com.nocode.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.underscore.lodash.Json.JsonStringBuilder.Step;
import com.github.underscore.lodash.U;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.nocode.exception.HttpException;

// TODO: Auto-generated Javadoc
/**
 * The Class JavaUtil.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class JavaUtil implements ILogger {

	/**
	 * To json.
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String toJson(Object object) {
		String json = "";
		if (null == object) {
			return null;
		} else {
			if (isJsonValid(object))
				json = object.toString();
			else
				json = new GsonBuilder().serializeNulls().create().toJson(object);
		}
		return json;
	}

	/**
	 * Convert to html.
	 *
	 * @param string the string
	 * @return the string
	 */
	public static String convertToHtml(String string) {
		return string.replaceAll("\\r?\\n", "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	}

	/**
	 * Pretty json.
	 *
	 * @param json the json
	 * @return the string
	 */
	public static String prettyJson(Object json) {
		if (null == json) {
			return "<nil>";
		} else {
			try {
				return U.formatJson(json.toString(), Step.TWO_SPACES);
			} catch (ClassCastException e) {
				throw new HttpException("Invalid json body " + toJson(json));
			}
		}
	}

	/**
	 * Pretty xml.
	 *
	 * @param xml the xml
	 * @return the string
	 */
	public static String prettyXml(Object xml) {
		return xml == null ? "<nil>" : U.formatXml(xml.toString());
	}

	/**
	 * Checks if is json valid.
	 *
	 * @param object the object
	 * @return true, if is json valid
	 */
	public static boolean isJsonValid(Object object) {
		try {
			new Gson().fromJson(object.toString(), Object.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Execute shell command.
	 *
	 * @param shellCmd the shell cmd
	 * @return the string
	 */
	public static String executeShellCommand(String... shellCmd) {
		try {
			ProcessBuilder builder = new ProcessBuilder(shellCmd);
			builder.redirectErrorStream(true);
			Process process = builder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			LOG.info("Execution of shell command in progress...");
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					LOG.info("Executing of shell command is completed.");
					return line;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(
					String.format("Unable to execute shell command: %1s. %2s", shellCmd, e.getLocalizedMessage()));
		}
	}

	/**
	 * Gets the current date.
	 *
	 * @param pattern the pattern
	 * @return the current date
	 */
	public static String getCurrentDate(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * To xml.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public static String toXml(Object obj) {
		return U.jsonToXml(toJson(obj));
	}

	/**
	 * Gets the class from json array.
	 *
	 * @param filePath the file path
	 * @param claz the claz
	 * @return the class from json array
	 */
	public static Object getClassFromJsonArray(String filePath, Class<?> claz) {
		JSONParser jsonParser = new JSONParser();
		Object obj;
		try {
			obj = jsonParser.parse(new FileReader(filePath));
			JSONArray jsonArray = (JSONArray) obj;
			return new ObjectMapper().readValue(jsonArray.toString(), claz);
		} catch (IOException | ParseException e) {
			throw new JsonParseException("Unable to parse the json file: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Gets the class from json object.
	 *
	 * @param filePath the file path
	 * @param claz the claz
	 * @return the class from json object
	 */
	public static Object getClassFromJsonObject(String filePath, Class<?> claz) {
		JSONParser jsonParser = new JSONParser();
		Object obj;
		try {
			obj = jsonParser.parse(new FileReader(filePath));
			JSONObject jsonObject = (JSONObject) obj;
			return new ObjectMapper().readValue(jsonObject.toString(), claz);
		} catch (IOException | ParseException e) {
			throw new JsonParseException("Unable to parse the json file: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Gets the json from map.
	 *
	 * @param obj the obj
	 * @return the json from map
	 */
	public static Object getJsonFromMap(Map<String, Object> obj) {
		return new JSONObject(obj);
	}

	/**
	 * Gets the map from object.
	 *
	 * @param objectMap the object map
	 * @return the map from object
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromObject(Object objectMap) {
		return (Map<String, Object>) objectMap;
	}

	/**
	 * Get unknown parameters from string enclosed between {}.
	 *
	 * @param str the str
	 * @return unknown parameters
	 */
	public static Set<String> retrieveUnknownParams(String str) {
		Matcher matcher = Pattern.compile("\\{(.*?)\\}").matcher(str);
		Set<String> unknownParams = new HashSet<>();
		while (matcher.find())
			unknownParams.add(matcher.group(1));
		return unknownParams;
	}

	/**
	 * Deep merge maps.
	 *
	 * @param originalMap the original map
	 * @param mapToUpdateOriginal the map to update original
	 * @return the map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> deepMergeMaps(Map<String, Object> originalMap,
			Map<String, Object> mapToUpdateOriginal) {
		for (String key : mapToUpdateOriginal.keySet()) {
			if (mapToUpdateOriginal.get(key) instanceof Map && originalMap.get(key) instanceof Map) {
				Map<String, Object> originalChild = (Map<String, Object>) originalMap.get(key);
				Map<String, Object> newChild = (Map<String, Object>) mapToUpdateOriginal.get(key);
				originalMap.put(key, deepMergeMaps(originalChild, newChild));
			} else if (mapToUpdateOriginal.get(key) instanceof List && originalMap.get(key) instanceof List) {
				List<Object> originalChild = (List<Object>) originalMap.get(key);
				List<?> newChild = (List<?>) mapToUpdateOriginal.get(key);
				for (Object each : newChild) {
					if (!originalChild.contains(each))
						originalChild.add(each);
				}
			} else
				originalMap.put(key, mapToUpdateOriginal.get(key));
		}
		return originalMap;
	}

	/**
	 * Instantiates a new java util.
	 */
	private JavaUtil() {
	}
}

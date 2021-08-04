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
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.underscore.lodash.Json.JsonStringBuilder.Step;
import com.github.underscore.lodash.U;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.nocode.exception.HttpException;

import lombok.NonNull;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
public class JavaUtil implements ILogger {

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

	public static String convertToHtml(@NonNull String string) {
		return string.replaceAll("\\r?\\n", "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	}

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

	public static String prettyXml(Object xml) {
		return xml == null ? "<nil>" : U.formatXml(xml.toString());
	}

	public static boolean isJsonValid(Object object) {
		try {
			new Gson().fromJson(object.toString(), String.class);
//			new ObjectMapper().readTree(object.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

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

	public static String getCurrentDate(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	public static String toXml(Object obj) {
		return U.jsonToXml(toJson(obj));
	}

	public static Object getClassFromJsonArray(String filePath, Class<?> claz) {
		JSONParser jsonParser = new JSONParser();
		Gson gson = new Gson();
		Object obj;
		try {
			obj = jsonParser.parse(new FileReader(filePath));
			JSONArray jsonArray = (JSONArray) obj;
			return gson.fromJson(jsonArray.toJSONString(), claz);
		} catch (IOException | ParseException e) {
			throw new JsonParseException("Unable to parse the json file: " + e.getLocalizedMessage());
		}
	}

	public static Object getClassFromJsonObject(String filePath, Class<?> claz) {
		JSONParser jsonParser = new JSONParser();
		Gson gson = new Gson();
		Object obj;
		try {
			obj = jsonParser.parse(new FileReader(filePath));
			JSONObject jsonObject = (JSONObject) obj;
			return gson.fromJson(jsonObject.toString(), claz);
		} catch (IOException | ParseException e) {
			throw new JsonParseException("Unable to parse the json file: " + e.getLocalizedMessage());
		}
	}

	public static Object getJsonFromMap(Map<String, Object> obj) {
		return new JSONObject(obj);
	}

	/**
	 * Get unknown parameters from string enclosed between {}
	 * 
	 * @param str
	 * @return unknown parameters
	 */
	public static Set<String> retrieveUnknownParams(String str) {
		Matcher matcher = Pattern.compile("\\{(.*?)\\}").matcher(str);
		Set<String> unknownParams = new HashSet<>();
		while (matcher.find())
			unknownParams.add(matcher.group(1));
		return unknownParams;
	}

	private JavaUtil() {
	}

}

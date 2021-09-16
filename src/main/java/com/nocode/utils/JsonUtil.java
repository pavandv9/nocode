/**
 * 
 */
package com.nocode.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.nocode.exception.HttpException;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonUtil.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class JsonUtil {

	/**
	 * Read json file.
	 *
	 * @param filePath the file path
	 * @return the JSON array
	 */
	public static JSONArray readJsonFile(String filePath) {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			Object obj = jsonParser.parse(new FileReader(filePath));
			jsonArray = (JSONArray) obj;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	/**
	 * Read json file.
	 *
	 * @param file the file
	 * @return the JSON array
	 */
	public static JSONArray readJsonFile(File file) {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			Object obj = jsonParser.parse(new FileReader(file));
			jsonArray = (JSONArray) obj;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	/**
	 * Parse response body.
	 *
	 * @param jsonBody the json body
	 * @param jsonpath the jsonpath
	 * @return path of the value
	 */
	public static String parse(String jsonBody, String jsonpath) {
		String value = "";
		try {
			Object documentContext = Configuration.defaultConfiguration().jsonProvider().parse(jsonBody);
			value = JsonPath.read(documentContext, jsonpath).toString();
		} catch (PathNotFoundException e) {
			throw new HttpException("JsonPath [\"" + jsonpath + "\"] not found");
		}
		return value;
	}
}

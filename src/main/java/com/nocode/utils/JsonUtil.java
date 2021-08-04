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

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.nocode.exception.HttpException;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
public class JsonUtil {

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
	 * @param jsonpath
	 * @return path of the value
	 */
	public static String parse(String jsonBody, String jsonpath) {
		String value = "";
		try {
			DocumentContext documentContext = JsonPath.parse(jsonBody);
			value = documentContext.read(jsonpath).toString();
		} catch (PathNotFoundException e) {
			throw new HttpException("JsonPath [\"" + jsonpath + "\"] not found");
		}
		return value;
	}
}

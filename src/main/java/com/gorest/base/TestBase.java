/**
 * @author Suman
 * 
 * TestBase class to store and get the values used in automation suite
 */
package com.gorest.base;

import java.util.HashMap;
import java.util.Map;
import io.restassured.response.Response;

public class TestBase {

	public static final Map<String, String> propertyMap = new HashMap<>();
	public static Response response;

	public static String getProperty(String attribute) {
		return propertyMap.get(attribute);
	}

	public static void setProperty(String attribute, String value) {
		if (attribute.equalsIgnoreCase("token")) {
			propertyMap.put(attribute, "Bearer " + value);
		} else {
			propertyMap.put(attribute, value);
		}
	}

}

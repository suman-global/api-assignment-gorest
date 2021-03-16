/**
 * @author Suman
 * 
 * Utility class contains resuable functions that help to create request, read json etc.
 */

package com.gorest.utility;

import static io.restassured.config.EncoderConfig.encoderConfig;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;

import com.gorest.base.TestBase;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.messages.internal.com.google.common.base.Charsets;
import io.cucumber.messages.internal.com.google.common.io.Resources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utility extends TestBase {

	/**
	 * Creates the end point by concating base_url and apiEndpoint.
	 *
	 * @param base_url    the base url
	 * @param apiEndpoint the api endpoint
	 * @return the string
	 */
	public static String createEndPoint(String base_url, String apiEndpoint) {
		return base_url.concat(apiEndpoint);
	}

	/**
	 * readJson --> Reads json Value as String
	 * 
	 * @param jsonName --> Name of json
	 * @return --> Json content as String
	 */
	public static String readJson(String jsonName) {
		try {
			URL file = Resources.getResource("jsonFolder/" + jsonName + ".json");
			String jsonString = Resources.toString(file, Charsets.UTF_8);
			return jsonString;
		} catch (Exception e) {

			System.out.println("Error while altering json : " + e);
			return null;
		}

	}

	/**
	 * Gets the map from data table using key.
	 *
	 * @param table the table
	 * @param key   the key
	 * @return the map from data table using key
	 */
	public static Map<String, String> getMapFromDataTableUsingKey(DataTable table, String key) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < table.asLists().size(); i++) {
			if (table.asLists().get(i).get(0).equalsIgnoreCase(key)) {
				map.put(table.asLists().get(i).get(1),
						getProperty(table.asLists().get(i).get(2)) == null ? table.asLists().get(i).get(2)
								: getProperty(table.asLists().get(i).get(2)));
			}
		}
		return map;
	}

	/**
	 * Build api reauest and hit
	 * 
	 * @param table      datatable
	 * @param methodType http method type
	 * @return response of api
	 */
	public static Response buildRequest(DataTable table, String methodType) {

		RequestSpecification spec = RestAssured.given().log().all().contentType(ContentType.JSON)
				.queryParams(getMapFromDataTableUsingKey(table, "query"))
				.pathParams(getMapFromDataTableUsingKey(table, "path"))
				.headers(getMapFromDataTableUsingKey(table, "header"))
				.formParams(getMapFromDataTableUsingKey(table, "formParam"))
				.urlEncodingEnabled(Boolean.FALSE).config(RestAssured.config().encoderConfig(
						encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(Boolean.FALSE)));;
		switch (methodType.toLowerCase()) {
		case "get":
			return spec.log().ifValidationFails().relaxedHTTPSValidation().get(getProperty("URL"));
		case "post":
			return spec.log().ifValidationFails().relaxedHTTPSValidation().body(getProperty("body")).post(getProperty("URL"));
		case "put":
			return spec.log().ifValidationFails().relaxedHTTPSValidation().body(getProperty("body")).put(getProperty("URL"));
		case "delete":
			return spec.log().ifValidationFails().relaxedHTTPSValidation().delete(getProperty("URL"));
		case "patch":
			return spec.log().ifValidationFails().relaxedHTTPSValidation().body(getProperty("body")).patch(getProperty("URL"));
		default:
			Assert.assertTrue("Invalid method type passed: " + getProperty("URL"), false);
			return null;
		}
	}

	/**
	 * Updates the request body as per dataTable.
	 *
	 * @param table    the DataTable
	 * @param jsonBody the json body
	 * @return the string
	 */
	public static String updateRequestBody(DataTable table, String jsonBody) {
		for (int i = 0; i < table.asLists().size(); i++) {
			if (table.asLists().get(i).get(0).toLowerCase().equalsIgnoreCase("key to be update")) {
				jsonBody = Utility.updateKeyValueInJson(jsonBody, table.asLists().get(i).get(1),
						table.asLists().get(i).get(2));
			}
		}

		return jsonBody;
	}

	/**
	 * Update key value in json.
	 *
	 * @param json             the json in which key's value to be updated
	 * @param jsonPath         the jsonpath for key
	 * @param valueToBeUpdated the value to be updated
	 * @return the updated json in string
	 */
	public static String updateKeyValueInJson(String json, String jsonPath, String valueToBeUpdated) {
		DocumentContext updateJson = null;
		try {
			Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
			jsonPath = getProperty(jsonPath) == null ? jsonPath : getProperty(jsonPath);
			Object addObject = Configuration.defaultConfiguration().jsonProvider().parse(valueToBeUpdated);
			updateJson = JsonPath.parse(document).set(jsonPath, addObject);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), Boolean.FALSE);
		}
		return updateJson.jsonString();
	}

	/**
	 * Gets the data from response using json path.
	 *
	 * @param jsonPath the json path
	 * @return the data from response using json path
	 */
	public static String getDataFromResponseUsingJsonPath(String jsonPath) {
		return response.then().extract().jsonPath().getString(jsonPath);
	}

	/**
	 * Compare values.
	 *
	 * @param actualValue   the actual value in string
	 * @param expectedValue the expected value in string
	 */
	public static void compareValues(String actualValue, String expectedValue) {
		Assert.assertTrue(
				"Values are not matching, actual value: " + actualValue + ", but expected was: " + expectedValue,
				actualValue.equals(expectedValue));
	}
}

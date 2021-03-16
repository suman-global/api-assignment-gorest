/**
 * @author Suman
 * 
 * Common step definations class
 */

package com.gorest.steps;

import com.gorest.base.TestBase;
import com.gorest.utility.PropertyManager;
import com.gorest.utility.Utility;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GoRestCommonSteps extends TestBase {

	@Given("^I have the endpoint as \"([^\"]*)\"$")
	public void createEndpoint(String endpoint) {
		setProperty("URL", Utility.createEndPoint(PropertyManager.getInstance().valueFromConfig(endpoint.split("/")[0]),
				PropertyManager.getInstance().valueFromConfig(endpoint.split("/")[1])));
	}

	@Given("^I have valid Authorization token$")
	public void addToken() {
		setProperty("token",
				System.getProperty("token") == null ? System.getenv("token") : System.getProperty("token"));
	}

	@When("^I send the \"([^\"]*)\" request to \"([^\"]*)\" using$")
	public void sendReq(String methodType, String endpoint, DataTable table) {
		response = Utility.buildRequest(table, methodType);
	}

	@When("^I send the \"([^\"]*)\" request using request body as \"([^\"]*)\"$")
	public void sendPost(String methodType, String jsonBody, DataTable table) {
		setProperty("body", Utility.updateRequestBody(table, Utility.readJson(jsonBody)));
		response = Utility.buildRequest(table, methodType);
	}

	@Then("I should see the response status code as {int}")
	public void i_should_see_the_response_status_code_as_something(int strArg1) {
		response.then().log().ifValidationFails().statusCode(strArg1);
	}

	@Then("^I should see the following parameters in response as$")
	public void i_should_see_the_following_parameters_in_response_as(DataTable table) {
		for (int i = 1; i < table.asLists().size(); i++) {
			Utility.compareValues(
					Utility.getDataFromResponseUsingJsonPath(
							getProperty(table.asLists().get(i).get(1)) == null ? table.asLists().get(i).get(1)
									: getProperty(table.asLists().get(i).get(1))),
					getProperty(table.asLists().get(i).get(2)) == null ? table.asLists().get(i).get(2)
							: getProperty(table.asLists().get(i).get(2)));
		}
	}

	@And("^I fetched the following parameters in response payload as$")
	public void i_fetched_the_following_parameters_in_request_payload_as(DataTable table) {
		for (int i = 1; i < table.asLists().size(); i++) {
			setProperty(table.asLists().get(i).get(0),
					Utility.getDataFromResponseUsingJsonPath(table.asLists().get(i).get(1)));
		}
	}
}

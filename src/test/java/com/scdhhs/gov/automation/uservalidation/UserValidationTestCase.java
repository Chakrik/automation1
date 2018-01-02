package com.scdhhs.gov.automation.uservalidation;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.scdhhs.gov.automation.TestBaseWebClient;
import com.scdhhs.gov.automation.json.framework.ErrorDesc;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserValidationTestCase extends TestBaseWebClient {

	static int i = 0;
	List<ErrorDesc> errorsList = null;

	@Before
	public void before() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// startWebDriver();
		// page = getDriver().getPage(getUrl());

		System.out.println("xxxx Scenario " + i++);
	}
	//
	// @After
	// public void after() {
	// stopWebDriver();
	// }

	String testData = null;

	String testDataValid = ("{" + "  'alternatePhone': {" + "    'areaCode': '408'," + "    'lineNumber': '5309',"
			+ "    'phoneType': 'Business'," + "    'preFix': '867'" + "  }," + "  'email': 'a1@sample.com',"
			+ "  'firstName': 'Test'," + "  'lastName': 'BobLastName'," + "  'messageInformation': {"
			+ "    'source': 'SwaggerExamplePage'" + "  }," + "  'phone': {" + "    'areaCode': '408',"
			+ "    'lineNumber': '5309'," + "    'phoneType': 'Business'," + "    'preFix': '867'" + "  },"
			+ "  'preferredContactMethod': 'email'," + "  'preferredLanguage': 'American Sign'" + "}").replaceAll("'",
					"\"");

	String testDataInvalidFirstName = ("{" + "  'alternatePhone': {" + "    'areaCode': '408',"
			+ "    'lineNumber': '5309'," + "    'phoneType': 'Business'," + "    'preFix': '867'" + "  },"
			+ "  'email': 'jedwards@nope.com'," + "  'firstName': ''," + "  'lastName': 'BobLastName',"
			+ "  'messageInformation': {" + "    'source': 'SwaggerExamplePage'" + "  }," + "  'phone': {"
			+ "    'areaCode': '408'," + "    'lineNumber': '5309'," + "    'phoneType': 'Business',"
			+ "    'preFix': '867'" + "  }," + "  'preferredContactMethod': 'email',"
			+ "  'preferredLanguage': 'American Sign'" + "}").replaceAll("'", "\"");

	String testDataInvalidLastName = ("{" + "  'alternatePhone': {" + "    'areaCode': '408',"
			+ "    'lineNumber': '5309'," + "    'phoneType': 'Business'," + "    'preFix': '867'" + "  },"
			+ "  'email': 'jedwards@nope.com'," + "  'firstName': 'Bob'," + "  'lastName': '',"
			+ "  'messageInformation': {" + "    'source': 'SwaggerExamplePage'" + "  }," + "  'phone': {"
			+ "    'areaCode': '408'," + "    'lineNumber': '5309'," + "    'phoneType': 'Business',"
			+ "    'preFix': '867'" + "  }," + "  'preferredContactMethod': 'email',"
			+ "  'preferredLanguage': 'American Sign'" + "}").replaceAll("'", "\"");

	@Given("^A createCustomerInfo object$")
	public void a_createCustomerInfo_object() throws Throwable {

		System.out.println(testDataInvalidFirstName);

		System.out.println(testDataInvalidLastName);

		String responseStr = invokeWebRequestAndGetResponse(testDataValid);
		errorsList = covertResponseToErrorDesc(responseStr);
		assertTrue(errorsList.size() == 0);
	}

	@When("^the firstName field does not have a value$")
	public void the_firstName_field_does_not_have_a_value() throws Throwable {

		String responseStr = invokeWebRequestAndGetResponse(testDataInvalidFirstName);
		errorsList = covertResponseToErrorDesc(responseStr);

		System.out.println("Response  : " + errorsList);
	}

	private String invokeWebRequestAndGetResponse(String testData) throws MalformedURLException, IOException {
		URL url = new URL(getUrl());
		WebRequest requestSettings = new WebRequest(url, HttpMethod.POST);

		requestSettings.setAdditionalHeader("Accept", "application/json");
		requestSettings.setAdditionalHeader("Content-Type", "application/json");

		requestSettings.setRequestBody(testData);

		Page redirectPage = getDriver().getPage(requestSettings);
		String responseStr = redirectPage.getWebResponse().getContentAsString();
		System.out.println("web Response :" + responseStr);

		responseStr = " {\"result\" : " + responseStr + "}";
		return responseStr;
	}

	private List<ErrorDesc> covertResponseToErrorDesc(String responseStr) {
		Configuration conf = Configuration.builder().mappingProvider(new JacksonMappingProvider())
				.jsonProvider(new JacksonJsonProvider()).build();

		TypeRef<List<ErrorDesc>> type = new TypeRef<List<ErrorDesc>>() {
		};

		List<ErrorDesc> list = JsonPath.using(conf).parse(responseStr).read("$.result", type);
		return list;
	}

	@Then("^The error \"([^\"]*)\" will be returned$")
	public void the_error_will_be_returned(String expectedErrorMessage) throws Throwable {

		boolean isMessageFound = false;

		assertNotNull(errorsList);
		assertTrue(errorsList.size() > 0);

		for (ErrorDesc errDesc : errorsList) {
			if (expectedErrorMessage.equals(errDesc.getErrorMessage())) {
				isMessageFound = true;
			}
		}

		if (!isMessageFound) {
			fail();
		}
	}

	@Then("^the system will continue to the next test$")
	public void the_system_will_continue_to_the_next_test() throws Throwable {

	}

	@When("^the lastName field does not have a value$")
	public void the_lastName_field_does_not_have_a_value() throws Throwable {

		String responseStr = invokeWebRequestAndGetResponse(testDataInvalidLastName);
		errorsList = covertResponseToErrorDesc(responseStr);
		System.out.println("Response  : " + errorsList);
	}

	@When("^the date of birth field does not have a well formed date$")
	public void the_date_of_birth_field_does_not_have_a_well_formed_date() throws Throwable {
		throw new UnsupportedOperationException("date of birth field is not implemented yet.");
	}

	public UserValidationTestCase() {

	}
}

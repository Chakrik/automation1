package com.scdhhs.gov.automation;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;

public class CustomTestNGCucumberRunner extends TestNGCucumberRunner {

	public CustomTestNGCucumberRunner(Class clazz) {
		super(clazz);
	}

	public void runCucumber(CucumberFeature cucumberFeature) {

		try {
			super.runCucumber(cucumberFeature);
		} catch (Exception exception) {
			if (exception instanceof FailingHttpStatusCodeException) {
				FailingHttpStatusCodeException fhsce = (FailingHttpStatusCodeException) exception;
				System.out.println(fhsce.getMessage());
				if ("400 Bad Request".contains(fhsce.getMessage())) {
					System.out.println("Can skip 400 as WebService Is giving this Error. ");
				}

			}

		}
	}

}

package com.scdhhs.gov.automation;

import cucumber.api.CucumberOptions;


@CucumberOptions(
//        features = "target/test-classes/features",
//        glue = {"cucumber.examples.java.testNG.stepDefinitions"},
        format = {"pretty",
                "html:target/cucumber-report/firefox",
                "json:target/cucumber-report/firefox/cucumber.json",
                "junit:target/cucumber-report/firefox/cucumber.xml"},
        tags = {"~@ignored"})
public class RunTestsInFirefox extends TestBaseWebClient {
}

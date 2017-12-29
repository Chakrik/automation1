package com.scdhhs.gov.automation;

import cucumber.api.CucumberOptions;

//@RunWith(Cucumber.class) --JUnit
@CucumberOptions(
    format = {
        "json:target/cucumber/wikipedia.json",
        "html:target/cucumber/wikipedia.html",
        "pretty"
    },
    tags = {"~@ignored"}
)
public class RunTestsInChrome extends TestBase {
}

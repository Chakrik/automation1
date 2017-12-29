package com.scdhhs.gov.automation;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.gargoylesoftware.htmlunit.WebClient;

import cucumber.api.testng.AbstractTestNGCucumberTests;

public class TestBaseWebClient extends CustomAbstractTestNGCucumberTests {

	// Declare ThreadLocal Driver (ThreadLocalMap) for ThreadSafe Tests
	protected static ThreadLocal<WebClient> driver = new ThreadLocal<>();

	protected static ThreadLocal<String> url = new ThreadLocal<>();
	
	@BeforeMethod
	@Parameters(value = { "browser" })
	public void setupTest(String browser) throws MalformedURLException {
		
		// Set Browser to ThreadLocalMap
		if (browser == null ||  "".equals(browser)) {
			throw new RuntimeException("Invalid URL to start with.");
			
		} else {
			WebClient webClient = new WebClient();
		
	        webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setUseInsecureSSL(true);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
   		    webClient.getOptions().setThrowExceptionOnScriptError(false);
//	   		 webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//	         webClient.getCookieManager().setCookiesEnabled(true);
//	         webClient.getOptions().setUseInsecureSSL(true);
		    
			driver.set(webClient);
			url.set(browser);
		}
	}

	public WebClient getDriver() {
		// Get driver from ThreadLocalMap
		return driver.get();
	}
	
	public String getUrl() {
		// Get url from ThreadLocalMap
		return url.get();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		getDriver().close();
	}

	@AfterClass
	void terminate() {
		// Remove the ThreadLocalMap element
		driver.remove();
		url.remove();
	}

	protected void wait(int timeOutInSeconds) {
		try {
			Thread.sleep(timeOutInSeconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

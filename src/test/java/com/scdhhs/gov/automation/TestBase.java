package com.scdhhs.gov.automation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import cucumber.api.testng.AbstractTestNGCucumberTests;

public class TestBase extends AbstractTestNGCucumberTests{

    //Declare ThreadLocal Driver (ThreadLocalMap) for ThreadSafe Tests
    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
	
//    static {
//		  System.setProperty("webdriver.gecko.driver", "/Users/Developer/Software/SeleniumDrivers/geckodriver");	
//	}
	
    @BeforeMethod
    @Parameters(value={"browser"})
    public void setupTest (String browser) throws MalformedURLException {
        //Set DesiredCapabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //Firefox Profile Settings
        /*if (browser=="firefox") {
            FirefoxProfile profile = new FirefoxProfile();
            //Accept Untrusted Certificates
            profile.setAcceptUntrustedCertificates(true);
            profile.setAssumeUntrustedCertificateIssuer(false);
            //Use No Proxy Settings
            profile.setPreference("network.proxy.type", 0);
            //Set Firefox profile to capabilities
            capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        }*/

        //Set BrowserName
        capabilities.setCapability("browserName", browser);
        
        //Set Browser to ThreadLocalMap
        if (browser !=null && !"".equals(browser)) {
//          driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities));
            driver.set(new RemoteWebDriver(new URL("http://192.168.99.100:31582/wd/hub"), capabilities));
        } else {
        		FirefoxDriver fxDriver = new FirefoxDriver();
            fxDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
        	    driver.set(fxDriver);
        }
    }

    public WebDriver getDriver() {
        //Get driver from ThreadLocalMap
        return driver.get();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        getDriver().quit();
    }

    @AfterClass 
    void terminate () {
        //Remove the ThreadLocalMap element
        driver.remove();
    }
    
    protected void wait(int timeOutInSeconds) {
        try {
            Thread.sleep(timeOutInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

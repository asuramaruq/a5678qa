package com.example.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SauceLabsTestBase {

    protected WebDriver driver;

    @BeforeClass
    public void setUp() throws Exception {

        String username = "username";
        String accessKey = "apikey";

        System.out.println("SauceLabs Username: " + username);
        System.out.println("SauceLabs Access Key: " + accessKey);

        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 11");
        browserOptions.setBrowserVersion("latest");

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", username);
        sauceOptions.put("accessKey", accessKey);
        sauceOptions.put("build", "selenium-build-NB90Y");
        sauceOptions.put("name", "test4");

        browserOptions.setCapability("sauce:options", sauceOptions);

        URL url = new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
        driver = new RemoteWebDriver(url, browserOptions);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

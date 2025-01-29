package com.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.io.FileHandler;

public class AppTest {
    private static final Logger logger = LogManager.getLogger(AppTest.class);
    WebDriver driver;
    private static ExtentReports extent;
    private static ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public void setup() {
        logger.info("Starting test setup...");
        System.setProperty("webdriver.chrome.driver", "/Users/nesstq/Developer/qa/Assignment5TestNG/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void searchKpop() {
        test = extent.createTest("Search Kpop Test");
        try {
            logger.info("Opening Google...");
            driver.get("https://www.wikipedia.org/");
            Thread.sleep(2000);

            logger.info("Searching for 'kpop'...");
            WebElement searchBox = driver.findElement(By.name("search"));
            searchBox.sendKeys("kpop");
            Thread.sleep(1000);
            searchBox.submit();
            Thread.sleep(2000);

            logger.info("Checking search results...");
            String pageSource = driver.getPageSource();
            boolean isKpopPresent = pageSource.contains("K-pop") || pageSource.contains("Kpop") || pageSource.contains("kpop");
            Assert.assertTrue(isKpopPresent, "Kpop not found in search results");

            test.pass("Kpop found in search results.");
            String screenshotPath = captureScreenshot("searchKpopSuccess");
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            String screenshotPath = captureScreenshot("searchKpopFail");
            test.addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Test(priority = 2)
    public void searchKpopFail() {
        test = extent.createTest("Search Kpop Test Fail");
        try {
            logger.info("Opening Wiki...");
            driver.get("https://www.wikipedia.org/");
            Thread.sleep(2000);

            logger.info("Searching for 'mozart'...");
            WebElement searchBox = driver.findElement(By.name("search"));
            searchBox.sendKeys("mozart");
            Thread.sleep(1000);
            searchBox.submit();
            Thread.sleep(2000);

            logger.info("Checking search results...");
            String pageSource = driver.getPageSource();
            boolean isKpopPresent = pageSource.contains("K-pop") || pageSource.contains("Kpop") || pageSource.contains("kpop");
            Assert.assertFalse(isKpopPresent, "Kpop found in search results");

            test.pass("Kpop not found in search results as expected.");
            String screenshotPath = captureScreenshot("searchKpopFail");
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            test.fail("Unexpected error: " + e.getMessage());
            String screenshotPath = captureScreenshot("searchKpopFailError");
            test.addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Test(priority = 3)
    public void loginTest() {
        test = extent.createTest("Login Test");
        try {
            logger.info("Opening login page...");
            driver.get("https://valhall-proj.onrender.com/");
            Thread.sleep(2000);

            logger.info("Entering username...");
            driver.findElement(By.id("usernameL")).sendKeys("user");
            Thread.sleep(2000);

            logger.info("Entering password...");
            driver.findElement(By.id("pwdL")).sendKeys("user");
            Thread.sleep(2000);

            logger.info("Clicking sign in button...");
            driver.findElement(By.xpath("//button[text()='Sign in']")).click();
            Thread.sleep(5000);

            logger.info("Checking login success...");
            String pageSource = driver.getPageSource();
            boolean isLoginSuccessful = pageSource.contains("Search for your favorite esports team");
            Assert.assertTrue(isLoginSuccessful, "Login not successful");

            test.pass("Login successful.");
            String screenshotPath = captureScreenshot("loginTestSuccess");
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            String screenshotPath = captureScreenshot("loginTestFail");
            test.addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Test(priority = 4)
    public void loginTestFail() {
        test = extent.createTest("Login Test Fail");
        try {
            logger.info("Opening login page...");
            driver.get("https://ornur.github.io/delight/");
            Thread.sleep(2000);

            driver.findElement(By.linkText("Login")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@class='card-footer py-3 border-0']/div/a")).click();            Thread.sleep(2000);


            logger.info("Entering username...");
            driver.findElement(By.id("email")).sendKeys("user123");
            Thread.sleep(2000);

            logger.info("Entering password...");
            driver.findElement(By.id("password")).sendKeys("user123");
            Thread.sleep(2000);

            logger.info("Clicking sign in button...");
            driver.findElement(By.id("loginBtn")).click();
            Thread.sleep(5000);

            logger.info("Checking login success...");
            String pageSource = driver.getPageSource();
            boolean isLoginUnsuccessful = pageSource.contains("Please check if the entered information is correct!");
            Assert.assertTrue(isLoginUnsuccessful, "Login is expectedly unsuccessful");

            test.pass("Login failed as expected.");
            String screenshotPath = captureScreenshot("loginTestFail");
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            test.fail("Unexpected error: " + e.getMessage());
            String screenshotPath = captureScreenshot("loginTestFailError");
            test.addScreenCaptureFromPath(screenshotPath);
        }
    }

    @AfterClass
    public void teardown() {
        logger.info("Closing browser...");
        driver.quit();
        extent.flush();
    }

    public String captureScreenshot(String fileName) {
        File directory = new File("screenshots");
        if (!directory.exists()) {
            directory.mkdir();
        }

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File("screenshots/" + fileName + ".png");
        try {
            FileHandler.copy(srcFile, destFile);
            logger.info("Screenshot captured: " + destFile.getAbsolutePath());
            return "screenshots/" + fileName + ".png";
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}
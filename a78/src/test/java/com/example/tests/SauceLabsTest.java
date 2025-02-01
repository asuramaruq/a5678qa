package com.example.tests;

import com.example.utils.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;


public class SauceLabsTest extends SauceLabsTestBase {

    private String username;
    private String password;

    @BeforeTest
    public void setupCredentials() {
        String[] credentials = ExcelUtils.getCredentials(1);
        username = credentials[0];
        password = credentials[1];
    }

    @Test(priority = 1)
    public void loginTest() {
        try {
            driver.get("https://valhall-proj.onrender.com/");
            Thread.sleep(2000);

            driver.findElement(By.id("usernameL")).sendKeys(username);
            Thread.sleep(2000);

            driver.findElement(By.id("pwdL")).sendKeys(password);
            Thread.sleep(2000);

            driver.findElement(By.xpath("//button[text()='Sign in']")).click();
            Thread.sleep(5000);

            String pageSource = driver.getPageSource();
            boolean isLoginSuccessful = pageSource.contains("Search for your favorite esports team");
            Assert.assertTrue(isLoginSuccessful, "Login not successful");
            
            ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");

            Assert.fail("Login test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void loginTestFail() {
        try {
            driver.get("https://ornur.github.io/delight/");
            Thread.sleep(2000);

            driver.findElement(By.linkText("Login")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@class='card-footer py-3 border-0']/div/a")).click();            Thread.sleep(2000);


            driver.findElement(By.id("email")).sendKeys("user123");
            Thread.sleep(2000);

            driver.findElement(By.id("password")).sendKeys("user123");
            Thread.sleep(2000);

            driver.findElement(By.id("loginBtn")).click();
            Thread.sleep(5000);

            String pageSource = driver.getPageSource();
            boolean isLoginUnsuccessful = pageSource.contains("Please check if the entered information is correct!");
            Assert.assertTrue(isLoginUnsuccessful, "Login is expectedly unsuccessful");
            ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");

            Assert.fail("Login test failed: " + e.getMessage());
        }
    }
}

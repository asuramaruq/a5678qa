package com.example.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.testng.Assert;

public class LoginSteps {

    private WebDriver driver;

    // Set up WebDriver before each scenario
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
    }

    // Scenario 1: Successful login
    @Given("the user navigates to the login page")
    public void theUserNavigatesToTheLoginPage() throws InterruptedException {
        driver.get("https://valhall-proj.onrender.com/");
        Thread.sleep(2000); // Sleep for 2 seconds to ensure page is loaded
    }

    @When("the user enters valid credentials")
    public void theUserEntersValidCredentials() throws InterruptedException {
        driver.findElement(By.id("usernameL")).sendKeys("user");
        Thread.sleep(1000); // Sleep for 1 second
        driver.findElement(By.id("pwdL")).sendKeys("user");
        Thread.sleep(1000); // Sleep for 1 second
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();
        Thread.sleep(2000); // Sleep for 2 seconds to allow page to load
    }

    @Then("the user should be redirected to the home page")
    public void theUserShouldBeRedirectedToTheHomePage() throws InterruptedException {
        Thread.sleep(2000); // Sleep for 2 seconds to ensure page is fully loaded
        String pageSource = driver.getPageSource();
        boolean isLoginSuccessful = pageSource.contains("Search for your favorite esports team");
        Assert.assertTrue(isLoginSuccessful, "Login not successful");
    }

    // Scenario 2: Unsuccessful login
    @Given("the user navigates to the login page for invalid credentials")
    public void theUserNavigatesToTheLoginPageFail() throws InterruptedException {
        driver.get("https://ornur.github.io/delight/");
        Thread.sleep(2000); // Sleep for 2 seconds to ensure page is loaded
        driver.findElement(By.linkText("Login")).click();
        Thread.sleep(1000); // Sleep for 1 second
        driver.findElement(By.xpath("//div[@class='card-footer py-3 border-0']/div/a")).click();
        Thread.sleep(1000); // Sleep for 1 second
    }

    @When("the user enters invalid credentials")
    public void theUserEntersInvalidCredentials() throws InterruptedException {
        driver.findElement(By.id("email")).sendKeys("user123");
        Thread.sleep(1000); // Sleep for 1 second
        driver.findElement(By.id("password")).sendKeys("user123");
        Thread.sleep(1000); // Sleep for 1 second
        driver.findElement(By.id("loginBtn")).click();
        Thread.sleep(2000); // Sleep for 2 seconds to allow page to load
    }

    @Then("the user should see an error message")
    public void theUserShouldSeeAnErrorMessage() throws InterruptedException {
        Thread.sleep(2000); // Sleep for 2 seconds to ensure page is fully loaded
        String pageSource = driver.getPageSource();
        boolean isLoginUnsuccessful = pageSource.contains("Please check if the entered information is correct!");
        Assert.assertTrue(isLoginUnsuccessful, "Login is expectedly unsuccessful");
    }

    // Tear down WebDriver after each scenario
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Close the browser and end the session after each scenario
        }
    }
}

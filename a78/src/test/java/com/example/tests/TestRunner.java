package com.example.tests;

import org.testng.annotations.Test;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.example.steps",  // Correct the glue path to match the package of LoginSteps
    plugin = {"pretty", "html:target/cucumber-reports"},
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Test
    public void runCucumberTests() {
        // This will run the Cucumber scenarios
    }
}

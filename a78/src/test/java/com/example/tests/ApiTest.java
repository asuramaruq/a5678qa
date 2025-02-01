package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTest {
    //

    private String api_key = "apikey";

    private double latitude = 51.1655; // Latitude for Nur-Sultan
    private double longitude = 71.4272; // Longitude for Nur-Sultan

    private String api_url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + api_key;

    @Test
    public void testGetWeatherByCoordinates() {
        Response response = RestAssured.get(api_url);

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");

        String cityName = response.jsonPath().getString("name");
        Assert.assertEquals(cityName, "Nur-Sultan", "City name should be Nur-Sultan");

        String country = response.jsonPath().getString("sys.country");
        Assert.assertEquals(country, "KZ", "Country code should be KZ (Kazakhstan)");

        System.out.println("Response: " + response.getBody().asString());
    }

    @Test
    public void testInvalidCoordinates() {
        latitude = 9999;  // Invalid latitude
        longitude = 9999; // Invalid longitude
        String invalidApiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + api_key;
    
        Response response = RestAssured.get(invalidApiUrl);
    
        // Expect 400 status code instead of 404
        Assert.assertEquals(response.getStatusCode(), 400, "Expected status code is 400 for invalid coordinates");
    
        System.out.println("Response: " + response.getBody().asString());
    }
    
}

Feature: User Login

  Scenario: Successful login
    Given the user navigates to the login page
    When the user enters valid credentials
    Then the user should be redirected to the home page

  Scenario: Unsuccessful login
    Given the user navigates to the login page for invalid credentials
    When the user enters invalid credentials
    Then the user should see an error message

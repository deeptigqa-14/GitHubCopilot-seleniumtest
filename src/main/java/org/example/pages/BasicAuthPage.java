package org.example.pages;

import org.openqa.selenium.WebDriver;

public class BasicAuthPage extends BasePage {
    private static final String BASE_URL = "https://the-internet.herokuapp.com/basic_auth";

    public BasicAuthPage(WebDriver driver) {
        super(driver);
    }

    public void goToBasicAuthPage(String username, String password) {
        // Construct URL with basic auth credentials
        String authUrl = "https://" + username + ":" + password + "@the-internet.herokuapp.com/basic_auth";
        navigateTo(authUrl);
    }

    public void goToBasicAuthPageWithoutAuth() {
        navigateTo(BASE_URL);
    }

    public boolean isAuthenticationSuccessful() {
        // Check if the page contains the success message
        return driver.getPageSource().contains("Congratulations!");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getPageContent() {
        return driver.getPageSource();
    }

    public boolean isUnauthorizedPageDisplayed() {
        // Check if browser shows 401 unauthorized or similar
        return driver.getPageSource().contains("401") ||
                driver.getPageSource().contains("Unauthorized");
    }
}
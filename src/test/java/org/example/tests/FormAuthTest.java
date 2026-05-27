package org.example.tests;

import org.example.pages.FormAuthPage;
import org.example.pages.HomePage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.*;

public class FormAuthTest {
    private WebDriver driver;
    private HomePage homePage;
    private FormAuthPage formAuthPage;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
        formAuthPage = new FormAuthPage(driver);
    }

    @After
    public void tearDown() {
        DriverManager.closeDriver();
    }

    @Test
    public void testSuccessfulLogin() {
        // Navigate to home page and click form authentication
        homePage.goToHome();
        homePage.clickFormAuthentication();

        // Perform login with valid credentials
        formAuthPage.login("tomsmith", "SuperSecretPassword!");

        // Wait for manual verification
        formAuthPage.staticWait(5);

        // Verify success message appears
        assertTrue("Flash message should be displayed", formAuthPage.isFlashMessageDisplayed());
        assertTrue("Success message should contain 'You logged into a secure area'",
                formAuthPage.getFlashMessage().contains("You logged into a secure area"));
    }

    @Test
    public void testLoginWithInvalidUsername() {
        // Navigate and attempt login with invalid username
        homePage.goToHome();
        homePage.clickFormAuthentication();

        formAuthPage.login("invaliduser", "SuperSecretPassword!");

        // Wait for manual verification
        formAuthPage.staticWait(5);

        // Verify error message appears
        assertTrue("Flash message should be displayed", formAuthPage.isFlashMessageDisplayed());
        assertTrue("Error message should contain 'Invalid credentials'",
                formAuthPage.getFlashMessage().contains("Invalid credentials"));
    }

    @Test
    public void testLoginWithInvalidPassword() {
        // Navigate and attempt login with invalid password
        homePage.goToHome();
        homePage.clickFormAuthentication();

        formAuthPage.login("tomsmith", "WrongPassword");

        // Wait for manual verification
        formAuthPage.staticWait(5);

        // Verify error message appears
        assertTrue("Flash message should be displayed", formAuthPage.isFlashMessageDisplayed());
        assertTrue("Error message should contain 'Invalid credentials'",
                formAuthPage.getFlashMessage().contains("Invalid credentials"));
    }

    @Test
    public void testLoginWithEmptyFields() {
        // Navigate to form auth page
        homePage.goToHome();
        homePage.clickFormAuthentication();

        // Attempt to login with empty fields
        formAuthPage.clickLogin();

        // Wait for manual verification
        formAuthPage.staticWait(5);

        // Verify error or validation message appears
        assertTrue("Flash message should be displayed", formAuthPage.isFlashMessageDisplayed());
    }
}
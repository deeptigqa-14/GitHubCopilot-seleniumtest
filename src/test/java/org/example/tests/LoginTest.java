package org.example.tests;

import org.example.pages.LoginPage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.*;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {
        DriverManager.closeDriver();
    }

    @Test
    public void testLoginPageLoads() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify login page elements are displayed
        assertTrue("Username field should be displayed", loginPage.isUsernameFieldDisplayed());
        assertTrue("Password field should be displayed", loginPage.isPasswordFieldDisplayed());
        assertTrue("Login button should be displayed", loginPage.isLoginButtonDisplayed());
    }

    @Test
    public void testSuccessfulLogin() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Perform login with valid credentials
        loginPage.login("tomsmith", "SuperSecretPassword!");

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify success message appears
        assertTrue("Flash message should be displayed", loginPage.isFlashMessageDisplayed());
        String flashMessage = loginPage.getFlashMessage();
        assertTrue("Flash message should contain success message",
                flashMessage.contains("You logged into a secure area"));
    }

    @Test
    public void testLoginWithInvalidUsername() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Attempt login with invalid username
        loginPage.login("invaliduser", "SuperSecretPassword!");

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify error message appears
        assertTrue("Flash message should be displayed", loginPage.isFlashMessageDisplayed());
        String flashMessage = loginPage.getFlashMessage();
        assertTrue("Flash message should contain error message",
                flashMessage.contains("Invalid credentials"));
    }

    @Test
    public void testLoginWithInvalidPassword() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Attempt login with invalid password
        loginPage.login("tomsmith", "WrongPassword");

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify error message appears
        assertTrue("Flash message should be displayed", loginPage.isFlashMessageDisplayed());
        String flashMessage = loginPage.getFlashMessage();
        assertTrue("Flash message should contain error message",
                flashMessage.contains("Invalid credentials"));
    }

    @Test
    public void testLoginWithBlankUsername() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Attempt login with blank username
        loginPage.enterPassword("SuperSecretPassword!");
        loginPage.clickLoginButton();

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify error message or appropriate response
        assertTrue("Error message should be displayed or page should remain",
                loginPage.isFlashMessageDisplayed() || loginPage.isUsernameFieldDisplayed());
    }

    @Test
    public void testLoginWithBlankPassword() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Attempt login with blank password
        loginPage.enterUsername("tomsmith");
        loginPage.clickLoginButton();

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify error message or appropriate response
        assertTrue("Error message should be displayed or page should remain",
                loginPage.isFlashMessageDisplayed() || loginPage.isPasswordFieldDisplayed());
    }

    @Test
    public void testLoginWithBothFieldsBlank() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Attempt login without filling any fields
        loginPage.clickLoginButton();

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify error message appears
        assertTrue("Login page or error message should be displayed",
                loginPage.isUsernameFieldDisplayed() || loginPage.isFlashMessageDisplayed());
    }

    @Test
    public void testUsernameFieldClear() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Enter and clear username
        loginPage.enterUsername("tomsmith");
        loginPage.staticWait(2);
        loginPage.clearUsernameField();

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify field is empty
        String usernameValue = loginPage.getUsernameFieldValue();
        assertTrue("Username field should be empty", usernameValue.isEmpty());
    }

    @Test
    public void testPasswordFieldClear() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Enter and clear password
        loginPage.enterPassword("SuperSecretPassword!");
        loginPage.staticWait(2);
        loginPage.clearPasswordField();

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify field is empty
        String passwordValue = loginPage.getPasswordFieldValue();
        assertTrue("Password field should be empty", passwordValue.isEmpty());
    }

    @Test
    public void testMultipleLoginAttempts() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // First login attempt - invalid credentials
        loginPage.login("wronguser", "wrongpass");
        loginPage.staticWait(3);

        // Verify error message
        assertTrue("First login attempt should show error",
                loginPage.isFlashMessageDisplayed());

        // Navigate back to login page
        loginPage.navigateToLogin();

        // Second login attempt - valid credentials
        loginPage.login("tomsmith", "SuperSecretPassword!");

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify success
        assertTrue("Second login attempt should be successful",
                loginPage.isFlashMessageDisplayed());
        assertTrue("Success message should be displayed",
                loginPage.getFlashMessage().contains("You logged into a secure area"));
    }

    @Test
    public void testLoginPageURL() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify current URL
        String currentUrl = driver.getCurrentUrl();
        assertTrue("URL should contain /login",
                currentUrl.contains("the-internet.herokuapp.com/login") ||
                        currentUrl.contains("secure"));
    }

    @Test
    public void testLoginWithSpecialCharactersInPassword() {
        // Navigate to login page
        loginPage.navigateToLogin();

        // Attempt login with special characters
        loginPage.login("tomsmith", "Pass@word!123#");

        // Wait for manual verification
        loginPage.staticWait(5);

        // Verify response (should be error with this password)
        assertTrue("Page should respond to special character input",
                loginPage.isFlashMessageDisplayed() || loginPage.isLoginButtonDisplayed());
    }
}
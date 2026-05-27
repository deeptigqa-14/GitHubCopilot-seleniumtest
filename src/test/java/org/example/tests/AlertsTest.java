package org.example.tests;

import org.example.pages.AlertsPage;
import org.example.pages.HomePage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.*;

public class AlertsTest {
    private WebDriver driver;
    private HomePage homePage;
    private AlertsPage alertsPage;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
        alertsPage = new AlertsPage(driver);
    }

    @After
    public void tearDown() {
        DriverManager.closeDriver();
    }

    @Test
    public void testJSAlert() {
        // Navigate to alerts page
        homePage.goToHome();
        homePage.clickAlerts();

        // Click JS alert button
        alertsPage.clickJSAlertButton();

        // Wait for manual verification
        alertsPage.staticWait(5);

        // Get and verify alert text
        String alertText = alertsPage.getAlertText();
        assertTrue("Alert should contain 'I am a JS Alert'", alertText.contains("I am a JS Alert"));

        // Accept the alert
        alertsPage.acceptAlert();
    }

    @Test
    public void testJSConfirmAccept() {
        // Navigate to alerts page
        homePage.goToHome();
        homePage.clickAlerts();

        // Click JS confirm button
        alertsPage.clickJSConfirmButton();

        // Wait for manual verification
        alertsPage.staticWait(5);

        // Get and verify alert text
        String alertText = alertsPage.getAlertText();
        assertTrue("Alert should contain 'I am a JS Confirm'", alertText.contains("I am a JS Confirm"));

        // Accept the alert
        alertsPage.acceptAlert();

        // Verify result message
        alertsPage.staticWait(2);
        assertTrue("Result should be displayed", alertsPage.isResultDisplayed());
        assertTrue("Result should contain 'Ok'", alertsPage.getResultText().contains("Ok"));
    }

    @Test
    public void testJSConfirmDismiss() {
        // Navigate to alerts page
        homePage.goToHome();
        homePage.clickAlerts();

        // Click JS confirm button
        alertsPage.clickJSConfirmButton();

        // Wait for manual verification
        alertsPage.staticWait(5);

        // Get and verify alert text
        String alertText = alertsPage.getAlertText();
        assertTrue("Alert should contain 'I am a JS Confirm'", alertText.contains("I am a JS Confirm"));

        // Dismiss the alert
        alertsPage.dismissAlert();

        // Verify result message
        alertsPage.staticWait(2);
        assertTrue("Result should be displayed", alertsPage.isResultDisplayed());
        assertTrue("Result should contain 'Cancel'", alertsPage.getResultText().contains("Cancel"));
    }

    @Test
    public void testJSPromptWithInput() {
        // Navigate to alerts page
        homePage.goToHome();
        homePage.clickAlerts();

        // Click JS prompt button
        alertsPage.clickJSPromptButton();

        // Wait for manual verification
        alertsPage.staticWait(5);

        // Get and verify alert text
        String alertText = alertsPage.getAlertText();
        assertTrue("Alert should contain 'I am a JS prompt'", alertText.contains("I am a JS prompt"));

        // Send text to prompt
        alertsPage.sendKeysToAlert("Test Input for Alerts");
        alertsPage.acceptAlert();

        // Verify result message
        alertsPage.staticWait(2);
        assertTrue("Result should be displayed", alertsPage.isResultDisplayed());
        assertTrue("Result should contain input text", alertsPage.getResultText().contains("Test Input"));
    }

    @Test
    public void testJSPromptDismiss() {
        // Navigate to alerts page
        homePage.goToHome();
        homePage.clickAlerts();

        // Click JS prompt button
        alertsPage.clickJSPromptButton();

        // Wait for manual verification
        alertsPage.staticWait(5);

        // Dismiss the prompt without entering text
        alertsPage.dismissAlert();

        // Verify result message
        alertsPage.staticWait(2);
        assertTrue("Result should be displayed", alertsPage.isResultDisplayed());
    }

}
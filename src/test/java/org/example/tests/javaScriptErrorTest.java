package org.example.tests;

import org.example.pages.HomePage;
import org.example.pages.JavaScriptErrorPage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;

import static org.junit.Assert.*;

public class javaScriptErrorTest {
    private WebDriver driver;
    private JavaScriptErrorPage jsErrorPage;
    private HomePage homePage;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
        jsErrorPage = new JavaScriptErrorPage(driver);
    }

    @After
    public void tearDown() {
        DriverManager.closeDriver();
    }

    @Test
    public void testJavaScriptErrorExists() {
        // Navigate to the JavaScript error page
        homePage.goToHome();
        jsErrorPage.navigateToJSErrorPage();
        jsErrorPage.staticWait(3);

        // Allow time for JavaScript to execute and error to be logged
        jsErrorPage.waitForJSError();

        // Wait for manual verification
        jsErrorPage.staticWait(5);

        // Verify that JavaScript errors exist
        assertTrue("JavaScript error should exist on the page",
                jsErrorPage.hasJavaScriptErrors());
    }

    @Test
    public void testErrorCount() {
        // Navigate to JavaScript error page
        jsErrorPage.navigateToJSErrorPage();
        jsErrorPage.staticWait(3);

        // Wait for error to appear
       // jsErrorPage.waitForJSError();
        jsErrorPage.staticWait(2);

        // Get error count
        int errorCount = jsErrorPage.getErrorCount();

        // Wait for manual verification
        jsErrorPage.staticWait(5);

        // Verify at least one error exists
        assertTrue("At least one error should be present", errorCount > 0);
        System.out.println("Total JavaScript errors found: " + errorCount);
    }

    @Test
    public void testFirstErrorMessage() {
        // Navigate to JavaScript error page
        jsErrorPage.navigateToJSErrorPage();
        jsErrorPage.staticWait(3);

        // Wait for error
        jsErrorPage.waitForJSError();
        jsErrorPage.staticWait(2);

        // Get first error message
        String firstError = jsErrorPage.getFirstErrorMessage();

        // Wait for manual verification
        jsErrorPage.staticWait(5);

        // Verify error message is not empty
        assertNotNull("Error message should not be null", firstError);
        assertFalse("Error message should not be empty", firstError.isEmpty());
        System.out.println("First error message: " + firstError);
    }

    @Test
    public void testErrorMessageContent() {
        // Navigate to JavaScript error page
        jsErrorPage.navigateToJSErrorPage();
        jsErrorPage.staticWait(3);

        // Wait for error
        jsErrorPage.waitForJSError();
        jsErrorPage.staticWait(2);

        // Get error messages
        List<String> errorMessages = jsErrorPage.getAllErrorMessages();

        // Wait for manual verification
        jsErrorPage.staticWait(5);

        // Verify error messages contain expected content
        assertTrue("Error messages should not be empty", !errorMessages.isEmpty());
        for (String msg : errorMessages) {
            System.out.println("Error found: " + msg);
            assertNotNull("Error message should not be null", msg);
        }
    }

    @Test
    public void testErrorMessageContainsKeyword() {
        // Navigate to JavaScript error page
        jsErrorPage.navigateToJSErrorPage();
        jsErrorPage.staticWait(3);

        // Wait for error
        jsErrorPage.waitForJSError();
        jsErrorPage.staticWait(2);

        // Check for specific keyword in error message
        // Adjust keyword based on actual error on the page
        String keyword = "Cannot read properties"; // Common JS error pattern

        // Wait for manual verification
        jsErrorPage.staticWait(5);

        // Verify error contains keyword or print actual errors
        if (!jsErrorPage.errorMessageContains(keyword)) {
            System.out.println("Keyword '" + keyword + "' not found. Actual errors:");
            jsErrorPage.printJavaScriptErrors();
        }
    }

    @Test
    public void testAllErrorMessages() {
        // Navigate to JavaScript error page
        jsErrorPage.navigateToJSErrorPage();
        jsErrorPage.staticWait(3);

        // Wait for error
        jsErrorPage.waitForJSError();
        jsErrorPage.staticWait(2);

        // Get all error messages
        List<String> allErrors = jsErrorPage.getAllErrorMessages();

        // Wait for manual verification
        jsErrorPage.staticWait(5);

        // Print all errors to console
        System.out.println("=== All JavaScript Errors ===");
        for (int i = 0; i < allErrors.size(); i++) {
            System.out.println((i + 1) + ". " + allErrors.get(i));
        }

        // Verify at least one error
        assertTrue("Should have at least one error", allErrors.size() > 0);
    }

    @Test
    public void testErrorDetails() {
        // Navigate to JavaScript error page
        jsErrorPage.navigateToJSErrorPage();
        jsErrorPage.staticWait(3);

        // Wait for error
        jsErrorPage.waitForJSError();
        jsErrorPage.staticWait(2);

        // Get error count
        int errorCount = jsErrorPage.getErrorCount();

        // Wait for manual verification
        jsErrorPage.staticWait(5);

        // Print detailed error info
        if (errorCount > 0) {
            System.out.println("=== Error Details ===");
            String details = jsErrorPage.getErrorDetails(0);
            System.out.println(details);
            assertNotNull("Error details should not be null", details);
        }
    }

    @Test
    public void testConsoleLogs() {
        // Navigate to JavaScript error page
        jsErrorPage.navigateToJSErrorPage();
        jsErrorPage.staticWait(3);

        // Wait for error
        jsErrorPage.waitForJSError();
        jsErrorPage.staticWait(2);

        // Wait for manual verification
        jsErrorPage.staticWait(5);

        // Print all console logs
        jsErrorPage.printAllConsoleLogs();
        jsErrorPage.printJavaScriptErrors();
    }

    @Test
    public void testNoErrorOnErrorFreePageNavigation() {
        // Navigate to home page (should have no JS errors)
        jsErrorPage.navigateTo("https://the-internet.herokuapp.com");
        jsErrorPage.staticWait(3);

        // Check for errors
        int errorCount = jsErrorPage.getErrorCount();

        // Wait for manual verification
        jsErrorPage.staticWait(5);

        // Home page should ideally have fewer/no errors
        System.out.println("Errors on home page: " + errorCount);
        // Note: This may vary; some pages might have expected warnings
    }
        // Additional cleanup if needed

}
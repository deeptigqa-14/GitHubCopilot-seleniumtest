package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Page object for JavaScript error page: https://the-internet.herokuapp.com/javascript_error
 * This page captures and validates JavaScript errors logged to the browser console.
 */
public class JavaScriptErrorPage extends BasePage {
    private static final String JS_ERROR_URL = "https://the-internet.herokuapp.com/javascript_error";

   /* @FindBy(tagName = "body")
    private WebElement pageBody;*/

    public JavaScriptErrorPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToJSErrorPage() {
        navigateTo(JS_ERROR_URL);
    }

    /**
     * Get all browser console logs (all levels: SEVERE, WARNING, INFO, etc.)
     */
    public List<LogEntry> getAllConsoleLogs() {
        try {
            List<LogEntry> logs = driver.manage().logs().get(LogType.BROWSER).getAll();
            System.out.println("Total console logs retrieved: " + logs.size());
            for (LogEntry log : logs) {
                System.out.println("[" + log.getLevel() + "] " + log.getMessage());
            }
            return logs;
        } catch (Exception e) {
            System.err.println("Error fetching console logs: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Get only SEVERE level logs (typically JavaScript errors).
     */
    public List<LogEntry> getJavaScriptErrors() {
        List<LogEntry> allLogs = getAllConsoleLogs();
        List<LogEntry> jsErrors = new ArrayList<>();
        for (LogEntry log : allLogs) {
            if (log.getLevel() == Level.SEVERE) {
                jsErrors.add(log);
            }
        }
        System.out.println("SEVERE level logs found: " + jsErrors.size());
        return jsErrors;
    }

    /**
     * Get count of JavaScript errors found in console.
     */
    public int getErrorCount() {
        int count = getJavaScriptErrors().size();
        System.out.println("Error count: " + count);
        return count;
    }

    /**
     * Check if any JavaScript errors exist on the page.
     */
    public boolean hasJavaScriptErrors() {
        return getErrorCount() > 0;
    }

    /**
     * Get the message of the first JavaScript error (or empty string if none).
     */
    public String getFirstErrorMessage() {
        List<LogEntry> errors = getJavaScriptErrors();
        if (errors.isEmpty()) {
            return "";
        }
        return errors.get(0).getMessage();
    }

    /**
     * Get all JavaScript error messages as a list.
     */
    public List<String> getAllErrorMessages() {
        List<LogEntry> errors = getJavaScriptErrors();
        List<String> messages = new ArrayList<>();
        for (LogEntry error : errors) {
            messages.add(error.getMessage());
        }
        return messages;
    }

    /**
     * Check if a specific error message (substring) exists in console.
     */
    public boolean errorMessageContains(String substring) {
        for (String message : getAllErrorMessages()) {
            if (message.contains(substring)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print all console logs to console for debugging.
     */
    public void printAllConsoleLogs() {
        List<LogEntry> logs = getAllConsoleLogs();
        System.out.println("=== All Console Logs ===");
        for (LogEntry log : logs) {
            System.out.println("[" + log.getLevel() + "] " + log.getMessage());
        }
    }

    /**
     * Print only JavaScript errors to console.
     */
    public void printJavaScriptErrors() {
        List<LogEntry> errors = getJavaScriptErrors();
        System.out.println("=== JavaScript Errors ===");
        for (LogEntry error : errors) {
            System.out.println("[" + error.getLevel() + "] " + error.getMessage());
        }
    }

    /**
     * Get verbose error details including timestamp and logger.
     */
    public String getErrorDetails(int errorIndex) {
        List<LogEntry> errors = getJavaScriptErrors();
        if (errorIndex < 0 || errorIndex >= errors.size()) {
            return "Error index out of bounds";
        }
        LogEntry error = errors.get(errorIndex);
        return String.format("Timestamp: %d, Level: %s, Logger: %s, Message: %s",
                error.getTimestamp(),
                error.getLevel(),
               // error.getLoggerName(),
                error.getMessage());
    }

    /**
     * Wait for a JavaScript error to appear in console (up to 10 seconds).
     */
    public boolean waitForJSError() {
        for (int i = 0; i < 10; i++) {
            staticWait(1);
            if (hasJavaScriptErrors()) {
                return true;
            }
        }
        return false;
    }
}
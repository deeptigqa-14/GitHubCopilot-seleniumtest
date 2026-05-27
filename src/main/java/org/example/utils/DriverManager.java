package org.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class DriverManager {
    //private static WebDriver driver;
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");

            // Enable logging to capture console errors
            LoggingPreferences loggingPreferences = new LoggingPreferences();
            loggingPreferences.enable(LogType.BROWSER, Level.ALL);
            options.setCapability("goog:loggingPrefs", loggingPreferences);


            WebDriver wd = new ChromeDriver(options);
            driver.set(wd);
           // driver.manage().window().maximize();
        }
        return driver.get();
    }

    public static void closeDriver() {
        WebDriver wd= driver.get();
        if (wd != null) {
            wd.quit();
            driver.remove();
        }
    }

    public static void takeScreenshot(String filePath) throws IOException {
        WebDriver wd= driver.get();
        if (wd != null) {
            // Implement screenshot logic using TakesScreenshot interface
            // Example:
            File screenshot = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filePath));
        }
    }
}
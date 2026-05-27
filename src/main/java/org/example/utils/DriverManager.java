package org.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
}
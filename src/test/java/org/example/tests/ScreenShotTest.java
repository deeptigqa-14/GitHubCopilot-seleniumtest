package org.example.tests;

import org.example.pages.HomePage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class ScreenShotTest {
    // This test class is intended to verify the screenshot functionality of the WebDriver.
    // It will capture a screenshot of the current browser window and save it to a specified location.
    // The test will ensure that the screenshot is successfully taken and saved without any exceptions.
    private WebDriver driver;
    private HomePage homePage;
    @Before
    public void setUp() {
        // Initialization code for the test
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
    }
    @After
    public void tearDown() {
        // Cleanup code for the test
        DriverManager.closeDriver();
    }

    //open homepage and take a screenshot
    @Test
    public void testTakeScreenshot() throws IOException {
        // Navigate to the homepage
        homePage.goToHome();

        // Take a screenshot and save it to a specified location
        String screenshotPath = "screenshots/homepage.png";
        DriverManager.takeScreenshot(screenshotPath);

        // Additional assertions can be added here to verify that the screenshot was taken successfully
    }

}

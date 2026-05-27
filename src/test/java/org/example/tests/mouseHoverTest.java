package org.example.tests;

import org.example.pages.HomePage;
import org.example.pages.MouseHoverPage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class mouseHoverTest {
    private WebDriver driver;
    private HomePage homePage;
    private MouseHoverPage mouseHoverPage;

    @Before
    //it should hover the first image and verify the text appears
    public void setup() {
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
        //homePage.navigateToMouseHoverPage();
        mouseHoverPage = new MouseHoverPage(driver);
    }

    @Test
    //Hover over the first image and verify the text appears
    public void testHoverOverFirstImage() {
        homePage.goToHome();
        homePage.navigateToMouseHoverPage();
        mouseHoverPage.hoverOverImage(1);
        String hoverText = mouseHoverPage.getHoverText(1);
        System.out.println("Hover text for image 1: " + hoverText);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}

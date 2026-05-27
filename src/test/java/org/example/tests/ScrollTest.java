package org.example.tests;

import org.example.pages.HomePage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public class ScrollTest {
    //write a selenium code to scroll at the bottom of the page on the page "https://the-internet.herokuapp.com/infinite_scroll"
    // and validate that new content is loaded after scrolling.

    private WebDriver driver;
    private HomePage homePage;

        @Before
        public void setUp() {
            driver = DriverManager.getDriver();
            homePage = new HomePage(driver);
        }

        @After
        public void tearDown() {
            // Close the browser
           DriverManager.closeDriver();
        }

        public void testInfiniteScroll() {
            homePage.goToHome();
            homePage.navigateToInfiniteScroll();
            // Scroll to the bottom of the page and validate that new content is loaded
            int initialContentCount = driver.findElements(org.openqa.selenium.By.className("jscroll-added")).size();
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            homePage.staticWait(2); // Wait for new content to load
            int newContentCount = driver.findElements(org.openqa.selenium.By.className("jscroll-added")).size();
            assert newContentCount > initialContentCount : "New content should be loaded after scrolling.";
        }

        public void scroll() {
            setUp();
            testInfiniteScroll();
            tearDown();
        }

}

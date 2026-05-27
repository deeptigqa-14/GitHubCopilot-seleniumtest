package org.example.tests;

import org.example.pages.FramesPage;
import org.example.pages.HomePage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class framesTest {
    WebDriver driver;
    HomePage homePage;
    FramesPage framesPage;

    @Before
    public void setUp() {
        // Initialization code here, e.g., driver setup, page object instantiation
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
        framesPage = new FramesPage(driver);
    }

    @Test
    //Test to verify if iframe is present and switch to it, then switch back to main content and copy the content of the iframe and print it in the console
    public void testiFrames() {
        // Navigate to frames page
        homePage.goToHome();
        homePage.navigateToFramesPage();
        homePage.navigateToiFrame();
        // Switch to iframe and verify content
        framesPage.staticWait(5);
        framesPage.switchToiFrame();
        String iframeContent = framesPage.getIFrameContent();
        System.out.println("Iframe Content: " + iframeContent);
        framesPage.switchToDefaultContent();
    }

    @Test
    //Test to verify if the left, middle, right and bottom frames are present and switch to each of them and print the content of each frame in the console
    public void testNestedFrames() {
        // Navigate to frames page
        homePage.goToHome();
        homePage.navigateToFramesPage();
        homePage.navigateToNestedFramesPage();
        // Switch to left frame and print content
        framesPage.switchtoTopFrame();
        framesPage.switchToLeftFrame();
        String leftFrameContent = framesPage.getFrameContent();
        System.out.println("Left Frame Content: " +leftFrameContent);
        framesPage.switchToParentFrame();
        framesPage.switchToMiddleFrame();
        String middleFrameContent = framesPage.getFrameContent();
        System.out.println("Left Frame Content: " +middleFrameContent);

    }



    @After
    public void tearDown() {
        // Cleanup code here, e.g., closing the driver
        DriverManager.closeDriver();
    }


}

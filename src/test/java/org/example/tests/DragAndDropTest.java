package org.example.tests;

import org.example.pages.DragAndDropPage;
import org.example.pages.HomePage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;

import static org.junit.Assert.*;

public class DragAndDropTest {

    //write a selenium automation for drag and drop feature on the page "https://the-internet.herokuapp.com/drag_and_drop".
    // The test should validate that the elements have been swapped after the drag and drop action.

    private WebDriver driver;
    private HomePage homePage;
    private DragAndDropPage dragAndDropPage;

    @Before
     public void setUp() {
         driver = DriverManager.getDriver();
         homePage = new HomePage(driver);
         dragAndDropPage = new DragAndDropPage(driver);
     }

     @After
     public void tearDown() {
         // Close the browser
         DriverManager.closeDriver();
     }

     @Test
     public void testDragAndDrop() {
         // Implementation for drag and drop test case
         homePage.goToHome();
         homePage.navigateToDragAndDrop();
         // Perform drag and drop action here using Actions class
         // Validate that the elements have been swapped after the drag and drop action
         dragAndDropPage.dragAndDropAtoB();
            // Add assertions to verify the swap
         assertTrue("Column A should now contain B", dragAndDropPage.isColumnAText("B"));
         assertTrue("Column B should now contain A", dragAndDropPage.isColumnBText("A"));


     }
}

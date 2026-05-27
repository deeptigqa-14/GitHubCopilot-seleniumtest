package org.example.tests;

import org.example.pages.HomePage;
import org.example.pages.TableDataPage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class testTableData {

    //write a selenium automation test to validate the data in a table on  "https://the-internet.herokuapp.com/tables".
    //create two testcases, one validating the presence of a specific text in table
    //and another test case to ensure that the table is not empty
    private WebDriver driver;
    private HomePage homePage;
    private TableDataPage tableDataPage;

    @Before
    public void setUp() {
        // Initialize WebDriver and page objects
        // driver = new ChromeDriver(); // or any other driver
        driver= DriverManager.getDriver();
        homePage = new HomePage(driver);
        tableDataPage = new TableDataPage(driver);
    }
    @After
    public void tearDown() {
        // Close the browser
        DriverManager.closeDriver();
    }

    // Test case 1: Validate the presence of a specific text in the table
    @Test
    public void testPresenceOfSpecificText() {
        // Implementation for test case 1
        homePage.goToHome();
        homePage.navigateToDataTables();
        // Example: Validate that "John Doe" is present in the first table
        boolean isTextPresent = tableDataPage.isTextPresentInTable(1, "Doe");
        assertTrue("Expected text 'John Doe' should be present in the table", isTextPresent);

    }
    //test 2 : return the list of first and last name from the table and validate that the list is not empty
    @Test
    public void testListOfNames() {
        // Implementation for test case 2
        homePage.goToHome();
        homePage.navigateToDataTables();
        // Example: Get the list of first and last names from the first table
        List<String> names = tableDataPage.getFirstAndLastNames(1);
        assertTrue("The list of names should not be empty", !names.isEmpty());
        System.out.println("Names in the table: " + names);

    }


}

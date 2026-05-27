package org.example.tests;

import org.example.pages.DropdownPage;
import org.example.pages.HomePage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.*;

public class DropdownTest {
    private WebDriver driver;
    private HomePage homePage;
    private DropdownPage dropdownPage;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
        dropdownPage = new DropdownPage(driver);
    }

    @After
    public void tearDown() {
        DriverManager.closeDriver();
    }

    @Test
    public void testSelectOptionByValue() {
        // Navigate to dropdown page
        homePage.goToHome();
        homePage.clickDropdown();

        // Select option 1 by value
        dropdownPage.selectOptionByValue("1");

        // Wait for manual verification
        dropdownPage.staticWait(5);

        // Verify correct option is selected
        assertEquals("Option 1 should be selected", "Option 1", dropdownPage.getSelectedOption());
    }

    @Test
    public void testSelectOptionByVisibleText() {
        // Navigate to dropdown page
        homePage.goToHome();
        homePage.clickDropdown();

        // Select option 2 by visible text
        dropdownPage.selectOptionByVisibleText("Option 2");

        // Wait for manual verification
        dropdownPage.staticWait(5);

        // Verify correct option is selected
        assertEquals("Option 2 should be selected", "Option 2", dropdownPage.getSelectedOption());
    }

    @Test
    public void testSelectOptionByIndex() {
        // Navigate to dropdown page
        homePage.goToHome();
        homePage.clickDropdown();

        // Select option by index (index 2 = Option 1)
        dropdownPage.selectOptionByIndex(1);

        // Wait for manual verification
        dropdownPage.staticWait(5);

        // Verify an option is selected
        assertNotNull("An option should be selected", dropdownPage.getSelectedOption());
    }

    @Test
    public void testDropdownHasTwoOptions() {
        // Navigate to dropdown page
        homePage.goToHome();
        homePage.clickDropdown();

        // Wait for manual verification
        dropdownPage.staticWait(5);

        // Verify dropdown has correct number of options
        int totalOptions = dropdownPage.getTotalOptions();
        assertEquals("Dropdown should have 3 options (including 'Please select an option')",
                3, totalOptions);
    }

    @Test
    public void testSwitchBetweenOptions() {
        // Navigate to dropdown page
        homePage.goToHome();
        homePage.clickDropdown();

        // Select first option
        dropdownPage.selectOptionByValue("1");
        dropdownPage.staticWait(2);
        assertEquals("Option 1 should be selected", "Option 1", dropdownPage.getSelectedOption());

        // Switch to second option
        dropdownPage.selectOptionByValue("2");

        // Wait for manual verification
        dropdownPage.staticWait(5);

        // Verify second option is now selected
        assertEquals("Option 2 should be selected", "Option 2", dropdownPage.getSelectedOption());
    }
}
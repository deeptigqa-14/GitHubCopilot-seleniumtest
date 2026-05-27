package org.example.tests;

import org.example.pages.CheckboxPage;
import org.example.pages.HomePage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.*;

public class CheckboxTest {
    private WebDriver driver;
    private HomePage homePage;
    private CheckboxPage checkboxPage;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
        checkboxPage = new CheckboxPage(driver);
    }

    @After
    public void tearDown() {
        DriverManager.closeDriver();
    }

    @Test
    public void testCheckSingleCheckbox() {
        // Navigate to checkboxes page
        homePage.goToHome();
        if(!homePage.isCheckboxesLinkVisible()) {
            fail("Checkboxes link is not visible on the home page");
        }
        homePage.clickCheckboxes();

        // Verify total checkboxes count
        int totalCheckboxes = checkboxPage.getTotalCheckboxCount();
        assertEquals("Should have 2 checkboxes", 2, totalCheckboxes);

        // Check the first checkbox
        checkboxPage.checkCheckboxByIndex(0);

        // Wait for manual verification
        checkboxPage.staticWait(5);

        // Verify checkbox is checked
        assertTrue("First checkbox should be checked", checkboxPage.isCheckboxCheckedByIndex(0));
    }

    @Test
    public void testUncheckSingleCheckbox() {
        // Navigate to checkboxes page
        homePage.goToHome();
        homePage.clickCheckboxes();

        // Uncheck the second checkbox
        checkboxPage.uncheckCheckboxByIndex(1);

        // Wait for manual verification
        checkboxPage.staticWait(5);

        // Verify checkbox is unchecked
        assertFalse("Second checkbox should be unchecked", checkboxPage.isCheckboxCheckedByIndex(1));
    }

    @Test
    public void testCheckAllCheckboxes() {
        // Navigate to checkboxes page
        homePage.goToHome();
        homePage.clickCheckboxes();

        // Check all checkboxes
        checkboxPage.checkAllCheckboxes();

        // Wait for manual verification
        checkboxPage.staticWait(5);

        // Verify all checkboxes are checked
        int totalCheckboxes = checkboxPage.getTotalCheckboxCount();
        for (int i = 0; i < totalCheckboxes; i++) {
            assertTrue("Checkbox at index " + i + " should be checked",
                    checkboxPage.isCheckboxCheckedByIndex(i));
        }
    }

    @Test
    public void testUncheckAllCheckboxes() {
        // Navigate to checkboxes page
        homePage.goToHome();
        homePage.clickCheckboxes();

        // First check all, then uncheck all
        checkboxPage.checkAllCheckboxes();
        checkboxPage.uncheckAllCheckboxes();

        // Wait for manual verification
        checkboxPage.staticWait(5);

        // Verify all checkboxes are unchecked
        int totalCheckboxes = checkboxPage.getTotalCheckboxCount();
        for (int i = 0; i < totalCheckboxes; i++) {
            assertFalse("Checkbox at index " + i + " should be unchecked",
                    checkboxPage.isCheckboxCheckedByIndex(i));
        }
    }
}
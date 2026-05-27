package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    private static final String BASE_URL = "https://the-internet.herokuapp.com";

    @FindBy(linkText = "Form Authentication")
    private WebElement formAuthLink;

    @FindBy(linkText = "Checkboxes")
    private WebElement checkboxesLink;

    @FindBy(linkText = "Dropdown")
    private WebElement dropdownLink;

    @FindBy(linkText = "JavaScript Alerts")
    private WebElement alertsLink;

    @FindBy(linkText = "Hovers")
    private WebElement mouseHoverLink;

    @FindBy(linkText = "Frames")
    private WebElement framesLink;

    @FindBy(linkText = "Nested Frames")
    private WebElement nestedFramesLink;
    @FindBy(linkText = "iFrame")
    private WebElement iFramesLink;

    @FindBy(linkText = "Sortable Data Tables")
    private WebElement tablesLink;

    @FindBy(linkText = "JavaScript onload event error")
    private WebElement jsErrorLink;

    @FindBy(linkText = "Drag and Drop")
    private WebElement dragAndDropLink;

    @FindBy(linkText = "Infinite Scroll")
    private WebElement infiniteScrollLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void goToHome() {
        navigateTo(BASE_URL);
    }

    public void clickFormAuthentication() {
        formAuthLink.click();
    }

    public void clickCheckboxes() {
        checkboxesLink.click();
    }

    public void clickDropdown() {
        dropdownLink.click();
    }

    public void clickAlerts() {
        alertsLink.click();
    }
    public boolean isCheckboxesLinkVisible() {
        return checkboxesLink.isDisplayed();
    }
    public void navigateToMouseHoverPage() {
        mouseHoverLink.click();
    }
    public void navigateToNestedFramesPage() {
        nestedFramesLink.click();
    }

    public void navigateToiFrame() {
        iFramesLink.click();
    }
    public void navigateToFramesPage() {
            framesLink.click();
    }
    public void  navigateToDataTables() {
        tablesLink.click();
    }

    public void navigateToJSErrorPage() {
            jsErrorLink.click();
    }

    public void navigateToDragAndDrop() {
        dragAndDropLink.click();
    }

    public void navigateToInfiniteScroll() {
        infiniteScrollLink.click();
    }
}
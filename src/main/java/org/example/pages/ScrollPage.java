package org.example.pages;

public class ScrollPage extends BasePage {
    public ScrollPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }

    public void scrollToElement(org.openqa.selenium.WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        staticWait(1); // Optional: wait for the scroll to complete
    }

}

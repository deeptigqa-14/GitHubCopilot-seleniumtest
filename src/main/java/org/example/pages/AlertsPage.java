package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Alert;

public class AlertsPage extends BasePage {
    @FindBy(css = "button[onclick='jsAlert()']")
    private WebElement jsAlertButton;

    @FindBy(xpath = "//button[text()='Click for JS Confirm']")
    private WebElement jsConfirmButton;

    @FindBy(xpath = "//button[text()='Click for JS Prompt']")
    private WebElement jsPromptButton;

    @FindBy(id = "result")
    private WebElement resultText;

    public AlertsPage(WebDriver driver) {
        super(driver);
    }

    public void clickJSAlertButton() {
        jsAlertButton.click();
    }

    public void clickJSConfirmButton() {
        jsConfirmButton.click();
    }

    public void clickJSPromptButton() {
        jsPromptButton.click();
    }

    public void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void dismissAlert() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    public void sendKeysToAlert(String text) {
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(text);
    }

    public String getAlertText() {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public String getResultText() {
        return resultText.getText();
    }

    public boolean isResultDisplayed() {
        return resultText.isDisplayed();
    }
}
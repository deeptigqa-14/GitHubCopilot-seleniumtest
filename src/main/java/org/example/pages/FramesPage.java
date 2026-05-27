package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FramesPage extends BasePage {
    public FramesPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(name = "frame-top")
    private WebElement topFrame;
    @FindBy(name="frame-left")
    private WebElement leftFrame;
    @FindBy (name="frame-middle")
    private WebElement middleFrame;
    @FindBy(name="frame-right")
    private WebElement rightFrame;
    @FindBy (name="frame-bottom")
    private WebElement bottomFrame;

    @FindBy(id="mce_0_ifr")
    private WebElement myIFrame;
    @FindBy(id="tinymce")
    private WebElement iFrameContent;

    public void switchToLeftFrame() {
        driver.switchTo().frame(leftFrame);
    }

    public void switchToMiddleFrame() {
        driver.switchTo().frame(middleFrame);
    }
    public void switchToRightFrame() {
        driver.switchTo().frame(rightFrame);
    }

    public void switchToBottomFrame() {
        driver.switchTo().frame(bottomFrame);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        staticWait(2);
    }
    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }
    public void switchToiFrame() {
        driver.switchTo().frame(myIFrame);
    }

    public String getIFrameContent() {
        return iFrameContent.getText();
    }

    public void switchtoTopFrame() {
        driver.switchTo().frame(topFrame);
    }

    public String getFrameContent() {
        return  driver.findElement(By.tagName("body")).getText();
    }


}
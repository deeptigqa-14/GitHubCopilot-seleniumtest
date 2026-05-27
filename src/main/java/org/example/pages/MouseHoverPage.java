package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class MouseHoverPage extends BasePage {

    public MouseHoverPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(xpath="//div[@class='figure'][1]")
    private WebElement image1;
    @FindBy(xpath="//div[@class='figure'][2]")
    private WebElement image2;
    @FindBy(xpath="//div[@class='figure'][3]")
    private WebElement image3;
    @FindBy(xpath="//div[@class='figure'][1]/div/h5")
    private WebElement text1;
    @FindBy(xpath="//div[@class='figure'][2]/div/h5")
    private WebElement text2;
    @FindBy(xpath="//div[@class='figure'][3]/div/h5")
    private WebElement text3;

    public void hoverOverImage(int imageIndex) {
        // Implement the logic to hover over the specified image
        // This may involve using Actions class to perform the hover action
        Actions actions = new Actions(driver);
        WebElement imageToHover;
        switch (imageIndex) {
            case 1:
                imageToHover = image1;
                actions.moveToElement(imageToHover).perform();
                break;
            case 2:
                imageToHover = image2;
                actions.moveToElement(imageToHover).perform();
                break;
            case 3:
                imageToHover = image3;
                actions.moveToElement(imageToHover).perform();
                break;
            default:
                throw new IllegalArgumentException("Invalid image index: " + imageIndex);
        }
    }
    public String getHoverText(int imageIndex) {
        // Implement the logic to retrieve the text that appears when hovering over the specified image
            WebElement textElement;
        switch (imageIndex) {
            case 1:
                textElement = text1;
                break;
            case 2:
                textElement = text2;
                break;
            case 3:
                textElement = text3;
                break;
            default:
                throw new IllegalArgumentException("Invalid image index: " + imageIndex);
        }
        return textElement.getText();
    }
}

package org.example.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class DragAndDropPage extends BasePage {
    public DragAndDropPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }
    @FindBy(id="column-a")
    private WebElement columnA;
    @FindBy(id="column-b")
    private WebElement columnB;

    public void dragAndDropAtoB() {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(columnA, columnB).perform();
    }

    public boolean isColumnAText(String expectedText) {
        return columnA.getText().equals(expectedText);
    }
    public boolean isColumnBText(String expectedText) {
        return columnB.getText().equals(expectedText);
    }

}

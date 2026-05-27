package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class DropdownPage extends BasePage {
    @FindBy(id = "dropdown")
    private WebElement dropdownElement;

    public DropdownPage(WebDriver driver) {
        super(driver);
    }

    public void selectOptionByValue(String value) {
        Select select = new Select(dropdownElement);
        select.selectByValue(value);
    }

    public void selectOptionByVisibleText(String text) {
        Select select = new Select(dropdownElement);
        select.selectByVisibleText(text);
    }

    public void selectOptionByIndex(int index) {
        Select select = new Select(dropdownElement);
        select.selectByIndex(index);
    }

    public String getSelectedOption() {
        Select select = new Select(dropdownElement);
        return select.getFirstSelectedOption().getText();
    }

    public int getTotalOptions() {
        Select select = new Select(dropdownElement);
        return select.getOptions().size();
    }
}
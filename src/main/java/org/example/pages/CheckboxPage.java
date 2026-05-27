package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class CheckboxPage extends BasePage {
    @FindBy(css = "input[type='checkbox']")
    private List<WebElement> checkboxes;

    public CheckboxPage(WebDriver driver) {
        super(driver);
    }

    public void checkCheckboxByIndex(int index) {
        if (index < checkboxes.size()) {
            WebElement checkbox = checkboxes.get(index);
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    public void uncheckCheckboxByIndex(int index) {
        if (index < checkboxes.size()) {
            WebElement checkbox = checkboxes.get(index);
            if (checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    public boolean isCheckboxCheckedByIndex(int index) {
        if (index < checkboxes.size()) {
            return checkboxes.get(index).isSelected();
        }
        return false;
    }

    public int getTotalCheckboxCount() {
        return checkboxes.size();
    }

    public void checkAllCheckboxes() {
        for (WebElement checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    public void uncheckAllCheckboxes() {
        for (WebElement checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }
}
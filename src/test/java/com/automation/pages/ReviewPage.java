package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReviewPage extends BasePage {

    @FindBy(xpath = "//div[@class='inventory_item_name']")
    WebElement itemName;

    @FindBy(id = "finish")
    WebElement finishBtn;

    public ReviewPage(WebDriver driver) {
        super(driver);
    }

    public String getItemName() {
        return itemName.getText();
    }

    public void clickFinishBtn() {
        finishBtn.click();
    }
}

package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderConfirmationPage extends BasePage {

    @FindBy(xpath = "//h2[@class='complete-header']")
    WebElement successMsg;

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String getSuccessMessage() {
        return successMsg.getText();
    }
}

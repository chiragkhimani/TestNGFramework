package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    @FindBy(id = "checkout")
    WebElement checkoutBtn;

    @FindBy(xpath = "//div[@class='inventory_item_name']")
    WebElement itemName;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getItemName() {
        return itemName.getText();
    }

    public void clickCheckoutBtn() {
        checkoutBtn.click();
    }
}

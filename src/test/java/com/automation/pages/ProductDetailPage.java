package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailPage extends BasePage{

    @FindBy(xpath = "//div[@data-test=\"inventory-item-name\"]")
    WebElement productDetailsPageTitle;

    @FindBy(xpath = "//button[contains(@class,'btn_inventory') and normalize-space()='Add to cart']")
    WebElement addToCartBtn;

    @FindBy(css = ".shopping_cart_badge")
    WebElement shoppingCartBadge;

    @FindBy(css = ".shopping_cart_link")
    WebElement cartIcon;

    public ProductDetailPage(WebDriver driver){
        super(driver);
    }

    public String getProductDetailPageTitle(){
        return productDetailsPageTitle.getText();
    }

    public void clickOnAddToCartBtn(){
        addToCartBtn.click();
    }

    public String getShoppingCartText(){
        return shoppingCartBadge.getText();
    }

    public void clickOnCartIcon(){
        cartIcon.click();
    }









}

package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage{

    public HomePage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//select[@class='product_sort_container']")
    WebElement filterDropdown;

    @FindBy(css = ".inventory_item_name")
    List<WebElement> productNameElements;

    @FindBy(css = ".inventory_item_price")
    List<WebElement> productPriceElements;

    public boolean isHomePageDisplayed(){
        return filterDropdown.isDisplayed();
    }

    public void sortByNameAToZ() {
        new Select(filterDropdown).selectByValue("az");
    }

    public void sortByPriceLowToHigh() {
        new Select(filterDropdown).selectByValue("lohi");
    }

    public List<String> getAllProductNames() {
        return productNameElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getAllProductPrices() {
        return productPriceElements.stream()
                .map(WebElement::getText)
                .map(t -> t.replace("$", "").trim())
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    public void clickOnProductName(String productName){
        String productLinkXpath = "//div[contains(@class,'inventory_item_name') and normalize-space(text())='" + productName + "']";
        WebElement productLink = driver.findElement(By.xpath(productLinkXpath));
        productLink.click();
    }


}

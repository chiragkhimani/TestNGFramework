package com.automation.test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {

    private static final String PRODUCT_BACKPACK = "Sauce Labs Backpack";
    private static final String PRODUCT_BIKE_LIGHT = "Sauce Labs Bike Light";

    @Test(description = "Positive: Add a product to cart and verify it appears in cart")
    public void addSingleItemToCart_shouldShowInCart() {
        loginPage.doLogin();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed after login");

        homePage.clickOnProductName(PRODUCT_BACKPACK);
        Assert.assertEquals(
                productDetailPage.getProductDetailPageTitle(),
                PRODUCT_BACKPACK,
                "Product detail page title should match selected product"
        );

        productDetailPage.clickOnAddToCartBtn();
        Assert.assertEquals(productDetailPage.getShoppingCartText(), "1", "Cart badge should show 1 item");

        productDetailPage.clickOnCartIcon();
        Assert.assertEquals(cartPage.getItemName(), PRODUCT_BACKPACK, "Item in cart should match selected product");
    }

    @Test(description = "Edge: Add multiple products and verify cart count and items")
    public void addMultipleItemsToCart_shouldShowCorrectCountAndItems() {
        loginPage.doLogin();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed after login");

        addProductToCartFromHome(PRODUCT_BACKPACK);
        addProductToCartFromHome(PRODUCT_BIKE_LIGHT);

        Assert.assertEquals(getCartBadgeCount(), "2", "Cart badge should show 2 items");

        openCartFromHeader();
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_item"));
        Assert.assertEquals(cartItems.size(), 2, "Cart should contain 2 items");

        List<String> itemNames = driver.findElements(By.cssSelector(".cart_item .inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertTrue(itemNames.contains(PRODUCT_BACKPACK), "Cart should contain Sauce Labs Backpack");
        Assert.assertTrue(itemNames.contains(PRODUCT_BIKE_LIGHT), "Cart should contain Sauce Labs Bike Light");
    }

    @Test(description = "Positive: Remove item from cart and verify cart becomes empty")
    public void removeItemFromCart_shouldRemoveAndClearBadge() {
        loginPage.doLogin();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed after login");

        addProductToCartFromHome(PRODUCT_BACKPACK);
        Assert.assertEquals(getCartBadgeCount(), "1", "Cart badge should show 1 item");

        openCartFromHeader();

        WebElement removeButton = driver.findElement(By.cssSelector("button[id^='remove-'], button[data-test^='remove-']"));
        removeButton.click();

        Assert.assertEquals(driver.findElements(By.cssSelector(".cart_item")).size(), 0, "Cart should be empty after removing the item");
        Assert.assertFalse(isCartBadgePresent(), "Cart badge should not be displayed when cart is empty");
    }

    @Test(description = "Edge: Proceed to checkout from an empty cart should navigate to checkout info page")
    public void checkoutFromEmptyCart_shouldNavigateToCheckoutInfo() {
        loginPage.doLogin();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed after login");

        openCartFromHeader();
        Assert.assertEquals(driver.findElements(By.cssSelector(".cart_item")).size(), 0, "Precondition: cart should be empty");

        cartPage.clickCheckoutBtn();
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Checkout page should be displayed");
        Assert.assertEquals(
                checkoutPage.getCheckoutPageTitle(),
                "Checkout: Your Information",
                "Checkout page title should match expected"
        );
    }

    @Test(description = "Negative: Accessing cart without login should redirect to login page")
    public void cartWithoutLogin_shouldRedirectToLogin() {
        driver.get("https://www.saucedemo.com/cart.html");
        Assert.assertTrue(isLoginPageDisplayed(), "User should be redirected to login page when accessing cart without login");
    }

    private void addProductToCartFromHome(String productName) {
        String addButtonXpath = "//div[contains(@class,'inventory_item')]" +
                "[.//div[contains(@class,'inventory_item_name') and normalize-space(text())='" + productName + "']]" +
                "//button[starts-with(@id,'add-to-cart-') or starts-with(@data-test,'add-to-cart-')]";
        driver.findElement(By.xpath(addButtonXpath)).click();
    }

    private void openCartFromHeader() {
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();
    }

    private boolean isCartBadgePresent() {
        return !driver.findElements(By.cssSelector(".shopping_cart_badge")).isEmpty();
    }

    private String getCartBadgeCount() {
        return driver.findElement(By.cssSelector(".shopping_cart_badge")).getText();
    }

    private boolean isLoginPageDisplayed() {
        try {
            return driver.findElement(By.id("login-button")).isDisplayed()
                    && driver.findElement(By.id("user-name")).isDisplayed()
                    && driver.findElement(By.id("password")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

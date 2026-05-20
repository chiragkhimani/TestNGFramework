package com.automation.test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoClassTest extends BaseTest {

    @Test
    public void orderPlacementTest() {
        // Step 1: Login
        loginPage.doLogin();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed after login");

        // Step 2: Select product
        String productName = "Sauce Labs Backpack";
        homePage.clickOnProductName(productName);
        Assert.assertEquals(productDetailPage.getProductDetailPageTitle(), productName);

        // Step 3: Add product to cart
        productDetailPage.clickOnAddToCartBtn();
        Assert.assertEquals(productDetailPage.getShoppingCartText(), "1", "Cart should have 1 item");

        // Step 4: Navigate to cart and verify item
        productDetailPage.clickOnCartIcon();
        Assert.assertEquals(cartPage.getItemName(), productName, "Item in cart should match selected product");

        // Step 5: Proceed to checkout
        cartPage.clickCheckoutBtn();

        // Step 6: Fill checkout information
        checkoutPage.fillCheckoutForm("Test", "Automation", "411012");
        checkoutPage.clickContinueBtn();

        // Step 7: Verify item on review page
        Assert.assertEquals(reviewPage.getItemName(), productName, "Item on review page should match selected product");

        // Step 8: Complete order
        reviewPage.clickFinishBtn();
        String successMessage = orderConfirmationPage.getSuccessMessage();
        Assert.assertNotNull(successMessage, "Success message should be displayed");
    }

}

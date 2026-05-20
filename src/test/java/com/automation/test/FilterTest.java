package com.automation.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterTest extends BaseTest {

    @Test(description = "Verify products are sorted by Price (low to high)")
    public void verifyProductsSortedByPriceLowToHigh() {
        loginPage.doLogin();
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed after login");

        homePage.sortByPriceLowToHigh();

        List<Double> actualPrices = homePage.getAllProductPrices();
        Assert.assertTrue(actualPrices.size() > 1, "Expected multiple products to validate sorting");

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        Assert.assertEquals(actualPrices, expectedPrices, "Product prices should be sorted from low to high");
    }
}


package com.os8580;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.os8580.pages.GooglePage;
import com.os8580.pages.Pages;
import com.os8580.pages.RozetkaPage;
import com.os8580.utils.PageFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;


import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.*;

public class RozetkaTest {
    private static PageFactory pageFactory;

    @BeforeAll
    public static void setupDriver() {
        pageFactory = new PageFactory();
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"}) // Add other browsers as needed
    public void rozetkaSearch(String browser) {
        // Configure the browser
        Configuration.browser = browser;

        // Get page instances from the factory
        GooglePage googlePage = (GooglePage) pageFactory.get(Pages.GOOGLE);
        RozetkaPage rozetkaPage = (RozetkaPage) pageFactory.get(Pages.ROZETKA);

        // Step 1: Perform a search on Google
        googlePage.loadPage();
        googlePage.acceptCookiesIfPresent();
        googlePage.searchFor("rozetka com ua");

        ElementsCollection searchResults = googlePage.getSearchResults();
        searchResults.shouldHave(CollectionCondition.sizeGreaterThan(0));

        searchResults.first().click();

        // Step 2: Perform a search on the Rozetka page
        rozetkaPage.searchFor("Iphone");
        ElementsCollection searchResultsRozetka = rozetkaPage.getSearchResults();
        searchResultsRozetka.shouldHave(CollectionCondition.sizeGreaterThan(0));

        // Step 3: Get details of the selected product
        Map<String, Object> selectedProductDetails = new HashMap<>();
        selectedProductDetails.put("name", rozetkaPage.getProductName());
        String productPrice = rozetkaPage.getProductPrice().replaceAll("\\D", "");
        selectedProductDetails.put("price", Integer.parseInt(productPrice));

        // Step 4: Add the product to the cart and open the cart
        rozetkaPage.clickAddToCartButton();
        rozetkaPage.openCart();

        // Step 5: Get details of the product in the cart
        Map<String, Object> productDetailsInCart = new HashMap<>();
        productDetailsInCart.put("name", rozetkaPage.getCartProductName());
        String productPriceInCart = rozetkaPage.getCartProductPrice().replaceAll("\\D+", "");
        productDetailsInCart.put("price", Integer.parseInt(productPriceInCart));

        // Step 6: Verify that product details in the cart match the selected product details
        assertEquals(selectedProductDetails, productDetailsInCart, "Product details in the cart do not match the selected product details.");

        // Step 7: Get initial quantity and price of a single product
        int initialQuantity = rozetkaPage.getProductQuantity();
        int singleProductPrice = rozetkaPage.getSingleProductPrice();

        // Step 8: Increase product quantity by one
        rozetkaPage.increaseProductQuantityByOne();
        int increasedQuantity = rozetkaPage.getProductQuantity();
        int increasedPrice = rozetkaPage.getSingleProductPrice() * rozetkaPage.getProductQuantity();

        // Step 9: Verify that product quantity was increased by one and that total product price was increased proportionally
        assertEquals(initialQuantity + 1, increasedQuantity, "Product quantity did not increase by 1.");
        assertTrue(increasedPrice > initialQuantity * singleProductPrice, "Product price did not increase after adding another instance.");

        // Step 10: Decrease product quantity back to the initial
        rozetkaPage.decreaseProductQuantityByOne();
        int decreasedQuantity = rozetkaPage.getProductQuantity();
        int decreasedPrice = rozetkaPage.getSingleProductPrice() * rozetkaPage.getProductQuantity();

        // Step 11: Verify that product quantity was decreased by one and that total product price was decreased proportionally
        assertEquals(initialQuantity, decreasedQuantity, "Product quantity did not decrease by 1.");
        assertEquals(initialQuantity * singleProductPrice, decreasedPrice, "Product price did not return to the initial value after removing an instance.");

        // Step 12: Increase product quantity by three
        rozetkaPage.increaseProductQuantityByOne();
        rozetkaPage.increaseProductQuantityByOne();
        rozetkaPage.increaseProductQuantityByOne();
        int increasedQuantityByThree = rozetkaPage.getProductQuantity();
        int increasedPriceByThree = rozetkaPage.getSingleProductPrice() * rozetkaPage.getProductQuantity();

        // Step 13: Verify that product quantity was increased by three and that total product price was increased proportionally
        assertEquals(4, increasedQuantityByThree);
        assertEquals(singleProductPrice * increasedQuantityByThree, increasedPriceByThree);

        // Step 14: Set product quantity to 7
        rozetkaPage.setProductQuantity(7);
        int setQuantity = rozetkaPage.getProductQuantity();
        int setPrice = rozetkaPage.getSingleProductPrice() * rozetkaPage.getProductQuantity();

        // Step 15: Verify that product quantity was set to 7 and that total product price was updated accordingly
        assertEquals(7, setQuantity);
        assertEquals(singleProductPrice * 7, setPrice);
    }

    @AfterEach
    public void tearDown() {
        // Tear down the driver after the test suite is finished
        pageFactory.tearDown();
    }
}

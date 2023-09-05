package com.os8580.pages;

import com.codeborne.selenide.*;
import com.codeborne.selenide.impl.CollectionSource;
//import com.os8580.pages.AbstractPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RozetkaPage extends AbstractPage {

    private static final String URL = "https://rozetka.com.ua/";

    public RozetkaPage() {
        super(URL);
    }

    // Define the search field element
    private SelenideElement searchField = $(byName("search"));

    /**
     * Perform a search action on the website.
     *
     * @param text The text to search for.
     */
    public void searchFor(String text) {
        searchField.val(text).pressEnter();
    }

    /**
     * Get a collection of search results.
     *
     * @return A collection of search results.
     */
    public ElementsCollection getSearchResults() {
        // Get the list of search results on the page
        return $$(".goods-tile").filter(visible);
    }

    /**
     * Get the name of the first product in the search results.
     *
     * @return The name of the first product.
     */
    public String getProductName() {
        // Get the name of the first product in the search results
        SelenideElement firstProduct = getSearchResults().get(0);
        return firstProduct.$(".goods-tile__title").getText();
    }

    /**
     * Get the price of the first product in the search results.
     *
     * @return The price of the first product.
     */
    public String getProductPrice() {
        // Get the price of the first product in the search results
        SelenideElement firstProduct = getSearchResults().get(0);
        return firstProduct.$(".goods-tile__price").getText();
    }

    /**
     * Click the "Add to Cart" button for the first product in the search results.
     */
    public void clickAddToCartButton() {
        ElementsCollection searchResults = getSearchResults();
        searchResults.shouldHave(CollectionCondition.sizeGreaterThan(0));
        SelenideElement firstProduct = searchResults.first();
        SelenideElement addToCartButton = firstProduct.$("html > body > app-root > div > div > rz-category > div > main > rz-catalog > div > div > section > rz-grid > ul > li:nth-of-type(1) > rz-catalog-tile > app-goods-tile-default > div > div:nth-of-type(2) > div:nth-of-type(4) > div:nth-of-type(2) > app-buy-button > button");
        addToCartButton.shouldBe(visible).shouldBe(enabled).click();

        // Wait for the cart badge to indicate one item
        $$(".badge").findBy(text("1")).should(exist);
    }

    /**
     * Open the shopping cart.
     */
    public void openCart() {
        // Open the cart by clicking on the cart icon
        $(byCssSelector("li[class*='item--cart']")).click();
    }

    /**
     * Get the name of the product in the shopping cart.
     *
     * @return The name of the product in the cart.
     */
    public String getCartProductName() {
        return $(".cart-product__title").getText();
    }

    /**
     * Get the price of the product in the shopping cart.
     *
     * @return The price of the product in the cart.
     */
    public String getCartProductPrice() {
        return $(".cart-product__price").shouldBe(Condition.visible).getText();
    }

    /**
     * Get the quantity of the product in the shopping cart.
     *
     * @return The quantity of the product in the cart.
     */
    public int getProductQuantity() {
        String quantityText = $("[data-testid='cart-counter-input']").shouldBe(visible).getValue();
        return Integer.parseInt(quantityText);
    }

    /**
     * Get the price of a single product in the shopping cart.
     *
     * @return The price of a single product in the cart.
     */
    public int getSingleProductPrice() {
        String productPriceText = $("div[class='cart-receipt__sum-price']").shouldBe(visible).getText().replaceAll("\\D+", "");
        return Integer.parseInt(productPriceText) / getProductQuantity();
    }

    // Helper method to wait for the cart total price to change
    private void waitForCartTotalPriceToChange(String initialPrice) {
        $("div[class*='sum-price']").shouldHave(not(text(initialPrice)), Duration.ofSeconds(10));
    }

    // Get the total price of the products in the cart
    private String getCartTotalPrice() {
        return $("div[class*='sum-price']").getText();
    }

    /**
     * Increase the quantity of the product in the cart by one.
     */
    public void increaseProductQuantityByOne() {
        String initialPrice = getCartTotalPrice();
        SelenideElement quantityIncreaseButton = $("button[data-testid='cart-counter-increment-button']");
        quantityIncreaseButton.click();
        waitForCartTotalPriceToChange(initialPrice);
    }

    /**
     * Decrease the quantity of the product in the cart by one.
     */
    public void decreaseProductQuantityByOne() {
        String initialPrice = getCartTotalPrice();
        SelenideElement quantityDecreaseButton = $("button[data-testid='cart-counter-decrement-button']");
        quantityDecreaseButton.click();
        waitForCartTotalPriceToChange(initialPrice);
    }

    /**
     * Set the quantity of the product in the cart to a specific value.
     *
     * @param quantity The desired quantity.
     */
    public void setProductQuantity(int quantity) {
        SelenideElement quantityInput = $("input[data-testid='cart-counter-input']");
        String initialQuantity = quantityInput.getValue();
        quantityInput.clear();
        quantityInput.sendKeys(Integer.toString(quantity));

        // Wait for the new quantity to be reflected in the input box
        quantityInput.shouldNotHave(value(initialQuantity), Duration.ofSeconds(30));

        String initialPrice = getCartTotalPrice();

        // Wait for the cart total price to change from the initial price
        $("div[class*='sum-price']").shouldNotHave(text(initialPrice), Duration.ofSeconds(10));
    }
}

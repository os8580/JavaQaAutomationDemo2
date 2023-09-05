package com.os8580.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.os8580.pages.AbstractPage;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Page object for Google search page.
 */
public class GooglePage extends AbstractPage {

    private static final String URL = "https://google.com/";

    /**
     * Constructs a new GooglePage object by calling super constructor with the URL.
     */
    public GooglePage() {
        super(URL);
    }

    // The search input field on Google's main page
    private SelenideElement searchField = $(byName("q"));

    /**
     * Enters the given text into the search field and presses Enter to start the search.
     *
     * @param text the text to search for
     */
    public void searchFor(String text) {
        searchField.val(text).pressEnter();
    }

    /**
     * Checks for the presence of cookies acceptance button and clicks it if present.
     */
    public void acceptCookiesIfPresent() {
        // We look for buttons on the page
        ElementsCollection buttons = $$(byTagName("button"));

        // We assume that if there are 5 buttons, it might indicate the presence of a cookie consent banner
        if (buttons.size() == 5) {
            // We click the 4th button to accept cookies
            buttons.get(3).click();
        }
    }

    /**
     * Gets the search results on the current page.
     *
     * @return an ElementsCollection containing all the search result items that are visible
     */
    public ElementsCollection getSearchResults() {
        // We get all search result items on the page
        ElementsCollection elements = $$(byXpath("//h3/.."));

        // We only keep the elements that are visible
        return elements.filter(visible);
    }
}
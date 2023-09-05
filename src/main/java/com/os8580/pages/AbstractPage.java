package com.os8580.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Abstract base class for all pages in the application.
 */
public abstract class AbstractPage {
    private final String URL;

    /**
     * Constructs a new page object with the specified URL.
     *
     * @param URL the URL of this page
     */
    public AbstractPage(String URL) {
        this.URL = URL;
    }

    /**
     * Loads the page by navigating to its URL.
     */
    public void loadPage() {
        open(URL);
    }

    /**
     * Checks if the current page has the same URL as this page object.
     *
     * @return true if the current page URL matches this page object's URL, otherwise false
     */
    public boolean isPageLoaded() {
        return com.codeborne.selenide.WebDriverRunner.url().equals(URL);
    }

    /**
     * Checks if the specified text is present anywhere on the current page.
     *
     * @param text the text to check for
     * @return true if the text is found, otherwise false
     */
    public boolean isTextPresent(String text) {
        SelenideElement bodyElement = $("body");
        return bodyElement.has(text(text));
    }
}
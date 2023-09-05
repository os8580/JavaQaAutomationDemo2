package com.os8580.utils;

import com.codeborne.selenide.Selenide;
import com.os8580.pages.AbstractPage;
import com.os8580.pages.GooglePage;
import com.os8580.pages.Pages;
import com.os8580.pages.RozetkaPage;

public class PageFactory {

    /**
     * Get an instance of the requested page based on the provided enum value.
     *
     * @param pages The enum value representing the requested page.
     * @return An instance of the requested page.
     */
    public AbstractPage get(Pages pages) {
        // Create and return an instance of the requested page based on the provided enum value
        switch (pages) {
            case GOOGLE:
                return Selenide.page(GooglePage.class);
            case ROZETKA:
                return Selenide.page(RozetkaPage.class);
            default:
                throw new RuntimeException("Unsupported page requested");
        }
    }

    /**
     * Teardown method to close the Selenide WebDriver.
     */
    public void tearDown() {
        // Close the Selenide WebDriver to clean up resources
        Selenide.closeWebDriver();
    }
}

package com.solvd.laba.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;

public class CheckoutCompletePage extends BasePage {

    @FindBy(className = "complete-header")
    private ExtendedWebElement orderCompleteMessage;

    @FindBy(className = "title")
    private ExtendedWebElement completeTitle;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(completeTitle);
    }

    public boolean isOrderCompleteMessageDisplayed() {
        return orderCompleteMessage.getText().equals("Thank you for your order!");
    }
}

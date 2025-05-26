package com.solvd.laba.web.components;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;

public class CartItemComponent extends AbstractUIObject {

    @FindBy(xpath = ".//div[contains(@class, 'inventory_item_name')]")
    private ExtendedWebElement productName;

    @FindBy(xpath = ".//button[contains(@id, 'remove')]")
    private ExtendedWebElement removeButton;

    public CartItemComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getProductName() {
        return productName.getText().trim();
    }

    public void clickRemoveButton() {
        removeButton.click();
    }
}

package com.solvd.laba.web.components;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;

public class ProductCardComponent extends AbstractUIObject {

    @FindBy(xpath = ".//div[contains(@class, 'inventory_item_name')]")
    private ExtendedWebElement productName;

    @FindBy(xpath = ".//div[contains(@class, 'inventory_item_price')]")
    private ExtendedWebElement productPrice;

    @FindBy(xpath = ".//button[contains(@class, 'btn_inventory')]")
    private ExtendedWebElement addToCartButton;

    public ProductCardComponent (WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getProductName() {
        return productName.getText().trim();
    }

    public String getProductPrice() {
        return productPrice.getText().trim();
    }

    public void clickAddToCart() {
        addToCartButton.click();
    }

    public void clickOnProductName() {
        productName.click();
    }

    public void removeFromCart() {
        addToCartButton.click();
    }

    public boolean isAddToCartVisible() {
        return addToCartButton.isElementPresent();
    }
}

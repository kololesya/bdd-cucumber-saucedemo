package com.solvd.laba.web.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;

import com.solvd.laba.web.components.HeaderMenuComponent;
import com.solvd.laba.web.components.ProductCardComponent;

public class ProductsPage extends BasePage {

    @FindBy(className = "primary_header")
    private HeaderMenuComponent primaryHeader;

    @FindBy(className = "inventory_item")
    private List<ProductCardComponent> inventoryItems;

    @FindBy(className = "product_sort_container")
    private ExtendedWebElement sortingDropdown;

    @FindBy(xpath = "//div[@role='dialog']//button[contains(text(),'OK')]")
    private ExtendedWebElement chromeAlertOkButton;

    public void closeChromePasswordAlertIfPresent() {
        if (chromeAlertOkButton.isElementPresent(3)) {
            chromeAlertOkButton.click();
        }
    }

    public ProductsPage (WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(sortingDropdown);
    }

    public HeaderMenuComponent getHeaderMenuComponent() {
        return primaryHeader;
    }

    public void addProductToCartByName(String productName) {
        inventoryItems.stream()
                .filter(item -> item.getProductName().equalsIgnoreCase(productName))
                .findFirst()
                .ifPresentOrElse(
                        ProductCardComponent::clickAddToCart,
                        () -> {
                            throw new RuntimeException("Product not found in inventory: " + productName);
                        }
                );
    }

//    public ProductPage openProductPageByProductName(String productName) {
//        inventoryItems.stream()
//                .filter(item -> item.getProductName().equalsIgnoreCase(productName))
//                .findFirst()
//                .ifPresentOrElse(
//                        InventoryItemComponent::clickOnProductName
//                        ,
//                        () -> {
//                            throw new RuntimeException("Product not found: " + productName);
//                        }
//                );
//
//        return new ProductPage(getDriver());
//    }

    public void selectSortingOption(String option) {
        sortingDropdown.select(option);
    }

//    public boolean isSorted(List<? extends Comparable> list, SortType sortType) {
//        for (int i = 0; i < list.size() - 1; i++) {
//            int comparisonResult = list.get(i).compareTo(list.get(i + 1));
//            if ((sortType == SortType.NAME_A_TO_Z || sortType == SortType.PRICE_LOW_TO_HIGH) && comparisonResult > 0) {
//                return false;
//            }
//            else if ((sortType == SortType.NAME_Z_TO_A || sortType == SortType.PRICE_HIGH_TO_LOW) && comparisonResult < 0) {
//                return false;
//            }
//        }
//        return true;
//    }

    public List<String> getProductNames() {
        return inventoryItems.stream()
                .map(ProductCardComponent::getProductName)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return inventoryItems.stream()
                .map(item -> Double.parseDouble(item.getProductPrice().replace("$", "")))
                .collect(Collectors.toList());
    }

    public String getCartBadgeText() {
        return primaryHeader.getCartBadgeText();
    }
}
package com.solvd.laba.web.pages;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import com.zebrunner.carina.webdriver.gui.AbstractPage;

public abstract class BasePage extends AbstractPage {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public Optional<String> getUrl() {
        return Optional.empty();
    }
}


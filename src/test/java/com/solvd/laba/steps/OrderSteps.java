package com.solvd.laba.steps;

import java.io.IOException;
import java.util.List;

import com.zebrunner.carina.core.AbstractTest;
import io.cucumber.java.en.*;
import org.apache.ibatis.session.SqlSession;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

import static com.solvd.laba.utils.ChromeCapabilitiesProvider.getChromeCapabilities;
import static org.testng.Assert.assertTrue;

import com.solvd.laba.models.User;
import com.solvd.laba.models.Order;
import com.solvd.laba.mappers.UserMapper;
import com.solvd.laba.mappers.OrderMapper;
import com.solvd.laba.utils.MyBatisConfig;
import com.solvd.laba.web.pages.*;

public class OrderSteps extends AbstractTest {

    User user;

    private WebDriver driver;

    @Given("user {string} logs in")
    public void userLogin(String username) throws IOException {
        MutableCapabilities caps = getChromeCapabilities();
        driver = getDriver("chrome-" + username, caps);
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            user = userMapper.getUserByUsername(username);
            LoginPage loginPage = new LoginPage(driver);
            loginPage.open();
            ProductsPage productsPage = loginPage.login(user.getUsername(), user.getPassword());
        }
    }

    @And("user adds all ordered products to the cart")
    public void addAllOrderedProductsToCart() {
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            List<Order> orders = orderMapper.getOrdersByUserId(user.getId());
            ProductsPage productsPage = new ProductsPage(driver);
            for (Order order : orders) {
                for (int i = 0; i < order.getQuantity(); i++) {
                    productsPage.addProductToCartByName(order.getProductName());
                }
            }
            CartPage cartPage = productsPage.getHeaderMenuComponent().clickCartButton();
        }
    }

    @When("user completes the checkout process")
    public void completeCheckout() {
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        checkoutPage.fillCheckoutForm(user.getFirstName(), user.getLastName(), user.getZipCode());
        CheckoutOverviewPage checkoutOverviewPage = checkoutPage.clickContinueButton();
        CheckoutCompletePage checkoutCompletePage = checkoutOverviewPage.clickFinishButton();
    }

    @Then("order should be placed successfully")
    public void verifySuccessMessage() {
        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(driver);
        assertTrue(checkoutCompletePage.isOrderCompleteMessageDisplayed(), "Order was not completed!");
        driver.quit();
    }
}

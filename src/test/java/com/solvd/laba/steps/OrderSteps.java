package com.solvd.laba.steps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.zebrunner.carina.core.AbstractTest;
import io.cucumber.java.en.*;
import org.apache.ibatis.session.SqlSession;
import static org.testng.Assert.assertTrue;

import com.solvd.laba.models.User;
import com.solvd.laba.models.Order;
import com.solvd.laba.mappers.UserMapper;
import com.solvd.laba.mappers.OrderMapper;
import com.solvd.laba.utils.MyBatisUtil;
import com.solvd.laba.web.pages.*;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class OrderSteps extends AbstractTest {

    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutCompletePage checkoutCompletePage;
    User user;// productsPage was stored between steps as a compromise to enable continued UI navigation across steps (login → add to cart → checkout). All DB logic remains localized per step.

    @Given("user {string} logs in")
    public void userLogsIn(String username) throws IOException {
        MutableCapabilities caps = getMutableCapabilities();
        WebDriver driver = getDriver("chrome", caps);
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            user = userMapper.getUserByUsername(username);
            LoginPage loginPage = new LoginPage(driver);
            loginPage.open();
            productsPage = loginPage.login(user.getUsername(), user.getPassword());
        }
    }

    @And("user adds all ordered products to the cart")
    public void addAllOrderedProductsToCart() {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            List<Order> orders = orderMapper.getOrdersByUserId(user.getId());
            for (Order order : orders) {
                for (int i = 0; i < order.getQuantity(); i++) {
                    productsPage.addProductToCartByName(order.getProductName());
                }
            }
            cartPage = productsPage.getHeaderMenuComponent().clickCartButton();
        }
    }

    @When("user completes the checkout process")
    public void completeCheckout() {
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        checkoutPage.fillCheckoutForm(user.getFirstName(), user.getLastName(), user.getZipCode());
        CheckoutOverviewPage checkoutOverviewPage = checkoutPage.clickContinueButton();
        checkoutCompletePage = checkoutOverviewPage.clickFinishButton();
    }

    @Then("order should be placed successfully")
    public void verifySuccessMessage() {
        assertTrue(checkoutCompletePage.isOrderCompleteMessageDisplayed(), "Order was not completed!");
    }

    private static MutableCapabilities getMutableCapabilities() throws IOException {
        Path tmpProfile = Files.createTempDirectory("chrome-profile-");
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--disable-notifications",
                "--disable-popup-blocking",
                "--start-maximized",
                "--user-data-dir=" + tmpProfile.toString()
        );
        Map<String,Object> prefs = Map.of(
                "profile.password_manager_enabled", false,
                "credentials_enable_service",      false
        );
        options.setExperimentalOption("prefs", prefs);
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        return caps;
    }
}

package com.solvd.laba.steps;

import java.util.List;

import com.zebrunner.carina.core.AbstractTest;
import io.cucumber.java.en.*;
import org.apache.ibatis.session.SqlSession;
import static org.testng.Assert.assertTrue;

import com.solvd.laba.models.User;
import com.solvd.laba.models.Order;
import com.solvd.laba.models.Product;
import com.solvd.laba.mappers.UserMapper;
import com.solvd.laba.mappers.OrderMapper;
import com.solvd.laba.mappers.ProductMapper;
import com.solvd.laba.utils.MyBatisUtil;
import com.solvd.laba.web.pages.*;

public class OrderSteps extends AbstractTest {

    private User user;
    private List<Order> orders;
    private SqlSession session;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutCompletePage checkoutCompletePage;

    @Given("user {string} from DB logs in")
    public void userLogsInFromDb(String username) {
        session = MyBatisUtil.getSessionFactory().openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        user = userMapper.getUserByUsername(username);
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        productsPage = loginPage.login(user.getUsername(), user.getPassword());
        productsPage.closeChromePasswordAlertIfPresent();
    }

    @And("adds all products from DB assigned to this user to the cart")
    public void addProductsToCartFromDb() {
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);
        ProductMapper productMapper = session.getMapper(ProductMapper.class);
        orders = orderMapper.getOrdersByUserId((long) user.getUserId());
        for (Order order : orders) {
            Product product = productMapper.getProductById((long) order.getProductId());
            for (int i = 0; i < order.getQuantity(); i++) {
                productsPage.addProductToCartByName(product.getName());
            }
        }
        cartPage = productsPage.getHeaderMenuComponent().clickCartButton();
    }

    @When("user proceeds to checkout and confirms the order")
    public void userChecksOut() {
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        checkoutPage.fillCheckoutForm("Olesya", "Testova", "12345");
        CheckoutOverviewPage checkoutOverviewPage = checkoutPage.clickContinueButton();
        checkoutCompletePage = checkoutOverviewPage.clickFinishButton();
    }

    @Then("order should be placed successfully")
    public void verifySuccessMessage() {
        assertTrue(checkoutCompletePage.isOrderCompleteMessageDisplayed(), "Order failed");
        session.close();
    }
}

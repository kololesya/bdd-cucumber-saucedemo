package com.solvd.laba.bdd.steps;

import com.solvd.laba.models.User;
import com.solvd.laba.models.Order;
import com.solvd.laba.models.Product;
import com.solvd.laba.mappers.UserMapper;
import com.solvd.laba.mappers.OrderMapper;
import com.solvd.laba.mappers.ProductMapper;
import com.solvd.laba.utils.MyBatisUtil;
import com.solvd.laba.web.pages.*;

import io.cucumber.java.en.*;
import org.apache.ibatis.session.SqlSession;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class OrderSteps {

    private User user;
    private List<Order> orders;
    private SqlSession session;

    private WebDriver driver;

    @Given("user {string} from DB logs in")
    public void userLogsInFromDb(String username) {
        session = MyBatisUtil.getSessionFactory().openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        user = userMapper.getUserByUsername(username);

        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.login(user.getUsername(), user.getPassword());
    }

    @And("adds all products from DB assigned to this user to the cart")
    public void addProductsToCartFromDb() {
        OrderMapper orderMapper = session.getMapper(OrderMapper.class);
        ProductMapper productMapper = session.getMapper(ProductMapper.class);

        orders = orderMapper.getOrdersByUserId(user.getUserId());
        ProductsPage productsPage = new ProductsPage();

        for (Order order : orders) {
            Product product = productMapper.getProductById(order.getProductId());
            for (int i = 0; i < order.getQuantity(); i++) {
                productsPage.addProductToCartByName(product.getName());
            }
        }

        productsPage.getHeaderMenuComponent().clickCartButton();
    }

    @When("user proceeds to checkout and confirms the order")
    public void userChecksOut() {
        CartPage cartPage = new CartPage();
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage();
        checkoutPage.fillInfo("Olesya", "Testova", "12345");
        checkoutPage.continueToFinish();
    }

    @Then("order should be placed successfully")
    public void verifySuccessMessage() {
        OrderSuccessPage successPage = new OrderSuccessPage();
        assertTrue(successPage.isOrderSuccessful(), "Order failed");

        session.close();
    }
}

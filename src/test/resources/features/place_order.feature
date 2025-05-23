Feature: Order placement

  Scenario: Successful order placement by user from DB
    Given user "standard_user" from DB logs in
    And adds all products from DB assigned to this user to the cart
    When user proceeds to checkout and confirms the order
    Then order should be placed successfully

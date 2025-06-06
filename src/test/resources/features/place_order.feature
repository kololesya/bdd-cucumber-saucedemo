Feature: Order placement

  Scenario: Successful order placement by user from DB
    Given user "standard_user" logs in
    And user adds all ordered products to the cart
    When user completes the checkout process
    Then order should be placed successfully

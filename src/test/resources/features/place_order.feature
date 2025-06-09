Feature: Order placement

  Scenario Outline: Successful order placement by user from DB
    Given user "<username>" logs in
    And user adds all ordered products to the cart
    When user completes the checkout process
    Then order should be placed successfully

    Examples:
      | username       |
      | standard_user  |
      | problem_user   |

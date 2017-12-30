Feature: Cart page testing
  These tests deals with cart page

  Scenario: Cart page verification
    Given I go to Literatura_by
    When I select first book with price
    And I click on add to cart icon
    And I go to cart page
    And I set new quantity "2"
    Then I verify that the amount is correct
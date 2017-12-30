Feature: Literatura.by testing
  These tests deals with Literatura.by

  Scenario: Single book
    Given I go to Literatura_by
    When I search book named "Hello, world!"
    Then URL should contain '/kniga/'
    And Link should contain 'Книги'

  Scenario: Multiple book
    Given I go to Literatura_by
    When I search book named "Windows"
    Then URL should contain '/poisk/'
    And Text should contain "Windows"

  Scenario Outline: Book test with data provider
    Given I go to Literatura_by
    When I search the book <BookTitle>
    Then URL should contain '/poisk/'
    And Text should contain book name <BookTitle>
    And Book name should contain <BookTitle>
    Examples:
      | BookTitle |
      | Windows   |
      | Java      |
      | USA       |
      | Scrum     |


  Scenario: Cart verification
    Given I go to Literatura_by
    When I select first book with price
    Then Book names should be the same
    When I click on add to cart icon
    Then Add to cart icon should change
    And Quantity in the cart should change by 1
    And Price in the cart should be the same
    When I set promo "813524"
    Then Discount banner should appear
    And Discount is calculated correctly


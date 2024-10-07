@contact-us
Feature: Login

  Scenario: Check main page
    When get page url
    Then System must display main page

  Scenario: Search 'şort' and select ones
    Given get page url
    When search 'şort'
    And select random product
    Then System must display selected product in the cart


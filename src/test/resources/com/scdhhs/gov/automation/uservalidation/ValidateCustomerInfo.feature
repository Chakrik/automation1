Feature: When a user submits thier basic information
  the user needs to be informed if the information is valid.

  Scenario: We have recieved a message that does not have a first name
    Given A createCustomerInfo object
    When the firstName field does not have a value
    Then The error "The first name must not be empty" will be returned
    And the system will continue to the next test

  Scenario: We have recieved a message that does not have a Last name
    Given A createCustomerInfo object
    When the lastName field does not have a value
    Then The error "The last name must not be empty" will be returned
    And the system will continue to the next test

  Scenario: We have recieved a message that does not have a well formed date of birth
    Given A createCustomerInfo object
    When the date of birth field does not have a well formed date
    Then The error "The date of birth must be valid" will be returned
    And the system will continue to the next test
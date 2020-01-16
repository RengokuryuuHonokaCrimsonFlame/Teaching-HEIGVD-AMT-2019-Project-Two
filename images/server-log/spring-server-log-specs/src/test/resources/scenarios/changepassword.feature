Feature: Application ChangePassword
  Background:
    Given there is a Player server

  Scenario: Change a normal player password
    Given I have a normal Player Token
    Given I have a old password
    Given I have a new password
    When I post them to the /changepassword endpoint
    Then I received a 200 status code
    Given I have a normal Player Credential new
    When I post it to the /login endpoint
    Then I received a 200 status code
    Then I have a x-token-dnd header
    Given I have a old password reverse
    Given I have a new password reverse
    When I post them to the /changepassword endpoint
    Then I received a 200 status code

  Scenario: Change a normal player password (wrong password)
    Given I have a normal Player Token
    Given I have a wrong password
    Given I have a new password
    When I post them to the /changepassword endpoint
    Then I received a 403 status code

  Scenario: Change a locked player password
    Given I have a locked Player Token
    Given I have a old password
    Given I have a new password
    When I post them to the /changepassword endpoint
    Then I received a 423 status code

  Scenario: Change a normal player password (old credential)
    Given I have a old Player Token
    Given I have a old password
    Given I have a new password
    When I post them to the /changepassword endpoint
    Then I received a 403 status code

  Scenario: Change a unknown player password
    Given I have an unknown Player Token
    Given I have a old password
    Given I have a new password
    When I post them to the /changepassword endpoint
    Then I received a 404 status code

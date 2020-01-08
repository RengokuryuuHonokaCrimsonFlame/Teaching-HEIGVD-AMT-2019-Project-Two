Feature: Application Login
  Background:
    Given there is a Player server

  Scenario: Log a normal player
    Given I have a normal Player Credential
    When I post it to the /login endpoint
    Then I received a 200 status code
    Then I have a x-token-dnd header

  Scenario: Log a normal Player Credential with false password
    Given I have a normal Player Credential (false password version)
    When I post it to the /login endpoint
    Then I received a 404 status code
    Then I do not have a x-token-dnd header

  Scenario: Log a normal Player Credential with false username
    Given I have a normal Player Credential (false username version)
    When I post it to the /login endpoint
    Then I received a 404 status code
    Then I do not have a x-token-dnd header

  Scenario: Log a locked Player Credential
    Given I have a locked Player Credential
    When I post it to the /login endpoint
    Then I received a 423 status code
    Then I do not have a x-token-dnd header
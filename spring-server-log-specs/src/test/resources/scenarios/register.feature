Feature: Application Register
  Background:
    Given there is a Player server

  Scenario: Register a new player
    Given I have a player email
    When I post it to the /register endpoint
    Then I received a 201 status code
    Then I have a x-token-dnd header
    When I post it to the /register endpoint
    Then I received a 409 status code
    Then I do not have a x-token-dnd header
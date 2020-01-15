Feature: Application Player
  Background:
    Given there is an application server

  Scenario: Player connect to the player
    Given I have a Player Token
    Given I have a pagination id
    When I get them to the /getplayer endpoint
    Then I received a 201 status code
    Then I received a new player information
    Given I update the players information
    When I post them to the /updateplayer endpoint
    Then I received a 200 status code
    When I get them to the /getplayer endpoint
    Then I received a 200 status code
    Then I received a the update player information
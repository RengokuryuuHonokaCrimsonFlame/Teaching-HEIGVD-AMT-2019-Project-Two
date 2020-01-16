Feature: Application Party
  Background:
    Given there is an application server
    Given i populate the party database

  Scenario: Player can see all party
    Given I have a Player Token
    Given I have a pagination id
    When I get them to the /getplayer endpoint
    When I get them to the /getallparties endpoint
    Then I received a 200 status code
    Then I received a parties list

  Scenario: Player can see a party
    Given I have a Player Token
    Given I have a pagination id
    Given I have a party name
    When I get them to the /getplayer endpoint
    When I get them to the /getparty endpoint
    Then I received a 200 status code
    Then I received the party information

  Scenario: Player can join and quit a party
    Given I have a Player Token
    Given I have a party name
    When I get them to the /getplayer endpoint
    When I get them to the /joinparty endpoint
    Then I received a 200 status code
    Then I received the party information with me inside
    When I get them to the /quitparty endpoint
    Then I received a 200 status code
    Then I received the party information


  Scenario: Player can't join an unknown party
    Given I have a Player Token
    Given I have a false party name
    When I get them to the /getplayer endpoint
    When I get them to the /joinparty endpoint
    Then I received a 404 status code

  Scenario: Player can't join an unknown party
    Given I have a Player Token
    Given I have a false party name
    When I get them to the /getplayer endpoint
    When I get them to the /quitparty endpoint
    Then I received a 404 status code

  Scenario: Unknown Player can't see all parties
    Given I have a Player Token
    Given I have a pagination id
    When I get them to the /getallparties endpoint
    Then I received a 401 status code

  Scenario: Unknown Player can't see a party
    Given I have a Player Token
    Given I have a pagination id
    Given I have a party name
    When I get them to the /getparty endpoint
    Then I received a 401 status code

  Scenario: Unknown Player can't join a party
    Given I have a Player Token
    Given I have a party name
    When I get them to the /joinparty endpoint
    Then I received a 404 status code

  Scenario: Unknown Player can't quit a party
    Given I have a Player Token
    Given I have a party name
    When I get them to the /quitparty endpoint
    Then I received a 404 status code

  Scenario: Player with old token can't see all parties
    Given I have a Player old Token
    Given I have a pagination id
    Given I have a party name
    When I get them to the /getparty endpoint
    Then I received a 401 status code

  Scenario: Player with old token can't see a party
    Given I have a Player old Token
    Given I have a pagination id
    Given I have a party name
    When I get them to the /getparty endpoint
    Then I received a 401 status code

  Scenario: Player with old token can't join a party
    Given I have a Player old Token
    Given I have a party name
    When I get them to the /joinparty endpoint
    Then I received a 401 status code

  Scenario: Player with old token can't quit a party
    Given I have a Player old Token
    Given I have a party name
    When I get them to the /quitparty endpoint
    Then I received a 401 status code

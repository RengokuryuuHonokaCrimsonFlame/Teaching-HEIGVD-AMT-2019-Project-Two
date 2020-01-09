Feature: Application AdminPlayers
  Background:
    Given there is a Player server

  Scenario: Admin Player Access to the page
    Given I have a admin Player Token
    Given I have a pagination id
    When I get them to the /adminplayer endpoint
    Then I received a 200 status code
    Then I received a list of players

  #TODO Active this test next week
  #Scenario: Admin Player Access to the page (old token)
  #  Given I have a old admin Player Token
  #  Given I have a pagination id
  #  When I get them to the /adminplayer endpoint
  #  Then I received a 403 status code

  Scenario: Normal Player Access to the page
    Given I have a normal Player Token
    Given I have a pagination id
    When I get them to the /adminplayer endpoint
    Then I received a 403 status code

  Scenario: Locked Admin Player Access to the page
    Given I have a locked admin Player Token
    Given I have a pagination id
    When I get them to the /adminplayer endpoint
    Then I received a 403 status code

  Scenario: Admin Player Locked A Player
    Given I have a admin Player Token
    Given I have a username
    When I post them to the /adminplayer endpoint
    Then I received a 200 status code
    Given I have a normal Player Credential
    When I post it to the /login endpoint
    Then I received a 423 status code
    When I post them to the /adminplayer endpoint
    Then I received a 200 status code

  #TODO Active this test next week
  #Scenario: Admin Player Locked A Player (old token)
  #  Given I have a old Player Token
  #  Given I have a username
  #  When I post them to the /adminplayer endpoint
  #  Then I received a 403 status code

  Scenario: Normal Player Locked A Player
    Given I have a normal Player Token
    Given I have a username
    When I post them to the /adminplayer endpoint
    Then I received a 403 status code

  Scenario: Locked Admin Player Locked A Player
    Given I have a locked admin Player Token
    Given I have a username
    When I post them to the /adminplayer endpoint
    Then I received a 403 status code
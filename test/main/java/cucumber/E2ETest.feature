Feature: E2E test

  Scenario: positive case - we run a game with 2 mazes and 2 players in 3 threads
    Given we have mazes from following files
      | maze1.in |
      | maze2.in |
    And we have players
    When we start a game for multiple players in 3 threads
    And we get game results
    Then results for "Another working maze!!" are
      | PlayerDistantGoing       | true    | 12 |
      | PlayerRuleSet            | true   | 16 |
    And results for "A maze ing !!" are
      | PlayerDistantGoing       | true   | 8 |
      | PlayerRuleSet            | true   | 7 |



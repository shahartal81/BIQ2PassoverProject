Feature: test PlayerAdvanced
  Scenario: positive case - we get valid move from move method
    Given we create PlayerIntelligent
    When we do a move
    Then we get valid move
    And move is saved as last move
    And bookmark sequence is 0

  Scenario Outline: positive case - we hit a wall
    Given we create PlayerIntelligent
    When we do a move
    And we hit wall
    Then we create bookmark with sequence <bookmark>
    And bookmark <bookmark> contains last move

    Examples:
    | bookmark |
    | 1        |

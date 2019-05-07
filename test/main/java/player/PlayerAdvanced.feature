Feature: test PlayerAdvanced
  Scenario: positive case - we get valid move from move method
    Given we create PlayerAdvanced
    When we do a move
    Then we get valid move
    And move is saved as last move
    And bookmark sequence is 0

#  Scenario: positive case - we hit a wall
#    Given we create PlayerAdvanced
#    When we do a move
#    And we hit wall
#    Then we get valid move

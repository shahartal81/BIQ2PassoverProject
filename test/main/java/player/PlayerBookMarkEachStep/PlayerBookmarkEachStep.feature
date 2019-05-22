Feature: test PlayerBookmarkEachStep

  Scenario: positive case - we plan next move to be Bookmark
    Given we create PlayerBookmarkEachStep
    When we do a move
    Then we mark next turn as Bookmark
    When we do a move
    And last move should be Bookmark

  Scenario Outline: positive case - we hit a wall
    Given we create PlayerBookmarkEachStep
    When we do a move
    Then move is saved as last move
    And bookmark sequence is <seq1>
    When we hit wall
    Then we mark next turn as Bookmark
    When we do another move
    Then bookmark sequence is <seq2>
    And bookmark <seq2> is saved
    And bookmark <seq2> contains last move
    And last move should be Bookmark

    Examples:
      | seq1 | seq2 |
      | 0    | 1    |

  Scenario Outline: positive case - we hit a bookmark
    Given we create PlayerBookmarkEachStep
    When we do a move
    And we hit wall
    And we do a move
    And we hit bookmark <seq1>
    And we do a move
    And we hit bookmark <seq2>
    Then we mark next turn as Bookmark
    And bookmark <seq2> is saved
    And bookmark <seq2> contains last move

    Examples:
      | seq1 | seq2 |
      | 1    | 2    |
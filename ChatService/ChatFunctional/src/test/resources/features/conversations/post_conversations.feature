Feature: Post conversations to another user

  Scenario: A user should be able to send conversations to another user
    And a user Alice already exists on the system
    And a user Bob already exists on the system
    And user Alice is successfully authenticated
    When user Alice tries to send a message to user Bob
    Then user Bob should be able to receive the conversation
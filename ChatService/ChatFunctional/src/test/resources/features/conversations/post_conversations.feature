Feature: Post conversations to another user

  Scenario: A user should be able to send conversations to another user
    Given a user Alice already exists on the system
    And a user Bob already exists on the system
    And user Alice is successfully authenticated
    And user Alice sends a message 'Hello Bob' to user Bob successfully
    When user Bob tries to access their conversations
    Then user Bob should be able to receive the message 'Hello Bob' from Alice
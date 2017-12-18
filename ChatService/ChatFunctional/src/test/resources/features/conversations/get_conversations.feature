Feature: Get Conversations for a user

  Scenario: A user is able to retrieve the conversations successfully
    Given a user is successfully authenticated
    When a user tries to access their conversations
    Then the user should be able to receive their conversations in the response

#  Scenario: A audit event of MESSAGE_READ should be generated when user retrieves their conversation



  
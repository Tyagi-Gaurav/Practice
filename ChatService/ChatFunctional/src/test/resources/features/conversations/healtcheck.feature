Feature: Healthcheck for main application

  Scenario: Healthcheck for main application when all dependencies are up and running
    When healthcheck endpoint is accessed for main application
    Then the healthcheck should be successful

#  Scenario: Healthcheck for main application when application when database is down
#    When healthcheck endpoint is accessed for main application
#    Then the healthcheck should be successful
#    And the healthcheck should report that audit is down

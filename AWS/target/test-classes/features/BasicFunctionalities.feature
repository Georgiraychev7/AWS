Feature: In this feature we will test basic operations with AWS

  @Basic
  Scenario: Create buckets
    When Create two buckets
    Then Verify buckets are created
    And  Delete buckets
    Then Verify bucket are deleted

  @Basic
  Scenario: Create directories
    When  Create directories
    Then  List all directories
    And   Delete directories
    Then  Verify directories are deleted

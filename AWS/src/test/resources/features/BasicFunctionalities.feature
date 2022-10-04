Feature: In this feature we will test basic operations with AWS

  @Basic
  Scenario: Create buckets
    When Create new-test-bucket-for-testing and new-test-bucket-for-testing-2
    Then Verify new-test-bucket-for-testing and new-test-bucket-for-testing-2 are created
    And  Delete new-test-bucket-for-testing and new-test-bucket-for-testing-2
    Then Verify new-test-bucket-for-testing and new-test-bucket-for-testing-2 are deleted

  @Basic
  Scenario: Create directories
    When  Create directories test-folder and test-folder-2
    Then  List all directories in new-test-bucket-for-testing
    And   Delete directories in new-test-bucket-for-testing
    Then  Verify directories in new-test-bucket-for-testing are deleted

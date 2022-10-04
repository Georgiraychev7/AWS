Feature: In this feature we will test some AWS functionalities

  Background: Clean s3 buckets and directories
    When Delete all files from directory "test-folder"
    And  Delete all files from new-test-bucket-for-s3
    Then List all objects in new-test-bucket-for-s3 and verify it is empty
  @AWS
  Scenario: Upload and Download file in s3 bucket
    Given Upload a file to new-test-bucket-for-s3
    When  List bucket new-test-bucket-for-s3
    Then  Verify file is uploaded
    And   Download file from new-test-bucket-for-s3

  @AWS
  Scenario: Upload and Download file in s3 directory
    Given  Upload file to test-folder
    When   List directory test-folder
    Then   Verify that file is uploaded to directory
    And    Download file from directory in new-test-bucket-for-s3




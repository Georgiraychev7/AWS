Feature: In this feature we will test some AWS functionalities

  Background: Clean s3 buckets and directories
    When Delete all files from directories and directories
    And  Delete all files from bucket
    Then List all objects in bucket and verify it is empty
  @AWS
  Scenario: Upload and Download file in s3 bucket
    Given Upload a file to s3 bucket
    When  List bucket
    Then  Verify file is uploaded
    And   Download file from s3 bucket

  @AWS
  Scenario: Upload and Download file in s3 directory
    Given  Upload file to s3 directory
    When   List directory
    Then   Verify that file is uploaded to directory
    And    Download file from s3 directory




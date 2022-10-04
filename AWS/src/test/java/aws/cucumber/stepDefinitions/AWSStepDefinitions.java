package aws.cucumber.stepDefinitions;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import aws.helper.AWS;
import aws.helper.AWSS3Service;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AWSStepDefinitions implements AWS {

    @When("Delete all files from directory {string}")
    public void deleteDirectoryAndHisFiles(String folder) {
        AWSS3Service.deleteFolderAndContent(bucketName,folder);
    }
    @When("Delete all files from {word}")
    public void deleteAllFilesFromBucket(String bucketName) {
        AWSS3Service.deleteAllObjectsFromBucket(bucketName);
    }
    @Then("List all objects in {word} and verify it is empty")
    public void listObjectsFromBucket(String bucketName) {
        ObjectListing objectsFromBucket = AWSS3Service.listObjectsInBucket(bucketName);
        for (S3ObjectSummary objectSummary : objectsFromBucket.getObjectSummaries()) {
            System.out.println("Objects in bucket are : " + objectSummary.getKey());
        }
    }
    @When("Upload a file to {word}")
    public void uploadFileToBucket(String bucketName) {
        AWSS3Service.uploadFileToBucket(bucketName);
    }
    @Then("Verify file is uploaded")
    public void verifyFileIsUploaded() {
       AWSS3Service.getObject(bucketName,fileKeyRestAssured2);
    }

    @When("Download file from {word}")
    public void downloadFileFromBucket(String bucketName) {
        AWSS3Service.multipleDownloadFromBucket(bucketName, false);
    }

    @When("Upload file to {word}")
    public void uploadFileToDirectory(String folder) {
       AWSS3Service.createFolder(bucketName, folder);
       AWSS3Service.uploadFileToFolder(bucketName, folder);
    }
    @Then("Verify that file is uploaded to directory")
    public void verifyFileIsUploadedToDirectory() {
       AWSS3Service.getObject(bucketName, directoryKeyRestAssured2);
    }
    @When("Download file from directory in {word}")
    public void downloadFileFromDirectory(String bucketName) {
        AWSS3Service.multipleDownloadFromDirectory(bucketName,true);
    }

    @When("List bucket {word}")
    public void listBucket() {
        ObjectListing objectsFromBucket = AWSS3Service.listObjectsInBucket(bucketName);
        for (S3ObjectSummary objectSummary : objectsFromBucket.getObjectSummaries()) {
            System.out.println("Objects in bucket are : " + objectSummary.getKey());
        }
    }
    @When("List directory {word}")
    public void listDirectory(String folder) {
        ObjectListing objectsFromFolder = AWSS3Service.listObjectsInFolder(bucketName,folder);
        for (S3ObjectSummary objectSummary : objectsFromFolder.getObjectSummaries()) {
            System.out.println("Objects in directory are : " + objectSummary.getKey());
        }
    }
}

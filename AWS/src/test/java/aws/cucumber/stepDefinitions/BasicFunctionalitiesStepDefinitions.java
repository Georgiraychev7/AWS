package aws.cucumber.stepDefinitions;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import aws.helper.AWS;
import aws.helper.AWSS3Service;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BasicFunctionalitiesStepDefinitions implements AWS {

    @When("Create {word} and {word}")
    public void createBuckets(String newBucket, String newBucket2) {
        AWSS3Service.createBucket(newBucket);
        AWSS3Service.createBucket(newBucket2);
    }
    @Then("Verify {word} and {word} are created")
    public void verifyBucketsAreCreated(String newBucket, String newBucket2) {
        System.out.println("doesBucketExistV2 -> " + AWSS3Service.doesBucketExist(newBucket));
        System.out.println("doesBucketExistV2 -> " + AWSS3Service.doesBucketExist(newBucket2));
    }
    @And("Delete {word} and {word}")
    public void deleteBuckets(String newBucket, String newBucket2) {
        AWSS3Service.deleteBucket(newBucket);
        AWSS3Service.deleteBucket(newBucket2);
    }

    @Then("Verify {word} and {word} are deleted")
    public void verifyBucketsAreDeleted(String newBucket, String newBucket2) {
        System.out.println("doesBucketExistV2 -> " + AWSS3Service.doesBucketExist(newBucket));
        System.out.println("doesBucketExistV2 -> " + AWSS3Service.doesBucketExist(newBucket2));
    }

    @When("Create directories {word} and {word}")
    public void createDirectories(String folder, String folder2) {
        AWSS3Service.createBucket(newBucket);
        AWSS3Service.createFolder(newBucket, folder);
        AWSS3Service.createFolder(newBucket, folder2);
    }
    @When("List all directories in {word}")
    public void listAllDirectories(String newBucket) {
        ObjectListing objectsFromBucket = AWSS3Service.listObjectsInBucket(newBucket);
        for (S3ObjectSummary objectSummary : objectsFromBucket.getObjectSummaries()) {
            System.out.println("Objects in bucket are : " + objectSummary.getKey());
        }
    }
    @And("Delete directories in {word}")
    public void deleteDirectories(String newBucket) {
        AWSS3Service.deleteFolderAndContent(newBucket, folder);
        AWSS3Service.deleteFolderAndContent(newBucket, folder2);
    }

    @Then("Verify directories in {word} are deleted")
    public void verifyDirectoriesAreDeleted(String newBucket) {
        ObjectListing objectsFromBucket = AWSS3Service.listObjectsInBucket(newBucket);
        for (S3ObjectSummary objectSummary : objectsFromBucket.getObjectSummaries()) {
            System.out.println("Objects in bucket are : " + objectSummary.getKey());
        }
        AWSS3Service.deleteBucket(newBucket);
    }
}

package aws.helper;

import java.io.*;
import java.util.List;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.*;

public class AWSS3Service implements AWS {
    public static AWSCredentials credentials = new BasicAWSCredentials(
            "", ""
    );
    public static AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.EU_WEST_2)
            .build();

    public AWSS3Service(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    //is bucket exist
    public static boolean doesBucketExist(String bucketName) {
        return s3client.doesBucketExistV2(bucketName);
    }

    //create a bucket
    public static Bucket createBucket(String bucketName) {
        return s3client.createBucket(bucketName);
    }

    //list all buckets
    public static List<Bucket> listBuckets() {
        return s3client.listBuckets();
    }

    //create folder in s3 bucket
    public static void createFolder(String bucketName, String folderName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                folderName + SUFFIX, emptyContent, metadata);
        s3client.putObject(putObjectRequest);
    }

    // delete folder and his content
    public static void deleteFolderAndContent(String bucketName, String folderPrefix) {
        if (s3client.doesBucketExistV2(bucketName)) {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix(folderPrefix);

            ObjectListing objectListing = s3client.listObjects(listObjectsRequest);

            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                s3client.deleteObject(bucketName, objectSummary.getKey());
            }
            if (objectListing.isTruncated()) {
                objectListing = s3client.listNextBatchOfObjects(objectListing);
            }
        }
    }

    //delete a bucket
    public static void deleteBucket(String bucketName) {
        s3client.deleteBucket(bucketName);
    }

    //upload file to folder
    public static void uploadFileToFolder(String bucketName, String folderName) {
        String fileName = folderName + SUFFIX + fileKeyRestAssured2;
        s3client.putObject(new PutObjectRequest(bucketName, fileName,
                new File(filePath)));
    }

    //upload file to bucket

    public static void uploadFileToBucket(String bucketName) {
        s3client.putObject(new PutObjectRequest(bucketName, fileKeyRestAssured2,
                new File(filePath)));
    }

    public static void uploadThousandFiles(String bucketName){
        for(int i = 0; i <=100; i++) {
            s3client.putObject(new PutObjectRequest(bucketName, fileKeyRestAssured2 + i,new File(filePath)));
        }
    }

    //listing objects in bucket
    public static ObjectListing listObjectsInBucket(String bucketName) {
        return s3client.listObjects(bucketName);
    }

    //listing objects in folder
    public static ObjectListing listObjectsInFolder(String bucketName, String folderName) {
        return s3client.listObjects(bucketName, folderName);
    }

    //get an object from bucket
    public static S3Object getObject(String bucketName, String objectKey) {
        return s3client.getObject(bucketName, objectKey);
    }

    //deleting an object
    public static void deleteObjectFromBucket(String bucketName, String objectKey) {
        s3client.deleteObject(bucketName, objectKey);
    }

    public static void deleteAllObjectsFromBucket(String bucketName) {
        for (S3ObjectSummary file : s3client.listObjects(bucketName).getObjectSummaries()) {
            s3client.deleteObject(bucketName, file.getKey());
        }
    }

    public static void multipleDownloadFromBucket(String bucketName, boolean includeSubDirectories) {
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3client).build();
        File directory = new File(downloadPath);
        MultipleFileDownload multipleFileDownload = transferManager.downloadDirectory(bucketName,"", directory, includeSubDirectories);
        try {
            multipleFileDownload.waitForCompletion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void multipleDownloadFromDirectory(String bucketName, boolean includeSubDirectories) {
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3client).build();
        File directory = new File(downloadPath);
        MultipleFileDownload multipleFileDownload = transferManager.downloadDirectory(bucketName,folder, directory, includeSubDirectories);
        try {
            multipleFileDownload.waitForCompletion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uploadMultipleFiles(String bucket, boolean includeSubDirectories) {
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3client).build();
        File directory = new File(downloadPath);
        MultipleFileUpload upload = transferManager.uploadDirectory(bucket, "", directory, includeSubDirectories);
        try {
            upload.waitForCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void deleteAllObjects(String bucketName) {
//        List<S3ObjectSummary> objects = s3client.listObjects(bucketName).getObjectSummaries();
//        for (S3ObjectSummary file : s3client.listObjects(bucketName).getObjectSummaries()) {
//            System.out.println(file.getKey());
//        }
//    }
}





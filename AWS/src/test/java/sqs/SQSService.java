package sqs;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQSService implements SQS {

    public static AWSCredentials credentials = new BasicAWSCredentials(
            "", ""
    );
    private static String queueUrl;
    private static List<Message> sqsMessages;

    public static AmazonSQS sqs = AmazonSQSClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();

    // create queue

    public static CreateQueueResult createQueue(String queueName) {
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
        try {
            sqs.createQueue(createQueueRequest);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }
        return sqs.createQueue(createQueueRequest);
    }

    // get Url

    public static String getQueueUrl() {
        queueUrl = createQueue(queueName).getQueueUrl();
        return queueUrl;
    }


    // List your queues

    public static ListQueuesResult listWithQueues() {
        return sqs.listQueues();
    }

    // Send a message to a standard queue

    public static void sendMessage() {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

        messageAttributes.put("AttributeOne", new MessageAttributeValue().withStringValue("This is an attribute")
                .withDataType("String"));

        SendMessageRequest sendMessageStandardQueue = new SendMessageRequest().withQueueUrl(getQueueUrl())
                .withMessageBody("This is a simple test message.")
                .withDelaySeconds(30) // Message will arrive in the queue after 30 seconds. We can use this only in standard queues
                .withMessageAttributes(messageAttributes);

        sqs.sendMessage(sendMessageStandardQueue);
    }

    // Read a message from a queue

    public static void readMessage(String queueUrl) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl).withWaitTimeSeconds(10) // Long polling;
                .withMaxNumberOfMessages(1); // Max is 10
         sqsMessages = sqs.receiveMessage(receiveMessageRequest)
                .getMessages();
        sqsMessages.get(0)
                .getAttributes();
        sqsMessages.get(0)
                .getBody();
    }

    // Delete a message from a queue

    public static void deleteMessage(String queueUrl) {
        sqs.deleteMessage(new DeleteMessageRequest().withQueueUrl(queueUrl)
                .withReceiptHandle(sqsMessages.get(0)
                        .getReceiptHandle()));
    }

    // Delete a queue

    public static void deleteQueue(String queueUrl) {
        sqs.deleteQueue(queueUrl);
    }
}




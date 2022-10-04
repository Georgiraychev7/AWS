package sqs;

import com.amazonaws.services.sqs.model.ListQueuesResult;

public class Test {

    @org.junit.jupiter.api.Test
    public void createQueue() {
        SQSService.createQueue("");
    }

    @org.junit.jupiter.api.Test
    public void listAllQueues() {
        ListQueuesResult queuesResult = SQSService.listWithQueues();
        for (String url : queuesResult.getQueueUrls()) {
            System.out.println(url);
        }
    }
}

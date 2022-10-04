package sqs;

import java.util.Date;

public interface SQS {

    String queueName = "testQueue" +
            new Date().getTime();
}

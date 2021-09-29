package com.products.core.queue;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class ProductQueue {
    private static final String AWS_REGION = System.getenv("REGION");
    private static final String QUEUE_URL = System.getenv("QUEUE_URL");

    private static ProductQueue productQueue;

    private AmazonSQS sqs;

    public ProductQueue() {
        sqs = AmazonSQSClientBuilder.standard()
                .withRegion(Regions.fromName(AWS_REGION))
                .build();
    }

    public static ProductQueue getInstance() {
        if (productQueue == null) {
            productQueue = new ProductQueue();
        }

        return productQueue;
    }

    public void sendMessage(String message) {
        SendMessageRequest sendMsgRequest = new SendMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMessageBody(message)
                .withDelaySeconds(5);

        sqs.sendMessage(sendMsgRequest);
    }
}

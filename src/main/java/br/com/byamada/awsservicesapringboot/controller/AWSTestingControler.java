package br.com.byamada.awsservicesapringboot.controller;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/aws")
public class AWSTestingControler {

    private final String TOPIC_ARN = "arn:aws:sns:us-east-1:000000000000:messages-topic";

    @Autowired
    private AmazonSNS snsClient;

    //[AWS - SNS] [Step 4] Subscribing in a topic
    @GetMapping("/sns/subscription/{email}")
    public ResponseEntity<String> addSubscription(@PathVariable String email) {
        log.info("Subscribing to the topic with email: {}", email);
        SubscribeRequest subscribeRequest = new SubscribeRequest(TOPIC_ARN, "email", email);
        snsClient.subscribe(subscribeRequest);
        log.info("Subscribed to the topic, waiting confirmation: {}", email);
        return ResponseEntity.ok("Subscription request is pending. To confirm the subscription, check your email: " + email);
    }

    //[AWS - SNS] [Step 5] Sending a notification to the topic
    @GetMapping("/sns/sendNotification/{message}")
    public ResponseEntity<String> snsTeste(@PathVariable String message) {
        log.info("Sending notification: {}", message);
        PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, message, "Message from SNS spring boot test");
        snsClient.publish(publishRequest);
        log.info("Notification send successfully: {}", message);
        return ResponseEntity.ok("Notification send successfully");
    }

}

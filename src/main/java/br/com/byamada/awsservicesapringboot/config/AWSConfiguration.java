package br.com.byamada.awsservicesapringboot.config;

import brave.Tracing;
import brave.instrumentation.aws.sqs.SqsMessageTracing;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfiguration {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region.static}")
    private String region;

    @Value("${aws.sqs.end-point}")
    private String sqsServiceEndpoint;

    @Value("${aws.sns.end-point}")
    private String snsServiceEndpoint;

    @Value("${aws.s3.end-point}")
    private String s3ServiceEndpoint;


    @Bean
    public AWSCredentialsProvider credentialsProvider() {
        final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        return credentialsProvider;
    }

    //[AWS-SQS] [step 3] Configuring
    @Bean
    public AmazonSQS amazonSQS(AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonSQSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                //for local test propose I'm seting the endPoint, if using real implementation you can use the complete address
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsServiceEndpoint, region))
                //[TRACING SQS] [step 4]
                .withRequestHandlers(sqsMessageTracing().requestHandler())
                .build();
    }

    //[AWS - ClOUDWATCH] [STEP 3] Configuring
    @Bean
    public AmazonCloudWatch amazonCloudWatch(AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonCloudWatchClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(region)
                .build();
    }

    //[AWS - S3] [STEP 3] Configuring
    @Bean
    public AmazonS3 amazonS3(AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                //for local test propose I'm seting the endPoint, if using real implementation you can use the complete address
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3ServiceEndpoint, region))
                .build();
    }

    //[AWS - SNS] [Step 3] Configuring
    @Bean
    public AmazonSNS amazonSNS(AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonSNSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                //for local test propose I'm seting the endPoint, if using real implementation you can use the complete arn address
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(snsServiceEndpoint, region))
                .build();
    }


    //[TRACING SQS] [step 3] Configure Tracing and SqsMessageTracing
    private SqsMessageTracing sqsMessageTracing() {
        Tracing currentTracing = Tracing.current();
        return SqsMessageTracing.create(currentTracing);
    }


}

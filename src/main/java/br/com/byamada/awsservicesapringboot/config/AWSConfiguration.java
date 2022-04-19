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

    @Value("${aws.sq.end-point}")
    private String serviceEndpoint;

    @Bean
    public AWSCredentialsProvider credentialsProvider() {
        final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        return credentialsProvider;
    }

    //[TRACING SQS] [step 2] Configuring
    @Bean
    public AmazonSQS amazonSQS(AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonSQSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, region))
                //[TRACING SQS] [step 4]
                .withRequestHandlers(sqsMessageTracing().requestHandler())
                .build();
    }

    //[AWS - ClOUDWATCH] [STEP 1] Configuring
    @Bean
    public AmazonCloudWatch amazonCloudWatch(AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonCloudWatchClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(region)
                .build();
    }

    //[AWS - S3] [STEP 1] Configuring
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .build();
    }

    //[TRACING SQS] [step 3] Configure Tracing and SqsMessageTracing
    private SqsMessageTracing sqsMessageTracing() {
        Tracing currentTracing = Tracing.current();
        return SqsMessageTracing.create(currentTracing);
    }
}

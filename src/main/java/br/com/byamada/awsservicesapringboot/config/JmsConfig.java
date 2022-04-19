//[AWS-SQS] [Step 3] Configuring the jms
package br.com.byamada.awsservicesapringboot.config;

import brave.Tracer;
import brave.Tracing;
import brave.instrumentation.aws.sqs.SqsMessageTracing;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.annotation.PostConstruct;
import javax.jms.Session;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region.static}")
    private String region;

    @Value("${aws.sq.end-point}")
    private String serviceEndpoint;

    private SQSConnectionFactory sqsConnectionFactory;

    @Autowired
    private AmazonSQS amazonSQS;

    @PostConstruct
    public void init() {
        sqsConnectionFactory = createConnectionFactory();
    }
/*

    public SQSConnectionFactory createConnectionFactory(){

        //[TRACING SQS] [step 3] Configure Tracing and SqsMessageTracing
        Tracing current = Tracing.current();
        SqsMessageTracing sqsMessageTracing = SqsMessageTracing.create(current);

        final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AmazonSQS sqs = AmazonSQSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, region))
                //[TRACING SQS] [step 4]
                .withCredentials(credentialsProvider).withRequestHandlers(sqsMessageTracing.requestHandler())
                .build();

        return new SQSConnectionFactory(new ProviderConfiguration(), sqs);
    }
*/
    //v2
    public SQSConnectionFactory createConnectionFactory(){
        return new SQSConnectionFactory(new ProviderConfiguration(), amazonSQS);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsConnectionFactory);
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate(){
        return new JmsTemplate(sqsConnectionFactory);
    }



}

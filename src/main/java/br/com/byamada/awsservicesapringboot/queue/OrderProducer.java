//[AWS-SQS] [Step 4] Creating a SQS Producer
package br.com.byamada.awsservicesapringboot.queue;

import br.com.byamada.awsservicesapringboot.dto.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderProducer {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    //@Scheduled(fixedDelay = 10000)
    public void sendOrderCreationMessage() {

        log.info("Starting produce message method");
        OrderRequest orderRequest = OrderRequest.builder()
                .document("1233456")
                .amount(BigDecimal.valueOf(1200, 00))
                .build();

        try {
            log.info("Producing message");
            jmsTemplate.convertAndSend("order-registration", objectMapper.writeValueAsString(orderRequest));
        }catch (Exception ex){
            log.error("Error trying produce message: {}", orderRequest);
            log.error("Error trying produce message, erro: {}", ex.getMessage());
        }
    }

}

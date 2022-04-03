//[AWS-SQS] [Step 5] Listening the message
package br.com.byamada.awsservicesapringboot.queue;

import br.com.byamada.awsservicesapringboot.dto.OrderRequest;
import br.com.byamada.awsservicesapringboot.model.Order;
import br.com.byamada.awsservicesapringboot.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderListener {

    private final ObjectMapper objectMapper;
    private final OrderService service;

    @JmsListener(destination = "order-registration")
    public void orderValidator(@Headers Map<String,Object> messageAtributes, @Payload String message) throws Exception{
        log.info("*** A message was received!");
        try {
            OrderRequest orderRequest = objectMapper.readValue(message, OrderRequest.class);
            orderRequest.validate();
            Order order = new Order(orderRequest);
            service.saveOrder(order)
                    .orElseThrow(() -> new RuntimeException("Order was not saved"));
            log.info("Order received = " + order);
        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
        }
    }
}

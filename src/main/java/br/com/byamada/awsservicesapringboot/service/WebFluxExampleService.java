//[WEBFLUX] Making a call with Webclient
package br.com.byamada.awsservicesapringboot.service;

import br.com.byamada.awsservicesapringboot.dto.CustomerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebFluxExampleService {

    private final WebClient webClient;

    //@Scheduled(fixedDelay = 10000)
    public void getAllCustomer() {

        log.info("Calling customer service");
        CustomerRequest customer = webClient
                .get()
                .uri("http://localhost:8087/customer/1")
                .headers(header -> header.setBasicAuth("YWRtaW5AZW1haWwuY29tOmFkbWlu"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CustomerRequest.class)
                .block();

        log.info("Customers received: {}", customer.getName());

    }
}

package br.com.byamada.awsservicesapringboot.service;

import br.com.byamada.awsservicesapringboot.metrics.MetricRegistrion;
import br.com.byamada.awsservicesapringboot.model.Order;
import br.com.byamada.awsservicesapringboot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final MetricRegistrion metricRegistrion;

    public Optional<Order> saveOrder(Order order) {
        //[AWS - ClOUDWATCH] [STEP 4] Registring a metric
        metricRegistrion.registerOrderCreationMetric();
        return Optional.ofNullable(repository.save(order));
    }


}

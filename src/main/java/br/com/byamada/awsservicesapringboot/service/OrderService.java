package br.com.byamada.awsservicesapringboot.service;

import br.com.byamada.awsservicesapringboot.model.Order;
import br.com.byamada.awsservicesapringboot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public Optional<Order> saveOrder(Order order) {
        return Optional.ofNullable(repository.save(order));
    }


}

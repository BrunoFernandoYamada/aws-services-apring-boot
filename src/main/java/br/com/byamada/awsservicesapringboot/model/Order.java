package br.com.byamada.awsservicesapringboot.model;

import br.com.byamada.awsservicesapringboot.dto.OrderRequest;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID orderNumber;
    private String document;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    public Order() {
    }

    public Order(OrderRequest orderRequest) {
        UUID uuid = UUID.randomUUID();
        this.orderNumber = uuid;
        this.document = orderRequest.getDocument();
        this.amount = orderRequest.getAmount();
        this.status = OrderStatusEnum.OPEN;
    }
}

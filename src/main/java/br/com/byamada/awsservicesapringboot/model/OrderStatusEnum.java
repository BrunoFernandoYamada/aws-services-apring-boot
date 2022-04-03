package br.com.byamada.awsservicesapringboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    OPEN("Aberta"),
    CANCELED("Cancelada"),
    PAID("Pago");

    private String description;

    public String getName(){
        return this.name();
    }

}

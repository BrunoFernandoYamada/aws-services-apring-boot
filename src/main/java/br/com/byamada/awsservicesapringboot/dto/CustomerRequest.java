package br.com.byamada.awsservicesapringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    private Long customerId;

    private String name;

    private String document;

    private int age;

    private Date creationDate;

    private Date updateDate;

    private String userOperation;

}

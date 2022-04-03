package br.com.byamada.awsservicesapringboot.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class OrderRequest {

    private String document;
    private BigDecimal amount;

    public Boolean validate(){
        if (StringUtils.isEmpty(document) || !(amount.compareTo(BigDecimal.ZERO) > 0) ){
            return false;
        }
        return true;
    }

}

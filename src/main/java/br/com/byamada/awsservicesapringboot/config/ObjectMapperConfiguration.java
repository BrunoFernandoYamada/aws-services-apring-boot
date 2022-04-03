package br.com.byamada.awsservicesapringboot.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper(){
         return new ObjectMapper()
                 .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                 .configure(SerializationFeature.INDENT_OUTPUT, true)
                 .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                 .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                 .findAndRegisterModules();
    }
}

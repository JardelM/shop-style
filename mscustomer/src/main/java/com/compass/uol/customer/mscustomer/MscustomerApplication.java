package com.compass.uol.customer.mscustomer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class MscustomerApplication {

    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }
    public static void main(String[] args) {
        SpringApplication.run(MscustomerApplication.class, args);
    }

}

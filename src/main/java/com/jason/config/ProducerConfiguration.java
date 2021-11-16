package com.jason.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfiguration {

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("back");
    }

}

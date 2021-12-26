package com.jason.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This is the Rabbit MQ Producer Config.
 *
 * It injects the Bean of the FanoutExchange named "back" to the Spring
 * @author Jason(Jiasehng Xu)
 * @since 2021-11-03
 * */
@Configuration
public class ProducerConfiguration {

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("back");
    }

}

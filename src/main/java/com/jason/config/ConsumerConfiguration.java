package com.jason.config;



import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfiguration {

    @Bean
    public Queue createQueue(){
        return new Queue("sensorDataTest");
    }


}

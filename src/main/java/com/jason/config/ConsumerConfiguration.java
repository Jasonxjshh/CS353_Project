package com.jason.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This is the Rabbit MQ Consumer Config.
 * It injects the Bean of the Queue named "sensorData" to the Spring.
 *
 * @author Jason(Jiasehng Xu)
 * @since 2021-11-03
 * */
@Configuration
public class ConsumerConfiguration {

    @Bean
    public Queue createQueue(){
        return new Queue("sensorData");
    }

}

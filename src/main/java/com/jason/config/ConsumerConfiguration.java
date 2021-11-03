package com.jason.config;



import org.apache.catalina.filters.CorsFilter;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class ConsumerConfiguration {

    @Bean
    public Queue createQueue(){
        return new Queue("sensorDataTest");
    }

}

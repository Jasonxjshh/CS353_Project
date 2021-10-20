package com.jason.cs353_project;

import com.jason.controller.SensorDataTestController;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Cs353ProjectApplicationTests {
    @Autowired
    SensorDataTestController sensorDataTestController;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void test(){
        rabbitTemplate.convertAndSend("sensorDataTest","Hello");
    }
}

package com.jason.cs353_project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.controller.SensorDataController;

import com.jason.mapper.SensorDataMapper;
import com.jason.pojo.SensorData;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.sql.Wrapper;
import java.util.Date;
import java.util.Timer;

@SpringBootTest
class Cs353ProjectApplicationTests {
    @Autowired
    SensorDataController sensorDataController;
    @Autowired
    FanoutExchange fanoutExchange;

    @Autowired
    SensorDataMapper sensorDataMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void test(){
        rabbitTemplate.convertAndSend("sensorData","Hello");
    }

    @Test
    public void test1(){
        rabbitTemplate.convertAndSend(fanoutExchange.getName(),"backMessage","Data55");
    }
    @Test
    public void test2(){
        Integer integer = sensorDataMapper.selectCount(new QueryWrapper<SensorData>());
        System.out.println(integer);
    }
}

package com.jason.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jason.mapper.SensorDataTestMapper;
import com.jason.pojo.SensorDataTest;
import com.jason.service.SensorDataTestService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jason
 * @since 2021-10-16
 */
@RestController

public class SensorDataTestController {

    @Autowired
    SensorDataTestMapper sensorDataTestMapper;

    @RabbitListener(queues = "sensorDataTest")
    @RabbitHandler
    public void receiveData(Message message){
        String msg = new String(message.getBody());
        System.out.println(message);
        SensorDataTest dataTest = JSON.parseObject(msg, SensorDataTest.class);
        System.out.println(dataTest);
        System.out.println(sensorDataTestMapper.insert(dataTest));
        System.out.println("============");
    }
}


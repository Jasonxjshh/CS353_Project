package com.jason.controller;


import com.alibaba.fastjson.JSON;
import com.jason.mapper.SensorDataMapper;
import com.jason.mapper.SensorDataTestMapper;
import com.jason.pojo.SensorData;
import com.jason.pojo.SensorDataTest;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jason
 * @since 2021-11-03
 */
@RestController
@CrossOrigin
@RequestMapping("/sensorData")
public class SensorDataController {
    @Autowired
    SensorDataMapper sensorDataMapper;

    @RabbitListener(queues = "sensorData")
    @RabbitHandler
    public void receiveData(Message message){
        String msg = new String(message.getBody());
        System.out.println(message);
        SensorData sensorData = JSON.parseObject(msg, SensorData.class);
        System.out.println(sensorData);
        System.out.println(sensorDataMapper.insert(sensorData));
        System.out.println("============");
    }


    @RequestMapping("/get")
    public String testData(){
        SensorData sensorData = sensorDataMapper.selectById(1);
        System.out.println(sensorData);
        String s = JSON.toJSONString(sensorData);
        System.out.println(s);
        return s;
    }

}


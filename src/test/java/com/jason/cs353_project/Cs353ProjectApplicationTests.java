package com.jason.cs353_project;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.controller.SensorDataController;

import com.jason.mapper.SensorDataMapper;
import com.jason.pojo.SensorData;
import com.jason.service.SensorDataService;
import com.jason.util.Result;
import com.jason.util.ResultUtils;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.sql.Wrapper;
import java.util.Date;
import java.util.List;
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
    SensorDataService sensorDataService;

    @Autowired
    RabbitTemplate rabbitTemplate;

//    @Test
//    public void test1(){
//        rabbitTemplate.convertAndSend(fanoutExchange.getName(),"backMessage","Data55");
//    }
    @Test
    public void test2(){
        Integer integer = sensorDataMapper.selectCount(new QueryWrapper<SensorData>());
        System.out.println(integer);
    }
    @Test
    public void test3(){
        List<SensorData> sensorData = sensorDataService.showAll();
        for (SensorData sensorDatum : sensorData) {
            long captureTime = sensorDatum.getCaptureDateTime().getTime();
            if (captureTime-(new Date().getTime())>7000||captureTime-(new Date().getTime())<2000){
                sensorDatum.setStatus("400");
            }
            System.out.println(sensorDatum);
        }
        Result<Object> result = ResultUtils.success(sensorData);
        System.out.println(JSON.toJSONString(result));

//        SensorData sensorData = sensorDataService.selectOne22(40);
//        System.out.println(sensorData);
    }

    @Test
    public void test4(){
        SensorData sensorData = sensorDataService.selectBySensorName("SCD30_Jason");
        Result<Object> success = ResultUtils.success(sensorData);
        System.out.println(success);
        String s = JSON.toJSONString(success);
        System.out.println(s);
    }
}

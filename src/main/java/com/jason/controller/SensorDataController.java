package com.jason.controller;


import com.alibaba.fastjson.JSON;
import com.jason.mapper.SensorDataMapper;
import com.jason.pojo.SensorData;
import com.jason.service.SensorDataService;
import com.jason.util.Result;
import com.jason.util.ResultUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
    @Autowired
    SensorDataService sensorDataService;

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

    @RequestMapping("/showAll")
    public String showAll(){
        int count = 0;
        List<SensorData> sensorData = sensorDataService.showAll();
        for (SensorData sensorDatum : sensorData) {
            long captureTime = sensorDatum.getCaptureDateTime().getTime();
            if ((new Date().getTime())-captureTime>=4000||(new Date().getTime())-captureTime<=500){
                sensorDatum.setStatus("400");
            }else {
                count ++;
            }
        }
        int[] counts = new int[]{
                count,sensorData.size()
        };
        Result<Object> result = ResultUtils.success(sensorData);
        result.setMsg(JSON.toJSONString(counts));
        return  JSON.toJSONString(result);
    }


    @RequestMapping("/get/{sensorName}")
    public String getSensorNameData(@PathVariable String sensorName){
        SensorData sensorData = sensorDataService.selectBySensorName(sensorName);
        Result<Object> success = ResultUtils.success(sensorData);
        System.out.println(success);
        String s = JSON.toJSONString(success);
        System.out.println(s);
        return s;
    }

}


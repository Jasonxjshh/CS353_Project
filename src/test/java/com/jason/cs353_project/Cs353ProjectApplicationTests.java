package com.jason.cs353_project;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.controller.SensorDataController;

import com.jason.mapper.SensorDataMapper;
import com.jason.pojo.SensorData;
import com.jason.service.SensorDataService;
import com.jason.util.Result;
import com.jason.util.ResultUtils;
import javafx.scene.input.DataFormat;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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
//    public void test1() throws InterruptedException {
//        String[] s = new String[]{
//                "Data", "Close"
//        };
//        for (int i = 0; i < 6; i++) {
//            int index = (int) (Math.random()*s.length);
//            rabbitTemplate.convertAndSend(fanoutExchange.getName(),"backMessage",s[index]);
//            System.out.println(s[index]);
//            Thread.sleep(5000);
//        }
//        rabbitTemplate.convertAndSend(fanoutExchange.getName(),"backMessage","Kill");
//
//    }
    @Test
    public void test2(){
        Integer integer = sensorDataMapper.selectCount(new QueryWrapper<SensorData>());
        System.out.println(integer);
    }
    @Test
    public void test3(){
//        int count = 0;
//        List<SensorData> sensorData = sensorDataService.showAll();
//        for (int i=0;i<sensorData.size();i++) {
//            SensorData sensorDatum = sensorData.get(i);
//            long captureTime = sensorDatum.getCaptureDateTime().getTime();
//            if ((new Date().getTime())-captureTime>=4000||(new Date().getTime())-captureTime<=500){
//                if (sensorDatum.getSensorName().equals("SCD30_Jason")){
//                    sensorData.add(0,sensorDatum);
//                    i++;
//                }
//                sensorDatum.setStatus("400");
//            }else {
//                sensorData.add(0,sensorDatum);
//                count ++;
//            }
//        }
//        int[] counts = new int[]{
//                count,sensorData.size()
//        };
//        Result<Object> result = ResultUtils.success(sensorData);
//        result.setMsg(JSON.toJSONString(counts));
//        System.out.println(JSON.toJSONString(result));

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

    @Test
    public void test5(){
        System.out.println("Jason".compareTo("xjs"));
    }

    @Test
    public void test6(){
        Date start = new Date();
        Date end = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            start = df.parse("2021-11-16 20:24:00");
            end = df.parse("2021-11-19 19:37:10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double[] s = sensorDataService.toQueryPeriod("SCD30_Jason", start, end, 1);
        Map<String, double[]> data = new HashMap<>(3);
        data.put("pre",sensorDataService.toQueryPeriod("SCD30_Jason",start,end,1));
        data.put("post",sensorDataService.toQueryPeriod("SCD30_Jason",start,end,2));
        data.put("evn",sensorDataService.toQueryPeriod("SCD30_Jason",start,end,3));

        Result<Object> result = ResultUtils.success(data);
        String s1= JSON.toJSONString(result);
        System.out.println(s1);

    }
}

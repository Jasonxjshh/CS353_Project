package com.jason.controller;


import com.alibaba.fastjson.JSON;
import com.jason.mapper.SensorDataMapper;
import com.jason.pojo.SensorData;
import com.jason.pojo.SensorDataVo;
import com.jason.service.SensorDataService;
import com.jason.util.Result;
import com.jason.util.ResultUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  Front-end Controller
 * </p>
 *
 * @author Jason(Jiasehng Xu)
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

    /**
     * This is receiving method.
     * It listens to the "sensorData" queue and consume the message from it.
     * */
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

    /**
     * This is showAll request.
     * It will select all sensor in the database and
     * to give the front-end the information of whether the sensor if is alive
     * */
    @RequestMapping("/showAll")
    public String showAll(){
        int count = 0;
        List<SensorData> sensorData = sensorDataService.showAll();
        for (SensorData sensorDatum : sensorData) {
            //Traverse all the sensor to check whether it if alive.
            long captureTime = sensorDatum.getCaptureDateTime().getTime();
            if ((new Date().getTime())-captureTime>=4000||(new Date().getTime())-captureTime<=500){
                sensorDatum.setStatus("400");
            }else {
                //count the number of alive sensor.
                count ++;
            }
        }
        //This is to sort the Sensor to make sure that the front-end and display the sensor with
        //a particular order.
        //First to display the alive sensor by lexicographical order,
        //then to display the dead sensor also by lexicographical order.
        sensorData.sort(
                new Comparator<SensorData>() {
                    @Override
                    public int compare(SensorData o1, SensorData o2) {
                        if (Integer.parseInt(o1.getStatus())<Integer.parseInt(o2.getStatus())){
                            return -1;
                        }else if (Integer.parseInt(o1.getStatus())>Integer.parseInt(o2.getStatus())){
                            return 1;
                        }else {
                            if (o1.getSensorName().compareTo(o2.getSensorName())<0){
                                return -1;
                            }else if (o1.getSensorName().compareTo(o2.getSensorName())>0){
                                return 1;
                            }else {
                                return 0;
                            }
                        }
                    }
                }
        );
        int[] counts = new int[]{
                count,sensorData.size()
        };
        //To encapsulation the result and convent it to the Json String format.
        Result<Object> result = ResultUtils.success(sensorData);
        result.setMsg(JSON.toJSONString(counts));
        return  JSON.toJSONString(result);
    }
    /**
     * This is getSensorNameData request.
     * To get a data of the alive sensor by its sensor name.
     * */

    @RequestMapping("/get/{sensorName}")
    public String getSensorNameData(@PathVariable String sensorName){
        Result<Object> result;
        SensorData sensorData = sensorDataService.selectBySensorName(sensorName);
        if (sensorData!=null){
             result = ResultUtils.success(sensorData);
        }else {
            result = ResultUtils.Err(400, "No data");
        }
        System.out.println(result);
        //To encapsulation the result and convent it to the Json String format.
        String s = JSON.toJSONString(result);
        System.out.println(s);
        return s;
    }
    /**
     * This is getSpecificData request.
     * To get a specific data of the alive sensor by its sensor name and the requirement.
     * That mean this function return the information of the specific requirement and its
     * capture time.
     * */
    @RequestMapping("/toGet/{sensorName}/{require}")
    public String getSpecificData(@PathVariable String sensorName,@PathVariable String require){
        SensorData sensorData = sensorDataService.selectBySensorName(sensorName);
        SensorDataVo sensorDataVo = new SensorDataVo();

        //Deal with different requirement
        if (require.equals("co2")){
            sensorDataVo.setCaptureDateTime(sensorData.getCaptureDateTime());
            sensorDataVo.setData(sensorData.getPpm());
        }else if (require.equals("humidity")){
            sensorDataVo.setCaptureDateTime(sensorData.getCaptureDateTime());
            sensorDataVo.setData(sensorData.getHumidity());
        }else {
            sensorDataVo.setCaptureDateTime(sensorData.getCaptureDateTime());
            sensorDataVo.setData(sensorData.getTemperature());
        }
        //To encapsulation the result and convent it to the Json String format.
        Result<Object> result = ResultUtils.success(sensorDataVo);
        if (sensorData!=null){
            result = ResultUtils.success(sensorDataVo);
        }else {
            result = ResultUtils.Err(400, "No data");
        }
        System.out.println(result);
        String s = JSON.toJSONString(result);
        System.out.println(s);
        return s;
    }


    /**
     * This is toAnalyze request.
     *
     *
     *
     * In this function, it receives two datetime. One of them is start time and the
     * other is end time. Then it returns to the front-end about the three group
     * (pre post evn) average value of ppm, humidity and temperature.
     * */
    @RequestMapping("/toAnalyze/{sensorName}")
    public String toAnalyze(@PathVariable String sensorName,
                            @RequestParam("starttime") String startTime,
                            @RequestParam("endtime")String endTime){

        //To change the dateformat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = "";
        try {
            Date st = sdf.parse(startTime);
            Date et = sdf.parse(endTime);
            Map<String, double[]> data = new HashMap<>(3);
            //To query the data
            data.put("pre",sensorDataService.toQueryPeriod(sensorName,st,et,1));
            data.put("post",sensorDataService.toQueryPeriod(sensorName,st,et,2));
            data.put("evn",sensorDataService.toQueryPeriod(sensorName,st,et,3));
            //To encapsulation the result and convent it to the Json String format.
            Result<Object> result = ResultUtils.success(data);
            s = JSON.toJSONString(result);
            System.out.println(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }

    @RequestMapping("/changeState/{sensorName}")
    public String toChangeState(@PathVariable String sensorName,@RequestBody String postCode){
        System.out.println(sensorName);
        System.out.println(postCode);
        Result<Object> success = ResultUtils.success();
        return JSON.toJSONString(success);
    }

}


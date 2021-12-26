package com.jason.service.impl;

import com.jason.pojo.SensorData;
import com.jason.mapper.SensorDataMapper;
import com.jason.service.SensorDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jason
 * @since 2021-11-03
 */
@Service
public class SensorDataServiceImpl extends ServiceImpl<SensorDataMapper, SensorData> implements SensorDataService {
    @Autowired
    SensorDataMapper sensorDataMapper;

    @Override
    public List<SensorData> showAll() {
        return sensorDataMapper.showAll();
    }

    @Override
    public SensorData selectBySensorName(String sensorName) {
        return sensorDataMapper.selectBySensorName(sensorName);
    }

    @Override
    public SensorData selectSpecificData(String sensorName, String require) {
        return sensorDataMapper.selectSpecificData(sensorName, require);
    }

    @Override
    public double[] toQueryPeriod(String sensorName, Date startTime, Date endTime, int event) {
        Map<String, Double> map = sensorDataMapper.toQueryPeriod(sensorName, startTime, endTime, event);

        if (map==null){
            return new double[3];
        }else {
            double[] result = new double[3];
            result[0] = map.get("avg(ppm)");
            result[1] = map.get("avg(temperature)");
            result[2] = map.get("avg(humidity)");
            return result;
        }
    }


}

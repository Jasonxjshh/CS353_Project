package com.jason.service;

import com.jason.pojo.SensorData;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason
 * @since 2021-11-03
 */
@Service

public interface SensorDataService extends IService<SensorData> {

    List<SensorData> showAll();

    SensorData selectBySensorName(String sensorName);

    SensorData selectSpecificData(String sensorName, String require);

    double[] toQueryPeriod(String sensorName, Date startTime, Date endTime, int event);
}

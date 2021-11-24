package com.jason.service;

import com.jason.mapper.SensorDataMapper;
import com.jason.pojo.SensorData;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

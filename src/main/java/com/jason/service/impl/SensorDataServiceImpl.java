package com.jason.service.impl;

import com.jason.pojo.SensorData;
import com.jason.mapper.SensorDataMapper;
import com.jason.service.SensorDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


}

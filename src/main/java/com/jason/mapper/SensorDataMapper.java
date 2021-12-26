package com.jason.mapper;

import com.jason.pojo.SensorData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper Interface
 * </p>
 *
 * @author Jason
 * @since 2021-11-03
 */
@Mapper
@Repository
public interface SensorDataMapper extends BaseMapper<SensorData> {


    List<SensorData> showAll();

    SensorData selectBySensorName(String sensorName);

    SensorData selectSpecificData(String sensorName, String require);

    Map<String,Double> toQueryPeriod(String sensorName, Date startTime, Date endTime, int event);
}

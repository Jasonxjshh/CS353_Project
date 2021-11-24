package com.jason.mapper;

import com.jason.pojo.SensorData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
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
}

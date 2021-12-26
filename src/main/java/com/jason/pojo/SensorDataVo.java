package com.jason.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * <p>
 * This is the pojo class of sensor data VO
 * </p>
 *
 * @author Jason
 * @since 2021-11-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDataVo {
    private Date captureDateTime;
    private Float data;
}

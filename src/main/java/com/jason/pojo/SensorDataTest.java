package com.jason.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jason
 * @since 2021-10-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
  @EqualsAndHashCode(callSuper = false)
    @ApiModel(value="SensorDataTest对象", description="")
public class SensorDataTest implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String processorCode;

    private Date captureDataTime;

    private Integer ppm;

    private Integer temperature;

    private Integer humidity;


}

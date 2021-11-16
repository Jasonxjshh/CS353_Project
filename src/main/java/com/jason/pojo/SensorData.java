package com.jason.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-11-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
  @EqualsAndHashCode(callSuper = false)
    @ApiModel(value="SensorData对象", description="pojo")
public class SensorData implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "ID")
        @TableId(value = "ID", type = IdType.AUTO)
      private Integer id;

    @TableField("json_message_ID")
    private String jsonMessageId;

    private String processorCode;

    private String processorShortname;

    private String processorModel;

    private String sensorName;

    private String captureType;

    private String dataCaptureEvent;

    private Date captureDateTime;

    private Integer eventIndex;

    private String sensateName;

    private Float ppm;

    private Float celcius;

    private Float temperature;

    private Float humidity;

    private String filtername;

    private Float longitude;

    private Float latitude;

    private String hsModel;

    private String hsManufacturer;

    private String hsDescription;

    private String fuelName;

    private String hstDescription;

    private String fuFilterName;

    private String fuFilterModel;

    @TableField("c_RFID")
    private String cRfid;

    private Date cInstalled;

    private String ctDescription;

    private String status;

    private Date ssmaTimestamp;


}

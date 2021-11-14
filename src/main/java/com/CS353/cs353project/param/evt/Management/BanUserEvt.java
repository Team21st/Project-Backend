package com.CS353.cs353project.param.evt.Management;

import com.CS353.cs353project.param.valid.ValidField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BanUserEvt {
    @ApiModelProperty(value = "用户编码", required = true)
    private String userNo;
    @ApiModelProperty(value = "封禁状态(0 正常, 1 封禁)", required = true)
    private Integer isBan;
    @ApiModelProperty(value = "封禁原因", required = false)
    private String banReason;

}

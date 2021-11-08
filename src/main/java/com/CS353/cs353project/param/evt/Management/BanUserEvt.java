package com.CS353.cs353project.param.evt.Management;

import com.CS353.cs353project.param.valid.ValidField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BanUserEvt {
    @ValidField(value = "用户编码", nullable = false)
    @ApiModelProperty(value = "用户编码", required = true)
    private String userNo;
    @ValidField(value = "封禁状态(0 正常, 1 封禁)", nullable = false)
    @ApiModelProperty(value = "封禁状态(0 正常, 1 封禁)", required = true)
    private Integer isBan;
}

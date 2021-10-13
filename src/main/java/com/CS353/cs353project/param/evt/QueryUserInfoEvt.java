package com.CS353.cs353project.param.evt;

import com.CS353.cs353project.param.valid.ValidField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryUserInfoEvt {
    @ValidField(value = "用户编码", nullable = false)
    @ApiModelProperty(value = "用户编码", required = true)
    private String userNo;
}

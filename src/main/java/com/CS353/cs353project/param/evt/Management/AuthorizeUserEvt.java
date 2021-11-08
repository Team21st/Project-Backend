package com.CS353.cs353project.param.evt.Management;

import com.CS353.cs353project.param.valid.ValidField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthorizeUserEvt {
    @ValidField(value = "用户编码", nullable = false)
    @ApiModelProperty(value = "用户编码", required = true)
    private String userNo;
    @ValidField(value = "认证状态(0 未认证, 1 认证中, 2 认证通过 , 3 认证失败)", nullable = false)
    @ApiModelProperty(value = "认证状态(0 未认证, 1 认证中, 2 认证通过 , 3 认证失败)", required = true)
    private Integer authentication;
}

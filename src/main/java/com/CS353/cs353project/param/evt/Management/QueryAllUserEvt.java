package com.CS353.cs353project.param.evt.Management;

import com.CS353.cs353project.param.in.QueryEvt;
import com.CS353.cs353project.param.valid.ValidField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryAllUserEvt extends QueryEvt {
    @ValidField(value = "用户邮箱", nullable = true)
    @ApiModelProperty(value = "用户邮箱", required = false)
    private String userEmail;
    @ValidField(value = "封禁状态 （0：正常， 1：封禁）", nullable = true)
    @ApiModelProperty(value = "封禁状态 （0：正常， 1：封禁）", required = false)
    private Integer isBan;
    @ValidField(value = "认证状态(0 未认证, 1 认证中, 2 认证通过 , 3 认证失败)", nullable = true)
    @ApiModelProperty(value = "认证状态(0 未认证, 1 认证中, 2 认证通过 , 3 认证失败)", required = false)
    private Integer authentication;
}

package com.CS353.cs353project.param.evt;

import com.CS353.cs353project.param.valid.ValidField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserPrivateInfoEvt {
    @ValidField(value = "账户名称", nullable = true)
    @ApiModelProperty(value = "账户名称", required = false)
    private String userName;
    @ValidField(value = "用户真实姓名", nullable = true)
    @ApiModelProperty(value = "用户真实姓名", required = false)
    private String userRealName;
    @ValidField(value = "用户生日", nullable = true)
    @ApiModelProperty(value = "用户生日", required = false)
    private Date birthday;
    @ValidField(value = "学号", nullable = true)
    @ApiModelProperty(value = "学号", required = false)
    private String sno;
    @ValidField(value = "学院", nullable = true)
    @ApiModelProperty(value = "学院", required = false)
    private String college;
    @ValidField(value = "用户简介", nullable = true)
    @ApiModelProperty(value = "用户简介", required = false)
    private String userInfo;

}

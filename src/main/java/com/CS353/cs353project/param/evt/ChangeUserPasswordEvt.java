package com.CS353.cs353project.param.evt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeUserPasswordEvt {
    //原密码
    @NotNull(message = "Old password cannot be empty")
    @ApiModelProperty(value = "原密码", required = true)
    private String oldPassword;
    //新密码
    @NotNull(message = "New password cannot be empty")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}

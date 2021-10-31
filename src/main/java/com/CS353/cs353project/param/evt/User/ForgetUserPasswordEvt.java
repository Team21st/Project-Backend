package com.CS353.cs353project.param.evt.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ForgetUserPasswordEvt {
    //用户邮箱
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Email format is incorrect")
    @ApiModelProperty(value = "用户邮箱", required = true)
    private String userEmail;
    //新密码
    @NotNull(message = "New password cannot be empty")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
    //验证码
    @NotBlank(message = "Code cannot be empty")
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}

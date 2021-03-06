package com.CS353.cs353project.param.evt.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserLoginEvt {

    //用户邮箱
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Email format is incorrect")
    @ApiModelProperty(value = "用户邮箱", required = true)
    private String userEmail;
    //用户密码
    @NotBlank(message = "Password cannot be empty")
    @ApiModelProperty(value = "用户密码", required = true)
    private String userPassword;


}
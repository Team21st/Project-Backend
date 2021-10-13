package com.CS353.cs353project.param.evt;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterEvt {

    //用户姓名
    @NotBlank(message = "User name cannot be empty")
    @Length(min = 1, max = 20, message = "Nickname length cannot exceed 20")
    @ApiModelProperty(value = "用户名称(限制长度20)", required = true)
    private String userName;
    //用户邮箱
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Email format is incorrect")
    @ApiModelProperty(value = "用户邮箱", required = true)
    private String userEmail;
    //用户密码
    @NotBlank(message = "Password cannot be empty")
    @ApiModelProperty(value = "用户密码", required = true)
    private String userPassword;
    //验证码
    @NotBlank(message = "Code cannot be empty")
    @ApiModelProperty(value = "验证码", required = true)
    private String code;

}

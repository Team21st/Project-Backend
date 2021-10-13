package com.CS353.cs353project.param.model;

import lombok.Data;

@Data
public class VerificationCodeModel {
    //加密验证码
    private String code;
    //验证码时效
    private String time;
}

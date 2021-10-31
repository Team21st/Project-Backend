package com.CS353.cs353project.param.model.Email;

import lombok.Data;

@Data
public class SendEmailModel {
    //邮箱
    private String email;
    //消息
    private String msg;
}

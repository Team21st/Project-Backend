package com.CS353.cs353project.param.evt.Message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SendOnShelveMsgEvt {
    //用户邮箱
    private String userEmail;
    //图书名称
    private String bookName;
}

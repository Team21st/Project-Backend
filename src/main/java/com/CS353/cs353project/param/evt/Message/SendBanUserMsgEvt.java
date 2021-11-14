package com.CS353.cs353project.param.evt.Message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SendBanUserMsgEvt {
    @ApiModelProperty(value = "用户邮箱",required = true)
    private String userEmail;
    @ApiModelProperty(value = "封禁原因（封禁时传）",required = false)
    private String banReason;

}

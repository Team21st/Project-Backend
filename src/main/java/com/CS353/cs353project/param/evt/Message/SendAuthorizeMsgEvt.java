package com.CS353.cs353project.param.evt.Message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SendAuthorizeMsgEvt {
    @ApiModelProperty(value = "1:通过验证,2:未通过验证")
    private Integer authorizeStatus;
    @ApiModelProperty(value = "用户邮箱",required = true)
    private String userEmail;
    @ApiModelProperty(value = "不通过用户认证的原因",required = false)
    private String reason;
}

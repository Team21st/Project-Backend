package com.CS353.cs353project.param.evt.Message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SendAuditFailedMsgEvt {
    @ApiModelProperty(value = "商品名称",required = true)
    private String commodityName;
    @ApiModelProperty(value = "用户邮箱",required = true)
    private String userEmail;
    @ApiModelProperty(value = "审核不通过原因",required = true)
    private String reason;
}

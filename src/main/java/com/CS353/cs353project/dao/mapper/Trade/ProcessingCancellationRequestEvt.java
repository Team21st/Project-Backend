package com.CS353.cs353project.dao.mapper.Trade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProcessingCancellationRequestEvt {
    @ApiModelProperty(value = "订单编码",required = true)
    private String orderNo;
    @ApiModelProperty(value = "进行的操作（0：同哟，1：拒绝）",required = true)
    private Integer operation;
    @ApiModelProperty(value = "拒绝理由",required = false)
    private String refuseReason;
}

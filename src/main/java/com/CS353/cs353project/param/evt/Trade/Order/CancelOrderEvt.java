package com.CS353.cs353project.param.evt.Trade.Order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CancelOrderEvt {
    @ApiModelProperty(value = "取消订单角色（0：买家，1：卖家）",required = true)
    private Integer operatorRole;
    @ApiModelProperty(value = "订单编码",required = true)
    private String orderNo;
    @ApiModelProperty(value = "取消订单理由",required = true)
    private String cancelReason;
}

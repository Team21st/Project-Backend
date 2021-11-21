package com.CS353.cs353project.param.evt.Trade.Order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteOrderRecordEvt {
    @ApiModelProperty(value = "操作人角色（0：买家【查看我下单的商品】，（1：卖家【查看买家订单】）,（2：管理员【查看所有订单】））",required = true)
    private Integer operatorRole;
    @ApiModelProperty(value = "订单编码",required = true)
    private String orderNo;
}

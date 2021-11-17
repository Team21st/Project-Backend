package com.CS353.cs353project.param.evt.Trade.Order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliverGoodEvt {
    @ApiModelProperty(value = "订单编码", required = true)
    private String orderNo;
}

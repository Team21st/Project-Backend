package com.CS353.cs353project.param.evt.Trade.ShoppingCart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PlaceCartOrderEvt {
    @ApiModelProperty(value = "批量下单的购物车商品编码数组",required = true)
    private String[] idArray;
    @ApiModelProperty(value = "收货地址", required = true)
    private String address;
    @ApiModelProperty(value = "收货人", required = true)
    private String consignee;
    @ApiModelProperty(value = "收货人手机号", required = true)
    private String phone;
    @ApiModelProperty(value = "期望送达时间From", required = false)
    private Date deTimeFrom;
    @ApiModelProperty(value = "期望送达时间To", required = false)
    private Date deTimeTo;
}

package com.CS353.cs353project.param.evt.Trade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
public class PlaceOrderEvt {
    @ApiModelProperty(value = "在架图书编码", required = true)
    private String bookNo;
    @ApiModelProperty(value = "卖家编码", required = true)
    private String sellerNo;
    @ApiModelProperty(value = "收货地址", required = true)
    private String address;
    @ApiModelProperty(value = "收货人", required = true)
    private String consignee;
    @ApiModelProperty(value = "收货人手机号", required = true)
    private String phone;
    @ApiModelProperty(value = "购买数量", required = true)
    private Integer num;
    @ApiModelProperty(value = "价格(单价x数量)", required = true)
    private Double price;
    @ApiModelProperty(value = "期望送达时间From", required = false)
    private Date deTimeFrom;
    @ApiModelProperty(value = "期望送达时间To", required = false)
    private Date deTimeTo;
}

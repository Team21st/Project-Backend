package com.CS353.cs353project.param.evt.Trade.ShoppingCart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddShoppingCartEvt {
    @ApiModelProperty(value = "图书编码", required = true)
    private String bookNo;
    @ApiModelProperty(value = "购买数目", required = true)
    private Integer num;
}

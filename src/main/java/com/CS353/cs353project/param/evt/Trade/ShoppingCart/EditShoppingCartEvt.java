package com.CS353.cs353project.param.evt.Trade.ShoppingCart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EditShoppingCartEvt {
    @ApiModelProperty(value = "购物车商品记录编号",required = true)
    private String id;
    @ApiModelProperty(value = "商品数量",required = false)
    private Integer num;
    @ApiModelProperty(value = "状态 （E：正常,D：删除）",required = false)
    private String status;
}

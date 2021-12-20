package com.CS353.cs353project.param.model.Trade;

import com.CS353.cs353project.bean.ShoppingCartBean;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryShoppingCartModel {
    //购物车内所有商品总价
    private String sum;
    //购物车列表
    private Map cartList;
}

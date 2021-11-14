package com.CS353.cs353project.param.evt.Trade;

import com.CS353.cs353project.param.in.QueryEvt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BuyerQueryOrderListEvt extends QueryEvt {
    @ApiModelProperty(value = "买家用户编码",required = false)
    private String buyerNo;
    @ApiModelProperty(value = "图书名称",required = false)
    private String bookName;
    @ApiModelProperty(value = "收货人邮箱",required = false)
    private String consignee;
    @ApiModelProperty(value = "订单状态(0 待发货(未发货),1 待收货(已发货),2 完成,3 申请取消,4 订单取消)",required = false)
    private Integer orderStatus;
    @ApiModelProperty(value = "排序类型（0:时间（最新在前），1时间（最旧在前）：，2：price金额（最多在前），3:price金额（最少在前）4：num购买数量（最多在前），5：num 购买数量（最少在前））",required = false)
    private Integer[] sortType;
}

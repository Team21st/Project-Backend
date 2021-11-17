package com.CS353.cs353project.param.evt.Trade;

import com.CS353.cs353project.param.in.QueryEvt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryBusinessEvt extends QueryEvt {
    @ApiModelProperty(value = "商家名称",required = false)
    private String sellerName;
    @ApiModelProperty(value = "排序方式,1(按创建时间最新排序)，2（按创建时间最久排序），3（按发布商品最少），4（按发布商品最多），5（按销售商品最多），6（按销售商品最少）",required = false)
    private Integer[] sortType;
}

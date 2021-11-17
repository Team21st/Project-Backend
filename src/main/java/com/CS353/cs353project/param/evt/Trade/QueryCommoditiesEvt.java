package com.CS353.cs353project.param.evt.Trade;

import com.CS353.cs353project.param.in.QueryEvt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryCommoditiesEvt extends QueryEvt {
    //图书编码
    @ApiModelProperty(value = "图书编码",required = false)
    private String bookNo;
    //书名
    @ApiModelProperty(value = "书名",required = false)
    private String bookName;
    //排序方式
    @ApiModelProperty(value = "排序方式,1(按时间最新排序)，2（按时间最久排序），3（按价格低到高排序），4（按价格高到低排序），5（按图书销量最多排序），6（按图书销量最少排序）",required = false)
    private Integer[] sortType;
    //商家名称
    @ApiModelProperty(value = "商家名称",required = false)
    private String sellerName;
}

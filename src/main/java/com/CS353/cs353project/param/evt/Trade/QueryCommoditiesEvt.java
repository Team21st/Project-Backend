package com.CS353.cs353project.param.evt.Trade;

import com.CS353.cs353project.param.in.QueryEvt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryCommoditiesEvt extends QueryEvt {
    //书名
    @ApiModelProperty(value = "书名",required = false)
    private String bookName;
    //排序方式
    @ApiModelProperty(value = "排序方式,1(按时间最新排序)，2（按价格低到高排序），3（按价格高到低排序），4（按图书销量排序）",required = false)
    private Integer sortType;
}

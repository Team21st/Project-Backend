package com.CS353.cs353project.param.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryEvt implements Serializable {
    @ApiModelProperty(value = "页数(QueryEvt)",required = false)
    private Integer queryPage = 1;
    @ApiModelProperty(value = "查询条数(QueryEvt)",required = false)
    private Integer querySize = 10;
}

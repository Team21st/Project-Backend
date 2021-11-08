package com.CS353.cs353project.param.evt.Management;

import com.CS353.cs353project.param.in.QueryEvt;
import com.CS353.cs353project.param.valid.ValidField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryAuditRecordsEvt extends QueryEvt {
    @ValidField(value = "审核状态", nullable = true)
    @ApiModelProperty(value = "审核状态(1 通过,0 审核中,2 审核不通过)", required = false)
    private String type;
}

package com.CS353.cs353project.param.evt.Management;

import com.CS353.cs353project.param.in.QueryEvt;
import com.CS353.cs353project.param.valid.ValidField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryAuditRecordsEvt extends QueryEvt {
    @ValidField(value = "审核状态", nullable = false)
    @ApiModelProperty(value = "审核状态", required = true)
    private String type;
}

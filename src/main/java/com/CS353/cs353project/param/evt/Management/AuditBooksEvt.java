package com.CS353.cs353project.param.evt.Management;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuditBooksEvt {
    @ApiModelProperty(value = "图书编码",required = true)
    private String bookNo;
    @ApiModelProperty(value = "审核状态(1 通过,0 审核中,2 审核不通过)",required = true)
    private String auditStatus;
    @ApiModelProperty(value = "审核不通过原因",required = false)
    private String reason;
}

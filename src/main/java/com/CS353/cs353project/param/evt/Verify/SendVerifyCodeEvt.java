package com.CS353.cs353project.param.evt.Verify;

import com.CS353.cs353project.param.valid.ValidField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SendVerifyCodeEvt {
    @ValidField(value = "用户编码", nullable = false)
    @ApiModelProperty(value = "用户编码", required = true)
    private String userNo;
    @ValidField(value = "用户邮箱", nullable = false)
    @ApiModelProperty(value = "用户邮箱", required = true)
    private String email;
}

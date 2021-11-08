package com.CS353.cs353project.param.evt.Trade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BookOffShelveEvt {
    //图书编码
    @NotBlank(message = "book number can not be empty")
    @ApiModelProperty(value = "图书编码", required = true)
    private String bookNo;
    //用户身份
    @NotBlank(message = "user role can not be empty")
    @ApiModelProperty(value = "用户身份 (管理员：1， 普通用户：0)", required = true)
    private Integer userRole;
    //下架原因,管理员下架书本时才传
    @ApiModelProperty(value = "下架原因,管理员下架书本时才传（管理员下架时此项必传）", required = false)
    private String reason;
}

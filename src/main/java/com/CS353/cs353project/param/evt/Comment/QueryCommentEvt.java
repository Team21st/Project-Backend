package com.CS353.cs353project.param.evt.Comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryCommentEvt {
    @ApiModelProperty(value = "图书编码(查看对应图书下的评论)(图书页面)",required = false)
    private String bookNo;
    @ApiModelProperty(value = "查看是否被删除的评论（E:启用，D：已被删除[管理员]）",required = false)
    private String status;
    @ApiModelProperty(value = "被举报次数范围（最小值）",required = false)
    private Integer min;
    @ApiModelProperty(value = "被举报次数范围（最大值）",required = false)
    private Integer max;
}

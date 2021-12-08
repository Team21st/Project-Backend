package com.CS353.cs353project.param.evt.Comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportCommentEvt {
    @ApiModelProperty(value = "评论编号",required = true)
    private String commentNo;
    @ApiModelProperty(value = "举报理由",required = true)
    private String reason;
}

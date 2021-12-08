package com.CS353.cs353project.param.evt.Comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PublishBookReviewEvt {
    @ApiModelProperty(value = "图书编码",required = true)
    private String bookNo;
    @ApiModelProperty(value = "评论内容",required = true)
    private String content;
}

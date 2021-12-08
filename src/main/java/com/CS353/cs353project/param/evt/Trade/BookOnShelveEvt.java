package com.CS353.cs353project.param.evt.Trade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class BookOnShelveEvt {
    //图书名称
    @NotBlank(message = "book can not be empty")
    @ApiModelProperty(value = "图书名称", required = true)
    private String bookName;
    //图书描述
    @NotBlank(message = "book description can not be empty")
    @ApiModelProperty(value = "图书描述", required = true)
    private String bookDesc;
    //图书价格
    @NotBlank(message = "book price can not be empty")
    @ApiModelProperty(value = "图书价格", required = true)
    private double bookPrice;
    //图书新旧程度
    @NotBlank(message = "New and old degree of book can not be empty")
    @ApiModelProperty(value = "图书新旧程度", required = true)
    private Integer newOldDegree;
    //图书标签(0 文学,1 随笔,2 历史,3 科幻,4 奇幻,5 悬疑,6 推理,7 哲学,8 工具,9 专业知识)
    @NotBlank(message = "the tag of book can not be empty")
    @ApiModelProperty(value = "图书标签(0 文学,1 随笔,2 历史,3 科幻,4 奇幻,5 悬疑,6 推理,7 哲学,8 工具,9 专业知识)", required = true)
    private String bookTag;
    //图书库存
    @NotBlank(message = "stock of book can not be empty")
    @ApiModelProperty(value = "图书库存", required = true)
    private Integer bookStock;
    //自定义标签
    @ApiModelProperty(value = "自定义标签", required = false)
    private String customTags;
    @ApiModelProperty(value = "认证图片（至少一张最多四张", required = true)
    MultipartFile[] file;

}

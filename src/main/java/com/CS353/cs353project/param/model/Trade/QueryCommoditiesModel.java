package com.CS353.cs353project.param.model.Trade;


import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class QueryCommoditiesModel {
    //图书编码
    private String bookNo;
    //图书名称
    private String bookName;
    //商家名称
    private String sellerName;
    //商家用户编码
    private String sellerNo;
    //图书标签(0 文学,1 随笔,2 历史,3 科幻,4 奇幻,5 悬疑,6 推理,7 哲学,8 工具,9 专业知识)
    private String  bookTag;
    //图书描述
    private String bookDesc;
    //图书价格
    private Double bookPrice;
    //图书销量
    private Integer bookSale;
    //图书库存
    private Integer bookStock;
    //推荐(0 未推荐, 1 推荐)(首页展示)
    private Integer recommend;
    //自定义标签
    private String customTags;
    //创建时间
    private Date createTime;
    //图书新旧程度(ex:九五新:95)
    private Integer newOldDegree;
    //进行处理过的价格
    private String truePrice;
    //图书实物图地址List
    private List<String> picUrlBackList;

}

package com.CS353.cs353project.param.model.User;

import lombok.Data;

@Data
public class QueryMyCommodityModel {
    //上架书本序号
    public String bookNo;
    //书名
    public String bookName;
    //书本图片
    public String bookPicUrl;
    //书本描述
    public String bookDesc;
    //书本价格
    public double bookPrice;
    //新旧程度
    public Integer newOldDegree;
    //书本库存
    public Integer bookStock;
    //创建时间
    public String createTime;
    //书本标签
    public String bookTag;
    //审核状态
    public String auditStatus;
    //真实返回书本价格
    public String truePrice;
}

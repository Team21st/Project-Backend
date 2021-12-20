package com.CS353.cs353project.param.model.Management;

import lombok.Data;

import java.util.List;

@Data
public class QueryAuditRecordsModel {
    //书本编码
    private String bookNo;
    //书名
    private String bookName;
    //书本图片
    private String bookPicUrl;
    //书本描述
    private String bookDesc;
    //书本价格
    private double bookPrice;
    //新旧程度
    private Integer newOldDegree;
    //书本库存
    private Integer bookStock;
    //创建人
    private String createUser;
    //创建时间
    private String createTime;
    //书本标签
    private String bookTag;
    //审核状态
    private String auditStatus;
    //真实返回书本价格
    private String truePrice;
    //审核图书的图片
    private List<String> picList;
}

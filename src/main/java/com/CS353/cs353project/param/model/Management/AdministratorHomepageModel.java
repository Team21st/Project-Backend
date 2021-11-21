package com.CS353.cs353project.param.model.Management;

import lombok.Data;

@Data
public class AdministratorHomepageModel {
    //用户姓名
    private String userName;
    //用户真实姓名
    private String userRealName;
    //用户院系
    private String college;
    //用户UUID
    private String userNo;
    //用户角色
    private String userRoot;
    //用户邮箱
    private String userEmail;
    //-------------------------------------------
    //用户总数
    private Integer totalUserNum;
    //订单总数
    private Integer totalOrderNum;
    //总销售额(已经拼接完成)
    private String totalSales;
    //每日登录统计
    private Integer totalLoginNum;
    //各分区商品数
    private Integer totalBookNum;
}

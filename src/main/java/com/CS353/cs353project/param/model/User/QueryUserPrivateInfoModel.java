package com.CS353.cs353project.param.model.User;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class QueryUserPrivateInfoModel {
    //头像地址
    private String profileUrl;
    //用户名称
    private String userName;
    //用户真实姓名
    private String userRealName;
    //用户生日
    private Date birthday;
    //学号
    private String sno;
    //学院
    private String college;
    //用户简介
    private String userInfo;
}

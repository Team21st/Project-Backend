package com.CS353.cs353project.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "t_user")
@TableName(value = "t_user")
public class UserBean {
    //用户编码
    @Id
    @TableId("userNo")
    @Column(name = "userNo", length = 32, nullable = false)
    private String userNo;
    //用户名称
    @Column(name = "userName", length = 128, nullable = false)
    private String userName;
    //用户邮箱
    @Column(name = "userEmail", length = 128)
    private String userEmail;
    //用户手机号
    @Column(name = "userPhone", length = 128)
    private String userPhone;
    //用户密码
    @Column(name = "userPassword", length = 128)
    private String userPassword;
    //用户简介
    @Column(name = "userInfo", length = 256)
    private String userInfo;
    //用户性别
    @Column(name = "userSex", length = 128)
    private String userSex;
    //用户真实姓名
    @Column(name = "userRealName", length = 128)
    private String userRealName;
    //头像地址
    @Column(name = "profileUrl", length = 256)
    private String profileUrl;
    //不合格商品数
    @Column(name = "unquaComm", nullable = false)
    private Integer unquaComm;
    //余额
    @Column(name = "balance")
    private Double balance;
    //封禁状态(0 正常, 1 封禁)
    @Column(name = "isBan", nullable = false)
    private Integer isBan;
    //用户权限(0 普通用户, 1 管理员)
    @Column(name = "userRoot", nullable = false)
    private Integer userRoot;
    //认证状态(0 未认证, 1 认证中, 2 认证通过 , 3 认证失败)
    @Column(name = "authentication", nullable = false)
    private Integer authentication;
    //学号
    @Column(name = "Sno", length = 128)
    private String sno;
    //学院
    @Column(name = "college", length = 128)
    private String college;
    //认证照片1
    @Column(name = "photoUrl1", length = 128)
    private String photoUrl1;
    //认证照片2
    @Column(name = "photoUrl2", length = 128)
    private String photoUrl2;
    //已发布商品数量
    @Column(name = "releaseCommNum",nullable = false)
    private Integer releaseCommNum;
    //已卖出商品数量
    @Column(name = "soldCommNum",nullable = false)
    private Integer soldCommNum;
    //最近登录时间
    @Column(name = "lastLoginTime")
    private Date lastLoginTime;
    //状态
    @Column(name = "status", length = 128, nullable = false)
    private String status;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @Column(name = "createTime")
    private Date createTime;
    //创建人
    @Column(name = "createUser", length = 128)
    private String createUser;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(name = "updateTime")
    private Date updateTime;
    //更新人员
    @Column(name = "updateUser", length = 128)
    private String updateUser;
    //用户生日
    @Column(name = "birthday")
    private Date birthday;
    //用户总登录次数
    @Column(name = "loginNum", nullable = false)
    private Integer loginNum;
}

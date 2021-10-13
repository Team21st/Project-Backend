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
@Table(name = "t_withdrawal")
@TableName(value = "t_withdrawal")
public class WithdrawalBean {
    //申请单编码
    @Id
    @TableId("formNo")
    @Column(name = "formNo", length = 32, nullable = false)
    private String formNo;
    //真实姓名
    @Column(name = "realName", length = 128, nullable = false)
    private String realName;
    //户名
    @Column(name = "cardName", length = 128, nullable = false)
    private String cardName;
    //银行卡号
    @Column(name = "cardNo", length = 128, nullable = false)
    private String cardNo;
    //提现金额
    @Column(name = "money", nullable = false)
    private Double money;
    //审核状态(0 审核中, 1 通过, 2驳回)
    @Column(name = "auditStatus", nullable = false)
    private Integer auditStatus;
    //状态
    @Column(name = "status", length = 128, nullable = false)
    private String status;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @Column(name = "createTime")
    private Date createTime;
    //创建人
    @Column(name = "createUser", length = 32, nullable = false)
    private String createUser;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(name = "updateTime")
    private Date updateTime;
    //更新人员
    @Column(name = "updateUser", length = 128)
    private String updateUser;
}

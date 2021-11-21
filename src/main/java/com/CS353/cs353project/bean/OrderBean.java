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
@Table(name = "t_order")
@TableName(value = "t_order")
public class OrderBean {
    //订单编码
    @Id
    @TableId("orderNo")
    @Column(name = "orderNo", length = 32, nullable = false)
    private String orderNo;
    //在架图书编码
    @Column(name = "bookNo", length = 32, nullable = false)
    private String bookNo;
    //在架图书名称
    @Column(name = "bookName", length = 32, nullable = false)
    private String bookName;
    //卖家编码
    @Column(name = "sellerNo", length = 32, nullable = false)
    private String sellerNo;
    //买家编码
    @Column(name = "buyerNo", length = 32, nullable = false)
    private String buyerNo;
    //收货地址
    @Column(name = "address", length = 256, nullable = false)
    private String address;
    //收货人
    @Column(name = "consignee", length = 128, nullable = false)
    private String consignee;
    //收货人手机号
    @Column(name = "phone", length = 128, nullable = false)
    private String phone;
    //购买数量
    @Column(name = "num", nullable = false)
    private Integer num;
    //价格
    @Column(name = "price", length = 128, nullable = false)
    private Double price;
    //送达时间From
    @Column(name = "deTimeFrom")
    private Date deTimeFrom;
    //送达时间To
    @Column(name = "deTimeTo")
    private Date deTimeTo;
    //买家展示(0 展示, 1不展示)
    @Column(name = "buyerDisplay", nullable = false)
    private Integer buyerDisplay;
    //卖家展示(0 展示, 1不展示 )
    @Column(name = "sellerDisplay", nullable = false)
    private Integer sellerDisplay;
    //订单状态(0 待发货(未发货),1 待收货(已发货),2 完成,3 申请取消,4 订单取消)
    @Column(name = "orderStatus", nullable = false)
    private Integer orderStatus;
    //状态
    @Column(name = "status", length = 128, nullable = false)
    private String status;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @Column(name = "createTime")
    private Date createTime;
    //创建人
    @Column(name = "createUser", length = 128, nullable = false)
    private String createUser;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(name = "updateTime")
    private Date updateTime;
    //更新人员
    @Column(name = "updateUser", length = 128)
    private String updateUser;
    //订单取消原因
    @Column(name = "cancelReason", length = 256)
    private String cancelReason;
    //取消人
    @Column(name = "cancelOperator", length = 128)
    private String cancelOperator;
    //（取消人角色：【买家：0，卖家：1】）
    @Column(name = "cancelOperatorRole")
    private Integer cancelOperatorRole;

}

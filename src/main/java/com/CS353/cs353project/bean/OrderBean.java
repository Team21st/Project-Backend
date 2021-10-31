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
    //商品编码
    @Column(name = "commNo", length = 32, nullable = false)
    private String commNo;
    //卖家编码
    @Column(name = "sellerNo", length = 32, nullable = false)
    private String sellerNo;
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
    //买家展示(0 展示, 1不展示 )
    @Column(name = "buyerDisplay")
    private Integer buyerDisplay;
    //卖家展示(0 展示, 1不展示 )
    @Column(name = "sellerDisplay")
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
}

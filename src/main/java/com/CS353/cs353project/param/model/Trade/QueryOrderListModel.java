package com.CS353.cs353project.param.model.Trade;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class QueryOrderListModel {
    //订单编码
    private String orderNo;
    //在架图书编码
    private String bookNo;
    //在架图书名称
    private String bookName;
    //卖家编码
    private String sellerNo;
    //收货地址
    private String address;
    //收货人
    private String consignee;
    //收货人手机号
    private String phone;
    //购买数量
    private Integer num;
    //价格
    private Double price;
    //送达时间From
    private Date deTimeFrom;
    //送达时间To
    private Date deTimeTo;
    //买家展示(0 展示, 1不展示 )
    private Integer buyerDisplay;
    //卖家展示(0 展示, 1不展示 )
    private Integer sellerDisplay;
    //订单状态(0 待发货(未发货),1 待收货(已发货),2 完成,3 申请取消,4 订单取消)
    private Integer orderStatus;
    //创建时间
    private Date createTime;
    //创建人
    private String createUser;
    //拼接后的字符串
    private String turePrice;
}

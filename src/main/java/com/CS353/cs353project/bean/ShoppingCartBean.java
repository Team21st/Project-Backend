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
@Table(name = "t_shoppingCart")
@TableName(value = "t_shoppingCart")
public class ShoppingCartBean {
    //购物车内商品编号
    @Id
    @TableId("id")
    @Column(name = "id", length = 32, nullable = false)
    private String id;
    //买家用户编码
    @Column(name = "buyerNo", length = 32, nullable = false)
    private String buyerNo;
    //买家用户邮箱
    @Column(name = "buyerEmail", length = 128)
    private String buyerEmail;
    //图书编码
    @Column(name = "bookNo", length = 32, nullable = false)
    private String bookNo;
    //图书名称
    @Column(name = "bookName", length = 128, nullable = false)
    private String bookName;
    //商家名称
    @Column(name = "sellerName", length = 128, nullable = false)
    private String sellerName;
    //商家用户编码
    @Column(name = "sellerNo", length = 128, nullable = false)
    private  String sellerNo;
    //图书单价
    @Column(name = "bookPrice", length = 128, nullable = false)
    private Double bookPrice;
    //图书实物图地址1
    @Column(name = "bookPicUrl1", length = 128, nullable = true)
    private String bookPicUrl1;
    //图书实物图地址2
    @Column(name = "bookPicUrl2", length = 128, nullable = true)
    private String bookPicUrl2;
    //图书实物图地址3
    @Column(name = "bookPicUrl3", length = 128, nullable = true)
    private String bookPicUrl3;
    //图书实物图地址4
    @Column(name = "bookPicUrl4", length = 128, nullable = true)
    private String bookPicUrl4;
    //图书标签(0 文学,1 随笔,2 历史,3 科幻,4 奇幻,5 悬疑,6 推理,7 哲学,8 工具,9 专业知识)
    @Column(name = "bookTag", nullable = false)
    private String  bookTag;
    //图书新旧程度(ex:九五新:95)
    @Column(name = "newOldDegree")
    private Integer newOldDegree;
    //购买数量
    @Column(name = "num", nullable = false)
    private Integer num;
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
}

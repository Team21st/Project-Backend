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
@Table(name = "t_commodity")
@TableName(value = "t_commodity")
public class CommodityBean {
    //图书编码
    @Id
    @TableId("bookNo")
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
    //图书标签(0 文学,1 随笔,2 历史,3 科幻,4 奇幻,5 悬疑,6 推理,7 哲学,8 工具,9 专业知识)
    @Column(name = "bookTag", nullable = false)
    private String  bookTag;
    //图书描述
    @Column(name = "bookDesc", length = 512, nullable = false)
    private String bookDesc;
    //图书价格
    @Column(name = "bookPrice", length = 128, nullable = false)
    private Double bookPrice;
    //图书销量
    @Column(name = "bookSale", nullable = false)
    private Integer bookSale;
    //图书库存
    @Column(name = "bookStock",nullable = false)
    private Integer bookStock;
    //推荐(0 未推荐, 1 推荐)(首页展示)
    @Column(name = "recommend", nullable = false)
    private Integer recommend;
    //自定义标签
    @Column(name = "customTags", length = 256)
    private String customTags;
    //状态(E启用\D禁用)（商品状态）
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
    //审核状态(1 通过,0 审核中,2 审核不通过)
    @Column(name = "auditStatus", nullable = false)
    private String  auditStatus;
    //审核人
    @Column(name = "auditor", length = 128)
    private String auditor;
    //审核时间
    @Column(name = "auditTime")
    private Date auditTime;
    //审核留言
    @Column(name = "auditMsg", length = 256)
    private String auditMsg;
    //图书实物图地址
    @Column(name = "bookPicUrl", length = 128, nullable = false)
    private String bookPicUrl;
    //图书新旧程度(ex:九五新:95)
    @Column(name = "newOldDegree")
    private Integer newOldDegree;
}

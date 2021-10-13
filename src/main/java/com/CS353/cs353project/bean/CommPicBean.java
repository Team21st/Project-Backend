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
@Table(name = "t_commPic")
@TableName(value = "t_commPic")
public class CommPicBean {
    //商品图片编码
    @Id
    @TableId("commPicNo")
    @Column(name = "commPicNo", length = 32, nullable = false)
    private String commPicNo;
    //商品编码
    @Column(name = "commNo", length = 32, nullable = false)
    private String commNo;
    //图片地址
    @Column(name = "pictureUrl", length = 128, nullable = false)
    private String pictureUrl;
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

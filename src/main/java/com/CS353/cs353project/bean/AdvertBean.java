package com.CS353.cs353project.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_advert")
@TableName(value = "t_advert")
public class AdvertBean {
    //广告编码
    @Id
    @TableId(value = "advertNo", type = IdType.AUTO)
    @Column(name = "advertNo", length = 32, nullable = false)
    private Integer advertNo;
    //图片地址
    @Column(name = "picUrl", length = 128, nullable = false)
    private String picUrl;
    //链接
    @Column(name = "url", length = 128)
    private String url;
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

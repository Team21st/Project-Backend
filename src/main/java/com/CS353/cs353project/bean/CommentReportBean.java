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
@Table(name = "t_commentReport")
@TableName(value = "t_commentReport")
public class CommentReportBean {
    //举报编码
    @Id
    @TableId("reportNo")
    @Column(name = "reportNo", length = 32, nullable = false)
    private String reportNo;
    //评论编码
    @Column(name = "commentNo", length = 32, nullable = false)
    private String commentNo;
    //原因(限制长度100)
    @Column(name = "reason", length = 128, nullable = false)
    private String reason;
    //状态
    @Column(name = "status", length = 128, nullable = false)
    private String status;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @Column(name = "createTime")
    private Date createTime;
    //创建人
    @Column(name = "createUser", length = 32)
    private String createUser;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(name = "updateTime")
    private Date updateTime;
    //更新人员
    @Column(name = "updateUser", length = 128)
    private String updateUser;


}

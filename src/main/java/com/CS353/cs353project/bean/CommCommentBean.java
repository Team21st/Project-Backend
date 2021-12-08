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
@Table(name = "t_commComment")
@TableName(value = "t_commComment")
public class CommCommentBean {
    //评论编码
    @Id
    @TableId("commentNo")
    @Column(name = "commentNo", length = 32, nullable = false)
    private String commentNo;
    //图书
    @Column(name = "bookNo", length = 32, nullable = false)
    private String bookNo;
    //内容(限制长度80)
    @Column(name = "content", length = 256, nullable = false)
    private String content;
    //被举报次数
    @Column(name = "reportedNum", nullable = false)
    private Integer reportedNum;
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

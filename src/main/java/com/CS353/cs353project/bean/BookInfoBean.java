package com.CS353.cs353project.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_bookInfo")
@TableName(value = "t_bookInfo")
public class BookInfoBean {
    //序号
    @Id
    @TableId("serialNumber")
    @Column(name = "serialNumber", length = 32, nullable = false)
    private String serialNumber;
    //书名
    @TableId("bookName")
    @Column(name = "bookName", length = 128, nullable = false)
    private String bookName;
    //评分
    @TableId("score")
    @Column(name = "score", length = 32, nullable = true)
    private String score;
    //作者
    @TableId("author")
    @Column(name = "author", length = 128, nullable = false)
    private String author;
    //出版社信息
    @TableId("publisherInfo")
    @Column(name = "publisherInfo", length = 255, nullable = true)
    private String publisherInfo;
    //书籍图片
    @TableId("picture")
    @Column(name = "picture", length = 255, nullable = true)
    private String picture;
}

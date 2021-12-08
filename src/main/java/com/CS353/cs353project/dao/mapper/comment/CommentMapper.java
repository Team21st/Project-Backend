package com.CS353.cs353project.dao.mapper.comment;

import com.CS353.cs353project.bean.CommCommentBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<CommCommentBean> {

}

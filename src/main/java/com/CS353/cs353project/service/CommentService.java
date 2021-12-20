package com.CS353.cs353project.service;

import com.CS353.cs353project.bean.CommCommentBean;
import com.CS353.cs353project.bean.CommentReportBean;
import com.CS353.cs353project.bean.CommodityBean;
import com.CS353.cs353project.dao.mapper.comment.CommentMapper;
import com.CS353.cs353project.dao.mapper.Trade.CommodityMapper;
import com.CS353.cs353project.dao.mapper.comment.CommentReportMapper;
import com.CS353.cs353project.param.evt.Comment.PublishBookReviewEvt;
import com.CS353.cs353project.param.evt.Comment.QueryCommentEvt;
import com.CS353.cs353project.param.evt.Comment.ReportCommentEvt;
import com.CS353.cs353project.param.out.ServiceResp;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentReportMapper commentReportMapper;
    /**
     * 发表书评接口
     */
    public ServiceResp publishBookReview (HttpServletRequest request, PublishBookReviewEvt evt){
        String operatorEmail= (String) request.getAttribute("userEmail");
        CommodityBean bookInfo=commodityMapper.selectById(evt.getBookNo());
        if(bookInfo==null){
            return new ServiceResp().error("bookInfo not found");
        }
        CommCommentBean commInfo=new CommCommentBean();
        BeanUtils.copyProperties(evt,commInfo);
        commInfo.setCommentNo(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        commInfo.setReportedNum(0);
        commInfo.setStatus("E");
        commInfo.setCreateUser(operatorEmail);
        commInfo.setUpdateUser(operatorEmail);
        int result=commentMapper.insert(commInfo);
        if (result!=1){
            return new ServiceResp().error("create new comment error");
        }
        return new ServiceResp().success("publishBookReview successfully");
    }

    /**
     * 查看评论
     */
    public ServiceResp queryComment(QueryCommentEvt evt){
        QueryWrapper<CommCommentBean> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(evt.getBookNo())){
            queryWrapper.eq("bookNo",evt.getBookNo());
        }
        if(StringUtils.isNotBlank(evt.getStatus())){
            queryWrapper.eq("status",evt.getStatus());
        }
        if(evt.getMin()!=null&&evt.getMax()!=null){
            queryWrapper.between("reportedNum",evt.getMin(),evt.getMax());
        }
        List<CommCommentBean> model=commentMapper.selectList(queryWrapper);
        if(model==null){
            return new ServiceResp().error("query comment error");
        }
        return new ServiceResp().success(model);
    }


    /**
     * 举报评论接口
     */
    public ServiceResp reportComment (HttpServletRequest request, ReportCommentEvt evt){
        String operatorEmail=(String) request.getAttribute("userEmail");
        CommCommentBean commInfo=commentMapper.selectById(evt.getCommentNo());
        if (commInfo==null){
            return new ServiceResp().error("commInfo not found");
        }
        CommentReportBean commentReportInfo = new CommentReportBean();
        BeanUtils.copyProperties(evt,commentReportInfo);
        commentReportInfo.setReportNo(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        commentReportInfo.setStatus("E");
        commentReportInfo.setCreateUser(operatorEmail);
        commentReportInfo.setUpdateUser(operatorEmail);
        int result = commentReportMapper.insert(commentReportInfo);
        if (result!=1){
            return new ServiceResp().error("create new comment report error");
        }
        return new ServiceResp().success("create new comment report successfully");
    }

}

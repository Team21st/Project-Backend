package com.CS353.cs353project.controller;


import com.CS353.cs353project.param.evt.Comment.PublishBookReviewEvt;
import com.CS353.cs353project.param.evt.Comment.ReportCommentEvt;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论相关接口")
@CrossOrigin
@Validated
public class CommentController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CommentService commentService;

    /**
     * 发表书评接口
     */
    @ResponseBody
    @ApiOperation(value = "发表书评接口", notes = "")
    @RequestMapping(value = "/publishBookReview", method = RequestMethod.POST)
    public ServiceResp publishBookReview(HttpServletRequest request, @RequestBody PublishBookReviewEvt evt) {
        try {
            return commentService.publishBookReview(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("query audit records function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 举报评论接口
     */
    @ResponseBody
    @ApiOperation(value = "举报评论接口", notes = "")
    @RequestMapping(value = "/reportComment", method = RequestMethod.POST)
    public ServiceResp reportComment(HttpServletRequest request, @RequestBody ReportCommentEvt evt) {
        try {
            return commentService.reportComment(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("report comment function error");
            return new ServiceResp().error(e.getMessage());
        }
    }
}

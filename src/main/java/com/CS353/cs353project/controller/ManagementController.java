package com.CS353.cs353project.controller;

import com.CS353.cs353project.param.evt.Management.*;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.service.ManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/manage")
@Api(tags = "管理员相关接口")
@CrossOrigin
@Validated
public class ManagementController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ManagementService managementService;

    /**
     * 查询审核记录接口
     */
    @ResponseBody
    @ApiOperation(value = "查询审核记录接口", notes = "")
    @RequestMapping(value = "/queryAuditRecords", method = RequestMethod.POST)
    public ServiceResp queryAuditRecords(HttpServletRequest request, @RequestBody QueryAuditRecordsEvt evt) throws Exception {
        try {
            return managementService.queryAuditRecords(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("query audit records function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 展示所有用户
     */
    @ResponseBody
    @ApiOperation(value = "展示所有用户", notes = "")
    @RequestMapping(value = "/queryAllUsers", method = RequestMethod.POST)
    public ServiceResp queryAllUsers(@RequestBody QueryAllUserEvt evt) throws Exception {
        try {
            return managementService.queryAllUsers(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("query All Users records function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 设置用户封禁状态接口
     */
    @ResponseBody
    @ApiOperation(value ="设置用户封禁状态接口", notes="")
    @RequestMapping(value = "/banUser", method = RequestMethod.POST)
    public ServiceResp banUser(HttpServletRequest request,@RequestBody BanUserEvt evt){
        try {
            return managementService.banUser(request,evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ban  user function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 设置用户认证状态接口
     */
    @ResponseBody
    @ApiOperation(value ="设置用户认证状态接口", notes="")
    @RequestMapping(value = "/authorizeUser", method = RequestMethod.POST)
    public ServiceResp authorizeUser(HttpServletRequest request,@RequestBody AuthorizeUserEvt evt){
        try {
            return managementService.authorizeUser(request,evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("authorize  user function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 通过\拒绝审核
     */
    @ResponseBody
    @ApiOperation(value ="通过\\拒绝审核", notes="")
    @RequestMapping(value = "/auditBooks", method = RequestMethod.POST)
    public ServiceResp auditBooks(HttpServletRequest request,@RequestBody AuditBooksEvt evt){
        try {
            return managementService.auditBooks(request,evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("audit  books function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 管理员首页展示
     */
    @ResponseBody
    @ApiOperation(value ="管理员首页展示", notes="")
    @RequestMapping(value = "/administratorHomepageDisplay", method = RequestMethod.POST)
    public ServiceResp administratorHomepageDisplay(HttpServletRequest request){
        try {
            return managementService.administratorHomepageDisplay(request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("administrator Homepage Display function error");
            return new ServiceResp().error("System error");
        }
    }

}

package com.CS353.cs353project.controller;

import com.CS353.cs353project.param.evt.Management.QueryAuditRecordsEvt;
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
     * 上架图书接口(提交审核)
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
}

package com.CS353.cs353project.controller;

import com.CS353.cs353project.anotation.PassToken;
import com.CS353.cs353project.param.evt.Management.AdminRegisterEvt;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员相关接口")
@CrossOrigin
@Validated
public class AdminController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdminService adminService;

    /**
     * 管理员注册
     */
    @PassToken
    @ApiOperation("管理员注册接口")
    @RequestMapping(value = "/adminRegister", method = RequestMethod.POST)
    public ServiceResp adminRegister(@RequestBody @Validated AdminRegisterEvt evt) {
        try {
            return new ServiceResp().success(adminService.adminRegister(evt));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Admin registration function error");
            return new ServiceResp().error("System error");
        }
    }
}

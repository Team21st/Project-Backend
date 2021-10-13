package com.CS353.cs353project.controller;

import com.CS353.cs353project.anotation.PassToken;
import com.CS353.cs353project.param.evt.ChangeUserPasswordEvt;
import com.CS353.cs353project.param.evt.UserLoginEvt;
import com.CS353.cs353project.param.evt.UserRegisterEvt;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
@CrossOrigin
@Validated
public class UserController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PassToken
    @ApiOperation("用户注册接口")
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public ServiceResp userRegister(@RequestBody @Validated UserRegisterEvt evt) {
        try {
            return new ServiceResp().success(userService.userRegister(evt));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("User registration function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 用户登录
     */
    @PassToken
    @ApiOperation("用户登录接口")
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public ServiceResp userLogin(@RequestBody @Validated UserLoginEvt evt) {
        try {
            return userService.userLogin(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("User login function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 修改密码,已经登录的情况下？
     */
    @ApiOperation("修改密码接口")
    @RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST)
    public ServiceResp changePassword(HttpServletRequest request, @RequestBody @Validated ChangeUserPasswordEvt evt) {
        try {
            return userService.changeUserPassword(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Password modification function error");
            return new ServiceResp().error("System error");
        }
    }



}

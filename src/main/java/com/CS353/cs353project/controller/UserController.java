package com.CS353.cs353project.controller;

import com.CS353.cs353project.anotation.PassToken;
import com.CS353.cs353project.param.evt.User.*;
import com.CS353.cs353project.param.in.QueryEvt;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

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
            return userService.userRegister(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("User registration function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 判断用户名是否重名
     */
    @PassToken
    @ApiOperation("判断用户名是否重名接口")
    @RequestMapping(value = "/judgeUserName", method = RequestMethod.POST)
    public ServiceResp judgeUserName(@RequestBody @ApiParam("用户名") @NotBlank(message = "user name can not be blank") String userName) {
        try {
            return userService.judgeUserName(userName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("judge User Name function error");
            return new ServiceResp().error(e.getMessage());
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
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 修改密码(在已经登录的情况下)
     */
    @ApiOperation("修改密码(在已经登录的情况下)")
    @RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST)
    public ServiceResp changePassword(HttpServletRequest request, @RequestBody @Validated ChangeUserPasswordEvt evt) {
        try {
            return userService.changeUserPassword(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Password modification function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 修改密码(在忘记密码的情况下)
     */
    @PassToken
    @ApiOperation("修改密码(在忘记密码的情况下)")
    @RequestMapping(value = "/forgetUserPassword", method = RequestMethod.POST)
    public ServiceResp forgetUserPassword(@RequestBody @Validated ForgetUserPasswordEvt evt) {
        try {
            return userService.forgetUserPassword(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("forget user password function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 上传头像
     */
    @ResponseBody
    @ApiOperation(value = "上传头像接口", notes = "")
    @RequestMapping(value = "/uploadHeadPortrait", method = RequestMethod.POST)
    public ServiceResp uploadHeadPortrait(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        try {
            return userService.uploadHeadPortrait(request, file);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("upload head portrait function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 获取用户个人信息
     */
    @ResponseBody
    @ApiOperation(value = "获取用户个人信息接口", notes = "")
    @RequestMapping(value = "/queryUserPrivateInfo", method = RequestMethod.POST)
    public ServiceResp queryUserPrivateInfo(HttpServletRequest request)  {
        try {
            return userService.queryUserPrivateInfo(request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("query user privateInfo function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 修改用户个人信息（除了头像）
     */
    @ResponseBody
    @ApiOperation(value = "修改用户个人信息接口（除了头像）", notes = "")
    @RequestMapping(value = "/updateUserPrivateInfo", method = RequestMethod.POST)
    public ServiceResp updateUserPrivateInfo(HttpServletRequest request, @RequestBody UpdateUserPrivateInfoEvt evt)  {
        try {
            return userService.updateUserPrivateInfo(request,evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("update user privateInfo function error");
            return new ServiceResp().error(e.getMessage());
        }
    }


    /**
     * 用户申请认证
     */
    @ResponseBody
    @ApiOperation(value = "用户申请认证",notes = "")
    @RequestMapping(value = "/applyForCertification",method = RequestMethod.POST)
    public ServiceResp applyForCertification (HttpServletRequest request){
        try {
            return userService.applyForCertification(request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("apply For Certification function error");
            return new ServiceResp().error(e.getMessage());
        }
    }
}

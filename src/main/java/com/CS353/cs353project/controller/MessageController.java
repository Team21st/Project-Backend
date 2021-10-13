package com.CS353.cs353project.controller;

import com.CS353.cs353project.anotation.PassToken;
import com.CS353.cs353project.async.JmsProducer;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/message")
@Api(tags = "通知相关接口")
@CrossOrigin
@Validated
public class MessageController {


    @Autowired
    private MessageService messageService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PassToken
    @ApiOperation(("发送邮箱验证码接口"))
    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
    @ApiImplicitParam(name = "userEmail", value = "邮箱账号", required = true, paramType = "query")
    public ServiceResp sendVerifyCode(@NotBlank(message = "E-mail can not be empty") String userEmail) {
        try {
            return messageService.sendEmail(userEmail);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Sending code function error");
            return new ServiceResp().error("System error");
        }
    }
}

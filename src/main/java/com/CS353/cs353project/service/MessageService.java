package com.CS353.cs353project.service;


import com.CS353.cs353project.async.JmsProducer;
import com.CS353.cs353project.exception.SendMailException;
import com.CS353.cs353project.param.model.Email.SendEmailModel;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.utils.VerifyCodeUtils;
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageService {

    @Value("${mail.fromMail.sender}")
    private String sender;// 发送者

    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    private JmsProducer jmsProducer;
    @Autowired
    private VerifyCodeUtils verifyCodeUtils;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 发送邮箱验证码
     */
    public ServiceResp sendEmail(String email) {
        //生成对应邮箱的验证码
        ServiceResp resp = verifyCodeUtils.sendCode(email);
        if (resp.getHead().getRespCode() == -1) {
            return resp;
        } else {
            String code = (String) resp.getBody();
            SendEmailModel model = new SendEmailModel();
            model.setEmail(email);
            model.setMsg("Your verification code is: " + code + ", validity period is 2 minutes");
            //发送邮件
            String json = JSON.toJSONString(model);
            jmsProducer.sendMsg("HSBM.mail.send", json);//????destinationNmae>>???
            logger.info(String.format("Registration email has been sent to %s", email));
            return new ServiceResp().success("send verify code successfully, expire time 2 minutes");
        }
    }

    /**
     * 发送邮件信息
     */
    @JmsListener(destination = "HSBM.mail.send")
    public void sendEmailMsg(String json) {
        SendEmailModel model = JSONObject.parseObject(json, SendEmailModel.class);
        try {
            //生成其他信息
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(model.getEmail());
            message.setSubject("Second hand book market validation");// 标题
            message.setText("[SHBM] " + model.getMsg());// 内容
            //发送邮件
            javaMailSender.send(message);
        } catch (MailSendException e) {
            logger.error("Target email " + model.getEmail() + " does not exist. failed to send mail");
            throw new SendMailException("Target email " + model.getEmail() + " does not exist. failed to send mail");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Sending email system error");
        }
    }
}

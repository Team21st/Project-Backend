package com.CS353.cs353project.service;


import com.CS353.cs353project.async.JmsProducer;
import com.CS353.cs353project.exception.SendMailException;
import com.CS353.cs353project.param.evt.Message.SendAuditFailedMsgEvt;
import com.CS353.cs353project.param.evt.Message.SendAuthorizeMsgEvt;
import com.CS353.cs353project.param.evt.Message.SendBanUserMsgEvt;
import com.CS353.cs353project.param.evt.Message.SendOffShelveReasonEvt;
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
            jmsProducer.sendMsg("SHBM.mail.send", json);//????destinationNmae>>???
            logger.info(String.format("Registration email has been sent to %s", email));
            return new ServiceResp().success("send verify code successfully, expire time 2 minutes");
        }
    }


    /**
     * 发送下架原因
     */
    public void sendOffShelveReason(SendOffShelveReasonEvt evt) {
        SendEmailModel model = new SendEmailModel();
        model.setEmail(evt.getEmail());
        model.setMsg("Dear user, your on shelve book: \"" + evt.getBookName() + "\" had been off shelve by shopping administrator. the reason as below:\n" + evt.getReason());
        //发送邮件
        String json = JSON.toJSONString(model);
        jmsProducer.sendMsg("SHBM.mail.send", json);//????destinationNmae>>???
        logger.info(String.format("Off Shelve Reason email has been sent to %s", evt.getEmail()));
    }

    /**
     * 发送封禁、解封用户邮件
     */
    public void senBanUserMsg(SendBanUserMsgEvt evt){
        SendEmailModel model = new SendEmailModel();
        model.setEmail(evt.getUserEmail());
        model.setMsg("Dear user, you have been baned by administrator just now, the reason is as showed as below:\n  "+evt.getBanReason());
        //发送邮件
        String json = JSON.toJSONString(model);
        jmsProducer.sendMsg("SHBM.mail.send", json);
        logger.info(String.format("ban Reason email has been sent to %s", evt.getUserEmail()));
    }

    /**
     * 发送不通过用户认证原因邮件
     */
    public void senAuthorizeMsg(SendAuthorizeMsgEvt evt){
        SendEmailModel model = new SendEmailModel();
        model.setEmail(evt.getUserEmail());
        model.setMsg("Dear user, your user authentication request has been rejected, the reason is as showed as below:\n  "+evt.getReason());
        //发送邮件
        String json = JSON.toJSONString(model);
        jmsProducer.sendMsg("SHBM.mail.send", json);
        logger.info(String.format(" Refuse authentication reason email has been sent to %s", evt.getUserEmail()));
    }

    /**
     * 发送审核不通过原因邮件
     */
    public void sendAuditFailedMsg(SendAuditFailedMsgEvt evt){
        SendEmailModel model = new SendEmailModel();
        model.setEmail(evt.getUserEmail());
        model.setMsg("Dear user, Your request for approval of your product: "+evt.getCommodityName()+" has been rejected, the reason is as showed as below:\n  "+evt.getReason());
        //发送邮件
        String json = JSON.toJSONString(model);
        jmsProducer.sendMsg("SHBM.mail.send", json);
        logger.info(String.format(" send audit failed reason email has been sent to %s", evt.getUserEmail()));
    }

    /**
     * 发送邮件信息
     */
    @JmsListener(destination = "SHBM.mail.send")
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

package com.CS353.cs353project.param.evt.Message;

import lombok.Data;

@Data
public class SendCancelOrderMsgEvt {
    //操作人角色
    private Integer operatorRole;
    //取消理由
    private String cancelReason;
    //用户邮箱
    private String userEmail;
    //书本名称
    private String bookName;
    //操作人名称
    private String operatorName;
}

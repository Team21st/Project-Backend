package com.CS353.cs353project.param.evt.Message;

import lombok.Data;

@Data
public class OperateCancelOperationMsgEvt {
    //用户邮箱
    private String userEmail;
    //进行的操作（0：同哟，1：拒绝）
    private Integer operation;
    //拒绝的理由
    private String reason;
    //操作人邮箱
    private String operator;
    //图书名
    private String bookName;
}

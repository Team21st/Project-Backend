package com.CS353.cs353project.param.evt.Message;

import lombok.Data;

@Data
public class SendOffShelveReasonEvt {
    //用户邮箱
    private String email;
    //要下架的书名
    private  String bookName;
    //下架信息
    private String reason;
}

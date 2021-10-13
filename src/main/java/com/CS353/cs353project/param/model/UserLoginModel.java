package com.CS353.cs353project.param.model;

import com.CS353.cs353project.bean.UserBean;
import lombok.Data;

@Data
public class UserLoginModel {
    //令牌
    private String token;
    //用户信息
    private UserBean userBean;
}

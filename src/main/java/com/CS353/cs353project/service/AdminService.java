package com.CS353.cs353project.service;

import com.CS353.cs353project.param.evt.AdminRegisterEvt;
import com.CS353.cs353project.param.out.ServiceResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 注册
     */
    public ServiceResp adminRegister(AdminRegisterEvt evt) {
        //通知其他管理员来确认是否通过
        return new ServiceResp().success("success");
    }
}

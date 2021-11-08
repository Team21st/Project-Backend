package com.CS353.cs353project.service;

import com.CS353.cs353project.bean.CommodityBean;
import com.CS353.cs353project.bean.UserBean;
import com.CS353.cs353project.dao.mapper.ManagementMapper;
import com.CS353.cs353project.dao.mapper.Trade.CommodityMapper;
import com.CS353.cs353project.dao.mapper.UserMapper;
import com.CS353.cs353project.param.evt.Management.*;
import com.CS353.cs353project.param.model.Management.QueryAuditRecordsModel;
import com.CS353.cs353project.param.out.ServiceResp;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
public class ManagementService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private ManagementMapper managementMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询审核记录接口
     */
    public ServiceResp queryAuditRecords(HttpServletRequest request, QueryAuditRecordsEvt evt) {
        Page<QueryAuditRecordsModel> page = new Page<>(evt.getQueryPage(), evt.getQuerySize());
        Page<QueryAuditRecordsModel> modelPage = commodityMapper.queryAuditRecords(evt, page);
        if (modelPage.getRecords() == null) {
            return new ServiceResp().error("no records");
        }

        //拼接成价格格式
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        for (QueryAuditRecordsModel model : page.getRecords()) {
            BigDecimal decimalPrice = new BigDecimal(model.getBookPrice());
            StringBuffer sb1 = new StringBuffer(df.format(decimalPrice));
            sb1.insert(0, "$");
            model.setTruePrice(new String(sb1));
        }
        return new ServiceResp().success(modelPage);
    }

    /**
     * 查询所有用户
     */
    public ServiceResp queryAllUsers(QueryAllUserEvt evt) {
        Page<UserBean> page = new Page<>(evt.getQueryPage(), evt.getQuerySize());
        Page<UserBean> modelPage = managementMapper.queryAllUsers(evt, page);
        if (modelPage.getRecords() == null) {
            return new ServiceResp().error("no users has been found");
        }
        return new ServiceResp().success(modelPage);
    }

    /**
     * 封禁\解封禁用户
     */
    public ServiceResp banUser(BanUserEvt evt) {
        UserBean userInfo = userMapper.queryUserByNo(evt.getUserNo());
        if (userInfo == null) {
            return new ServiceResp().error("User doesn't exist");
        }
        int result;
        if (evt.getIsBan() == 1) {
            result = managementMapper.banUser(evt.getUserNo());
        } else {
            result = managementMapper.disBanUser(evt.getUserNo());
        }
        if (result != 1){
            return new ServiceResp().error("Failed to modify the user ban status");
        }
        return new ServiceResp().success("Successfully modify the user ban status");
    }


    /**
     * 封禁\解封禁用户
     */
    public ServiceResp authorizeUser(AuthorizeUserEvt evt) {
        UserBean userInfo = userMapper.queryUserByNo(evt.getUserNo());
        if (userInfo == null) {
            return new ServiceResp().error("User doesn't exist");
        }
        if(userInfo.getAuthentication()==0){
            return new ServiceResp().error("this user haven't ask for authorize yet");
        }
        if(userInfo.getAuthentication()!=1){
            return new ServiceResp().error("this user's authorization status had been changed");
        }
        int result=0;
        if (evt.getAuthentication() == 2) {//通过
            result = managementMapper.authorizeUser(evt.getUserNo());
        } else if(evt.getAuthentication() ==3){
            result = managementMapper.disAuthorizeUser(evt.getUserNo());
        }
        if (result != 1){
            return new ServiceResp().error("Failed to modify the user ban status");
        }
        return new ServiceResp().success("Successfully modify the user ban status");
    }

    /**
     * 通过\拒绝审核
     */
    public ServiceResp auditBooks(HttpServletRequest request,AuditBooksEvt evt){
        CommodityBean commodityInfo = commodityMapper.queryOneRecord(evt.getBookNo());
        if(commodityInfo==null){
            return new ServiceResp().error("record doesn't exist");
        }
        if("1".equals(evt.getAuditStatus())){
            commodityInfo.setAuditStatus("1");
        }else if("2".equals(evt.getAuditStatus())){
            commodityInfo.setAuditStatus("2");
        }
        commodityInfo.setUpdateUser((String) request.getAttribute("userEmail"));
        int result = commodityMapper.updateById(commodityInfo);
        if(result!=1){
            return new ServiceResp().error("audit books fail");
        }
        return new ServiceResp().success("audit books successfully");
    }
}

package com.CS353.cs353project.service;

import com.CS353.cs353project.bean.*;
import com.CS353.cs353project.dao.mapper.ManagementMapper;
import com.CS353.cs353project.dao.mapper.Trade.CommPicMapper;
import com.CS353.cs353project.dao.mapper.Trade.CommodityMapper;
import com.CS353.cs353project.dao.mapper.Trade.OrderMapper;
import com.CS353.cs353project.dao.mapper.UserMapper;
import com.CS353.cs353project.dao.mapper.comment.CommentMapper;
import com.CS353.cs353project.param.evt.Comment.QueryCommentEvt;
import com.CS353.cs353project.param.evt.Management.*;
import com.CS353.cs353project.param.evt.Message.SendAuditFailedMsgEvt;
import com.CS353.cs353project.param.evt.Message.SendAuthorizeMsgEvt;
import com.CS353.cs353project.param.evt.Message.SendBanUserMsgEvt;
import com.CS353.cs353project.param.evt.Message.SendOnShelveMsgEvt;
import com.CS353.cs353project.param.model.Management.AdministratorHomepageModel;
import com.CS353.cs353project.param.model.Management.QueryAuditRecordsModel;
import com.CS353.cs353project.param.out.ServiceResp;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ManagementService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private ManagementMapper managementMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CommPicMapper commPicMapper;

    /**
     * ????????????????????????
     */
    public ServiceResp queryAuditRecords(QueryAuditRecordsEvt evt) {
        Page<QueryAuditRecordsModel> page = new Page<>(evt.getQueryPage(), evt.getQuerySize());
        Page<QueryAuditRecordsModel> modelPage = commodityMapper.queryAuditRecords(evt, page);
        if (modelPage.getRecords() == null) {
            return new ServiceResp().error("no records");
        }

        //?????????????????????,?????????bookPicUrl

        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        for (QueryAuditRecordsModel model : page.getRecords()) {
            //?????????????????????????????????
            QueryWrapper<CommPicBean> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("status","E");
            queryWrapper.eq("bookNo",model.getBookNo());
            List<CommPicBean> commPicBeanList= commPicMapper.selectList(queryWrapper);
            List<String> picList=new ArrayList<>();
            for(CommPicBean bean : commPicBeanList){
                picList.add(bean.getPictureUrl());
            }
            model.setPicList(picList);
            BigDecimal decimalPrice = new BigDecimal(model.getBookPrice());
            StringBuffer sb1 = new StringBuffer(df.format(decimalPrice));
            sb1.insert(0, "$");
            model.setTruePrice(new String(sb1));
        }
        return new ServiceResp().success(modelPage);
    }

    /**
     * ??????????????????
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
     * ??????????????????????????????
     */
    public ServiceResp banUser(HttpServletRequest request, BanUserEvt evt) {
        UserBean userInfo = userMapper.queryUserByNo(evt.getUserNo());
        if (userInfo == null) {
            return new ServiceResp().error("User doesn't exist");
        }
        String userEmail = (String) request.getAttribute("userEmail");
        userInfo.setUpdateUser(userEmail);
        int result;
        if (evt.getIsBan() == 1) {
            //????????????????????????
            SendBanUserMsgEvt msgEvt = new SendBanUserMsgEvt();
            msgEvt.setUserEmail(userInfo.getUserEmail());
            msgEvt.setBanReason(evt.getBanReason());
            messageService.senBanUserMsg(msgEvt);
            userInfo.setIsBan(1);
        } else {
            userInfo.setIsBan(0);
        }
        result = userMapper.updateById(userInfo);
        if (result != 1) {
            return new ServiceResp().error("Failed to modify the user ban status");
        }
        return new ServiceResp().success("Successfully modify the user ban status");
    }


    /**
     * ??????????????????????????????
     */
    public ServiceResp authorizeUser(HttpServletRequest request, AuthorizeUserEvt evt) {
        UserBean userInfo = userMapper.queryUserByNo(evt.getUserNo());
        String userEmail = (String) request.getAttribute("userEmail");
        if (userInfo == null) {
            return new ServiceResp().error("User doesn't exist");
        }
        if (userInfo.getAuthentication() == 0) {
            return new ServiceResp().error("this user haven't ask for authorize yet");
        }
        userInfo.setUpdateUser((String) request.getAttribute("userEmail"));
        if (evt.getAuthentication() == 2) {//??????
            SendAuthorizeMsgEvt msgEvt = new SendAuthorizeMsgEvt();
            msgEvt.setAuthorizeStatus(1);
            msgEvt.setUserEmail(userInfo.getUserEmail());
            messageService.sendAuthorizeMsg(msgEvt);
            userInfo.setAuthentication(2);
        } else if (evt.getAuthentication() == 3) {//?????????
            //??????????????????
            SendAuthorizeMsgEvt msgEvt = new SendAuthorizeMsgEvt();
            msgEvt.setAuthorizeStatus(2);
            msgEvt.setUserEmail(userInfo.getUserEmail());
            msgEvt.setReason(evt.getReason());
            messageService.sendAuthorizeMsg(msgEvt);
            userInfo.setAuthentication(3);
        }
        int result = userMapper.updateById(userInfo);
        if (result != 1) {
            return new ServiceResp().error("Failed to modify the user ban status");
        }
        return new ServiceResp().success("Successfully modify the user ban status");
    }

    /**
     * ??????\????????????
     */
    public ServiceResp auditBooks(HttpServletRequest request, AuditBooksEvt evt) {
        CommodityBean commodityInfo = commodityMapper.selectById(evt.getBookNo());
        String administratorEmail = (String) request.getAttribute("userEmail");
        String sellerNo = commodityInfo.getSellerNo();
        UserBean sellerInfo = userMapper.selectById(sellerNo);
        if (commodityInfo == null) {
            return new ServiceResp().error("audit record doesn't exist");
        }
        if (sellerInfo == null) {
            return new ServiceResp().error("seller record doesn't exist");
        }
        if ("1".equals(evt.getAuditStatus())) {//????????????
            commodityInfo.setAuditStatus("1");
            //???????????????????????????????????????
            SendOnShelveMsgEvt msgEvt = new SendOnShelveMsgEvt();
            msgEvt.setUserEmail(commodityInfo.getCreateUser());
            msgEvt.setBookName(commodityInfo.getBookName());
            messageService.sendOnShelveReason(msgEvt);
            //???????????????????????????????????????
            Integer releaseCommNum = sellerInfo.getReleaseCommNum() + 1;
            sellerInfo.setReleaseCommNum(releaseCommNum);
            sellerInfo.setUpdateUser(administratorEmail);
            int result = userMapper.updateById(sellerInfo);
            if (result != 1) {
                return new ServiceResp().error("update releaseCommNum failed");
            }
        } else if ("2".equals(evt.getAuditStatus())) {//???????????????
            //???????????????????????????
            SendAuditFailedMsgEvt msgEvt = new SendAuditFailedMsgEvt();
            msgEvt.setReason(evt.getReason());
            msgEvt.setUserEmail(commodityInfo.getCreateUser());
            msgEvt.setCommodityName(commodityInfo.getBookName());
            messageService.sendAuditFailedMsg(msgEvt);
            commodityInfo.setAuditStatus("2");
        }
        commodityInfo.setUpdateUser(administratorEmail);
        commodityInfo.setAuditor(administratorEmail);
        commodityInfo.setAuditTime(new Date());
        int result = commodityMapper.updateById(commodityInfo);
        if (result != 1) {
            return new ServiceResp().error("audit books fail");
        }
        return new ServiceResp().success("audit books successfully");
    }

    /**
     * ?????????????????????
     */
    public ServiceResp administratorHomepageDisplay(HttpServletRequest request){
        String adminNo=(String) request.getAttribute("userNo");
        AdministratorHomepageModel finalModel= new AdministratorHomepageModel();
        UserBean adminInfo=userMapper.selectById(adminNo);
        if(adminInfo==null){
            return new ServiceResp().error("admin information not found");
        }
        BeanUtils.copyProperties(adminInfo,finalModel);//???????????????????????????
        if(adminInfo.getUserRoot()==1){
            finalModel.setUserRoot("?????????");
        }else{
            finalModel.setUserRoot("????????????");
        }
        QueryWrapper<UserBean> queryUserNumWrapper=new QueryWrapper<>();
        queryUserNumWrapper.eq("status","E");
        int totalUserNum= userMapper.selectCount(queryUserNumWrapper);
        finalModel.setTotalUserNum(totalUserNum);//??????????????????
        QueryWrapper<OrderBean> queryOrderWrapper=new QueryWrapper<>();
        queryOrderWrapper.eq("status","E");
        int totalOrderNum= orderMapper.selectCount(queryOrderWrapper);
        finalModel.setTotalOrderNum(totalOrderNum);//??????????????????
        double totalSales = orderMapper.queryTotalSales();
        //??????????????????
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        //???????????????
        BigDecimal decimalPrice = new BigDecimal(totalSales);
        StringBuffer sb1 = new StringBuffer(df.format(decimalPrice));
        sb1.insert(0, "$");
        finalModel.setTotalSales(new String(sb1));//??????????????????
        Integer totalLoginNum = userMapper.queryTotalLoginNum();
        finalModel.setTotalLoginNum(totalLoginNum);//??????????????????
        QueryWrapper<CommodityBean> queryTotalBookNumWrapper = new QueryWrapper<>();
        queryTotalBookNumWrapper.eq("status","E")
                .eq("auditStatus",1);
        Integer totalBookNum = commodityMapper.selectCount(queryTotalBookNumWrapper);
        finalModel.setTotalBookNum(totalBookNum);//??????????????????
        return new ServiceResp().success(finalModel);
    }

}

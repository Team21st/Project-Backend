package com.CS353.cs353project.service;

import com.CS353.cs353project.bean.CommodityBean;
import com.CS353.cs353project.bean.OrderBean;
import com.CS353.cs353project.dao.mapper.Trade.CommodityMapper;
import com.CS353.cs353project.dao.mapper.Trade.OrderMapper;
import com.CS353.cs353project.param.evt.Message.SendOffShelveReasonEvt;
import com.CS353.cs353project.param.evt.Trade.*;
import com.CS353.cs353project.param.model.Oss.AliyunOssResultModel;
import com.CS353.cs353project.param.model.Trade.QueryCommoditiesModel;
import com.CS353.cs353project.param.model.Trade.QueryOrderListModel;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.utils.AliyunOSSUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;

@Service
public class TradeService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 上架图书接口(提交审核)
     */
    public ServiceResp bookOnShelve(HttpServletRequest request, MultipartFile file, BookOnShelveEvt evt) {
        //用户邮箱(唯一账户识别标准,而不是用户名)
        String userEmail = (String) request.getAttribute("userEmail");
        String userName = (String) request.getAttribute("userName");
        String sellerNo = (String) request.getAttribute("userNo");

        CommodityBean commodityBean = new CommodityBean();
        BeanUtils.copyProperties(evt, commodityBean);
        commodityBean.setBookNo(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        commodityBean.setUserName(userName);
        commodityBean.setRecommend(0);
        commodityBean.setBookStock(evt.getBookStock());
        commodityBean.setBookSale(0);
        commodityBean.setSellerNo(sellerNo);
        if (evt.getCustomTags() != null) {
            commodityBean.setCustomTags(evt.getCustomTags());
        }
        commodityBean.setStatus("E");
        commodityBean.setCreateUser(userEmail);
        commodityBean.setUpdateUser(userEmail);
        commodityBean.setAuditStatus("0");

        //上传图书实物图
        String ossUrl = "SHBM/OnShelveBook/" + userEmail + "/" + evt.getBookName();
        AliyunOssResultModel resultModel = AliyunOSSUtil.uploadFile(file, ossUrl);
        if (!resultModel.isSuccess()) {//若上传图片不成功
            return new ServiceResp().error(resultModel.getMsg());
        }
        String bookPicUrl = resultModel.getUrl();
        commodityBean.setBookPicUrl(bookPicUrl);
        //存入数据库
        int result = commodityMapper.insert(commodityBean);
        if (result == 1) {
            logger.info(userEmail + " make books on shelve");
            return new ServiceResp().success(" make books on shelve success");
        }
        return new ServiceResp().error("On shelve failed");
    }

    /**
     * 修改商品信息
     */
    public ServiceResp editBookOnShelve(HttpServletRequest request,EditBookOnShelveEvt evt) {
        CommodityBean recordInfo = commodityMapper.queryOneRecord(evt.getBookNo());
        if (recordInfo == null) {
            return new ServiceResp().error("can't find the record information");
        }
        BeanUtils.copyProperties(recordInfo, evt);
        recordInfo.setUpdateUser((String) request.getAttribute("userEmail"));
        int result = commodityMapper.updateById(recordInfo);
        if (result != 1) {
            return new ServiceResp().error("edit on shelve record failed");
        }
        return new ServiceResp().success("edit on shelve record failed success");
    }

    /**
     * 下架商品
     */
    public ServiceResp bookOffShelve(HttpServletRequest request,BookOffShelveEvt evt) {
        CommodityBean recordInfo = commodityMapper.queryOneRecord(evt.getBookNo());
        if (recordInfo == null) {
            return new ServiceResp().error("can't find the record information");
        }
        recordInfo.setStatus("D");
        recordInfo.setUpdateUser((String) request.getAttribute("userEmail"));
        if (evt.getUserRole() == 1) {//若是由管理员下架的，发邮件说明原因
            SendOffShelveReasonEvt evt1 = new SendOffShelveReasonEvt();
            evt1.setBookName(recordInfo.getBookName());
            evt1.setReason(evt.getReason());
            evt1.setEmail(recordInfo.getCreateUser());
            messageService.sendOffShelveReason(evt1);
        }
        int result = commodityMapper.updateById(recordInfo);
        if (result != 1) {
            return new ServiceResp().error("delete on shelve record failed");
        }
        return new ServiceResp().success("delete on shelve record failed success");
    }

    /**
     * 查询商品
     */
    public ServiceResp queryCommodities(QueryCommoditiesEvt evt) {
        Page<QueryCommoditiesModel> page = new Page<>(evt.getQueryPage(), evt.getQuerySize());
        Page<QueryCommoditiesModel> modelPage = commodityMapper.queryCommodities(evt, page);
        if (modelPage.getRecords() == null) {
            return new ServiceResp().error("can not find relative books");
        }

        //拼接成价格格式
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        for (QueryCommoditiesModel model : page.getRecords()) {
            BigDecimal decimalPrice = new BigDecimal(model.getBookPrice());
            StringBuffer sb1 = new StringBuffer(df.format(decimalPrice));
            sb1.insert(0, "$");
            model.setTruePrice(new String(sb1));
        }
        return new ServiceResp().success(modelPage);
    }

    /**
     * 下单商品
     */
    public ServiceResp placeOrder(HttpServletRequest request, PlaceOrderEvt evt) {
        String buyerNo =(String)request.getAttribute("userNo");
        //查询商品状况
        CommodityBean commodityInfo = commodityMapper.queryOneRecord(evt.getBookNo());
        if (commodityInfo.getBookStock() == 0) {
            return new ServiceResp().error("this book has been out of the shelve just now");
        }
        int currentStock = commodityInfo.getBookStock() - evt.getNum();
        int currentSell = commodityInfo.getBookSale()+ evt.getNum();
        if (currentStock < 0) {
            return new ServiceResp().error("the goods you purchased: " + commodityInfo.getBookName() + "  has exceeded the actual quantity");
        }
        commodityInfo.setBookStock(currentStock);
        commodityInfo.setBookSale(currentSell);
        OrderBean orderBean = new OrderBean();
        BeanUtils.copyProperties(evt, orderBean);
        orderBean.setBuyerNo(buyerNo);
        orderBean.setStatus("E");
        orderBean.setBookName(commodityInfo.getBookName());
        orderBean.setOrderNo(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        orderBean.setOrderStatus(0);
        orderBean.setCreateUser((String) request.getAttribute("userEmail"));
        orderBean.setUpdateUser((String) request.getAttribute("userEmail"));
        int result2 = orderMapper.insert(orderBean);
        if (result2 != 1) {
            return new ServiceResp().error("place order fail");
        }
        int result1 = commodityMapper.updateById(commodityInfo);
        if (result1 != 1) {
            return new ServiceResp().error("place order fail, when changing inventory quantity");
        }
        return new ServiceResp().success(orderBean);
    }

    /**
     * 商家查看订单
     */
    public ServiceResp sellerQueryOrderList(SellerQueryOrderListEvt evt) {
        Page<QueryOrderListModel> page = new Page<>(evt.getQueryPage(), evt.getQuerySize());
        Page<QueryOrderListModel> modelPage = orderMapper.sellerQueryOrderList(evt, page);
        if (modelPage.getRecords() == null) {
            return new ServiceResp().error("can not find relative order list");
        }
        //拼接成价格格式
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        for (QueryOrderListModel model : page.getRecords()) {
            BigDecimal decimalPrice = new BigDecimal(model.getPrice());
            StringBuffer sb1 = new StringBuffer(df.format(decimalPrice));
            sb1.insert(0, "$");
            model.setTurePrice(new String(sb1));
        }
        return new ServiceResp().success(modelPage);
    }

    /**
     * 买家查看订单
     */
    public ServiceResp buyerQueryOrderList(BuyerQueryOrderListEvt evt) {
        Page<QueryOrderListModel> page = new Page<>(evt.getQueryPage(), evt.getQuerySize());
        Page<QueryOrderListModel> modelPage = orderMapper.buyerQueryOrderList(evt, page);
        if (modelPage.getRecords() == null) {
            return new ServiceResp().error("can not find relative order list");
        }
        //拼接成价格格式
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        for (QueryOrderListModel model : page.getRecords()) {
            BigDecimal decimalPrice = new BigDecimal(model.getPrice());
            StringBuffer sb1 = new StringBuffer(df.format(decimalPrice));
            sb1.insert(0, "$");
            model.setTurePrice(new String(sb1));
        }
        return new ServiceResp().success(modelPage);
    }
}

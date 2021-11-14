package com.CS353.cs353project.service;

import com.CS353.cs353project.bean.CommodityBean;
import com.CS353.cs353project.bean.OrderBean;
import com.CS353.cs353project.bean.ShoppingCartBean;
import com.CS353.cs353project.bean.UserBean;
import com.CS353.cs353project.dao.mapper.Trade.CommodityMapper;
import com.CS353.cs353project.dao.mapper.Trade.OrderMapper;
import com.CS353.cs353project.dao.mapper.Trade.ShoppingCartMapper;
import com.CS353.cs353project.dao.mapper.UserMapper;
import com.CS353.cs353project.param.evt.Message.SendOffShelveReasonEvt;
import com.CS353.cs353project.param.evt.Trade.*;
import com.CS353.cs353project.param.evt.Trade.ShoppingCart.AddShoppingCartEvt;
import com.CS353.cs353project.param.evt.Trade.ShoppingCart.EditShoppingCartEvt;
import com.CS353.cs353project.param.evt.Trade.ShoppingCart.PlaceCartOrderEvt;
import com.CS353.cs353project.param.model.Oss.AliyunOssResultModel;
import com.CS353.cs353project.param.model.Trade.QueryCommoditiesModel;
import com.CS353.cs353project.param.model.Trade.QueryOrderListModel;
import com.CS353.cs353project.param.out.ServiceHeader;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.utils.AliyunOSSUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.*;

@Service
public class TradeService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private UserMapper userMapper;

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
        commodityBean.setSellerName(userName);
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
    public ServiceResp editBookOnShelve(HttpServletRequest request, EditBookOnShelveEvt evt) {
        CommodityBean recordInfo = commodityMapper.queryOneRecord(evt.getBookNo());
        if (recordInfo == null) {
            return new ServiceResp().error("can't find the record information");
        }
        BeanUtils.copyProperties(evt, recordInfo);
        recordInfo.setUpdateUser((String) request.getAttribute("userEmail"));
        int result = commodityMapper.updateById(recordInfo);
        if (result != 1) {
            return new ServiceResp().error("edit on shelve record failed");
        }
        return new ServiceResp().success("edit on shelve record  success");
    }

    /**
     * 下架商品
     */
    public ServiceResp bookOffShelve(HttpServletRequest request, BookOffShelveEvt evt) {
        CommodityBean recordInfo = commodityMapper.queryOneRecord(evt.getBookNo());
        if (recordInfo == null) {
            return new ServiceResp().error("can't find the record information");
        }
        recordInfo.setStatus("D");
        recordInfo.setUpdateUser((String) request.getAttribute("userEmail"));
        if (evt.getUserRole() == 1) {//若是由管理员下架的，发邮件说明原因
            SendOffShelveReasonEvt msgEvt = new SendOffShelveReasonEvt();
            msgEvt.setBookName(recordInfo.getBookName());
            msgEvt.setReason(evt.getReason());
            msgEvt.setEmail(recordInfo.getCreateUser());
            messageService.sendOffShelveReason(msgEvt);
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
    public ServiceResp buyerQueryOrderList(HttpServletRequest request, BuyerQueryOrderListEvt evt) {
        String buyerNo = (String) request.getAttribute("userNo");
        evt.setBuyerNo(buyerNo);
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

    /**
     * 加入购物车
     */
    public ServiceResp addShoppingCart(HttpServletRequest request, AddShoppingCartEvt evt) {
        String buyerNo = (String) request.getAttribute("userNo");
        String buyerEmail = (String) request.getAttribute("userEmail");
        UserBean userInfo = userMapper.queryUserByNo(buyerNo);
        if (userInfo == null) {
            return new ServiceResp().error("user doesn't exist");
        }
        CommodityBean commodityInfo = commodityMapper.queryOneRecord(evt.getBookNo());
        if (commodityInfo == null) {
            return new ServiceResp().error("commodity doesn't exist");
        }
        //加入t_shoppingCart表
        ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
        shoppingCartBean.setId(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        shoppingCartBean.setBuyerNo(buyerNo);
        shoppingCartBean.setBuyerEmail(buyerEmail);
        shoppingCartBean.setBookNo(commodityInfo.getBookNo());
        shoppingCartBean.setBookName(commodityInfo.getBookName());
        shoppingCartBean.setSellerName(commodityInfo.getSellerName());
        shoppingCartBean.setSellerNo(commodityInfo.getSellerNo());
        shoppingCartBean.setBookPrice(commodityInfo.getBookPrice());
        shoppingCartBean.setBookPicUrl(commodityInfo.getBookPicUrl());
        shoppingCartBean.setBookTag(commodityInfo.getBookTag());
        shoppingCartBean.setNewOldDegree(commodityInfo.getNewOldDegree());
        shoppingCartBean.setStatus("E");
        shoppingCartBean.setNum(evt.getNum());
        shoppingCartBean.setUpdateUser(buyerEmail);
        shoppingCartBean.setCreateUser(buyerEmail);

        int result = shoppingCartMapper.insert(shoppingCartBean);

        if (result != 1) {
            return new ServiceResp().error("add to shopping cart error");
        } else return new ServiceResp().success("add to shopping cart successfully");
    }

    /**
     * 查询购物车
     */
    public ServiceResp queryShoppingCart(HttpServletRequest request) {
        String buyerNo = (String) request.getAttribute("userNo");
        List<ShoppingCartBean> returnModel = shoppingCartMapper.queryShoppingCart(buyerNo);
        if (returnModel == null) {
            return new ServiceResp().error("can not find relative shopping cart list");
        }
        Map<String, List<ShoppingCartBean>> shoppingCartMap = new HashMap<>();
        String sellerName = "";
        List<ShoppingCartBean> list = new ArrayList<>();
        for (ShoppingCartBean bean : returnModel) {
            if (StringUtils.isBlank(sellerName)) {
                sellerName = bean.getSellerName();
            }
            if (!sellerName.equals(bean.getSellerName())) {//若商家不同
                sellerName = bean.getSellerName();
                list = new ArrayList<>();//清空list
            }
            list.add(bean);
            shoppingCartMap.put(sellerName, list);
        }
        return new ServiceResp().success(shoppingCartMap);
    }

    /**
     * 修改购物车内商品信息(数量、删除)
     */
    public ServiceResp editShoppingCart(HttpServletRequest request, EditShoppingCartEvt evt) {
        ShoppingCartBean shoppingCartInfo = shoppingCartMapper.selectById(evt.getId());
        if (shoppingCartInfo == null) {
            return new ServiceResp().error("record doesn't exist");
        }
        QueryWrapper<CommodityBean> queryWrapper = new QueryWrapper<CommodityBean>();
        queryWrapper
                .eq("status", "E")
                .eq("bookNo", shoppingCartInfo.getBookNo());
        CommodityBean commodityInfo = commodityMapper.selectOne(queryWrapper);
        if (commodityInfo == null) {
            return new ServiceResp().error("this book had already been off shelved");
        }
        //去除购物车商品
        if ("D".equals(evt.getStatus())) {
            shoppingCartInfo.setStatus("D");
        }
        //更新商品数量
        if (evt.getNum() != null) {
            if (commodityInfo.getBookStock() == 0) {
                return new ServiceResp().error("The book has been snapped up");
            }
            if (commodityInfo.getBookStock() < evt.getNum()) {
                return new ServiceResp().error("the number of book you purchase is bigger than stock!");
            }
            shoppingCartInfo.setNum(evt.getNum());
        }
        shoppingCartInfo.setUpdateUser((String) request.getAttribute("userEmail"));
        int result = shoppingCartMapper.updateById(shoppingCartInfo);
        if (result != 1) {
            return new ServiceResp().error("edit shopping cart error");
        }
        return new ServiceResp().success("edit shopping cart successfully");
    }

    /**
     * 下单商品
     */
    public ServiceResp placeOrder(HttpServletRequest request, PlaceOrderEvt evt) {
        String buyerNo = (String) request.getAttribute("userNo");
        //查询商品状况
        CommodityBean commodityInfo = commodityMapper.queryOneRecord(evt.getBookNo());
        if (commodityInfo.getBookStock() == 0) {
            return new ServiceResp().error("this book has been out of the shelve just now");
        }
        int currentStock = commodityInfo.getBookStock() - evt.getNum();
        int currentSell = commodityInfo.getBookSale() + evt.getNum();
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
     * 购物车内下单(批量下单)
     */
    public ServiceResp palaceCartOrder(PlaceCartOrderEvt evt) {
        String[] idArray = evt.getIdArray();
        List<String> responseList = new ArrayList<>();
        for (String id : idArray) {
            String responseMsg="";
            ShoppingCartBean shoppingCartInfo = shoppingCartMapper.selectById(id);
            String bookName = shoppingCartInfo.getBookName();
            //查询商品状况
            CommodityBean commodityInfo = commodityMapper.queryOneRecord(shoppingCartInfo.getBookNo());
            if(commodityInfo==null){
                responseMsg="your book: "+bookName+" place order failed, the reason is: book record doesn't exist";
                responseList.add(responseMsg);
                continue;
            }
            if(commodityInfo.getBookStock()==0){
                responseMsg="your book: "+bookName+" place order failed, the reason is: this book has been out of the shelve just now";
                responseList.add(responseMsg);
                continue;
            }
            int currentStock = commodityInfo.getBookStock() - shoppingCartInfo.getNum();
            int currentSell = commodityInfo.getBookSale() + shoppingCartInfo.getNum();
            if (currentStock < 0) {
                responseMsg="your book: "+bookName+" place order failed, the reason is: this order has exceeded the actual quantity";
                responseList.add(responseMsg);
                continue;
            }
            commodityInfo.setBookStock(currentStock);
            commodityInfo.setBookSale(currentSell);
            //更新stock、sale
            int result1 = commodityMapper.updateById(commodityInfo);
            if (result1 != 1) {
                responseMsg="your book: "+bookName+" place order failed, the reason is: place order fail, when changing inventory quantity";
                responseList.add(responseMsg);
                continue;
            }
            OrderBean orderBean = new OrderBean();
            BeanUtils.copyProperties(evt, orderBean);
            //计算同一种书本的总价,并存入数据库
            Double price =commodityInfo.getBookPrice()*shoppingCartInfo.getNum();
            orderBean.setPrice(price);
            orderBean.setNum(shoppingCartInfo.getNum());
            //存入sellerNo，bookNo
            orderBean.setBookNo(shoppingCartInfo.getBookNo());
            orderBean.setSellerNo(shoppingCartInfo.getSellerNo());
            //存入其他必存
            orderBean.setBuyerNo(shoppingCartInfo.getBuyerNo());
            orderBean.setStatus("E");
            orderBean.setBookName(shoppingCartInfo.getBookName());
            orderBean.setOrderNo(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
            orderBean.setOrderStatus(0);
            orderBean.setCreateUser(shoppingCartInfo.getBuyerEmail());
            orderBean.setUpdateUser(shoppingCartInfo.getBuyerEmail());
            int result = orderMapper.insert(orderBean);
            if (result != 1) {
                //需要变更回去？
                commodityInfo.setBookStock(currentStock + shoppingCartInfo.getNum());
                commodityInfo.setBookSale(currentSell - shoppingCartInfo.getNum());
                commodityMapper.updateById(commodityInfo);
                //记错
                responseMsg="your book: "+bookName+" place order failed, the reason is: failed when add order to the database";
                responseList.add(responseMsg);
                continue;
            }
            //删除t_shoppingCart的记录
            shoppingCartInfo.setStatus("D");
            int result2=shoppingCartMapper.updateById(shoppingCartInfo);
            if(result2 !=1){
                responseMsg="your book: "+bookName+" place order failed, the reason is: place order fail, when deleting shopping cart record";
                responseList.add(responseMsg);
                continue;
            }
        }
        if(responseList.size()==0){
            return new ServiceResp().success("place orders successfully!");
        }
        ServiceResp resp =new ServiceResp();
        resp.setBody(responseList);
        ServiceHeader header =new ServiceHeader();
        header.setRespCode(-1);
        header.setRespMsg("there are some orders failed");
        resp.setHead(header);
        return resp;
    }
}

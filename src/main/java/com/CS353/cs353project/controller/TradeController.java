package com.CS353.cs353project.controller;

import com.CS353.cs353project.param.evt.Trade.*;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.service.TradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/trade")
@Api(tags = "交易相关接口")
@CrossOrigin
@Validated
public class TradeController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TradeService tradeService;

    /**
     * 上架图书接口(提交审核)
     */
    @ResponseBody
    @ApiOperation(value = "上架图书接口", notes = "")
    @RequestMapping(value = "/bookOnShelve", method = RequestMethod.POST)
    public ServiceResp bookOnShelve(HttpServletRequest request, MultipartFile file,BookOnShelveEvt evt) {
        try {
            return tradeService.bookOnShelve(request, file, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Book On Shelve function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 修改商品信息
     */
    @ResponseBody
    @ApiOperation(value = "修改商品信息接口", notes = "")
    @RequestMapping(value = "/editBookOnShelve", method = RequestMethod.POST)
    public ServiceResp editBookOnShelve(@RequestBody EditBookOnShelveEvt evt){
        try {
            return tradeService.editBookOnShelve(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("edit book 0n shelve function error");
            return new ServiceResp().error("System error");
        }
    }


    /**
     * 下架商品
     */
    @ResponseBody
    @ApiOperation(value = "下架商品接口", notes = "")
    @RequestMapping(value = "/bookOffShelve", method = RequestMethod.POST)
    public ServiceResp bookOffShelve(@RequestBody BookOffShelveEvt evt){
        try {
            return tradeService.bookOffShelve(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("delete book 0n shelve function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 查询商品
     */
    @ResponseBody
    @ApiOperation(value = "查询商品", notes = "")
    @RequestMapping(value = "/queryCommodities", method = RequestMethod.POST)
    public ServiceResp queryCommodities(@RequestBody QueryCommoditiesEvt evt){
        try {
            return tradeService.queryCommodities(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("query commodities function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 下单商品
     */
    @ResponseBody
    @ApiOperation(value = "下单商品",notes = "")
    @RequestMapping(value = "/placeOrder",method = RequestMethod.POST)
    public ServiceResp placeOrder(HttpServletRequest request, @RequestBody PlaceOrderEvt evt){
        try {
            return tradeService.placeOrder(request,evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("place Order function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 商家查看订单
     */
    @ResponseBody
    @ApiOperation(value = "商家查看订单",notes = "")
    @RequestMapping(value = "/sellerQueryOrderList",method = RequestMethod.POST)
    public ServiceResp sellerQueryOrderList(@RequestBody SellerQueryOrderListEvt evt){
        try {
            return tradeService.sellerQueryOrderList(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("seller query Order List function error");
            return new ServiceResp().error("System error");
        }
    }

    /**
     * 买家查看订单
     */
    @ResponseBody
    @ApiOperation(value = "买家查看订单",notes = "")
    @RequestMapping(value = "/buyerQueryOrderList",method = RequestMethod.POST)
    public ServiceResp buyerQueryOrderList(@RequestBody BuyerQueryOrderListEvt evt){
        try {
            return tradeService.buyerQueryOrderList(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("buyer query Order List function error");
            return new ServiceResp().error("System error");
        }
    }
}

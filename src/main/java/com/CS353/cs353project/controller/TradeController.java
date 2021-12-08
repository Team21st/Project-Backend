package com.CS353.cs353project.controller;

import com.CS353.cs353project.anotation.PassToken;
import com.CS353.cs353project.dao.mapper.Trade.ProcessingCancellationRequestEvt;
import com.CS353.cs353project.param.evt.Trade.*;
import com.CS353.cs353project.param.evt.Trade.Order.*;
import com.CS353.cs353project.param.evt.Trade.ShoppingCart.AddShoppingCartEvt;
import com.CS353.cs353project.param.evt.Trade.ShoppingCart.EditShoppingCartEvt;
import com.CS353.cs353project.param.evt.Trade.ShoppingCart.PlaceCartOrderEvt;
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
    public ServiceResp bookOnShelve(HttpServletRequest request, BookOnShelveEvt evt) {
        try {
            return tradeService.bookOnShelve(request, evt.getFile(), evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Book On Shelve function error");
            return new ServiceResp().error(e.toString());
        }
    }

    /**
     * 修改商品信息
     */
    @ResponseBody
    @ApiOperation(value = "修改商品信息接口", notes = "")
    @RequestMapping(value = "/editBookOnShelve", method = RequestMethod.POST)
    public ServiceResp editBookOnShelve(HttpServletRequest request, @RequestBody EditBookOnShelveEvt evt) {
        try {
            return tradeService.editBookOnShelve(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("edit book 0n shelve function error");
            return new ServiceResp().error(e.getMessage());
        }
    }


    /**
     * 下架商品（商家也可下架尚未通过审核的商品）
     */
    @ResponseBody
    @ApiOperation(value = "下架商品接口", notes = "")
    @RequestMapping(value = "/bookOffShelve", method = RequestMethod.POST)
    public ServiceResp bookOffShelve(HttpServletRequest request, @RequestBody BookOffShelveEvt evt) {
        try {
            return tradeService.bookOffShelve(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("delete book 0n shelve function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 查询商品
     */
    @PassToken
    @ResponseBody
    @ApiOperation(value = "查询商品", notes = "")
    @RequestMapping(value = "/queryCommodities", method = RequestMethod.POST)
    public ServiceResp queryCommodities(@RequestBody QueryCommoditiesEvt evt) {
        try {
            return tradeService.queryCommodities(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("query commodities function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 查询商家
     */
    @PassToken
    @ResponseBody
    @ApiOperation(value = "查询商家", notes = "")
    @RequestMapping(value = "/queryBusiness", method = RequestMethod.POST)
    public ServiceResp queryBusiness(@RequestBody QueryBusinessEvt evt) {
        try {
            return tradeService.queryBusiness(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("query business function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 下单商品
     */
    @ResponseBody
    @ApiOperation(value = "下单商品", notes = "")
    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
    public ServiceResp placeOrder(HttpServletRequest request, @RequestBody PlaceOrderEvt evt) {
        try {
            return tradeService.placeOrder(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("place Order function error");
            return new ServiceResp().error(e.getMessage());
        }
    }


    /**
     * 查看订单
     */
    @ResponseBody
    @ApiOperation(value = "查看订单", notes = "")
    @RequestMapping(value = "/queryOrder", method = RequestMethod.POST)
    public ServiceResp queryOrder(HttpServletRequest request, @RequestBody QueryOrderEvt evt) {
        try {
            return tradeService.queryOrder(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("query Order List function error");
            return new ServiceResp().error(e.getMessage());
        }
    }


    /**
     * 添加购物车
     */
    @ResponseBody
    @ApiOperation(value = "添加购物车", notes = "")
    @RequestMapping(value = "/addShoppingCart", method = RequestMethod.POST)
    public ServiceResp addShoppingCart(HttpServletRequest request, @RequestBody AddShoppingCartEvt evt) {
        try {
            return tradeService.addShoppingCart(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("add shopping cart function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 查询购物车
     */
    @ResponseBody
    @ApiOperation(value = "查询购物车", notes = "")
    @RequestMapping(value = "/queryShoppingCart", method = RequestMethod.POST)
    public ServiceResp queryShoppingCart(HttpServletRequest request) {
        try {
            return tradeService.queryShoppingCart(request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("query shopping cart function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 修改购物车内商品信息(数量、删除)
     */
    @ResponseBody
    @ApiOperation(value = "修改购物车内商品信息(数量、删除)", notes = "")
    @RequestMapping(value = "/editShoppingCart", method = RequestMethod.POST)
    public ServiceResp editShoppingCart(HttpServletRequest request, @RequestBody EditShoppingCartEvt evt) {
        try {
            return tradeService.editShoppingCart(request, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("edit shopping cart function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 购物车内下单(批量下单)
     */
    @ResponseBody
    @ApiOperation(value = "购物车内下单(批量下单)", notes = "")
    @RequestMapping(value = "/placeCartOrder", method = RequestMethod.POST)
    public ServiceResp placeCartOrder(@RequestBody PlaceCartOrderEvt evt) {
        try {
            return tradeService.placeCartOrder(evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("palace cart order function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 取消订单（商家或买家）
     */
    @ResponseBody
    @ApiOperation(value = "取消订单（商家或买家）", notes = "")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public ServiceResp cancelOrder(HttpServletRequest request,@RequestBody CancelOrderEvt evt) {
        try {
            return tradeService.cancelOrder(request,evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("cancel order function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 商家确认订单发货
     */
    @ResponseBody
    @ApiOperation(value = "商家确认订单发货", notes = "")
    @RequestMapping(value = "/deliverGood", method = RequestMethod.POST)
    public ServiceResp deliverGood(HttpServletRequest request,@RequestBody DeliverGoodEvt evt) {
        try {
            return tradeService.deliverGood(request,evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deliver good function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 买家确认收货
     */
    @ResponseBody
    @ApiOperation(value = "买家确认收货", notes = "")
    @RequestMapping(value = "/confirmReceipt", method = RequestMethod.POST)
    public ServiceResp confirmReceipt(HttpServletRequest request,@RequestBody String orderNo) {
        try {
            return tradeService.confirmReceipt(request,orderNo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("confirm receipt function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 卖家处理"取消订单申请"
     */
    @ResponseBody
    @ApiOperation(value = "卖家处理\"取消订单申请\"", notes = "")
    @RequestMapping(value = "/processingCancellationRequest", method = RequestMethod.POST)
    public ServiceResp processingCancellationRequest(HttpServletRequest request,@RequestBody ProcessingCancellationRequestEvt evt) {
        try {
            return tradeService.processingCancellationRequest(request,evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("processing Cancellation Request function error");
            return new ServiceResp().error(e.getMessage());
        }
    }

    /**
     * 删除订单记录
     */
    @ResponseBody
    @ApiOperation(value = "删除订单记录", notes = "")
    @RequestMapping(value = "/deleteOrderRecord", method = RequestMethod.POST)
    public ServiceResp deleteOrderRecord(HttpServletRequest request, @RequestBody DeleteOrderRecordEvt evt) {
        try {
            return tradeService.deleteOrderRecord(request,evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("delete Order Record function error");
            return new ServiceResp().error(e.getMessage());
        }
    }
}

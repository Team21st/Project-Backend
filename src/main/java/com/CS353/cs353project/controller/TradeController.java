package com.CS353.cs353project.controller;

import com.CS353.cs353project.param.evt.Trade.BookOnShelveEvt;
import com.CS353.cs353project.param.evt.Trade.EditBookOnShelveEvt;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.service.TradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

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
    public ServiceResp editBookOnShelve(EditBookOnShelveEvt evt){
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
    @RequestMapping(value = "/deleteBookOnShelve", method = RequestMethod.POST)
    public ServiceResp deleteBookOnShelve(@NotBlank(message="bookNo can not be empty") @ApiParam("图书编码") String bookNo){
        try {
            return tradeService.deleteBookOnShelve(bookNo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("delete book 0n shelve function error");
            return new ServiceResp().error("System error");
        }
    }
}

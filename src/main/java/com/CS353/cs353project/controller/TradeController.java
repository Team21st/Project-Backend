package com.CS353.cs353project.controller;

import com.CS353.cs353project.param.evt.BookOnShelveEvt;
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
    public ServiceResp bookOnShelve(HttpServletRequest request, MultipartFile file,BookOnShelveEvt evt) throws Exception {
        try {
            return tradeService.bookOnShelve(request, file, evt);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Book On Shelve function error");
            return new ServiceResp().error("System error");
        }
    }


}

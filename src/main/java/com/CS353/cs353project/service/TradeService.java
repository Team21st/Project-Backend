package com.CS353.cs353project.service;

import com.CS353.cs353project.bean.CommodityBean;
import com.CS353.cs353project.dao.mapper.TradeMapper;
import com.CS353.cs353project.param.evt.Trade.BookOnShelveEvt;
import com.CS353.cs353project.param.evt.Trade.EditBookOnShelveEvt;
import com.CS353.cs353project.param.evt.Trade.QueryCommoditiesEvt;
import com.CS353.cs353project.param.model.Management.QueryAuditRecordsModel;
import com.CS353.cs353project.param.model.Oss.AliyunOssResultModel;
import com.CS353.cs353project.param.model.Trade.QueryCommoditiesModel;
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
    private TradeMapper tradeMapper;

    /**
     * 上架图书接口(提交审核)
     */
    public ServiceResp bookOnShelve(HttpServletRequest request, MultipartFile file, BookOnShelveEvt evt) {
        //用户邮箱(唯一账户识别标准,而不是用户名)
        String userEmail = (String) request.getAttribute("userEmail");
        String userName=(String) request.getAttribute("userName");

        CommodityBean commodityBean = new CommodityBean();
        BeanUtils.copyProperties(evt, commodityBean);
        commodityBean.setBookNo(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        commodityBean.setUserName(userName);
        commodityBean.setRecommend(0);
        commodityBean.setBookStock(evt.getBookStock());
        if (evt.getCustomTags() != null) {
            commodityBean.setCustomTags(evt.getCustomTags());
        }
        commodityBean.setStatus("E");
        commodityBean.setCreateUser(userEmail);
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
        int result = tradeMapper.insert(commodityBean);
        if (result == 1) {
            logger.info(userEmail + " make books on shelve");
            return new ServiceResp().success(" make books on shelve success");
        }
        return new ServiceResp().error("On shelve failed");
    }

    /**
     * 修改商品信息
     */
    public ServiceResp editBookOnShelve(EditBookOnShelveEvt evt) {
        CommodityBean recordInfo = tradeMapper.queryOneRecord(evt.getBookNo());
        if (recordInfo == null) {
            return new ServiceResp().error("can't find the record information");
        }
        BeanUtils.copyProperties(recordInfo,evt);
        int result= tradeMapper.updateById(recordInfo);
        if(result!=1){
            return new ServiceResp().error("edit on shelve record failed");
        }
        return new ServiceResp().success("edit on shelve record failed success");
    }

    /**
     * 下架商品
     */
    public ServiceResp deleteBookOnShelve(String bookNo){
        CommodityBean recordInfo = tradeMapper.queryOneRecord(bookNo);
        if (recordInfo == null) {
            return new ServiceResp().error("can't find the record information");
        }
        recordInfo.setStatus("D");
        int result= tradeMapper.updateById(recordInfo);
        if(result!=1){
            return new ServiceResp().error("delete on shelve record failed");
        }
        return new ServiceResp().success("delete on shelve record failed success");
    }

    /**
     * 查询商品
     */
    public ServiceResp queryCommodities(QueryCommoditiesEvt evt){
        Page<QueryCommoditiesModel> page =new Page<>(evt.getQueryPage(),evt.getQuerySize());
        Page<QueryCommoditiesModel> modelPage= tradeMapper.queryCommodities(evt,page);
        if (modelPage==null){
            return new ServiceResp().error("can not find relative books");
        }

        //拼接成价格格式
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        for(QueryCommoditiesModel model: page.getRecords()){
            BigDecimal decimalPrice = new BigDecimal(model.getBookPrice());
            StringBuffer sb1 = new StringBuffer(df.format(decimalPrice));
            sb1.insert(0, "$");
            model.setTruePrice(new String(sb1));
        }
        return new ServiceResp().success(modelPage);
    }


}

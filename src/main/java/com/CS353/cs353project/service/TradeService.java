package com.CS353.cs353project.service;

import com.CS353.cs353project.bean.CommodityBean;
import com.CS353.cs353project.dao.mapper.TradeMapper;
import com.CS353.cs353project.param.evt.BookOnShelveEvt;
import com.CS353.cs353project.param.model.AliyunOssResultModel;
import com.CS353.cs353project.param.model.QueryAuditRecordsModel;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.utils.AliyunOSSUtil;
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
import java.util.List;
import java.util.UUID;

@Service
public class TradeService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TradeMapper tradeMapper;

    public ServiceResp bookOnShelve(HttpServletRequest request, MultipartFile file, BookOnShelveEvt evt){
        //用户邮箱(唯一账户识别标准,而不是用户名)
        String userEmail = (String) request.getAttribute("userEmail");

        CommodityBean commodityBean=new CommodityBean();
        BeanUtils.copyProperties(evt,commodityBean);
        commodityBean.setBookNo(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        commodityBean.setUserEmail(userEmail);
        commodityBean.setRecommend(0);
        commodityBean.setBookStock(evt.getBookStock());
        commodityBean.setStatus("E");
        commodityBean.setCreateUser(userEmail);
        commodityBean.setAuditStatus("0");

        //上传图书实物图
        String ossUrl = "SHBM/OnShelveBook/"+userEmail+"/"+evt.getBookName();
        AliyunOssResultModel resultModel= AliyunOSSUtil.uploadFile(file,ossUrl);
        if(!resultModel.isSuccess()){//若上传图片不成功
            return new ServiceResp().error(resultModel.getMsg());
        }
        String bookPicUrl = resultModel.getUrl();
        commodityBean.setBookPicUrl(bookPicUrl);
        //存入数据库
        int result = tradeMapper.insert(commodityBean);
        if (result == 1) {
            logger.info(userEmail+" make books on shelve");
            return new ServiceResp().success(" make books on shelve success");
        }
        return new ServiceResp().error("On shelve failed");
    }


}

package com.CS353.cs353project.service;

import com.CS353.cs353project.dao.mapper.TradeMapper;
import com.CS353.cs353project.param.evt.Management.QueryAuditRecordsEvt;
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
    private TradeMapper tradeMapper;

    public ServiceResp queryAuditRecords(HttpServletRequest request, QueryAuditRecordsEvt evt){
        Page<QueryAuditRecordsModel> page=new Page<>(evt.getQueryPage(),evt.getQuerySize());
        Page<QueryAuditRecordsModel> modelPage= tradeMapper.queryAuditRecords(evt,page);

        if (modelPage==null){
            return new ServiceResp().error("no records");
        }

        //拼接成价格格式
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        for(QueryAuditRecordsModel model: page.getRecords()){
            BigDecimal decimalPrice = new BigDecimal(model.getBookPrice());
            StringBuffer sb1 = new StringBuffer(df.format(decimalPrice));
            sb1.insert(0, "$");
            model.setTruePrice(new String(sb1));
        }
        return new ServiceResp().success(modelPage);
    }
}

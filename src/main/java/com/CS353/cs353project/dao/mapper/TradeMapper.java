package com.CS353.cs353project.dao.mapper;

import com.CS353.cs353project.bean.CommodityBean;
import com.CS353.cs353project.dao.provider.TradeProvider;
import com.CS353.cs353project.param.model.QueryAuditRecordsModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TradeMapper extends BaseMapper<CommodityBean> {

    /**
     *查询审核记录
     */
   @SelectProvider(type = TradeProvider.class, method = "queryAuditRecords")
   List<QueryAuditRecordsModel> queryAuditRecords(String type);

}

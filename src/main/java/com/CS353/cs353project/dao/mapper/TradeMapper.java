package com.CS353.cs353project.dao.mapper;

import com.CS353.cs353project.bean.CommodityBean;
import com.CS353.cs353project.dao.provider.TradeProvider;
import com.CS353.cs353project.param.evt.Management.QueryAuditRecordsEvt;
import com.CS353.cs353project.param.evt.Trade.QueryCommoditiesEvt;
import com.CS353.cs353project.param.model.Management.QueryAuditRecordsModel;
import com.CS353.cs353project.param.model.Trade.QueryCommoditiesModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TradeMapper extends BaseMapper<CommodityBean> {

    /**
     *查询审核记录
     */
   @SelectProvider(type = TradeProvider.class, method = "queryAuditRecords")
   Page<QueryAuditRecordsModel> queryAuditRecords(QueryAuditRecordsEvt evt, Page<QueryAuditRecordsModel> page);

    /**
     * 查询指定一单审核记录
     */
    @Select("select * from t_commodity where status='E' and bookNo= #{bookNo}")
    CommodityBean queryOneRecord(@Param("bookNo") String bookNo);

    /**
     * 查询商品
     */
    @SelectProvider(type= TradeProvider.class,method = "queryCommodities")
    Page<QueryCommoditiesModel> queryCommodities(QueryCommoditiesEvt evt, Page<QueryCommoditiesModel> page);
}

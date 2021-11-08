package com.CS353.cs353project.dao.mapper.Trade;

import com.CS353.cs353project.bean.OrderBean;
import com.CS353.cs353project.dao.provider.TradeProvider;
import com.CS353.cs353project.param.evt.Trade.BuyerQueryOrderListEvt;
import com.CS353.cs353project.param.evt.Trade.SellerQueryOrderListEvt;
import com.CS353.cs353project.param.model.Trade.QueryOrderListModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper extends BaseMapper<OrderBean> {
    /**
     * 商家查看订单
     */
    @SelectProvider(type = TradeProvider.class,method = "sellerQueryOrderList")
    Page<QueryOrderListModel> sellerQueryOrderList(@Param("evt") SellerQueryOrderListEvt evt, Page<QueryOrderListModel> page);

    /**
     * 买家查看订单
     */
    @SelectProvider(type = TradeProvider.class,method = "buyerQueryOrderList")
    Page<QueryOrderListModel> buyerQueryOrderList(@Param("evt") BuyerQueryOrderListEvt evt, Page<QueryOrderListModel> page);
}

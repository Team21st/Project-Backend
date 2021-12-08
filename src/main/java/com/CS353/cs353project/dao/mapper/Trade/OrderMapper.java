package com.CS353.cs353project.dao.mapper.Trade;

import com.CS353.cs353project.bean.OrderBean;
import com.CS353.cs353project.dao.provider.TradeProvider;
import com.CS353.cs353project.param.evt.Trade.Order.QueryOrderEvt;
import com.CS353.cs353project.param.model.Trade.QueryOrderModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface OrderMapper extends BaseMapper<OrderBean> {
  /**
   *查看订单
   */
  @SelectProvider(value = TradeProvider.class,method = "queryOrder")
  Page<QueryOrderModel> queryOrder(@Param("evt") QueryOrderEvt evt, Page<QueryOrderModel> page);

  /**
   *查询总销售额
   */
  @Select("SELECT sum(price) FROM t_order where status='E' and orderStatus != 4")
  double queryTotalSales();
}

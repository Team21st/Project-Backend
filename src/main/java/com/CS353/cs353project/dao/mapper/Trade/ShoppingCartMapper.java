package com.CS353.cs353project.dao.mapper.Trade;

import com.CS353.cs353project.bean.ShoppingCartBean;
import com.CS353.cs353project.dao.provider.TradeProvider;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ShoppingCartMapper extends BaseMapper<ShoppingCartBean> {
    /**
     * 查询购物车
     */
    @SelectProvider(type = TradeProvider.class, method = "queryShoppingCart")
    List<ShoppingCartBean> queryShoppingCart(@Param("buyerNo") String buyerNo);
}

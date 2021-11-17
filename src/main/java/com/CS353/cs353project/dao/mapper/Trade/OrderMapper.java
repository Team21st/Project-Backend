package com.CS353.cs353project.dao.mapper.Trade;

import com.CS353.cs353project.bean.OrderBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper extends BaseMapper<OrderBean> {

}

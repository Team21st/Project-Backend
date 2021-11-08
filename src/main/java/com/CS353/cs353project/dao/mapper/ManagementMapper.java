package com.CS353.cs353project.dao.mapper;


import com.CS353.cs353project.bean.UserBean;
import com.CS353.cs353project.dao.provider.ManagementProvider;
import com.CS353.cs353project.param.evt.Management.QueryAllUserEvt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ManagementMapper {
    /**
     * 查询所有用户
     */
    @SelectProvider(type = ManagementProvider.class, method = "queryAllUsers")
    Page<UserBean> queryAllUsers(QueryAllUserEvt evt, Page<UserBean> page);

    /**
     * 封禁用户
     */
    @Update("update t_user set isBan=1 where userNo=#{userNo} and status ='E'")
    Integer banUser(@Param("userNo") String userNo);

    /**
     * 解封禁用户
     */
    @Update("update t_user set isBan = 0 where userNo=#{userNo} and status ='E'")
    Integer disBanUser(@Param("userNo") String userNo);

    /**
     * 用户认证通过
     */
    @Update("update t_user set authentication = 2 where userNo=#{userNo} and status ='E'")
    Integer authorizeUser(@Param("userNo") String userNo);

    /**
     * 用户认证不通过
     */
    @Update("update t_user set authentication = 3 where userNo=#{userNo} and status ='E'")
    Integer disAuthorizeUser(@Param("userNo") String userNo);
}

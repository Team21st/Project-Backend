package com.CS353.cs353project.dao.mapper;

import com.CS353.cs353project.bean.UserBean;
import com.CS353.cs353project.dao.provider.UserProvider;
import com.CS353.cs353project.param.model.User.QueryMyCommodityModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserBean> {

    /**
     * 查询用户
     * 通过邮箱查询
     */
    @Select("select * from t_user where userEmail = #{userEmail} and status = 'E'")
    UserBean queryUserByEmail(@Param("userEmail") String userEmail);

    /**
     * 判断用户名是否重名
     */
    @Select("select * from t_user where userName=#{userName} and status ='E'")
    UserBean judgeUserName(@Param("userName") String userName);

    /**
     * 查询用户
     * 通过用户编码查询
     */
    @Select("select * from t_user where userNo = #{userNo} and status = 'E'")
    UserBean queryUserByNo(@Param("userNo") String userNo);

    /**
     * 查询上架商品信息（包括所有审核状态）
     */
    @SelectProvider(type= UserProvider.class,method = "queryMyCommodity")
    Page<QueryMyCommodityModel> queryMyCommodity(@Param("createUser") String createUser, Page<QueryMyCommodityModel> page);

}
